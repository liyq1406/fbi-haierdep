package org.fbi.dep.component.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-6-14
 * Time: 上午9:27
 * To change this template use File | Settings | File Templates.
 */
public class JmsMbpClient {

    private static final Logger logger = LoggerFactory.getLogger(JmsMbpClient.class);
    private static String ACTIVEMQ_URL = PropertyManager.getProperty("dep.activemq.url");
    private static String ACTIVEMQ_GUEST_USERNAME = PropertyManager.getProperty("dep.activemq.guest.username");
    private static String ACTIVEMQ_GUEST_PASSWORD = PropertyManager.getProperty("dep.activemq.guest.password");
    private static int ACTIVEMQ_TIMEOUT = PropertyManager.getIntProperty("dep.activemq.request.timeout");

    public byte[] sendRecivMsg(byte[] tiaBuf) throws Exception {

        Connection connection = null;
        Session session = null;
        try {
            ConnectionFactory queueConnectionFactory = new ActiveMQConnectionFactory(
                    ACTIVEMQ_GUEST_USERNAME, ACTIVEMQ_GUEST_PASSWORD, ACTIVEMQ_URL);
            connection = queueConnectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination requestDestination = session.createQueue("queue.dep.core.mbp.fcdep");
            MessageProducer sender = session.createProducer(requestDestination);
            sender.setDeliveryMode(DeliveryMode.PERSISTENT);
            BytesMessage message = session.createBytesMessage();
            message.writeBytes(tiaBuf);
            message.setStringProperty("JMSX_CHANNELID", "900");
            message.setStringProperty("JMSX_APPID", "mbp");
            message.setStringProperty("JMSX_BIZID", "mbp");
            message.setStringProperty("JMSX_TXCODE", "mbp");
            message.setStringProperty("JMSX_USERID", "mbp");
            message.setStringProperty("JMSX_PASSWORD", "mbp");
            sender.send(message);
            String sentMsgID = message.getJMSMessageID();
            long startMillis = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
            logger.info(sentMsgID + " StartTime:" + sdf.format(new Date()));
            String filter = "JMSCorrelationID = '" + sentMsgID + "'";
            logger.info(filter);

            Destination responseDestination = session.createQueue("queue.dep.core.fcdep.mbp");
            MessageConsumer receiver = session.createConsumer(responseDestination, filter);
            BytesMessage rtnMessage = (BytesMessage) receiver.receive(ACTIVEMQ_TIMEOUT);
            long endMillis = System.currentTimeMillis();
            logger.info("MessageID : " + sentMsgID + " 耗时：" + (endMillis - startMillis) + " mm. 超时限定：" + ACTIVEMQ_TIMEOUT);

            if (rtnMessage == null) {
                session.close();
                connection.close();
                logger.info(sentMsgID + " EndTime:" + sdf.format(new Date()));
                throw new RuntimeException("消息接收超时！");
            } else {
                session.close();
                connection.close();
                byte[] rtnBytes = new byte[(int) rtnMessage.getBodyLength()];
                rtnMessage.readBytes(rtnBytes);
                logger.info(sentMsgID + "\n 返回报文 " + new String(rtnBytes));

                return rtnBytes;
            }
        } catch (JMSException jmse) {
            throw new RuntimeException("MQ连接异常。");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
                if (session != null) {
                    session.close();
                    session = null;
                }
            } catch (JMSException jmsex) {
                throw new RuntimeException("MQ连接关闭异常。");
            }
        }
    }
}
