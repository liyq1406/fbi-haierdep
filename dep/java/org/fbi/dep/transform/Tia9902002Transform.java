package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.txn.TIA9902002;

/**
 * ̩�����������ʽ���ϵͳ���������
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 2015-07-24
 * Time: 9:11
 * To change this template use File | Settings | File Templates.
 */
public class Tia9902002Transform extends AbstractTiaTransform {

    @Override
    public String transform(TIA tia) {
        TIA9902002 tia9902002 = (TIA9902002) tia;
        return convertBeanToStr(tia9902002);
    }

    private String convertBeanToStr(TIA9902002 tia9902002Para) {
        /*01	���״���	    4	2002
          02	������д���	2
          03	���д���	    6
          04	����������	14
          05	������	    20	2001���׽�����֤���ص�ʵ�ɽ�2003�����޷�����ɹ�Ա¼�롣
          06	����˺�	    30	������֤�������
          07	���㷽ʽ	    2	01_ �ֽ� 02_ ת�� 03_ ֧Ʊ
          08	֧Ʊ����	    30
          09	���м�����ˮ	30
          10	��������	    10	��ϵͳ���ڼ���
          11	��������	    30
          12	������Ա	    30
          13	����	        1	1_�������*/
        String strRtn=
                StringUtils.rightPad(tia9902002Para.header.TX_CODE, 4, ' ')+"|"+
                StringUtils.rightPad(tia9902002Para.body.BANK_ID,   2, ' ')+"|"+
                StringUtils.rightPad(tia9902002Para.body.CITY_ID,   6, ' ')+"|"+
                StringUtils.rightPad(tia9902002Para.header.BIZ_ID,  14, ' ')+"|"+
                StringUtils.rightPad(tia9902002Para.body.TX_AMT,  20, ' ')+"|"+
                StringUtils.rightPad(tia9902002Para.body.ACC_ID,  30, ' ')+"|"+
                StringUtils.rightPad(tia9902002Para.body.STL_TYPE,  2, ' ')+"|"+
                StringUtils.rightPad(tia9902002Para.body.CHECK_ID,  30, ' ')+"|"+
                StringUtils.rightPad(tia9902002Para.header.REQ_SN,  30, ' ')+"|"+
                StringUtils.rightPad(tia9902002Para.body.TX_DATE,   10, ' ')+"|"+
                StringUtils.rightPad(tia9902002Para.body.BRANCH_ID, 30, ' ')+"|"+
                StringUtils.rightPad(tia9902002Para.header.USER_ID, 30, ' ')+"|"+
                StringUtils.rightPad(tia9902002Para.body.INITIATOR, 1, ' ');
        Integer intStrRtnLength=strRtn.length();
        strRtn= StringUtils.leftPad(intStrRtnLength.toString(),6, '0')+"|"+strRtn;
        return strRtn;
    }
    private TIA9902002 convertStrToBean(String strPara) {
        TIA9902002 tia9902002Para=new TIA9902002();
        /*01	���״���	    4	2002
          02	������д���	2
          03	���д���	    6
          04	����������	14
          05	������	    20	2001���׽�����֤���ص�ʵ�ɽ�2003�����޷�����ɹ�Ա¼�롣
          06	����˺�	    30	������֤�������
          07	���㷽ʽ	    2	01_ �ֽ� 02_ ת�� 03_ ֧Ʊ
          08	֧Ʊ����	    30
          09	���м�����ˮ	30
          10	��������	    10	��ϵͳ���ڼ���
          11	��������	    30
          12	������Ա	    30
          13	����	        1	1_�������*/
        strPara=strPara.replace("|","");
          if(strPara.length()>209) {
              tia9902002Para.header.TX_CODE = strPara.substring(0,4);
              tia9902002Para.body.BANK_ID= strPara.substring(4,2);
              tia9902002Para.body.CITY_ID= strPara.substring(6,6);
              tia9902002Para.header.BIZ_ID= strPara.substring(12,14);
              tia9902002Para.body.TX_AMT= strPara.substring(26,20);
              tia9902002Para.body.ACC_ID= strPara.substring(46,30);
              tia9902002Para.body.STL_TYPE= strPara.substring(76,2);
              tia9902002Para.body.CHECK_ID= strPara.substring(78,30);
              tia9902002Para.header.REQ_SN= strPara.substring(108,30);
              tia9902002Para.body.TX_DATE= strPara.substring(138,10);
              tia9902002Para.body.BRANCH_ID= strPara.substring(148,30);
              tia9902002Para.header.USER_ID= strPara.substring(178,30);
              tia9902002Para.body.INITIATOR= strPara.substring(208,1);
          }
        return tia9902002Para;
    }
}
