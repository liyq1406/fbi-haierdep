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
 * 房产中心交易处理
 */
public class Core990Processor implements Processor {

    private static Logger logger = LoggerFactory.getLogger(Core990Processor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        logger.info("------房产中心 核心报文处理------");
        Object msgBody = exchange.getIn().getBody();
        logger.info("[RFM] 发送DEP报文内容： " + msgBody.toString());

        String sndMsg = new MsgHelper().buildMsg(msgBody);
        logger.info("[房产中心] 请求报文内容： " + sndMsg);

        RfmClient client = new RfmClient();
        String rtnMsg = client.processTxn(sndMsg);
        logger.info("[房产中心] 返回报文内容： " + rtnMsg);

        String txCode = (String) exchange.getIn().getHeader("JMSX_TXCODE");
        Object obj = new MsgHelper().buildObj(rtnMsg, txCode);
        logger.info("[DEP] 返回RFM报文内容： " + obj.toString());

        exchange.getOut().setHeader("JMSCorrelationID", exchange.getIn().getHeader("JMSCorrelationID"));
        exchange.getOut().setHeader("JMSX_APPID", exchange.getIn().getHeader("JMSX_APPID"));
        exchange.getOut().setHeader("JMSX_CHANNELID", exchange.getIn().getHeader("JMSX_CHANNELID"));
        exchange.getOut().setHeader("JMSX_SRCMSGFLAG", exchange.getIn().getHeader("JMSX_SRCMSGFLAG"));
        exchange.getOut().setBody(obj);
    }
}
