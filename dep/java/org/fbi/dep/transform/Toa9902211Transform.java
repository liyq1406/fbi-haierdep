package org.fbi.dep.transform;

import org.fbi.dep.model.txn.Toa9902211;

/**
 * 泰安房产资金监管系统，返还冲正翻译
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 15-6-30
 * Time: 下午2:12
 * To change this template use File | Settings | File Templates.
 */
public class Toa9902211Transform extends AbstractToaTransform {

    @Override
    public Toa9902211 transform(String tiaStrDatagram, String txCode) {
        Toa9902211 toa9902211 = convertStrToBean(tiaStrDatagram);
        return toa9902211;
    }

    private Toa9902211 convertStrToBean(String strPara) {
        Toa9902211 toa9902211Para=new Toa9902211();
        /*正确返回：
          01    结果	                4   0000表示成功
          02    预售资金监管平台流水	16
        */
        /*错误返回：
          01    返回结果                4   0000表示成功
          02    错误原因描述	        60
        */
        String[] sourceStrArray = strPara.split("\\|");
        String strRtnCode=sourceStrArray[0].substring(6,10);
        toa9902211Para.header.RETURN_CODE = strRtnCode;
        if("0000".equals(strRtnCode)) {
            toa9902211Para.header.REQ_SN = sourceStrArray[1];
        }else{
            toa9902211Para.header.RETURN_MSG = sourceStrArray[1];
        }
        return toa9902211Para;
    }
}
