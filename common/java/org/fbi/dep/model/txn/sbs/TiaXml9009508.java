package org.fbi.dep.model.txn.sbs;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.fbi.dep.model.base.TiaXml;

import java.io.Serializable;

/**
 * 根据证件查询-存款信息9009508
 * (对应SBS-交易)
 */

@XStreamAlias("ROOT")
public class TiaXml9009508 extends TiaXml {
    private Info INFO;
    private Body BODY;

    public Body getBODY() {
        return BODY;
    }

    public void setBODY(Body BODY) {
        this.BODY = BODY;
    }

    public Info getINFO() {
        return INFO;
    }

    public void setINFO(Info INFO) {
        this.INFO = INFO;
    }

    public static class Info implements Serializable {
        private String TXN_CODE; // 交易代码（固定值：9009508）
        private String REQ_SN;   // 交易流水号

        public String getTXN_CODE() {
            return TXN_CODE;
        }

        public void setTXN_CODE(String TXN_CODE) {
            this.TXN_CODE = TXN_CODE;
        }

        public String getREQ_SN() {
            return REQ_SN;
        }

        public void setREQ_SN(String REQ_SN) {
            this.REQ_SN = REQ_SN;
        }
    }

    public static class Body implements Serializable{
        private String PASTYP; // 证件种类
        private String PASSNO; // 证件号
    }

    @Override
    public TiaXml getTia(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(TiaXml9009508.class);
        return (TiaXml9009508) xs.fromXML(xml);
    }
}
