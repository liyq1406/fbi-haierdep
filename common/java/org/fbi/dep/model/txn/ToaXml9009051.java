package org.fbi.dep.model.txn;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.fbi.dep.model.base.ToaXml;
import org.fbi.dep.model.base.ToaXmlInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XIANGYANG on 2015-5-21.
 * 账户当日交易明细查询
 */

@XStreamAlias("ROOT")
public class ToaXml9009051 extends ToaXml {
    public ToaXmlInfo INFO = new ToaXmlInfo();
    public Body BODY = new Body();

    public static class Body implements Serializable {
        public String TOTCNT;       // 总记录数
        public String CURCNT;       // 本包内记录数
        public String DBTCNT;       // 借方总笔数
        public String DBTAMT;       // 借方总金额
        public String CRTCNT;       // 贷方总笔数
        public String CRTAMT;       // 贷方总金额
        public List<BodyDetail> DETAILS;

        @XStreamAlias("DETAIL")
        public static class BodyDetail implements Serializable {

            public String CUSIDT;       // 客户号
            public String APCODE;       // 核算码
            public String CURCDE;       // 货币号
            public String STMDAT;       // 交易日期
            public String TLRNUM;       // 交易柜员
            public String VCHSET;       // 传票套号
            public String SETSEQ;       // 传票套内序号
            public String TXNAMT;       // 交易金额
            public String LASBAL;       // 交易后余额
            public String FURINF;       // 摘要

            public String getCUSIDT() {
                return CUSIDT;
            }

            public void setCUSIDT(String CUSIDT) {
                this.CUSIDT = CUSIDT;
            }

            public String getAPCODE() {
                return APCODE;
            }

            public void setAPCODE(String APCODE) {
                this.APCODE = APCODE;
            }

            public String getCURCDE() {
                return CURCDE;
            }

            public void setCURCDE(String CURCDE) {
                this.CURCDE = CURCDE;
            }

            public String getSTMDAT() {
                return STMDAT;
            }

            public void setSTMDAT(String STMDAT) {
                this.STMDAT = STMDAT;
            }

            public String getTLRNUM() {
                return TLRNUM;
            }

            public void setTLRNUM(String TLRNUM) {
                this.TLRNUM = TLRNUM;
            }

            public String getVCHSET() {
                return VCHSET;
            }

            public void setVCHSET(String VCHSET) {
                this.VCHSET = VCHSET;
            }

            public String getSETSEQ() {
                return SETSEQ;
            }

            public void setSETSEQ(String SETSEQ) {
                this.SETSEQ = SETSEQ;
            }

            public String getTXNAMT() {
                return TXNAMT;
            }

            public void setTXNAMT(String TXNAMT) {
                this.TXNAMT = TXNAMT;
            }

            public String getLASBAL() {
                return LASBAL;
            }

            public void setLASBAL(String LASBAL) {
                this.LASBAL = LASBAL;
            }

            public String getFURINF() {
                return FURINF;
            }

            public void setFURINF(String FURINF) {
                this.FURINF = FURINF;
            }
        }
    }

    @Override
    public String toString() {
        this.INFO.TXN_CODE = "9009051";
        XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("$", "_");
        HierarchicalStreamDriver hierarchicalStreamDriver = new XppDriver(replacer);
        XStream xs = new XStream(hierarchicalStreamDriver);
        xs.processAnnotations(ToaXml9009051.class);
        return "<?xml version=\"1.0\" encoding=\"GBK\"?>" + "\n" + xs.toXML(this);
    }

    public static ToaXml9009051 getToa(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(ToaXml9009051.class);
        return (ToaXml9009051) xs.fromXML(xml);
    }
}
