package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.txn.Tia9902501;

/**
 * ̩�����������ʽ���ϵͳ���������
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 2015-07-24
 * Time: 9:11
 * To change this template use File | Settings | File Templates.
 */
public class Tia9902501Transform extends AbstractTiaTransform {

    @Override
    public String transform(TIA tia) {
        Tia9902501 tia9902501 = (Tia9902501) tia;
        return convertBeanToStr(tia9902501);
    }

    private String convertBeanToStr(Tia9902501 tia9902501Para) {
        /*01	���״���	    4	2501
          02	������д���	2
          03	���д���	    6
          04	ҵ����	    14 ��������� ����ҵ���� �˻�ҵ����
          05	��ѯ����	    30
          06	��ѯ��Ա	    30
          07	����	        1 1_�������*/
        String strRtn=
                StringUtils.rightPad(tia9902501Para.header.TX_CODE, 4, ' ')+"|"+
                StringUtils.rightPad(tia9902501Para.body.BANK_ID,   2, ' ')+"|"+
                StringUtils.rightPad(tia9902501Para.body.CITY_ID,   6, ' ')+"|"+
                StringUtils.rightPad(tia9902501Para.header.BIZ_ID,  14, ' ')+"|"+               
                StringUtils.rightPad(tia9902501Para.body.BRANCH_ID, 30, ' ')+"|"+
                StringUtils.rightPad(tia9902501Para.header.USER_ID, 30, ' ')+"|"+
                StringUtils.rightPad(tia9902501Para.body.INITIATOR, 1, ' ');
        Integer intStrRtnLength=strRtn.replace("|","").length();
        strRtn= StringUtils.leftPad(intStrRtnLength.toString(),6, '0')+"|"+strRtn;
        return strRtn;
    }
    private Tia9902501 convertStrToBean(String strPara) {
        Tia9902501 tia9902501Para=new Tia9902501();
         /*01	���״���	    4	2501
          02	������д���	2
          03	���д���	    6
          04	ҵ����	    14 ��������� ����ҵ���� �˻�ҵ����
          05	��ѯ����	    30
          06	��ѯ��Ա	    30
          07	����	        1 1_�������*/
        strPara=strPara.replace("|","");
          if(strPara.length()>127) {
              tia9902501Para.header.TX_CODE = strPara.substring(0,4);
              tia9902501Para.body.BANK_ID= strPara.substring(4,2);
              tia9902501Para.body.CITY_ID= strPara.substring(6,6);
              tia9902501Para.header.BIZ_ID= strPara.substring(12,14);
              tia9902501Para.body.BRANCH_ID= strPara.substring(26,30);
              tia9902501Para.header.USER_ID= strPara.substring(56,30);
              tia9902501Para.body.INITIATOR= strPara.substring(86,1);
          }
        return tia9902501Para;
    }
}
