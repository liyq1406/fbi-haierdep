package org.fbi.dep.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.fbi.dep.helper.DepHttpClient;
import org.fbi.dep.helper.UnionpayCryptHelper;
import org.fbi.dep.util.PropertyManager;
import org.fbi.endpoint.ccbvips.VipsSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-2-13
 * Time: 下午9:53
 * To change this template use File | Settings | File Templates.
 */
public class Core200Processor implements Processor {

    private static Logger logger = LoggerFactory.getLogger(Core200Processor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        Message inMessage = exchange.getIn();

        String appID = (String) inMessage.getHeader("JMSX_APPID");
        String bizID = (String) inMessage.getHeader("JMSX_BIZID");

        String datagram = inMessage.getBody(String.class);
        logger.info("发往建行重客报文：" + datagram);
        logger.info("连接 WinBridge 执行交易开始----------：");

        // 连接建行重客系统，获取响应报文
        String rtnDatagram = new VipsSocketClient().processTxn(datagram);
        logger.info("建行重客返回报文：" + rtnDatagram);

        exchange.getOut().setHeader("JMSCorrelationID", inMessage.getHeader("JMSCorrelationID"));
        exchange.getOut().setHeader("JMSX_APPID", appID);
        exchange.getOut().setHeader("JMSX_TXCODE", "ccbvips");
        exchange.getOut().setHeader("JMSX_CHANNELID", inMessage.getHeader("JMSX_CHANNELID"));
        exchange.getOut().setHeader("JMSX_SRCMSGFLAG", inMessage.getHeader("JMSX_SRCMSGFLAG"));
        exchange.getOut().setBody(rtnDatagram);

    }
}
