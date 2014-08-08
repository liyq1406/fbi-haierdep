package org.fbi.dep.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.txn.*;
import org.fbi.dep.model.txn.TIA1002001;
import org.fbi.dep.transform.*;
import org.fbi.dep.transform.TIA1002001Transform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-2-13
 * Time: ÏÂÎç9:27
 * To change this template use File | Settings | File Templates.
 */
public class TIA100Processor implements Processor {

    private static Logger logger = LoggerFactory.getLogger(TIA100Processor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        Object msgBody = exchange.getIn().getBody();
        String datagram = null;
        // TODO ADD
        if (msgBody instanceof TIA1003001) {
            TIA1003001 tia1003001 = (TIA1003001) msgBody;
            exchange.getOut().setHeader("REQ_TXN_CODE", "1003001");
            datagram = new TIA1003001Transform().transform(tia1003001);
        } else if (msgBody instanceof TIA1001001) {
            TIA1001001 tia1001001 = (TIA1001001) msgBody;
            datagram = new TIA1001001Transform().transform(tia1001001);
        } else if(msgBody instanceof TIA1001003) {
            TIA1001003 tia1001003 = (TIA1001003) msgBody;
            exchange.getOut().setHeader("REQ_TXN_CODE", "1001003");
            datagram = new TIA1001003Transform().transform(tia1001003);
        } else if(msgBody instanceof TIA1001002) {
            TIA1001002 tia1001002 = (TIA1001002) msgBody;
            datagram = new TIA1002001Transform().transform(tia1001002);
        } else if(msgBody instanceof TIA1002001) {
            TIA1002001 tia1002001 = (TIA1002001) msgBody;
            datagram = new TIA1002001Transform().transform(tia1002001);
        } else if(msgBody instanceof TIA1003003) {
            TIA1003003 tia1003003 = (TIA1003003) msgBody;
            exchange.getOut().setHeader("REQ_TXN_CODE", "1003003");
            datagram = new TIA1003003Transform().transform(tia1003003);
        }
        exchange.getOut().setBody(datagram);

        String correlationID = exchange.getIn().getHeader("JMSCorrelationID", String.class);
        if (StringUtils.isEmpty(correlationID)) {
            exchange.getOut().setHeader("JMSCorrelationID", exchange.getIn().getMessageId());
            logger.info("TIA JMSCorrelationID : " + exchange.getIn().getMessageId());
        } else {
            exchange.getOut().setHeader("JMSCorrelationID", correlationID);
            logger.info("TIA JMSCorrelationID : " + correlationID);
        }

        exchange.getOut().setHeader("JMSX_APPID", exchange.getIn().getHeader("JMSX_APPID"));
        exchange.getOut().setHeader("JMSX_BIZID", exchange.getIn().getHeader("JMSX_BIZID"));
        exchange.getOut().setHeader("JMSX_CHANNELID", exchange.getIn().getHeader("JMSX_CHANNELID"));
        exchange.getOut().setHeader("JMSX_SRCMSGFLAG", exchange.getIn().getHeader("JMSX_SRCMSGFLAG"));
    }
}
