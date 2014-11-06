package org.fbi.dep.model.txn;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.fbi.dep.model.base.TiaXml;
import org.fbi.dep.model.base.TiaXmlInfo;

import java.io.Serializable;

/**
 * 建立个人客户信息 对应SBS-8011交易
 */

@XStreamAlias("ROOT")
public class TiaXml9009301 extends TiaXml {
    public TiaXmlInfo INFO;
    public Body BODY;

    public static class Body implements Serializable {

        public String CUSNAM;  // 客户名称
        public String CORADD;  // 地址
        public String ZIPCDE;  // 邮编
        public String TELNUM;  // 电话
        public String TELEXN;  // 传真
        public String PASTYP;  // 证件类型
        public String PASSNO;  // 证件号

    }

    @Override
    public TiaXml getTia(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(TiaXml9009301.class);
        return (TiaXml9009301) xs.fromXML(xml);
    }
}
