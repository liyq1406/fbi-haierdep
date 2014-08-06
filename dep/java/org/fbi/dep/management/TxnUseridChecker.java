package org.fbi.dep.management;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.enums.TxnRtnCode;
import org.fbi.dep.model.CheckResult;
import org.fbi.dep.util.PropertyManager;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lenovo on 2014-08-06.
 */
public class TxnUseridChecker {

    public boolean checkUserid(String userid, String txnCode, CheckResult res) {
        String txns = PropertyManager.getProperty("sbs.txns." + userid);
        if (StringUtils.isEmpty(txns)) {
            res.setResultCode(TxnRtnCode.TXN_CHECK_ERR.getCode());
            res.setResultMsg(TxnRtnCode.TXN_CHECK_ERR.getTitle());
            return false;
        } else {
            String[] txnArr = txns.split(",");
            List<String> txnList = Arrays.asList(txnArr);
            if (!txnList.contains(txnCode)) {
                res.setResultCode(TxnRtnCode.TXN_CHECK_ERR.getCode());
                res.setResultMsg("Ȩ�޲��㣬�޷�ִ�н��ף�" + txnCode);
                return false;
            }
        }
        return true;
    }

}
