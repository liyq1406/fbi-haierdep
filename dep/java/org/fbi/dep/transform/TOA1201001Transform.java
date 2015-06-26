package org.fbi.dep.transform;

import org.fbi.dep.enums.TxnStatus;
import org.fbi.dep.model.txn.TOA1201001;
import org.fbi.endpoint.allinpay.domain.T120001Toa;

/**
 * Created by Lichao.W At 2015/6/24 22:21
 * wanglichao@163.com
 */
public class TOA1201001Transform extends AbstractToaTransform {
    @Override
    public TOA1201001 transform(String datagram, String txCode) {

        TOA1201001 toa1201001 = null;
        switch (Integer.parseInt(txCode)) {
            case 100001:
                toa1201001 = get120001RtnBean(datagram);
                break;
            default:
                break;
        }
        return toa1201001;
    }

    private TOA1201001 get120001RtnBean(String message) {

        T120001Toa toa = T120001Toa.getToa(message);
        TOA1201001 toa1201001 = null;
        String retcode_head = toa.INFO.RET_CODE;      //报文头返回码
        toa1201001 = new TOA1201001();
        toa1201001.header.REQ_SN = toa.INFO.REQ_SN;
        toa1201001.header.TX_CODE = toa.INFO.TRX_CODE;

        if ("0000".equals(retcode_head)) { //报文头“0000”：处理完成
            T120001Toa.Body.BodyDetail bodyDetail = toa.BODY.RET_DETAILS.get(0);
            String retcode_detl = bodyDetail.RET_CODE;
            if ("0000".equals(retcode_detl)) { //交易成功的唯一标志
                toa1201001.header.RETURN_CODE = TxnStatus.TXN_SUCCESS.getCode();
                toa1201001.header.RETURN_MSG = TxnStatus.TXN_SUCCESS.getTitle();
                toa1201001.body.SN = bodyDetail.SN;
                toa1201001.body.RET_CODE = bodyDetail.RET_CODE;
                toa1201001.body.ERR_MSG = bodyDetail.ERR_MSG;
            } else {
                toa1201001.header.RETURN_CODE = TxnStatus.TXN_FAILED.getCode();
                toa1201001.header.RETURN_MSG = "[" + retcode_detl + "]" + bodyDetail.ERR_MSG;
            }
        } else if (retcode_head.startsWith("1")) { // 交易失败
            toa1201001.header.RETURN_CODE = TxnStatus.TXN_FAILED.getCode();
            toa1201001.header.RETURN_MSG = "[" + toa.INFO.RET_CODE + "]" + toa.INFO.ERR_MSG;
        } else { //待查询
            toa1201001.header.RETURN_CODE = TxnStatus.TXN_QRY_PEND.getCode();
            toa1201001.header.RETURN_MSG = TxnStatus.TXN_QRY_PEND.getTitle();
        }
        return toa1201001;
    }

    /*private TOA1201001 get120001RtnBean(String message) {

        T120001Toa toa = T120001Toa.getToa(message);
        String retcode_head = toa.INFO.RET_CODE;      //报文头返回码
        TOA1201001 toa1201001 = new TOA1201001();
        toa1201001.header.REQ_SN = toa.INFO.REQ_SN;
        toa1201001.header.TX_CODE = toa.INFO.TRX_CODE;
        if (retcode_head.startsWith("1")) {
            toa1201001.header.RETURN_CODE = TxnStatus.TXN_FAILED.getCode();
            toa1201001.header.RETURN_MSG = "[" + retcode_head + "]" + toa.INFO.ERR_MSG;
        } else {
            toa1201001.header.RETURN_CODE = TxnStatus.TXN_QRY_PEND.getCode();
            toa1201001.header.RETURN_MSG = "[" + retcode_head + "]" + toa.INFO.ERR_MSG
                    + "。" + TxnStatus.TXN_QRY_PEND.getTitle();
        }
        return toa1201001;
    }*/
}
