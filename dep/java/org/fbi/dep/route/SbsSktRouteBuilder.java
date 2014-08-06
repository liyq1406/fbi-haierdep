package org.fbi.dep.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.lang.StringUtils;
import org.fbi.dep.component.jms.JmsBytesClient;
import org.fbi.dep.enums.TxnRtnCode;
import org.fbi.dep.helper.MD5Helper;
import org.fbi.dep.management.Txn900Checker;
import org.fbi.dep.management.TxnChecker;
import org.fbi.dep.management.TxnUseridChecker;
import org.fbi.dep.model.CheckResult;
import org.fbi.dep.transform.*;
import org.fbi.dep.util.PropertyManager;
import org.fbi.dep.util.StringPad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 外联SBS交易
 */
public class SbsSktRouteBuilder extends RouteBuilder {

    private static Logger logger = LoggerFactory.getLogger(SbsSktRouteBuilder.class);
    private static String SERVER_IP = PropertyManager.getProperty("dep.localhost.ip");
    private String server_port;

    public SbsSktRouteBuilder(String port) {
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
                                     logger.info("用户标识：" + userid + " " + userkey + " 交易时间:" + txnDate + " " + txnTime);
                                     String mac = new String(headerBytes, 62, 32);
                                     // MD5校验
                                     // MAC	32	以（Message Data部分 + TXN_DATE + USER_ID + USER_KEY）为依据产生的用ASC字符表示的16进制MD5值。其中USER_KEY由财务公司针对每个用户单独下发。
                                     String md5 = MD5Helper.getMD5String(msgData + txnDate + userid + userkey);
                                     // 验证失败 返回验证失败信息
                                     if (!md5.equalsIgnoreCase(mac)) {
                                         logger.info("MAC校验失败[服务端]MD5:" + md5 + "[客户端]MAC:" + mac);
                                         throw new RuntimeException(TxnRtnCode.MSG_VERIFY_MAC_ILLEGAL.toRtnMsg());
                                     } else {
                                         logger.info("MAC校验成功");
                                     }

                                     // TODO 闸口
                                     String checkerClass = PropertyManager.getProperty("check." + userid + "." + txnCode);
                                     CheckResult checkResult = new CheckResult(userid, txnCode);
                                     if (!StringUtils.isEmpty(checkerClass)) {
                                         logger.info(txnCode + "交易启动闸口：" + checkerClass);
                                         Txn900Checker checker = (Txn900Checker) Class.forName(checkerClass).newInstance();
                                         checker.check(userid, txnCode, msgData, checkResult);
                                         logger.info(txnCode + "交易闸口检查结果：" + ("0000".equals(checkResult.getResultCode()) ? "通过" : checkResult.getResultMsg()));
                                         if (!"0000".equals(checkResult.getResultCode())) {
                                             throw new RuntimeException(checkResult.getResultCode() + "|" + checkResult.getResultMsg());
                                         }
                                     } else {
                                         new TxnUseridChecker().checkUserid(userid, txnCode, checkResult);
                                         if (!"0000".equals(checkResult.getResultCode())) {
                                             throw new RuntimeException(checkResult.getResultCode() + "|" + checkResult.getResultMsg());
                                         } else {
                                             logger.info("闸口校验通过.");
                                         }
                                     }
                                     AbstractTiaBytesTransform bytesTransform = (AbstractTiaBytesTransform) Class.forName("org.fbi.dep.transform.TiaXml" + txnCode + "Transform").newInstance();
                                     byte[] sbsReqMsg = bytesTransform.run(msgData, userid);
                                     // SBS
                                     byte[] sbsResBytes = new JmsBytesClient().sendRecivMsg("900", "fcdep", "fcdep", txnCode, userid,
                                             "queue.dep.core.fcdep.sbs", "queue.dep.core.sbs.fcdep", sbsReqMsg);
                                     // 报文转换为dep-sbs-xml报文
                                     AbstractToaBytesTransform toaTransform = (AbstractToaBytesTransform) Class.forName("org.fbi.dep.transform.ToaXml" + txnCode + "Transform").newInstance();
                                     String rtnXml = toaTransform.run(sbsResBytes);
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
