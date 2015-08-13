package org.fbi.dep.transform;

import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.txn.Tia900010002;
import org.fbi.dep.model.txn.Tia900012601;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
/**
 * Created by hanjianlong on 2015-8-11.
 * SBS 900012601 -> 8872 日间总数对账请求报文
 */
public class Tia900012601Transform extends AbstractTiaTransform{
    private static Logger logger = LoggerFactory.getLogger(Tia900012601Transform.class);
    @Override
    public byte[] transform(TIA tiaPara) {
        String termID = PropertyManager.getProperty("sbs.termid.TAFDCAPP001");
        Tia900012601 tia900012601Para=(Tia900012601)tiaPara;
        byte[] bytes = SbsTxnDataTransform.convertToTxn8872(tia900012601Para, termID);
        return bytes;
    }
}