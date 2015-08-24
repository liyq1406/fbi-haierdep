package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.txn.Tia9902001;

/**
 * ̩�����������ʽ���ϵͳ��������֤
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 2015-07-24
 * Time: 9:11
 * To change this template use File | Settings | File Templates.
 */
public class Tia9902001Transform extends AbstractTiaTransform {

    @Override
    public String transform(TIA tia) {
        Tia9902001 tia9902001 = (Tia9902001) tia;
        return convertBeanToStr(tia9902001);
    }

    private String convertBeanToStr(Tia9902001 tia9902001Para) {
        /*01	���״���	    4	2001
          02	������д���	2
          03	���д���	    6
          04	����������    14
          05	��֤��ˮ    	30
          06	��֤����	    10	��ϵͳ���ڼ���
          07	��֤����	    30
          08	��֤��Ա	    30
          09	����	        1	1_�������*/
        String strRtn=
                StringUtils.rightPad(tia9902001Para.header.TX_CODE, 4, ' ')+"|"+
                StringUtils.rightPad(tia9902001Para.body.BANK_ID,   2, ' ')+"|"+
                StringUtils.rightPad(tia9902001Para.body.CITY_ID,   6, ' ')+"|"+
                StringUtils.rightPad(tia9902001Para.header.BIZ_ID,  14, ' ')+"|"+
                StringUtils.rightPad(tia9902001Para.header.REQ_SN,  30, ' ')+"|"+
                StringUtils.rightPad(tia9902001Para.body.TX_DATE,   10, ' ')+"|"+
                StringUtils.rightPad(tia9902001Para.body.BRANCH_ID, 30, ' ')+"|"+
                StringUtils.rightPad(tia9902001Para.header.USER_ID, 30, ' ')+"|"+
                StringUtils.rightPad(tia9902001Para.body.INITIATOR, 1, ' ')+"|";
        Integer intStrRtnLength=strRtn.length();
        strRtn= StringUtils.leftPad(intStrRtnLength.toString(),6, '0')+strRtn;
        return strRtn;
    }
}
