package org.fbi.dep.transform;

import org.fbi.dep.enums.TxnStatus;
import org.fbi.dep.model.txn.TOA1001001;
import org.fbi.endpoint.unionpay.txn.domain.T100001Toa;
import org.fbi.endpoint.unionpay.txn.domain.T100004Toa;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-2-14
 * Time: 上午11:59
 * To change this template use File | Settings | File Templates.
 */
public class TOA8001001Transform extends AbstractToaTransform {
    @Override
    public TOA1001001 transform(String datagram, String txCode) {

        TOA1001001 toa1001001 = null;
        switch (Integer.parseInt(txCode)) {
            case 100004:
                toa1001001 = get100004RtnBean(datagram);
                break;
            case 100001:
                toa1001001 = get100001RtnBean(datagram);
                break;
            default:
                break;
        }
        return toa1001001;
    }

    private TOA1001001 get100004RtnBean(String message) {

        T100004Toa toa = T100004Toa.getToa(message);
        TOA1001001 toa1001001 = null;
        String retcode_head = toa.INFO.RET_CODE;      //报文头返回码
        toa1001001 = new TOA1001001();
        toa1001001.header.REQ_SN = toa.INFO.REQ_SN;
        toa1001001.header.TX_CODE = toa.INFO.TRX_CODE;

        if ("0000".equals(retcode_head)) { //报文头“0000”：处理完成
            T100004Toa.Body.BodyDetail bodyDetail = toa.BODY.RET_DETAILS.get(0);
            String retcode_detl = bodyDetail.RET_CODE;
            if ("0000".equals(retcode_detl)) { //交易成功的唯一标志
                toa1001001.header.RETURN_CODE = TxnStatus.TXN_SUCCESS.getCode();
                toa1001001.header.RETURN_MSG = TxnStatus.TXN_SUCCESS.getTitle();
                toa1001001.body.ACCOUNT_NAME = bodyDetail.ACCOUNT_NAME;
                toa1001001.body.ACCOUNT_NO = bodyDetail.ACCOUNT_NO;
                toa1001001.body.AMOUNT = new BigDecimal(bodyDetail.AMOUNT).divide(new BigDecimal(100));
            } else {
                toa1001001.header.RETURN_CODE = TxnStatus.TXN_FAILED.getCode();
                toa1001001.header.RETURN_MSG = "[" + retcode_detl + "]" + bodyDetail.ERR_MSG;
            }
        } else if (retcode_head.startsWith("1")) { // 交易失败
            toa1001001.header.RETURN_CODE = TxnStatus.TXN_FAILED.getCode();
            toa1001001.header.RETURN_MSG = "[" + toa.INFO.RET_CODE + "]" + toa.INFO.ERR_MSG;
        } else if (retcode_head.startsWith("2")) { //待查询
            toa1001001.header.RETURN_CODE = TxnStatus.TXN_QRY_PEND.getCode();
            toa1001001.header.RETURN_MSG = TxnStatus.TXN_QRY_PEND.getTitle();
        } else { //待查询
            toa1001001.header.RETURN_CODE = TxnStatus.TXN_QRY_PEND.getCode();
            toa1001001.header.RETURN_MSG = TxnStatus.TXN_QRY_PEND.getTitle();
        }
        return toa1001001;
    }

    private TOA1001001 get100001RtnBean(String message) {

        T100001Toa toa = T100001Toa.getToa(message);
        String retcode_head = toa.INFO.RET_CODE;      //报文头返回码
        TOA1001001 toa1001001 = new TOA1001001();
        toa1001001.header.REQ_SN = toa.INFO.REQ_SN;
        toa1001001.header.TX_CODE = toa.INFO.TRX_CODE;
        if (retcode_head.startsWith("1")) {
            toa1001001.header.RETURN_CODE = TxnStatus.TXN_FAILED.getCode();
            toa1001001.header.RETURN_MSG = "[" + retcode_head + "]" + toa.INFO.ERR_MSG;
        } else {
            toa1001001.header.RETURN_CODE = TxnStatus.TXN_QRY_PEND.getCode();
            toa1001001.header.RETURN_MSG = "[" + retcode_head + "]" + toa.INFO.ERR_MSG
                    + "。" + TxnStatus.TXN_QRY_PEND.getTitle();
        }
        return toa1001001;
    }
}
