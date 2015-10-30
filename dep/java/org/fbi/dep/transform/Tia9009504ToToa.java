package org.fbi.dep.transform;

import org.fbi.dep.model.txn.sbs.ToaXml9009504;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ”…tia…˙≥…toa
 */
public class Tia9009504ToToa extends AbstractTiaToToa {
    private static Logger logger = LoggerFactory.getLogger(Tia9009504ToToa.class);

    public String transform(String xml, String rtnCode, String errMsg) {
        ToaXml9009504 toa = new ToaXml9009504();
        toa.getINFO().setTXN_CODE("9009504");
        toa.getINFO().setREQ_SN(getSubstrBetweenStrs(xml, "<REQ_SN>", "</REQ_SN>"));
        toa.getINFO().setRET_CODE(rtnCode);
        toa.getINFO().setRET_MSG(errMsg);
        return toa.toString();
    }
}
