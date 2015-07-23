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
 * Created by XIANGYANG on 2015-6-30.
 * 代扣结果查询-批量模式-响应报文
 */

@XStreamAlias("root")
public class ToaXml9101104 extends ToaXml {
    public ToaXmlHttpInfo info = new ToaXmlHttpInfo();
    public Body Body = new Body();

    public ToaXml9101104.Body getBody() {
        return Body;
    }

    public void setBody(ToaXml9101104.Body body) {
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
        public String pagesum;    //总页数
        @XStreamImplicit
        public List<BodyDetail> records;

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

        public List<BodyDetail> getRecords() {
            return records;
        }

        public void setRecords(List<BodyDetail> records) {
            this.records = records;
        }
    }

    @XStreamAlias("records")
    public static class BodyDetail implements Serializable {

        public String iouno;        //借据号
        public String poano;        //期次号
        public String txnamt;       //代扣金额
        public String schpaydate;   //计划还款日
        public String channel;      //代扣渠道编号 01-银联 02-通联
        public String resultcode;   //代扣结果响应码 0-未处理 1-成功 2-失败  3-不明
        public String resultmsg;    //代扣结果响应信息

        public String getIouno() {
            return iouno;
        }

        public void setIouno(String iouno) {
            this.iouno = iouno;
        }

        public String getPoano() {
            return poano;
        }

        public void setPoano(String poano) {
            this.poano = poano;
        }

        public String getTxnamt() {
            return txnamt;
        }

        public void setTxnamt(String txnamt) {
            this.txnamt = txnamt;
        }

        public String getSchpaydate() {
            return schpaydate;
        }

        public void setSchpaydate(String schpaydate) {
            this.schpaydate = schpaydate;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getResultcode() {
            return resultcode;
        }

        public void setResultcode(String resultcode) {
            this.resultcode = resultcode;
        }

        public String getResultmsg() {
            return resultmsg;
        }

        public void setResultmsg(String resultmsg) {
            this.resultmsg = resultmsg;
        }
    }

    @Override
    public String toString() {
        this.info.txncode = "9101104";
        XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("$", "_");
        HierarchicalStreamDriver hierarchicalStreamDriver = new XppDriver(replacer);
        XStream xs = new XStream(hierarchicalStreamDriver);
        xs.processAnnotations(ToaXml9101104.class);
        return "<?xml version=\"1.0\" encoding=\"GBK\"?>" + "\n" + xs.toXML(this);
    }

    public static ToaXml9101104 getToa(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(ToaXml9101104.class);
        return (ToaXml9101104) xs.fromXML(xml);
    }
}

