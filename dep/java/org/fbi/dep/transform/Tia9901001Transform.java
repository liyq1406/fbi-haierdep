package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.txn.TIA1001001;
import org.fbi.dep.model.txn.TIA9901001;
import org.fbi.dep.util.DateUtils;
import org.fbi.dep.util.PropertyManager;
import org.fbi.endpoint.unionpay.txn.domain.T100001Tia;
import org.fbi.endpoint.unionpay.txn.domain.T100004Tia;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-2-13
 * Time: ����9:11
 * To change this template use File | Settings | File Templates.
 */
public class Tia9901001Transform extends AbstractTiaTransform {

    @Override
    public String transform(TIA tia) {
        TIA9901001 tia8001001 = (TIA9901001) tia;

        return convertTo800001Str(tia8001001);
    }

    private String convertTo800001Str(TIA9901001 tia8001001) {
        /*T100001Tia tia = new T100001Tia();
        tia.INFO.TRX_CODE = "100001";
        tia.INFO.LEVEL = "5";
        tia.INFO.REQ_SN = tia8001001.header.REQ_SN;
        //�̻�Э��ѡ�� ���������Ի�����
        if (DEP_IS_RUNNING_DEBUG) {
            tia.INFO.USER_NAME = PropertyManager.getProperty("unionpay_user_name_TEST");
            tia.INFO.USER_PASS = PropertyManager.getProperty("unionpay_user_pass_TEST");
            tia.BODY.TRANS_SUM.BUSINESS_CODE = PropertyManager.getProperty("unionpay_business_code_TEST");
            tia.BODY.TRANS_SUM.MERCHANT_ID = PropertyManager.getProperty("unionpay_merchant_id_TEST");
        } else {
            String bizID = tia8001001.header.BIZ_ID.toUpperCase();
            tia.INFO.USER_NAME = PropertyManager.getProperty("unionpay_user_name_" + bizID);
            tia.INFO.USER_PASS = PropertyManager.getProperty("unionpay_user_pass_" + bizID);
            tia.BODY.TRANS_SUM.BUSINESS_CODE = PropertyManager.getProperty("unionpay_business_code_" + bizID);
            tia.BODY.TRANS_SUM.MERCHANT_ID = PropertyManager.getProperty("unionpay_merchant_id_" + bizID);
        }
        tia.BODY.TRANS_SUM.SUBMIT_TIME = DateUtils.getDatetime14();
        tia.BODY.TRANS_SUM.TOTAL_ITEM = "1";
        long amt = new BigDecimal(tia8001001.body.AMOUNT).multiply(new BigDecimal("100")).longValue();
        T100001Tia.Body.BodyDetail detail = new T100001Tia.Body.BodyDetail();

        //detail.SN = "0001";
        //zhanrui  20120319  Ӧ�ۺ�Ҫ������ˮ���ó��뱨�ı��һ��
        detail.SN = getDetailSn(tia.INFO.REQ_SN);

        detail.BANK_CODE = tia1001001.body.BANK_CODE;
        detail.ACCOUNT_TYPE = (StringUtils.isEmpty(tia1001001.body.ACCOUNT_TYPE) ? "00" : tia1001001.body.ACCOUNT_TYPE);
        detail.ACCOUNT_NO = tia1001001.body.ACCOUNT_NO;
        detail.ACCOUNT_NAME = tia1001001.body.ACCOUNT_NAME;
        detail.PROVINCE = tia1001001.body.PROVINCE;
        detail.CITY = tia1001001.body.CITY;
        detail.ACCOUNT_PROP = (StringUtils.isEmpty(tia1001001.body.ACCOUNT_PROP) ? "0" : tia1001001.body.ACCOUNT_PROP);      // ����
        detail.AMOUNT = String.valueOf(amt);
        // 2012-10-23 ��ע=֤���� [���ˣ��ǿգ�����1518]
        if ("0".equals(detail.ACCOUNT_PROP) && !StringUtils.isEmpty(tia1001001.body.REMARK)) {
            int remarkLength = tia1001001.body.REMARK.getBytes().length;
            if (remarkLength == 15 || remarkLength == 18) {
                detail.ID = tia1001001.body.REMARK;
                // 2012-10-25 �������أ�ϵͳ���ܶ��ʺŽ��д���
                detail.ID_TYPE = "0";
            }
        }
        detail.REMARK = tia1001001.body.REMARK;

        tia.BODY.TRANS_DETAILS.add(detail);
        tia.BODY.TRANS_SUM.TOTAL_SUM = String.valueOf(amt);

        return tia.toString();*/
        return null;
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