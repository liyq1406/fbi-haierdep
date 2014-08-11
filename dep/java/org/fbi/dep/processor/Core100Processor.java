package org.fbi.dep.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.fbi.dep.helper.DepHttpClient;
import org.fbi.dep.helper.UnionpayCryptHelper;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-2-13
 * Time: 下午9:53
 * To change this template use File | Settings | File Templates.
 */
public class Core100Processor implements Processor {

    private static Logger logger = LoggerFactory.getLogger(Core100Processor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        Message inMessage = exchange.getIn();

        String appID = (String) inMessage.getHeader("JMSX_APPID");
        String bizID = (String) inMessage.getHeader("JMSX_BIZID");

        String serverUrl = PropertyManager.getProperty("unionpay_server_url");
        String datagram = inMessage.getBody(String.class);

        logger.info("银联业务处理报文：" + datagram);

        String xmlStr = UnionpayCryptHelper.signMsg(datagram, bizID.toUpperCase());
        String rtnDatagram = new DepHttpClient().doPost(serverUrl, xmlStr, PropertyManager.getProperty("unionpay_http_charset_name"));

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
