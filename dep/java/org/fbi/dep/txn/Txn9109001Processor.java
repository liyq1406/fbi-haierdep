package org.fbi.dep.txn;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.component.jms.JmsBytesClient;
import org.fbi.dep.component.jms.JmsObjMsgClient;
import org.fbi.dep.enums.SBSFormCode;
import org.fbi.dep.model.txn.TiaXml9009002;
import org.fbi.dep.model.txn.TiaXml9109001;
import org.fbi.dep.model.txn.ToaXml9009002;
import org.fbi.dep.transform.SbsTxnDataTransform;
import org.fbi.dep.util.JaxbHelper;
import org.fbi.dep.util.PropertyManager;
import org.fbi.dep.util.StringPad;
import org.fbi.endpoint.mbp.MbpClient;
import org.fbi.endpoint.mbp.domain.ClientRequestHead;
import org.fbi.endpoint.mbp.domain.transactreponse.TransactResponseRoot;
import org.fbi.endpoint.mbp.domain.transactrequest.TransactRequestParam;
import org.fbi.endpoint.mbp.domain.transactrequest.TransactRequestRoot;
import org.fbi.endpoint.sbs.core.FebResponse;
import org.fbi.endpoint.sbs.domain.SOFForm;
import org.fbi.endpoint.sbs.model.form.re.T531;
import org.fbi.endpoint.sbs.model.form.re.T999;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 交易处理
 */
public class Txn9109001Processor extends AbstractTxnProcessor {

    private static Logger logger = LoggerFactory.getLogger(Txn9109001Processor.class);

    public String process(String userid, String msgData) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {


        TiaXml9109001 tia = (TiaXml9109001) (new TiaXml9109001().getTia(msgData));

        // fip
        Object toa = null;
        try {
            toa = new JmsObjMsgClient().sendRecivMsg("910", "9109001", "fcdep",
                    "queue.dep.in.fcdep.object", "queue.dep.out.fcdep.object", tia);
        } catch (Exception e) {
            logger.error("巨商会交易异常.", e);
        }

        return toa.toString();
    }


}
