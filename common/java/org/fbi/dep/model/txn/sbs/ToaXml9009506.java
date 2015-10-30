package org.fbi.dep.model.txn.sbs;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.fbi.dep.model.base.ToaXml;

import java.io.Serializable;

/**
 * 通知存款-支取结清9009506
 * (对应SBS-a13a交易)
 */

@XStreamAlias("ROOT")
public class ToaXml9009506 extends ToaXml {
    private Info INFO = new Info();
    private Body BODY = new Body();

    public Info getINFO() {
        return INFO;
    }

    public void setINFO(Info INFO) {
        this.INFO = INFO;
    }

    public Body getBODY() {
        return BODY;
    }

    public void setBODY(Body BODY) {
        this.BODY = BODY;
    }

    public static class Info implements Serializable {
        private String TXN_CODE; // 交易代码（固定值：9009506）
        private String REQ_SN;   // 交易流水号
        private String RET_CODE; // 返回代码（0000：交易成功；1000：交易失败；2000：交易结果不明）
        private String RET_MSG;  // 返回信息

        public String getTXN_CODE() {
            return TXN_CODE;
        }

        public void setTXN_CODE(String TXN_CODE) {
            this.TXN_CODE = TXN_CODE;
        }

        public String getREQ_SN() {
            return REQ_SN;
        }

        public void setREQ_SN(String REQ_SN) {
            this.REQ_SN = REQ_SN;
        }

        public String getRET_CODE() {
            return RET_CODE;
        }

        public void setRET_CODE(String RET_CODE) {
            this.RET_CODE = RET_CODE;
        }

        public String getRET_MSG() {
            return RET_MSG;
        }

        public void setRET_MSG(String RET_MSG) {
            this.RET_MSG = RET_MSG;
        }
    }

    public static class Body implements Serializable{
        private String TELLER;  // 柜员代码
        private String TXNDAT;  // 交易日期（固定长度yyyyMMdd）
        private String VCHSET;  // 传票套号
        private String ACTTY;   // 账户类别
        private String IPTAC;   // 帐户号
        private String ACTNAM;  // 帐户名
        private String CCYNAM;  // 货币中文名称
        private String APCDE1;  // 期内
        private String TXNAMT1; // 取款金额（##############9.99）
        private String OPNIRT;  // 利率（##9.999999）
        private String ININT;   // 期内利息（##############9.99）
        private String APCDE2;  // 期外
        private String TXNAMT2; // 期外计息本金（##############9.99）
        private String SAVIRT;  // 期外利率（##9.999999）
        private String OUTINT;  // 期外利息（##############9.99）
        private String APCDE3;  // 备用
        private String TXNAMT3; // 备用（##############9.99）
        private String VALIRT;  // 备用（##9.999999）
        private String VALINT;  // 备用（##############9.99）
        private String APCDE4;  // 备用
        private String TXNAMT4; // 备用（##############9.99）
        private String FEERAT;  // 备用（##9.999999）
        private String FEEAMT;  // 备用（##############9.99）
        private String PIVINT1; // 利息总额（##############9.99）
        private String PIVINT2; // 计税利息（##############9.99）
        private String TAXRATE; // 税率（##9.999999）
        private String TAXAMT;  // 税额（##############9.99）
        private String TOTINT;  // 实付利息总额（##############9.99）
        private String DATHED;  // 起息日:
        private String VALDAT;  // 起息日期（固定长 yyyyMMdd）
        private String TOTAMT;  // 支付本息总金额（##############9.99）

        public String getTELLER() {
            return TELLER;
        }

        public void setTELLER(String TELLER) {
            this.TELLER = TELLER;
        }

        public String getTXNDAT() {
            return TXNDAT;
        }

        public void setTXNDAT(String TXNDAT) {
            this.TXNDAT = TXNDAT;
        }

        public String getVCHSET() {
            return VCHSET;
        }

        public void setVCHSET(String VCHSET) {
            this.VCHSET = VCHSET;
        }

        public String getACTTY() {
            return ACTTY;
        }

        public void setACTTY(String ACTTY) {
            this.ACTTY = ACTTY;
        }

        public String getIPTAC() {
            return IPTAC;
        }

        public void setIPTAC(String IPTAC) {
            this.IPTAC = IPTAC;
        }

        public String getACTNAM() {
            return ACTNAM;
        }

        public void setACTNAM(String ACTNAM) {
            this.ACTNAM = ACTNAM;
        }

        public String getCCYNAM() {
            return CCYNAM;
        }

        public void setCCYNAM(String CCYNAM) {
            this.CCYNAM = CCYNAM;
        }

        public String getAPCDE1() {
            return APCDE1;
        }

        public void setAPCDE1(String APCDE1) {
            this.APCDE1 = APCDE1;
        }

        public String getTXNAMT1() {
            return TXNAMT1;
        }

        public void setTXNAMT1(String TXNAMT1) {
            this.TXNAMT1 = TXNAMT1;
        }

        public String getOPNIRT() {
            return OPNIRT;
        }

        public void setOPNIRT(String OPNIRT) {
            this.OPNIRT = OPNIRT;
        }

        public String getININT() {
            return ININT;
        }

        public void setININT(String ININT) {
            this.ININT = ININT;
        }

        public String getAPCDE2() {
            return APCDE2;
        }

        public void setAPCDE2(String APCDE2) {
            this.APCDE2 = APCDE2;
        }

        public String getTXNAMT2() {
            return TXNAMT2;
        }

        public void setTXNAMT2(String TXNAMT2) {
            this.TXNAMT2 = TXNAMT2;
        }

        public String getSAVIRT() {
            return SAVIRT;
        }

        public void setSAVIRT(String SAVIRT) {
            this.SAVIRT = SAVIRT;
        }

        public String getOUTINT() {
            return OUTINT;
        }

        public void setOUTINT(String OUTINT) {
            this.OUTINT = OUTINT;
        }

        public String getAPCDE3() {
            return APCDE3;
        }

        public void setAPCDE3(String APCDE3) {
            this.APCDE3 = APCDE3;
        }

        public String getTXNAMT3() {
            return TXNAMT3;
        }

        public void setTXNAMT3(String TXNAMT3) {
            this.TXNAMT3 = TXNAMT3;
        }

        public String getVALIRT() {
            return VALIRT;
        }

        public void setVALIRT(String VALIRT) {
            this.VALIRT = VALIRT;
        }

        public String getVALINT() {
            return VALINT;
        }

        public void setVALINT(String VALINT) {
            this.VALINT = VALINT;
        }

        public String getAPCDE4() {
            return APCDE4;
        }

        public void setAPCDE4(String APCDE4) {
            this.APCDE4 = APCDE4;
        }

        public String getTXNAMT4() {
            return TXNAMT4;
        }

        public void setTXNAMT4(String TXNAMT4) {
            this.TXNAMT4 = TXNAMT4;
        }

        public String getFEERAT() {
            return FEERAT;
        }

        public void setFEERAT(String FEERAT) {
            this.FEERAT = FEERAT;
        }

        public String getFEEAMT() {
            return FEEAMT;
        }

        public void setFEEAMT(String FEEAMT) {
            this.FEEAMT = FEEAMT;
        }

        public String getPIVINT1() {
            return PIVINT1;
        }

        public void setPIVINT1(String PIVINT1) {
            this.PIVINT1 = PIVINT1;
        }

        public String getPIVINT2() {
            return PIVINT2;
        }

        public void setPIVINT2(String PIVINT2) {
            this.PIVINT2 = PIVINT2;
        }

        public String getTAXRATE() {
            return TAXRATE;
        }

        public void setTAXRATE(String TAXRATE) {
            this.TAXRATE = TAXRATE;
        }

        public String getTAXAMT() {
            return TAXAMT;
        }

        public void setTAXAMT(String TAXAMT) {
            this.TAXAMT = TAXAMT;
        }

        public String getTOTINT() {
            return TOTINT;
        }

        public void setTOTINT(String TOTINT) {
            this.TOTINT = TOTINT;
        }

        public String getDATHED() {
            return DATHED;
        }

        public void setDATHED(String DATHED) {
            this.DATHED = DATHED;
        }

        public String getVALDAT() {
            return VALDAT;
        }

        public void setVALDAT(String VALDAT) {
            this.VALDAT = VALDAT;
        }

        public String getTOTAMT() {
            return TOTAMT;
        }

        public void setTOTAMT(String TOTAMT) {
            this.TOTAMT = TOTAMT;
        }
    }

    @Override
    public String toString() {
        this.INFO.setTXN_CODE("9009506");
        XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("$", "_");
        HierarchicalStreamDriver hierarchicalStreamDriver = new XppDriver(replacer);
        XStream xs = new XStream(hierarchicalStreamDriver);
        xs.processAnnotations(ToaXml9009506.class);
        return "<?xml version=\"1.0\" encoding=\"GBK\"?>" + "\n" + xs.toXML(this);
    }

    public static ToaXml9009506 getToa(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(ToaXml9009506.class);
        return (ToaXml9009506) xs.fromXML(xml);
    }
}
