package org.fbi.endpoint.allinpay.domain;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.fbi.endpoint.allinpay.core.ABaseToaHeader;

import java.util.List;

/**
 * Created by Lichao.W At 2015/6/25 22:27
 * wanglichao@163.com
 * 通联批量代扣交易
 */
@XStreamAlias("AIPG")
public class T120001Toa {
    public ToaHeader INFO;
    public Body BODY;

    public static class ToaHeader extends ABaseToaHeader {
    }

    public static class Body {
        public List<BodyDetail> RET_DETAILS;

        @XStreamAlias("RET_DETAIL")
        public static class BodyDetail {
            public String SN = "";
            public String RET_CODE = "";
            public String ERR_MSG = "";
        }
    }

    @Override
    public String toString() {
        XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("__", "_");
        HierarchicalStreamDriver hierarchicalStreamDriver = new XppDriver(replacer);
        XStream xs = new XStream(hierarchicalStreamDriver);
        xs.processAnnotations(T120001Toa.class);
        return "<?xml version=\"1.0\" encoding=\"GBK\"?>" + xs.toXML(this);
    }

    public static T120001Toa getToa(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(T120001Toa.class);
        return (T120001Toa) xs.fromXML(xml);
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
                "  <BODY>\n" +
                "    <RET_DETAILS>\n" +
                "      <RET_DETAIL>\n" +
                "        <SN>0001</SN>\n" +
                "        <RET_CODE>0000</RET_CODE>\n" +
                "        <ERR_MSG>支付成功</ERR_MSG>\n" +
                "      </RET_DETAIL>\n" +
                "      <RET_DETAIL>\n" +
                "        <SN>0002</SN>\n" +
                "        <RET_CODE>0000</RET_CODE>\n" +
                "        <ERR_MSG>支付成功</ERR_MSG>\n" +
                "      </RET_DETAIL>\n" +
                "      <RET_DETAIL>\n" +
                "        <SN>0003</SN>\n" +
                "        <RET_CODE>0000</RET_CODE>\n" +
                "        <ERR_MSG>支付成功</ERR_MSG>\n" +
                "      </RET_DETAIL>\n" +
                "    </RET_DETAILS>\n" +
                "  </BODY>\n" +
                "</AIPG>";

        T120001Toa toa = new T120001Toa();

        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(T120001Toa.class);
        toa = (T120001Toa) xs.fromXML(xml);

        System.out.println(toa);
    }
}
