package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.txn.TIA9902002;

/**
 * 泰安房产中心资金监管系统—交存记账
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 2015-07-24
 * Time: 9:11
 * To change this template use File | Settings | File Templates.
 */
public class Tia9902002Transform extends AbstractTiaTransform {

    @Override
    public String transform(TIA tia) {
        TIA9902002 tia9902002 = (TIA9902002) tia;
        return convertBeanToStr(tia9902002);
    }

    private String convertBeanToStr(TIA9902002 tia9902002Para) {
        /*01	交易代码	    4	2002
          02	监管银行代码	2
          03	城市代码	    6
          04	交存申请编号	14
          05	交存金额	    20	2001交易交存验证返回的实缴金额；2003交易无返此项，由柜员录入。
          06	监管账号	    30	划拨验证的输出项
          07	结算方式	    2	01_ 现金 02_ 转账 03_ 支票
          08	支票号码	    30
          09	银行记账流水	30
          10	记账日期	    10	送系统日期即可
          11	记账网点	    30
          12	记账人员	    30
          13	发起方	        1	1_监管银行*/
        String strRtn=
                StringUtils.rightPad(tia9902002Para.header.TX_CODE, 4, ' ')+"|"+
                StringUtils.rightPad(tia9902002Para.body.BANK_ID,   2, ' ')+"|"+
                StringUtils.rightPad(tia9902002Para.body.CITY_ID,   6, ' ')+"|"+
                StringUtils.rightPad(tia9902002Para.header.BIZ_ID,  14, ' ')+"|"+
                StringUtils.rightPad(tia9902002Para.body.TX_AMT,  20, ' ')+"|"+
                StringUtils.rightPad(tia9902002Para.body.ACC_ID,  30, ' ')+"|"+
                StringUtils.rightPad(tia9902002Para.body.STL_TYPE,  2, ' ')+"|"+
                StringUtils.rightPad(tia9902002Para.body.CHECK_ID,  30, ' ')+"|"+
                StringUtils.rightPad(tia9902002Para.header.REQ_SN,  30, ' ')+"|"+
                StringUtils.rightPad(tia9902002Para.body.TX_DATE,   10, ' ')+"|"+
                StringUtils.rightPad(tia9902002Para.body.BRANCH_ID, 30, ' ')+"|"+
                StringUtils.rightPad(tia9902002Para.header.USER_ID, 30, ' ')+"|"+
                StringUtils.rightPad(tia9902002Para.body.INITIATOR, 1, ' ');
        Integer intStrRtnLength=strRtn.length();
        strRtn= StringUtils.leftPad(intStrRtnLength.toString(),6, '0')+"|"+strRtn;
        return strRtn;
    }
    private TIA9902002 convertStrToBean(String strPara) {
        TIA9902002 tia9902002Para=new TIA9902002();
        /*01	交易代码	    4	2002
          02	监管银行代码	2
          03	城市代码	    6
          04	交存申请编号	14
          05	交存金额	    20	2001交易交存验证返回的实缴金额；2003交易无返此项，由柜员录入。
          06	监管账号	    30	划拨验证的输出项
          07	结算方式	    2	01_ 现金 02_ 转账 03_ 支票
          08	支票号码	    30
          09	银行记账流水	30
          10	记账日期	    10	送系统日期即可
          11	记账网点	    30
          12	记账人员	    30
          13	发起方	        1	1_监管银行*/
        strPara=strPara.replace("|","");
          if(strPara.length()>209) {
              tia9902002Para.header.TX_CODE = strPara.substring(0,4);
              tia9902002Para.body.BANK_ID= strPara.substring(4,2);
              tia9902002Para.body.CITY_ID= strPara.substring(6,6);
              tia9902002Para.header.BIZ_ID= strPara.substring(12,14);
              tia9902002Para.body.TX_AMT= strPara.substring(26,20);
              tia9902002Para.body.ACC_ID= strPara.substring(46,30);
              tia9902002Para.body.STL_TYPE= strPara.substring(76,2);
              tia9902002Para.body.CHECK_ID= strPara.substring(78,30);
              tia9902002Para.header.REQ_SN= strPara.substring(108,30);
              tia9902002Para.body.TX_DATE= strPara.substring(138,10);
              tia9902002Para.body.BRANCH_ID= strPara.substring(148,30);
              tia9902002Para.header.USER_ID= strPara.substring(178,30);
              tia9902002Para.body.INITIATOR= strPara.substring(208,1);
          }
        return tia9902002Para;
    }
}
