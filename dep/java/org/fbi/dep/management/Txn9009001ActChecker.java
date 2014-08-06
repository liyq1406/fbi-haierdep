package org.fbi.dep.management;

import org.fbi.dep.enums.TxnRtnCode;
import org.fbi.dep.model.Act9009001List;
import org.fbi.dep.model.CheckResult;
import org.fbi.dep.model.txn.TiaXml9009001;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * 9009001 SBS内转账号白名单检查
 */
public class Txn9009001ActChecker extends Txn900Checker {

    private static Logger logger = LoggerFactory.getLogger(Txn9009001ActChecker.class);

    public void check(String userid, String txnCode, String reqMsg, CheckResult res) {
        if (res == null) res = new CheckResult(userid, txnCode);
        TiaXml9009001 tia = (TiaXml9009001) (new TiaXml9009001().getTia(reqMsg));
        Act9009001List.ActInfo actno = null;
        for (Act9009001List.ActInfo record : getActnoWhitelist("/9009001Actlist.xml")) {
            if (record.actno.equals(tia.BODY.OUT_ACTNO) && record.userid.equalsIgnoreCase(userid)) {
                actno = record;
                break;
            }
        }
        if (actno == null) {
            res.setResultCode(TxnRtnCode.TXN_CHECK_ERR.getCode());
            res.setResultMsg(TxnRtnCode.TXN_CHECK_ERR.getTitle());
            logger.info("ActnoWhitelistChecker 校验未通过：[Actno]" + tia.BODY.OUT_ACTNO + "[userid]" + userid);
        } else {
            logger.info("ActnoWhitelistChecker 校验通过");
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

    private List<Act9009001List.ActInfo> getActnoWhitelist(String fileName) {
        String xml = readFile(fileName);
        logger.info("ActnoWhitelist: " + xml);
        Act9009001List actnoWhiteList = new Act9009001List();
        actnoWhiteList = actnoWhiteList.toBean(xml);
        logger.info("ActnoWhitelist账号数：" + actnoWhiteList.ActInfo.size());
        return actnoWhiteList.ActInfo;
    }

}
