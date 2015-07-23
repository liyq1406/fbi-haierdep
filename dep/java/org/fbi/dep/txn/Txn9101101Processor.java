package org.fbi.dep.txn;

import com.thoughtworks.xstream.converters.ConversionException;
import org.fbi.dep.component.jms.JmsObjMsgClient;
import org.fbi.dep.model.txn.TiaXml9101101;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by XIANGYANG on 2015-6-30.
 * 还款流程启动
 */

public class Txn9101101Processor extends AbstractTxnProcessor  {
    private static Logger logger = LoggerFactory.getLogger(Txn9101101Processor.class);

    public String process(String userid, String msgData) throws ClassNotFoundException,ConversionException, InstantiationException, IllegalAccessException, IOException {
        TiaXml9101101 tia = (TiaXml9101101) (new TiaXml9101101().getTia(msgData));

        // fip
        Object toa = null;
        try {
            toa = new JmsObjMsgClient().sendRecivMsg("910", "9101101", "fcdep",
                    "queue.dep.in.fcdep.object", "queue.dep.out.fcdep.object", tia);
        } catch (Exception e) {
            logger.error("FIP交易异常.", e);
            throw new RuntimeException(e);
        }

        return toa.toString();
    }
}
