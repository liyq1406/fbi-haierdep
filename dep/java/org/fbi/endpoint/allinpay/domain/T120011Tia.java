package org.fbi.endpoint.allinpay.domain;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.fbi.endpoint.allinpay.core.ABaseTiaHeader;

/**
 * Created by Lichao.W At 2015/6/25 22:27
 * wanglichao@163.com
 * 通联实时代扣
 */
@XStreamAlias("AIPG")
public class T120011Tia {
    public static class TiaHeader extends ABaseTiaHeader {
    }

    public static class Body {
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

    public TiaHeader INFO;
    public Body TRANS;

    @Override
    public String toString() {
        XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("__", "_");
        HierarchicalStreamDriver hierarchicalStreamDriver = new XppDriver(replacer);
        XStream xs = new XStream(hierarchicalStreamDriver);
        xs.processAnnotations(T120011Tia.class);
        return "<?xml version=\"1.0\" encoding=\"GBK\"?>" + xs.toXML(this);
    }

    public static void main(String[] argv) {
        T120011Tia tia = new T120011Tia();
        tia.INFO = new TiaHeader();
        tia.INFO.TRX_CODE = "100011";
        tia.INFO.VERSION = "03";
        tia.INFO.DATA_TYPE = "2";
        tia.INFO.LEVEL = "5";
        tia.INFO.USER_NAME = "20060400000044502";
        tia.INFO.USER_PASS = "`12qwe";
        tia.INFO.REQ_SN = "" + System.currentTimeMillis();

        tia.TRANS = new Body();
        tia.TRANS.BUSINESS_CODE = "10600";
        tia.TRANS.MERCHANT_ID = "200581000000519";
        System.out.println(tia);
    }
}
