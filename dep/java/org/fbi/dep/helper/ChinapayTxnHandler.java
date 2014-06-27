package org.fbi.dep.helper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.fbi.dep.util.PropertyManager;
import org.fbi.endpoint.netpay.util.DigestMD5;
import org.fbi.endpoint.netpay.util.MsgUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 上海银联加解密辅助类
 */
public class ChinapayTxnHandler {

    private Log logger = LogFactory.getLog(this.getClass());

    public static final String BATCH_FILE_PATH = PropertyManager.getProperty("chinapay_path_batchfile");
    public static final String MER_ID = PropertyManager.getProperty("chinapay_haierfc_merid");
    static final String MER_KEY_PATH = PropertyManager.getProperty("chinapay_crypt_path_merprk");
    static final String PUB_KEY_PATH = PropertyManager.getProperty("chinapay_crypt_path_pgpubk");

    static final String BATCH_FILE_QUERY_URL = PropertyManager.getProperty("chinapay_server_http_batch_query_url");
    static final String BATCH_FILE_UPLOAD_URL = PropertyManager.getProperty("chinapay_server_http_batch_cut_url");

    // 上传批量文件
    public Map<String, String>  uploadBatchFile(String fileName, String fileContent) throws Exception {

        // 生成参数
        List<NameValuePair> nvps = genBatchCutCryptParams(fileName, fileContent);
        // 发起请求，获取响应报文
        String responseBody = new ChinapayHttpClient(BATCH_FILE_UPLOAD_URL).doPost("UTF-8", nvps);
        // 拆分应答报文数据
        // 对收到的ChinaPay应答传回的域段进行验签
        if (attest(responseBody)) {
            // 获取返回参数组
            return getResponseValues(responseBody);
        }
        return null;
    }

    // 查询批量文件上传处理结果
    public Map<String, String>  qryBatchFileStatus(String fileName, String signMsg) throws Exception {

        // 生成参数
        List<NameValuePair> nvps = genBatchQryCryptParams(fileName, signMsg);
        // 发起请求，获取响应报文
        String responseBody = new ChinapayHttpClient(BATCH_FILE_QUERY_URL).doPost("UTF-8", nvps);
        // 拆分应答报文数据
        // 对收到的ChinaPay应答传回的域段进行验签
        if (attest(responseBody)) {
            // 获取返回参数组
            return getResponseValues(responseBody);
        }
        return null;
    }

    // 读取文件、Base64编码、压缩
    private String readFileToBase64(String fileName) throws Exception {
        File file = new File(BATCH_FILE_PATH + fileName);
        byte[] fileContentBase64Bytes = MsgUtil.getBytes(file);
        return new String(fileContentBase64Bytes, "UTF-8");
    }

    // 生成批量上传文件交易HTTP表单参数 signMsg为验签字段，一般为文件内容
    private List<NameValuePair> genBatchCutCryptParams(String fileName, String fileContent) throws Exception {
        // 对需要上传的字段签名
        String chkValue = DigestMD5.MD5Sign(MER_ID, fileName, fileContent.getBytes("UTF-8"), MER_KEY_PATH);
        String fileContentBase64 = readFileToBase64(fileName);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("merId", MER_ID));
        nvps.add(new BasicNameValuePair("fileName", fileName));
        nvps.add(new BasicNameValuePair("fileContent", fileContentBase64));
        nvps.add(new BasicNameValuePair("chkValue", chkValue));
        return nvps;
    }

    // 生成批量查询HTTP表单参数 signMsg为验签字段
    private List<NameValuePair> genBatchQryCryptParams(String fileName, String signMsg) throws Exception {
        // 对需要上传的字段签名
        String chkValue = DigestMD5.MD5Sign(MER_ID, signMsg.getBytes("UTF-8"), MER_KEY_PATH);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("merId", MER_ID));
        nvps.add(new BasicNameValuePair("fileName", fileName));
        nvps.add(new BasicNameValuePair("chkValue", chkValue));
        return nvps;
    }


    // 响应报文验签
    private boolean attest(String responseBody) throws Exception {
        int mingIndex = responseBody.lastIndexOf("=");
        String mingParam = responseBody.substring(0, mingIndex + 1);
        String resChkValue = responseBody.substring(mingIndex + 1);
        boolean res = DigestMD5.MD5Verify(mingParam.getBytes("UTF-8"), resChkValue, PUB_KEY_PATH);
        if (!res) {
            logger.error("返回报文签名数据不匹配！[responsebody]---" + responseBody);
            throw new RuntimeException("返回报文签名数据不匹配！");
        } else return true;
    }

    // 解析响应报文
    private Map<String, String>  getResponseValues(String responseBody) {
        String[] pairs = responseBody.split("&");
        Map<String, String> resPairs = new HashMap<String, String>();
        for(String str : pairs) {
            String[] aPair =  str.split("=");
            resPairs.put(aPair[0], aPair[1]);
        }
        return resPairs;
    }

}
