package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.txn.TiaXml9009062;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by XIANGYANG on 2015-5-11.
 * SBS 9009062 -> 8118 根据账号查询账户信息
 */

public class TiaXml9009062Transform extends AbstractTiaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(TiaXml9009062Transform.class);

    @Override
    byte[] transform(String xml, String userid) {
        TiaXml9009062 tia = (TiaXml9009062) (new TiaXml9009062().getTia(xml));
        String termID = PropertyManager.getProperty("sbs.termid." + userid);
        if (StringUtils.isEmpty(termID)) {
            termID = "MT01";
        }
        byte[] bytes = SbsTxnDataTransform.convertToTxn8118(tia, termID);
        return bytes;
    }
}
