package org.fbi.dep.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.commons.lang.StringUtils;
import org.fbi.dep.enums.EnuTaTxCode;
import org.fbi.dep.model.base.TIA;
import org.fbi.dep.transform.Tia9901001Transform;
import org.fbi.dep.transform.Tia9901002Transform;
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
        String correlationID = inMessage.getHeader("JMSCorrelationID", String.class);
        if (StringUtils.isEmpty(correlationID)) {
            exchange.getOut().setHeader("JMSCorrelationID", inMessage.getMessageId());
            logger.info("AppRouteBuilder JMSCorrelationID : " + inMessage.getMessageId());
        } else {
            exchange.getOut().setHeader("JMSCorrelationID", correlationID);
        }
        exchange.getOut().setHeader("JMSX_APPID", inMessage.getHeader("JMSX_APPID"));
        exchange.getOut().setHeader("JMSX_CHANNELID", inMessage.getHeader("JMSX_CHANNELID"));
        exchange.getOut().setHeader("JMSX_SRCMSGFLAG", inMessage.getHeader("JMSX_SRCMSGFLAG"));

        String  msg = inMessage.getBody(String.class);
        logger.info("[RFM] 请求报文内容： " + msg);
        String strTemp="";
        TIA tiaTemp=(TIA) inMessage.getBody();
        if(EnuTaTxCode.TRADE_1001.getCode().equals(tiaTemp.getHeader().TX_CODE)) {
            Tia9901001Transform tia9901001TransformTemp = new Tia9901001Transform();
            strTemp = tia9901001TransformTemp.transform(tiaTemp);
        }else if(EnuTaTxCode.TRADE_1002.getCode().equals(tiaTemp.getHeader().TX_CODE)){
            Tia9901002Transform tia9901002TransformTemp = new Tia9901002Transform();
            strTemp = tia9901002TransformTemp.transform(tiaTemp);
        }
        exchange.getOut().setBody(strTemp);
    }
}
