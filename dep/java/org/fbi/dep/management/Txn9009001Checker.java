package org.fbi.dep.management;

import org.fbi.dep.enums.TxnRtnCode;
import org.fbi.dep.model.ActList9009001;
import org.fbi.dep.model.CheckResult;
import org.fbi.dep.model.txn.TiaXml9009001;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * 9009001 SBS内转账号白名单检查
 */
public class Txn9009001Checker implements TxnChecker {

    private static Logger logger = LoggerFactory.getLogger(Txn9009001Checker.class);

    public void check(String userid, String txnCode, String reqMsg, CheckResult res) {
        if (res == null) res = new CheckResult(userid, txnCode);
        TiaXml9009001 tia = (TiaXml9009001) (new TiaXml9009001().getTia(reqMsg));
        ActList9009001.Act actno = null;
        for (ActList9009001.Act record : getActnoWhitelist("/ActList9009001.xml")) {
            if (record.actno.equals(tia.BODY.OUT_ACTNO) && record.userid.equalsIgnoreCase(userid)) {
                actno = record;
                break;
            }
        }
        if (actno == null) {
            res.setResultCode(TxnRtnCode.TXN_CHECK_ERR.getCode());
            res.setResultMsg(TxnRtnCode.TXN_CHECK_ERR.getTitle());
            logger.info("校验未通过：[Actno]" + tia.BODY.OUT_ACTNO + "[userid]" + userid);
        }
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

    private List<ActList9009001.Act> getActnoWhitelist(String fileName) {
        String xml = readFile(fileName);
        ActList9009001 actnoWhiteList = new ActList9009001();
        actnoWhiteList = actnoWhiteList.toBean(xml);
        return actnoWhiteList.Acts;
    }

}
