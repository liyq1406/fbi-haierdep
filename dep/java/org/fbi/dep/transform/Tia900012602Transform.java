package org.fbi.dep.transform;

import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.txn.Tia900012601;
import org.fbi.dep.model.txn.Tia900012602;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
/**
 * Created by hanjianlong on 2015-8-11.
 * SBS 900012601 -> 8872 日间总数对账请求报文
 */
public class Tia900012602Transform extends AbstractTiaTransform{
    private static Logger logger = LoggerFactory.getLogger(Tia900012602Transform.class);
    @Override
    public byte[] transform(TIA tiaPara) {
        String termID = PropertyManager.getProperty("sbs.termid.TAFDCAPP001");
        Tia900012602 tia900012602Para=(Tia900012602)tiaPara;
        byte[] bytes = SbsTxnDataTransform.convertToTxn8874(tia900012602Para, termID);
        return bytes;
    }
}