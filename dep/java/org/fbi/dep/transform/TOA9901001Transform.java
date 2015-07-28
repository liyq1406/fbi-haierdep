package org.fbi.dep.transform;

import org.fbi.dep.model.base.TOA;
import org.fbi.dep.model.txn.TOA9901001;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-2-14
 * Time: 上午11:59
 * To change this template use File | Settings | File Templates.
 */
public class TOA9901001Transform extends AbstractToaTransform {

    @Override
    public TOA9901001 transform(String tiaStrDatagram, String txCode) {
        TOA9901001 toa9901001 = convertStrToBean(tiaStrDatagram);
        return toa9901001;
    }

    private TOA9901001 convertStrToBean(String strPara) {
        TOA9901001 toa9901001Para=new TOA9901001();
        /*01	结果	                4   0000表示成功
          02	预售资金监管平台流水	16
          03	预售人名称	            255
          04	预售项目地址            128
          05    预售项目名称            128
        */
        strPara=strPara.replace("|","");
        int i=strPara.length();
        toa9901001Para.header.RETURN_CODE = strPara.substring(6,10);
        toa9901001Para.header.REQ_SN= strPara.substring(10,26);
        toa9901001Para.body.PRE_SALE_PER_NAME= strPara.substring(26,281);
        toa9901001Para.body.PRE_SALE_PRO_ADDR= strPara.substring(281,409);
        toa9901001Para.body.PRE_SALE_PRO_NAME= strPara.substring(409,537);
        return toa9901001Para;
    }
}
