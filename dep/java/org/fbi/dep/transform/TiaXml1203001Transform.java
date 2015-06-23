package org.fbi.dep.transform;

import org.fbi.dep.model.txn.TiaXml1003001;
import org.fbi.dep.util.PropertyManager;
import org.fbi.endpoint.unionpay.txn.domain.T200001Tia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 13-4-2
 * Time: 下午11:04
 * To change this template use File | Settings | File Templates.
 */
/*
银联 1003001 -> 200001
 */
public class TiaXml1203001Transform extends AbstractTiaXmlTransform {
    private static Logger logger = LoggerFactory.getLogger(TiaXml1003001Transform.class);

    public String transform(String xml) {
        // xml -> bean
        TiaXml1003001 xml1003001 = (TiaXml1003001)(new TiaXml1003001().getTia(xml));
        // bean -> unionpay bean
        T200001Tia tia = new T200001Tia();
        tia.INFO = new T200001Tia.TiaHeader();
        tia.INFO.TRX_CODE = "200001";
        tia.INFO.REQ_SN = xml1003001.INFO.REQ_SN;

        tia.BODY = new T200001Tia.Body();

        //商户协议选择 （包括测试环境）
        if (DEP_IS_RUNNING_DEBUG) {
            tia.INFO.USER_NAME = PropertyManager.getProperty("unionpay_user_name_TEST");
            tia.INFO.USER_PASS = PropertyManager.getProperty("unionpay_user_pass_TEST");
        } else {
            String bizID = xml1003001.INFO.WSYS_ID.toUpperCase();
            tia.INFO.USER_NAME = PropertyManager.getProperty("unionpay_user_name_" + bizID);
            tia.INFO.USER_PASS = PropertyManager.getProperty("unionpay_user_pass_" + bizID);
        }
        tia.BODY.QUERY_TRANS = new T200001Tia.Body.BodyHeader();
        tia.BODY.QUERY_TRANS.QUERY_SN = xml1003001.BODY.QUERY_TRANS.QUERY_SN;
        tia.BODY.QUERY_TRANS.QUERY_REMARK = xml1003001.BODY.QUERY_TRANS.QUERY_REMARK;
        // unionpay bean -> unionpay xml
        return tia.toString();
    }
}
