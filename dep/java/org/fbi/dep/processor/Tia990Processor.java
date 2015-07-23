package org.fbi.dep.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.txn.*;
import org.fbi.dep.transform.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-2-13
 * Time: ÏÂÎç9:27
 * To change this template use File | Settings | File Templates.
 */
public class TIA990Processor implements Processor {

    private static Logger logger = LoggerFactory.getLogger(TIA990Processor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        Object msgBody = exchange.getIn().getBody();
        String datagram = null;
        // TODO ADD
        if (msgBody instanceof TIA9901001) {
            TIA9901001 tia9901001 = (TIA9901001) msgBody;
            datagram = new Tia9901001ToStr().transform(tia9901001);
        } else if (msgBody instanceof TIA1001001) {

        }
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
