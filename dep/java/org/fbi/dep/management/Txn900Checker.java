package org.fbi.dep.management;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.enums.TxnRtnCode;
import org.fbi.dep.model.CheckResult;
import org.fbi.dep.util.PropertyManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * �����Χϵͳ��ʶ
 */
public abstract class Txn900Checker extends TxnUseridChecker implements TxnChecker {

    public void execute(String userid, String txnCode, String reqMsg, CheckResult res) {

        // У��Userid�Ƿ���ִ��txnCode���׵�Ȩ��
        if (checkUserid(userid, txnCode, res)) {
            check(userid, txnCode, reqMsg, res);
        }
    }

    @Override
    public abstract void check(String userid, String txnCode, String reqMsg, CheckResult res);

}
