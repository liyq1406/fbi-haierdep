package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.txn.TiaXml9009201;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
SBS 9009201 -> aa41
此交易用于二代支付系统进行手续费核算。

财务公司付手续费时，收付标志为‘F’，账务处理为：
借：电子汇划费支出 801090106006053001
         贷：存放中央银行 801090106001031001

财务公司收手续费时，收付标志为‘S’，账务处理为：
借：存放中央银行 801090106001031001
         贷：电子汇划费支出 801090106006053001

 */
public class TiaXml9009201Transform extends AbstractTiaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(TiaXml9009201Transform.class);
    // SBS电子汇划费支出账户-损益账户
    private static final String SBS_ACT_ELEC_EXCHANGE_EXPOSE = PropertyManager.getProperty("sbs.eeeact");
    // SBS 存储中央银行账户-同业账户
    private static final String SBS_ACT_DEPOSIT_CENT_BANK = PropertyManager.getProperty("sbs.dcbact");

    @Override
    byte[] transform(String xml, String userid) {
        TiaXml9009201 tia = (TiaXml9009201) (new TiaXml9009201().getTia(xml));
        byte[] bytes = null;
        String termID = PropertyManager.getProperty("sbs.termid." + userid);
        if (StringUtils.isEmpty(termID)) {
            termID = "MT01";
        }
        if ("F".equalsIgnoreCase(tia.BODY.SFFLAG.trim())) {
            bytes = SbsTxnDataTransform.convertToTxnAa41(tia.INFO.REQ_SN,
                    SBS_ACT_ELEC_EXCHANGE_EXPOSE, SBS_ACT_DEPOSIT_CENT_BANK,
                    tia.BODY.TXNAMT, termID, tia.BODY.REMARK);
        } else if ("S".equalsIgnoreCase(tia.BODY.SFFLAG.trim())) {
            bytes = SbsTxnDataTransform.convertToTxnAa41(tia.INFO.REQ_SN,
                    SBS_ACT_DEPOSIT_CENT_BANK, SBS_ACT_ELEC_EXCHANGE_EXPOSE,
                    tia.BODY.TXNAMT, termID, tia.BODY.REMARK);
        } else {
            throw new RuntimeException("二代支付手续费入账收付标志错:" + tia.BODY.SFFLAG);
        }
        return bytes;
    }
}
