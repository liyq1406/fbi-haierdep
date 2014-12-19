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
 * Time: ����4:38
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
            Field[] fields = tia.getClass().getFields();
            Object obj = null;
            for (Field field : fields) {
                obj = field.get(tia.BODY);
                if (obj != null) {
                    tiaList.add(obj.toString());
                } else {
                    tiaList.add("");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("SBS������װ�쳣");
        }
        return convert("n120", termId, tiaList);
    }

    public static byte[] convertToTxnAa41(String sn, String outActno, String inActno, String amt, String termId, String remark) {
        if (StringUtils.isEmpty(remark)) remark = "�ʽ𽻻�ƽ̨";
        List<String> tiaList = assembleTaa41Param(sn, outActno, inActno, new BigDecimal(amt), remark);
        return convert("aa41", termId, tiaList);
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

    public static byte[] convertToTxn8011(TiaXml9009301 tia, String termId) {
        return convert("8011", termId, assembleT8011Param(tia));
    }


    public static byte[] convertToTxn8124(TiaXml9009401 tia, String termId) {
        return convert("8124", termId, assembleT8124Param(tia));
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
        paramList.add("�������Ų����������ι�˾");
        paramList.add(tia.BODY.IBKACT);
        paramList.add(tia.BODY.IBKNUM);
        paramList.add(tia.BODY.IBKNAM);
        paramList.add(tia.BODY.RETAUX);
        return paramList;
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
