package org.fbi.dep.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.fbi.endpoint.sbs.CtgManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SBS交易处理
 */
public class Core900Processor implements Processor {

    private static Logger logger = LoggerFactory.getLogger(Core900Processor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        
        logger.info("------SBS 核心报文处理------");
        Message inMessage = exchange.getIn();
        byte[] bytesDatagram = (byte[]) inMessage.getBody();
        logger.info("客户端的报文内容【byte[]】： " + new String(bytesDatagram));

        CtgManager ctgManager = new CtgManager();
        byte[] rtnBytesDatagram = ctgManager.processTxn(bytesDatagram);
        logger.info("接收sbs的报文内容【byte[]】： " + new String(rtnBytesDatagram));

        exchange.getOut().setHeader("JMSCorrelationID", inMessage.getHeader("JMSCorrelationID"));
        exchange.getOut().setHeader("JMSX_APPID", inMessage.getHeader("JMSX_APPID"));
        exchange.getOut().setHeader("JMSX_CHANNELID", inMessage.getHeader("JMSX_CHANNELID"));
        exchange.getOut().setHeader("JMSX_SRCMSGFLAG", inMessage.getHeader("JMSX_SRCMSGFLAG"));
        exchange.getOut().setBody(rtnBytesDatagram);
    }
}
