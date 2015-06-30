package org.fbi.dep.model.txn;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.fbi.dep.model.base.ToaXml;
import org.fbi.dep.model.base.ToaXmlHttpInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XIANGYANG on 2015-6-30.
 * 还款流程启动-响应报文
 */

@XStreamAlias("root")
public class ToaXml9101101 extends ToaXml {
    public ToaXmlHttpInfo info = new ToaXmlHttpInfo();
    public Body Body = new Body();

    public ToaXml9101101.Body getBody() {
        return Body;
    }

    public void setBody(ToaXml9101101.Body body) {
        Body = body;
    }

    public ToaXmlHttpInfo getInfo() {
        return info;
    }

    public void setInfo(ToaXmlHttpInfo info) {
        this.info = info;
    }

    public static class Body implements Serializable {
        public String flowcode;    //流程响应码
        public String flowmsg;     //流程响应信息

        public String getFlowcode() {
            return flowcode;
        }

        public void setFlowcode(String flowcode) {
            this.flowcode = flowcode;
        }

        public String getFlowmsg() {
            return flowmsg;
        }

        public void setFlowmsg(String flowmsg) {
            this.flowmsg = flowmsg;
        }
    }

    @Override
    public String toString() {
        this.info.txncode = "9101101";
        XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("$", "_");
        HierarchicalStreamDriver hierarchicalStreamDriver = new XppDriver(replacer);
        XStream xs = new XStream(hierarchicalStreamDriver);
        xs.processAnnotations(ToaXml9101101.class);
        return "<?xml version=\"1.0\" encoding=\"GBK\"?>" + "\n" + xs.toXML(this);
    }

    public static ToaXml9101101 getToa(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(ToaXml9101101.class);
        return (ToaXml9101101) xs.fromXML(xml);
    }
}

