package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.txn.TiaXml9009001;
import org.fbi.dep.model.txn.TiaXml9009002;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
SBS 单笔对外支付9009002
 */
public class TiaXml9009002Transform extends AbstractTiaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(TiaXml9009002Transform.class);

    @Override
    byte[] transform(String xml, String userid) {
        TiaXml9009002 xml9009002 = (TiaXml9009002) (new TiaXml9009002().getTia(xml));
        String termID = PropertyManager.getProperty("sbs.termid." + userid);
        if (StringUtils.isEmpty(termID)) {
            termID = "MT01";
        }
        byte[] bytes = SbsTxnDataTransform.convertToTxnN120(xml9009002, termID);
        return bytes;
    }
}
