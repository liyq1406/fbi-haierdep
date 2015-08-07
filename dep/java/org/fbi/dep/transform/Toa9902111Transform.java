package org.fbi.dep.transform;

import org.fbi.dep.model.txn.Toa9902111;

/**
 * 泰安房产资金监管系统，划拨冲正翻译
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 15-6-30
 * Time: 下午2:12
 * To change this template use File | Settings | File Templates.
 */
public class Toa9902111Transform extends AbstractToaTransform {

    @Override
    public Toa9902111 transform(String tiaStrDatagram, String txCode) {
        Toa9902111 toa9902111 = convertStrToBean(tiaStrDatagram);
        return toa9902111;
    }

    private Toa9902111 convertStrToBean(String strPara) {
        Toa9902111 toa9902111Para=new Toa9902111();
        /*正确返回：
          01    结果	                4   0000表示成功
          02    预售资金监管平台流水	16
        */
        /*错误返回：
          01    返回结果                4   0000表示成功
          02    错误原因描述	        60
        */
        strPara=strPara.replace("|","");
        String strRtnCode=strPara.substring(6,10);
        toa9902111Para.header.RETURN_CODE = strRtnCode;
        if("0000".equals(strRtnCode)) {
            toa9902111Para.header.REQ_SN = strPara.substring(10, 26);
        }else{
            toa9902111Para.header.RETURN_MSG = strPara.substring(10, 76);
        }
        return toa9902111Para;
    }
}
