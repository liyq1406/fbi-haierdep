package org.fbi.dep.helper;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * HTTP客户端
 */
public class ChinapayShHttpClient {

    private Log logger = LogFactory.getLog(this.getClass());

    private String serverUrl;
    private org.apache.http.client.HttpClient httpclient = null;
    private HttpPost httppost = null;
    private int connTimeOut = 40 * 1000;
    private int soTimeOut = 120 * 1000;

    public ChinapayShHttpClient(String serverUrl) {
        this.serverUrl = serverUrl;
        try {
            httpclient = new DefaultHttpClient();
            httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connTimeOut);
            httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeOut);
            httppost = new HttpPost(serverUrl);
            httppost.getURI();
        } catch (Exception e) {
            logger.error("初始化http网关错误!URL: " + serverUrl, e);
            httpclient.getConnectionManager().shutdown();
            throw new RuntimeException(e);
        }
    }

    public String doPost(String charsetName, List<NameValuePair> nvps) {
        String responseBody = null;
        String errmsg = "";
        try {
            httpclient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, charsetName);
            httppost.setHeader("Connection", "close");
            httppost.setEntity(new UrlEncodedFormEntity(nvps));
            HttpResponse httpResponse = httpclient.execute(httppost);
            // 设置字符集，以防乱码
            responseBody = EntityUtils.toString(httpResponse.getEntity(), charsetName);
        } catch (UnsupportedEncodingException e) {
            errmsg = "Http接口通讯报文格式转换错误!";
            logger.error(errmsg, e);
            throw new RuntimeException(errmsg, e);
        } catch (IOException e) {
            errmsg = "与Http接口通讯连接错误!";
            logger.error(errmsg, e);
            throw new RuntimeException(errmsg, e);
        } catch (Exception e) {
            errmsg = "Http接口的通讯错误!";
            logger.error(errmsg, e);
            throw new RuntimeException(errmsg, e);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }

        if (StringUtils.isEmpty(responseBody)) {
            throw new RuntimeException("通讯可能出现错误，返回报文为空！");
        } else {
            logger.info("HttpClient接收报文内容：" + responseBody);
        }
        return responseBody;
    }

}
