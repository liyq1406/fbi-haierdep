package org.fbi.dep.transform;

import org.fbi.dep.model.txn.TiaXml9009101;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
SBS 9009101 -> aa41
 */
public class TiaXml9009101Transform extends AbstractTiaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(TiaXml9009101Transform.class);

    @Override
    byte[] transform(String xml, String userid) {
        TiaXml9009101 xml9009101 = (TiaXml9009101) (new TiaXml9009101().getTia(xml));
        String termID = PropertyManager.getProperty("sbs.termid." + userid);
        byte[] bytes = SbsTxnDataTransform.convertToTxnN080(xml9009101, termID);
        return bytes;
    }
}
