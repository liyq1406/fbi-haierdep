package org.fbi.dep.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.netty.http.NettyChannelBufferStreamCache;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.fbi.dep.component.jms.JmsBytesClient;
import org.fbi.dep.enums.SBSFormCode;
import org.fbi.dep.enums.TxnRtnCode;
import org.fbi.dep.helper.MD5Helper;
import org.fbi.dep.management.TxnChecker;
import org.fbi.dep.management.TxnUseridChecker;
import org.fbi.dep.model.CheckResult;
import org.fbi.dep.model.base.ToaXmlHttpErr;
import org.fbi.dep.transform.AbstractTiaBytesTransform;
import org.fbi.dep.transform.AbstractToaBytesTransform;
import org.fbi.dep.txn.AbstractTxnProcessor;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by XIANGYANG on 2015-5-23.
 * http协议
 */

public class SbsHttpRouteBuilder extends RouteBuilder {

    private static Logger logger = LoggerFactory.getLogger(SbsHttpRouteBuilder.class);
    private static String SERVER_IP = PropertyManager.getProperty("dep.localhost.ip");
    private String server_port;

    public SbsHttpRouteBuilder(String port) {
        this.server_port = port;
    }

    @Override
    public void configure() throws Exception {
        from("netty-http:http://" + SERVER_IP + ":" + server_port + "/depService?matchOnUriPrefix=false").
                process(new Processor() {
                            public void process(Exchange exchange) throws Exception {
                                String rtnmac = null;
                                String txnCode = null;
                                String userId = null;
                                String reqsn = null;
                                String version = null;
                                String txnDate = null;
                                String txnTime = null;
                                String userKey = null;
                                String rtnXml = null;
                                try {
                                    NettyChannelBufferStreamCache cf = (NettyChannelBufferStreamCache) exchange.getIn().getBody();
                                    StringBuffer sb = new StringBuffer();
                                    int l;
                                    byte[] tmp = new byte[2048];
                                    while ((l = cf.read(tmp)) != -1) {
                                        sb.append(new String(tmp, 0, l));
                                    }
                                    byte[] bytes = sb.toString().getBytes();
                                    logger.info("【SbsHttpRouteBuilder 接收到报文】" + new String(bytes));

                                    //客户端请求报文格式为(Encrypt-MD5:32字节MD5值+回车换行+XML报文体)
                                    //1.解析mac
                                    int macLength = 32;
                                    byte[] macBytes = new byte[macLength];
                                    System.arraycopy(bytes, 12, macBytes, 0, macBytes.length);
                                    String mac = new String(macBytes, "GBK");

                                    //2.解析XML报文中info部分
                                    byte[] bodyBytes = new byte[bytes.length - macLength - 13];
                                    System.arraycopy(bytes, macLength + 13, bodyBytes, 0, bodyBytes.length);
                                    String xmlMsgData = new String(bodyBytes, "GBK");

                                    SAXReader saxReader = new SAXReader();
                                    InputStream inputStream = new ByteArrayInputStream(bodyBytes);
                                    Document document = saxReader.read(inputStream);
                                    Element root = document.getRootElement();
                                    for (Iterator i = root.elementIterator(); i.hasNext(); ) {
                                        Element info = (Element) i.next();
                                        if ("info".equals(info.getName())){
                                            for (Iterator j = info.elementIterator(); j.hasNext(); ) {
                                                Element node = (Element) j.next();
                                                if ("txncode".equals(node.getName())){
                                                    txnCode = node.getText();
                                                }
                                                if ("userid".equals(node.getName())){
                                                    userId = node.getText();
                                                }
                                                if ("reqsn".equals(node.getName())){
                                                    reqsn = node.getText();
                                                }
                                                if ("version".equals(node.getName())){
                                                    version = node.getText();
                                                }
                                                if ("txndate".equals(node.getName())){
                                                    txnDate = node.getText();
                                                }
                                                if ("txntime".equals(node.getName())){
                                                    txnTime = node.getText();
                                                }
                                            }
                                            break;
                                        }
                                    }

                                    //3.MD5校验
                                    // MAC(32位，以Message Data部分 +  USER_ID + USER_KEY）为依据产生的用ASC字符表示的16进制MD5值。其中USER_KEY由财务公司针对每个用户单独下发。
                                    userKey = PropertyManager.getProperty("wsys.userkey." + userId);
                                    String md5 = MD5Helper.getMD5String(xmlMsgData + userId + userKey);
                                    // 验证失败 返回验证失败信息
                                    if (!md5.equalsIgnoreCase(mac)) {
                                        logger.info("用户标识：" + userId + " " + userKey + " 交易时间:" + txnDate + " " + txnTime);
                                        logger.info("MAC校验失败[服务端]MD5:" + md5 + "[客户端]MAC:" + mac);
                                        throw new RuntimeException(TxnRtnCode.MSG_VERIFY_MAC_ILLEGAL.toRtnMsg());
                                    } else {
                                        logger.info("MAC校验成功,用户标识：" + userId + " " + userKey + " 交易时间:" + txnDate + " " + txnTime);
                                    }

                                    // 未通过闸口时，抛出异常，但暂时保留CheckResult
                                    CheckResult checkResult = new CheckResult(userId, txnCode);
                                    // 用户交易权限闸口
                                    new TxnUseridChecker().check(userId, txnCode, checkResult);
                                    // 业务数据闸口
                                    String checkerClass = PropertyManager.getProperty("check." + userId + "." + txnCode);
                                    if (!StringUtils.isEmpty(checkerClass)) {
                                        logger.info(txnCode + "交易启动闸口：" + checkerClass);
                                        TxnChecker checker = (TxnChecker) Class.forName(checkerClass).newInstance();
                                        checker.check(userId, txnCode, xmlMsgData, checkResult);
                                    }
                                    // 特殊交易特殊处理
                                    Class txnClass = null;
                                    try {
                                        txnClass = Class.forName("org.fbi.dep.txn.Txn" + txnCode + "Processor");
                                    } catch (ClassNotFoundException e) {
                                        txnClass = null;
                                    }
                                    if (txnClass != null) {
                                        AbstractTxnProcessor processor = (AbstractTxnProcessor) txnClass.newInstance();
                                        rtnXml = processor.process(userId, xmlMsgData);
                                    } else {
                                        // 直连SBS
                                        AbstractTiaBytesTransform bytesTransform = (AbstractTiaBytesTransform) Class.forName("org.fbi.dep.transform.TiaXml" + txnCode + "Transform").newInstance();
                                        byte[] sbsReqMsg = bytesTransform.run(xmlMsgData, userId);
                                        // SBS
                                        byte[] sbsResBytes = new JmsBytesClient().sendRecivMsg("900", "fcdep", "fcdep", txnCode, userId,
                                                "queue.dep.core.fcdep.sbs", "queue.dep.core.sbs.fcdep", sbsReqMsg);
                                        // 报文转换为dep-sbs-xml报文
                                        AbstractToaBytesTransform toaTransform = (AbstractToaBytesTransform) Class.forName("org.fbi.dep.transform.ToaXml" + txnCode + "Transform").newInstance();
                                        rtnXml = toaTransform.run(sbsResBytes);
                                    }
                                    rtnmac = MD5Helper.getMD5String(rtnXml + userId + userKey);
                                    logger.info("【SbsHttpRouteBuilder 发送报文】" + new String((rtnmac + "\n" + rtnXml).getBytes("GBK")));
                                    exchange.getOut().setBody((rtnmac + "\n" + rtnXml).getBytes("GBK"));
                                } catch (Exception e) {
                                    //  返回异常信息
                                    ToaXmlHttpErr errXmlHttp = new ToaXmlHttpErr();
                                    errXmlHttp.getInfo().setTxncode(txnCode);
                                    errXmlHttp.getInfo().setUserid(userId);
                                    errXmlHttp.getInfo().setReqsn(reqsn);
                                    errXmlHttp.getInfo().setVersion(version);
                                    errXmlHttp.getInfo().setTxndate(txnDate);
                                    errXmlHttp.getInfo().setTxntime(txnTime);

                                    if (txnCode == null) {
                                        String errMsg = "报文解析失败，无法解析到交易码.";
                                        logger.error(errMsg, e);
                                        errXmlHttp.getInfo().setRtncode(TxnRtnCode.MSG_ANALYSIS_ILLEGAL.getCode());
                                        errXmlHttp.getInfo().setRtnmsg(errMsg);
                                        errXmlHttp.getBody().setRtncode(TxnRtnCode.MSG_ANALYSIS_ILLEGAL.getCode());
                                        errXmlHttp.getBody().setRtnmsg(errMsg);
                                    } else {
                                        String exmsg = e.getMessage();
                                        logger.error("交易异常", e);
                                        if (exmsg == null) {
                                            exmsg = TxnRtnCode.SERVER_EXCEPTION.getCode() + "|" + TxnRtnCode.SERVER_EXCEPTION.getTitle();
                                        } else if (!exmsg.contains("|")) {
                                            exmsg = TxnRtnCode.SERVER_EXCEPTION.getCode() + "|" + exmsg;
                                        }
                                        String errmsg[] = exmsg.split("\\|");
                                        SBSFormCode msgFormCode = SBSFormCode.valueOfAlias(errmsg[0]);
                                        if (msgFormCode != null) {
                                            errmsg[1] = msgFormCode.getTitle();
                                        }
                                        errXmlHttp.getInfo().setRtncode(errmsg[0]);
                                        errXmlHttp.getInfo().setRtnmsg(errmsg[1]);
                                        errXmlHttp.getBody().setRtncode(errmsg[0]);
                                        errXmlHttp.getBody().setRtnmsg(errmsg[1]);
                                    }
                                    rtnXml = errXmlHttp.toString();
                                    rtnmac = MD5Helper.getMD5String(rtnXml + userId + userKey);
                                    logger.info("【SbsHttpRouteBuilder 发送报文】" + new String((rtnmac + "\n" + rtnXml).getBytes("GBK")));
                                    exchange.getOut().setBody((rtnmac + "\n" + rtnXml).getBytes("GBK"));
                                }
                            }
                        }
                );
    }
}
