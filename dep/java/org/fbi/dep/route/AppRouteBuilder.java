package org.fbi.dep.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.lang.StringUtils;
import org.fbi.dep.processor.TOA100Processor;
import org.fbi.dep.processor.TOA120Processor;
import org.fbi.dep.processor.TOA900Processor;
import org.fbi.dep.processor.TOA990Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-2-13
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
public class AppRouteBuilder extends RouteBuilder {

    private static Logger logger = LoggerFactory.getLogger(AppRouteBuilder.class);

    @Override
    public void configure() throws Exception {

        logger.info("开始添加交易通用队列路由规则...");
        // 交易队列 —— 通用接口
        from("jms:queue:queue.dep.app.in").process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                String correlationID = exchange.getIn().getHeader("JMSCorrelationID", String.class);
                if (StringUtils.isEmpty(correlationID)) {
                    exchange.getOut().setHeader("JMSCorrelationID", exchange.getIn().getMessageId());
                    logger.info("AppRouteBuilder JMSCorrelationID : " + exchange.getIn().getMessageId());

                } else {
                    exchange.getOut().setHeader("JMSCorrelationID", correlationID);
                }
                exchange.getOut().setHeader("JMSX_CHANNELID", exchange.getIn().getHeader("JMSX_CHANNELID"));
                exchange.getOut().setHeader("JMSX_APPID", exchange.getIn().getHeader("JMSX_APPID"));
                exchange.getOut().setHeader("JMSX_BIZID", exchange.getIn().getHeader("JMSX_BIZID"));
                exchange.getOut().setHeader("REQ_TXN_CODE", exchange.getIn().getHeader("REQ_TXN_CODE"));
                exchange.getOut().setHeader("JMSX_SRCMSGFLAG", exchange.getIn().getHeader("JMSX_SRCMSGFLAG"));
                exchange.getOut().setBody(exchange.getIn().getBody());
            }
        }).to("jms:queue:queue.dep.core.in");

        from("jms:queue:queue.dep.app.out")
                .choice()
                .when(simple("${header.JMSX_SRCMSGFLAG} == 'bcc.app'"))
                .to("jms:queue:queue.dep.core.fcdep.bcc")
                .when(simple("${header.JMSX_SRCMSGFLAG} == 'mbp.app'"))
                .to("jms:queue:queue.dep.core.fcdep.mbp")
                .when(simple("${header.JMSX_SRCMSGFLAG} == 'haierfip.app'"))
                .to("jms:queue:queue.dep.core.fcdep.haierfip")
                .when(simple("${header.JMSX_SRCMSGFLAG} == 'fcdep.app'"))
                .to("jms:queue:queue.dep.core.sbs.fcdep")
                .when(simple("${header.JMSX_SRCMSGFLAG} == 'ccbvips.app'"))
                .to("jms:queue:queue.dep.core.fcdep.ccbvips")
                .when(simple("${header.JMSX_CHANNELID} == '100'"))
                .process(new TOA100Processor())
                .to("jms:queue:queue.dep.object.out")
                .when(simple("${header.JMSX_CHANNELID} == '120'"))
                .process(new TOA120Processor())
                .to("jms:queue:queue.dep.object.out")
                .when(simple("${header.JMSX_CHANNELID} == '990'"))
                .process(new TOA990Processor())
                .to("jms:queue:queue.dep.object.out")
                .when(simple("${header.JMSX_CHANNELID} == '900'"))
                .process(new TOA900Processor())
                .to("jms:queue:queue.dep.object.out")
                .when(simple("${header.JMSX_CHANNELID} == '910'"))
                .to("jms:queue:queue.dep.object.out")
                .when(simple("${header.JMSX_CHANNELID} == '91001'"))
                .to("jms:queue:queue.dep.object.out")
                .otherwise()
                .to("jms:queue:queue.dep.route.error");
    }
}
