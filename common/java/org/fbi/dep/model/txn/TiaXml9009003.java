package org.fbi.dep.model.txn;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.fbi.dep.model.base.TiaXml;
import org.fbi.dep.model.base.TiaXmlInfo;

import java.io.Serializable;

/**
 *  单笔对外支付结果查询 SBS-MBP-建行
 */

@XStreamAlias("ROOT")
public class TiaXml9009003 extends TiaXml {
    public TiaXmlInfo INFO;
    public Body BODY;

    public static class Body implements Serializable {

        public String FBTIDX;     // 外围系统索引号
        public String REQNUM;     // 请求序列号
        public String ORDDAT;     // 交易日期
        public String CHQNUM;     // 收款行行号

    }

    @Override
    public TiaXml getTia(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(TiaXml9009003.class);
        return (TiaXml9009003) xs.fromXML(xml);
    }
}
