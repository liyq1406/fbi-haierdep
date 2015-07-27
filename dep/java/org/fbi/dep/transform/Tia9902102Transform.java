package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.txn.TIA9902102;

/**
 * ̩�����������ʽ���ϵͳ����������
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 2015-07-24
 * Time: 9:11
 * To change this template use File | Settings | File Templates.
 */
public class Tia9902102Transform extends AbstractTiaTransform {

    @Override
    public String transform(TIA tia) {
        TIA9902102 tia9902102 = (TIA9902102) tia;
        return convertBeanToStr(tia9902102);
    }

    private String convertBeanToStr(TIA9902102 tia9902102Para) {
        /*01	���״���	    4	2102
          02	������д���	2
          03	���д���	    6
          04	����ҵ����	14
          05	��������	    32	MD5
          06	����˺�	    30	������֤�������
          07	�տλ�˺�	30	������֤�������
          08	�����ʽ�	    20	������֤�������
          09	���㷽ʽ	    2	01_ �ֽ� 02_ ת�� 03_ ֧Ʊ
          10	֧Ʊ����	    30
          11	���м�����ˮ	30
          12	��������	    10	��ϵͳ���ڼ���
          13	��������	    30
          14	������Ա	    30
          15	����	        1	1_�������*/
        String strRtn=
                StringUtils.rightPad(tia9902102Para.header.TX_CODE, 4, ' ')+"|"+
                StringUtils.rightPad(tia9902102Para.body.BANK_ID,   2, ' ')+"|"+
                StringUtils.rightPad(tia9902102Para.body.CITY_ID,   6, ' ')+"|"+
                StringUtils.rightPad(tia9902102Para.header.BIZ_ID,  14, ' ')+"|"+
                StringUtils.rightPad(tia9902102Para.header.PASSWORD,32, ' ')+"|"+
                StringUtils.rightPad(tia9902102Para.body.ACC_ID,  30, ' ')+"|"+
                StringUtils.rightPad(tia9902102Para.body.RECV_ACC_ID,30, ' ')+"|"+
                StringUtils.rightPad(tia9902102Para.body.TX_AMT,  20, ' ')+"|"+
                StringUtils.rightPad(tia9902102Para.body.STL_TYPE,  2, ' ')+"|"+
                StringUtils.rightPad(tia9902102Para.body.CHECK_ID,  30, ' ')+"|"+
                StringUtils.rightPad(tia9902102Para.header.REQ_SN,  30, ' ')+"|"+
                StringUtils.rightPad(tia9902102Para.body.TX_DATE,   10, ' ')+"|"+
                StringUtils.rightPad(tia9902102Para.body.BRANCH_ID, 30, ' ')+"|"+
                StringUtils.rightPad(tia9902102Para.header.USER_ID, 30, ' ')+"|"+
                StringUtils.rightPad(tia9902102Para.body.INITIATOR, 1, ' ');
        Integer intStrRtnLength=strRtn.length();
        strRtn= StringUtils.leftPad(intStrRtnLength.toString(),6, '0')+"|"+strRtn;
        return strRtn;
    }
    private TIA9902102 convertStrToBean(String strPara) {
        TIA9902102 tia9902102Para=new TIA9902102();
        /*01	���״���	    4	2102
          02	������д���	2
          03	���д���	    6
          04	����ҵ����	14
          05	��������	    32	MD5
          06	����˺�	    30	������֤�������
          07	�տλ�˺�	30	������֤�������
          08	�����ʽ�	    20	������֤�������
          09	���㷽ʽ	    2	01_ �ֽ� 02_ ת�� 03_ ֧Ʊ
          10	֧Ʊ����	    30
          11	���м�����ˮ	30
          12	��������	    10	��ϵͳ���ڼ���
          13	��������	    30
          14	������Ա	    30
          15	����	        1	1_�������*/
        strPara=strPara.replace("|","");
          if(strPara.length()>209) {
              tia9902102Para.header.TX_CODE = strPara.substring(0,4);
              tia9902102Para.body.BANK_ID= strPara.substring(4,2);
              tia9902102Para.body.CITY_ID= strPara.substring(6,6);
              tia9902102Para.header.BIZ_ID= strPara.substring(12,14);
              tia9902102Para.header.PASSWORD= strPara.substring(26,32);
              tia9902102Para.body.ACC_ID= strPara.substring(58,30);
              tia9902102Para.body.RECV_ACC_ID= strPara.substring(88,30);
              tia9902102Para.body.TX_AMT= strPara.substring(118,20);
              tia9902102Para.body.STL_TYPE= strPara.substring(138,2);
              tia9902102Para.body.CHECK_ID= strPara.substring(140,30);
              tia9902102Para.header.REQ_SN= strPara.substring(170,30);
              tia9902102Para.body.TX_DATE= strPara.substring(210,10);
              tia9902102Para.body.BRANCH_ID= strPara.substring(148,30);
              tia9902102Para.header.USER_ID= strPara.substring(178,30);
              tia9902102Para.body.INITIATOR= strPara.substring(208,1);
          }
        return tia9902102Para;
    }
}
