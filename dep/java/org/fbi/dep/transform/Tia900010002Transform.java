package org.fbi.dep.transform;

import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.txn.Tia900010002;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
SBS 9009001 -> aa41
*/
public class Tia900010002Transform extends AbstractTiaTransform{
    private static Logger logger = LoggerFactory.getLogger(Tia900010002Transform.class);
    @Override
    public byte[] transform(TIA tiaPara) {
        String termID = PropertyManager.getProperty("sbs.termid.TAFDCAPP001");
        Tia900010002 tia900010002Para=(Tia900010002)tiaPara;
        byte[] bytes = SbsTxnDataTransform.convertToTxnAa41(tia900010002Para.header.REQ_SN,
                tia900010002Para.body.GERL_ACC_ID,tia900010002Para.body.SPVSN_ACC_ID,
                tia900010002Para.body.TX_AMT, termID, "");
        return bytes;
    }
}