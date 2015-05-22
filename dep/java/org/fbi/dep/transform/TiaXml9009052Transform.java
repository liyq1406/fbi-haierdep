package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.txn.TiaXml9009052;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by XIANGYANG on 2015-5-21.
 * SBS 9009052 -> 8854 账户历史交易明细查询
 */

public class TiaXml9009052Transform extends AbstractTiaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(TiaXml9009052Transform.class);

    @Override
    byte[] transform(String xml, String userid) {
        TiaXml9009052 tia = (TiaXml9009052) (new TiaXml9009052().getTia(xml));
        String termID = PropertyManager.getProperty("sbs.termid." + userid);
        if (StringUtils.isEmpty(termID)) {
            termID = "MT01";
        }
        byte[] bytes = SbsTxnDataTransform.convertToTxn8854For9009052(tia, termID);
        return bytes;
    }
}
