package org.fbi.dep.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.fbi.dep.component.RfmSktClient;
import org.fbi.dep.enums.EnuTaTxCode;
import org.fbi.dep.model.txn.Toa9901001;
import org.fbi.dep.model.txn.Toa9901002;
import org.fbi.dep.transform.Toa9901001Transform;
import org.fbi.dep.transform.Toa9901002Transform;
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
        exchange.getOut().setHeader("JMSCorrelationID", inMessage.getHeader("JMSCorrelationID"));
        exchange.getOut().setHeader("JMSX_APPID", inMessage.getHeader("JMSX_APPID"));
        exchange.getOut().setHeader("JMSX_CHANNELID", inMessage.getHeader("JMSX_CHANNELID"));
        exchange.getOut().setHeader("JMSX_SRCMSGFLAG", inMessage.getHeader("JMSX_SRCMSGFLAG"));

        String strMsg= (String) inMessage.getBody();
        String rtnmsg = new RfmSktClient().processTxn(strMsg);
        System.out.println(rtnmsg);

        String strRtnTxCode=strMsg.substring(7,11);
        if(EnuTaTxCode.TRADE_1001.getCode().equals(strRtnTxCode)){
            Toa9901001Transform toa9901001TransformTemp =new Toa9901001Transform();
            Toa9901001 toa9901001= toa9901001TransformTemp.transform(rtnmsg,"");
            exchange.getOut().setBody(toa9901001);
        }else if(EnuTaTxCode.TRADE_1002.getCode().equals(strRtnTxCode)){
            Toa9901002Transform tOA9901002ransformTemp=new Toa9901002Transform();
            Toa9901002 toa9901002=tOA9901002ransformTemp.transform(rtnmsg,"");
            exchange.getOut().setBody(toa9901002);
        }
    }
}
