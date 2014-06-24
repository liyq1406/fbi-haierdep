package org.fbi.dep.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.fbi.dep.model.base.TOA;
import org.fbi.dep.transform.TOA1001001Transform;
import org.fbi.dep.transform.TOA1001002Transform;
import org.fbi.dep.transform.TOA1002001Transform;
import org.fbi.dep.transform.TOA1003001Transform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-2-13
 * Time: ����9:51
 * To change this template use File | Settings | File Templates.
 */
public class TOA100Processor implements Processor {

    private static Logger logger = LoggerFactory.getLogger(TOA100Processor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        Message inMessage = exchange.getIn();
        exchange.getOut().setHeader("JMSCorrelationID", inMessage.getHeader("JMSCorrelationID"));

        logger.info("TOA Processor JMSCorrelationID : " + inMessage.getHeader("JMSCorrelationID"));
        exchange.getOut().setHeader("JMSX_APPID", inMessage.getHeader("JMSX_APPID"));
        exchange.getOut().setHeader("JMSX_SRCMSGFLAG", inMessage.getHeader("JMSX_SRCMSGFLAG"));
        String datagram = (String) inMessage.getBody();

        String txnCode = (String) inMessage.getHeader("JMSX_TXCODE");
        TOA toa = null;
        switch (Integer.parseInt(txnCode)) {
            case 100001:
            case 100004:
                toa = new TOA1001001Transform().transform(datagram, txnCode);
                break;
            case 100005:
                toa = new TOA1001002Transform().transform(datagram, txnCode);
                break;
            case 100002:
                toa = new TOA1002001Transform().transform(datagram, txnCode);
                break;
            case 200001:
                toa = new TOA1003001Transform().transform(datagram, txnCode);
                break;
            default:
                break;
        }
        exchange.getOut().setBody(toa);
    }
}