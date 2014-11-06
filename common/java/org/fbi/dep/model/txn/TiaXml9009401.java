package org.fbi.dep.model.txn;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.fbi.dep.model.base.TiaXml;
import org.fbi.dep.model.base.TiaXmlInfo;

import java.io.Serializable;

/**
 * 贷款开户 SBS-8124交易
 */

@XStreamAlias("ROOT")
public class TiaXml9009401 extends TiaXml {
    public TiaXmlInfo INFO;
    public Body BODY;

    public static class Body implements Serializable {

        public String CUSIDT;  // 客户号
        public String APCODE;  // 核算码
        public String CURCDE;  // 币别
    }

    @Override
    public TiaXml getTia(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(TiaXml9009401.class);
        return (TiaXml9009401) xs.fromXML(xml);
    }
}
