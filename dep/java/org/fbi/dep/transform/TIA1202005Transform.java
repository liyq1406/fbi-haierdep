package org.fbi.dep.transform;

import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.txn.TIA1202005;
import org.fbi.dep.util.PropertyManager;
import org.fbi.endpoint.allinpay.domain.T120005Tia;

/**
 * Created by IntelliJ IDEA.
 * User: 批量结果查询
 */
public class TIA1202005Transform extends AbstractTiaTransform {

    @Override
    public String transform(TIA tia) {

        TIA1202005 tia1202005 = (TIA1202005) tia;
        T120005Tia tia120005 = new T120005Tia();
        tia120005.INFO = new T120005Tia.TiaHeader();
        tia120005.INFO.TRX_CODE = "200004";      //与单笔查询公用一个交易码
        tia120005.INFO.REQ_SN = tia1202005.header.REQ_SN;

        //商户协议选择 （包括测试环境）
        if (DEP_IS_RUNNING_DEBUG) {
            tia120005.INFO.USER_NAME = PropertyManager.getProperty("allinpay_user_name_TEST");
            tia120005.INFO.USER_PASS = PropertyManager.getProperty("allinpay_user_pass_TEST");
        } else {
            String bizID = tia1202005.header.BIZ_ID.toUpperCase();
            tia120005.INFO.USER_NAME = PropertyManager.getProperty("unionpay_user_name_" + bizID);
            tia120005.INFO.USER_PASS = PropertyManager.getProperty("unionpay_user_pass_" + bizID);
        }
        tia120005.QTRANSREQ = new T120005Tia.Body();
        tia120005.QTRANSREQ.MERCHANT_ID = tia1202005.body.MERCHANT_ID;
        tia120005.QTRANSREQ.QUERY_SN = tia1202005.body.QUERY_SN;

        return tia120005.toString();
    }
}
