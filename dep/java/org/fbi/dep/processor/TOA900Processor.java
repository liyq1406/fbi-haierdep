package org.fbi.dep.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.fbi.dep.model.base.TOA;
import org.fbi.dep.transform.TOA9008119Transform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-2-13
 * Time: ÏÂÎç9:51
 * To change this template use File | Settings | File Templates.
 */
public class TOA900Processor implements Processor {

    private static Logger logger = LoggerFactory.getLogger(TOA900Processor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        Message inMessage = exchange.getIn();
        exchange.getOut().setHeader("JMSCorrelationID", inMessage.getHeader("JMSCorrelationID"));
        logger.info("TOA Processor JMSCorrelationID : " + inMessage.getHeader("JMSCorrelationID"));

        exchange.getOut().setHeader("JMSX_APPID", inMessage.getHeader("JMSX_APPID"));
        exchange.getOut().setHeader("JMSX_SRCMSGFLAG", inMessage.getHeader("JMSX_SRCMSGFLAG"));

        byte[] datagram = (byte[]) inMessage.getBody();

        String txnCode = (String) inMessage.getHeader("JMSX_TXCODE");
        TOA toa = null;
        toa = new TOA9008119Transform().transform(datagram, txnCode);
        // TODO
        /*switch (Integer.parseInt(txnCode)) {
            case 9008119:
                toa = new TOA9008119Transform().transform(datagram, txnCode);
                break;
            default:
                break;
        }*/
        exchange.getOut().setBody(toa);
    }
}
