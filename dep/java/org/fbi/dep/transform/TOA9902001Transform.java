package org.fbi.dep.transform;

import org.fbi.dep.model.txn.Toa9902001;

/**
 * 泰安房产资金监管系统，交存验证翻译
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 15-6-30
 * Time: 下午2:12
 * To change this template use File | Settings | File Templates.
 */
public class Toa9902001Transform extends AbstractToaTransform {

    @Override
    public Toa9902001 transform(String tiaStrDatagram, String txCode) {
        Toa9902001 toa9902001 = convertStrToBean(tiaStrDatagram);
        return toa9902001;
    }

    private Toa9902001 convertStrToBean(String strPara) {
        Toa9902001 toa9902001Para=new Toa9902001();
        /*正确返回：
          01	结果	                4   0000表示成功
          02	帐户类别	            1   0：监管户；
          03	交存金额	            20  以分为单位
          04	监管专户账号            30
          05    监管专户户名            150
          06    预售资金监管平台流水    16
        */
        /*错误返回：
          01    返回结果                4   0000表示成功
          02    错误原因描述	        60
        */
        strPara=strPara.replace("|","");
        String strRtnCode=strPara.substring(6,10);
        toa9902001Para.header.RETURN_CODE = strRtnCode;
        if("0000".equals(strRtnCode)) {
            toa9902001Para.body.ACC_TYPE = strPara.substring(10, 11);
            toa9902001Para.body.TX_AMT = strPara.substring(11, 31);
            toa9902001Para.body.ACC_ID = strPara.substring(31, 61);
            toa9902001Para.body.ACC_NAME = strPara.substring(61, 211);
            toa9902001Para.header.REQ_SN = strPara.substring(211, 361);
        }else{
            toa9902001Para.header.RETURN_MSG = strPara.substring(10, 76);
        }
        return toa9902001Para;
    }
}
