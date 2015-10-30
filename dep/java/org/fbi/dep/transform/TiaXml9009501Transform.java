package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.txn.sbs.TiaXml9009501;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 根据帐号客户名称-账户开通网银登记9009501
 * (对应SBS-8126交易)账户开通网银登记请求报文
 */
public class TiaXml9009501Transform extends AbstractTiaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(TiaXml9009501Transform.class);

    @Override
    byte[] transform(String xml, String userid) {
        TiaXml9009501 tia = (TiaXml9009501) (new TiaXml9009501().getTia(xml));
        String termID = PropertyManager.getProperty("sbs.termid." + userid);
        if (StringUtils.isEmpty(termID)) {
            termID = "MT01";
        }
        byte[] bytes = SbsTxnDataTransform.convertToTxn8126For9009501(tia, termID);
        return bytes;
    }
}
