package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.txn.TIA9902011;

/**
 * 泰安房产中心资金监管系统—交存冲正
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 2015-07-24
 * Time: 9:11
 * To change this template use File | Settings | File Templates.
 */
public class Tia9902011Transform extends AbstractTiaTransform {

    @Override
    public String transform(TIA tia) {
        TIA9902011 tia9902011 = (TIA9902011) tia;
        return convertBeanToStr(tia9902011);
    }

    private String convertBeanToStr(TIA9902011 tia9902011Para) {
         /*01	交易代码	    4	2011
          02	监管银行代码	2
          03	城市代码	    6
          04	交存申请编号	14
          05	银行冲正流水	30
          06	冲正日期	    10	送系统日期即可
          07	冲正网点	    30
          08	冲正人员	    30
          09	发起方	        1	1_监管银行*/
        String strRtn=
                StringUtils.rightPad(tia9902011Para.header.TX_CODE, 4, ' ')+"|"+
                StringUtils.rightPad(tia9902011Para.body.BANK_ID,   2, ' ')+"|"+
                StringUtils.rightPad(tia9902011Para.body.CITY_ID,   6, ' ')+"|"+
                StringUtils.rightPad(tia9902011Para.header.BIZ_ID,  14, ' ')+"|"+               
                StringUtils.rightPad(tia9902011Para.header.REQ_SN,  30, ' ')+"|"+
                StringUtils.rightPad(tia9902011Para.body.TX_DATE,   10, ' ')+"|"+
                StringUtils.rightPad(tia9902011Para.body.BRANCH_ID, 30, ' ')+"|"+
                StringUtils.rightPad(tia9902011Para.header.USER_ID, 30, ' ')+"|"+
                StringUtils.rightPad(tia9902011Para.body.INITIATOR, 1, ' ');
        Integer intStrRtnLength=strRtn.length();
        strRtn= StringUtils.leftPad(intStrRtnLength.toString(),6, '0')+"|"+strRtn;
        return strRtn;
    }
    private TIA9902011 convertStrToBean(String strPara) {
        TIA9902011 tia9902011Para=new TIA9902011();
         /*01	交易代码	    4	2011
          02	监管银行代码	2
          03	城市代码	    6
          04	交存申请编号	14
          05	银行冲正流水	30
          06	冲正日期	    10	送系统日期即可
          07	冲正网点	    30
          08	冲正人员	    30
          09	发起方	        1	1_监管银行*/
        strPara=strPara.replace("|","");
          if(strPara.length()>127) {
              tia9902011Para.header.TX_CODE = strPara.substring(0,4);
              tia9902011Para.body.BANK_ID= strPara.substring(4,2);
              tia9902011Para.body.CITY_ID= strPara.substring(6,6);
              tia9902011Para.header.BIZ_ID= strPara.substring(12,14);
              tia9902011Para.header.REQ_SN= strPara.substring(26,30);
              tia9902011Para.body.TX_DATE= strPara.substring(56,10);
              tia9902011Para.body.BRANCH_ID= strPara.substring(66,30);
              tia9902011Para.header.USER_ID= strPara.substring(96,30);
              tia9902011Para.body.INITIATOR= strPara.substring(126,1);
          }
        return tia9902011Para;
    }
}
