package org.fbi.dep.model.txn;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.fbi.dep.model.base.TiaXml;
import org.fbi.dep.model.base.TiaXmlInfo;

import java.io.Serializable;

/**
 * Created by XIANGYANG on 2015-5-21.
 * 账户历史交易明细查询
 */

@XStreamAlias("ROOT")
public class TiaXml9009052 extends TiaXml {
    public TiaXmlInfo INFO;
    public Body BODY;

    public static class Body implements Serializable {
        public String BATSEQ="111111";
        public String ORGIDT="010"; //柜员机构号
        public String DEPNUM="60";  //柜员部门号
        public String ORGID3="010"; //帐户机构号
        public String ACTNO;       //账户账号	  C(22) 完整账号(8010开头)
        public String START_DATE;       //起始交易日期  C(8)
        public String END_DATE;       //终止交易日期  C(8)
        public String START_NUM;       //起始笔数     C(6)
    }

    @Override
    public TiaXml getTia(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(TiaXml9009052.class);
        return (TiaXml9009052) xs.fromXML(xml);
    }
}
