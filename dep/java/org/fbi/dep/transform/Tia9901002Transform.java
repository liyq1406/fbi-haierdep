package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.txn.Tia9901002;

/**
 * ̩�����������ʽ���ϵͳ���������
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 2015-07-24
 * Time: 9:11
 * To change this template use File | Settings | File Templates.
 */
public class Tia9901002Transform extends AbstractTiaTransform {

    @Override
    public String transform(TIA tia) {
        Tia9901002 tia9901002 = (Tia9901002) tia;
        return convertBeanToStr(tia9901002);
    }

    private String convertBeanToStr(Tia9901002 tia9901002Para) {
          /*01	���״���	    4	1002
          02	������д���	2
          03	���д���	    6
          04	��ֹ֤�����    14
          05    ���ר���˺�    30
          06    ���ר������    150
          07	��ˮ��    	    30
          08	����	        10	��ϵͳ���ڼ���
          09	�����	        30
          10	��Ա��	        30
          11	����	        1	1_�������*/
        String strRtn=
                StringUtils.rightPad(tia9901002Para.header.TX_CODE, 4, ' ')+"|"+
                StringUtils.rightPad(tia9901002Para.body.BANK_ID,   2, ' ')+"|"+
                StringUtils.rightPad(tia9901002Para.body.CITY_ID,   6, ' ')+"|"+
                StringUtils.rightPad(tia9901002Para.header.BIZ_ID,  14, ' ')+"|"+
                StringUtils.rightPad(tia9901002Para.body.ACC_ID,    30, ' ')+"|"+
                StringUtils.rightPad(tia9901002Para.body.ACC_NAME,  150, ' ')+"|"+
                StringUtils.rightPad(tia9901002Para.header.REQ_SN,  30, ' ')+"|"+
                StringUtils.rightPad(tia9901002Para.body.TX_DATE,   10, ' ')+"|"+
                StringUtils.rightPad(tia9901002Para.body.BRANCH_ID, 30, ' ')+"|"+
                StringUtils.rightPad(tia9901002Para.header.USER_ID, 30, ' ')+"|"+
                StringUtils.rightPad(tia9901002Para.body.INITIATOR, 1, ' ');
        Integer intStrRtnLength=strRtn.replace("|","").length();
        strRtn= StringUtils.leftPad(intStrRtnLength.toString(),6, '0')+"|"+strRtn;
        return strRtn;
    }
    private Tia9901002 convertStrToBean(String strPara) {
        Tia9901002 tia9901002Para=new Tia9901002();
        /*01	���״���	    4	1002
          02	������д���	2
          03	���д���	    6
          04	��ֹ֤�����    14
          05    ���ר���˺�    30
          06    ���ר������    150
          07	��ˮ��    	    30
          08	����	        10	��ϵͳ���ڼ���
          09	�����	        30
          10	��Ա��	        30
          11	����	        1	1_�������*/
        strPara=strPara.replace("|","");
          if(strPara.length()>307) {
              tia9901002Para.header.TX_CODE = strPara.substring(0,4);
              tia9901002Para.body.BANK_ID= strPara.substring(4,2);
              tia9901002Para.body.CITY_ID= strPara.substring(6,6);
              tia9901002Para.header.BIZ_ID= strPara.substring(12,14);
              tia9901002Para.body.ACC_ID= strPara.substring(26,30);
              tia9901002Para.body.ACC_NAME= strPara.substring(56,150);
              tia9901002Para.header.REQ_SN= strPara.substring(206,30);
              tia9901002Para.body.TX_DATE= strPara.substring(236,10);
              tia9901002Para.body.BRANCH_ID= strPara.substring(246,30);
              tia9901002Para.header.USER_ID= strPara.substring(276,30);
              tia9901002Para.body.INITIATOR= strPara.substring(306,1);
          }
        return tia9901002Para;
    }
}
