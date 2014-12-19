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
 * 2.2	单笔对外支付结果查询 9009003
 */

@XStreamAlias("ROOT")
public class ToaXml9009003 extends ToaXml {
    public ToaXmlInfo INFO = new ToaXmlInfo();
    public Body BODY = new Body();

    public static class Body implements Serializable {


        public String TLRNUM;	// 柜员号
        public String VCHSET;	// 传票套号
        public String SETSEQ;	// 传票套内顺序号

        public String TXNSEQ;   // 银行流水号
        public String FBTIDX;	// 外围系统流水号
        /*
        1-落地审核中,2-已审核,3-银行拒绝,4-撤销,5-完成,6-所有明细失败,9-不明
         */
        public String PRCSTS;	// 交易状态
        public String RTNMSG;	// 银行返回信息


    }

    @Override
    public String toString() {
        this.INFO.TXN_CODE = "9009003";
        XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("$", "_");
        HierarchicalStreamDriver hierarchicalStreamDriver = new XppDriver(replacer);
        XStream xs = new XStream(hierarchicalStreamDriver);
        xs.processAnnotations(ToaXml9009003.class);
        return "<?xml version=\"1.0\" encoding=\"GBK\"?>" + "\n" + xs.toXML(this);
    }

    public static ToaXml9009003 getToa(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(ToaXml9009003.class);
        return (ToaXml9009003) xs.fromXML(xml);
    }
}
