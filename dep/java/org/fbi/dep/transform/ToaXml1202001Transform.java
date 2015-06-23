package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.base.ToaXmlHeader;
import org.fbi.dep.model.txn.ToaXml1002001;
import org.fbi.endpoint.unionpay.txn.domain.T100002Toa;
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
ÒøÁª 100002 -> 1002001
 */
public class ToaXml1202001Transform extends AbstractToaXmlTransform {
    private static Logger logger = LoggerFactory.getLogger(ToaXml1002001Transform.class);

    public String transform(String xml, String wsysid) {
        // xml -> bean
        T100002Toa toa100002 = T100002Toa.getToa(xml);
        // bean -> txn bean
        ToaXml1002001 toa = new ToaXml1002001();
        toa.INFO = new ToaXmlHeader();
        toa.INFO.TRX_CODE = "1002001";
        toa.INFO.REQ_SN = toa100002.INFO.REQ_SN;
        toa.INFO.RET_CODE = toa100002.INFO.RET_CODE;
        toa.INFO.ERR_MSG = toa100002.INFO.ERR_MSG;
        toa.INFO.WSYS_ID = wsysid;
        // txn bean -> txn xml
        toa.BODY = new ToaXml1002001.Body();
        toa.BODY.TRANS_DETAILS = new ArrayList<ToaXml1002001.Body.BodyDetail>();
        if (toa100002.BODY.RET_DETAILS != null && toa100002.BODY.RET_DETAILS.size() > 0) {
            for (T100002Toa.Body.BodyDetail bodyDetail : toa100002.BODY.RET_DETAILS) {
                ToaXml1002001.Body.BodyDetail record = new ToaXml1002001.Body.BodyDetail();
                record.SN = bodyDetail.SN;
                record.RET_CODE = bodyDetail.RET_CODE;
                record.ERR_MSG = StringUtils.isEmpty(bodyDetail.ERR_MSG)? "" : bodyDetail.ERR_MSG;
                toa.BODY.TRANS_DETAILS.add(record);
            }
        }
        return toa.toString();
    }
}
