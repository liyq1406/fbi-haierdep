package org.fbi.dep.transform;

import org.fbi.dep.enums.TxnStatus;
import org.fbi.dep.model.txn.TOA1001001;
import org.fbi.dep.model.txn.TOA1001002;
import org.fbi.dep.model.txn.TOA1002001;
import org.fbi.endpoint.unionpay.txn.domain.T100001Toa;
import org.fbi.endpoint.unionpay.txn.domain.T100002Toa;

import java.math.BigDecimal;

/**
 * 批量代付
 */
public class TOA1002001Transform extends AbstractToaTransform {
    @Override
    public TOA1002001 transform(String datagram, String txCode) {
        return get100002RtnBean(datagram);
    }

    private TOA1002001 get100002RtnBean(String message) {

        T100002Toa toa = T100002Toa.getToa(message);
        TOA1002001 toa1002001 = new TOA1002001();
        toa1002001.header.RETURN_CODE = toa.INFO.RET_CODE;      //报文头返回码
        toa1002001.header.RETURN_MSG = toa.INFO.ERR_MSG;      //报文头返回码
        toa1002001 = new TOA1002001();
        toa1002001.header.REQ_SN = toa.INFO.REQ_SN;
        toa1002001.header.TX_CODE = toa.INFO.TRX_CODE;

        if (toa.BODY.RET_DETAILS != null && !toa.BODY.RET_DETAILS.isEmpty()) {
            for (T100002Toa.Body.BodyDetail bodyDetail : toa.BODY.RET_DETAILS) {
                TOA1002001.Body.BodyDetail toadetail = new TOA1002001.Body.BodyDetail();
                toadetail.SN = bodyDetail.SN;
                toadetail.RET_CODE = bodyDetail.RET_CODE;
                toadetail.ERR_MSG = bodyDetail.ERR_MSG;
            }
        }
        return toa1002001;
    }
}
