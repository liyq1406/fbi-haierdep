package org.fbi.dep.model.txn;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.fbi.dep.model.base.TiaXml;
import org.fbi.dep.model.base.TiaXmlInfo;
import java.io.Serializable;

/**
 * Created by XIANGYANG on 2015-5-11.
 * 根据账号查询账户信息
 */

@XStreamAlias("ROOT")
public class TiaXml9009062 extends TiaXml {
    public TiaXmlInfo INFO;
    public Body BODY;

    public static class Body implements Serializable {
        public String ACTNUM;       //账号	 C(22) 完整账号(8010开头)
    }

    @Override
    public TiaXml getTia(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(TiaXml9009062.class);
        return (TiaXml9009062) xs.fromXML(xml);
    }
}
