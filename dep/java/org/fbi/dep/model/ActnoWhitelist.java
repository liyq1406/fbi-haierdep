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
public class ActnoWhitelist implements Serializable {

    @XStreamImplicit
    public List<WhiteActno> WhiteActno = new ArrayList<WhiteActno>();

    public static class WhiteActno implements Serializable {
        public String actno;
        public String userid;
        public String txnNo;
        public String inOut;
    }

    public ActnoWhitelist toBean(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(ActnoWhitelist.class);
        return (ActnoWhitelist) xs.fromXML(xml);
    }
}
