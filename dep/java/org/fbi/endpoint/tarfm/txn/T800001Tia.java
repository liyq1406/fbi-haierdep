package org.fbi.endpoint.tarfm.txn;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.fbi.endpoint.unionpay.core.BaseTiaHeader;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-7-25
 * Time: ����9:27
 * To change this template use File | Settings | File Templates.
 */
public class T800001Tia {
    public TiaHeader INFO = new TiaHeader();
    public Body BODY = new Body();

    public static class TiaHeader extends BaseTiaHeader {
    }

    public static class Body {
        public BodyHeader TRANS_SUM = new BodyHeader();
//        public List<BodyDetail> TRANS_DETAILS = new ArrayList<BodyDetail>();

        public static class BodyHeader {
            public String BUSINESS_CODE = "";
            public String MERCHANT_ID = "";
            public String SUBMIT_TIME = "";
            public String TOTAL_ITEM = "1";
            public String TOTAL_SUM = "1";
        }

        public static class BodyDetail {
            public String SN = "001";
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
            public String RECKON_ACCOUNT = "";
            public String CUST_USERID = "";
            public String REMARK = "";
            public String RESERVE1 = "";
            public String RESERVE2 = "";
        }
    }

    @Override
    public String toString() {
        XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("$", "_");
        HierarchicalStreamDriver hierarchicalStreamDriver = new XppDriver(replacer);
        XStream xs = new XStream(hierarchicalStreamDriver);
        xs.processAnnotations(T800001Tia.class);
        return xs.toXML(this);
    }

    public static void main(String[] argv) {
        T800001Tia tia = new T800001Tia();
        tia.INFO = new TiaHeader();
        tia.INFO.TRX_CODE = "800001";
        tia.INFO.REQ_SN = "" + System.currentTimeMillis();

        tia.BODY = new Body();
        tia.BODY.TRANS_SUM = new Body.BodyHeader();

        System.out.println(tia.toString());
    }
}
