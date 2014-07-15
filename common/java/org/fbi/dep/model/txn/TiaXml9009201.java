package org.fbi.dep.model.txn;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.fbi.dep.model.base.TiaXml;
import org.fbi.dep.model.base.TiaXmlInfo;

import java.io.Serializable;

/**
 * SBS二代支付手续费入账
 */

@XStreamAlias("ROOT")
public class TiaXml9009201 extends TiaXml {
    public TiaXmlInfo INFO;
    public Body BODY;

    public static class Body implements Serializable {
        public String SFFLAG;            // 收付标志	C(1)	S:收    F:付	非空
        public String TXNAMT;            // 金额	C(3，16)	格式: ####9.99 例:1234.56，0.00。最长16位。	非空

        public String REMARK;
        public String RESERVE;
    }

    @Override
    public TiaXml getTia(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(TiaXml9009201.class);
        return (TiaXml9009201) xs.fromXML(xml);
    }
}
