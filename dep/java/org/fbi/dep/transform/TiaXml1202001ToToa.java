package org.fbi.dep.transform;

import org.fbi.dep.model.base.ToaXmlHeader;
import org.fbi.dep.model.txn.ToaXml1002001;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 13-4-2
 * Time: 下午11:04
 * To change this template use File | Settings | File Templates.
 */
/*
由tia生成toa
 */
public class TiaXml1202001ToToa extends AbstractTiaToToa {
    private static Logger logger = LoggerFactory.getLogger(TiaXml1002001ToToa.class);

    public String transform(String xml, String rtnCode, String errMsg) {
        // xml -> bean
//        TiaXml1002001 xml1002001 = TiaXml1002001.getTia(xml);

        // bean -> toa bean
        ToaXml1002001 toa = new ToaXml1002001();
        toa.INFO = new ToaXmlHeader();
        toa.INFO.TRX_CODE = "1002001";
        toa.INFO.REQ_SN = getSubstrBetweenStrs(xml, "<REQ_SN>", "</REQ_SN>");
        toa.INFO.RET_CODE = rtnCode;
        toa.INFO.ERR_MSG = errMsg;
        toa.INFO.WSYS_ID = getSubstrBetweenStrs(xml, "<WSYS_ID>", "</WSYS_ID>");;
        toa.BODY = new ToaXml1002001.Body();
        toa.BODY.TRANS_DETAILS = new ArrayList<ToaXml1002001.Body.BodyDetail>();
        // txn bean -> txn xml
        return toa.toString();
    }
}
