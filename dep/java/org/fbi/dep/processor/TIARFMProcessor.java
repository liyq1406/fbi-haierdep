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
public class TIARFMProcessor implements Processor {

    private static Logger logger = LoggerFactory.getLogger(TIARFMProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        Object msgBody = exchange.getIn().getBody();
        String datagram = "SHOUDAO";

        exchange.getOut().setBody(datagram);
    }
}
