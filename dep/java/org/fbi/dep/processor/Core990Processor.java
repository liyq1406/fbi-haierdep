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
 * 房产中心交易处理
 */
public class Core990Processor implements Processor {

    private static Logger logger = LoggerFactory.getLogger(Core990Processor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        
        logger.info("------房产中心 核心报文处理------");
        Message inMessage = exchange.getIn();
        String  msg = inMessage.getBody(String.class);
        logger.info("[RFM] 请求报文内容： " + msg);

        String sndMsg = new MsgHelper().buildMsg(msg);
        logger.info("[房产中心] 请求报文内容： " + msg);

       /* RfmClient client = new RfmClient();
        String rtnMsg = client.processTxn(sndMsg);*/
        logger.info("[房产中心] 返回报文内容： " + msg);
        Tia9901001Transform tia9901001TransformTemp=new Tia9901001Transform();
        String strTemp=tia9901001TransformTemp.transform((TIA)inMessage.getBody());

        exchange.getOut().setBody(strTemp);
    }
}
