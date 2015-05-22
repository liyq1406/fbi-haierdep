package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.txn.TiaXml9009051;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by XIANGYANG on 2015-5-21.
 * SBS 9009051 -> 8854 账户当日交易明细查询
 */

public class TiaXml9009051Transform extends AbstractTiaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(TiaXml9009051Transform.class);

    @Override
    byte[] transform(String xml, String userid) {
        TiaXml9009051 tia = (TiaXml9009051) (new TiaXml9009051().getTia(xml));
        String termID = PropertyManager.getProperty("sbs.termid." + userid);
        if (StringUtils.isEmpty(termID)) {
            termID = "MT01";
        }
        byte[] bytes = SbsTxnDataTransform.convertToTxn8854For9009051(tia, termID);
        return bytes;
    }
}
