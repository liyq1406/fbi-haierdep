package org.fbi.dep.txn;

import com.thoughtworks.xstream.converters.ConversionException;
import org.fbi.dep.component.jms.JmsObjMsgClient;
import org.fbi.dep.model.txn.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by hanjianlong on 2015-9-8.
 * FEB发起的对账结果查询
 */

public class Txn9100002Processor extends AbstractTxnProcessor  {
    private static Logger logger = LoggerFactory.getLogger(Txn9100002Processor.class);

    public String process(String userid, String msgData) throws ClassNotFoundException,ConversionException, InstantiationException, IllegalAccessException, IOException {
        logger.error("接收FEB端发送过来的报文："+msgData);
        TiaXml9100002 tia = (TiaXml9100002)(new TiaXml9100002().toBean(msgData));
        logger.error("通过核心队列转发到RFM系统");
        try {
            Object toa = new JmsObjMsgClient().sendRecivMsg("91001",tia.INFO.getTXNCODE(), "febdep",
                    "queue.dep.in.fcdep.object", "queue.dep.out.fcdep.object", tia);
            ToaXml9100002 toaXml9100002=(ToaXml9100002)toa;
            return toaXml9100002.toString();
        } catch (Exception e) {
            logger.error("接收FEB端发送过来的对账执行命令异常.", e);
            throw new RuntimeException(e);
        }
    }
}

