package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.txn.TiaXml9009001;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
SBS 9009001 -> aa41
 */
public class TiaXml9009001Transform extends AbstractTiaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(TiaXml9009001Transform.class);

    @Override
    byte[] transform(String xml, String userid) {
        TiaXml9009001 xml9009001 = (TiaXml9009001) (new TiaXml9009001().getTia(xml));
        String termID = PropertyManager.getProperty("sbs.termid." + userid);
        if (StringUtils.isEmpty(termID)) {
            termID = "MT01";
        }
        byte[] bytes = SbsTxnDataTransform.convertToTxnAa41(xml9009001.INFO.REQ_SN,
                xml9009001.BODY.OUT_ACTNO, xml9009001.BODY.IN_ACTNO,
                xml9009001.BODY.TXN_AMT, termID, xml9009001.BODY.REMARK);
        return bytes;
    }
}
