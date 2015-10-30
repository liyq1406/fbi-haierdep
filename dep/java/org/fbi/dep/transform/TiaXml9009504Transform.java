package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.txn.sbs.TiaXml9009501;
import org.fbi.dep.model.txn.sbs.TiaXml9009504;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通知存款-提款通知9009504
 * (对应SBS-a111交易)
 * 本交易用于七天通知存款提取款项前的提款通知，客户办理通知时应填写通知单
 */
public class TiaXml9009504Transform extends AbstractTiaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(TiaXml9009504Transform.class);

    @Override
    byte[] transform(String xml, String userid) {
        TiaXml9009504 tia = (TiaXml9009504) (new TiaXml9009504().getTia(xml));
        String termID = PropertyManager.getProperty("sbs.termid." + userid);
        if (StringUtils.isEmpty(termID)) {
            termID = "MT01";
        }
        byte[] bytes = SbsTxnDataTransform.convertToTxna111For9009504(tia, termID);
        return bytes;
    }
}
