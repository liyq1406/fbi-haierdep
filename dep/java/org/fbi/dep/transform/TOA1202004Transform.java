package org.fbi.dep.transform;

import org.fbi.dep.enums.TxnStatus;
import org.fbi.dep.model.txn.TOA1202004;
import org.fbi.dep.util.PropertyManager;
import org.fbi.endpoint.allinpay.domain.T120004Toa;

import java.util.Arrays;
import java.util.List;

/**
 * 单笔实时代扣查询
 */
public class TOA1202004Transform extends AbstractToaTransform {
    @Override
    public TOA1202004 transform(String datagram, String txCode) {
        TOA1202004 toa1202004 = null;
        switch (Integer.parseInt(txCode)) {
            case 200004:
                toa1202004 = get120004RtnBean(datagram);
                break;
            default:
                break;
        }
        return toa1202004;
    }

    private static final List<String> RESULT_FAILED_RTNCODES = Arrays.asList(PropertyManager.getProperty("allinpay_trx_code_200001_failed").split(","));

    private TOA1202004 get120004RtnBean(String message) {

        T120004Toa toa = T120004Toa.getToa(message);
        TOA1202004 toa1202004 = null;
        toa1202004 = new TOA1202004();
        toa1202004.header.REQ_SN = toa.INFO.REQ_SN;
        toa1202004.header.TX_CODE = toa.INFO.TRX_CODE;
        toa1202004.body.BATCHID = toa.QTRANSRSP.QTDETAIL.BATCHID;
        toa1202004.body.SN = toa.QTRANSRSP.QTDETAIL.SN;
        toa1202004.body.TRXDIR = toa.QTRANSRSP.QTDETAIL.TRXDIR;
        toa1202004.body.SETTDAY = toa.QTRANSRSP.QTDETAIL.SETTDAY;
        toa1202004.body.FINTIME = toa.QTRANSRSP.QTDETAIL.FINTIME;
        toa1202004.body.SUBMITTIME = toa.QTRANSRSP.QTDETAIL.SUBMITTIME;
        toa1202004.body.ACCOUNT_NO = toa.QTRANSRSP.QTDETAIL.ACCOUNT_NO;
        toa1202004.body.ACCOUNT_NAME = toa.QTRANSRSP.QTDETAIL.ACCOUNT_NAME;
        toa1202004.body.AMOUNT = toa.QTRANSRSP.QTDETAIL.AMOUNT;
        toa1202004.body.CUST_USERID = toa.QTRANSRSP.QTDETAIL.CUST_USERID;
        toa1202004.body.RET_CODE = toa.QTRANSRSP.QTDETAIL.RET_CODE;
        toa1202004.body.ERR_MSG = toa.QTRANSRSP.QTDETAIL.ERR_MSG;
        // 处理完成
        if ("0000".equals(toa.INFO.RET_CODE)) {
            // 交易成功
            T120004Toa.Body.BodyDetail detail = toa.QTRANSRSP.QTDETAIL;
            if ("0000".equals(detail.RET_CODE)) {
                toa1202004.header.RETURN_CODE = TxnStatus.TXN_SUCCESS.getCode();
                toa1202004.header.RETURN_MSG = TxnStatus.TXN_SUCCESS.getTitle();
                toa1202004.body.ACCOUNT_NO = detail.ACCOUNT_NO;
                toa1202004.body.ACCOUNT_NAME = detail.ACCOUNT_NAME;
                toa1202004.body.AMOUNT = detail.AMOUNT;
            } else {   // 交易失败
                toa1202004.header.RETURN_CODE = TxnStatus.TXN_FAILED.getCode();
                toa1202004.header.RETURN_MSG = "[" + detail.RET_CODE + "]" + detail.ERR_MSG;
            }
            // 交易失败
        } else if (RESULT_FAILED_RTNCODES.contains(toa.INFO.RET_CODE)) {
            toa1202004.header.RETURN_CODE = TxnStatus.TXN_FAILED.getCode();
            toa1202004.header.RETURN_MSG = "[" + toa.INFO.RET_CODE + "]" + toa.INFO.ERR_MSG;
            // 结果不明
        } else {
            toa1202004.header.RETURN_CODE = TxnStatus.TXN_QRY_PEND.getCode();
            toa1202004.header.RETURN_MSG = "[" + toa.INFO.RET_CODE + "]" + toa.INFO.ERR_MSG + "。"
                    + TxnStatus.TXN_QRY_PEND.getTitle();
        }
        /*else if (RESULT_UNKNOWN_RTNCODES.contains(toa.INFO.RET_CODE)) {
            toa1003001.header.RETURN_CODE = TxnStatus.TXN_QRY_PEND.getCode();
            toa1003001.header.RETURN_MSG = "[" + toa.INFO.RET_CODE + "]" + toa.INFO.ERR_MSG + "。"
                    + TxnStatus.TXN_QRY_PEND.getTitle();
        }*/
        return toa1202004;
    }
}
