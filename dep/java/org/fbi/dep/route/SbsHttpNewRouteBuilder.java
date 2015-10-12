package org.fbi.dep.route;

import com.thoughtworks.xstream.converters.ConversionException;
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
import org.fbi.dep.transform.AbstractTiaBytesTransform;
import org.fbi.dep.transform.AbstractTiaToToa;
import org.fbi.dep.transform.AbstractToaBytesTransform;
import org.fbi.dep.txn.AbstractTxnProcessor;
import org.fbi.dep.util.PropertyManager;
import org.fbi.dep.util.StringPad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by XIANGYANG on 2015-5-23.
 * http协议
 */

public class SbsHttpNewRouteBuilder extends RouteBuilder {

    private static Logger logger = LoggerFactory.getLogger(SbsHttpNewRouteBuilder.class);
    private static String SERVER_IP = PropertyManager.getProperty("dep.localhost.ip");
    private String server_port;

    public SbsHttpNewRouteBuilder(String port) {
        this.server_port = port;
    }

    @Override
    public void configure() throws Exception {
        from("netty-http:http://" + SERVER_IP + ":" + server_port + "/depService?matchOnUriPrefix=false").
            process(new Processor() {
                            public void process(Exchange exchange) throws Exception {
                                byte[] bytes = (byte[]) exchange.getIn().getBody();
                                logger.info("接收到报文：" + new String(bytes));
                                String rtnMsgHeader = null;
                                String rtnMsgData = null;
                                String txnCode = null;
                                String txnDate = null;
                                String userid = null;
                                String userkey = null;
                                try {
                                    // 解析报文头
                                    int headerLength = 94;
                                    byte[] headerBytes = new byte[headerLength];
                                    System.arraycopy(bytes, 0, headerBytes, 0, headerBytes.length);
                                    String msgHeader = new String(headerBytes, "GBK");
                                    rtnMsgHeader = msgHeader.substring(0, 38);

                                    // 报文体
                                    byte[] bodyBytes = new byte[bytes.length - headerLength];
                                    System.arraycopy(bytes, headerLength, bodyBytes, 0, bodyBytes.length);
                                    String msgData = new String(bodyBytes, "GBK");
                                    rtnMsgData = msgData;
                                    // 外围系统用户ID、交易码、交易日期、mac
                                    userid = msgHeader.substring(4, 14).trim();
                                    userkey = PropertyManager.getProperty("wsys.userkey." + userid);
                                    txnCode = msgHeader.substring(14, 24).trim();
                                    txnDate = msgHeader.substring(24, 32).trim();
                                    String txnTime = msgHeader.substring(32, 38).trim();
                                    String mac = new String(headerBytes, 62, 32);
                                    // MD5校验
                                    // MAC	32	以（Message Data部分 + TXN_DATE + USER_ID + USER_KEY）为依据产生的用ASC字符表示的16进制MD5值。其中USER_KEY由财务公司针对每个用户单独下发。
                                    String md5 = MD5Helper.getMD5String(msgData + txnDate + userid + userkey);
                                    // 验证失败 返回验证失败信息
                                    if (!md5.equalsIgnoreCase(mac)) {
                                        logger.info("用户标识：" + userid + " " + userkey + " 交易时间:" + txnDate + " " + txnTime);
                                        logger.info("MAC校验失败[服务端]MD5:" + md5 + "[客户端]MAC:" + mac);
                                        throw new RuntimeException(TxnRtnCode.MSG_VERIFY_MAC_ILLEGAL.toRtnMsg());
                                    } else {
                                        logger.info("MAC校验成功,用户标识：" + userid + " " + userkey + " 交易时间:" + txnDate + " " + txnTime);
                                    }

                                    // 未通过闸口时，抛出异常，但暂时保留CheckResult
                                    String checkerClass = PropertyManager.getProperty("check." + userid + "." + txnCode);
                                    CheckResult checkResult = new CheckResult(userid, txnCode);
                                    // 用户交易权限闸口
                                    new TxnUseridChecker().check(userid, txnCode, checkResult);
                                    // 业务数据闸口
                                    if (!StringUtils.isEmpty(checkerClass)) {
                                        logger.info(txnCode + "交易启动闸口：" + checkerClass);
                                        TxnChecker checker = (TxnChecker) Class.forName(checkerClass).newInstance();
                                        checker.check(userid, txnCode, msgData, checkResult);
                                    }
                                    String rtnXml = "";
                                    // 特殊交易特殊处理
                                    Class txnClass = null;
                                    try {
                                        txnClass = Class.forName("org.fbi.dep.txn.Txn" + txnCode + "Processor");
                                    } catch (ClassNotFoundException e) {
                                        txnClass = null;
                                    }
                                    if (txnClass != null) {
                                        AbstractTxnProcessor processor = (AbstractTxnProcessor) txnClass.newInstance();
                                        rtnXml = processor.process(userid, msgData);
                                    } else {
                                        // 直连SBS
                                        AbstractTiaBytesTransform bytesTransform = (AbstractTiaBytesTransform) Class.forName("org.fbi.dep.transform.TiaXml" + txnCode + "Transform").newInstance();
                                        byte[] sbsReqMsg = bytesTransform.run(msgData, userid);
                                        // SBS
                                        byte[] sbsResBytes = new JmsBytesClient().sendRecivMsg("900", "fcdep", "fcdep", txnCode, userid,
                                                "queue.dep.core.fcdep.sbs", "queue.dep.core.sbs.fcdep", sbsReqMsg);
                                        // 报文转换为dep-sbs-xml报文
                                        AbstractToaBytesTransform toaTransform = (AbstractToaBytesTransform) Class.forName("org.fbi.dep.transform.http.sbs.ToaXmlHttp" + txnCode + "Transform").newInstance();
                                        rtnXml = toaTransform.run(sbsResBytes);

                                    }
                                    String rtnmac = MD5Helper.getMD5String(rtnXml + txnDate + userid + userkey);
                                    rtnMsgHeader = rtnMsgHeader + TxnRtnCode.TXN_PROCESSED.getCode()
                                            + StringPad.rightPad4ChineseToByteLength(TxnRtnCode.TXN_PROCESSED.getTitle(), 20, " ")
                                            + rtnmac;
                                    exchange.getOut().setBody((rtnMsgHeader + rtnXml).getBytes());
                                } catch (Exception e) {
                                    //  返回异常信息
                                    if (txnCode == null) {
                                        logger.error("报文解析失败，无法解析到交易码.", e);
                                        return;
                                    } else {
                                        String exmsg = e.getMessage();
                                        logger.error("交易异常", e);
                                        AbstractTiaToToa tiaToToa = (AbstractTiaToToa) Class.forName("org.fbi.dep.transform.Tia" + txnCode + "ToToa").newInstance();
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
                                        String rtnmsg = tiaToToa.run(rtnMsgData, errmsg[0], errmsg[1]);
                                        String rtnmac = MD5Helper.getMD5String(rtnmsg + txnDate + userid + userkey);
                                        rtnMsgHeader = rtnMsgHeader + errmsg[0]
                                                + StringPad.rightPad4ChineseToByteLength(errmsg[1], 20, " ")
                                                + rtnmac;
                                        exchange.getOut().setBody((rtnMsgHeader + rtnmsg).getBytes());
                                    }
                                }
                            }
                    }
            );
    }
}
