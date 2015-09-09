package org.fbi.dep.route;

import com.thoughtworks.xstream.converters.ConversionException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.netty.http.NettyChannelBufferStreamCache;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.fbi.dep.component.jms.JmsBytesClient;
import org.fbi.dep.enums.SBSFormCode;
import org.fbi.dep.enums.TxnRtnCode;
import org.fbi.dep.helper.MD5Helper;
import org.fbi.dep.management.TxnChecker;
import org.fbi.dep.management.TxnUseridChecker;
import org.fbi.dep.model.CheckResult;
import org.fbi.dep.model.base.ToaXmlHttpErr;
import org.fbi.dep.transform.AbstractTiaBytesTransform;
import org.fbi.dep.transform.AbstractToaBytesTransform;
import org.fbi.dep.txn.AbstractTxnProcessor;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by hanjianlong on 2015-5-23.
 * http协议
 */

public class FebHttpRouteBuilder extends RouteBuilder {

    private static Logger logger = LoggerFactory.getLogger(FebHttpRouteBuilder.class);
    private static String SERVER_IP = PropertyManager.getProperty("dep.localhost.ip");
    private String server_port;

    public FebHttpRouteBuilder(String port) {
        this.server_port = port;
    }

    @Override
    public void configure() throws Exception {
        from("netty-http:http://" + SERVER_IP + ":" + server_port + "/depService?matchOnUriPrefix=false").
            process(new Processor() {
                        public void process(Exchange exchange) throws Exception {
                            String txnCode = null;
                            String rtnXml = null;
                            NettyChannelBufferStreamCache cf = null;
                            try {
                                cf = (NettyChannelBufferStreamCache) exchange.getIn().getBody();
                                StringBuffer sb = new StringBuffer();
                                int l;
                                byte[] tmp = new byte[2048];
                                while ((l = cf.read(tmp)) != -1) {
                                    sb.append(new String(tmp, 0, l));
                                }
                                byte[] bytes = sb.toString().getBytes();
                                logger.info("【FebHttpRouteBuilder 接收到报文】" + sb.toString());

                                String xmlMsgData = new String(bytes, "GBK");

                                SAXReader saxReader = new SAXReader();
                                InputStream inputStream = new ByteArrayInputStream(bytes);
                                Document document = saxReader.read(inputStream);
                                Element root = document.getRootElement();
                                for (Iterator i = root.elementIterator(); i.hasNext(); ) {
                                    Element info = (Element) i.next();
                                    if ("INFO".equals(info.getName())) {
                                        for (Iterator j = info.elementIterator(); j.hasNext(); ) {
                                            Element node = (Element) j.next();
                                            if ("TXNCODE".equals(node.getName())) {
                                                txnCode = node.getText();
                                            }
                                        }
                                        break;
                                    }
                                }

                                // 特殊交易特殊处理
                                Class txnClass = null;
                                try {
                                    if(txnCode.equals("1001")){
                                        txnCode="9100001";
                                    }
                                    txnClass = Class.forName("org.fbi.dep.txn.Txn" + txnCode + "Processor");
                                    AbstractTxnProcessor processor = (AbstractTxnProcessor) txnClass.newInstance();
                                    rtnXml = processor.process("121", xmlMsgData);
                                    logger.info("【FebHttpRouteBuilder 发送报文】" + new String((rtnXml).getBytes("GBK")));
                                    exchange.getOut().setBody((rtnXml).getBytes("GBK"));
                                } catch (ClassNotFoundException e) {
                                    txnClass = null;
                                }
                            } catch (Exception e) {
                                //  返回异常信息
                                ToaXmlHttpErr errXmlHttp = new ToaXmlHttpErr();
                                errXmlHttp.getInfo().setTxncode(txnCode);

                                if (txnCode == null || ConversionException.class.equals(e.getClass())) {
                                    logger.error("报文解析失败", e);
                                    errXmlHttp.getInfo().setRtncode(TxnRtnCode.MSG_ANALYSIS_ILLEGAL.getCode());
                                    errXmlHttp.getInfo().setRtnmsg(TxnRtnCode.MSG_ANALYSIS_ILLEGAL.getTitle());
                                    errXmlHttp.getBody().setRtncode(TxnRtnCode.MSG_ANALYSIS_ILLEGAL.getCode());
                                    errXmlHttp.getBody().setRtnmsg(TxnRtnCode.MSG_ANALYSIS_ILLEGAL.getTitle());
                                } else {
                                    String exmsg = e.getMessage();
                                    logger.error("交易异常", e);
                                    if (exmsg == null) {
                                        exmsg = TxnRtnCode.SERVER_EXCEPTION.getCode() + "|" + TxnRtnCode.SERVER_EXCEPTION.getTitle();
                                    } else if (!exmsg.contains("|")) {
                                        exmsg = TxnRtnCode.SERVER_EXCEPTION.getCode() + "|" + exmsg;
                                    }
                                    String errmsg[] = exmsg.split("\\|");
                                    SBSFormCode msgFormCode = SBSFormCode.valueOfAlias(errmsg[0]);
                                    if (msgFormCode != null) {
                                        errmsg[1] = msgFormCode.getTitle();
                                    }
                                    errXmlHttp.getInfo().setRtncode(errmsg[0]);
                                    errXmlHttp.getInfo().setRtnmsg(errmsg[1]);
                                    errXmlHttp.getBody().setRtncode(errmsg[0]);
                                    errXmlHttp.getBody().setRtnmsg(errmsg[1]);
                                }
                                rtnXml = errXmlHttp.toString();
                                logger.info("【FebHttpRouteBuilder 发送报文】" + new String((rtnXml).getBytes("GBK")));
                                exchange.getOut().setBody((rtnXml).getBytes("GBK"));
                            } finally {
                                cf.close();
                            }
                        }
                    }
            );
    }
}
