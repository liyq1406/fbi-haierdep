package org.fbi.dep.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.fbi.dep.component.RfmSktClient;
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
public class TOA990Processor implements Processor {

    private static Logger logger = LoggerFactory.getLogger(TOA990Processor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        Message inMessage = exchange.getIn();
        String strMsg= (String) inMessage.getBody();
        String rtnmsg = new RfmSktClient().processTxn(strMsg);
        System.out.println(rtnmsg);
    }
}
