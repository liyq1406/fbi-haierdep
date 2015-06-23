package org.fbi.dep.transform;

import org.apache.commons.beanutils.BeanUtils;
import org.fbi.dep.model.txn.TiaXml1002001;
import org.fbi.dep.util.DateUtils;
import org.fbi.dep.util.PropertyManager;
import org.fbi.endpoint.unionpay.txn.domain.T100002Tia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 13-4-2
 * Time: 下午11:04
 * To change this template use File | Settings | File Templates.
 */
/*
银联 1002001 -> 100002
// **** SBS_ACCOUNT_NO sbs账户赋值到100002报文中的RESERVE2字段 *******
 */
public class TiaXml1202001Transform extends AbstractTiaXmlTransform {
    private static Logger logger = LoggerFactory.getLogger(TiaXml1002001Transform.class);

    public String transform(String xml) {
        // xml -> bean
        TiaXml1002001 xml1002001 = (TiaXml1002001) (new TiaXml1002001().getTia(xml));
        // bean -> unionpay bean
        T100002Tia tia = new T100002Tia();
        tia.INFO = new T100002Tia.TiaHeader();
        tia.INFO.TRX_CODE = "100002";
        tia.INFO.REQ_SN = xml1002001.INFO.REQ_SN;

        tia.BODY = new T100002Tia.Body();

        tia.BODY.TRANS_SUM = new T100002Tia.Body.BodyHeader();
        tia.BODY.TRANS_SUM.TOTAL_ITEM = xml1002001.BODY.TRANS_SUM.TOTAL_ITEM;
        tia.BODY.TRANS_SUM.TOTAL_SUM = xml1002001.BODY.TRANS_SUM.TOTAL_SUM;
        tia.BODY.TRANS_DETAILS = new ArrayList<T100002Tia.Body.BodyDetail>();

        //商户协议选择 （包括测试环境）
        if (DEP_IS_RUNNING_DEBUG) {
            tia.INFO.USER_NAME = PropertyManager.getProperty("unionpay_user_name_TEST");
            tia.INFO.USER_PASS = PropertyManager.getProperty("unionpay_user_pass_TEST");
            tia.BODY.TRANS_SUM.BUSINESS_CODE = PropertyManager.getProperty("unionpay_business_code_TEST");
            tia.BODY.TRANS_SUM.MERCHANT_ID = PropertyManager.getProperty("unionpay_merchant_id_TEST");
        } else {
            String bizID = xml1002001.INFO.WSYS_ID.toUpperCase();
            tia.INFO.USER_NAME = PropertyManager.getProperty("unionpay_user_name_" + bizID);
            tia.INFO.USER_PASS = PropertyManager.getProperty("unionpay_user_pass_" + bizID);
            tia.BODY.TRANS_SUM.BUSINESS_CODE = PropertyManager.getProperty("unionpay_business_code_" + bizID);
            tia.BODY.TRANS_SUM.MERCHANT_ID = PropertyManager.getProperty("unionpay_merchant_id_" + bizID);
        }
        tia.BODY.TRANS_SUM.SUBMIT_TIME = DateUtils.getDatetime14();

        for (TiaXml1002001.Body.BodyDetail bodyDetail : xml1002001.BODY.TRANS_DETAILS) {
            T100002Tia.Body.BodyDetail record = new T100002Tia.Body.BodyDetail();
            logger.info(bodyDetail.ACCOUNT_NAME + bodyDetail.ACCOUNT_NO);
            try {
                BeanUtils.copyProperties(record, bodyDetail);
               /* record.BANK_CODE = bodyDetail.BANK_CODE;
                record.ACCOUNT_TYPE = bodyDetail.ACCOUNT_TYPE;
                record.ACCOUNT_NO = bodyDetail.ACCOUNT_NO;
                record.ACCOUNT_NAME = bodyDetail.ACCOUNT_NAME;
                record.PROVINCE = bodyDetail.PROVINCE;
                record.CITY = bodyDetail.CITY;
                record.ACCOUNT_PROP = bodyDetail.ACCOUNT_PROP;
                record.AMOUNT = bodyDetail.AMOUNT;*/

            } catch (Exception e) {
                logger.error("数据转换失败。", e);
                throw new RuntimeException("数据转换失败。交易：1002001");
            }
            record.RESERVE2 = bodyDetail.SBS_ACCOUNT_NO;
            tia.BODY.TRANS_DETAILS.add(record);
        }
        // unionpay bean -> unionpay xml
        return tia.toString();
    }
}
