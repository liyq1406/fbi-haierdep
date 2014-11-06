package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.txn.TiaXml9009401;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
  贷款开户 对应SBS-8124交易
 */
public class TiaXml9009401Transform extends AbstractTiaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(TiaXml9009401Transform.class);

    @Override
    byte[] transform(String xml, String userid) {
        TiaXml9009401 tia = (TiaXml9009401) (new TiaXml9009401().getTia(xml));
        String termID = PropertyManager.getProperty("sbs.termid." + userid);
        if (StringUtils.isEmpty(termID)) {
            termID = "MT01";
        }
        byte[] bytes = SbsTxnDataTransform.convertToTxn8124(tia, termID);
        return bytes;
    }
}
