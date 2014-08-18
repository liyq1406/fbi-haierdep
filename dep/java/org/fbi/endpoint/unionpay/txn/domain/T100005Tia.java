package org.fbi.endpoint.unionpay.txn.domain;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.fbi.endpoint.unionpay.core.BaseTiaHeader;

import java.util.ArrayList;
import java.util.List;

/**
 * zhangxiaobo 2013-03-28
 */
@XStreamAlias("GZELINK")
public class T100005Tia {
    public static class TiaHeader extends BaseTiaHeader {
    }

    public static class Body {
        public BodyHeader TRANS_SUM;
        public List<BodyDetail> TRANS_DETAILS;

        public static class BodyHeader {
            public String BUSINESS_CODE = "";
            public String MERCHANT_ID = "";
            public String SUBMIT_TIME = "";
            public String TOTAL_ITEM = "1";
            public String TOTAL_SUM = "0";
        }

        @XStreamAlias("TRANS_DETAIL")
        public static class BodyDetail {
            public String SN = "0001";
            public String E_USER_CODE = "";//
            public String BANK_CODE = "";
            public String ACCOUNT_TYPE = ""; //
            public String ACCOUNT_NO = "";
            public String ACCOUNT_NAME = "";
            public String PROVINCE = "";
            public String CITY = "";
            public String BANK_NAME = "";
            public String ACCOUNT_PROP = "0";   //0私人，1公司。不填时，默认为私人0。
            public String AMOUNT = "";   //整数，单位分
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

            public String getSN() {
                return SN;
            }

            public void setSN(String SN) {
                this.SN = SN;
            }

            public String getE_USER_CODE() {
                return E_USER_CODE;
            }

            public void setE_USER_CODE(String e_USER_CODE) {
                E_USER_CODE = e_USER_CODE;
            }

            public String getBANK_CODE() {
                return BANK_CODE;
            }

            public void setBANK_CODE(String BANK_CODE) {
                this.BANK_CODE = BANK_CODE;
            }

            public String getACCOUNT_TYPE() {
                return ACCOUNT_TYPE;
            }

            public void setACCOUNT_TYPE(String ACCOUNT_TYPE) {
                this.ACCOUNT_TYPE = ACCOUNT_TYPE;
            }

            public String getACCOUNT_NO() {
                return ACCOUNT_NO;
            }

            public void setACCOUNT_NO(String ACCOUNT_NO) {
                this.ACCOUNT_NO = ACCOUNT_NO;
            }

            public String getACCOUNT_NAME() {
                return ACCOUNT_NAME;
            }

            public void setACCOUNT_NAME(String ACCOUNT_NAME) {
                this.ACCOUNT_NAME = ACCOUNT_NAME;
            }

            public String getPROVINCE() {
                return PROVINCE;
            }

            public void setPROVINCE(String PROVINCE) {
                this.PROVINCE = PROVINCE;
            }

            public String getCITY() {
                return CITY;
            }

            public void setCITY(String CITY) {
                this.CITY = CITY;
            }

            public String getBANK_NAME() {
                return BANK_NAME;
            }

            public void setBANK_NAME(String BANK_NAME) {
                this.BANK_NAME = BANK_NAME;
            }

            public String getACCOUNT_PROP() {
                return ACCOUNT_PROP;
            }

            public void setACCOUNT_PROP(String ACCOUNT_PROP) {
                this.ACCOUNT_PROP = ACCOUNT_PROP;
            }

            public String getAMOUNT() {
                return AMOUNT;
            }

            public void setAMOUNT(String AMOUNT) {
                this.AMOUNT = AMOUNT;
            }

            public String getCURRENCY() {
                return CURRENCY;
            }

            public void setCURRENCY(String CURRENCY) {
                this.CURRENCY = CURRENCY;
            }

            public String getPROTOCOL() {
                return PROTOCOL;
            }

            public void setPROTOCOL(String PROTOCOL) {
                this.PROTOCOL = PROTOCOL;
            }

            public String getPROTOCOL_USERID() {
                return PROTOCOL_USERID;
            }

            public void setPROTOCOL_USERID(String PROTOCOL_USERID) {
                this.PROTOCOL_USERID = PROTOCOL_USERID;
            }

            public String getID_TYPE() {
                return ID_TYPE;
            }

            public void setID_TYPE(String ID_TYPE) {
                this.ID_TYPE = ID_TYPE;
            }

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getTEL() {
                return TEL;
            }

            public void setTEL(String TEL) {
                this.TEL = TEL;
            }

            public String getRECKON_ACCOUNT() {
                return RECKON_ACCOUNT;
            }

            public void setRECKON_ACCOUNT(String RECKON_ACCOUNT) {
                this.RECKON_ACCOUNT = RECKON_ACCOUNT;
            }

            public String getCUST_USERID() {
                return CUST_USERID;
            }

            public void setCUST_USERID(String CUST_USERID) {
                this.CUST_USERID = CUST_USERID;
            }

            public String getREMARK() {
                return REMARK;
            }

            public void setREMARK(String REMARK) {
                this.REMARK = REMARK;
            }

            public String getRESERVE1() {
                return RESERVE1;
            }

            public void setRESERVE1(String RESERVE1) {
                this.RESERVE1 = RESERVE1;
            }

            public String getRESERVE2() {
                return RESERVE2;
            }

            public void setRESERVE2(String RESERVE2) {
                this.RESERVE2 = RESERVE2;
            }
        }
    }

    public TiaHeader INFO;
    public Body BODY;

    @Override
    public String toString() {
        this.INFO.TRX_CODE = "100005";
        this.INFO.LEVEL = "0";
        XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("$", "_");
        HierarchicalStreamDriver hierarchicalStreamDriver = new XppDriver(replacer);
        XStream xs = new XStream(hierarchicalStreamDriver);
        xs.processAnnotations(T100005Tia.class);
        return "<?xml version=\"1.0\" encoding=\"GBK\"?>" + "\n" + xs.toXML(this);
    }

    public static void main(String[] argv) {
        T100005Tia tia = new T100005Tia();
        tia.INFO = new TiaHeader();
        tia.INFO.TRX_CODE = "100005";
        tia.INFO.REQ_SN = "" + System.currentTimeMillis();

        tia.BODY = new Body();
        tia.BODY.TRANS_SUM = new Body.BodyHeader();

        tia.BODY.TRANS_DETAILS = new ArrayList<Body.BodyDetail>();

        // TODO add records
        Body.BodyDetail record = new Body.BodyDetail();
        tia.BODY.TRANS_DETAILS.add(record);
        record = new Body.BodyDetail();
        tia.BODY.TRANS_DETAILS.add(record);

        System.out.println(tia);
    }
}
