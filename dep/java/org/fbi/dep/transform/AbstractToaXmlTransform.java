package org.fbi.dep.transform;

import org.fbi.dep.enums.TxnRtnCode;
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
public abstract class AbstractToaXmlTransform {
    protected static boolean DEP_IS_RUNNING_DEBUG = !"0".equals(PropertyManager.getProperty("dep.running.debug"));
    private static Logger logger = LoggerFactory.getLogger(AbstractToaXmlTransform.class);

    public String run(String xml, String wsysid) {
        String rtnmsg = null;
        try {
            rtnmsg = transform(xml, wsysid);

        } catch (Exception e) {
            logger.error(TxnRtnCode.MSG_ANALYSIS_ILLEGAL.toRtnMsg(), e);
            throw new RuntimeException(TxnRtnCode.MSG_ANALYSIS_ILLEGAL.toRtnMsg());
        }
        return rtnmsg;
    }
    abstract String transform(String xml, String wsysid);
}
