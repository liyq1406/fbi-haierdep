package org.fbi.dep.model;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 13-9-13
 * Time: ÏÂÎç3:12
 * To change this template use File | Settings | File Templates.
 */
public class CheckResult {
    private String userid;
    private String txnCode;
    private String resultCode = "0000";
    private String resultMsg;

    public CheckResult() {
    }

    public CheckResult(String userid, String txnCode) {
        this.userid = userid;
        this.txnCode = txnCode;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTxnCode() {
        return txnCode;
    }

    public void setTxnCode(String txnCode) {
        this.txnCode = txnCode;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
