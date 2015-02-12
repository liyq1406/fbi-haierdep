package org.fbi.dep.transform;

import org.fbi.dep.enums.TxnRtnCode;
import org.fbi.dep.model.base.ToaXml;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 13-4-2
 * Time: ÏÂÎç11:40
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractTiaToToa {
    protected static boolean DEP_IS_RUNNING_DEBUG = !"0".equals(PropertyManager.getProperty("dep.running.debug"));
    private static Logger logger = LoggerFactory.getLogger(AbstractTiaToToa.class);

    public String run(String xml, String rtnCode, String errMsg) {
        String rtnmsg = null;
        try {
//            ToaXml toaXml = transform(xml, errCode, errMsg);
            rtnmsg = transform(xml, rtnCode, errMsg);
        } catch (Exception e) {
            logger.error(TxnRtnCode.MSG_ANALYSIS_ILLEGAL.toRtnMsg(), e);
            throw new RuntimeException(TxnRtnCode.MSG_ANALYSIS_ILLEGAL.toRtnMsg());
        }
        return rtnmsg;
    }
    abstract String transform(String xml, String errCode, String errMsg);

    protected static String getSubstrBetweenStrs(String fromStr, String startStr, String endStr) {
        int length = startStr.length();
        int start = fromStr.indexOf(startStr) + length;
        int end = fromStr.indexOf(endStr);
        return fromStr.substring(start, end);
    }
}
