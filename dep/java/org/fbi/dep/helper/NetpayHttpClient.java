package org.fbi.dep.helper;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fbi.dep.util.PropertyManager;
import org.fbi.endpoint.netpay.util.DigestMD5;
import org.fbi.endpoint.netpay.util.MsgUtil;

import java.io.*;

/**
 * �Ϻ�����HttpЭ��ͻ���
 */
public class NetpayHttpClient {

    private Log logger = LogFactory.getLog(this.getClass());

    static final String MER_ID = PropertyManager.getProperty("chinapay_haierfc_merid");
    static final String QUERY_URL=PropertyManager.getProperty("chinapay_server_http_batch_query_url");
    static final String CUT_URL=PropertyManager.getProperty("chinapay_server_http_batch_cut_url");
    static final String MER_KEY_PATH = PropertyManager.getProperty("chinapay_crypt_path_merprk");
    static final String PUB_KEY_PATH = PropertyManager.getProperty("chinapay_crypt_path_pgpubk");
    static final String BATCH_FILE_PATH = PropertyManager.getProperty("chinapay_path_batchfile");

    public String[] doPost(String url, String fileName, String fileContent) {

        // �ļ��ϴ�׼��
        HttpClient httpClient = null;
        PostMethod postMethod = null;
        BufferedReader reader = null;
        InputStream resInputStream = null;
        File file = new File(BATCH_FILE_PATH + fileName);
        try {
            httpClient = new HttpClient();
            httpClient.getParams().setParameter(
                    HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            postMethod = new PostMethod(url);

            byte[] fileContentBase64Bytes = null;
            try {
                fileContentBase64Bytes = MsgUtil.getBytes(file);
            } catch (Exception e) {
                logger.error("�ļ���ȡ����File: " + BATCH_FILE_PATH + fileName, e);
                httpClient.getHttpConnectionManager().closeIdleConnections(0);
                throw new RuntimeException(e);
            }
            String fileContentBase64 = new String(fileContentBase64Bytes, "UTF-8");

            // ����Ҫ�ϴ����ֶ�ǩ��
            String chkValue = DigestMD5.MD5Sign(MER_ID, fileName, fileContent.getBytes("UTF-8"), MER_KEY_PATH);
            logger.info("ǩ������:" + chkValue);

            // ��ù������
            HttpConnectionManagerParams managerParams = httpClient
                    .getHttpConnectionManager().getParams();
            // �������ӳ�ʱʱ��(��λ����)
            managerParams.setConnectionTimeout(40000);
            // ���ö����ݳ�ʱʱ��(��λ����)
            managerParams.setSoTimeout(120000);
            postMethod.setRequestHeader("Connection", "close");
            postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                    new DefaultHttpMethodRetryHandler(1, false));
            NameValuePair[] requestBody = {new NameValuePair("merId", MER_ID),
                    new NameValuePair("fileName", fileName),
                    new NameValuePair("fileContent", fileContentBase64),
                    new NameValuePair("chkValue", chkValue)};

            postMethod.setRequestBody(requestBody);

            httpClient.executeMethod(postMethod);
            resInputStream = postMethod.getResponseBodyAsStream();

            // ���շ��ر���
            reader = new BufferedReader(new InputStreamReader(resInputStream, "UTF-8"));
            String strLine = null;
            StringBuffer resultBuffer = new StringBuffer();
            while ((strLine = reader.readLine()) != null) {
                resultBuffer.append(strLine);
            }
            reader.close();
            resInputStream.close();
            String result = resultBuffer.toString();
            logger.info("���ر���=[" + result + "]");

            // ���Ӧ��������
            int dex = result.lastIndexOf("=");
            String tiakong = result.substring(0, dex + 1);
            logger.info("��ǩ���ģ�" + "[" + tiakong + "]");
            String resChkValue = result.substring(dex + 1);

            String str[] = result.split("&");
            int Res_Code = str[0].indexOf("=");
            int Res_message = str[1].indexOf("=");

            String responseCode = str[0].substring(Res_Code + 1);
            String message = str[1].substring(Res_message + 1);
            logger.info("ResponseCode=" + responseCode + " Message=" + message + " CheckValue=" + resChkValue);

            // ���յ���ChinaPayӦ�𴫻ص���ν�����ǩ
            boolean res = DigestMD5.MD5Verify(tiakong.getBytes("UTF-8"), resChkValue, PUB_KEY_PATH);


            if (responseCode.equals("20FM")) {
                logger.info("�����ļ��ӿ��ϴ��ɹ���");
            }
            if (!res) {
                logger.error("ǩ�����ݲ�ƥ�䣡");
            }
            return new String[]{responseCode, message};
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            // �ͷ�httpclient
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
