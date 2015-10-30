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
 * 通知存款-存入同时自动建立账号交易9009503
 * (对应SBS-交易)
 */

@XStreamAlias("ROOT")
public class ToaXml9009503 extends ToaXml {
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
        private String TXN_CODE; // 交易代码（固定值：9009503）
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
        private String CUSIDT; //客户号
        private String OURREF; //摘要
        private String TXNDAT; //交易日期（固定长度 yyyyMMdd）
        private String ORGNAM; //机构名
        private String ACTNAM; //户名
        private String BOKNUM; //帐户号
        private String VALDAT; //开户日（固定长度 yyyyMMdd）
        private String EXPDAT; //到期日（固定长度 yyyyMMdd）
        private String INTCUR; //币别
        private String TXNAMT; //金额（##############9.99）
        private String DPTTYP; //存款种类
        private String DPTPRD; //存期
        private String INTRAT; //利率（##9.999999）

        public String getCUSIDT() {
            return CUSIDT;
        }

        public void setCUSIDT(String CUSIDT) {
            this.CUSIDT = CUSIDT;
        }

        public String getOURREF() {
            return OURREF;
        }

        public void setOURREF(String OURREF) {
            this.OURREF = OURREF;
        }

        public String getTXNDAT() {
            return TXNDAT;
        }

        public void setTXNDAT(String TXNDAT) {
            this.TXNDAT = TXNDAT;
        }

        public String getORGNAM() {
            return ORGNAM;
        }

        public void setORGNAM(String ORGNAM) {
            this.ORGNAM = ORGNAM;
        }

        public String getACTNAM() {
            return ACTNAM;
        }

        public void setACTNAM(String ACTNAM) {
            this.ACTNAM = ACTNAM;
        }

        public String getBOKNUM() {
            return BOKNUM;
        }

        public void setBOKNUM(String BOKNUM) {
            this.BOKNUM = BOKNUM;
        }

        public String getVALDAT() {
            return VALDAT;
        }

        public void setVALDAT(String VALDAT) {
            this.VALDAT = VALDAT;
        }

        public String getEXPDAT() {
            return EXPDAT;
        }

        public void setEXPDAT(String EXPDAT) {
            this.EXPDAT = EXPDAT;
        }

        public String getINTCUR() {
            return INTCUR;
        }

        public void setINTCUR(String INTCUR) {
            this.INTCUR = INTCUR;
        }

        public String getTXNAMT() {
            return TXNAMT;
        }

        public void setTXNAMT(String TXNAMT) {
            this.TXNAMT = TXNAMT;
        }

        public String getDPTTYP() {
            return DPTTYP;
        }

        public void setDPTTYP(String DPTTYP) {
            this.DPTTYP = DPTTYP;
        }

        public String getDPTPRD() {
            return DPTPRD;
        }

        public void setDPTPRD(String DPTPRD) {
            this.DPTPRD = DPTPRD;
        }

        public String getINTRAT() {
            return INTRAT;
        }

        public void setINTRAT(String INTRAT) {
            this.INTRAT = INTRAT;
        }
    }

    @Override
    public String toString() {
        this.INFO.setTXN_CODE("9009503");
        XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("$", "_");
        HierarchicalStreamDriver hierarchicalStreamDriver = new XppDriver(replacer);
        XStream xs = new XStream(hierarchicalStreamDriver);
        xs.processAnnotations(ToaXml9009503.class);
        return "<?xml version=\"1.0\" encoding=\"GBK\"?>" + "\n" + xs.toXML(this);
    }

    public static ToaXml9009503 getToa(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(ToaXml9009503.class);
        return (ToaXml9009503) xs.fromXML(xml);
    }
}
