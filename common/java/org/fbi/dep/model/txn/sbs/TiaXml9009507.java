package org.fbi.dep.model.txn.sbs;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.fbi.dep.model.base.TiaXml;

import java.io.Serializable;

/**
 * 根据证件查询-通知存款提款通知9009507
 * (对应SBS-a113交易)
 * 本交易用于七天通知存款提取款项前的提款通知，客户办理通知时应填写通知单
 */

@XStreamAlias("ROOT")
public class TiaXml9009507 extends TiaXml {
    private Info INFO;
    private Body BODY;

    public Body getBODY() {
        return BODY;
    }

    public void setBODY(Body BODY) {
        this.BODY = BODY;
    }

    public Info getINFO() {
        return INFO;
    }

    public void setINFO(Info INFO) {
        this.INFO = INFO;
    }

    public static class Info implements Serializable {
        private String TXN_CODE; // 交易代码（固定值：9009507）
        private String REQ_SN;   // 交易流水号

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
    }

    public static class Body implements Serializable{
        private String BOKNUM; // 账户号
        private String ADVFLG; // 通知状态（1-通知,2-取款,3-撤销）
        private String VALDAT; // 通知日期
        private String SGNDAT; // 协定取款日
        private String CUSPW1; // 起始序号
        private String PASTYP; // 证件种类
        private String PASSNO; // 证件号

        public String getBOKNUM() {
            return BOKNUM;
        }

        public void setBOKNUM(String BOKNUM) {
            this.BOKNUM = BOKNUM;
        }

        public String getADVFLG() {
            return ADVFLG;
        }

        public void setADVFLG(String ADVFLG) {
            this.ADVFLG = ADVFLG;
        }

        public String getVALDAT() {
            return VALDAT;
        }

        public void setVALDAT(String VALDAT) {
            this.VALDAT = VALDAT;
        }

        public String getSGNDAT() {
            return SGNDAT;
        }

        public void setSGNDAT(String SGNDAT) {
            this.SGNDAT = SGNDAT;
        }

        public String getCUSPW1() {
            return CUSPW1;
        }

        public void setCUSPW1(String CUSPW1) {
            this.CUSPW1 = CUSPW1;
        }

        public String getPASTYP() {
            return PASTYP;
        }

        public void setPASTYP(String PASTYP) {
            this.PASTYP = PASTYP;
        }

        public String getPASSNO() {
            return PASSNO;
        }

        public void setPASSNO(String PASSNO) {
            this.PASSNO = PASSNO;
        }
    }

    @Override
    public TiaXml getTia(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(TiaXml9009507.class);
        return (TiaXml9009507) xs.fromXML(xml);
    }
}
