package org.fbi.dep.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.lang.StringUtils;
import org.fbi.dep.processor.TOA100Processor;
import org.fbi.dep.processor.TOA900Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO ��־��¼
 */
public class LogRouteBuilder extends RouteBuilder {

    private static Logger logger = LoggerFactory.getLogger(LogRouteBuilder.class);

    @Override
    public void configure() throws Exception {

        logger.info("��ʼ������־����·�� queue.dep.log.in ");
        // ���׶��� ���� ͨ�ýӿ�
        from("jms:queue:queue.dep.log.in").process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {

            }
        });
    }
}