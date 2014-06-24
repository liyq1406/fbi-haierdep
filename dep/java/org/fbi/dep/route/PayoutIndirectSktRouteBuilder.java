package org.fbi.dep.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.fbi.dep.component.jms.JmsObjMsgClient;
import org.fbi.dep.enums.TxnRtnCode;
import org.fbi.dep.helper.MD5Helper;
import org.fbi.dep.model.base.TiaXml;
import org.fbi.dep.model.base.ToaXml;
import org.fbi.dep.transform.AbstractTiaToToa;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-2-13
 * Time: 下午2:25
 * To change this template use File | Settings | File Templates.
 */
public class PayoutIndirectSktRouteBuilder extends RouteBuilder {

    private static Logger logger = LoggerFactory.getLogger(PayoutIndirectSktRouteBuilder.class);
    private static String SERVER_IP = PropertyManager.getProperty("dep.localhost.ip");
    private String server_port;

    public PayoutIndirectSktRouteBuilder(String port) {
        this.server_port = port;
    }

    @Override
    public void configure() throws Exception {
        from("netty:tcp://" + SERVER_IP + ":" + server_port + "?sync=true&disconnect=true" +
                "&encoding=GBK&decoder=#skt-decoder&encoder=#skt-encoder")
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        byte[] bytes = (byte[]) exchange.getIn().getBody();
                        logger.info("接收到报文：" + new String(bytes));
                        String rtnMsgHeader = null;
                        String rtnMsgData = null;
                        String txnCode = null;
                        String txnDate = "";
                        String userid = "";
                        try {
                            // 解析报文头
                            byte[] headerBytes = new byte[64];
                            System.arraycopy(bytes, 0, headerBytes, 0, headerBytes.length);
                            String msgHeader = new String(headerBytes, "GBK");
                            rtnMsgHeader = msgHeader.substring(0, 32);
                            // 解析报文体
                            byte[] bodyBytes = new byte[bytes.length - 64];
                            System.arraycopy(bytes, 64, bodyBytes, 0, bodyBytes.length);
                            String msgData = new String(bodyBytes, "GBK");
                            rtnMsgData = msgData;
                            // 外围系统代码、交易码、交易日期、mac
                            String wsysid = msgHeader.substring(4, 14).trim().toUpperCase();
                            userid = PropertyManager.getProperty("wsys.userkey." + wsysid);
                            txnCode = msgHeader.substring(14, 24).trim();
                            txnDate = msgHeader.substring(24, 32).trim();
                            String mac = msgHeader.substring(32);
                            // MD5校验
                            // Message Data部分加上8位交易日期加上用户ID后产生的MD5值
                            String md5 = MD5Helper.getMD5String(msgData + txnDate + userid);
                            // 验证失败 返回验证失败信息
                            if (!md5.equalsIgnoreCase(mac)) {
                                logger.info("【服务端】MD5:" + md5);
                                throw new RuntimeException(TxnRtnCode.MSG_VERIFY_MAC_ILLEGAL.toRtnMsg());
                            }
                            TiaXml tiaXml = (TiaXml) Class.forName("org.fbi.dep.model.txn.TiaXml" + txnCode).newInstance();
                            tiaXml = tiaXml.transform(msgData);
                            // 发送到fip
                           ToaXml resXml = (ToaXml)new JmsObjMsgClient().sendRecivMsg("910", txnCode, "fcdep",
                                    "queue.dep.in.fcdep.object", "queue.dep.out.fcdep.object", tiaXml);
                           String rtnxml = resXml.toString();
                            String rtnMd5 = MD5Helper.getMD5String(rtnxml + txnDate + userid);
                            exchange.getOut().setBody((rtnMsgHeader + rtnMd5 + rtnxml).getBytes("GBK"));
                        } catch (Exception e) {
                            //  返回异常信息
                            if (txnCode == null) {
                                logger.error("报文解析失败，无法解析到交易码.", e);
                                return;
                            } else {
                                String exmsg = e.getMessage();
                                AbstractTiaToToa tiaToToa = (AbstractTiaToToa) Class.forName("org.fbi.dep.transform.TiaXml" + txnCode + "ToToa").newInstance();
                                if (exmsg == null) {
                                    String rtnmsg = tiaToToa.run(rtnMsgData, TxnRtnCode.SERVER_EXCEPTION.getCode(),
                                            TxnRtnCode.SERVER_EXCEPTION.getTitle());
                                    String md5 = MD5Helper.getMD5String(rtnmsg + txnDate + userid);
                                    exchange.getOut().setBody((rtnMsgHeader + md5 + rtnmsg).getBytes("GBK"));
                                } else {
                                    String errmsg[] = exmsg.split("\\|");
                                    logger.error(exmsg, e);
                                    String rtnmsg = tiaToToa.run(rtnMsgData, errmsg[0], errmsg[1]);
                                    String md5 = MD5Helper.getMD5String(rtnmsg + txnDate + userid);
                                    exchange.getOut().setBody((rtnMsgHeader + md5 + rtnmsg).getBytes("GBK"));
//                                    exchange.getOut().setBody((rtnMsgHeader + tiaToToa.run(rtnMsgData, errmsg[0], errmsg[1])).getBytes());
                                }
                            }
                        }
                    }
                }
                );
    }
}
