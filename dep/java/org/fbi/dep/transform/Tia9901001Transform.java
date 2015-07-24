package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.txn.TIA1001001;
import org.fbi.dep.model.txn.TIA9901001;
import org.fbi.dep.util.DateUtils;
import org.fbi.dep.util.PropertyManager;
import org.fbi.endpoint.unionpay.txn.domain.T100001Tia;
import org.fbi.endpoint.unionpay.txn.domain.T100004Tia;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-2-13
 * Time: 下午9:11
 * To change this template use File | Settings | File Templates.
 */
public class Tia9901001Transform extends AbstractTiaTransform {

    @Override
    public String transform(TIA tia) {
        TIA9901001 tia9901001 = (TIA9901001) tia;
        return convertBeanToStr(tia9901001);
    }

    private String convertBeanToStr(TIA9901001 tia9901001Para) {
        /*01	交易代码	    4	2001
          02	监管银行代码	2
          03	城市代码	    6
          04	监管申请编号    14
          05    帐户类别        1  0：预售监管户
          06    监管专户账号    30
          07    监管专户户名    150
          08	流水号    	    30
          09	日期	        10	送系统日期即可
          10	网点号	        30
          11	柜员号	        30
          12	发起方	        1	1_监管银行*/
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
    private TIA9901001 convertStrToBean(String strPara) {
        TIA9901001 tia9901001Para=new TIA9901001();
        /*01	交易代码	    4	2001
          02	监管银行代码	2
          03	城市代码	    6
          04	监管申请编号    14
          05    帐户类别        1  0：预售监管户
          06    监管专户账号    30
          07    监管专户户名    150
          08	流水号    	    30
          09	日期	        10	送系统日期即可
          10	网点号	        30
          11	柜员号	        30
          12	发起方	        1	1_监管银行*/
        strPara=strPara.replace("|","");
          if(strPara.length()>308) {
              tia9901001Para.header.TX_CODE = strPara.substring(0,4);
              tia9901001Para.body.BANK_ID= strPara.substring(4,2);
              tia9901001Para.body.CITY_ID= strPara.substring(6,6);
              tia9901001Para.header.BIZ_ID= strPara.substring(12,14);
              tia9901001Para.body.ACC_TYPE= strPara.substring(26,1);
              tia9901001Para.body.ACC_ID= strPara.substring(27,30);
              tia9901001Para.body.ACC_NAME= strPara.substring(57,150);
              tia9901001Para.header.REQ_SN= strPara.substring(207,30);
              tia9901001Para.body.TX_DATE= strPara.substring(237,10);
              tia9901001Para.body.BRANCH_ID= strPara.substring(247,30);
              tia9901001Para.header.USER_ID= strPara.substring(277,30);
              tia9901001Para.body.INITIATOR= strPara.substring(307,1);
          }
        return tia9901001Para;
    }
}
