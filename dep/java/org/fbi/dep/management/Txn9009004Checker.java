package org.fbi.dep.management;

import org.fbi.dep.enums.TxnRtnCode;
import org.fbi.dep.model.ActList;
import org.fbi.dep.model.CheckResult;
import org.fbi.dep.model.txn.TiaXml9009004;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 9009004 SBS内转再内转账号检查
 */
public class Txn9009004Checker extends TxnActChecker implements TxnChecker {

    private static Logger logger = LoggerFactory.getLogger(Txn9009004Checker.class);

    public void check(String userid, String txnCode, String reqMsg, CheckResult res) {
        if (res == null) res = new CheckResult(userid, txnCode);
        TiaXml9009004 tia = (TiaXml9009004) (new TiaXml9009004().getTia(reqMsg));
        ActList.Act actno = null;
        List<ActList.Act> acts = getActnoWhitelist("/ActList.xml");
        for (ActList.Act record : acts) {
            if (record.actno.equals(tia.BODY.OUT_ACTNO) && record.userid.equalsIgnoreCase(userid)) {
                actno = record;
                break;
            }
        }
        if (actno == null) {
            res.setResultCode(TxnRtnCode.TXN_CHECK_ERR.getCode());
            res.setResultMsg(TxnRtnCode.TXN_CHECK_ERR.getTitle());
            logger.info("校验未通过：[Actno]" + tia.BODY.OUT_ACTNO + "[userid]" + userid);
            throw new RuntimeException(TxnRtnCode.TXN_CHECK_ERR.getCode() + "|转出账号校验未通过");
        }
    }
}
