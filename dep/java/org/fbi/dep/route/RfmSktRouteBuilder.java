package org.fbi.dep.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.lang.StringUtils;
import org.fbi.dep.util.PropertyManager;
import org.fbi.endpoint.sms.SmsTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 泰安房产资金监管
 * User: hanjianlong
 * Date: 2015-07-16
 */
public class RfmSktRouteBuilder extends RouteBuilder {

    private static Logger logger = LoggerFactory.getLogger(RfmSktRouteBuilder.class);
    private static String SERVER_IP = PropertyManager.getProperty("dep.localhost.ip");
    private String server_port;

    public RfmSktRouteBuilder(String port) {
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
                                 String bizCode = "0000";
                                 try {
                                     // 解析报文头
                                     byte[] headerBytes = new byte[4];
                                     System.arraycopy(bytes, 0, headerBytes, 0, headerBytes.length);
                                     bizCode = new String(headerBytes, "GBK");

                                     // 报文体
                                     byte[] bodyBytes = new byte[bytes.length - 4];
                                     System.arraycopy(bytes, 4, bodyBytes, 0, bodyBytes.length);
                                     String msgData = new String(bodyBytes, "GBK");

                                     // TODO 读取文件，根据业务号读取手机号码，将msgData发送到手机上
                                     // 0-短信 1-邮件 2-微信
                                     if (bizCode.startsWith("0")) {
                                         // 报文中有手机号
                                         if (bizCode.equals("0011")) {

                                             int index = msgData.indexOf("|");
                                             String phones = msgData.substring(0, index);
                                             String msgContent = msgData.substring(index + 1);
                                             if (StringUtils.isEmpty(phones)) {
                                                 exchange.getOut().setBody("0000".getBytes());
                                             } else {
                                                 String[] phoneNums = phones.split(",");
                                                 for (String num : phoneNums) {
                                                     if (!StringUtils.isEmpty(num) && num.length() == 11) {
                                                         SmsTool.sendMessage(num, msgContent);
                                                     } else {
                                                         logger.info("错误的手机号：" + num);
                                                     }
                                                 }
                                             }
                                         }
                                     }
                                     exchange.getOut().setBody(bizCode.getBytes());
                                 } catch (Exception e) {
                                     logger.error("信息发送异常", e);
                                     exchange.getOut().setBody("0000".getBytes());
                                 }
                             }
                         }
                );
    }
}
