package org.fbi.dep.transform;

import org.fbi.dep.enums.TxnStatus;
import org.fbi.dep.model.txn.TOA1201011;
import org.fbi.endpoint.allinpay.domain.T120011Toa;

/**
 * 通联单笔实时代扣
 */
public class TOA1201011Transform extends AbstractToaTransform {
    @Override
    public TOA1201011 transform(String datagram, String txCode) {
        //if ("120011".equals(txCode)) {
        return get1201011RtnBean(datagram);
        // } else
        //return get120005RtnBean(datagram);
    }

    private TOA1201011 get1201011RtnBean(String message) {

        T120011Toa toa = T120011Toa.getToa(message);
        TOA1201011 toa1201011 = null;
        String retcode_head = toa.INFO.RET_CODE;      //报文头返回码
        toa1201011 = new TOA1201011();
        toa1201011.header.REQ_SN = toa.INFO.REQ_SN;
        toa1201011.header.TX_CODE = toa.INFO.TRX_CODE;

        if ("0000".equals(retcode_head)) { //报文头“0000”：处理完成
            String retcode_detl = toa.TRANSRET.RET_CODE;
            if ("0000".equals(retcode_detl)) { //交易成功的唯一标志
                toa1201011.header.RETURN_CODE = TxnStatus.TXN_SUCCESS.getCode();
                toa1201011.header.RETURN_MSG = TxnStatus.TXN_SUCCESS.getTitle();
                toa1201011.body.RET_CODE = toa.TRANSRET.RET_CODE;
                toa1201011.body.SETTLE_DAY = toa.TRANSRET.SETTLE_DAY;
                toa1201011.body.ERR_MSG = toa.TRANSRET.ERR_MSG;
            } else {
                toa1201011.header.RETURN_CODE = TxnStatus.TXN_FAILED.getCode();
                toa1201011.header.RETURN_MSG = "[" + retcode_detl + "]" + toa.TRANSRET.ERR_MSG;
            }
        } else if (retcode_head.startsWith("1")) { // 交易失败
            toa1201011.header.RETURN_CODE = TxnStatus.TXN_FAILED.getCode();
            toa1201011.header.RETURN_MSG = "[" + toa.INFO.RET_CODE + "]" + toa.INFO.ERR_MSG;
        } else { //待查询
            toa1201011.header.RETURN_CODE = TxnStatus.TXN_QRY_PEND.getCode();
            toa1201011.header.RETURN_MSG = TxnStatus.TXN_QRY_PEND.getTitle();
        }
        return toa1201011;
    }


    /*private TOA1201011 get120005RtnBean(String message) {

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
        return toa1201011;
    }*/

}
