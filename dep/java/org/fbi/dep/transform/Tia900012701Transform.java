package org.fbi.dep.transform;

import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.txn.Tia900012601;
import org.fbi.dep.model.txn.Tia900012701;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
/**
 * Created by hanjianlong on 2015-8-11.
 * SBS 900012701 -> 8119 查询多账户余额请求报文(面向SBS  交易号8119）
 */
public class Tia900012701Transform extends AbstractTiaTransform{
    private static Logger logger = LoggerFactory.getLogger(Tia900012701Transform.class);
    @Override
    public byte[] transform(TIA tiaPara) {
        String termID = PropertyManager.getProperty("sbs.termid.TAFDCAPP001");
        Tia900012701 tia900012701Para=(Tia900012701)tiaPara;
        byte[] bytes = SbsTxnDataTransform.convertToTxn8119(tia900012701Para, termID);
        return bytes;
    }
}