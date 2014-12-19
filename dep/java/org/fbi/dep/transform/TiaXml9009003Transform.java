package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.txn.TiaXml9009002;
import org.fbi.dep.model.txn.TiaXml9009003;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
SBS 单笔对外支付结果查询 9009003
 */
public class TiaXml9009003Transform extends AbstractTiaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(TiaXml9009003Transform.class);

    @Override
    byte[] transform(String xml, String userid) {
        TiaXml9009003 xml9009003 = (TiaXml9009003) (new TiaXml9009003().getTia(xml));
        String termID = PropertyManager.getProperty("sbs.termid." + userid);
        if (StringUtils.isEmpty(termID)) {
            termID = "MT01";
        }
        byte[] bytes = SbsTxnDataTransform.convertToTxnN022(xml9009003, termID);
        return bytes;
    }
}
