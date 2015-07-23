package org.fbi.dep.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.fbi.dep.model.base.TOA;
import org.fbi.dep.transform.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-2-13
 * Time: ÏÂÎç9:51
 * To change this template use File | Settings | File Templates.
 */
public class TOA800Processor implements Processor {

    private static Logger logger = LoggerFactory.getLogger(TOA800Processor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        Message inMessage = exchange.getIn();
        exchange.getOut().setHeader("JMSCorrelationID", inMessage.getHeader("JMSCorrelationID"));

        exchange.getOut().setHeader("JMSX_APPID", inMessage.getHeader("JMSX_APPID"));
        exchange.getOut().setHeader("JMSX_SRCMSGFLAG", inMessage.getHeader("JMSX_SRCMSGFLAG"));
        String datagram = (String) inMessage.getBody();

        String txnCode = (String) inMessage.getHeader("JMSX_TXCODE");
        TOA toa = null;
        String reqTxnCode = null;
        switch (Integer.parseInt(txnCode)) {
            case 100001:
            case 100004:
                reqTxnCode = inMessage.getHeader("REQ_TXN_CODE", String.class);
                if ("1001003".equals(reqTxnCode)) {
                    toa = new TOA1001003Transform().transform(datagram, txnCode);
                } else
                    toa = new TOA1001001Transform().transform(datagram, txnCode);
                break;
            case 100005:
                toa = new TOA1001002Transform().transform(datagram, txnCode);
                break;
            case 100002:
                reqTxnCode = inMessage.getHeader("REQ_TXN_CODE", String.class);
                if ("1001002".equals(reqTxnCode)) {
                    toa = new TOA1001002Transform().transform(datagram, txnCode);
                } else
                    toa = new TOA1002001Transform().transform(datagram, txnCode);
                break;
            case 200001:
                reqTxnCode = inMessage.getHeader("REQ_TXN_CODE", String.class);
                if ("1003003".equals(reqTxnCode)) {
                    toa = new TOA1003003Transform().transform(datagram, txnCode);
                } else
                    toa = new TOA1003001Transform().transform(datagram, txnCode);
                break;
            default:
                break;
        }
        exchange.getOut().setBody(toa);
    }
}
