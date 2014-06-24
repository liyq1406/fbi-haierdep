package org.fbi.dep.helper;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fbi.endpoint.netpay.util.DigestMD5;
import org.fbi.endpoint.netpay.util.MsgUtil;

import java.io.*;

/**
 * 上海银联Http协议客户端
 */
public class NetpayHttpClient {

    private Log logger = LogFactory.getLog(this.getClass());

    static final String MerID = "";
    static final String MerKeyPath = "D:\\fcdep\\netpay\\MerPrk.key";
    static final String PubKeyPath = "D:\\fcdep\\netpay\\PgPubk.key";
    static final String FilePath = "D:\\fcdep\\batchfile\\";

    public String[] doPost(String url, String fileName) {

        // 文件上传准备
        HttpClient httpClient = null;
        PostMethod postMethod = null;
        BufferedReader reader = null;
        InputStream resInputStream = null;
        File file = new File(FilePath + fileName);
        try {
            httpClient = new HttpClient();
            httpClient.getParams().setParameter(
                    HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            postMethod = new PostMethod(url);

            byte[] fileContentBytes = null;
            try {
                fileContentBytes = MsgUtil.getBytes(file);
            } catch (Exception e) {
                logger.error("文件读取错误，File: " + FilePath + fileName, e);
                httpClient.getHttpConnectionManager().closeIdleConnections(0);
                throw new RuntimeException(e);
            }
            String fileContent = new String(fileContentBytes, "UTF-8");

            // 对需要上传的字段签名
            String chkValue = DigestMD5.MD5Sign(MerID, fileName, fileContentBytes, MerKeyPath);
            logger.info("签名内容:" + chkValue);

            httpClient.getParams().setParameter(
                    HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            // 获得管理参数
            HttpConnectionManagerParams managerParams = httpClient
                    .getHttpConnectionManager().getParams();
            // 设置连接超时时间(单位毫秒)
            managerParams.setConnectionTimeout(40000);
            // 设置读数据超时时间(单位毫秒)
            managerParams.setSoTimeout(120000);
            postMethod.setRequestHeader("Connection", "close");
            postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                    new DefaultHttpMethodRetryHandler(1, false));
            NameValuePair[] requestBody = {new NameValuePair("merId", MerID),
                    new NameValuePair("fileName", fileName),
                    new NameValuePair("fileContent", fileContent),
                    new NameValuePair("chkValue", chkValue)};

            postMethod.setRequestBody(requestBody);

            httpClient.executeMethod(postMethod);
            resInputStream = postMethod.getResponseBodyAsStream();

            // 接收返回报文
            reader = new BufferedReader(new InputStreamReader(resInputStream, "UTF-8"));
            String strLine = null;
            StringBuffer resultBuffer = new StringBuffer();
            while ((strLine = reader.readLine()) != null) {
                resultBuffer.append(strLine);
            }
            reader.close();
            resInputStream.close();
            String result = resultBuffer.toString();
            logger.info("返回报文=[" + result + "]");

            // 拆分应答报文数据
            int dex = result.lastIndexOf("=");
            String tiakong = result.substring(0, dex + 1);
            logger.info("验签明文：" + "[" + tiakong + "]");
            String resChkValue = result.substring(dex + 1);

            String str[] = result.split("&");
            int Res_Code = str[0].indexOf("=");
            int Res_message = str[1].indexOf("=");

            String responseCode = str[0].substring(Res_Code + 1);
            String message = str[1].substring(Res_message + 1);
            logger.info("ResponseCode=" + responseCode + " Message=" + message + " CheckValue=" + resChkValue);

            // 对收到的ChinaPay应答传回的域段进行验签
            boolean res = DigestMD5.MD5Verify(tiakong.getBytes("UTF-8"), resChkValue, PubKeyPath);


            if (responseCode.equals("20FM")) {
                logger.info("批量文件接口上传成功！");
            }
            if (!res) {
                logger.error("签名数据不匹配！");
            }
            return new String[]{responseCode, message};
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            // 释放httpclient
            if (postMethod != null) {
                postMethod.releaseConnection();
            }
            if (null != httpClient) {
                httpClient.getHttpConnectionManager().closeIdleConnections(0);
            }
        }
        return null;
    }
}
