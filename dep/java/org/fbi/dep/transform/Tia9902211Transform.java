package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.txn.Tia9902211;

/**
 * ̩�����������ʽ���ϵͳ���������
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 2015-07-24
 * Time: 9:11
 * To change this template use File | Settings | File Templates.
 */
public class Tia9902211Transform extends AbstractTiaTransform {

    @Override
    public String transform(TIA tia) {
        Tia9902211 tia9902211 = (Tia9902211) tia;
        return convertBeanToStr(tia9902211);
    }

    private String convertBeanToStr(Tia9902211 tia9902211Para) {
        /*01	���״���	    4	2211
          02	������д���	2
          03	���д���	    6
          04	����ҵ����	14
          05	���г�����ˮ	30
          06	��������	    10	��ϵͳ���ڼ���
          07	��������	    30
          08	������Ա	    30
          09	����	        1	1_�������*/
        String strRtn=
                StringUtils.rightPad(tia9902211Para.header.TX_CODE, 4, ' ')+"|"+
                StringUtils.rightPad(tia9902211Para.body.BANK_ID,   2, ' ')+"|"+
                StringUtils.rightPad(tia9902211Para.body.CITY_ID,   6, ' ')+"|"+
                StringUtils.rightPad(tia9902211Para.header.BIZ_ID,  14, ' ')+"|"+               
                StringUtils.rightPad(tia9902211Para.header.REQ_SN,  30, ' ')+"|"+
                StringUtils.rightPad(tia9902211Para.body.TX_DATE,   10, ' ')+"|"+
                StringUtils.rightPad(tia9902211Para.body.BRANCH_ID, 30, ' ')+"|"+
                StringUtils.rightPad(tia9902211Para.header.USER_ID, 30, ' ')+"|"+
                StringUtils.rightPad(tia9902211Para.body.INITIATOR, 1, ' ')+"|";
        Integer intStrRtnLength=strRtn.length();
        strRtn= StringUtils.leftPad(intStrRtnLength.toString(),6, '0')+strRtn;
        return strRtn;
    }
}
