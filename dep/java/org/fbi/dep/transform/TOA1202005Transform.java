package org.fbi.dep.transform;

import org.fbi.dep.model.txn.TOA1202005;
import org.fbi.dep.util.PropertyManager;
import org.fbi.endpoint.allinpay.domain.T120005Toa;

import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * 批量代扣结果查询
 */
public class TOA1202005Transform extends AbstractToaTransform {
    @Override
    public TOA1202005 transform(String datagram, String txCode) {
        TOA1202005 toa1202005 = null;
        switch (Integer.parseInt(txCode)) {
            case 200004:       //批量结果查询与单笔使用同一交易代码
                toa1202005 = get120005RtnBean(datagram);
                break;
            default:
                break;
        }
        return toa1202005;
    }

    private static final List<String> RESULT_FAILED_RTNCODES = Arrays.asList(PropertyManager.getProperty("allinpay_trx_code_200001_failed").split(","));

    private TOA1202005 get120005RtnBean(String message) {

        T120005Toa toa = T120005Toa.getToa(message);
        TOA1202005 toa1202005 = new TOA1202005();
        toa1202005.header.REQ_SN = toa.INFO.REQ_SN;
        toa1202005.header.TX_CODE = "1202005";
//        toa1202005.body. = toa.BODY.QUERY_TRANS.QUERY_SN;
//        toa1202005.body.REMARK = toa.BODY.QUERY_TRANS.QUERY_REMARK;
        toa1202005.header.RETURN_CODE = toa.INFO.RET_CODE;
        toa1202005.header.RETURN_MSG = toa.INFO.ERR_MSG;
        if (toa.QTRANSRSP != null) {
            for (T120005Toa.Body.BodyDetail detail : toa.QTRANSRSP) {
                TOA1202005.Body.BodyDetail record = new TOA1202005.Body.BodyDetail();
                record.SN = detail.SN;
                record.RET_CODE = detail.RET_CODE;
                record.ERR_MSG = detail.ERR_MSG;
                record.ACCOUNT_NO = detail.ACCOUNT_NO;
                record.ACCOUNT_NAME = detail.ACCOUNT_NAME;
                record.AMOUNT = detail.AMOUNT;
                toa1202005.body.RET_DETAILS.add(record);
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
        return toa1202005;
    }
}
