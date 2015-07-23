package org.fbi.dep.txn;

import com.thoughtworks.xstream.converters.ConversionException;
import org.fbi.dep.component.jms.JmsObjMsgClient;
import org.fbi.dep.model.txn.TiaXml9101102;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by XIANGYANG on 2015-6-30.
 * 还款流程查询
 */

public class Txn9101102Processor extends AbstractTxnProcessor  {
    private static Logger logger = LoggerFactory.getLogger(Txn9101102Processor.class);

    public String process(String userid, String msgData) throws ClassNotFoundException,ConversionException, InstantiationException, IllegalAccessException, IOException {
        TiaXml9101102 tia = (TiaXml9101102) (new TiaXml9101102().getTia(msgData));

        // fip
        Object toa = null;
        try {
            toa = new JmsObjMsgClient().sendRecivMsg("910", "9101102", "fcdep",
                    "queue.dep.in.fcdep.object", "queue.dep.out.fcdep.object", tia);
        } catch (Exception e) {
            logger.error("FIP交易异常.", e);
            throw new RuntimeException(e);
        }

        return toa.toString();
    }
}
