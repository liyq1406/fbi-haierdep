package org.fbi.dep.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.fbi.dep.helper.AllinpayCryptHelper;
import org.fbi.dep.helper.allinPayHelper.XmlTools;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Lichao.W At 2015/6/24 22:21
 * wanglichao@163.com
 */
public class Core120Processor implements Processor {

    private static Logger logger = LoggerFactory.getLogger(Core120Processor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        Message inMessage = exchange.getIn();

        String appID = (String) inMessage.getHeader("JMSX_APPID");
        String bizID = (String) inMessage.getHeader("JMSX_BIZID");

        String serverUrl = PropertyManager.getProperty("allinpay_server_url");
        String datagram = inMessage.getBody(String.class);

        logger.info("通联业务处理报文：" + datagram);

        String xmlStr = AllinpayCryptHelper.signMsgAl(datagram); // 添加通联签名验证

        String rtnDatagram = XmlTools.send(serverUrl, xmlStr);  //发送到通联

        exchange.getOut().setHeader("JMSCorrelationID", inMessage.getHeader("JMSCorrelationID"));
        exchange.getOut().setHeader("JMSX_APPID", appID);
        exchange.getOut().setHeader("JMSX_TXCODE", getSubstrBetweenStrs(rtnDatagram, "<TRX_CODE>", "</TRX_CODE>"));
        exchange.getOut().setHeader("JMSX_CHANNELID", inMessage.getHeader("JMSX_CHANNELID"));
        exchange.getOut().setHeader("REQ_TXN_CODE", inMessage.getHeader("REQ_TXN_CODE"));
        exchange.getOut().setHeader("JMSX_SRCMSGFLAG", inMessage.getHeader("JMSX_SRCMSGFLAG"));
        exchange.getOut().setBody(rtnDatagram);


    }

    public static String getSubstrBetweenStrs(String fromStr, String startStr, String endStr) {
        int length = startStr.length();
        int start = fromStr.indexOf(startStr) + length;
        int end = fromStr.indexOf(endStr);
        return fromStr.substring(start, end);
    }
}
