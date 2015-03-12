package org.fbi.dep.model.txn;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.fbi.dep.model.base.TiaXml;
import org.fbi.dep.model.base.TiaXmlInfo;

import java.io.Serializable;

/**
 * SBS×ªÕËÔÙ×ªÕË
 */

@XStreamAlias("ROOT")
public class TiaXml9009004 extends TiaXml {
    public TiaXmlInfo INFO;
    public Body BODY;

    public static class Body implements Serializable {
        public String OUT_ACTNO;
        public String IN_ACTNO;
        public String TXN_AMT;
        public String IN_ACTNO2;
        public String TXN_AMT2;
        public String REMARK;
        public String RESERVE;
    }

    @Override
    public TiaXml getTia(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(TiaXml9009004.class);
        return (TiaXml9009004) xs.fromXML(xml);
    }
}
