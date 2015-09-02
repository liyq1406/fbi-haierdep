import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.*;

/**
 * Created by XIANGYANG on 2015-7-3.
 */
public class Mock910012002 {
    private Log logger = LogFactory.getLog(this.getClass());

    private String serverUrl;
    private org.apache.http.client.HttpClient httpclient = null;
    private HttpPost httppost = null;
    public static void main(String[] args) {
        try {
            String xmlmsg = "<?xml version=\"1.0\" encoding=\"GBK\"?>" +
                    "<root>" +
                    "  <info>" +
                    "    <reqsn>bae490b3c1404b1e8e3a0febca3feb7b</reqsn>" +
                    "    <userid>HFNBAPP001</userid>" +
                    "    <txntime>095954</txntime>" +
                    "    <txndate>20150902</txndate>" +
                    "    <txncode>910012002</txncode>" +
                    "    <version>1.0</version>" +
                    "  </info>" +
                    "  <body>" +
                    "    <accountoutcode>801000250902012001</accountoutcode>" +
                    "    <tradeamt>1000000</tradeamt>" +
                    "    <accountcode>801000001102012001</accountcode>" +
                    "    <originid>03150902042894</originid>" +
                    "  </body>" +
                    "</root>";
                    /*"<root>" +
                    "<info>" +
                    "<reqsn>afa0462b2fc946cfadcce8dc2debc9fe</reqsn>" +
                    "<userid>HFNBAPP001</userid>" +
                    "<txndate>20150826</txndate>"+
                    "<txntime>133931</txntime>"+
                    "<txncode>910012002</txncode>" +
                    "<version>1.0</version>" +
                    "</info>" +
                    "<body>" +
                    "<accountoutcode>801000250902012001</accountoutcode>" +
                    "<tradeamt>10</tradeamt>" +
                    "<accountcode>801000016502013</accountcode>" +
                    "<originid>03150825042882</originid>" +
                    "</body>" +
                    "</root>";*/
            // crypt-MD5:e9baa4c4d7787f4c752a0b05122d15e
            String mac = helper.MD5Helper.getMD5String("HFNBAPP001HFNB20150623001" + xmlmsg);
            String reqmsg = "Encrypt-MD5:" + mac + "\n" + xmlmsg;
            Mock910012002 client = new Mock910012002();
            String serverUrl = "http://localhost:62080/depService";
            String rtnDatagram = client.doPost(serverUrl, reqmsg, "GBK");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init(String serverUrl) {

        this.serverUrl = serverUrl;
        try {
            httpclient = new DefaultHttpClient();
            //请求超时
            httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000 * 2);
            //读取超时
            httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000 * 5);
            httppost = new HttpPost(serverUrl);
            httppost.getURI();
        } catch (Exception e) {
            logger.error("初始化http网关错误!URL: " + serverUrl, e);
            httpclient.getConnectionManager().shutdown();
            throw new RuntimeException(e);
        }
    }

    public String doPost(String serverUrl, String datagram, String charsetName) {
        String responseBody = null;
        String errmsg = "";
        try {
            init(serverUrl);
            StringEntity xmlSE = new StringEntity(datagram, charsetName);
            httppost.setEntity(xmlSE);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
//            responseBody = httpclient.execute(httppost, responseHandler);
            HttpResponse httpResponse = httpclient.execute(httppost);
//            String charset = EntityUtils.getContentCharSet(httpResponse.getEntity());
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
            logger.info("HttpClient接收报文内容：\n" + responseBody);
        }
        return responseBody;
    }


    private void doGet() {
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,2000);//连接超时时间设置
        HttpGet httpget = new HttpGet("http://localhost:62003/nettyHttp");
        try {
            HttpResponse response = null;
            response = httpClient.execute(httpget);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                StringBuffer sb=new StringBuffer();
                InputStream instream = entity.getContent();
                int l;
                byte[] tmp = new byte[2048];
                while ((l = instream.read(tmp)) != -1) {
                    sb.append(new String(tmp, 0, l));
                }
                System.out.println(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            httpClient.getConnectionManager().shutdown();
        }
    }
}
