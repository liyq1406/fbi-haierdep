package org.fbi.dep.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.fbi.dep.component.jms.JmsTextMsgClient;
import org.fbi.dep.enums.TxnRtnCode;
import org.fbi.dep.helper.MD5Helper;
import org.fbi.dep.transform.AbstractTiaToToa;
import org.fbi.dep.transform.AbstractTiaXmlTransform;
import org.fbi.dep.transform.AbstractToaXmlTransform;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-2-13
 * Time: ����2:25
 * To change this template use File | Settings | File Templates.
 */
public class PayoutDirectSktRouteBuilder extends RouteBuilder {

    private static Logger logger = LoggerFactory.getLogger(PayoutDirectSktRouteBuilder.class);
    private static String SERVER_IP = PropertyManager.getProperty("dep.localhost.ip");
    private String server_port;

    public PayoutDirectSktRouteBuilder(String port) {
        this.server_port = port;
    }

    @Override
    public void configure() throws Exception {
        from("netty:tcp://" + SERVER_IP + ":" + server_port + "?sync=true&disconnect=true" +
                "&encoding=GBK&decoder=#skt-decoder&encoder=#skt-encoder")
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        byte[] bytes = (byte[]) exchange.getIn().getBody();
                        logger.info("���յ����ģ�" + new String(bytes));
                        String rtnMsgHeader = null;
                        String rtnMsgData = null;
                        String txnCode = null;
                        try {
                            // ��������ͷ
                            byte[] headerBytes = new byte[64];
                            System.arraycopy(bytes, 0, headerBytes, 0, headerBytes.length);
                            String msgHeader = new String(headerBytes, "GBK");
                            rtnMsgHeader = msgHeader;
                            // ����������
                            byte[] bodyBytes = new byte[bytes.length - 64];
                            System.arraycopy(bytes, 64, bodyBytes, 0, bodyBytes.length);
                            String msgData = new String(bodyBytes, "GBK");
                            rtnMsgData = msgData;
                            // ��Χϵͳ���롢�����롢�������ڡ�mac
                            String wsysid = msgHeader.substring(4, 14).trim().toUpperCase();
                            String userid = PropertyManager.getProperty("wsys.userkey." + wsysid);
                            txnCode = msgHeader.substring(14, 24).trim();
                            String txnDate = msgHeader.substring(24, 32).trim();
                            String mac = msgHeader.substring(32);
                            // MD5У��
                            // Message Data���ּ���8λ�������ڼ����û�ID�������MD5ֵ
                            String md5 = MD5Helper.getMD5String(msgData + txnDate + userid);
                            // ��֤ʧ�� ������֤ʧ����Ϣ
                            if (!md5.equals(mac)) {
                                logger.info("������ˡ�MD5:" + md5);
                                throw new RuntimeException(TxnRtnCode.MSG_VERIFY_MAC_ILLEGAL.toRtnMsg());
                            }
                            // ���漰sbs��ת����ʱ�����账��sbs����
                            AbstractTiaXmlTransform tiaXmlTransform = (AbstractTiaXmlTransform) Class.forName("org.fbi.dep.transform.TiaXml" + txnCode + "Transform").newInstance();
                            String unionpayReqXml = tiaXmlTransform.run(msgData);
                            // ���͵������õ���������xml����
                            String unionpayResXml = new JmsTextMsgClient().sendRecivMsg("100", wsysid, wsysid,
                                    "queue.dep.core.haierfip.fcdep", "queue.dep.core.fcdep.haierfip", unionpayReqXml);
                            // ����ת��Ϊdep-unionpay-xml����
                            AbstractToaXmlTransform toaXmlTransform = (AbstractToaXmlTransform) Class.forName("org.fbi.dep.transform.ToaXml" + txnCode + "Transform").newInstance();
                            String rtnXml = toaXmlTransform.run(unionpayResXml, wsysid);
                            exchange.getOut().setBody((msgHeader + rtnXml).getBytes());
                        } catch (Exception e) {
                            //  �����쳣��Ϣ
                            if (txnCode == null) {
                                logger.error("���Ľ���ʧ�ܣ��޷�������������.", e);
                                return;
                            } else {
                                String exmsg = e.getMessage();
                                AbstractTiaToToa tiaToToa = (AbstractTiaToToa) Class.forName("org.fbi.dep.transform.Tia" + txnCode + "ToToa").newInstance();
                                if (exmsg == null) {
                                    exchange.getOut().setBody((rtnMsgHeader + tiaToToa.run(rtnMsgData, TxnRtnCode.SERVER_EXCEPTION.getCode(),
                                            TxnRtnCode.SERVER_EXCEPTION.getTitle())).getBytes());
                                } else {
                                    String errmsg[] = exmsg.split("\\|");
                                    logger.error(exmsg);
                                    exchange.getOut().setBody((rtnMsgHeader + tiaToToa.run(rtnMsgData, errmsg[0], errmsg[1])).getBytes());
                                }
                            }
                        }
                    }
                }
                );
    }
}
