package org.fbi.dep.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.fbi.dep.model.txn.TIA4004879;
import org.fbi.dep.model.txn.TIA4007614;
import org.fbi.dep.model.txn.TOA400;
import org.fbi.endpoint.tcs.org.tempuri.dhwebservice1.service.Service;
import org.fbi.endpoint.tcs.org.tempuri.dhwebservice1.service.ServiceSoap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by XIANGYANG on 2015-9-9.
 * TCS交易处理（电汇-4879，总分账户-7616）
 */

public class Core400Processor implements Processor {
    private static Logger logger = LoggerFactory.getLogger(Core400Processor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        Message inMessage = exchange.getIn();
        logger.info("Core400Processor: " + exchange.getIn().getHeader("JMSCorrelationID", String.class));

        String txcode = (String) inMessage.getHeader("JMSX_TXCODE");
        Object msgBody = inMessage.getBody();
        Service service=new Service();
        ServiceSoap serviceSoap=service.getServiceSoap();
        TOA400 toa=new TOA400();
        try {
            Boolean result=null;
            if (msgBody instanceof TIA4004879) {
                exchange.getOut().setHeader("REQ_TXN_CODE", "4004879");
                TIA4004879 tia4004879 = (TIA4004879) msgBody;
                result = serviceSoap.dianhui(tia4004879.body.Version, tia4004879.body.TxCode, tia4004879.body.FuncCode,
                        tia4004879.body.Channel, tia4004879.body.SubCenterId, tia4004879.body.NodeId,
                        tia4004879.body.TellerId, tia4004879.body.TxSeqId, tia4004879.body.TxDate1,
                        tia4004879.body.TxTime1, tia4004879.body.UserId, tia4004879.body.OperatorUserId,
                        tia4004879.body.RecCount, tia4004879.body.AcctId, tia4004879.body.AcctHostSeqId,
                        tia4004879.body.TxDate2, tia4004879.body.OutAcctId, tia4004879.body.OutAcctName,
                        tia4004879.body.OutBranchName, tia4004879.body.InAcctId, tia4004879.body.InAcctName,
                        tia4004879.body.InBranchName, tia4004879.body.TxAmount, tia4004879.body.AcctBal,
                        tia4004879.body.AvBal, tia4004879.body.CAcctBal, tia4004879.body.CTxAmount,
                        tia4004879.body.CurCode, tia4004879.body.DCFlag, tia4004879.body.AbstractStr,
                        tia4004879.body.TxTime2, tia4004879.body.VoucherId, tia4004879.body.VoucherType,
                        tia4004879.body.CoSeqId, tia4004879.body.Abstract, tia4004879.body.BankNodeId,
                        tia4004879.body.CCBSTellerId, tia4004879.body.BankIndex, tia4004879.body.OutComAcctId,
                        tia4004879.body.OutComName, tia4004879.body.Reserve1, tia4004879.body.Reserve2);
            } else if (msgBody instanceof TIA4007614) {
                exchange.getOut().setHeader("REQ_TXN_CODE", "4007616");
                TIA4007614 tia4007614 = (TIA4007614) msgBody;
                result = serviceSoap.zongfenzhanghu(tia4007614.body.bankSerial, tia4007614.body.tradeDate, tia4007614.body.payAccount,
                        tia4007614.body.payAccountName, tia4007614.body.apAmount, tia4007614.body.toCompanyCode,
                        tia4007614.body.recCompanyName, tia4007614.body.recAccount, tia4007614.body.voucherFlag,
                        tia4007614.body.purpose, tia4007614.body.dcFlag);
            }
            toa.header.RETURN_CODE=new String(result.toString());
        } catch (Exception e) {
            logger.error(txcode + "到账通知处理出错", e);
            toa.header.RETURN_MSG=e.toString();
        }

        exchange.getOut().setHeader("JMSCorrelationID", inMessage.getHeader("JMSCorrelationID"));
        exchange.getOut().setHeader("JMSX_APPID", inMessage.getHeader("JMSX_APPID"));
        exchange.getOut().setHeader("JMSX_BIZID", inMessage.getHeader("JMSX_BIZID"));
        exchange.getOut().setHeader("JMSX_TXCODE", inMessage.getHeader("JMSX_TXCODE"));
        exchange.getOut().setHeader("JMSX_CHANNELID", inMessage.getHeader("JMSX_CHANNELID"));
        exchange.getOut().setHeader("JMSX_SRCMSGFLAG", inMessage.getHeader("JMSX_SRCMSGFLAG"));
        exchange.getOut().setBody(toa);
    }
}
