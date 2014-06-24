package org.fbi.dep.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.fbi.endpoint.eai.transCreditInfoFromSBStoJDE.InType;
import org.fbi.endpoint.eai.transCreditInfoFromSBStoJDE.TransCreditInfoFromSBStoJDE;
import org.fbi.endpoint.eai.transCreditInfoFromSBStoJDE.TransCreditInfoFromSBStoJDE_Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.Holder;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-2-13
 * Time: 下午9:53
 * To change this template use File | Settings | File Templates.
 */
public class Core300Processor implements Processor {

    private static Logger logger = LoggerFactory.getLogger(Core300Processor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        Message inMessage = exchange.getIn();
        logger.info("Core300Processor: " + exchange.getIn().getHeader("JMSCorrelationID", String.class));

        String appID = (String) inMessage.getHeader("JMSX_APPID");
        String bizID = (String) inMessage.getHeader("JMSX_BIZID");
        StringBuilder lines = new StringBuilder();

        if ("transCreditInfoFromSBStoJDE".equalsIgnoreCase(bizID)) {

            String record = inMessage.getBody(String.class);
            logger.info("dep 连接 eai 执行交易开始----------：");
//            for (String record : datagram.split("\n")) {
                String[] fields = record.split("\\|");
                logger.info("record: " + record);
                InType in = new InType();
                in.setSzNumberDL01(fields[0]);
                in.setSzClearDateDL02(fields[1]);
                //  DL03 对应 CBNK  --总公司账号  DL04  对应 EHBK  --分公司账号
                in.setSzZongAccountNumberDL03(fields[2]);
                in.setSzZongAccountNumberDL04(fields[3]);
                in.setSzClearAccountDL05(fields[4]);
                in.setSzClearDirectionDL06(fields[5]);
                TransCreditInfoFromSBStoJDE transCreditInfoFromSBStoJDE = new TransCreditInfoFromSBStoJDE_Service().getTransCreditInfoFromSBStoJDESOAP();
                String flag = transCreditInfoFromSBStoJDE.transCreditInfoFromSBStoJDE(in);
                logger.info("流水号：" + fields[0] + "  Flag: " + flag);
                lines.append(fields[0]).append("|").append(flag);
//            }
//            logger.info(" SBS总分账户清算明细发送结束。");
        } else {
            //
            logger.error("eai暂无此业务：" + bizID);
        }

        exchange.getOut().setHeader("JMSCorrelationID", inMessage.getHeader("JMSCorrelationID", String.class));
        exchange.getOut().setHeader("JMSX_APPID", appID);
        exchange.getOut().setHeader("JMSX_TXCODE", "ftpsbs");
        exchange.getOut().setHeader("JMSX_CHANNELID", inMessage.getHeader("JMSX_CHANNELID"));
        exchange.getOut().setHeader("JMSX_SRCMSGFLAG", inMessage.getHeader("JMSX_SRCMSGFLAG"));
        exchange.getOut().setBody(lines.toString());
    }
}
