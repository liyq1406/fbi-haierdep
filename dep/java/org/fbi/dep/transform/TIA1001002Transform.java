package org.fbi.dep.transform;

import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.txn.TIA1001002;
import org.fbi.dep.util.DateUtils;
import org.fbi.dep.util.PropertyManager;
import org.fbi.endpoint.unionpay.txn.domain.T100002Tia;
import org.fbi.endpoint.unionpay.txn.domain.T100005Tia;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * 批量单笔代付
 */
public class TIA1001002Transform extends AbstractTiaTransform {

    @Override
    public String transform(TIA tia) {
        TIA1001002 tia1001002 = (TIA1001002) tia;
//        return convertTo100005Xml(tia1001002);
        return convertTo100002Xml(tia1001002);
    }

    /*private String convertTo100005Xml(TIA1001002 tia1001002) {
        T100005Tia tia = new T100005Tia();
        tia.INFO = new T100005Tia.TiaHeader();
        tia.INFO.TRX_CODE = "100005";
        tia.INFO.REQ_SN = tia1001002.header.REQ_SN;
        tia.BODY = new T100005Tia.Body();
        tia.BODY.TRANS_SUM = new T100005Tia.Body.BodyHeader();

        //商户协议选择 （包括测试环境）
        if (DEP_IS_RUNNING_DEBUG) {
            tia.INFO.USER_NAME = PropertyManager.getProperty("unionpay_user_name_TEST");
            tia.INFO.USER_PASS = PropertyManager.getProperty("unionpay_user_pass_TEST");
            tia.BODY.TRANS_SUM.BUSINESS_CODE = PropertyManager.getProperty("unionpay_business_code_TEST");
            tia.BODY.TRANS_SUM.MERCHANT_ID = PropertyManager.getProperty("unionpay_merchant_id_TEST");
        } else {
            String bizID = tia1001002.header.BIZ_ID.toUpperCase();
            tia.INFO.USER_NAME = PropertyManager.getProperty("unionpay_user_name_" + bizID);
            tia.INFO.USER_PASS = PropertyManager.getProperty("unionpay_user_pass_" + bizID);
            tia.BODY.TRANS_SUM.BUSINESS_CODE = PropertyManager.getProperty("unionpay_business_code_" + bizID);
            tia.BODY.TRANS_SUM.MERCHANT_ID = PropertyManager.getProperty("unionpay_merchant_id_" + bizID);
        }
        tia.BODY.TRANS_SUM.SUBMIT_TIME = DateUtils.getDatetime14();

        long amount = new BigDecimal(tia1001002.body.AMOUNT).multiply(new BigDecimal("100")).longValue();
        tia.BODY.TRANS_SUM.TOTAL_SUM = String.valueOf(amount);
        tia.BODY.TRANS_DETAILS = new ArrayList<T100005Tia.Body.BodyDetail>();
        T100005Tia.Body.BodyDetail detail = new T100005Tia.Body.BodyDetail();

        detail.SN = getDetailSn(tia.INFO.REQ_SN);

        detail.ACCOUNT_NO = tia1001002.body.ACCOUNT_NO;
        detail.ACCOUNT_NAME = tia1001002.body.ACCOUNT_NAME;
        detail.AMOUNT = String.valueOf(amount);
        detail.BANK_CODE = tia1001002.body.BANK_CODE;
        tia.BODY.TRANS_DETAILS.add(detail);
        return tia.toString();
    }*/


    // 批量代付
    private String convertTo100002Xml(TIA1001002 tia1001002) {
        T100002Tia tia = new T100002Tia();
        tia.INFO = new T100002Tia.TiaHeader();
        tia.INFO.TRX_CODE = "100002";
        tia.INFO.REQ_SN = tia1001002.header.REQ_SN;
        tia.BODY = new T100002Tia.Body();
        tia.BODY.TRANS_SUM = new T100002Tia.Body.BodyHeader();

        //商户协议选择 （包括测试环境）
        if (DEP_IS_RUNNING_DEBUG) {
            tia.INFO.USER_NAME = PropertyManager.getProperty("unionpay_user_name_TEST");
            tia.INFO.USER_PASS = PropertyManager.getProperty("unionpay_user_pass_TEST");
            tia.BODY.TRANS_SUM.BUSINESS_CODE = PropertyManager.getProperty("unionpay_business_code_TEST");
            tia.BODY.TRANS_SUM.MERCHANT_ID = PropertyManager.getProperty("unionpay_merchant_id_TEST");
        } else {
            String bizID = tia1001002.header.BIZ_ID.toUpperCase();
            tia.INFO.USER_NAME = PropertyManager.getProperty("unionpay_user_name_" + bizID);
            tia.INFO.USER_PASS = PropertyManager.getProperty("unionpay_user_pass_" + bizID);
            tia.BODY.TRANS_SUM.BUSINESS_CODE = PropertyManager.getProperty("unionpay_business_code_DF_" + bizID);
            tia.BODY.TRANS_SUM.MERCHANT_ID = PropertyManager.getProperty("unionpay_merchant_id_" + bizID);
        }
        tia.BODY.TRANS_SUM.SUBMIT_TIME = DateUtils.getDatetime14();

        long amount = new BigDecimal(tia1001002.body.AMOUNT).multiply(new BigDecimal("100")).longValue();
        tia.BODY.TRANS_SUM.TOTAL_SUM = String.valueOf(amount);
        tia.BODY.TRANS_DETAILS = new ArrayList<T100002Tia.Body.BodyDetail>();
        T100002Tia.Body.BodyDetail detail = new T100002Tia.Body.BodyDetail();

        detail.SN = getDetailSn(tia.INFO.REQ_SN);

        detail.ACCOUNT_NO = tia1001002.body.ACCOUNT_NO;
        detail.ACCOUNT_NAME = tia1001002.body.ACCOUNT_NAME;
        detail.AMOUNT = String.valueOf(amount);
        detail.BANK_CODE = tia1001002.body.BANK_CODE;
        tia.BODY.TRANS_DETAILS.add(detail);
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
