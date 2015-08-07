package org.fbi.dep.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.fbi.dep.component.jms.JmsRfmSktClient;
import org.fbi.dep.enums.EnuTaTxCode;
import org.fbi.dep.model.txn.*;
import org.fbi.dep.transform.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-2-13
 * Time: 下午9:51
 * To change this template use File | Settings | File Templates.
 */
public class Toa990Processor implements Processor {

    private static Logger logger = LoggerFactory.getLogger(Toa990Processor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        Message inMessage = exchange.getIn();
        String strMsg= (String) inMessage.getBody();
        // 发送到泰安房地产中心查询所求数据（利用Socket协议）
        String rtnmsg = new JmsRfmSktClient().processTxn(strMsg);
        System.out.println(rtnmsg);

        exchange.getOut().setHeader("JMSCorrelationID", inMessage.getHeader("JMSCorrelationID"));
        exchange.getOut().setHeader("JMSX_APPID", inMessage.getHeader("JMSX_APPID"));
        exchange.getOut().setHeader("JMSX_CHANNELID", inMessage.getHeader("JMSX_CHANNELID"));
        exchange.getOut().setHeader("JMSX_SRCMSGFLAG", inMessage.getHeader("JMSX_SRCMSGFLAG"));

        String strRtnTxCode=strMsg.substring(7,11);
        if(EnuTaTxCode.TRADE_1001.getCode().equals(strRtnTxCode)){
            Toa9901001Transform toa9901001TransformTemp =new Toa9901001Transform();
            Toa9901001 toa9901001= toa9901001TransformTemp.transform(rtnmsg,"");
            exchange.getOut().setBody(toa9901001);
        }else if(EnuTaTxCode.TRADE_1002.getCode().equals(strRtnTxCode)){
            Toa9901002Transform tOA9901002ransformTemp=new Toa9901002Transform();
            Toa9901002 toa9901002=tOA9901002ransformTemp.transform(rtnmsg,"");
            exchange.getOut().setBody(toa9901002);
        }else if(EnuTaTxCode.TRADE_1002.getCode().equals(strRtnTxCode)){
            Toa9901002Transform tOA9901002ransformTemp=new Toa9901002Transform();
            Toa9901002 toa9901002=tOA9901002ransformTemp.transform(rtnmsg,"");
            exchange.getOut().setBody(toa9901002);
        }else if(EnuTaTxCode.TRADE_2001.getCode().equals(strRtnTxCode)){
            Toa9902001Transform tOA9902001ransformTemp=new Toa9902001Transform();
            Toa9902001 toa9902001=tOA9902001ransformTemp.transform(rtnmsg,"");
            exchange.getOut().setBody(toa9902001);
        }else if(EnuTaTxCode.TRADE_2002.getCode().equals(strRtnTxCode)){
            Toa9902002Transform tOA9902002ransformTemp=new Toa9902002Transform();
            Toa9902002 toa9902002=tOA9902002ransformTemp.transform(rtnmsg,"");
            exchange.getOut().setBody(toa9902002);
        }else if(EnuTaTxCode.TRADE_2011.getCode().equals(strRtnTxCode)){
            Toa9902011Transform tOA9902011ransformTemp=new Toa9902011Transform();
            Toa9902011 toa9902011=tOA9902011ransformTemp.transform(rtnmsg,"");
            exchange.getOut().setBody(toa9902011);
        }else if(EnuTaTxCode.TRADE_2101.getCode().equals(strRtnTxCode)){
            Toa9902101Transform tOA9902101ransformTemp=new Toa9902101Transform();
            Toa9902101 toa9902101=tOA9902101ransformTemp.transform(rtnmsg,"");
            exchange.getOut().setBody(toa9902101);
        }else if(EnuTaTxCode.TRADE_2102.getCode().equals(strRtnTxCode)){
            Toa9902102Transform tOA9902102ransformTemp=new Toa9902102Transform();
            Toa9902102 toa9902102=tOA9902102ransformTemp.transform(rtnmsg,"");
            exchange.getOut().setBody(toa9902102);
        }else if(EnuTaTxCode.TRADE_2111.getCode().equals(strRtnTxCode)){
            Toa9902111Transform tOA9902111ransformTemp=new Toa9902111Transform();
            Toa9902111 toa9902111=tOA9902111ransformTemp.transform(rtnmsg,"");
            exchange.getOut().setBody(toa9902111);
        }else if(EnuTaTxCode.TRADE_2201.getCode().equals(strRtnTxCode)){
            Toa9902201Transform tOA9902201ransformTemp=new Toa9902201Transform();
            Toa9902201 toa9902201=tOA9902201ransformTemp.transform(rtnmsg,"");
            exchange.getOut().setBody(toa9902201);
        }else if(EnuTaTxCode.TRADE_2202.getCode().equals(strRtnTxCode)){
            Toa9902202Transform tOA9902202ransformTemp=new Toa9902202Transform();
            Toa9902202 toa9902202=tOA9902202ransformTemp.transform(rtnmsg,"");
            exchange.getOut().setBody(toa9902202);
        }else if(EnuTaTxCode.TRADE_2211.getCode().equals(strRtnTxCode)){
            Toa9902211Transform tOA9902211ransformTemp=new Toa9902211Transform();
            Toa9902211 toa9902211=tOA9902211ransformTemp.transform(rtnmsg,"");
            exchange.getOut().setBody(toa9902211);
        }else if(EnuTaTxCode.TRADE_2501.getCode().equals(strRtnTxCode)){
            Toa9902501Transform tOA9902501ransformTemp=new Toa9902501Transform();
            Toa9902501 toa9902501=tOA9902501ransformTemp.transform(rtnmsg,"");
            exchange.getOut().setBody(toa9902501);
        }
    }
}
