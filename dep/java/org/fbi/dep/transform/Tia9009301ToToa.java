package org.fbi.dep.transform;

import org.fbi.dep.model.txn.ToaXml9009301;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class Tia9009301ToToa extends AbstractTiaToToa {
    private static Logger logger = LoggerFactory.getLogger(Tia9009301ToToa.class);

    public String transform(String xml, String rtnCode, String errMsg) {
        ToaXml9009301 toa = new ToaXml9009301();
        toa.INFO.TXN_CODE = "9009301";
        toa.INFO.REQ_SN = getSubstrBetweenStrs(xml, "<REQ_SN>", "</REQ_SN>");
        toa.INFO.RET_CODE = rtnCode;
        toa.INFO.RET_MSG = errMsg;
        return toa.toString();
    }
}
