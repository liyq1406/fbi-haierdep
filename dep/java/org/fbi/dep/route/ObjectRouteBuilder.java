package org.fbi.dep.route;

import org.apache.camel.builder.RouteBuilder;
import org.fbi.dep.processor.TIA100Processor;
import org.fbi.dep.processor.TIA120Processor;
import org.fbi.dep.processor.TIA900Processor;
import org.fbi.dep.processor.TIA800Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-2-17
 * Time: 下午4:37
 * To change this template use File | Settings | File Templates.
 */
public class ObjectRouteBuilder extends RouteBuilder {

    private static Logger logger = LoggerFactory.getLogger(ObjectRouteBuilder.class);

    @Override
    public void configure() throws Exception {

        logger.info("开始添加具体交易队列路由规则...");
        // 外围系统-DEP交易
        from("jms:queue:queue.dep.object.in")
                .choice()
                .when(simple("${header.JMSX_CHANNELID} == '100'"))
                .process(new TIA100Processor())
                .to("jms:queue:queue.dep.app.in")
                .when(simple("${header.JMSX_CHANNELID} == '120'"))
                .process(new TIA120Processor())
                .to("jms:queue:queue.dep.app.in")
                .when(simple("${header.JMSX_CHANNELID} == '800'"))
                .process(new TIA800Processor())
                .to("jms:queue:queue.dep.app.in")
                .when(simple("${header.JMSX_CHANNELID} == '900'"))
                .process(new TIA900Processor())
                .to("jms:queue:queue.dep.app.in")
                .when(simple("${header.JMSX_CHANNELID} == '910'"))
                .to("jms:queue:queue.dep.app.in");

        from("jms:queue:queue.dep.object.out")
                .choice()
                .when(simple("${header.JMSX_SRCMSGFLAG} == 'hcsp.object'"))
                .to("jms:queue:queue.dep.fcdep.hcsp")
                .when(simple("${header.JMSX_SRCMSGFLAG} == 'xfnew.object'"))
                .to("jms:queue:queue.dep.fcdep.xfnew")
                .when(simple("${header.JMSX_SRCMSGFLAG} == 'qdzzjs.object'"))
                .to("jms:queue:queue.dep.fcdep.qdzzjs")
                .when(simple("${header.JMSX_SRCMSGFLAG} == 'haierfip.object'"))
                .to("jms:queue:queue.dep.fcdep.haierfip")
                .when(simple("${header.JMSX_SRCMSGFLAG} == 'haierrfm.object'"))
                .to("jms:queue:queue.dep.fcdep.haierrfm")
                .when(simple("${header.JMSX_SRCMSGFLAG} == 'fcdep.object'"))
                .to("jms:queue:queue.dep.out.fcdep.object")

                .otherwise()
                        // TODO Process ： transform object to xml
                .to("jms:queue:queue.dep.text.out");
    }
}
