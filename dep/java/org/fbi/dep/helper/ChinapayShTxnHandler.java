package org.fbi.dep.helper;

import chinapay.Base64;
import chinapay.PrivateKey;
import chinapay.SecureLink;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.fbi.dep.util.PropertyManager;
import org.fbi.endpoint.chinapaysh.txn.Sincut;
import org.fbi.endpoint.chinapaysh.util.DigestMD5;
import org.fbi.endpoint.chinapaysh.util.MsgUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 上海银联加解密辅助类
 */
public class ChinapayShTxnHandler {

    private Log logger = LogFactory.getLog(this.getClass());

    public static final String BATCH_FILE_PATH = PropertyManager.getProperty("chinapaysh_path_batchfile");

    public static final String BATCH_MER_ID = PropertyManager.getProperty("chinapaysh_haierfc_batch_merid");
    public static final String BATCH_MER_KEY_PATH = PropertyManager.getProperty("chinapaysh_crypt_path_batch_merprk");
    public static final String BATCH_PUB_KEY_PATH = PropertyManager.getProperty("chinapaysh_crypt_path_batch_pgpubk");
    public static final String BATCH_FILE_QUERY_URL = PropertyManager.getProperty("chinapaysh_server_http_batch_query_url");
    public static final String BATCH_FILE_UPLOAD_URL = PropertyManager.getProperty("chinapaysh_server_http_batch_cut_url");

    public static final String SINGLE_MER_ID = PropertyManager.getProperty("chinapaysh_haierfc_single_merid");
    public static final String SINGLE_MER_KEY_PATH = PropertyManager.getProperty("chinapaysh_crypt_path_single_merprk");
    public static final String SINGLE_PUB_KEY_PATH = PropertyManager.getProperty("chinapaysh_crypt_path_single_pgpubk");
    public static final String SINGLE_QUERY_URL = PropertyManager.getProperty("chinapaysh_server_http_single_query_url");
    public static final String SINGLE_CUT_URL = PropertyManager.getProperty("chinapaysh_server_http_single_cut_url");

    // 上传批量文件
    public Map<String, String> uploadBatchFile(String fileName, String fileContent) throws Exception {

        // 生成参数
        List<NameValuePair> nvps = genBatchCutCryptParams(fileName, fileContent);
        // 发起请求，获取响应报文
        String responseBody = new ChinapayShHttpClient(BATCH_FILE_UPLOAD_URL).doPost("UTF-8", nvps);
        // 拆分应答报文数据
        // 对收到的ChinaPay应答传回的域段进行验签
        if (attestBatchTxnMsg(responseBody)) {
            // 获取返回参数组
            return getResponseValues(responseBody);
        }
        return null;
    }

    // 查询批量文件上传处理结果
    public Map<String, String> qryBatchFileStatus(String fileName, String signMsg) throws Exception {

        // 生成参数
        List<NameValuePair> nvps = genBatchQryCryptParams(fileName, signMsg);
        // 发起请求，获取响应报文
        String responseBody = new ChinapayShHttpClient(BATCH_FILE_QUERY_URL).doPost("UTF-8", nvps);
        // 拆分应答报文数据
        // 对收到的ChinaPay应答传回的域段进行验签
        if (attestBatchTxnMsg(responseBody)) {
            // 获取返回参数组
            return getResponseValues(responseBody);
        }
        return null;
    }

    // 单笔代扣
    public Map<String, String> singleCut(String signMsg, List<NameValuePair> nvps) throws Exception {
        String base64Msg = new String(Base64.encode(signMsg.toString().getBytes()));
        PrivateKey key = new PrivateKey();
        boolean keyFlag = key.buildKey(SINGLE_MER_ID, 0, ChinapayShTxnHandler.SINGLE_MER_KEY_PATH);
        if (!keyFlag) {
            logger.error("创建私钥对象失败，不可以签名");
            throw new RuntimeException("创建私钥对象失败，不可以签名");
        }
        SecureLink secureLink = new SecureLink(key);
        String chkValue = secureLink.Sign(base64Msg);
        nvps.add(new BasicNameValuePair("chkValue", chkValue));
        String responseBody = new ChinapayShHttpClient(SINGLE_CUT_URL).doPost("GBK", nvps);
        if (attestSingleTxnMsg(responseBody)) {
            return getResponseValues(responseBody);
        }
        return null;
    }

    // 读取文件、Base64编码、压缩
    private String readBatchFileToBase64(String fileName) throws Exception {
        File file = new File(BATCH_FILE_PATH + fileName);
        byte[] fileContentBase64Bytes = MsgUtil.getBytes(file);
        return new String(fileContentBase64Bytes, "UTF-8");
    }

    // 生成批量上传文件交易HTTP表单参数 signMsg为验签字段，一般为文件内容
    private List<NameValuePair> genBatchCutCryptParams(String fileName, String fileContent) throws Exception {
        // 对需要上传的字段签名
        String chkValue = DigestMD5.MD5Sign(BATCH_MER_ID, fileName, fileContent.getBytes("UTF-8"), BATCH_MER_KEY_PATH);
        String fileContentBase64 = readBatchFileToBase64(fileName);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("merId", BATCH_MER_ID));
        nvps.add(new BasicNameValuePair("fileName", fileName));
        nvps.add(new BasicNameValuePair("fileContent", fileContentBase64));
        nvps.add(new BasicNameValuePair("chkValue", chkValue));
        return nvps;
    }

    // 生成批量查询HTTP表单参数 signMsg为验签字段
    private List<NameValuePair> genBatchQryCryptParams(String fileName, String signMsg) throws Exception {
        // 对需要上传的字段签名
        String chkValue = DigestMD5.MD5Sign(BATCH_MER_ID, signMsg.getBytes("UTF-8"), BATCH_MER_KEY_PATH);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("merId", BATCH_MER_ID));
        nvps.add(new BasicNameValuePair("fileName", fileName));
        nvps.add(new BasicNameValuePair("chkValue", chkValue));
        return nvps;
    }


    // 批量交易响应报文验签
    private boolean attestBatchTxnMsg(String responseBody) throws Exception {
        int mingIndex = responseBody.lastIndexOf("=");
        String mingParam = responseBody.substring(0, mingIndex + 1);
        String resChkValue = responseBody.substring(mingIndex + 1);
        boolean chkFlag = DigestMD5.MD5Verify(mingParam.getBytes("UTF-8"), resChkValue, BATCH_PUB_KEY_PATH);
        if (!chkFlag) {
            logger.error("返回报文签名数据不匹配！[responsebody]---" + responseBody);
            throw new RuntimeException("返回报文签名数据不匹配！");
        } else return true;
    }

    // 单笔交易响应报文验签
    private boolean attestSingleTxnMsg(String responseBody) throws Exception {
        int mingIndex = responseBody.lastIndexOf("=");
        String mingParam = responseBody.substring(0, mingIndex + 1);
        String resChkValue = responseBody.substring(mingIndex + 1);

        chinapay.PrivateKey key = new chinapay.PrivateKey();
        boolean buildFlag = key.buildKey(SINGLE_MER_ID, 0, SINGLE_PUB_KEY_PATH);
        if (buildFlag == false) {
            System.out.println("build key error!");
            return false;
        }
        chinapay.SecureLink secureLink = new chinapay.SecureLink(key);
        boolean chkFlag = secureLink.verifyAuthToken(mingParam, resChkValue);
        if (!chkFlag) {
            logger.error("返回报文签名数据不匹配！[responsebody]---" + responseBody);
            throw new RuntimeException("返回报文签名数据不匹配！");
        } else return true;
    }

    // 解析响应报文
    private Map<String, String> getResponseValues(String responseBody) {
        String[] pairs = responseBody.split("&");
        Map<String, String> resPairs = new HashMap<String, String>();
        for (String str : pairs) {
            String[] aPair = str.split("=");
            resPairs.put(aPair[0], aPair[1]);
        }
        return resPairs;
    }

}
