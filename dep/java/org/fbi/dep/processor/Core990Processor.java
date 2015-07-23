package org.fbi.dep.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
/*import org.fbi.endpoint.tarfm.RfmClient;*/
import org.fbi.endpoint.tarfm.util.MsgHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * �������Ľ��״���
 */
public class Core990Processor implements Processor {

    private static Logger logger = LoggerFactory.getLogger(Core990Processor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        
        logger.info("------�������� ���ı��Ĵ���------");
        Message inMessage = exchange.getIn();
        String  msg = inMessage.getBody(String.class);
        logger.info("[RFM] ���������ݣ� " + msg);

        String sndMsg = new MsgHelper().buildMsg(msg);
        logger.info("[��������] ���������ݣ� " + msg);

        /*RfmClient client = new RfmClient();
        String rtnMsg = client.processTxn(sndMsg);*/
        logger.info("[��������] ���ر������ݣ� " + msg);

        exchange.getOut().setHeader("JMSCorrelationID", inMessage.getHeader("JMSCorrelationID"));
        exchange.getOut().setHeader("JMSX_APPID", inMessage.getHeader("JMSX_APPID"));
        exchange.getOut().setHeader("JMSX_CHANNELID", inMessage.getHeader("JMSX_CHANNELID"));
        exchange.getOut().setHeader("JMSX_SRCMSGFLAG", inMessage.getHeader("JMSX_SRCMSGFLAG"));
        //exchange.getOut().setBody(rtnMsg);
    }
}
