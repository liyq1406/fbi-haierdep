package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.base.TOA;
import org.fbi.dep.model.txn.TIA1001001;
import org.fbi.dep.model.txn.TIA9901001;
import org.fbi.dep.model.txn.TOA9901001;
import org.fbi.dep.util.DateUtils;
import org.fbi.dep.util.PropertyManager;
import org.fbi.endpoint.unionpay.txn.domain.T100001Tia;
import org.fbi.endpoint.unionpay.txn.domain.T100004Tia;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ̩�����������ʽ���ϵͳ���������
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 2015-07-24
 * Time: 9:11
 * To change this template use File | Settings | File Templates.
 */
public class Tia9901001Transform extends AbstractTiaTransform {

    @Override
    public String transform(TIA tia) {
        TIA9901001 tia9901001 = (TIA9901001) tia;
        return convertBeanToStr(tia9901001);
    }

    private String convertBeanToStr(TIA9901001 tia9901001Para) {
        /*01	���״���	    4	1001
          02	������д���	2
          03	���д���	    6
          04	���������    14
          05    �ʻ����        1  0��Ԥ�ۼ�ܻ�
          06    ���ר���˺�    30
          07    ���ר������    150
          08	��ˮ��    	    30
          09	����	        10	��ϵͳ���ڼ���
          10	�����	        30
          11	��Ա��	        30
          12	����	        1	1_�������*/
        String strRtn=
                StringUtils.rightPad(tia9901001Para.header.TX_CODE, 4, ' ')+"|"+
                StringUtils.rightPad(tia9901001Para.body.BANK_ID,   2, ' ')+"|"+
                StringUtils.rightPad(tia9901001Para.body.CITY_ID,   6, ' ')+"|"+
                StringUtils.rightPad(tia9901001Para.header.BIZ_ID,  14, ' ')+"|"+
                StringUtils.rightPad(tia9901001Para.body.ACC_TYPE,  1, ' ')+"|"+
                StringUtils.rightPad(tia9901001Para.body.ACC_ID,    30, ' ')+"|"+
                StringUtils.rightPad(tia9901001Para.body.ACC_NAME,  150, ' ')+"|"+
                StringUtils.rightPad(tia9901001Para.header.REQ_SN,  30, ' ')+"|"+
                StringUtils.rightPad(tia9901001Para.body.TX_DATE,   10, ' ')+"|"+
                StringUtils.rightPad(tia9901001Para.body.BRANCH_ID, 30, ' ')+"|"+
                StringUtils.rightPad(tia9901001Para.header.USER_ID, 30, ' ')+"|"+
                StringUtils.rightPad(tia9901001Para.body.INITIATOR, 1, ' ');
        Integer intStrRtnLength=strRtn.length();
        strRtn= StringUtils.leftPad(intStrRtnLength.toString(),6, '0')+"|"+strRtn;
        return strRtn;
    }
}
