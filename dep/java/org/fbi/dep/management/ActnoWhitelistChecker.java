package org.fbi.dep.management;

import org.fbi.dep.enums.TxnRtnCode;
import org.fbi.dep.model.ActnoWhitelist;
import org.fbi.dep.model.CheckResult;
import org.fbi.dep.model.txn.TiaXml9009001;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * 账号白名单检查
 */
public class ActnoWhitelistChecker implements CheckProcessor {

    private static Logger logger = LoggerFactory.getLogger(ActnoWhitelistChecker.class);

    public void check(String appid, String txnCode, String reqMsg, CheckResult res) {
        if (res == null) res = new CheckResult(appid, txnCode);
        TiaXml9009001 tia = (TiaXml9009001) (new TiaXml9009001().getTia(reqMsg));
        ActnoWhitelist.WhiteActno actno = null;
        for (ActnoWhitelist.WhiteActno record : getActnoWhitelist("/ActnoWhitelist.xml")) {
            if (record.actno.equals(tia.BODY.OUT_ACTNO)) {
                actno = record;
                break;
            }
        }
        if (actno == null) {
            res.setResultCode(TxnRtnCode.TXN_ACT_CHECK_ERR.getCode());
            res.setResultMsg(TxnRtnCode.TXN_ACT_CHECK_ERR.getTitle());
        } else if (!actno.userid.equalsIgnoreCase(appid)) {
            res.setResultCode(TxnRtnCode.TXN_SYSID_CHECK_ERR.getCode());
            res.setResultMsg(TxnRtnCode.TXN_SYSID_CHECK_ERR.getTitle());
        } else {
            res.setResultCode(TxnRtnCode.TXN_PROCESSED.getCode());
        }
        logger.info("ActnoWhitelistChecker 检查结果异常：" + res.getResultCode());
    }

    private String readFile(String fileName) {
        BufferedReader reader = null;
        StringBuffer buffer = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(fileName)));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                buffer.append(tempString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return buffer.toString();
    }

    private List<ActnoWhitelist.WhiteActno> getActnoWhitelist(String fileName) {
        String xml = readFile(fileName);
        logger.info("ActnoWhitelist: " + xml);
        ActnoWhitelist actnoWhiteList = new ActnoWhitelist();
        actnoWhiteList = actnoWhiteList.toBean(xml);
        logger.info("ActnoWhitelist账号数：" + actnoWhiteList.WhiteActno.size());
        return actnoWhiteList.WhiteActno;
    }

}
