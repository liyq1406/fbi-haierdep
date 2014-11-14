package org.fbi.endpoint.sms;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ÊÖ»úºÅ
 */
@XStreamAlias("List")
public class PhoneNumberList implements Serializable {

    @XStreamImplicit
    public List<Sms> SmsList = new ArrayList<Sms>();

    @XStreamAlias("Sms")
    public static class Sms implements Serializable {
        public String bizCode;
        public String phoneNumber;
    }

    public PhoneNumberList toBean(String xml) {

        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(PhoneNumberList.class);
        return (PhoneNumberList) xs.fromXML(xml);
    }

    @Override
    public String toString() {
        XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("$", "_");
        HierarchicalStreamDriver hierarchicalStreamDriver = new XppDriver(replacer);
        XStream xs = new XStream(hierarchicalStreamDriver);
        xs.processAnnotations(PhoneNumberList.class);
        return xs.toXML(this);
    }

}
