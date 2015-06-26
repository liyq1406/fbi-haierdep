package org.fbi.dep.helper.allinPayHelper;

import org.fbi.dep.helper.allinPayHelper.security.CryptInf;
import org.fbi.dep.helper.allinPayHelper.security.CryptNoRestrict;
import org.fbi.dep.helper.allinPayHelper.security.CryptUnderRestrict;

import javax.net.ssl.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.Provider;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by Lichao.W At 2015/6/24 20:16
 * wanglichao@163.com
 */
public class XmlTools {

    private static Provider prvd=null;
    private static final SSLHandler simpleVerifier=new SSLHandler();
    private static SSLSocketFactory sslFactory;

   //向通联发送报文
    public static String send(String url,String xml) throws Exception
    {
        OutputStream reqStream=null;
        InputStream resStream =null;
        URLConnection request = null;
        String respText=null;
        byte[] postData;
        try
        {
            postData = xml.getBytes("GBK");
            request = createRequest(url, "POST");

            request.setRequestProperty("Content-type", "application/tlt-notify");
            request.setRequestProperty("Content-length", String.valueOf(postData.length));
            request.setRequestProperty("Keep-alive", "false");

            reqStream = request.getOutputStream();
            reqStream.write(postData);
            reqStream.close();

            ByteArrayOutputStream ms = null;
            resStream = request.getInputStream();
            ms = new ByteArrayOutputStream();
            byte[] buf = new byte[4096];
            int count;
            while ((count = resStream.read(buf, 0, buf.length)) > 0)
            {
                ms.write(buf, 0, count);
            }
            resStream.close();
            respText = new String(ms.toByteArray(), "GBK");
        }
        catch(Exception ex)
        {
            throw ex;
        }
        finally
        {
            close(reqStream);
            close(resStream);
        }
        return respText;
    }

    private static URLConnection createRequest(String strUrl, String strMethod) throws Exception
    {
        URL url = new URL(strUrl);
        URLConnection conn = url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        if (conn instanceof HttpsURLConnection)
        {
            HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
            httpsConn.setRequestMethod(strMethod);
            httpsConn.setSSLSocketFactory(XmlTools.getSSLSF());
            httpsConn.setHostnameVerifier(XmlTools.getVerifier());
        }
        else if (conn instanceof HttpURLConnection)
        {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setRequestMethod(strMethod);
        }
        return conn;
    }
    public static synchronized SSLSocketFactory getSSLSF() throws Exception
    {
        if(sslFactory!=null) return sslFactory;
        SSLContext sc = prvd==null?SSLContext.getInstance("SSLv3"):SSLContext.getInstance("SSLv3");
        sc.init(null, new TrustManager[]{simpleVerifier}, null);
        sslFactory = sc.getSocketFactory();
        return sslFactory;
    }
    public static HostnameVerifier getVerifier()
    {
        return simpleVerifier;
    }

    private static class SSLHandler implements X509TrustManager,HostnameVerifier
    {
        private SSLHandler()
        {
        }

        public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean verify(String arg0, SSLSession arg1)
        {
            return true;
        }
    }

    private static void close(OutputStream c)
    {
        try
        {
            if(c!=null) c.close();
        }
        catch(Exception ex)
        {

        }
    }

    private static void close(InputStream c)
    {
        try
        {
            if(c!=null) c.close();
        }
        catch(Exception ex)
        {

        }
    }

    ////////////////////////添加数据验证//////////////////////////////

    public static String signMsg(String strData, String pathPfx, String pass,boolean restrict) throws Exception
    {
        return signMsg(strData, pathPfx, pass, prvd, restrict);
    }

    private static String signMsg(String strData, String pathPfx, String pass,Provider prv,boolean restrict) throws Exception
    {
        final String IDD_STR="<SIGNED_MSG></SIGNED_MSG>";
        String strMsg = strData.replaceAll(IDD_STR, "");
        String signedMsg = signPlain(strMsg, pathPfx, pass,prv,restrict);
        String strRnt = strData.replaceAll(IDD_STR, "<SIGNED_MSG>" + signedMsg + "</SIGNED_MSG>");
        return strRnt;
    }

    private static String signPlain(String strData, String pathPfx, String pass,Provider prv,boolean restrict) throws Exception
    {
        PaymentSign.initProvider();
        CryptInf crypt;
        if(restrict) crypt=new CryptUnderRestrict("GBK");
        else crypt=new CryptNoRestrict("GBK",prv);
        String strRnt = "";
        if (crypt.SignMsg(strData, pathPfx, pass))
        {
            String signedMsg = crypt.getLastSignMsg();
            strRnt = signedMsg;
        }
        else
        {
            throw new Exception("签名失败");
        }
        return strRnt;
    }
}
