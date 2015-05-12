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

/**
 * Created by XIANGYANG on 2015-5-11.
 * 根据账号查询账户信息
 */

@XStreamAlias("ROOT")
public class ToaXml9009062 extends ToaXml {
    public ToaXmlInfo INFO = new ToaXmlInfo();
    public Body BODY = new Body();

    public static class Body implements Serializable {
        public String ORGIDT;  //机构号
        public String ACTNUM;  //账户号
        public String ACTNAM;  //账户名
        public String BOKBAL;  //账户余额
        public String AVABAL;  //有效余额
        public String DIFBAL;  //借方不计息余额
        public String CIFBAL;  //贷方不计息余额
        public String MAVBAL;  //月平均余额
        public String YAVBAL;  //年平均余额
        public String DDRAMT;  //本日借方发生额
        public String DCRAMT;  //本日贷方发生额
        public String DDRCNT;  //本日借方发生数
        public String DCRCNT;  //本日贷方发生数
        public String MDRAMT;  //月累计借方发生额
        public String MCRAMT;  //月累计贷方发生额
        public String MDRCNT;  //月累计借方发生数
        public String MCRCNT;  //月累计贷方发生数
        public String YDRAMT;  //年累计借方发生额
        public String YCRAMT;  //年累计贷方发生额
        public String YDRCNT;  //年累计借方发生数
        public String YCRCNT;  //年累计贷方发生数
        public String DINRAT;  //借方利率代码
        public String CINRAT;  //贷方利率代码
        public String DRATSF;  //借方固定（浮动）利率
        public String CRATSF;  //贷方固定（浮动）利率
        public String DACINT;  //应付利息
        public String CACINT;  //应收利息
        public String DAMINT;  //已摊销应付利息
        public String CAMINT;  //已摊销应收利息
        public String INTFLG;  //计息标志
        public String INTCYC;  //计息周期
        public String INTTRA;  //转息帐户
        public String LINTDT;  //末次计息日期
        public String LINDTH;  //末次转积数历史日期
        public String STMCYC;  //对帐单出帐周期码
        public String STMCDT;  //对帐单出帐周期日期
        public String STMFMT;  //对帐单帐页形式
        public String STMSHT;  //对帐单出帐份数
        public String STMDEP;  //对账单分发部门
        public String STMADD;  //对账单邮寄地址
        public String STMZIP;  //邮政编码
        public String LEGCYC;  //分户账出账周期
        public String LEGCDT;  //分户帐出帐周期日期
        public String LEGFMT;  //分户账帐页形式
        public String LEGSHT;  //分户帐出帐份数
        public String LEGDEP;  //分户账分发部门
        public String LEGADD;  //分户账邮寄地址
        public String LEGZIP;  //邮编
        public String ACTTYP;  //帐户类型
        public String ACTGLC;  //总账码
        public String ACTPLC;  //损益码
        public String DEPNUM;  //部门号
        public String LENTDT;  //末次记账日期
        public String DRACCM;  //本计息段借方积数
        public String CRACCM;  //本计息段贷方积数
        public String CQEFLG;  //支票存折标志
        public String BALLIM;  //协定帐户余额限
        public String OVELIM;  //透支限额
        public String OVEEXP;  //额度到期日
        public String OPNDAT;  //开户日期
        public String CLSDAT;  //销户日期
        public String REGSTS;  //挂失状态
        public String FRZSTS;  //冻结状态
        public String ACTSTS;  //帐户状态
        public String AMDTLR;  //修改柜员
        public String UPDDAT;  //最后修改日期
        public String RECSTS;  //记录状态

        public String getORGIDT() {
            return ORGIDT;
        }

        public void setORGIDT(String ORGIDT) {
            this.ORGIDT = ORGIDT;
        }

        public String getACTNUM() {
            return ACTNUM;
        }

        public void setACTNUM(String ACTNUM) {
            this.ACTNUM = ACTNUM;
        }

        public String getACTNAM() {
            return ACTNAM;
        }

        public void setACTNAM(String ACTNAM) {
            this.ACTNAM = ACTNAM;
        }

        public String getBOKBAL() {
            return BOKBAL;
        }

        public void setBOKBAL(String BOKBAL) {
            this.BOKBAL = BOKBAL;
        }

        public String getAVABAL() {
            return AVABAL;
        }

        public void setAVABAL(String AVABAL) {
            this.AVABAL = AVABAL;
        }

        public String getDIFBAL() {
            return DIFBAL;
        }

        public void setDIFBAL(String DIFBAL) {
            this.DIFBAL = DIFBAL;
        }

        public String getCIFBAL() {
            return CIFBAL;
        }

        public void setCIFBAL(String CIFBAL) {
            this.CIFBAL = CIFBAL;
        }

        public String getMAVBAL() {
            return MAVBAL;
        }

        public void setMAVBAL(String MAVBAL) {
            this.MAVBAL = MAVBAL;
        }

        public String getYAVBAL() {
            return YAVBAL;
        }

        public void setYAVBAL(String YAVBAL) {
            this.YAVBAL = YAVBAL;
        }

        public String getDDRAMT() {
            return DDRAMT;
        }

        public void setDDRAMT(String DDRAMT) {
            this.DDRAMT = DDRAMT;
        }

        public String getDCRAMT() {
            return DCRAMT;
        }

        public void setDCRAMT(String DCRAMT) {
            this.DCRAMT = DCRAMT;
        }

        public String getDDRCNT() {
            return DDRCNT;
        }

        public void setDDRCNT(String DDRCNT) {
            this.DDRCNT = DDRCNT;
        }

        public String getDCRCNT() {
            return DCRCNT;
        }

        public void setDCRCNT(String DCRCNT) {
            this.DCRCNT = DCRCNT;
        }

        public String getMDRAMT() {
            return MDRAMT;
        }

        public void setMDRAMT(String MDRAMT) {
            this.MDRAMT = MDRAMT;
        }

        public String getMCRAMT() {
            return MCRAMT;
        }

        public void setMCRAMT(String MCRAMT) {
            this.MCRAMT = MCRAMT;
        }

        public String getMDRCNT() {
            return MDRCNT;
        }

        public void setMDRCNT(String MDRCNT) {
            this.MDRCNT = MDRCNT;
        }

        public String getMCRCNT() {
            return MCRCNT;
        }

        public void setMCRCNT(String MCRCNT) {
            this.MCRCNT = MCRCNT;
        }

        public String getYDRAMT() {
            return YDRAMT;
        }

        public void setYDRAMT(String YDRAMT) {
            this.YDRAMT = YDRAMT;
        }

        public String getYCRAMT() {
            return YCRAMT;
        }

        public void setYCRAMT(String YCRAMT) {
            this.YCRAMT = YCRAMT;
        }

        public String getYDRCNT() {
            return YDRCNT;
        }

        public void setYDRCNT(String YDRCNT) {
            this.YDRCNT = YDRCNT;
        }

        public String getYCRCNT() {
            return YCRCNT;
        }

        public void setYCRCNT(String YCRCNT) {
            this.YCRCNT = YCRCNT;
        }

        public String getDINRAT() {
            return DINRAT;
        }

        public void setDINRAT(String DINRAT) {
            this.DINRAT = DINRAT;
        }

        public String getCINRAT() {
            return CINRAT;
        }

        public void setCINRAT(String CINRAT) {
            this.CINRAT = CINRAT;
        }

        public String getDRATSF() {
            return DRATSF;
        }

        public void setDRATSF(String DRATSF) {
            this.DRATSF = DRATSF;
        }

        public String getCRATSF() {
            return CRATSF;
        }

        public void setCRATSF(String CRATSF) {
            this.CRATSF = CRATSF;
        }

        public String getDACINT() {
            return DACINT;
        }

        public void setDACINT(String DACINT) {
            this.DACINT = DACINT;
        }

        public String getCACINT() {
            return CACINT;
        }

        public void setCACINT(String CACINT) {
            this.CACINT = CACINT;
        }

        public String getDAMINT() {
            return DAMINT;
        }

        public void setDAMINT(String DAMINT) {
            this.DAMINT = DAMINT;
        }

        public String getCAMINT() {
            return CAMINT;
        }

        public void setCAMINT(String CAMINT) {
            this.CAMINT = CAMINT;
        }

        public String getINTFLG() {
            return INTFLG;
        }

        public void setINTFLG(String INTFLG) {
            this.INTFLG = INTFLG;
        }

        public String getINTCYC() {
            return INTCYC;
        }

        public void setINTCYC(String INTCYC) {
            this.INTCYC = INTCYC;
        }

        public String getINTTRA() {
            return INTTRA;
        }

        public void setINTTRA(String INTTRA) {
            this.INTTRA = INTTRA;
        }

        public String getLINTDT() {
            return LINTDT;
        }

        public void setLINTDT(String LINTDT) {
            this.LINTDT = LINTDT;
        }

        public String getLINDTH() {
            return LINDTH;
        }

        public void setLINDTH(String LINDTH) {
            this.LINDTH = LINDTH;
        }

        public String getSTMCYC() {
            return STMCYC;
        }

        public void setSTMCYC(String STMCYC) {
            this.STMCYC = STMCYC;
        }

        public String getSTMCDT() {
            return STMCDT;
        }

        public void setSTMCDT(String STMCDT) {
            this.STMCDT = STMCDT;
        }

        public String getSTMFMT() {
            return STMFMT;
        }

        public void setSTMFMT(String STMFMT) {
            this.STMFMT = STMFMT;
        }

        public String getSTMSHT() {
            return STMSHT;
        }

        public void setSTMSHT(String STMSHT) {
            this.STMSHT = STMSHT;
        }

        public String getSTMDEP() {
            return STMDEP;
        }

        public void setSTMDEP(String STMDEP) {
            this.STMDEP = STMDEP;
        }

        public String getSTMADD() {
            return STMADD;
        }

        public void setSTMADD(String STMADD) {
            this.STMADD = STMADD;
        }

        public String getSTMZIP() {
            return STMZIP;
        }

        public void setSTMZIP(String STMZIP) {
            this.STMZIP = STMZIP;
        }

        public String getLEGCYC() {
            return LEGCYC;
        }

        public void setLEGCYC(String LEGCYC) {
            this.LEGCYC = LEGCYC;
        }

        public String getLEGCDT() {
            return LEGCDT;
        }

        public void setLEGCDT(String LEGCDT) {
            this.LEGCDT = LEGCDT;
        }

        public String getLEGFMT() {
            return LEGFMT;
        }

        public void setLEGFMT(String LEGFMT) {
            this.LEGFMT = LEGFMT;
        }

        public String getLEGSHT() {
            return LEGSHT;
        }

        public void setLEGSHT(String LEGSHT) {
            this.LEGSHT = LEGSHT;
        }

        public String getLEGDEP() {
            return LEGDEP;
        }

        public void setLEGDEP(String LEGDEP) {
            this.LEGDEP = LEGDEP;
        }

        public String getLEGADD() {
            return LEGADD;
        }

        public void setLEGADD(String LEGADD) {
            this.LEGADD = LEGADD;
        }

        public String getLEGZIP() {
            return LEGZIP;
        }

        public void setLEGZIP(String LEGZIP) {
            this.LEGZIP = LEGZIP;
        }

        public String getACTTYP() {
            return ACTTYP;
        }

        public void setACTTYP(String ACTTYP) {
            this.ACTTYP = ACTTYP;
        }

        public String getACTGLC() {
            return ACTGLC;
        }

        public void setACTGLC(String ACTGLC) {
            this.ACTGLC = ACTGLC;
        }

        public String getACTPLC() {
            return ACTPLC;
        }

        public void setACTPLC(String ACTPLC) {
            this.ACTPLC = ACTPLC;
        }

        public String getDEPNUM() {
            return DEPNUM;
        }

        public void setDEPNUM(String DEPNUM) {
            this.DEPNUM = DEPNUM;
        }

        public String getLENTDT() {
            return LENTDT;
        }

        public void setLENTDT(String LENTDT) {
            this.LENTDT = LENTDT;
        }

        public String getDRACCM() {
            return DRACCM;
        }

        public void setDRACCM(String DRACCM) {
            this.DRACCM = DRACCM;
        }

        public String getCRACCM() {
            return CRACCM;
        }

        public void setCRACCM(String CRACCM) {
            this.CRACCM = CRACCM;
        }

        public String getCQEFLG() {
            return CQEFLG;
        }

        public void setCQEFLG(String CQEFLG) {
            this.CQEFLG = CQEFLG;
        }

        public String getBALLIM() {
            return BALLIM;
        }

        public void setBALLIM(String BALLIM) {
            this.BALLIM = BALLIM;
        }

        public String getOVELIM() {
            return OVELIM;
        }

        public void setOVELIM(String OVELIM) {
            this.OVELIM = OVELIM;
        }

        public String getOVEEXP() {
            return OVEEXP;
        }

        public void setOVEEXP(String OVEEXP) {
            this.OVEEXP = OVEEXP;
        }

        public String getOPNDAT() {
            return OPNDAT;
        }

        public void setOPNDAT(String OPNDAT) {
            this.OPNDAT = OPNDAT;
        }

        public String getCLSDAT() {
            return CLSDAT;
        }

        public void setCLSDAT(String CLSDAT) {
            this.CLSDAT = CLSDAT;
        }

        public String getREGSTS() {
            return REGSTS;
        }

        public void setREGSTS(String REGSTS) {
            this.REGSTS = REGSTS;
        }

        public String getFRZSTS() {
            return FRZSTS;
        }

        public void setFRZSTS(String FRZSTS) {
            this.FRZSTS = FRZSTS;
        }

        public String getACTSTS() {
            return ACTSTS;
        }

        public void setACTSTS(String ACTSTS) {
            this.ACTSTS = ACTSTS;
        }

        public String getAMDTLR() {
            return AMDTLR;
        }

        public void setAMDTLR(String AMDTLR) {
            this.AMDTLR = AMDTLR;
        }

        public String getUPDDAT() {
            return UPDDAT;
        }

        public void setUPDDAT(String UPDDAT) {
            this.UPDDAT = UPDDAT;
        }

        public String getRECSTS() {
            return RECSTS;
        }

        public void setRECSTS(String RECSTS) {
            this.RECSTS = RECSTS;
        }
    }

    @Override
    public String toString() {
        this.INFO.TXN_CODE = "9009062";
        XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("$", "_");
        HierarchicalStreamDriver hierarchicalStreamDriver = new XppDriver(replacer);
        XStream xs = new XStream(hierarchicalStreamDriver);
        xs.processAnnotations(ToaXml9009062.class);
        return "<?xml version=\"1.0\" encoding=\"GBK\"?>" + "\n" + xs.toXML(this);
    }

    public static ToaXml9009062 getToa(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(ToaXml9009062.class);
        return (ToaXml9009062) xs.fromXML(xml);
    }
}
