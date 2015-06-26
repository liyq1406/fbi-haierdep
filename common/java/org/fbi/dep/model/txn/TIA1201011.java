package org.fbi.dep.model.txn;

import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.base.TIABody;
import org.fbi.dep.model.base.TIAHeader;

/**
 * 通联：实时代扣 单笔
 * User: wanglichao
 * Date: 2015-06-25
 */

public class TIA1201011 extends TIA {
    public Header header = new Header();
    public Body body = new Body();


    @Override
    public TIAHeader getHeader() {
        return header;
    }

    @Override
    public TIABody getBody() {
        return body;
    }

    //====================================================================
    public static class Header extends TIAHeader {
    }

    public static class Body extends TIABody {
        public String BUSINESS_CODE;            // 业务代码
        public String MERCHANT_ID;              // 商户代码
        public String SUBMIT_TIME;               // 提交时间
        public String E_USER_CODE;              // 用户编号

        public String VALIDATE;                 // 有效期
        public String CVV2;                     // 信用卡CVV2
        public String BANK_CODE;                // 银行代码
        public String ACCOUNT_TYPE;              // 账号类型
        public String ACCOUNT_NO;                 // 账号
        public String ACCOUNT_NAME;              //账号名
        public String ACCOUNT_PROP;              // 账号属性
        public String AMOUNT;                    // 金额
        public String CURRENCY;                    // 货币类型
        public String ID_TYPE;                    // 开户证件类型
        public String ID;                    // 证件号
        public String SETTACCT;                    // 本交易结算户
        public String TEL;                    // 手机号/小灵通
        public String CUST_USERID;                    // 自定义用户号
        public String SETTGROUPFLAG;                    // 分组清算标志
        public String SUMMARY;                    // 交易附言
        public String REMARK;                    // 备注
    }
}
