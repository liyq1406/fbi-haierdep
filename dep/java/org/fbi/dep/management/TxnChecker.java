package org.fbi.dep.management;

import org.fbi.dep.model.ActList;
import org.fbi.dep.model.CheckResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

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
