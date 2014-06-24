package org.fbi.dep.transform;

import org.fbi.dep.model.base.ToaXmlHeader;
import org.fbi.dep.model.txn.ToaXml1003001;
import org.fbi.dep.model.txn.ToaXml9009001;
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
public class Tia9009001ToToa extends AbstractTiaToToa {
    private static Logger logger = LoggerFactory.getLogger(Tia9009001ToToa.class);

    public String transform(String xml, String rtnCode, String errMsg) {
        ToaXml9009001 toa = new ToaXml9009001();
        toa.INFO.TXN_CODE = "9009001";
        toa.INFO.REQ_SN = getSubstrBetweenStrs(xml, "<REQ_SN>", "</REQ_SN>");
        toa.INFO.RET_CODE = rtnCode;
        toa.INFO.RET_MSG = errMsg;
        return toa.toString();
    }
}
