package org.fbi.dep.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.base.TIA;
import org.fbi.dep.transform.Tia900010002Transform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-2-13
 * Time: 下午9:51
 * To change this template use File | Settings | File Templates.
 */
public class TIA900Processor implements Processor {

    private static Logger logger = LoggerFactory.getLogger(TIA900Processor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        Message inMessage = exchange.getIn();
        String correlationID =inMessage.getHeader("JMSCorrelationID", String.class);
        if (StringUtils.isEmpty(correlationID)) {
            exchange.getOut().setHeader("JMSCorrelationID", exchange.getIn().getMessageId());
            logger.info("TIA JMSCorrelationID : " + exchange.getIn().getMessageId());
        } else {
            exchange.getOut().setHeader("JMSCorrelationID", correlationID);
            logger.info("TIA JMSCorrelationID : " + correlationID);
        }

        String strJMSXSRCMSGFLAG=inMessage.getHeaders().get("JMSX_SRCMSGFLAG").toString();
        if("haierrfm.object".equals(strJMSXSRCMSGFLAG)){
            TIA tiaTemp=(TIA)inMessage.getBody();
            exchange.getOut().setHeader("JMSX_APPID", inMessage.getHeader("JMSX_APPID"));
            exchange.getOut().setHeader("JMSX_CHANNELID", inMessage.getHeader("JMSX_CHANNELID"));
            exchange.getOut().setHeader("JMSX_SRCMSGFLAG", inMessage.getHeader("JMSX_SRCMSGFLAG"));
            // 报文体填充
            Tia900010002Transform tia900010002TransformTemp = new Tia900010002Transform();
            byte[] sbsReqMsg = tia900010002TransformTemp.transform(tiaTemp);
            exchange.getOut().setBody(sbsReqMsg);
        }
    }
}
