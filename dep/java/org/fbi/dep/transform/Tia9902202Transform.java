package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.txn.Tia9902202;

/**
 * ̩�����������ʽ���ϵͳ����������
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 2015-07-24
 * Time: 9:11
 * To change this template use File | Settings | File Templates.
 */
public class Tia9902202Transform extends AbstractTiaTransform {

    @Override
    public String transform(TIA tia) {
        Tia9902202 tia9902202 = (Tia9902202) tia;
        return convertBeanToStr(tia9902202);
    }

    private String convertBeanToStr(Tia9902202 tia9902202Para) {
        /*01	���״���	    4	2202
          02	������д���	2
          03	���д���	    6
          04	����ҵ����	14
          05	��������	    32	MD5
          06	����˺�	    30
          07	�����ʽ�	    20
          08	���м�����ˮ	30
          09	��������	    10	��ϵͳ���ڼ���
          10	��������	    30
          11	������Ա	    30
          12	����	        1	1_�������*/
        String strRtn=
                StringUtils.rightPad(tia9902202Para.header.TX_CODE, 4, ' ')+"|"+
                StringUtils.rightPad(tia9902202Para.body.BANK_ID,   2, ' ')+"|"+
                StringUtils.rightPad(tia9902202Para.body.CITY_ID,   6, ' ')+"|"+
                StringUtils.rightPad(tia9902202Para.header.BIZ_ID,  14, ' ')+"|"+
                StringUtils.rightPad(tia9902202Para.header.PASSWORD,32, ' ')+"|"+
                StringUtils.rightPad(tia9902202Para.body.ACC_ID,  30, ' ')+"|"+
                StringUtils.rightPad(tia9902202Para.body.TX_AMT,  20, ' ')+"|"+
                StringUtils.rightPad(tia9902202Para.header.REQ_SN,  30, ' ')+"|"+
                StringUtils.rightPad(tia9902202Para.body.TX_DATE,   10, ' ')+"|"+
                StringUtils.rightPad(tia9902202Para.body.BRANCH_ID, 30, ' ')+"|"+
                StringUtils.rightPad(tia9902202Para.header.USER_ID, 30, ' ')+"|"+
                StringUtils.rightPad(tia9902202Para.body.INITIATOR, 1, ' ');
        Integer intStrRtnLength=strRtn.replace("|","").length();
        strRtn= StringUtils.leftPad(intStrRtnLength.toString(),6, '0')+"|"+strRtn;
        return strRtn;
    }
    private Tia9902202 convertStrToBean(String strPara) {
        Tia9902202 tia9902202Para=new Tia9902202();
        /*01	���״���	    4	2202
          02	������д���	2
          03	���д���	    6
          04	����ҵ����	14
          05	��������	    32	MD5
          06	����˺�	    30
          07	�����ʽ�	    20
          08	���м�����ˮ	30
          09	��������	    10	��ϵͳ���ڼ���
          10	��������	    30
          11	������Ա	    30
          12	����	        1	1_�������*/
        strPara=strPara.replace("|","");
          if(strPara.length()>209) {
              tia9902202Para.header.TX_CODE = strPara.substring(0,4);
              tia9902202Para.body.BANK_ID= strPara.substring(4,2);
              tia9902202Para.body.CITY_ID= strPara.substring(6,6);
              tia9902202Para.header.BIZ_ID= strPara.substring(12,14);
              tia9902202Para.header.PASSWORD= strPara.substring(26,32);
              tia9902202Para.body.ACC_ID= strPara.substring(58,30);
              tia9902202Para.body.TX_AMT= strPara.substring(88,20);
              tia9902202Para.header.REQ_SN= strPara.substring(108,30);
              tia9902202Para.body.TX_DATE= strPara.substring(138,10);
              tia9902202Para.body.BRANCH_ID= strPara.substring(148,30);
              tia9902202Para.header.USER_ID= strPara.substring(178,30);
              tia9902202Para.body.INITIATOR= strPara.substring(208,1);
          }
        return tia9902202Para;
    }
}
