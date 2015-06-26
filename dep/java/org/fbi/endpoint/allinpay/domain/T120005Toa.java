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
 * 通联结果查询 多笔
 */
@XStreamAlias("AIPG")
public class T120005Toa {
    public static class ToaHeader extends ABaseToaHeader {
    }

    public static class Body {

        @XStreamAlias("QTDETAIL")
        public static class BodyDetail {
            public String BATCHID = "";
            public String SN = "";
            public String TRXDIR = "";
            public String SETTDAY = "";
            public String FINTIME = "";
            public String SUBMITTIME = "";
            public String ACCOUNT_NO = "";
            public String ACCOUNT_NAME = "";
            public String AMOUNT = "";
            public String CUST_USERID = "";
            public String REMARK = "";
            public String RET_CODE = "";
            public String ERR_MSG = "";
        }
    }

    public ToaHeader INFO;
    public List<Body.BodyDetail> QTRANSRSP;

    @Override
    public String toString() {
        XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("__", "_");
        HierarchicalStreamDriver hierarchicalStreamDriver = new XppDriver(replacer);
        XStream xs = new XStream(hierarchicalStreamDriver);
        xs.processAnnotations(T120005Toa.class);
        return "<?xml version=\"1.0\" encoding=\"GBK\"?>" + xs.toXML(this);
    }

    public static T120005Toa getToa(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(T120005Toa.class);
        return (T120005Toa) xs.fromXML(xml);
    }

    public static void main(String[] argv) {
        String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?><AIPG>\n" +
                "  <INFO>\n" +
                "    <TRX_CODE>100004</TRX_CODE>\n" +
                "    <VERSION>04</VERSION>\n" +
                "    <DATA_TYPE>2</DATA_TYPE>\n" +
                "    <REQ_SN>1510956041000</REQ_SN>\n" +
                "    <RET_CODE>0000</RET_CODE>\n" +
                "    <ERR_MSG></ERR_MSG>\n" +
                "    <SIGNED_MSG>9a15fa1a2c98b32743852d67668f308e59e75dbcd80509a9b5fc48724ca1c447a8608a0d3288a32a73f1750649d9e6502d5f7754cf2e695c2e795884378a193c06c5744f2af1d2151fd62b42d0abc8daa0d5e53d5ffa51997ce2c6c8e5c7c3671859e545e5367955d8e3bd64930f4719469fd1b502a8b45aeed66d5d93002867</SIGNED_MSG>\n" +
                "  </INFO>\n" +
                "  <QTRANSRSP>\n" +
                "      <QTDETAIL>\n" +
                "        <SN>0001</SN>\n" +
                "        <RET_CODE>0000</RET_CODE>\n" +
                "        <ERR_MSG>支付成功</ERR_MSG>\n" +
                "        <ACCOUNT_NO>60138270140042110021</ACCOUNT_NO>\n" +
                "        <ACCOUNT_NAME>张三</ACCOUNT_NAME>\n" +
                "        <AMOUNT>10000</AMOUNT>\n" +
                "        <CUST_USERID>666666</CUST_USERID>\n" +
                "        <REMARK>取暖费</REMARK>\n" +
                "      </QTDETAIL>\n" +
                "      <QTDETAIL>\n" +
                "        <SN>0001</SN>\n" +
                "        <RET_CODE>0000</RET_CODE>\n" +
                "        <ERR_MSG>支付成功</ERR_MSG>\n" +
                "        <ACCOUNT_NO>60138270140042110021</ACCOUNT_NO>\n" +
                "        <ACCOUNT_NAME>张三</ACCOUNT_NAME>\n" +
                "        <AMOUNT>10000</AMOUNT>\n" +
                "        <CUST_USERID>666666</CUST_USERID>\n" +
                "        <REMARK>取暖费</REMARK>\n" +
                "      </QTDETAIL>\n" +
                "  </QTRANSRSP>\n" +
                "</AIPG>";

        T120005Toa toa = new T120005Toa();

        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(T120005Toa.class);
        toa = (T120005Toa) xs.fromXML(xml);
//        System.out.println(toa.QTRANSRSP.QUERY_TRANS.QUERY_SN);
        System.out.println(toa);
    }
}
