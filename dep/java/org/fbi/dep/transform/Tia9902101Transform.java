package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.txn.TIA9902101;

/**
 * 泰安房产中心资金监管系统—划拨验证
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 2015-07-24
 * Time: 9:11
 * To change this template use File | Settings | File Templates.
 */
public class Tia9902101Transform extends AbstractTiaTransform {

    @Override
    public String transform(TIA tia) {
        TIA9902101 tia9902101 = (TIA9902101) tia;
        return convertBeanToStr(tia9902101);
    }

    private String convertBeanToStr(TIA9902101 tia9902101Para) {
        /*01	交易代码	    4	2101
          02	监管银行代码	2
          03	城市代码	    6
          04	划拨业务编号	14
          05	划拨密码	    32	MD5
          06	验证流水    	30
          07	验证日期	    10	送系统日期即可
          08	验证网点	    30
          09	验证人员	    30
          10	发起方	        1	1_监管银行*/
        String strRtn=
                StringUtils.rightPad(tia9902101Para.header.TX_CODE, 4, ' ')+"|"+
                StringUtils.rightPad(tia9902101Para.body.BANK_ID,   2, ' ')+"|"+
                StringUtils.rightPad(tia9902101Para.body.CITY_ID,   6, ' ')+"|"+
                StringUtils.rightPad(tia9902101Para.header.BIZ_ID,  14, ' ')+"|"+
                StringUtils.rightPad(tia9902101Para.header.PASSWORD,32, ' ')+"|"+
                StringUtils.rightPad(tia9902101Para.header.REQ_SN,  30, ' ')+"|"+
                StringUtils.rightPad(tia9902101Para.body.TX_DATE,   10, ' ')+"|"+
                StringUtils.rightPad(tia9902101Para.body.BRANCH_ID, 30, ' ')+"|"+
                StringUtils.rightPad(tia9902101Para.header.USER_ID, 30, ' ')+"|"+
                StringUtils.rightPad(tia9902101Para.body.INITIATOR, 1, ' ');
        Integer intStrRtnLength=strRtn.length();
        strRtn= StringUtils.leftPad(intStrRtnLength.toString(),6, '0')+"|"+strRtn;
        return strRtn;
    }
    private TIA9902101 convertStrToBean(String strPara) {
        TIA9902101 tia9902101Para=new TIA9902101();
        /*01	交易代码	    4	2101
          02	监管银行代码	2
          03	城市代码	    6
          04	划拨业务编号	14
          05	划拨密码	    32	MD5
          06	验证流水    	30
          07	验证日期	    10	送系统日期即可
          08	验证网点	    30
          09	验证人员	    30
          10	发起方	        1	1_监管银行*/
        strPara=strPara.replace("|","");
          if(strPara.length()>159) {
              tia9902101Para.header.TX_CODE = strPara.substring(0,4);
              tia9902101Para.body.BANK_ID= strPara.substring(4,2);
              tia9902101Para.body.CITY_ID= strPara.substring(6,6);
              tia9902101Para.header.BIZ_ID= strPara.substring(12,14);
              tia9902101Para.header.BIZ_ID= strPara.substring(26,32);
              tia9902101Para.header.REQ_SN= strPara.substring(58,30);
              tia9902101Para.body.TX_DATE= strPara.substring(88,10);
              tia9902101Para.body.BRANCH_ID= strPara.substring(98,30);
              tia9902101Para.header.USER_ID= strPara.substring(128,30);
              tia9902101Para.body.INITIATOR= strPara.substring(158,1);
          }
        return tia9902101Para;
    }
}
