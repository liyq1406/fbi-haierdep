package org.fbi.dep.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
/*import org.fbi.endpoint.tarfm.RfmClient;*/
import org.fbi.dep.model.txn.TIA9901001;
import org.fbi.dep.model.txn.TIA9901002;
import org.fbi.endpoint.tarfm.RfmClient;
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
        Object msgBody = exchange.getIn().getBody();
        logger.info("[RFM] ����DEP�������ݣ� " + msgBody.toString());

        String sndMsg = new MsgHelper().buildMsg(msgBody);
        logger.info("[��������] ���������ݣ� " + sndMsg);

        RfmClient client = new RfmClient();
        String rtnMsg = client.processTxn(sndMsg);
        logger.info("[��������] ���ر������ݣ� " + rtnMsg);

        String txCode = (String) exchange.getIn().getHeader("JMSX_TXCODE");
        Object obj = new MsgHelper().buildObj(rtnMsg, txCode);
        logger.info("[DEP] ����RFM�������ݣ� " + obj.toString());

        exchange.getOut().setHeader("JMSCorrelationID", exchange.getIn().getHeader("JMSCorrelationID"));
        exchange.getOut().setHeader("JMSX_APPID", exchange.getIn().getHeader("JMSX_APPID"));
        exchange.getOut().setHeader("JMSX_CHANNELID", exchange.getIn().getHeader("JMSX_CHANNELID"));
        exchange.getOut().setHeader("JMSX_SRCMSGFLAG", exchange.getIn().getHeader("JMSX_SRCMSGFLAG"));
        exchange.getOut().setBody(obj);
    }
}
