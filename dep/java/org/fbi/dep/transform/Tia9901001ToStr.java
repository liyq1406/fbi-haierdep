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
public class Tia9901001ToStr extends AbstractTiaTransform {

    @Override
    public String transform(TIA tia) {
        TIA9901001 tia8001001 = (TIA9901001) tia;
        return convertTo9901001Str(tia8001001);
    }

    private String convertTo9901001Str(TIA9901001 tia9901001Para) {
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
                tia9901001Para.header.TX_CODE+"|"+
                tia9901001Para.body.BANK_ID+"|"+
                tia9901001Para.body.CITY_ID+"|"+
                tia9901001Para.header.BIZ_ID+"|"+
                tia9901001Para.body.ACC_TYPE+"|"+
                tia9901001Para.body.ACC_ID+"|"+
                tia9901001Para.body.ACC_NAME+"|"+
                tia9901001Para.header.REQ_SN+"|"+
                tia9901001Para.body.TX_DATE+"|"+
                tia9901001Para.body.BRANCH_ID+"|"+
                tia9901001Para.header.USER_ID+"|"+
                tia9901001Para.body.INITIATOR;
        return strRtn;
    }
}
