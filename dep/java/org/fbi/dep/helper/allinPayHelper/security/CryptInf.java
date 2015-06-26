package org.fbi.dep.helper.allinPayHelper.security;

/**
 * Created by Lichao.W At 2015/6/24 21:53
 * wanglichao@163.com
 */
public interface CryptInf {
    public boolean VerifyMsg(String TobeVerified, String PlainText, String CertFile) throws Exception;
    public boolean SignMsg(String TobeSigned, String KeyFile, String PassWord) throws Exception;
    public String getLastSignMsg();
}
