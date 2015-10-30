package org.fbi.dep.com;

import org.fbi.dep.enums.TxnStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class ToolUtil {

    private static final Logger logger = LoggerFactory.getLogger(ToolUtil.class);

    /**
     * @param strSbsRtnCodePara ��SBS���صķ�����
     * @return ����DEP��װ��ķ�����
     */
    public static String SbsRtnCodeToDepRtnCode(String strTxnCodePara,String strSbsRtnCodePara) {
        String strDepRtnCodeTmp=null;
        String strTxnTypeTmp=null;

        // ------�������������------
        // �����ڲ�ת��,�����ڲ�ת����ת��,�˻�����ѯ,�˻����ս�����ϸ��ѯ,�˻���ʷ������ϸ��ѯ,
        // ���ݿͻ�֤����Ϣ��ѯ�˻��б�,���ݿͻ�֤����Ϣ��ѯ�˺��б�,�����˺Ų�ѯ�˻���Ϣ,
        // ͬҵ����֪ͨ����,�ܷ��˻�����֪ͨ����,ͬҵ����֪ͨ���˽���,����֧������������,�������˿ͻ���Ϣ,�����
        if(("9009001,9009004,9009050,9009051,9009052," +
            "9009060,9009061,9009062," +
                "9009501,9009502,9009503,9009504,9009505,9009506,9009507,9009508," +
            "9009101,9009102,9009103,9009201,9009301,9009401").indexOf(strTxnCodePara)>=0){
            strTxnTypeTmp="ONLY_SBS";
        }else
        // ���ʶ���֧��,�������ʴ���
        if("9009002,9009010".indexOf(strTxnCodePara)>=0){
            strTxnTypeTmp="SBS_TO_OUT_EXE";
        }else
        // ���ʶ���֧�������ѯ
        if("9009003".indexOf(strTxnCodePara)>=0){
            strTxnTypeTmp="SBS_TO_OUT_QRY";
        }

        // ------���ݽ������ת��DEP������------
        // SBS�ܹ�ֱ�ӻ�Ӧ��
        if("ONLY_SBS".equals(strTxnTypeTmp)){
            if (strSbsRtnCodePara.startsWith("T")||
                    (strSbsRtnCodePara.startsWith("W"))) {
                strDepRtnCodeTmp=TxnStatus.TXN_SUCCESS.getCode();
            }else {
                strDepRtnCodeTmp=TxnStatus.TXN_FAILED.getCode();
            }
        }else
        // SBS���ܹ�ֱ�ӻ�Ӧ�� ����֧��
        if("SBS_TO_OUT_EXE".equals(strTxnTypeTmp)) {
            ArrayList<String> failRtnCodeListTmp=new ArrayList<String>();
            failRtnCodeListTmp.add("F001");
            failRtnCodeListTmp.add("F032");
            failRtnCodeListTmp.add("F033");
            failRtnCodeListTmp.add("F034");
            failRtnCodeListTmp.add("F035");
            failRtnCodeListTmp.add("F036");
            failRtnCodeListTmp.add("F037");
            failRtnCodeListTmp.add("F038");
            failRtnCodeListTmp.add("F127");
            failRtnCodeListTmp.add("F705");
            failRtnCodeListTmp.add("F711");
            failRtnCodeListTmp.add("M057");
            failRtnCodeListTmp.add("M102");
            failRtnCodeListTmp.add("M103");
            failRtnCodeListTmp.add("M104");
            failRtnCodeListTmp.add("M108");
            failRtnCodeListTmp.add("M115");
            failRtnCodeListTmp.add("M259");
            failRtnCodeListTmp.add("M309");
            failRtnCodeListTmp.add("M310");
            failRtnCodeListTmp.add("M311");
            failRtnCodeListTmp.add("M313");
            failRtnCodeListTmp.add("M316");
            failRtnCodeListTmp.add("M409");
            failRtnCodeListTmp.add("M612");
            failRtnCodeListTmp.add("M767");
            failRtnCodeListTmp.add("M801");
            failRtnCodeListTmp.add("M802");
            failRtnCodeListTmp.add("M803");
            failRtnCodeListTmp.add("M808");
            failRtnCodeListTmp.add("M901");
            failRtnCodeListTmp.add("M923");
            failRtnCodeListTmp.add("M930");
            failRtnCodeListTmp.add("M931");
            failRtnCodeListTmp.add("M947");
            failRtnCodeListTmp.add("MH32");
            failRtnCodeListTmp.add("MZZZ");
            if (strSbsRtnCodePara.startsWith("T")) {
                strDepRtnCodeTmp = TxnStatus.TXN_SUCCESS.getCode();
            } else if (failRtnCodeListTmp.contains(strSbsRtnCodePara)) {
                strDepRtnCodeTmp = TxnStatus.TXN_FAILED.getCode();
            } else {
                strDepRtnCodeTmp = TxnStatus.TXN_QRY_PEND.getCode();
            }
        }else
        // SBS���ܹ�ֱ�ӻ�Ӧ�� ��������ѯ
        if("SBS_TO_OUT_QRY".equals(strTxnTypeTmp)) {
            if (!strSbsRtnCodePara.startsWith("T")) {
                strDepRtnCodeTmp = TxnStatus.TXN_SUCCESS.getCode();
            } else if (strSbsRtnCodePara.equals("WB02")) {
                strDepRtnCodeTmp = TxnStatus.TXN_FAILED.getCode();
            } else {
                strDepRtnCodeTmp = TxnStatus.TXN_QRY_PEND.getCode();
            }
        }
        return strDepRtnCodeTmp;
    }
}

