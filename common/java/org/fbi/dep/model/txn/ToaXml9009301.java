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
 * 建立个人客户信息 对应SBS-8011交易
 */

@XStreamAlias("ROOT")
public class ToaXml9009301 extends ToaXml {
    public ToaXmlInfo INFO = new ToaXmlInfo();
    public Body BODY = new Body();

    public static class Body implements Serializable {
        public String ORGIDT;       // 部门号
        public String DEPNUM;       // 机构号
        public String CUSIDT;       // 客户号
        public String CUSNAM;       // 客户名称
        public String OPNDAT;       // 建立日期
        public String CLSDAT;       // 注销日期
        public String AMDTLR;       // 操作员
    }

    @Override
    public String toString() {
        this.INFO.TXN_CODE = "9009301";
        XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("$", "_");
        HierarchicalStreamDriver hierarchicalStreamDriver = new XppDriver(replacer);
        XStream xs = new XStream(hierarchicalStreamDriver);
        xs.processAnnotations(ToaXml9009301.class);
        return "<?xml version=\"1.0\" encoding=\"GBK\"?>" + "\n" + xs.toXML(this);
    }

    public static ToaXml9009301 getToa(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(ToaXml9009301.class);
        return (ToaXml9009301) xs.fromXML(xml);
    }
}
