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
public class ActList9009001 implements Serializable {

    @XStreamImplicit
    public List<Act> Acts = new ArrayList<Act>();

    public static class Act implements Serializable {
        public String actno;
        public String userid;
        public String txnNo;
        public String inOut;
    }

    public ActList9009001 toBean(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(ActList9009001.class);
        return (ActList9009001) xs.fromXML(xml);
    }
}
