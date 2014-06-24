package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
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
 * Time: ����4:38
 * To change this template use File | Settings | File Templates.
 */
public class SbsTxnDataTransform {

    private static Logger logger = LoggerFactory.getLogger(SbsTxnDataTransform.class);

    private static byte[] convert(String txnCode, List<String> tiaList) {
        String header = "TPEI" + txnCode + "  010       MT01MT01                       ";
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

    public static byte[] convertToTxnaa41(String sn, String outActno, String inActno, String amt, String remark) {
        if(StringUtils.isEmpty(remark)) remark = "�ʽ𽻻�ƽ̨";
        List<String> tiaList = assembleTaa41Param(sn, outActno, inActno, new BigDecimal(amt), remark);
        return convert("aa41", tiaList);
    }

    private static List<String> assembleTaa41Param(String sn, String fromAcct, String toAcct, BigDecimal txnAmt, String remark) {

        // ת���˻�
        String outAct = StringUtils.rightPad(fromAcct, 22, ' ');
        // ת���˻�
        String inAct = StringUtils.rightPad(toAcct, 22, ' ');

        DecimalFormat df = new DecimalFormat("#############0.00");
        List<String> txnparamList = new ArrayList<String>();
        String txndate = new SimpleDateFormat("yyyyMMdd").format(new Date());

        //ת���ʻ�����
        txnparamList.add("01");
        //ת���ʻ�
        txnparamList.add(outAct);
        //ȡ�ʽ
        txnparamList.add("3");
        //ת���ʻ�����
        txnparamList.add(StringUtils.leftPad("", 72, ' '));
        //ȡ������
        txnparamList.add(StringUtils.leftPad("", 6, ' '));
        //֤������
        txnparamList.add("N");
        //��Χϵͳ��ˮ��
        txnparamList.add(StringUtils.rightPad(sn, 18, ' '));      //������ˮ��
        //֧Ʊ����
        txnparamList.add(" ");
        //֧Ʊ��
        txnparamList.add(StringUtils.leftPad("", 10, ' '));
        //֧Ʊ����
        txnparamList.add(StringUtils.leftPad("", 12, ' '));
        //ǩ������
        txnparamList.add(StringUtils.leftPad("", 8, ' '));
        //���۱�ʶ
        txnparamList.add("3");
        //�����ֶ�
        txnparamList.add(StringUtils.leftPad("", 8, ' '));
        //�����ֶ�
        txnparamList.add(StringUtils.leftPad("", 4, ' '));

        //���׽��
        String amt = df.format(txnAmt);
        txnparamList.add("+" + StringUtils.leftPad(amt, 16, '0'));   //���

        //ת���ʻ�����
        txnparamList.add("01");
        //ת���ʻ� (�̻��ʺ�)
        String account = StringUtils.rightPad(inAct, 22, ' ');
        txnparamList.add(account);

        //ת���ʻ�����
        txnparamList.add(StringUtils.leftPad("", 72, ' '));
        //���۱�ʶ
        txnparamList.add(" ");
        //��������
        txnparamList.add(txndate);
        //ժҪ
        txnparamList.add(StringPad.rightPad4ChineseToByteLength(remark, 25, " "));
        //��Ʒ��
        txnparamList.add(StringUtils.leftPad("", 4, ' '));
        //MAGFL1
        txnparamList.add(" ");
        //MAGFL2
        txnparamList.add(" ");

        return txnparamList;
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