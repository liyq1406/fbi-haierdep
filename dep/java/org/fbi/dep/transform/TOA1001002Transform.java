package org.fbi.dep.transform;

import org.fbi.dep.enums.TxnStatus;
import org.fbi.dep.model.txn.TOA1001001;
import org.fbi.dep.model.txn.TOA1001002;
import org.fbi.endpoint.unionpay.txn.domain.T100001Toa;
import org.fbi.endpoint.unionpay.txn.domain.T100002Toa;
import org.fbi.endpoint.unionpay.txn.domain.T100005Toa;

import java.math.BigDecimal;

/**
 * 批量单笔代付
 */
public class TOA1001002Transform extends AbstractToaTransform {
    @Override
    public TOA1001002 transform(String datagram, String txCode) {
        if ("100002".equals(txCode)) {
            return get100002RtnBean(datagram);
        } else
            return get100005RtnBean(datagram);
    }

    private TOA1001002 get100002RtnBean(String message) {

        T100002Toa toa = T100002Toa.getToa(message);
        TOA1001002 toa1001002 = null;
        String retcode_head = toa.INFO.RET_CODE;      //报文头返回码
        toa1001002 = new TOA1001002();
        toa1001002.header.REQ_SN = toa.INFO.REQ_SN;
        toa1001002.header.TX_CODE = toa.INFO.TRX_CODE;

        toa1001002.header.RETURN_CODE = toa.INFO.RET_CODE;
        toa1001002.header.RETURN_MSG = toa.INFO.ERR_MSG;

//        T100002Toa.Body.BodyDetail bodyDetail = toa.BODY.RET_DETAILS.get(0);

        return toa1001002;
    }


    private TOA1001002 get100005RtnBean(String message) {

        T100005Toa toa = T100005Toa.getToa(message);
        TOA1001002 toa1001002 = null;
        String retcode_head = toa.INFO.RET_CODE;      //报文头返回码
        toa1001002 = new TOA1001002();
        toa1001002.header.REQ_SN = toa.INFO.REQ_SN;
        toa1001002.header.TX_CODE = toa.INFO.TRX_CODE;

        if ("0000".equals(retcode_head)) { //报文头“0000”：处理完成
            T100005Toa.Body.BodyDetail bodyDetail = toa.BODY.RET_DETAILS.get(0);
            String retcode_detl = bodyDetail.RET_CODE;
            if ("0000".equals(retcode_detl)) { //交易成功的唯一标志
                toa1001002.header.RETURN_CODE = TxnStatus.TXN_SUCCESS.getCode();
                toa1001002.header.RETURN_MSG = TxnStatus.TXN_SUCCESS.getTitle();
                toa1001002.body.ACCOUNT_NAME = bodyDetail.ACCOUNT_NAME;
                toa1001002.body.ACCOUNT_NO = bodyDetail.ACCOUNT_NO;
                toa1001002.body.AMOUNT = new BigDecimal(bodyDetail.AMOUNT).divide(new BigDecimal(100));
            } else {
                toa1001002.header.RETURN_CODE = TxnStatus.TXN_FAILED.getCode();
                toa1001002.header.RETURN_MSG = "[" + retcode_detl + "]" + bodyDetail.ERR_MSG;
            }
        } else if (retcode_head.startsWith("1")) { // 交易失败
            toa1001002.header.RETURN_CODE = TxnStatus.TXN_FAILED.getCode();
            toa1001002.header.RETURN_MSG = "[" + toa.INFO.RET_CODE + "]" + toa.INFO.ERR_MSG;
        } else if (retcode_head.startsWith("2")) { //待查询
            toa1001002.header.RETURN_CODE = TxnStatus.TXN_QRY_PEND.getCode();
            toa1001002.header.RETURN_MSG = TxnStatus.TXN_QRY_PEND.getTitle();
        } else { //待查询
            toa1001002.header.RETURN_CODE = TxnStatus.TXN_QRY_PEND.getCode();
            toa1001002.header.RETURN_MSG = TxnStatus.TXN_QRY_PEND.getTitle();
        }
        return toa1001002;
    }

}
