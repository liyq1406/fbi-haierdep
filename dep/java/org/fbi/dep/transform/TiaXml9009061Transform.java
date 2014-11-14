package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.txn.TiaXml9009060;
import org.fbi.dep.model.txn.TiaXml9009061;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
SBS 9009061 -> 8856 根据客户证件信息查询账户列表
 */
public class TiaXml9009061Transform extends AbstractTiaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(TiaXml9009061Transform.class);

    @Override
    byte[] transform(String xml, String userid) {
        TiaXml9009061 tia = (TiaXml9009061) (new TiaXml9009061().getTia(xml));
        String termID = PropertyManager.getProperty("sbs.termid." + userid);
        if (StringUtils.isEmpty(termID)) {
            termID = "MT01";
        }
        byte[] bytes = SbsTxnDataTransform.convertToTxn8856(tia, termID);
        return bytes;
    }
}
