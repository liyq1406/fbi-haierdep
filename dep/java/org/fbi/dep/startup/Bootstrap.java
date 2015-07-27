package org.fbi.dep.startup;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.util.jndi.JndiContext;
import org.fbi.dep.component.netty.*;
import org.fbi.dep.route.*;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.ConnectionFactory;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-2-13
 * Time: 上午11:05
 * To change this template use File | Settings | File Templates.
 */
public class Bootstrap {

    private static Logger logger = LoggerFactory.getLogger(Bootstrap.class);
    private static String ACTIVE_MQ_USER_NAME = PropertyManager.getProperty("dep.activemq.admin.username");
    private static String ACTIVE_MQ_PASSWORD = PropertyManager.getProperty("dep.activemq.admin.password");
    private static String ACTIVE_MQ_BORKER_URL = PropertyManager.getProperty("dep.activemq.brokerURL");
//    private static String UNIONPAY_SBS_SOCKET_PORT = PropertyManager.getProperty("dep.localhost.unionpay.sbs.port");
//    private static String UNIONPAY_SOCKET_PORT = PropertyManager.getProperty("dep.localhost.unionpay.port");
    private static String SBS_SOCKET_PORT = PropertyManager.getProperty("dep.localhost.sbs.port");
    //private static String RFM_SOCKET_PORT = PropertyManager.getProperty("socket.rfm.port");
    private static String SMS_SOCKET_PORT = PropertyManager.getProperty("dep.localhost.sms.port");
    private static String SBS_HTTP_PORT = PropertyManager.getProperty("dep.localhost.sbs.http.port");


    public static void main(String[] args) throws Exception {

        JndiRegistry registry = new JndiRegistry(new JndiContext());

        MbpSktMessageDecoder nettyMbpSktMessageDecoder = new MbpSktMessageDecoder();
        MbpSktMessageEncoder nettyMbpSktMessageEncoder = new MbpSktMessageEncoder();
        registry.bind("mbp-decoder", nettyMbpSktMessageDecoder);
        registry.bind("mbp-encoder", nettyMbpSktMessageEncoder);
        DepSktMessageDecoder nettyDepSktMessageDecoder = new DepSktMessageDecoder();
        DepSktMessageEncoder nettyDepSktMessageEncoder = new DepSktMessageEncoder();
        registry.bind("skt-decoder", nettyDepSktMessageDecoder);
        registry.bind("skt-encoder", nettyDepSktMessageEncoder);
        CamelContext context = new DefaultCamelContext(registry);

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVE_MQ_USER_NAME, ACTIVE_MQ_PASSWORD,
                ACTIVE_MQ_BORKER_URL);
        context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        RouteBuilder coreRouteBuilder = new CoreRouteBuilder();
        RouteBuilder appRouteBuilder = new AppRouteBuilder();
        RouteBuilder objectRouteBuilder = new ObjectRouteBuilder();
        RouteBuilder clientRouteBuilder = new ClientRouteBuilder();
        RouteBuilder mbpRouteBuilder = new MbpDztzRouteBuilder();
//        RouteBuilder upSktRouteBuilder = new PayoutDirectSktRouteBuilder(UNIONPAY_SOCKET_PORT);
//        RouteBuilder upSbsRouteBuilder = new PayoutIndirectSktRouteBuilder(UNIONPAY_SBS_SOCKET_PORT);
        RouteBuilder sbsSktRouteBuilder = new SbsSktRouteBuilder(SBS_SOCKET_PORT);
        // 房产中心没有主动发起到我
        //RouteBuilder rfmSktRouteBuilder = new RfmSktRouteBuilder(RFM_SOCKET_PORT);
        RouteBuilder smsSktRouteBuilder = new SmsSktRouteBuilder(SMS_SOCKET_PORT);
        RouteBuilder sbsHttpRouteBuilder = new SbsHttpRouteBuilder(SBS_HTTP_PORT);
        try {
            logger.info("CamelContext开始添加路由...");
            context.addRoutes(coreRouteBuilder);
            context.addRoutes(appRouteBuilder);
            context.addRoutes(objectRouteBuilder);
            context.addRoutes(clientRouteBuilder);
            context.addRoutes(mbpRouteBuilder);
//            context.addRoutes(upSktRouteBuilder);
//            context.addRoutes(upSbsRouteBuilder);
            context.addRoutes(sbsSktRouteBuilder);
            //context.addRoutes(rfmSktRouteBuilder);
            context.addRoutes(smsSktRouteBuilder);
            context.addRoutes(sbsHttpRouteBuilder);
            logger.info("CamelContext开始启动...");
            context.start();
            logger.info("CamelContext已成功启动...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
