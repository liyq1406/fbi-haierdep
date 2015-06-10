package org.fbi.dep.model.txn;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.fbi.dep.model.base.TiaXml;
import org.fbi.dep.model.base.TiaXmlHttpInfo;
import java.io.Serializable;

/**
 * Created by XIANGYANG on 2015-6-2.
 * 查询建行到账明细-请求报文
 */

@XStreamAlias("root")
public class TiaXml9109003 extends TiaXml {
    public TiaXmlHttpInfo info;
    public Body body;

    public TiaXmlHttpInfo getInfo() {
        return info;
    }

    public void setInfo(TiaXmlHttpInfo info) {
        this.info = info;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public static class Body implements Serializable {
        public String startdate;      //交易起始日期
        public String enddate;        //交易结束日期
        public String pagenum;        //请求页码
        public String pagesize;       //每页记录数
        public String reserve;        //保留域

        public String getStartdate() {
            return startdate;
        }

        public void setStartdate(String startdate) {
            this.startdate = startdate;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public String getPagenum() {
            return pagenum;
        }

        public void setPagenum(String pagenum) {
            this.pagenum = pagenum;
        }

        public String getPagesize() {
            return pagesize;
        }

        public void setPagesize(String pagesize) {
            this.pagesize = pagesize;
        }

        public String getReserve() {
            return reserve;
        }

        public void setReserve(String reserve) {
            this.reserve = reserve;
        }

    }

    @Override
    public TiaXml getTia(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(TiaXml9109003.class);
        return (TiaXml9109003) xs.fromXML(xml);
    }
}
