package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.txn.TiaXml9009001;
import org.fbi.dep.model.txn.TiaXml9009004;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
SBS 9009004 -> aa4b
 */
public class TiaXml9009004Transform extends AbstractTiaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(TiaXml9009004Transform.class);

    @Override
    byte[] transform(String xml, String userid) {
        TiaXml9009004 xml9009004 = (TiaXml9009004) (new TiaXml9009004().getTia(xml));
        String termID = PropertyManager.getProperty("sbs.termid." + userid);
        if (StringUtils.isEmpty(termID)) {
            termID = "MT01";
        }
        byte[] bytes = SbsTxnDataTransform.convertToTxnAa4b(xml9009004.INFO.REQ_SN,
                xml9009004.BODY.OUT_ACTNO, xml9009004.BODY.IN_ACTNO,
                xml9009004.BODY.TXN_AMT, xml9009004.BODY.IN_ACTNO2, xml9009004.BODY.TXN_AMT2, termID, xml9009004.BODY.REMARK);
        return bytes;
    }
}
