package org.fbi.dep.model.txn;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.fbi.dep.model.base.ToaXml;
import org.fbi.dep.model.base.ToaXmlHttpInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XIANGYANG on 2015-6-2.
 * 查询建行到账明细-响应报文
 */

@XStreamAlias("root")
public class ToaXml9109003 extends ToaXml {
    public ToaXmlHttpInfo info = new ToaXmlHttpInfo();
    public Body Body = new Body();

    public ToaXml9109003.Body getBody() {
        return Body;
    }

    public void setBody(ToaXml9109003.Body body) {
        Body = body;
    }

    public ToaXmlHttpInfo getInfo() {
        return info;
    }

    public void setInfo(ToaXmlHttpInfo info) {
        this.info = info;
    }

    public static class Body implements Serializable {
        public String rtncode;    //业务层面-返回代码
        public String rtnmsg;     //业务层面-返回信息
        public String pagesum;    //总记录数
        public String size;       //本包记录数
        @XStreamImplicit
        public List<BodyDetail> detail;
        public String reserve;    //保留域

        public String getRtncode() {
            return rtncode;
        }

        public void setRtncode(String rtncode) {
            this.rtncode = rtncode;
        }

        public String getRtnmsg() {
            return rtnmsg;
        }

        public void setRtnmsg(String rtnmsg) {
            this.rtnmsg = rtnmsg;
        }

        public String getPagesum() {
            return pagesum;
        }

        public void setPagesum(String pagesum) {
            this.pagesum = pagesum;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public List<BodyDetail> getDetail() {
            return detail;
        }

        public void setDetail(List<BodyDetail> detail) {
            this.detail = detail;
        }

        public String getReserve() {
            return reserve;
        }

        public void setReserve(String reserve) {
            this.reserve = reserve;
        }
    }

    @XStreamAlias("detail")
    public static class BodyDetail implements Serializable {

        public String sn;
        public String outacctid;
        public String inacctid;
        public String outacctname;
        public String inacctname;
        public String abstractstr;
        public String voucherid;
        public String vouchertype;
        public String outbranchname;
        public String inbranchname;
        public String curcode;
        public String dcflag;
        public String abstractcode;
        public String txdate;
        public String txtime;
        public String accthostseqid;
        public String coseqid;
        public String banknodeid;
        public String ccbstellerid;
        public String outcomacctid;
        public String outcomname;
        public String reserve1;
        public String reserve2;
        public String bankindex;
        public String acctbal;
        public String avbal;
        public String cacctbal;
        public String txamount;
        public String ctxamount;
        public String acctid;
        public String operatoruserid;
        public String reccount;
        public String txcode;
        public String businesstype;
        public String txseqid;

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getOutacctid() {
            return outacctid;
        }

        public void setOutacctid(String outacctid) {
            this.outacctid = outacctid;
        }

        public String getInacctid() {
            return inacctid;
        }

        public void setInacctid(String inacctid) {
            this.inacctid = inacctid;
        }

        public String getOutacctname() {
            return outacctname;
        }

        public void setOutacctname(String outacctname) {
            this.outacctname = outacctname;
        }

        public String getInacctname() {
            return inacctname;
        }

        public void setInacctname(String inacctname) {
            this.inacctname = inacctname;
        }

        public String getAbstractstr() {
            return abstractstr;
        }

        public void setAbstractstr(String abstractstr) {
            this.abstractstr = abstractstr;
        }

        public String getVoucherid() {
            return voucherid;
        }

        public void setVoucherid(String voucherid) {
            this.voucherid = voucherid;
        }

        public String getVouchertype() {
            return vouchertype;
        }

        public void setVouchertype(String vouchertype) {
            this.vouchertype = vouchertype;
        }

        public String getOutbranchname() {
            return outbranchname;
        }

        public void setOutbranchname(String outbranchname) {
            this.outbranchname = outbranchname;
        }

        public String getInbranchname() {
            return inbranchname;
        }

        public void setInbranchname(String inbranchname) {
            this.inbranchname = inbranchname;
        }

        public String getCurcode() {
            return curcode;
        }

        public void setCurcode(String curcode) {
            this.curcode = curcode;
        }

        public String getDcflag() {
            return dcflag;
        }

        public void setDcflag(String dcflag) {
            this.dcflag = dcflag;
        }

        public String getAbstractcode() {
            return abstractcode;
        }

        public void setAbstractcode(String abstractcode) {
            this.abstractcode = abstractcode;
        }

        public String getTxdate() {
            return txdate;
        }

        public void setTxdate(String txdate) {
            this.txdate = txdate;
        }

        public String getTxtime() {
            return txtime;
        }

        public void setTxtime(String txtime) {
            this.txtime = txtime;
        }

        public String getAccthostseqid() {
            return accthostseqid;
        }

        public void setAccthostseqid(String accthostseqid) {
            this.accthostseqid = accthostseqid;
        }

        public String getCoseqid() {
            return coseqid;
        }

        public void setCoseqid(String coseqid) {
            this.coseqid = coseqid;
        }

        public String getBanknodeid() {
            return banknodeid;
        }

        public void setBanknodeid(String banknodeid) {
            this.banknodeid = banknodeid;
        }

        public String getCcbstellerid() {
            return ccbstellerid;
        }

        public void setCcbstellerid(String ccbstellerid) {
            this.ccbstellerid = ccbstellerid;
        }

        public String getOutcomacctid() {
            return outcomacctid;
        }

        public void setOutcomacctid(String outcomacctid) {
            this.outcomacctid = outcomacctid;
        }

        public String getOutcomname() {
            return outcomname;
        }

        public void setOutcomname(String outcomname) {
            this.outcomname = outcomname;
        }

        public String getReserve1() {
            return reserve1;
        }

        public void setReserve1(String reserve1) {
            this.reserve1 = reserve1;
        }

        public String getReserve2() {
            return reserve2;
        }

        public void setReserve2(String reserve2) {
            this.reserve2 = reserve2;
        }

        public String getBankindex() {
            return bankindex;
        }

        public void setBankindex(String bankindex) {
            this.bankindex = bankindex;
        }

        public String getAcctbal() {
            return acctbal;
        }

        public void setAcctbal(String acctbal) {
            this.acctbal = acctbal;
        }

        public String getAvbal() {
            return avbal;
        }

        public void setAvbal(String avbal) {
            this.avbal = avbal;
        }

        public String getCacctbal() {
            return cacctbal;
        }

        public void setCacctbal(String cacctbal) {
            this.cacctbal = cacctbal;
        }

        public String getTxamount() {
            return txamount;
        }

        public void setTxamount(String txamount) {
            this.txamount = txamount;
        }

        public String getCtxamount() {
            return ctxamount;
        }

        public void setCtxamount(String ctxamount) {
            this.ctxamount = ctxamount;
        }

        public String getAcctid() {
            return acctid;
        }

        public void setAcctid(String acctid) {
            this.acctid = acctid;
        }

        public String getOperatoruserid() {
            return operatoruserid;
        }

        public void setOperatoruserid(String operatoruserid) {
            this.operatoruserid = operatoruserid;
        }

        public String getReccount() {
            return reccount;
        }

        public void setReccount(String reccount) {
            this.reccount = reccount;
        }

        public String getTxcode() {
            return txcode;
        }

        public void setTxcode(String txcode) {
            this.txcode = txcode;
        }

        public String getBusinesstype() {
            return businesstype;
        }

        public void setBusinesstype(String businesstype) {
            this.businesstype = businesstype;
        }

        public String getTxseqid() {
            return txseqid;
        }

        public void setTxseqid(String txseqid) {
            this.txseqid = txseqid;
        }
    }

    @Override
    public String toString() {
        this.info.txncode = "9109003";
        XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("$", "_");
        HierarchicalStreamDriver hierarchicalStreamDriver = new XppDriver(replacer);
        XStream xs = new XStream(hierarchicalStreamDriver);
        xs.processAnnotations(ToaXml9109003.class);
        return "<?xml version=\"1.0\" encoding=\"GBK\"?>" + "\n" + xs.toXML(this);
    }

    public static ToaXml9109003 getToa(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(ToaXml9109003.class);
        return (ToaXml9109003) xs.fromXML(xml);
    }
}
