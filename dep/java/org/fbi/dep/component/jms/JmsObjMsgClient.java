package org.fbi.dep.component.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.fbi.dep.enums.TxnRtnCode;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-6-14
 * Time: 上午9:27
 * To change this template use File | Settings | File Templates.
 */
public class JmsObjMsgClient {

    private static final Logger logger = LoggerFactory.getLogger(JmsObjMsgClient.class);
    private static String ACTIVEMQ_URL = PropertyManager.getProperty("dep.activemq.url");
    private static String ACTIVEMQ_GUEST_USERNAME = PropertyManager.getProperty("dep.activemq.guest.username");
    private static String ACTIVEMQ_GUEST_PASSWORD = PropertyManager.getProperty("dep.activemq.guest.password");
    private static int ACTIVEMQ_TIMEOUT = PropertyManager.getIntProperty("dep.activemq.request.timeout");

    public Object sendRecivMsg(String channelID, String bizID, String appID,
                               String reqQueue, String resQueue, Serializable msg) throws Exception {

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
            ObjectMessage message = session.createObjectMessage(msg);
            message.setStringProperty("JMSX_CHANNELID", channelID);
            message.setStringProperty("JMSX_APPID", appID);
            message.setStringProperty("JMSX_BIZID", bizID);
            message.setStringProperty("JMSX_TXCODE", bizID);
            message.setStringProperty("JMSX_USERID", "fcdep");
            message.setStringProperty("JMSX_PASSWORD", "fcdep");
            sender.send(message);
            String sentMsgID = message.getJMSMessageID();
            logger.info("JmsObjMsgClient MessageID : " + sentMsgID);
            String filter = "JMSCorrelationID = '" + sentMsgID + "'";
            Destination responseDestination = session.createQueue(resQueue);
//        MessageConsumer receiver = session.createConsumer(responseDestination);
            MessageConsumer receiver = session.createConsumer(responseDestination, filter);
            ObjectMessage rtnMessage = (ObjectMessage) receiver.receive(ACTIVEMQ_TIMEOUT);
            logger.info("RtnMsg:" + rtnMessage.getStringProperty("JMSCorrelationID"));
            if (rtnMessage == null) {
                session.close();
                connection.close();
                throw new RuntimeException(TxnRtnCode.SERVER_EXCEPTION.toRtnMsg());
            } else {
                session.close();
                connection.close();
                return rtnMessage.getObject();
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
