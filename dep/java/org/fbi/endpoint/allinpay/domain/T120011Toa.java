package org.fbi.endpoint.allinpay.domain;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.fbi.endpoint.allinpay.core.ABaseToaHeader;

/**
 * Created by Lichao.W At 2015/6/25 22:27
 * wanglichao@163.com
 * 通联实时代扣
 */
@XStreamAlias("AIPG")
public class T120011Toa {
    public ToaHeader INFO;
    public Body TRANSRET;

    public static class ToaHeader extends ABaseToaHeader {
    }

    public static class Body {
        public String RET_CODE = "";
        public String SETTLE_DAY = "";
        public String ERR_MSG = "";
    }

    @Override
    public String toString() {
        XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("__", "_");
        HierarchicalStreamDriver hierarchicalStreamDriver = new XppDriver(replacer);
        XStream xs = new XStream(hierarchicalStreamDriver);
        xs.processAnnotations(T120011Toa.class);
        return "<?xml version=\"1.0\" encoding=\"GBK\"?>" + xs.toXML(this);
    }

    public static T120011Toa getToa(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(T120011Toa.class);
        return (T120011Toa) xs.fromXML(xml);
    }

    public static void main(String[] argv) {
        String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?><AIPG>\n" +
                "  <INFO>\n" +
                "    <TRX_CODE>100001</TRX_CODE>\n" +
                "    <VERSION>03</VERSION>\n" +
                "    <DATA_TYPE>2</DATA_TYPE>\n" +
                "    <REQ_SN>200604000000445-1435148087763</REQ_SN>\n" +
                "    <RET_CODE>0000</RET_CODE>\n" +
                "    <ERR_MSG></ERR_MSG>\n" +
                "    <SIGNED_MSG>9a15fa1a2c98b32743852d67668f308e59e75dbcd80509a9b5fc48724ca1c447a8608a0d3288a32a73f1750649d9e6502d5f7754cf2e695c2e795884378a193c06c5744f2af1d2151fd62b42d0abc8daa0d5e53d5ffa51997ce2c6c8e5c7c3671859e545e5367955d8e3bd64930f4719469fd1b502a8b45aeed66d5d93002867</SIGNED_MSG>\n" +
                "  </INFO>\n" +
                "  <TRANSRET>\n" +
                "     <RET_CODE>0000</RET_CODE>\n" +
                "     <SETTLE_DAY>20150625</SETTLE_DAY>\n" +
                "     <ERR_MSG>支付成功</ERR_MSG>\n" +
                "  </TRANSRET>\n" +
                "</AIPG>";

        T120011Toa toa = new T120011Toa();

        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(T120011Toa.class);
        toa = (T120011Toa) xs.fromXML(xml);

        System.out.println(toa);
    }
}
