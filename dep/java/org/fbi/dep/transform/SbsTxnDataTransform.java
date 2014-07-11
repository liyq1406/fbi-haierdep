package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.txn.TiaXml9009101;
import org.fbi.dep.util.StringPad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
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



    public static byte[] convertToTxnAa41(String sn, String outActno, String inActno, String amt, String remark) {
        if(StringUtils.isEmpty(remark)) remark = "资金交换平台";
        List<String> tiaList = assembleTaa41Param(sn, outActno, inActno, new BigDecimal(amt), remark);
        return convert("aa41", "MT01", tiaList);
    }

    public static byte[] convertToTxnN080(TiaXml9009101 tia, String termId) {
        return convert("n080", termId, assembleTn080Param(tia));
    }

    private static List<String> assembleTn080Param(TiaXml9009101 tia) {
        List<String> paramList = new ArrayList<String>();
        paramList.add(StringUtils.rightPad(tia.INFO.REQ_SN, 18, ' '));
        paramList.add(StringUtils.rightPad(tia.BODY.BANKSN, 16, ' '));
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

    private static byte[] convert(String txnCode, String termID, List<String> tiaList) {
        String header = "TPEI" + txnCode + "  010       " + termID + "MT01                       ";
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
