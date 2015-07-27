package org.fbi.dep.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.fbi.dep.model.base.TIA;
import org.fbi.dep.transform.Tia9901001Transform;
import org.fbi.endpoint.tarfm.util.MsgHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 泰安房产资金监管
 * User: hanjianlong
 * Date: 2015-07-16
 */
public class Core990Processor implements Processor {

    private static Logger logger = LoggerFactory.getLogger(Core990Processor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        
        logger.info("------房产中心 核心报文处理------");
        Message inMessage = exchange.getIn();
        String  msg = inMessage.getBody(String.class);
        logger.info("[RFM] 请求报文内容： " + msg);

        Tia9901001Transform tia9901001TransformTemp=new Tia9901001Transform();
        String strTemp=tia9901001TransformTemp.transform((TIA)inMessage.getBody());

        exchange.getOut().setHeader("JMSCorrelationID", inMessage.getHeader("JMSCorrelationID"));
        exchange.getOut().setHeader("JMSX_APPID", inMessage.getHeader("JMSX_APPID"));
        exchange.getOut().setHeader("JMSX_CHANNELID", inMessage.getHeader("JMSX_CHANNELID"));
        exchange.getOut().setHeader("JMSX_SRCMSGFLAG", inMessage.getHeader("JMSX_SRCMSGFLAG"));
        exchange.getOut().setBody(strTemp);
    }
}
