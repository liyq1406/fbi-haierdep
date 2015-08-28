package org.fbi.dep.txn;

import com.thoughtworks.xstream.converters.ConversionException;
import org.fbi.dep.component.jms.JmsObjMsgClient;
import org.fbi.dep.model.txn.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by XIANGYANG on 2015-7-10.
 */

public class Txn910012002Processor extends AbstractTxnProcessor  {
    private static Logger logger = LoggerFactory.getLogger(Txn910012002Processor.class);

    public String process(String userid, String msgData) throws ClassNotFoundException,ConversionException, InstantiationException, IllegalAccessException, IOException {
        TiaXml910012002 tia = (TiaXml910012002) (new TiaXml910012002().getTia(msgData));

        Tia900010002 tia900010002=new Tia900010002();
        Toa900010002 toa900010002=new Toa900010002();

        try {
            tia900010002.header.TX_CODE="2002";
            tia900010002.header.BIZ_ID=tia.body.originid;
            tia900010002.body.TX_AMT=tia.body.tradeamt;
            tia900010002.body.GERL_ACC_ID=tia.body.accountoutcode;
            tia900010002.body.SPVSN_ACC_ID=tia.body.accountcode;
            tia900010002.header.USER_ID=tia.info.bankoperid;
            tia900010002.header.REQ_SN=tia.info.reqsn;
            tia900010002.body.BANK_BRANCH_ID=tia.info.bankbranchid;
            Object toa = new JmsObjMsgClient().sendRecivMsg("91001", "910012002", "fcdep",
                    "queue.dep.in.fcdep.object", "queue.dep.out.fcdep.object", tia900010002);
            toa900010002=(Toa900010002)toa;
        } catch (Exception e) {
            logger.error("rfm-ta½»Ò×Òì³£.", e);
            throw new RuntimeException(e);
        }
        ToaXml910012002 toaXml910012002=new ToaXml910012002();
        toaXml910012002.info.rtncode=toa900010002.header.RETURN_CODE.trim();
        if(toa900010002.header.RETURN_MSG!=null) {
            toaXml910012002.info.rtnmsg = toa900010002.header.RETURN_MSG.trim();
        }else{
            toaXml910012002.info.rtnmsg="";
        }
        toaXml910012002.info.reqsn=toa900010002.header.REQ_SN.trim();
        toaXml910012002.Body.rtncode=toaXml910012002.info.rtncode;
        toaXml910012002.Body.rtnmsg=toaXml910012002.info.rtnmsg;
        toaXml910012002.Body.fdcserial=toaXml910012002.info.reqsn;

        return toaXml910012002.toString();
    }
}

