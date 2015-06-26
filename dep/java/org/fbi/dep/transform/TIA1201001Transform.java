package org.fbi.dep.transform;

import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.txn.TIA1201001;
import org.fbi.dep.util.DateUtils;
import org.fbi.dep.util.PropertyManager;
import org.fbi.endpoint.allinpay.domain.T120001Tia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * Created by Lichao.W At 2015/6/24 22:21
 * wanglichao@163.com
 */
public class TIA1201001Transform extends AbstractTiaTransform {
    private static Logger logger = LoggerFactory.getLogger(TIA1201001Transform.class);

    @Override
    public String transform(TIA tia) {
        TIA1201001 tia1201001 = (TIA1201001) tia;
        // TODO 多笔批量代扣接口
        return convertTo120001Xml(tia1201001);
    }

    private String convertTo120001Xml(TIA1201001 tia1201001) {
        T120001Tia tia = new T120001Tia();
        tia.INFO.TRX_CODE = "100001";   //批量代扣交易码
        tia.INFO.LEVEL = "5";
        tia.INFO.REQ_SN = tia1201001.header.REQ_SN;
        //商户协议选择 （包括测试环境）
        if (DEP_IS_RUNNING_DEBUG) {  //测试环境
            tia.INFO.USER_NAME = PropertyManager.getProperty("allinpay_user_name_TEST");
            tia.INFO.USER_PASS = PropertyManager.getProperty("allinpay_user_pass_TEST");
            tia.BODY.TRANS_SUM.MERCHANT_ID = PropertyManager.getProperty("allinpay_merchant_id_TEST");
            tia.BODY.TRANS_SUM.BUSINESS_CODE = tia1201001.body.TRANS_SUM.BUSINESS_CODE;

        } else {    // 生产
            String bizID = tia1201001.header.BIZ_ID.toUpperCase();
            tia.INFO.USER_NAME = PropertyManager.getProperty("unionpay_user_name_" + bizID);
            tia.INFO.USER_PASS = PropertyManager.getProperty("unionpay_user_pass_" + bizID);
            tia.BODY.TRANS_SUM.BUSINESS_CODE = PropertyManager.getProperty("unionpay_business_code_" + bizID);
            tia.BODY.TRANS_SUM.MERCHANT_ID = PropertyManager.getProperty("unionpay_merchant_id_" + bizID);
        }

        tia.BODY.TRANS_SUM.SUBMIT_TIME = DateUtils.getDatetime14();
        tia.BODY.TRANS_SUM.TOTAL_ITEM = tia1201001.body.TRANS_SUM.TOTAL_ITEM;
        long amt = new BigDecimal(tia1201001.body.TRANS_SUM.TOTAL_SUM).multiply(new BigDecimal("100")).longValue();
        tia.BODY.TRANS_SUM.TOTAL_SUM = String.valueOf(amt);

        for (TIA1201001.Body.BodyDetail bodyDetail : tia1201001.body.TRANS_DETAILS) {
            T120001Tia.Body.BodyDetail detail = new T120001Tia.Body.BodyDetail();
            try {
                detail.SN = bodyDetail.SN;
                detail.E_USER_CODE = bodyDetail.E_USER_CODE;
                detail.ACCOUNT_TYPE = bodyDetail.ACCOUNT_TYPE;
                detail.ACCOUNT_NO = bodyDetail.ACCOUNT_NO;
                detail.ACCOUNT_NAME = bodyDetail.ACCOUNT_NAME;
                detail.PROVINCE = bodyDetail.PROVINCE;
                detail.CITY = bodyDetail.CITY;
                detail.BANK_NAME = bodyDetail.BANK_NAME;
                detail.BANK_CODE = bodyDetail.BANK_CODE;
                detail.ACCOUNT_PROP = bodyDetail.ACCOUNT_PROP;
                detail.CURRENCY = bodyDetail.CURRENCY;
                detail.PROTOCOL = bodyDetail.PROTOCOL;
                detail.PROTOCOL_USERID = bodyDetail.PROTOCOL_USERID;
                detail.ID_TYPE = bodyDetail.ID_TYPE;
                detail.ID = bodyDetail.ID;
                detail.TEL = bodyDetail.TEL;
                detail.CUST_USERID = bodyDetail.CUST_USERID;
                detail.SETTACCT = bodyDetail.SETTACCT;
                detail.REMARK = bodyDetail.REMARK;
                detail.SETTGROUPFLAG = bodyDetail.SETTGROUPFLAG;
                detail.SUMMARY = bodyDetail.SUMMARY;
                detail.UNION_BANK = bodyDetail.UNION_BANK;
                detail.AMOUNT = String.valueOf(new BigDecimal(bodyDetail.AMOUNT).multiply(new BigDecimal("100")).longValue());
            } catch (Exception e) {
                logger.error("TIA1201001Transform:copbean 赋值错误。", e);
                throw new RuntimeException("TIA1201001Transform:copbean 赋值错误。");
            }
            tia.BODY.TRANS_DETAILS.add(detail);
        }

        return tia.toString();
    }

    /**
     * detail.SN = "0001";
     * zhanrui  20120319  应售后要求将子流水号置成与报文编号一致
     * 暂时没用
     */
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
