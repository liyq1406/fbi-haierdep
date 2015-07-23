package org.fbi.dep.txn;

import com.thoughtworks.xstream.converters.ConversionException;
import org.fbi.dep.component.jms.JmsObjMsgClient;
import org.fbi.dep.model.txn.TiaXml910012001;
import org.fbi.dep.model.txn.TiaXml910012002;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by XIANGYANG on 2015-7-10.
 */

public class Txn910012002Processor extends AbstractTxnProcessor  {
    private static Logger logger = LoggerFactory.getLogger(Txn910012002Processor.class);

    public String process(String userid, String msgData) throws ClassNotFoundException,ConversionException, InstantiationException, IllegalAccessException, IOException {
        TiaXml910012002 tia = (TiaXml910012002) (new TiaXml910012002().getTia(msgData));

        // rfm-ta
        Object toa = null;
        try {
            toa = new JmsObjMsgClient().sendRecivMsg("91001", "910012002", "fcdep",
                    "queue.dep.in.fcdep.object", "queue.dep.out.fcdep.object", tia);
        } catch (Exception e) {
            logger.error("rfm-ta½»Ò×Òì³£.", e);
            throw new RuntimeException(e);
        }

        return toa.toString();
    }
}

