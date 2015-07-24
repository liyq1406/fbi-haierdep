package org.fbi.dep.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
/*import org.fbi.endpoint.tarfm.RfmClient;*/
import org.fbi.dep.model.base.TIA;
import org.fbi.dep.transform.Tia9901001Transform;
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

       /* RfmClient client = new RfmClient();
        String rtnMsg = client.processTxn(sndMsg);*/
        logger.info("[��������] ���ر������ݣ� " + msg);
        Tia9901001Transform tia9901001TransformTemp=new Tia9901001Transform();
        String strTemp=tia9901001TransformTemp.transform((TIA)inMessage.getBody());

        exchange.getOut().setBody(strTemp);
    }
}
