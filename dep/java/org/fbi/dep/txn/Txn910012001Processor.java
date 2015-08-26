package org.fbi.dep.txn;

import com.thoughtworks.xstream.converters.ConversionException;
import org.fbi.dep.component.jms.JmsBytesClient;
import org.fbi.dep.component.jms.JmsObjMsgClient;
import org.fbi.dep.model.txn.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

/**
 * Created by XIANGYANG on 2015-7-10.
 */

public class Txn910012001Processor extends AbstractTxnProcessor  {
    private static Logger logger = LoggerFactory.getLogger(Txn910012001Processor.class);

    public String process(String userid, String msgData) throws ClassNotFoundException,ConversionException, InstantiationException, IllegalAccessException, IOException {
        TiaXml910012001 tia = (TiaXml910012001) (new TiaXml910012001().getTia(msgData));

        // rfm-ta
        Tia9902001 tia9902001=new Tia9902001();
        Toa9902001 toa9902001=new Toa9902001();
        try {
            tia9902001.header.TX_CODE="2001";
            tia9902001.header.BIZ_ID=tia.body.originid;
            tia9902001.body.BRANCH_ID=tia.info.bankbranchid;
            tia9902001.header.USER_ID=tia.info.bankoperid;
            tia9902001.header.REQ_SN=tia.info.reqsn;
            Object toa = new JmsObjMsgClient().sendRecivMsg("91001",tia.info.txncode, "fcdep",
                    "queue.dep.in.fcdep.object", "queue.dep.out.fcdep.object", tia9902001);
            toa9902001=(Toa9902001)toa;
        } catch (Exception e) {
            logger.error("rfm-ta½»Ò×Òì³£.", e);
            throw new RuntimeException(e);
        }
        ToaXml910012001 toaXml910012001=new ToaXml910012001();
        toaXml910012001.info.rtncode=toa9902001.header.RETURN_CODE.trim();
        if(toa9902001.header.RETURN_MSG!=null) {
            toaXml910012001.info.rtnmsg = toa9902001.header.RETURN_MSG.trim();
        }else{
            toaXml910012001.info.rtnmsg="";
        }
        toaXml910012001.info.reqsn=toa9902001.header.REQ_SN.trim();
        toaXml910012001.info.txncode=tia.info.txncode;

        toaXml910012001.Body.rtncode=toaXml910012001.info.rtncode;
        toaXml910012001.Body.rtnmsg=toaXml910012001.info.rtnmsg;
        toaXml910012001.Body.accountcode=toa9902001.body.ACC_ID.trim();
        toaXml910012001.Body.accountname=toa9902001.body.ACC_NAME.trim();
        toaXml910012001.Body.tradeamt=toa9902001.body.TX_AMT.trim();
        toaXml910012001.Body.accounttype=toa9902001.body.ACC_TYPE.trim();
        toaXml910012001.Body.fdcserial=toaXml910012001.info.reqsn;

        return toaXml910012001.toString();
    }
}

