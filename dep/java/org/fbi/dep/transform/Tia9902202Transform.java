package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.txn.Tia9902202;

/**
 * 泰安房产中心资金监管系统—返还记账
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
        /*01	交易代码	    4	2202
          02	监管银行代码	2
          03	城市代码	    6
          04	返还业务编号	14
          05	返还密码	    32	MD5
          06	监管账号	    30
          07	返还资金	    20
          08	银行记账流水	30
          09	记账日期	    10	送系统日期即可
          10	记账网点	    30
          11	记账人员	    30
          12	发起方	        1	1_监管银行*/
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
                StringUtils.rightPad(tia9902202Para.body.INITIATOR, 1, ' ')+"|";
        Integer intStrRtnLength=strRtn.length();
        strRtn= StringUtils.leftPad(intStrRtnLength.toString(),6, '0')+strRtn;
        return strRtn;
    }
}
