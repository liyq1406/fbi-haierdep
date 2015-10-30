package org.fbi.dep.model.txn.sbs;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.fbi.dep.model.base.TiaXml;

import java.io.Serializable;

/**
 * 通知存款-支取结清9009506
 * (对应SBS-a13a交易)
 */

@XStreamAlias("ROOT")
public class TiaXml9009506 extends TiaXml {
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
        private String TXN_CODE; // 交易代码（固定值：9009506）
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
        private String ACTTY1; // 账号类型
        private String IPTAC1; // 账户号
        private String DRAMD1; // 取款方式
        private String CUSPW1; // 客户密码
        private String TXNDAT; // 交易日期（固定长度yyyyMMdd）
        private String ADVNUM; // 通知单号（有通知单号即通知存款，没有通知单号即没有通知）
        private String TXNAMT; // 交易金额
        private String ACTTY2; // 转入帐号类型（01.单位活期及内部账号）
        private String IPTAC2; // 转入帐号
        private String PASTYP; // 证件种类
        private String PASSNO; // 证件号码
        private String REMARK; // 摘要
        private String ANACDE; // 分类统计码
        private String MAGFL1; // 刷磁条读入帐号标志
        private String MAGFL2; // 备用字段
        private String BOKNUM; // 外围系统流水号

        public String getACTTY1() {
            return ACTTY1;
        }

        public void setACTTY1(String ACTTY1) {
            this.ACTTY1 = ACTTY1;
        }

        public String getIPTAC1() {
            return IPTAC1;
        }

        public void setIPTAC1(String IPTAC1) {
            this.IPTAC1 = IPTAC1;
        }

        public String getDRAMD1() {
            return DRAMD1;
        }

        public void setDRAMD1(String DRAMD1) {
            this.DRAMD1 = DRAMD1;
        }

        public String getCUSPW1() {
            return CUSPW1;
        }

        public void setCUSPW1(String CUSPW1) {
            this.CUSPW1 = CUSPW1;
        }

        public String getTXNDAT() {
            return TXNDAT;
        }

        public void setTXNDAT(String TXNDAT) {
            this.TXNDAT = TXNDAT;
        }

        public String getADVNUM() {
            return ADVNUM;
        }

        public void setADVNUM(String ADVNUM) {
            this.ADVNUM = ADVNUM;
        }

        public String getTXNAMT() {
            return TXNAMT;
        }

        public void setTXNAMT(String TXNAMT) {
            this.TXNAMT = TXNAMT;
        }

        public String getACTTY2() {
            return ACTTY2;
        }

        public void setACTTY2(String ACTTY2) {
            this.ACTTY2 = ACTTY2;
        }

        public String getIPTAC2() {
            return IPTAC2;
        }

        public void setIPTAC2(String IPTAC2) {
            this.IPTAC2 = IPTAC2;
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

        public String getREMARK() {
            return REMARK;
        }

        public void setREMARK(String REMARK) {
            this.REMARK = REMARK;
        }

        public String getANACDE() {
            return ANACDE;
        }

        public void setANACDE(String ANACDE) {
            this.ANACDE = ANACDE;
        }

        public String getMAGFL1() {
            return MAGFL1;
        }

        public void setMAGFL1(String MAGFL1) {
            this.MAGFL1 = MAGFL1;
        }

        public String getMAGFL2() {
            return MAGFL2;
        }

        public void setMAGFL2(String MAGFL2) {
            this.MAGFL2 = MAGFL2;
        }

        public String getBOKNUM() {
            return BOKNUM;
        }

        public void setBOKNUM(String BOKNUM) {
            this.BOKNUM = BOKNUM;
        }
    }

    @Override
    public TiaXml getTia(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(TiaXml9009506.class);
        return (TiaXml9009506) xs.fromXML(xml);
    }
}
