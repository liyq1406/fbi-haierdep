package org.fbi.dep.transform;

import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.txn.TIA1002001;
import org.fbi.dep.util.DateUtils;
import org.fbi.dep.util.PropertyManager;
import org.fbi.endpoint.unionpay.txn.domain.T100002Tia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * 批量代付
 */
public class TIA1202001Transform extends AbstractTiaTransform {
    private static Logger logger = LoggerFactory.getLogger(TIA1202001Transform.class);

    @Override
    public String transform(TIA tia) {
        TIA1002001 tia1002001 = (TIA1002001) tia;
        return convertTo100002Xml(tia1002001);
    }

    private String convertTo100002Xml(TIA1002001 tia1002001) {
        T100002Tia tia = new T100002Tia();
        tia.INFO = new T100002Tia.TiaHeader();
        tia.INFO.TRX_CODE = "100002";
        tia.INFO.REQ_SN = tia1002001.header.REQ_SN;
        tia.BODY = new T100002Tia.Body();
        tia.BODY.TRANS_SUM = new T100002Tia.Body.BodyHeader();

        //商户协议选择 （包括测试环境）
        if (DEP_IS_RUNNING_DEBUG) {
            tia.INFO.USER_NAME = PropertyManager.getProperty("unionpay_user_name_TEST");
            tia.INFO.USER_PASS = PropertyManager.getProperty("unionpay_user_pass_TEST");
            tia.BODY.TRANS_SUM.BUSINESS_CODE = PropertyManager.getProperty("unionpay_business_code_TEST");
            tia.BODY.TRANS_SUM.MERCHANT_ID = PropertyManager.getProperty("unionpay_merchant_id_TEST");
        } else {
            String bizID = tia1002001.header.BIZ_ID.toUpperCase();
            tia.INFO.USER_NAME = PropertyManager.getProperty("unionpay_user_name_" + bizID);
            tia.INFO.USER_PASS = PropertyManager.getProperty("unionpay_user_pass_" + bizID);
            tia.BODY.TRANS_SUM.BUSINESS_CODE = PropertyManager.getProperty("unionpay_business_code_DF_" + bizID);
            tia.BODY.TRANS_SUM.MERCHANT_ID = PropertyManager.getProperty("unionpay_merchant_id_" + bizID);
        }
        tia.BODY.TRANS_SUM.SUBMIT_TIME = DateUtils.getDatetime14();

        long amount = new BigDecimal(tia1002001.body.TRANS_SUM.TOTAL_SUM).multiply(new BigDecimal("100")).longValue();
        tia.BODY.TRANS_SUM.TOTAL_SUM = String.valueOf(amount);
        tia.BODY.TRANS_SUM.TOTAL_ITEM = tia1002001.body.TRANS_SUM.TOTAL_ITEM;
        tia.BODY.TRANS_DETAILS = new ArrayList<T100002Tia.Body.BodyDetail>();

        for(TIA1002001.Body.BodyDetail bodyDetail : tia1002001.body.TRANS_DETAILS) {
            T100002Tia.Body.BodyDetail detail = new T100002Tia.Body.BodyDetail();
            try {
//                BeanUtils.copyProperties(detail, bodyDetail);
                detail.BANK_CODE = bodyDetail.BANK_CODE;
                detail.ACCOUNT_TYPE = bodyDetail.ACCOUNT_TYPE;
                detail.ACCOUNT_NO = bodyDetail.ACCOUNT_NO;
                detail.ACCOUNT_NAME = bodyDetail.ACCOUNT_NAME;
                detail.ACCOUNT_PROP = bodyDetail.ACCOUNT_PROP;
                detail.PROVINCE = bodyDetail.PROVINCE;
                detail.CITY = bodyDetail.CITY;
                detail.REMARK = bodyDetail.REMARK;
                detail.RESERVE1 = bodyDetail.RESERVE1;
                logger.info(detail.ACCOUNT_NAME+detail.ACCOUNT_NO);
                detail.AMOUNT = String.valueOf(new BigDecimal(bodyDetail.AMOUNT).multiply(new BigDecimal("100")).longValue());
            } catch (Exception e) {
                logger.error("BeanUtils.copyProperties 赋值错误。", e);
                throw new RuntimeException("BeanUtils.copyProperties 赋值错误。");
            }
            tia.BODY.TRANS_DETAILS.add(detail);
        }


        return tia.toString();
    }
}
