package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.txn.sbs.TiaXml9009506;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通知存款-支取结清9009506
 * (对应SBS-a13a交易)
 */
public class TiaXml9009506Transform extends AbstractTiaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(TiaXml9009506Transform.class);

    @Override
    byte[] transform(String xml, String userid) {
        TiaXml9009506 tia = (TiaXml9009506) (new TiaXml9009506().getTia(xml));
        String termID = PropertyManager.getProperty("sbs.termid." + userid);
        if (StringUtils.isEmpty(termID)) {
            termID = "MT01";
        }
        byte[] bytes = SbsTxnDataTransform.convertToTxna13aFor9009506(tia, termID);
        return bytes;
    }
}
