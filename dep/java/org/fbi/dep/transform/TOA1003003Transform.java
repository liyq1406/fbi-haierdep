package org.fbi.dep.transform;

import org.fbi.dep.model.txn.TOA1003003;
import org.fbi.dep.util.PropertyManager;
import org.fbi.endpoint.unionpay.txn.domain.T200001Toa;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 */
public class TOA1003003Transform extends AbstractToaTransform {
    @Override
    public TOA1003003 transform(String datagram, String txCode) {
        TOA1003003 toa1003003 = null;
        switch (Integer.parseInt(txCode)) {
            case 200001:
                toa1003003 = get200001RtnBean(datagram);
                break;
            default:
                break;
        }
        return toa1003003;
    }

    private static final List<String> RESULT_FAILED_RTNCODES = Arrays.asList(PropertyManager.getProperty("unionpay_trx_code_200001_failed").split(","));
    // private static final List<String> RESULT_UNKNOWN_RTNCODES = Arrays.asList(PropertyManager.getProperty("unionpay_trx_code_200001_unknown").split(","));

    private TOA1003003 get200001RtnBean(String message) {

        T200001Toa toa = T200001Toa.getToa(message);
        TOA1003003 toa1003003 = new TOA1003003();
        toa1003003.header.REQ_SN = toa.INFO.REQ_SN;
        toa1003003.header.TX_CODE = "1003003";
        toa1003003.body.QUERY_SN = toa.BODY.QUERY_TRANS.QUERY_SN;
        toa1003003.body.REMARK = toa.BODY.QUERY_TRANS.QUERY_REMARK;
        toa1003003.header.RETURN_CODE = toa.INFO.RET_CODE;
        toa1003003.header.RETURN_MSG = toa.INFO.ERR_MSG;
        if (toa.BODY != null && toa.BODY.RET_DETAILS != null && !toa.BODY.RET_DETAILS.isEmpty()) {
            for (T200001Toa.Body.BodyDetail detail : toa.BODY.RET_DETAILS) {
                TOA1003003.Body.BodyDetail record = new TOA1003003.Body.BodyDetail();
                record.SN = detail.SN;
                record.RET_CODE = detail.RET_CODE;
                record.ERR_MSG = detail.ERR_MSG;
                record.ACCOUNT_NO = detail.ACCOUNT;
                record.ACCOUNT_NAME = detail.ACCOUNT_NAME;
                record.AMOUNT = new BigDecimal(detail.AMOUNT).divide(new BigDecimal(100));
                toa1003003.body.RET_DETAILS.add(record);
            }
        }
/*
            // 交易失败
        } else if (RESULT_FAILED_RTNCODES.contains(toa.INFO.RET_CODE)) {
            toa1003003.header.RETURN_CODE = TxnStatus.TXN_FAILED.getCode();
            toa1003003.header.RETURN_MSG = "[" + toa.INFO.RET_CODE + "]" + toa.INFO.ERR_MSG;
            // 结果不明
        } else {
            toa1003003.header.RETURN_CODE = TxnStatus.TXN_QRY_PEND.getCode();
            toa1003003.header.RETURN_MSG = "[" + toa.INFO.RET_CODE + "]" + toa.INFO.ERR_MSG;
        }*/
        /*else if (RESULT_UNKNOWN_RTNCODES.contains(toa.INFO.RET_CODE)) {
            toa1003001.header.RETURN_CODE = TxnStatus.TXN_QRY_PEND.getCode();
            toa1003001.header.RETURN_MSG = "[" + toa.INFO.RET_CODE + "]" + toa.INFO.ERR_MSG + "。"
                    + TxnStatus.TXN_QRY_PEND.getTitle();
        }*/
        return toa1003003;
    }
}
