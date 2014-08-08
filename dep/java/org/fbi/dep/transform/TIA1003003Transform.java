package org.fbi.dep.transform;

import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.txn.TIA1003001;
import org.fbi.dep.model.txn.TIA1003003;
import org.fbi.dep.util.PropertyManager;
import org.fbi.endpoint.unionpay.txn.domain.T200001Tia;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 */
public class TIA1003003Transform extends AbstractTiaTransform {

    @Override
    public String transform(TIA tia) {

        TIA1003003 tia1003003 = (TIA1003003) tia;
        T200001Tia tia200001 = new T200001Tia();
        tia200001.INFO = new T200001Tia.TiaHeader();
        tia200001.INFO.TRX_CODE = "200001";
        tia200001.INFO.REQ_SN = tia1003003.header.REQ_SN;

        //商户协议选择 （包括测试环境）
        if (DEP_IS_RUNNING_DEBUG) {
            tia200001.INFO.USER_NAME = PropertyManager.getProperty("unionpay_user_name_TEST");
            tia200001.INFO.USER_PASS = PropertyManager.getProperty("unionpay_user_pass_TEST");
        } else {
            String bizID = tia1003003.header.BIZ_ID.toUpperCase();
            tia200001.INFO.USER_NAME = PropertyManager.getProperty("unionpay_user_name_" + bizID);
            tia200001.INFO.USER_PASS = PropertyManager.getProperty("unionpay_user_pass_" + bizID);
        }
        tia200001.BODY = new T200001Tia.Body();
        tia200001.INFO.VERSION = "03";
        tia200001.BODY.QUERY_TRANS = new T200001Tia.Body.BodyHeader();
        tia200001.BODY.QUERY_TRANS.QUERY_REMARK = tia1003003.body.REMARK;
        tia200001.BODY.QUERY_TRANS.QUERY_SN = tia1003003.body.QUERY_SN;

        return tia200001.toString();
    }
}
