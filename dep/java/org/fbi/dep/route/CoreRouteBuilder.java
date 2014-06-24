package org.fbi.dep.route;

import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.fbi.dep.processor.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ���ݽ���ƽ̨���Ķ���·�ɹ���
 */
public class CoreRouteBuilder extends RouteBuilder {

    private static Logger logger = LoggerFactory.getLogger(CoreRouteBuilder.class);

    @Override
    public void configure() throws Exception {

        logger.info("��ʼ���Ӻ��Ķ���·�ɹ���...");
        from("jms:queue:queue.dep.core.in")
                .choice()
                .when(simple("${header.JMSX_CHANNELID} == '900'"))
                .to("jms:queue:queue.dep.core.sbs.in")
                .when(simple("${header.JMSX_CHANNELID} == '910'"))
                .to("jms:queue:queue.dep.core.fip.in")
                .when(simple("${header.JMSX_CHANNELID} == '100'"))
                .to("jms:queue:queue.dep.core.unionpay.in")
                .when(simple("${header.JMSX_CHANNELID} == '200'"))
                .to("jms:queue:queue.dep.core.ccbvips.in")
                .when(simple("${header.JMSX_CHANNELID} == '300'"))
                .to("jms:queue:queue.dep.core.eai.in")
                .otherwise()
                .to("jms:queue:queue.dep.route.error");

        // sbs
        from("jms:queue:queue.dep.core.sbs.in?concurrentConsumers=20")
                .process(new Core900Processor())
                .to("jms:queue:queue.dep.core.sbs.out");
        from("jms:queue:queue.dep.core.sbs.out").to("jms:queue:queue.dep.core.out");

        // unionpay
        from("jms:queue:queue.dep.core.unionpay.in?concurrentConsumers=10")
                .process(new Core100Processor())
                .to("jms:queue:queue.dep.core.unionpay.out");
        from("jms:queue:queue.dep.core.unionpay.out").to("jms:queue:queue.dep.core.out");

        // ccbvips
        from("jms:queue:queue.dep.core.ccbvips.in")
                .process(new Core200Processor())
                .to("jms:queue:queue.dep.core.ccbvips.out");
        from("jms:queue:queue.dep.core.ccbvips.out").to("jms:queue:queue.dep.core.out");

        // eai
        from("jms:queue:queue.dep.core.eai.in").doTry()
                .process(new Core300Processor())
                .to("jms:queue:queue.dep.core.eai.out").doCatch(RuntimeCamelException.class);
        from("jms:queue:queue.dep.core.eai.out").to("jms:queue:queue.dep.core.out");

        // fip
        /*
        fip����queue.dep.core.fip.in���У�����queue.dep.core.fip.out����
        fip�䵱Core910Processor�Ľ�ɫ
         */
        from("jms:queue:queue.dep.core.fip.out").to("jms:queue:queue.dep.core.out");

        from("jms:queue:queue.dep.core.out").to("jms:queue:queue.dep.app.out");
        
    }

}