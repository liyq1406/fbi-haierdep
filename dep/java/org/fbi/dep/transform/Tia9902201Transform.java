package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.txn.Tia9902201;
import org.fbi.dep.util.ToolUtil;

/**
 * 泰安房产中心资金监管系统—返还验证
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 2015-07-24
 * Time: 9:11
 * To change this template use File | Settings | File Templates.
 */
public class Tia9902201Transform extends AbstractTiaTransform {

    @Override
    public String transform(TIA tia) {
        Tia9902201 tia9902201 = (Tia9902201) tia;
        return convertBeanToStr(tia9902201);
    }

    private String convertBeanToStr(Tia9902201 tia9902201Para) {
        /*01	交易代码	    4	2201
          02	监管银行代码	2
          03	城市代码	    6
          04	返还业务编号	14
          05	支付密码	    32	MD5
          06	验证流水    	30
          07	验证日期	    10	送系统日期即可
          08	验证网点	    30
          09	验证人员	    30
          10	发起方	        1	1_监管银行*/
        String strRtn=
                ToolUtil.rightPad(tia9902201Para.header.TX_CODE, 4, ' ')+"|"+
                ToolUtil.rightPad(tia9902201Para.body.SPVSN_BANK_ID,   2, ' ')+"|"+
                ToolUtil.rightPad(tia9902201Para.body.CITY_ID,   6, ' ')+"|"+
                ToolUtil.rightPad(tia9902201Para.header.BIZ_ID,  14, ' ')+"|"+
                ToolUtil.rightPad(tia9902201Para.header.PASSWORD,  32, ' ')+"|"+
                ToolUtil.rightPad(tia9902201Para.header.REQ_SN,  30, ' ')+"|"+
                ToolUtil.rightPad(tia9902201Para.body.TX_DATE,   10, ' ')+"|"+
                ToolUtil.rightPad(tia9902201Para.body.BRANCH_ID, 30, ' ')+"|"+
                ToolUtil.rightPad(tia9902201Para.header.USER_ID, 30, ' ')+"|"+
                ToolUtil.rightPad(tia9902201Para.body.INITIATOR, 1, ' ')+"|";
        Integer intStrRtnLength=strRtn.length();
        strRtn= StringUtils.leftPad(intStrRtnLength.toString(),6, '0')+strRtn;
        return strRtn;
    }
}
