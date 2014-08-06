package org.fbi.dep.management;

import org.fbi.dep.model.CheckResult;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 13-9-5
 * Time: ионГ10:18
 * To change this template use File | Settings | File Templates.
 */
public interface TxnChecker {

    void check(String userid, String txnCode, String reqMsg, CheckResult res);
}
