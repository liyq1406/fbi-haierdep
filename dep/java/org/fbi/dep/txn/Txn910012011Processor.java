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

public class Txn910012011Processor extends AbstractTxnProcessor  {
    private static Logger logger = LoggerFactory.getLogger(Txn910012011Processor.class);

    public String process(String userid, String msgData) throws ClassNotFoundException,ConversionException, InstantiationException, IllegalAccessException, IOException {
        TiaXml910012011 tia = (TiaXml910012011) (new TiaXml910012011().getTia(msgData));

        Tia900010002 tia900010002=new Tia900010002();
        Toa900010002 toa900010002=new Toa900010002();

        try {
            tia900010002.header.TX_CODE="2011";
            tia900010002.header.BIZ_ID=tia.body.originid;
            tia900010002.header.USER_ID=tia.info.bankoperid;
            tia900010002.header.REQ_SN=tia.info.reqsn;
            tia900010002.body.BANK_BRANCH_ID=tia.info.bankbranchid;
            Object toa = new JmsObjMsgClient().sendRecivMsg("91001", "910012011", "fcdep",
                    "queue.dep.in.fcdep.object", "queue.dep.out.fcdep.object", tia900010002);
            toa900010002=(Toa900010002)toa;
        } catch (Exception e) {
            logger.error("rfm-ta½»Ò×Òì³£.", e);
            throw new RuntimeException(e);
        }

        ToaXml910012011 toaXml910012011=new ToaXml910012011();
        toaXml910012011.info.rtncode=toa900010002.header.RETURN_CODE.trim();
        if(toa900010002.header.RETURN_MSG!=null) {
            toaXml910012011.info.rtnmsg = toa900010002.header.RETURN_MSG.trim();
        }else{
            toaXml910012011.info.rtnmsg="";
        }
        if(toa900010002.header.REQ_SN!=null) {
            toaXml910012011.info.reqsn = toa900010002.header.REQ_SN.trim();
        }

        toaXml910012011.Body.rtncode=toaXml910012011.info.rtncode;
        toaXml910012011.Body.rtnmsg=toaXml910012011.info.rtnmsg;
        toaXml910012011.Body.fdcserial=toaXml910012011.info.reqsn;

        return toaXml910012011.toString();
    }
}

