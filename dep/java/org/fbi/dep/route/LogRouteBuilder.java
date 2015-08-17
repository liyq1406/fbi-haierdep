package org.fbi.dep.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO 日志记录
 */
public class LogRouteBuilder extends RouteBuilder {

    private static Logger logger = LoggerFactory.getLogger(LogRouteBuilder.class);

    @Override
    public void configure() throws Exception {

        logger.info("开始添加日志队列路由 queue.dep.log.in ");
        // 交易队列 —— 通用接口
        from("jms:queue:queue.dep.log.in").process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {

            }
        });
    }
}
