package org.fbi.dep.transform;

import org.fbi.dep.model.txn.Toa9901002;

/**
 * 泰安房产资金监管系统，解除监管翻译
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 15-6-30
 * Time: 下午2:12
 * To change this template use File | Settings | File Templates.
 */
public class Toa9901002Transform extends AbstractToaTransform {

    @Override
    public Toa9901002 transform(String tiaStrDatagram, String txCode) {
        Toa9901002 Toa9901002 = convertStrToBean(tiaStrDatagram);
        return Toa9901002;
    }

    private Toa9901002 convertStrToBean(String strPara) {
        Toa9901002 Toa9901002Para =new Toa9901002();
        /*正确返回：
          01	结果	                4   0000表示成功
          02	预售资金监管平台流水	16
        */
        /*错误返回：
          01    返回结果                4   0000表示成功
          02    错误原因描述	        60
        */
        strPara=strPara.replace("|","");
        String strRtnCode=strPara.substring(6,10);
        Toa9901002Para.header.RETURN_CODE = strRtnCode;
        if("0000".equals(strRtnCode)) {
            Toa9901002Para.header.REQ_SN = strPara.substring(10, 26);
        }else{
            Toa9901002Para.header.RETURN_MSG = strPara.substring(10, 76);
        }
        return Toa9901002Para;
    }
}
