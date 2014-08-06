package org.fbi.dep.management;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.enums.TxnRtnCode;
import org.fbi.dep.model.CheckResult;
import org.fbi.dep.util.PropertyManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 检查外围系统标识
 */
public abstract class Txn900Checker extends TxnUseridChecker implements TxnChecker {

    public void execute(String userid, String txnCode, String reqMsg, CheckResult res) {

        // 校验Userid是否有执行txnCode交易的权限
        if (checkUserid(userid, txnCode, res)) {
            check(userid, txnCode, reqMsg, res);
        }
    }

    @Override
    public abstract void check(String userid, String txnCode, String reqMsg, CheckResult res);

}
