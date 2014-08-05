package org.fbi.dep.component.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * bytesmessage
 */
public class JmsBytesClient {

    private static final Logger logger = LoggerFactory.getLogger(JmsBytesClient.class);
    private static String ACTIVEMQ_URL = PropertyManager.getProperty("dep.activemq.url");
    private static String ACTIVEMQ_GUEST_USERNAME = PropertyManager.getProperty("dep.activemq.guest.username");
    private static String ACTIVEMQ_GUEST_PASSWORD = PropertyManager.getProperty("dep.activemq.guest.password");
    private static int ACTIVEMQ_TIMEOUT = PropertyManager.getIntProperty("dep.activemq.request.timeout");

    public byte[] sendRecivMsg(String channelID, String bizID, String appID, String txncode, String userid,
                               String reqQueue, String resQueue, byte[] tiaBuf) {
        Connection connection = null;
        Session session = null;
        try {
            ConnectionFactory queueConnectionFactory = new ActiveMQConnectionFactory(
                    ACTIVEMQ_GUEST_USERNAME, ACTIVEMQ_GUEST_PASSWORD, ACTIVEMQ_URL);
            connection = queueConnectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination requestDestination = session.createQueue(reqQueue);
            MessageProducer sender = session.createProducer(requestDestination);
            sender.setDeliveryMode(DeliveryMode.PERSISTENT);
            BytesMessage message = session.createBytesMessage();
            message.writeBytes(tiaBuf);
            message.setStringProperty("JMSX_CHANNELID", channelID);
            message.setStringProperty("JMSX_APPID", appID);
            message.setStringProperty("JMSX_BIZID", bizID);
            message.setStringProperty("JMSX_TXCODE", txncode);
            message.setStringProperty("JMSX_USERID", userid);
            message.setStringProperty("JMSX_PASSWORD", userid);
            sender.send(message);
            String sentMsgID = message.getJMSMessageID();
            logger.info("MessageID : " + sentMsgID);
            String filter = "JMSCorrelationID = '" + sentMsgID + "'";
            Destination responseDestination = session.createQueue(resQueue);
            MessageConsumer receiver = session.createConsumer(responseDestination, filter);
            BytesMessage rtnMessage = (BytesMessage) receiver.receive(ACTIVEMQ_TIMEOUT);
            if (rtnMessage == null) {
                session.close();
                connection.close();
                throw new RuntimeException("7001|MQ消息接收超时！");
            } else {
                session.close();
                connection.close();
                byte[] rtnBytes = new byte[(int) rtnMessage.getBodyLength()];
                rtnMessage.readBytes(rtnBytes);
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
