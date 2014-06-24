package org.fbi.dep.transform;

import org.fbi.dep.enums.TxnRtnCode;
import org.fbi.dep.util.PropertyManager;
import org.fbi.endpoint.sbs.core.SOFHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 13-4-2
 * Time: ÏÂÎç11:40
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractToaBytesTransform {
    protected static boolean DEP_IS_RUNNING_DEBUG = !"0".equals(PropertyManager.getProperty("dep.running.debug"));
    private static Logger logger = LoggerFactory.getLogger(AbstractToaBytesTransform.class);

    public String run(byte[] bytes) {
        String rtnmsg = null;
        try {
            rtnmsg = transform(bytes);
        } catch (Exception e) {
            String errormsg = (e.getMessage() == null) ? TxnRtnCode.SERVER_EXCEPTION.toRtnMsg() : e.getMessage();
            logger.error(errormsg, e);
            throw new RuntimeException(errormsg);
        }
        return rtnmsg;
    }

    abstract String transform(byte[] bytes);


}
