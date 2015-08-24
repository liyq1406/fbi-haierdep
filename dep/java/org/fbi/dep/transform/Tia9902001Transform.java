package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.txn.Tia9902001;

/**
 * 泰安房产中心资金监管系统—交存验证
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
        /*01	交易代码	    4	2001
          02	监管银行代码	2
          03	城市代码	    6
          04	交存申请编号    14
          05	验证流水    	30
          06	验证日期	    10	送系统日期即可
          07	验证网点	    30
          08	验证人员	    30
          09	发起方	        1	1_监管银行*/
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
