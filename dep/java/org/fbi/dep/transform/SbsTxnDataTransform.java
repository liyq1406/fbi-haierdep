package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.txn.*;
import org.fbi.dep.util.StringPad;
import org.fbi.endpoint.eai.transCreditInfoFromSBStoJDE.ObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 13-7-25
 * Time: 下午4:38
 * To change this template use File | Settings | File Templates.
 */
public class SbsTxnDataTransform {

    private static Logger logger = LoggerFactory.getLogger(SbsTxnDataTransform.class);

    public static byte[] convertToTxnN022(TiaXml9009003 tia, String termId) {

        List<String> tiaList = new ArrayList<String>();
        tiaList.add(tia.BODY.FBTIDX);
        tiaList.add(tia.BODY.REQNUM);
        tiaList.add(tia.BODY.ORDDAT);
        tiaList.add(tia.BODY.CHQNUM);
        return convert("n022", termId, tiaList);
    }

    public static byte[] convertToTxnN120(TiaXml9009002 tia, String termId) {

        List<String> tiaList = new ArrayList<String>();
        tiaList.add(tia.INFO.REQ_SN);
        try {
            Field[] fields = tia.BODY.getClass().getFields();
            Object obj = null;
            for (Field field : fields) {
//                logger.info("Field:" + field.getName());
                obj = field.get(tia.BODY);
                if (obj != null) {
                    tiaList.add(obj.toString());
                } else {
                    tiaList.add("");
                }
            }
        } catch (Exception e) {
            logger.error("SBS报文组装异常", e);
            throw new RuntimeException("SBS报文组装异常");
        }
        return convert("n120", termId, tiaList);
    }

    public static byte[] convertToTxnAa41(String sn, String outActno, String inActno, String amt, String termId, String remark) {
        if (StringUtils.isEmpty(remark)) remark = "资金交换平台9009001";
        List<String> tiaList = assembleTaa41Param(sn, outActno, inActno, new BigDecimal(amt), remark);
        return convert("aa41", termId, tiaList);
    }

    public static byte[] convertToTxnAa4b(String sn, String outActno, String outActName, String inActno,
                                          String inActName, String amt,
                                          String inActno2, String inActName2, String amt2, String termId, String remark) {
        if (StringUtils.isEmpty(remark)) remark = "资金交换平台9009004";
        List<String> tiaList = assembleTaa4bParam(sn, outActno, outActName, inActno, inActName, amt, inActno2, inActName2, amt2, remark);
        return convert("aa4b", termId, tiaList);
    }

    public static byte[] convertToTxnN080(TiaXml9009101 tia, String termId) {
        return convert("n080", termId, assembleTn080Param(tia));
    }

    public static byte[] convertToTxn8855(TiaXml9009060 tia, String termId) {
        return convert("8855", termId, assembleT8855Param(tia));
    }

    public static byte[] convertToTxn8856(TiaXml9009061 tia, String termId) {
        return convert("8856", termId, assembleT8856Param(tia));
    }

    public static byte[] convertToTxn8118(TiaXml9009062 tia, String termId) {
        return convert("8118", termId, assembleT8118Param(tia));
    }

    public static byte[] convertToTxn8854For9009051(TiaXml9009051 tia, String termId) {
        return convert("8854", termId, assembleT8854ParamFor9009051(tia));
    }

    public static byte[] convertToTxn8854For9009052(TiaXml9009052 tia, String termId) {
        return convert("8854", termId, assembleT8854ParamFor9009052(tia));
    }

    public static byte[] convertToTxn8011(TiaXml9009301 tia, String termId) {
        return convert("8011", termId, assembleT8011Param(tia));
    }

    public static byte[] convertToTxn8124(TiaXml9009401 tia, String termId) {
        return convert("8124", termId, assembleT8124Param(tia));
    }

    public static byte[] convertToTxn8872(Tia900012601 tia, String termId) {
        return convert("8872", termId, assembleT8872Param(tia));
    }

    public static byte[] convertToTxn8874(Tia900012602 tia, String termId) {
        return convert("8874", termId, assembleT8874Param(tia));
    }

    public static byte[] convertToTxn8119(Tia900012701 tia, String termId) {
        return convert("8119", termId, assembleT8119Param(tia));
    }

    private static List<String> assembleT8119Param(Tia900012701 tia) {
        List<String> paramList = new ArrayList<String>();
        paramList.add(tia.body.TOTCNT);
        String strTemp="";
        for (Tia900012701.BodyDetail bdUnit: tia.body.DETAILS){
            strTemp+=bdUnit.ACTNM+"    ";
        }
        paramList.add(strTemp);
        return paramList;
    }

    private static List<String> assembleT8872Param(Tia900012601 tia) {
        List<String> paramList = new ArrayList<String>();
        paramList.add(tia.body.TX_DATE);
        return paramList;
    }

    private static List<String> assembleT8874Param(Tia900012602 tia) {
        List<String> paramList = new ArrayList<String>();
        paramList.add(tia.body.TX_DATE);
        paramList.add(tia.body.BEGNUM);
        return paramList;
    }

    private static List<String> assembleT8124Param(TiaXml9009401 tia) {
        List<String> paramList = new ArrayList<String>();
        paramList.add(tia.BODY.CUSIDT);
        paramList.add(tia.BODY.APCODE);
        paramList.add(tia.BODY.CURCDE);
        return paramList;
    }

    private static List<String> assembleT8011Param(TiaXml9009301 tia) {
        List<String> paramList = new ArrayList<String>();
        paramList.add(tia.BODY.CUSNAM);
        paramList.add(tia.BODY.CORADD);
        paramList.add(tia.BODY.ZIPCDE);
        paramList.add(tia.BODY.TELNUM);
        paramList.add(tia.BODY.TELEXN);
        paramList.add(tia.BODY.PASTYP);
        paramList.add(tia.BODY.PASSNO);
        return paramList;
    }

    private static List<String> assembleT8855Param(TiaXml9009060 tia) {
        List<String> paramList = new ArrayList<String>();
        paramList.add(tia.BODY.CUSKID);
        paramList.add(tia.BODY.PASTYP);
        paramList.add(tia.BODY.PASSNO);
        paramList.add(tia.BODY.ACTTYP);
        paramList.add(tia.BODY.BEGNUM);
        return paramList;
    }

    private static List<String> assembleT8856Param(TiaXml9009061 tia) {
        List<String> paramList = new ArrayList<String>();
        paramList.add(tia.BODY.CUSKID);
        paramList.add(tia.BODY.PASTYP);
        paramList.add(tia.BODY.PASSNO);
        paramList.add(tia.BODY.ACTTYP);
        paramList.add(tia.BODY.BEGNUM);
        return paramList;
    }

    private static List<String> assembleT8118Param(TiaXml9009062 tia) {
        List<String> paramList = new ArrayList<String>();
        paramList.add("111111");
        paramList.add("010"); //柜员机构号
        paramList.add("60");  //柜员部门号
        paramList.add("010"); //帐户机构号
        paramList.add(tia.BODY.ACTNUM);
        return paramList;
    }

    private static List<String> assembleT8854ParamFor9009051(TiaXml9009051 tia) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        List<String> paramList = new ArrayList<String>();
        paramList.add("111111");
        paramList.add("010"); //柜员机构号
        paramList.add("60");  //柜员部门号
        paramList.add("010"); //帐户机构号
        paramList.add(tia.BODY.ACTNO);
        paramList.add(sdf.format(new Date()));
        paramList.add(sdf.format(new Date()));
        paramList.add(StringPad.leftPad4ChineseToByteLength(tia.BODY.START_NUM,6,"0"));
        return paramList;
    }

    private static List<String> assembleT8854ParamFor9009052(TiaXml9009052 tia) {
        List<String> paramList = new ArrayList<String>();
        paramList.add("111111");
        paramList.add("010"); //柜员机构号
        paramList.add("60");  //柜员部门号
        paramList.add("010"); //帐户机构号
        paramList.add(tia.BODY.ACTNO);
        paramList.add(tia.BODY.START_DATE);
        paramList.add(tia.BODY.END_DATE);
        paramList.add(StringPad.leftPad4ChineseToByteLength(tia.BODY.START_NUM,6,"0"));
        return paramList;
    }

    private static List<String> assembleTn080Param(TiaXml9009101 tia) {
        List<String> paramList = new ArrayList<String>();
        paramList.add(StringUtils.rightPad(tia.BODY.BANKSN, 18, ' '));
        paramList.add(StringUtils.rightPad(tia.INFO.REQ_SN, 16, ' '));
        paramList.add(tia.BODY.BNKDAT);
        paramList.add(tia.BODY.BNKTIM);
        paramList.add(tia.BODY.PBKNUM);
        paramList.add(tia.BODY.PBKNAM);
        paramList.add(tia.BODY.PAYACT);
        paramList.add(tia.BODY.PAYNAM);
        paramList.add(tia.BODY.TXNAMT);
        paramList.add(tia.BODY.DCTYPE);
        paramList.add(tia.BODY.RECACT);
        paramList.add(tia.BODY.RECNAM);
        paramList.add("907452000016");
        paramList.add("海尔集团财务有限责任公司");
        paramList.add(tia.BODY.IBKACT);
        paramList.add(tia.BODY.IBKNUM);
        paramList.add(tia.BODY.IBKNAM);
        paramList.add(tia.BODY.RETAUX);
        return paramList;
    }

    private static List<String> assembleTaa41Param(String sn, String fromAcct, String toAcct, BigDecimal txnAmt, String remark) {

        // 转出账户
        String outAct = StringUtils.rightPad(fromAcct, 22, ' ');
        // 转入账户
        String inAct = StringUtils.rightPad(toAcct, 22, ' ');

        DecimalFormat df = new DecimalFormat("#############0.00");
        List<String> txnparamList = new ArrayList<String>();
        String txndate = new SimpleDateFormat("yyyyMMdd").format(new Date());

        //转出帐户类型
        txnparamList.add("01");
        //转出帐户
        txnparamList.add(outAct);
        //取款方式
        txnparamList.add("3");
        //转出帐户户名
        txnparamList.add(StringUtils.leftPad("", 72, ' '));
        //取款密码
        txnparamList.add(StringUtils.leftPad("", 6, ' '));
        //证件类型
        txnparamList.add("N");
        //外围系统流水号
        txnparamList.add(StringUtils.rightPad(sn, 18, ' '));      //交易流水号
        //支票种类
        txnparamList.add(" ");
        //支票号
        txnparamList.add(StringUtils.leftPad("", 10, ' '));
        //支票密码
        txnparamList.add(StringUtils.leftPad("", 12, ' '));
        //签发日期
        txnparamList.add(StringUtils.leftPad("", 8, ' '));
        //无折标识
        txnparamList.add("3");
        //备用字段
        txnparamList.add(StringUtils.leftPad("", 8, ' '));
        //备用字段
        txnparamList.add(StringUtils.leftPad("", 4, ' '));

        //交易金额
        String amt = df.format(txnAmt);
        txnparamList.add("+" + StringUtils.leftPad(amt, 16, '0'));   //金额

        //转入帐户类型
        txnparamList.add("01");
        //转入帐户 (商户帐号)
        String account = StringUtils.rightPad(inAct, 22, ' ');
        txnparamList.add(account);

        //转入帐户户名
        txnparamList.add(StringUtils.leftPad("", 72, ' '));
        //无折标识
        txnparamList.add(" ");
        //交易日期
        txnparamList.add(txndate);
        //摘要
        txnparamList.add(StringPad.rightPad4ChineseToByteLength(remark, 25, " "));
        //产品码
        txnparamList.add(StringUtils.leftPad("", 4, ' '));
        //MAGFL1
        txnparamList.add(" ");
        //MAGFL2
        txnparamList.add(" ");

        return txnparamList;
    }

    private static List<String> assembleTaa4bParam(String sn, String fromAcct, String outActName, String toAcct,
                                                   String inActName, String txnAmt,
                                                   String toAcct2, String inActName2,
                                                   String txnAmt2, String remark) {
//        DecimalFormat df = new DecimalFormat("#############0.00");
        List<String> txnparamList = new ArrayList<String>();
        String txndate = new SimpleDateFormat("yyyyMMdd").format(new Date());

        //转出帐户类型
        txnparamList.add("01");
        //转出帐户
        txnparamList.add(fromAcct);
        //取款方式
        txnparamList.add("3");
        //转出帐户户名
        txnparamList.add(outActName);
        //取款密码
        txnparamList.add(StringUtils.leftPad("", 6, ' '));
        //证件类型
        txnparamList.add("N");
        //外围系统流水号
        txnparamList.add(StringUtils.rightPad(sn, 18, ' '));      //交易流水号
        //支票种类
        txnparamList.add(" ");
        //支票号
        txnparamList.add("");
        //支票密码
        txnparamList.add("");
        //签发日期
        txnparamList.add("");
        //无折标识
        txnparamList.add("3");
        //备用字段
        txnparamList.add("");
        //备用字段
        txnparamList.add("");

        //交易金额
//        String amt = df.format(txnAmt);
//        txnparamList.add("+" + StringUtils.leftPad(amt, 16, '0'));   //金额

        txnparamList.add(txnAmt);

        //转入帐户类型
        txnparamList.add("01");
        //转入帐户 (商户帐号)
        txnparamList.add(toAcct);

        //转入帐户户名
        txnparamList.add(inActName);
        //无折标识
        txnparamList.add(" ");
        //交易日期
        txnparamList.add(txndate);
        //摘要
        txnparamList.add(remark);
        //产品码
        txnparamList.add("N201");
        //MAGFL1
        txnparamList.add(" ");
        //MAGFL2
        txnparamList.add(" ");
        // 再转入帐户
        txnparamList.add(toAcct2);
        // 再转入金额
//        txnparamList.add(df.format(txnAmt2));
        txnparamList.add(txnAmt2);

        // 再转入账户名 20150416
        logger.info("INACTNAME2:" + inActName2);
        txnparamList.add(inActName2);

        return txnparamList;
    }

    private static byte[] convert(String txnCode, String termID, List<String> tiaList) {
//        String header = "TPEI" + txnCode + "  010       " + termID + "MT01                       ";
        // 2015-06-02 授权柜员设置为9999
        String header = "TPEI" + txnCode + "  010       " + termID + "MT019999                   ";
        byte[] tiaBuf = new byte[32000];
        byte[] bytes = header.getBytes();
        System.arraycopy(bytes, 0, tiaBuf, 0, bytes.length);
        try {
            setBufferValues(tiaList, tiaBuf);
        } catch (UnsupportedEncodingException e) {
            logger.error("Unsupported  Encoding", e);
        }
        return tiaBuf;
    }

    private static void setBufferValues(List list, byte[] bb) throws UnsupportedEncodingException {
        int start = 51;
        for (int i = 1; i <= list.size(); i++) {
            String value = list.get(i - 1).toString();
            setVarData(start, value, bb);
            start = start + value.getBytes("GBK").length + 2;
        }
    }

    private static void setVarData(int pos, String data, byte[] aa) throws UnsupportedEncodingException {
        short len = (short) data.getBytes("GBK").length;

        byte[] slen = new byte[2];
        slen[0] = (byte) (len >> 8);
        slen[1] = (byte) (len);
        System.arraycopy(slen, 0, aa, pos, 2);
        System.arraycopy(data.getBytes(), 0, aa, pos + 2, len);
    }

}
