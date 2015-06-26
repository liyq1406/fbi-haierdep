package org.fbi.dep.helper;

import com.EasyLink.security.Crypt;
import org.fbi.dep.helper.allinPayHelper.XmlTools;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AllinpayCryptHelper {

    private static final Logger logger = LoggerFactory.getLogger(AllinpayCryptHelper.class);
    private static final String pathCer = PropertyManager.getProperty("allinpay_crypt_pathCer");

    /**
     * 通联报文签名
     */
    public static String signMsgAl(String xml) throws Exception{
        String pfxPath = PropertyManager.getProperty("allinpay_crypt_pathPfx");
        xml= XmlTools.signMsg(xml, pfxPath, "111111", false);
        return xml;
    }
}
