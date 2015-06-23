package org.fbi.dep.transform;

import org.fbi.dep.enums.TxnStatus;
import org.fbi.dep.model.txn.TOA1003001;
import org.fbi.dep.util.PropertyManager;
import org.fbi.endpoint.unionpay.txn.domain.T200001Toa;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-2-14
 * Time: 上午11:59
 * To change this template use File | Settings | File Templates.
 */
public class TOA1203001Transform extends AbstractToaTransform {
    @Override
    public TOA1003001 transform(String datagram, String txCode) {
        TOA1003001 toa1003001 = null;
        switch (Integer.parseInt(txCode)) {
            case 200001:
                toa1003001 = get200001RtnBean(datagram);
                break;
            default:
                break;
        }
        return toa1003001;
    }

    private static final List<String> RESULT_FAILED_RTNCODES = Arrays.asList(PropertyManager.getProperty("unionpay_trx_code_200001_failed").split(","));
    // private static final List<String> RESULT_UNKNOWN_RTNCODES = Arrays.asList(PropertyManager.getProperty("unionpay_trx_code_200001_unknown").split(","));

    private TOA1003001 get200001RtnBean(String message) {

        T200001Toa toa = T200001Toa.getToa(message);
        TOA1003001 toa1003001 = null;
        toa1003001 = new TOA1003001();
        toa1003001.header.REQ_SN = toa.INFO.REQ_SN;
        toa1003001.header.TX_CODE = toa.INFO.TRX_CODE;
        toa1003001.body.QUERY_SN = toa.BODY.QUERY_TRANS.QUERY_SN;
        toa1003001.body.REMARK = toa.BODY.QUERY_TRANS.QUERY_REMARK;
        // 处理完成
        if ("0000".equals(toa.INFO.RET_CODE)) {
            // 交易成功
            T200001Toa.Body.BodyDetail detail = toa.BODY.RET_DETAILS.get(0);
            if ("0000".equals(detail.RET_CODE)) {
                toa1003001.header.RETURN_CODE = TxnStatus.TXN_SUCCESS.getCode();
                toa1003001.header.RETURN_MSG = TxnStatus.TXN_SUCCESS.getTitle();
                toa1003001.body.QUERY_SN = toa.BODY.QUERY_TRANS.QUERY_SN;
                toa1003001.body.ACCOUNT_NO = detail.ACCOUNT;
                toa1003001.body.ACCOUNT_NAME = detail.ACCOUNT_NAME;
                toa1003001.body.AMOUNT = new BigDecimal(detail.AMOUNT).divide(new BigDecimal(100));
                // 交易失败
            } else {
                toa1003001.header.RETURN_CODE = TxnStatus.TXN_FAILED.getCode();
                toa1003001.header.RETURN_MSG = "[" + detail.RET_CODE + "]" + detail.ERR_MSG;
            }
            // 交易失败
        } else if (RESULT_FAILED_RTNCODES.contains(toa.INFO.RET_CODE)) {
            toa1003001.header.RETURN_CODE = TxnStatus.TXN_FAILED.getCode();
            toa1003001.header.RETURN_MSG = "[" + toa.INFO.RET_CODE + "]" + toa.INFO.ERR_MSG;
            // 结果不明
        } else {
            toa1003001.header.RETURN_CODE = TxnStatus.TXN_QRY_PEND.getCode();
            toa1003001.header.RETURN_MSG = "[" + toa.INFO.RET_CODE + "]" + toa.INFO.ERR_MSG + "。"
                    + TxnStatus.TXN_QRY_PEND.getTitle();
        }
        /*else if (RESULT_UNKNOWN_RTNCODES.contains(toa.INFO.RET_CODE)) {
            toa1003001.header.RETURN_CODE = TxnStatus.TXN_QRY_PEND.getCode();
            toa1003001.header.RETURN_MSG = "[" + toa.INFO.RET_CODE + "]" + toa.INFO.ERR_MSG + "。"
                    + TxnStatus.TXN_QRY_PEND.getTitle();
        }*/
        return toa1003001;
    }
}
