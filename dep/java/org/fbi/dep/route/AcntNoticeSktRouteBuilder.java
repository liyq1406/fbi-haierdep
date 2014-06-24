package org.fbi.dep.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.fbi.dep.component.jms.JmsMbpClient;
import org.fbi.dep.util.PropertyManager;
import org.fbi.dep.util.StringPad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-2-13
 * Time: 下午2:25
 * To change this template use File | Settings | File Templates.
 */
public class AcntNoticeSktRouteBuilder extends RouteBuilder {

    private static Logger logger = LoggerFactory.getLogger(AcntNoticeSktRouteBuilder.class);
    private static String SERVER_IP = PropertyManager.getProperty("dep.localhost.ip");
    private static String MBP_SBS_PORT = PropertyManager.getProperty("dep.localhost.mbp.sbs.port");

    @Override
    public void configure() throws Exception {
        from("netty:tcp://" + SERVER_IP + ":" + MBP_SBS_PORT + "?sync=true&disconnect=true" +
                "&encoding=GBK&decoder=#mbp-decoder&encoder=#mbp-encoder")
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        byte[] nettyBytes = (byte[]) exchange.getIn().getBody();
                        String message = new String(nettyBytes);
                        logger.info("【PROCESSOR报文解码】：" + message);
                        logger.info("【PROCESSOR报文解码】报文正文长度：" + nettyBytes.length);

                        byte[] macBytes = new byte[32];
                        System.arraycopy(nettyBytes, 0, macBytes, 0, macBytes.length);
                        String macStr = new String(macBytes);
                        logger.info("【服务端报文解码】Mac：" + macStr);

                        byte[] sbsBytes = new byte[nettyBytes.length - 32];
                        System.arraycopy(nettyBytes, 32, sbsBytes, 0, sbsBytes.length);

                        byte[] termId = new byte[4]; // 终端号
                        logger.info("【sbs报文】" + new String(sbsBytes, "ISO8859-1"));
                        System.arraycopy(sbsBytes, 20, termId, 0, termId.length);

                        // 按终端分配权限，IP，可用的交易，非法IP不可接入
                        // TODO  不同客户端终端号的判断
//                        if (!"1111".equals(new String(termId))) {
                        if (!"MBP1".equals(new String(termId))) {
                            logger.info("非法终端号：" + new String(termId));
                            exchange.getOut().setBody((macStr + "9001" +
                                    StringPad.rightPad4ChineseToByteLength("非法终端号：" + new String(termId), 100, " ")).getBytes());
                        } else {
                            byte[] sbsRtnBytes = new JmsMbpClient().sendRecivMsg(sbsBytes);
                            // TODO 获取响应报文Form号
                            byte[] sbsRtnFormCodeBytes = new byte[4];
                            System.arraycopy(sbsRtnBytes, 21, sbsRtnFormCodeBytes, 0, sbsRtnFormCodeBytes.length);
                            if (!new String(sbsRtnFormCodeBytes).startsWith("T")) {
                                Thread.currentThread().sleep(20000);
                            }
                            byte[] sbsLenDataBytes = new byte[2];
                            System.arraycopy(sbsRtnBytes, 27, sbsLenDataBytes, 0, sbsLenDataBytes.length);
                            short pakLength = (short) ((sbsLenDataBytes[0] << 8 & 0xFF00) | (sbsLenDataBytes[1] & 0x00FF));
                            if (sbsRtnBytes != null) {
                                logger.info("【SBS响应包正文】长度" + pakLength);
                            } else {
                                logger.error("【SBS响应包】为空");
                            }

                            int sbsLength = pakLength + 29;
                            byte[] header = (macStr + "0000" + StringPad.rightPad4ChineseToByteLength("验证通过", 100, " ")).getBytes();
                            byte[] sbsDataBytes = new byte[sbsLength + header.length];

                            System.arraycopy(header, 0, sbsDataBytes, 0, header.length);
                            System.arraycopy(sbsRtnBytes, 0, sbsDataBytes, header.length, sbsLength);

                            exchange.getOut().setBody(sbsDataBytes);
                        }
                    }
                }
                );
    }
}
