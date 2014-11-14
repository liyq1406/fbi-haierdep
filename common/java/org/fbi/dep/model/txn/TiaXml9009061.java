package org.fbi.dep.model.txn;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.fbi.dep.model.base.TiaXml;
import org.fbi.dep.model.base.TiaXmlInfo;

import java.io.Serializable;

/**
 * 根据客户证件信息查询账号列表-返回报文中有客户号
 */

@XStreamAlias("ROOT")
public class TiaXml9009061 extends TiaXml {
    public TiaXmlInfo INFO;
    public Body BODY;

    public static class Body implements Serializable {
        public String CUSKID; // 客户类别	C(1)	1-单位客户 2-个人客户 3-内部客户 4-银行同业客户
        public String PASTYP; // 证件类别	C(1)	1-营业执照 2-登记证 3-主管部门证明文件 4-身份证 5-军人证 6-护照
        public String PASSNO; // 证件号
        public String ACTTYP; // 帐户类型
        public String BEGNUM; // 起始笔数
    }

    @Override
    public TiaXml getTia(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(TiaXml9009061.class);
        return (TiaXml9009061) xs.fromXML(xml);
    }
}
