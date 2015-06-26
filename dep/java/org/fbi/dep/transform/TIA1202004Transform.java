package org.fbi.dep.transform;

import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.txn.TIA1202004;
import org.fbi.dep.util.PropertyManager;
import org.fbi.endpoint.allinpay.domain.T120004Tia;

/**
 * 单笔实时查询
 */
public class TIA1202004Transform extends AbstractTiaTransform {

    @Override
    public String transform(TIA tia) {

        TIA1202004 tia1202004 = (TIA1202004) tia;
        T120004Tia tia120004 = new T120004Tia();
        tia120004.INFO = new T120004Tia.TiaHeader();
        tia120004.INFO.TRX_CODE = "200004";
        tia120004.INFO.LEVEL = "5";
        tia120004.INFO.REQ_SN = tia1202004.header.REQ_SN;

        //商户协议选择 （包括测试环境）
        if (DEP_IS_RUNNING_DEBUG) {
            tia120004.INFO.USER_NAME = PropertyManager.getProperty("allinpay_user_name_TEST");
            tia120004.INFO.USER_PASS = PropertyManager.getProperty("allinpay_user_pass_TEST");
        } else {//生产
            String bizID = tia1202004.header.BIZ_ID.toUpperCase();
            tia120004.INFO.USER_NAME = PropertyManager.getProperty("unionpay_user_name_" + bizID);
            tia120004.INFO.USER_PASS = PropertyManager.getProperty("unionpay_user_pass_" + bizID);
        }
        tia120004.QTRANSREQ = new T120004Tia.Body();
//        tia120004.INFO.VERSION = "03";
        tia120004.QTRANSREQ.MERCHANT_ID = tia1202004.body.MERCHANT_ID;
        tia120004.QTRANSREQ.QUERY_SN = tia1202004.body.QUERY_SN;
        tia120004.QTRANSREQ.STATUS = tia1202004.body.STATUS;
        tia120004.QTRANSREQ.TYPE = tia1202004.body.TYPE;
        tia120004.QTRANSREQ.START_DAY = tia1202004.body.START_DAY;
        tia120004.QTRANSREQ.END_DAY = tia1202004.body.END_DAY;

        return tia120004.toString();
    }
}
