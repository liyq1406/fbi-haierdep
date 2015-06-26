package org.fbi.dep.transform;

import org.fbi.dep.model.txn.TOA1001003;
import org.fbi.endpoint.unionpay.txn.domain.T100001Toa;

/**
 * 批量多笔代扣
 */
public class TOA1001003Transform extends AbstractToaTransform {
    @Override
    public TOA1001003 transform(String datagram, String txCode) {
        return get100001RtnBean(datagram);
    }

    private TOA1001003 get100001RtnBean(String message) {

        T100001Toa toa = T100001Toa.getToa(message);
        TOA1001003 toa1001003 = new TOA1001003();
        toa1001003.header.RETURN_CODE = toa.INFO.RET_CODE;      //报文头返回码
        toa1001003.header.RETURN_MSG = toa.INFO.ERR_MSG;      //报文头返回码
        toa1001003.header.REQ_SN = toa.INFO.REQ_SN;
        toa1001003.header.TX_CODE = "1001003";

        if (toa.BODY != null && toa.BODY.RET_DETAILS != null && !toa.BODY.RET_DETAILS.isEmpty()) {
            for (T100001Toa.Body.BodyDetail bodyDetail : toa.BODY.RET_DETAILS) {
                TOA1001003.Body.BodyDetail toadetail = new TOA1001003.Body.BodyDetail();
                toadetail.SN = bodyDetail.SN;
                toadetail.RET_CODE = bodyDetail.RET_CODE;
                toadetail.ERR_MSG = bodyDetail.ERR_MSG;
            }
        }
        return toa1001003;
    }
}
