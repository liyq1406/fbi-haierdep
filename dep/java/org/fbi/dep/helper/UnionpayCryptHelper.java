package org.fbi.dep.helper;

import com.EasyLink.security.Crypt;
import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnionpayCryptHelper {

    private static final Logger logger = LoggerFactory.getLogger(UnionpayCryptHelper.class);
    private static final String pathCer = PropertyManager.getProperty("unionpay_crypt_pathCer");

    /**
     * 数据签名
     *
     * @param strData
     * @return
     * @since gnete-ora 0.0.0.1
     */
    public static String signMsg(String strData, String bizID) {
        logger.info("数据签名 BIZID:" + bizID);
        String strRnt = "";

        //TODO
        String pathPfx = PropertyManager.getProperty("unionpay_crypt_pathPfx_" + bizID);

        Crypt crypt = new Crypt();
        String strMsg = strData.replaceAll("<SIGNED_MSG></SIGNED_MSG>", "");
        if (crypt.SignMsg(strMsg, pathPfx, "123456")) {
            String signedMsg = crypt.getLastSignMsg();
            strRnt = strData.replaceAll("<SIGNED_MSG></SIGNED_MSG>", "<SIGNED_MSG>" + signedMsg + "</SIGNED_MSG>");
        } else {
            strRnt = strData;
        }
        return strRnt;
    }

    /**
     * 验证签名信息
     */
    public static boolean verifySign(String strXML) {
        logger.info("验证签名");
        Crypt crypt = new Crypt();
        int iStart = strXML.indexOf("<SIGNED_MSG>");
        if (iStart != -1) {
            int end = strXML.indexOf("</SIGNED_MSG>");
            String signedMsg = strXML.substring(iStart + 12, end);
            String strMsg = strXML.substring(0, iStart) + strXML.substring(end + 13);
            if (crypt.VerifyMsg(signedMsg, strMsg, pathCer)) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }
}
