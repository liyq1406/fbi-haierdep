package org.fbi.dep.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.fbi.endpoint.tarfm.RfmClient;
import org.fbi.endpoint.tarfm.util.MsgHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 房产中心交易处理
 */
public class Core800Processor implements Processor {

    private static Logger logger = LoggerFactory.getLogger(Core800Processor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        
        logger.info("------房产中心 核心报文处理------");
        Message inMessage = exchange.getIn();
        String  msg = inMessage.getBody(String.class);
        logger.info("[RFM] 请求报文内容： " + msg);

        String sndMsg = new MsgHelper().buildMsg(msg);
        logger.info("[房产中心] 请求报文内容： " + msg);

        RfmClient client = new RfmClient();
        String rtnMsg = client.processTxn(sndMsg);
        logger.info("[房产中心] 返回报文内容： " + msg);

        exchange.getOut().setHeader("JMSCorrelationID", inMessage.getHeader("JMSCorrelationID"));
        exchange.getOut().setHeader("JMSX_APPID", inMessage.getHeader("JMSX_APPID"));
        exchange.getOut().setHeader("JMSX_CHANNELID", inMessage.getHeader("JMSX_CHANNELID"));
        exchange.getOut().setHeader("JMSX_SRCMSGFLAG", inMessage.getHeader("JMSX_SRCMSGFLAG"));
        exchange.getOut().setBody(rtnMsg);
    }
}
