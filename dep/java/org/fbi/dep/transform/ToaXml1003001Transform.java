package org.fbi.dep.transform;

import org.fbi.dep.model.base.ToaXmlHeader;
import org.fbi.dep.model.txn.ToaXml1003001;
import org.fbi.endpoint.unionpay.txn.domain.T200001Toa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 13-4-2
 * Time: ÏÂÎç11:04
 * To change this template use File | Settings | File Templates.
 */
/*
ÒøÁª 200001 -> 1003001
 */
public class ToaXml1003001Transform extends AbstractToaXmlTransform {
    private static Logger logger = LoggerFactory.getLogger(ToaXml1003001Transform.class);

    public String transform(String xml, String wsysid) {
        // xml -> bean
        T200001Toa toa200001 = T200001Toa.getToa(xml);
        // bean -> txn bean
        ToaXml1003001 toa = new ToaXml1003001();
        toa.INFO = new ToaXmlHeader();
        toa.INFO.TRX_CODE = "1003001";
        toa.INFO.REQ_SN = toa200001.INFO.REQ_SN;
        toa.INFO.RET_CODE = toa200001.INFO.RET_CODE;
        toa.INFO.ERR_MSG = toa200001.INFO.ERR_MSG;
        toa.INFO.WSYS_ID = wsysid;
        // txn bean -> txn xml
        toa.BODY = new ToaXml1003001.Body();
        toa.BODY.QUERY_TRANS.QUERY_SN = toa200001.BODY.QUERY_TRANS.QUERY_SN;
        toa.BODY.QUERY_TRANS.QUERY_REMARK = toa200001.BODY.QUERY_TRANS.QUERY_REMARK;
        toa.BODY.RET_DETAILS = new ArrayList<ToaXml1003001.Body.BodyDetail>();
        if (toa200001.BODY.RET_DETAILS != null) {
            for (T200001Toa.Body.BodyDetail bodyDetail : toa200001.BODY.RET_DETAILS) {
                ToaXml1003001.Body.BodyDetail record = new ToaXml1003001.Body.BodyDetail();
                record.SN = bodyDetail.SN;
                record.ACCOUNT_NO = bodyDetail.ACCOUNT;
                record.ACCOUNT_NAME = bodyDetail.ACCOUNT_NAME;
                record.AMOUNT = bodyDetail.AMOUNT;
                record.REMARK = bodyDetail.REMARK;
                record.RET_CODE = bodyDetail.RET_CODE;
                record.ERR_MSG = bodyDetail.ERR_MSG;
                toa.BODY.RET_DETAILS.add(record);
            }
        }
        return toa.toString();
    }
}
