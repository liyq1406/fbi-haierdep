package org.fbi.dep.model;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * °×Ãûµ¥ÕËºÅ
 */
@XStreamAlias("List")
public class Act9009001List implements Serializable {

    @XStreamImplicit
    public List<ActInfo> ActInfo = new ArrayList<ActInfo>();

    public static class ActInfo implements Serializable {
        public String actno;
        public String userid;
        public String txnNo;
        public String inOut;
    }

    public Act9009001List toBean(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(Act9009001List.class);
        return (Act9009001List) xs.fromXML(xml);
    }
}
