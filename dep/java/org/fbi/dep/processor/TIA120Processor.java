package org.fbi.dep.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.txn.*;
import org.fbi.dep.transform.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Lichao.W At 2015/6/24 22:21
 * wanglichao@163.com
 */
public class TIA120Processor implements Processor {

    private static Logger logger = LoggerFactory.getLogger(TIA120Processor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        Object msgBody = exchange.getIn().getBody();
        String datagram = null;
        // TODO ADD
        if (msgBody instanceof TIA1201001) {
            TIA1201001 tia1201001 = (TIA1201001) msgBody;
            exchange.getOut().setHeader("REQ_TXN_CODE", "1201001");
            datagram = new TIA1201001Transform().transform(tia1201001);
        } else if (msgBody instanceof TIA1201011) {
            TIA1201011 tia1201011 = (TIA1201011) msgBody;
            exchange.getOut().setHeader("REQ_TXN_CODE", "1201011");
            datagram = new TIA1201011Transform().transform(tia1201011);
        } else if(msgBody instanceof TIA1202004) {
            TIA1202004 tia1202004 = (TIA1202004) msgBody;
            exchange.getOut().setHeader("REQ_TXN_CODE", "1202004");
            datagram = new TIA1202004Transform().transform(tia1202004);
        } else if(msgBody instanceof TIA1202005) {
            TIA1202005 tia1202005 = (TIA1202005) msgBody;
            exchange.getOut().setHeader("REQ_TXN_CODE", "1202005");
            datagram = new TIA1202005Transform().transform(tia1202005);
        } /*else if(msgBody instanceof TIA1002001) {
            TIA1002001 tia1002001 = (TIA1002001) msgBody;
            datagram = new TIA1002001Transform().transform(tia1002001);
        } else if(msgBody instanceof TIA1003003) {
            TIA1003003 tia1003003 = (TIA1003003) msgBody;
            exchange.getOut().setHeader("REQ_TXN_CODE", "1003003");
            datagram = new TIA1003003Transform().transform(tia1003003);
        }*/
        exchange.getOut().setBody(datagram);

        String correlationID = exchange.getIn().getHeader("JMSCorrelationID", String.class);
        if (StringUtils.isEmpty(correlationID)) {
            exchange.getOut().setHeader("JMSCorrelationID", exchange.getIn().getMessageId());
            logger.info("TIA JMSCorrelationID : " + exchange.getIn().getMessageId());
        } else {
            exchange.getOut().setHeader("JMSCorrelationID", correlationID);
        }

        exchange.getOut().setHeader("JMSX_APPID", exchange.getIn().getHeader("JMSX_APPID"));
        exchange.getOut().setHeader("JMSX_BIZID", exchange.getIn().getHeader("JMSX_BIZID"));
        exchange.getOut().setHeader("JMSX_CHANNELID", exchange.getIn().getHeader("JMSX_CHANNELID"));
        exchange.getOut().setHeader("JMSX_SRCMSGFLAG", exchange.getIn().getHeader("JMSX_SRCMSGFLAG"));
    }
}
