package org.fbi.endpoint.allinpay.domain;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.fbi.endpoint.allinpay.core.ABaseTiaHeader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lichao.W At 2015/6/25 22:27
 * wanglichao@163.com
 * 通联批量代扣
 */
@XStreamAlias("AIPG")
public class T120001Tia {
    public TiaHeader INFO = new TiaHeader();
    public Body BODY = new Body();

    public static class TiaHeader extends ABaseTiaHeader {
    }

    public static class Body {
        public BodyHeader TRANS_SUM = new BodyHeader();
        public List<T120001Tia.Body.BodyDetail> TRANS_DETAILS = new ArrayList<>();

        public static class BodyHeader {
            public String BUSINESS_CODE = "";
            public String MERCHANT_ID = "";
            public String SUBMIT_TIME = "";
            public String TOTAL_ITEM = "1";
            public String TOTAL_SUM = "000";
        }

        @XStreamAlias("TRANS_DETAIL")
        public static class BodyDetail {
            public String SN = "";
            public String E_USER_CODE = "";
            public String BANK_CODE = "";
            public String ACCOUNT_TYPE = "";
            public String ACCOUNT_NO = "";
            public String ACCOUNT_NAME = "";
            public String PROVINCE = "0";
            public String CITY = "";
            public String BANK_NAME = "";
            public String ACCOUNT_PROP = "";
            public String AMOUNT = "";
            public String CURRENCY = "";
            public String PROTOCOL = "";
            public String PROTOCOL_USERID = "";
            public String ID_TYPE = "";
            public String ID = "";
            public String TEL = "";
            public String CUST_USERID = "";
            public String SETTACCT = "";
            public String REMARK = "";
            public String SETTGROUPFLAG = "";
            public String SUMMARY = "";
            public String UNION_BANK = "";

        }
    }

    @Override
    public String toString() {
        XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("__", "_");
        HierarchicalStreamDriver hierarchicalStreamDriver = new XppDriver(replacer);
        XStream xs = new XStream(hierarchicalStreamDriver);
        xs.processAnnotations(T120001Tia.class);
        return "<?xml version=\"1.0\" encoding=\"GBK\"?>" + xs.toXML(this);

    }

    public static void main(String[] argv) {
        T120001Tia tia = new T120001Tia();
        tia.INFO = new TiaHeader();
        tia.INFO.TRX_CODE = "100001"; //通联批量代扣交易码
        tia.INFO.REQ_SN = "" + System.currentTimeMillis();

        tia.BODY.TRANS_DETAILS = new ArrayList<>();

        T120001Tia.Body.BodyDetail record = new T120001Tia.Body.BodyDetail();
        tia.BODY.TRANS_DETAILS.add(record);

        System.out.println(tia.toString());
    }
}
