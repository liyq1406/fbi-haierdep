package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.txn.sbs.TiaXml9009502;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 根据证件-客户开通网银登记9009502
 * (对应SBS- 8014交易)
 */
public class TiaXml9009502Transform extends AbstractTiaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(TiaXml9009502Transform.class);

    @Override
    byte[] transform(String xml, String userid) {
        TiaXml9009502 tia = (TiaXml9009502) (new TiaXml9009502().getTia(xml));
        String termID = PropertyManager.getProperty("sbs.termid." + userid);
        if (StringUtils.isEmpty(termID)) {
            termID = "MT01";
        }
        byte[] bytes = SbsTxnDataTransform.convertToTxn8014For9009502(tia, termID);
        return bytes;
    }
}
