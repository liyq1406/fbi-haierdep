package org.fbi.dep.transform;

import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.txn.TIA1201011;
import org.fbi.dep.util.DateUtils;
import org.fbi.dep.util.PropertyManager;
import org.fbi.endpoint.allinpay.domain.T120011Tia;

import java.math.BigDecimal;

/**
 * 单笔实时代扣
 */
public class TIA1201011Transform extends AbstractTiaTransform {

    @Override
    public String transform(TIA tia) {
        TIA1201011 tia1201011 = (TIA1201011) tia;
        return convertTo1201011Xml(tia1201011);
    }

    private String convertTo1201011Xml(TIA1201011 tia1201011) {
        T120011Tia tia = new T120011Tia();
        tia.INFO = new T120011Tia.TiaHeader();
        tia.INFO.TRX_CODE = "100011";
        tia.INFO.LEVEL = "5";
        tia.INFO.REQ_SN = tia1201011.header.REQ_SN;
        tia.TRANS = new T120011Tia.Body();

        //商户协议选择 （包括测试环境）
        if (DEP_IS_RUNNING_DEBUG) {
            tia.INFO.USER_NAME = PropertyManager.getProperty("allinpay_user_name_TEST");
            tia.INFO.USER_PASS = PropertyManager.getProperty("allinpay_user_pass_TEST");
            tia.TRANS.MERCHANT_ID = PropertyManager.getProperty("allinpay_merchant_id_TEST");
//            tia.TRANS.BUSINESS_CODE = tia1201011.body.BUSINESS_CODE;
        } else {  //
            String bizID = tia1201011.header.BIZ_ID.toUpperCase();
            tia.INFO.USER_NAME = PropertyManager.getProperty("allinpay_user_name_TEST" + bizID);
            tia.INFO.USER_PASS = PropertyManager.getProperty("allinpay_user_pass_TEST" + bizID);
//            tia.TRANS.BUSINESS_CODE = tia1201011.body.BUSINESS_CODE;
            tia.TRANS.MERCHANT_ID = PropertyManager.getProperty("allinpay_merchant_id_TEST" + bizID);
        }
        tia.TRANS.SUBMIT_TIME = DateUtils.getDatetime14();

        long amount = new BigDecimal(tia1201011.body.AMOUNT).multiply(new BigDecimal("100")).longValue();


        tia.TRANS.BUSINESS_CODE = tia1201011.body.BUSINESS_CODE;
        tia.TRANS.E_USER_CODE = tia1201011.body.E_USER_CODE;
        tia.TRANS.VALIDATE = tia1201011.body.VALIDATE;
        tia.TRANS.CVV2 = tia1201011.body.CVV2;
        tia.TRANS.BANK_CODE = tia1201011.body.BANK_CODE;
        tia.TRANS.ACCOUNT_TYPE = tia1201011.body.ACCOUNT_TYPE;
        tia.TRANS.ACCOUNT_NO = tia1201011.body.ACCOUNT_NO;
        tia.TRANS.ACCOUNT_NAME = tia1201011.body.ACCOUNT_NAME;
        tia.TRANS.ACCOUNT_PROP = tia1201011.body.ACCOUNT_PROP;
        tia.TRANS.AMOUNT = String.valueOf(amount);
        tia.TRANS.CURRENCY = tia1201011.body.CURRENCY;
        tia.TRANS.ID_TYPE = tia1201011.body.ID_TYPE;
        tia.TRANS.ID = tia1201011.body.ID;
        tia.TRANS.SETTACCT = tia1201011.body.SETTACCT;
        tia.TRANS.TEL = tia1201011.body.TEL;
        tia.TRANS.CUST_USERID = tia1201011.body.CUST_USERID;
        tia.TRANS.SETTGROUPFLAG = tia1201011.body.SETTGROUPFLAG;
        tia.TRANS.SUMMARY = tia1201011.body.SUMMARY;
        tia.TRANS.SUMMARY = tia1201011.body.REMARK;

        return tia.toString();
    }

    private String getDetailSn(String reqSn) {
        String detailSn = "";
        int len = reqSn.length();
        if (reqSn.startsWith("HAIERHCSP") && len > 16) {
            detailSn = reqSn.substring(len - 16, len);
        } else {
            if (len > 25) {
                if (reqSn.startsWith("HAIER")) {
                    detailSn = reqSn.substring(5);
                } else {
                    detailSn = reqSn.substring(0, 25);
                }
            } else {
                detailSn = reqSn;
            }
        }
        return detailSn;
    }
}
