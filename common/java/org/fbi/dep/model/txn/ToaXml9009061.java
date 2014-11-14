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
 * 根据客户证件信息查询账户列表-返回报文中含有客户号
 */

@XStreamAlias("ROOT")
public class ToaXml9009061 extends ToaXml {
    public ToaXmlInfo INFO = new ToaXmlInfo();
    public Body BODY = new Body();

    public static class Body implements Serializable {
        public String TOTCNT;       // 总记录数
        public String CURCNT;       // 本包内记录数
        public List<BodyDetail> TRANS_DETAILS;

        @XStreamAlias("TRANS_DETAIL")
        public static class BodyDetail implements Serializable {

            public String CUSIDT = "";       // 客户号
            public String CUSNAM = "";       // 户名
            public String CUSACT = "";       // 账号
            public String ACTTYP = "";       // 账户种类
            public String APCODE = "";       // 核算码


            public String getCUSACT() {
                return CUSACT;
            }

            public void setCUSACT(String CUSACT) {
                this.CUSACT = CUSACT;
            }

            public String getACTTYP() {
                return ACTTYP;
            }

            public void setACTTYP(String ACTTYP) {
                this.ACTTYP = ACTTYP;
            }

            public String getCUSIDT() {
                return CUSIDT;
            }

            public void setCUSIDT(String CUSIDT) {
                this.CUSIDT = CUSIDT;
            }

            public String getCUSNAM() {
                return CUSNAM;
            }

            public void setCUSNAM(String CUSNAM) {
                this.CUSNAM = CUSNAM;
            }

            public String getAPCODE() {
                return APCODE;
            }

            public void setAPCODE(String APCODE) {
                this.APCODE = APCODE;
            }
        }
    }

    @Override
    public String toString() {
        this.INFO.TXN_CODE = "9009060";
        XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("$", "_");
        HierarchicalStreamDriver hierarchicalStreamDriver = new XppDriver(replacer);
        XStream xs = new XStream(hierarchicalStreamDriver);
        xs.processAnnotations(ToaXml9009061.class);
        return "<?xml version=\"1.0\" encoding=\"GBK\"?>" + "\n" + xs.toXML(this);
    }

    public static ToaXml9009061 getToa(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(ToaXml9009061.class);
        return (ToaXml9009061) xs.fromXML(xml);
    }
}
