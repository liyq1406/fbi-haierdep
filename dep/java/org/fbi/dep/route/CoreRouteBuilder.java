package org.fbi.dep.route;

import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.fbi.dep.processor.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据交换平台核心队列路由规则
 */
public class CoreRouteBuilder extends RouteBuilder {

    private static Logger logger = LoggerFactory.getLogger(CoreRouteBuilder.class);

    @Override
    public void configure() throws Exception {

        logger.info("开始添加核心队列路由规则...");
        from("jms:queue:queue.dep.core.in")
                .choice()
                .when(simple("${header.JMSX_CHANNELID} == '900'"))
                .to("jms:queue:queue.dep.core.sbs.in")
                .when(simple("${header.JMSX_CHANNELID} == '910'"))
                .to("jms:queue:queue.dep.core.fip.in")
                .when(simple("${header.JMSX_CHANNELID} == '91001'"))
                .to("jms:queue:queue.dep.core.fip.rfm.in")
                .when(simple("${header.JMSX_CHANNELID} == '100'"))
                .to("jms:queue:queue.dep.core.unionpay.in")
                .when(simple("${header.JMSX_CHANNELID} == '120'"))
                .to("jms:queue:queue.dep.core.allinpay.in")
                .when(simple("${header.JMSX_CHANNELID} == '200'"))
                .to("jms:queue:queue.dep.core.ccbvips.in")
                .when(simple("${header.JMSX_CHANNELID} == '300'"))
                .to("jms:queue:queue.dep.core.eai.in")
                .when(simple("${header.JMSX_CHANNELID} == '920'"))
                .to("jms:queue:queue.dep.core.mbp.in")
                .otherwise()
                .to("jms:queue:queue.dep.route.error");

        // sbs
        from("jms:queue:queue.dep.core.sbs.in?concurrentConsumers=20")
                .process(new Core900Processor())
                .to("jms:queue:queue.dep.core.sbs.out");
        from("jms:queue:queue.dep.core.sbs.out").to("jms:queue:queue.dep.core.out");

        // unionpay
        from("jms:queue:queue.dep.core.unionpay.in?concurrentConsumers=20")
                .process(new Core100Processor())
                .to("jms:queue:queue.dep.core.unionpay.out");
        from("jms:queue:queue.dep.core.unionpay.out").to("jms:queue:queue.dep.core.out");

        //TODO allinpay start
        from("jms:queue:queue.dep.core.allinpay.in?concurrentConsumers=20")
                .process(new Core120Processor())
                .to("jms:queue:queue.dep.core.allinpay.out");
        from("jms:queue:queue.dep.core.allinpay.out").to("jms:queue:queue.dep.core.out");

        // ccbvips
        from("jms:queue:queue.dep.core.ccbvips.in?concurrentConsumers=20")
                .process(new Core200Processor())
                .to("jms:queue:queue.dep.core.ccbvips.out");
        from("jms:queue:queue.dep.core.ccbvips.out").to("jms:queue:queue.dep.core.out");

        // eai
        from("jms:queue:queue.dep.core.eai.in?concurrentConsumers=20").doTry()
                .process(new Core300Processor())
                .to("jms:queue:queue.dep.core.eai.out").doCatch(RuntimeCamelException.class);
        from("jms:queue:queue.dep.core.eai.out").to("jms:queue:queue.dep.core.out");

        // mbp
        from("jms:queue:queue.dep.core.mbp.in?concurrentConsumers=20")
                .process(new Core920Processor())
                .to("jms:queue:queue.dep.core.mbp.out");
        from("jms:queue:queue.dep.core.mbp.out").to("jms:queue:queue.dep.core.out");

        // fip
        /*
        fip监听queue.dep.core.fip.in队列，返回queue.dep.core.fip.out队列
        fip充当Core910Processor的角色
         */
        from("jms:queue:queue.dep.core.fip.out").to("jms:queue:queue.dep.core.out");

        from("jms:queue:queue.dep.core.out").to("jms:queue:queue.dep.app.out");

        // rfm
        /*
        rfm监听queue.dep.core.rfm.in队列，返回queue.dep.core.rfm.out队列
        rfm充当Core910Processor的角色
         */
        from("jms:queue:queue.dep.core.rfm.out").to("jms:queue:queue.dep.core.out");

        from("jms:queue:queue.dep.core.out").to("jms:queue:queue.dep.app.out");

        from("jms:queue:queue.rfm.dep")
                .process(new TIARFMProcessor())
                .to("jms:queue:queue.dep.rfm");
    }

}
