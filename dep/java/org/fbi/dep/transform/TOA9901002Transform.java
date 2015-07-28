package org.fbi.dep.transform;

import org.fbi.dep.model.txn.TOA9901002;

/**
 * 泰安房产资金监管系统，解除监管翻译
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 15-6-30
 * Time: 下午2:12
 * To change this template use File | Settings | File Templates.
 */
public class TOA9901002Transform extends AbstractToaTransform {

    @Override
    public TOA9901002 transform(String tiaStrDatagram, String txCode) {
        TOA9901002 TOA9901002 = convertStrToBean(tiaStrDatagram);
        return TOA9901002;
    }

    private TOA9901002 convertStrToBean(String strPara) {
        TOA9901002 TOA9901002Para=new TOA9901002();
        /*01	结果	                4   0000表示成功
          02	预售资金监管平台流水	16
        */
        strPara=strPara.replace("|","");
        int i=strPara.length();
        TOA9901002Para.header.RETURN_CODE = strPara.substring(6,10);
        TOA9901002Para.header.REQ_SN= strPara.substring(10,26);
        return TOA9901002Para;
    }
}
