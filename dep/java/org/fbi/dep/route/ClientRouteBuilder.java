package org.fbi.dep.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 外围系统队列路由规则
 */
public class ClientRouteBuilder extends RouteBuilder {

    private static Logger logger = LoggerFactory.getLogger(ClientRouteBuilder.class);

    @Override
    public void configure() throws Exception {

        logger.info("开始添加外围系统队列路由规则...");
        from("jms:queue:queue.dep.hcsp.fcdep")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setHeader("JMSCorrelationID", exchange.getIn().getMessageId());
                        exchange.getOut().setHeader("JMSX_CHANNELID", exchange.getIn().getHeader("JMSX_CHANNELID"));
                        exchange.getOut().setHeader("JMSX_APPID", exchange.getIn().getHeader("JMSX_APPID"));
                        exchange.getOut().setHeader("JMSX_BIZID", exchange.getIn().getHeader("JMSX_BIZID"));
                        exchange.getOut().setHeader("JMSX_SRCMSGFLAG", "hcsp.object");
                        exchange.getOut().setBody(exchange.getIn().getBody());
                    }
                })
                .to("jms:queue:queue.dep.object.in");

        from("jms:queue:queue.dep.haierfip.fcdep")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setHeader("JMSCorrelationID", exchange.getIn().getMessageId());
                        exchange.getOut().setHeader("JMSX_CHANNELID", exchange.getIn().getHeader("JMSX_CHANNELID"));
                        exchange.getOut().setHeader("JMSX_APPID", exchange.getIn().getHeader("JMSX_APPID"));
                        exchange.getOut().setHeader("JMSX_BIZID", exchange.getIn().getHeader("JMSX_BIZID"));
                        exchange.getOut().setHeader("JMSX_SRCMSGFLAG", "haierfip.object");
                        exchange.getOut().setBody(exchange.getIn().getBody());
                    }
                })
                .to("jms:queue:queue.dep.object.in");

        from("jms:queue:queue.dep.in.fcdep.object")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setHeader("JMSCorrelationID", exchange.getIn().getMessageId());
                        logger.info("ClientRouteBuilder JMSCorrelationID : " + exchange.getIn().getMessageId());
                        exchange.getOut().setHeader("JMSX_CHANNELID", exchange.getIn().getHeader("JMSX_CHANNELID"));
                        exchange.getOut().setHeader("JMSX_APPID", exchange.getIn().getHeader("JMSX_APPID"));
                        exchange.getOut().setHeader("JMSX_BIZID", exchange.getIn().getHeader("JMSX_BIZID"));
                        exchange.getOut().setHeader("JMSX_SRCMSGFLAG", "fcdep.object");
                        exchange.getOut().setBody(exchange.getIn().getBody());
                    }
                })
                .to("jms:queue:queue.dep.object.in");

        // --------------------------------

        from("jms:queue:queue.dep.core.bcc.fcdep")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setHeader("JMSCorrelationID", exchange.getIn().getMessageId());
                        exchange.getOut().setHeader("JMSX_CHANNELID", exchange.getIn().getHeader("JMSX_CHANNELID"));
                        exchange.getOut().setHeader("JMSX_APPID", exchange.getIn().getHeader("JMSX_APPID"));
                        exchange.getOut().setHeader("JMSX_BIZID", exchange.getIn().getHeader("JMSX_BIZID"));
                        exchange.getOut().setHeader("JMSX_SRCMSGFLAG", "bcc.app");
                        exchange.getOut().setBody(exchange.getIn().getBody());
                    }
                })
                .to("jms:queue:queue.dep.app.in");

        from("jms:queue:queue.dep.core.haierfip.fcdep")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setHeader("JMSCorrelationID", exchange.getIn().getMessageId());
                        exchange.getOut().setHeader("JMSX_CHANNELID", exchange.getIn().getHeader("JMSX_CHANNELID"));
                        exchange.getOut().setHeader("JMSX_APPID", exchange.getIn().getHeader("JMSX_APPID"));
                        exchange.getOut().setHeader("JMSX_BIZID", exchange.getIn().getHeader("JMSX_BIZID"));
                        exchange.getOut().setHeader("JMSX_SRCMSGFLAG", "haierfip.app");
                        exchange.getOut().setBody(exchange.getIn().getBody());
                    }
                })
                .to("jms:queue:queue.dep.app.in");

        // 2012-06-06
        from("jms:queue:queue.dep.core.mbp.fcdep?concurrentConsumers=20")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setHeader("JMSCorrelationID", exchange.getIn().getMessageId());

                        exchange.getOut().setHeader("JMSX_CHANNELID", exchange.getIn().getHeader("JMSX_CHANNELID"));
                        exchange.getOut().setHeader("JMSX_APPID", exchange.getIn().getHeader("JMSX_APPID"));
                        exchange.getOut().setHeader("JMSX_BIZID", exchange.getIn().getHeader("JMSX_BIZID"));
                        exchange.getOut().setHeader("JMSX_SRCMSGFLAG", "mbp.app");
                        exchange.getOut().setBody(exchange.getIn().getBody());
                    }
                })
                .to("jms:queue:queue.dep.app.in");

        from("jms:queue:queue.dep.core.ccbvips.fcdep")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setHeader("JMSCorrelationID", exchange.getIn().getMessageId());
                        exchange.getOut().setHeader("JMSX_CHANNELID", exchange.getIn().getHeader("JMSX_CHANNELID"));
                        exchange.getOut().setHeader("JMSX_APPID", exchange.getIn().getHeader("JMSX_APPID"));
                        exchange.getOut().setHeader("JMSX_BIZID", exchange.getIn().getHeader("JMSX_BIZID"));
                        exchange.getOut().setHeader("JMSX_SRCMSGFLAG", "ccbvips.app");
                        exchange.getOut().setBody(exchange.getIn().getBody());
                    }
                })
                .to("jms:queue:queue.dep.app.in");

        from("jms:queue:queue.dep.xfnew.fcdep")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setHeader("JMSCorrelationID", exchange.getIn().getMessageId());
                        exchange.getOut().setHeader("JMSX_CHANNELID", exchange.getIn().getHeader("JMSX_CHANNELID"));
                        exchange.getOut().setHeader("JMSX_APPID", exchange.getIn().getHeader("JMSX_APPID"));
                        exchange.getOut().setHeader("JMSX_BIZID", exchange.getIn().getHeader("JMSX_BIZID"));
                        exchange.getOut().setHeader("JMSX_SRCMSGFLAG", "xfnew.object");
                        exchange.getOut().setBody(exchange.getIn().getBody());
                    }
                })
                .to("jms:queue:queue.dep.object.in");

        from("jms:queue:queue.dep.qdzzjs.fcdep")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setHeader("JMSCorrelationID", exchange.getIn().getMessageId());
                        exchange.getOut().setHeader("JMSX_CHANNELID", exchange.getIn().getHeader("JMSX_CHANNELID"));
                        exchange.getOut().setHeader("JMSX_APPID", exchange.getIn().getHeader("JMSX_APPID"));
                        exchange.getOut().setHeader("JMSX_BIZID", exchange.getIn().getHeader("JMSX_BIZID"));
                        exchange.getOut().setHeader("JMSX_SRCMSGFLAG", "qdzzjs.object");
                        exchange.getOut().setBody(exchange.getIn().getBody());
                    }
                })
                .to("jms:queue:queue.dep.object.in");

        from("jms:queue:queue.dep.core.fcdep.sbs")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setHeader("JMSCorrelationID", exchange.getIn().getMessageId());
                        exchange.getOut().setHeader("JMSX_CHANNELID", exchange.getIn().getHeader("JMSX_CHANNELID"));
                        exchange.getOut().setHeader("JMSX_APPID", exchange.getIn().getHeader("JMSX_APPID"));
                        exchange.getOut().setHeader("JMSX_BIZID", exchange.getIn().getHeader("JMSX_BIZID"));
                        exchange.getOut().setHeader("JMSX_SRCMSGFLAG", "fcdep.app");
                        exchange.getOut().setBody(exchange.getIn().getBody());
                    }
                })
                .to("jms:queue:queue.dep.app.in");
    }
}
