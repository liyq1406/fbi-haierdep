package org.fbi.dep.txn;

import com.thoughtworks.xstream.converters.ConversionException;
import org.fbi.dep.component.jms.JmsObjMsgClient;
import org.fbi.dep.model.txn.TiaXml9109003;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by XIANGYANG on 2015-6-2.
 * DEP-FIP,查询建行到账明细
 */

public class Txn9109003Processor extends AbstractTxnProcessor {
    private static Logger logger = LoggerFactory.getLogger(Txn9109003Processor.class);

    public String process(String userid, String msgData) throws ClassNotFoundException,ConversionException, InstantiationException, IllegalAccessException, IOException {
        TiaXml9109003 tia = (TiaXml9109003) (new TiaXml9109003().getTia(msgData));

        // fip
        Object toa = null;
        try {
            toa = new JmsObjMsgClient().sendRecivMsg("910", "9109003", "fcdep",
                    "queue.dep.in.fcdep.object", "queue.dep.out.fcdep.object", tia);
        } catch (Exception e) {
            logger.error("FIP交易异常.", e);
            throw new RuntimeException(e);
        }

        return toa.toString();
    }

}
