package org.fbi.dep.transform;

import org.fbi.dep.model.txn.Toa9902002;

/**
 * 泰安房产资金监管系统，交存记账翻译
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 15-6-30
 * Time: 下午2:12
 * To change this template use File | Settings | File Templates.
 */
public class Toa9902002Transform extends AbstractToaTransform {

    @Override
    public Toa9902002 transform(String tiaStrDatagram, String txCode) {
        Toa9902002 toa9902002 = convertStrToBean(tiaStrDatagram);
        return toa9902002;
    }

    private Toa9902002 convertStrToBean(String strPara) {
        Toa9902002 toa9902002Para=new Toa9902002();
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
        toa9902002Para.header.RETURN_CODE = strRtnCode;
        if("0000".equals(strRtnCode)) {
            toa9902002Para.header.REQ_SN = sourceStrArray[1];
        }else{
            toa9902002Para.header.RETURN_MSG = sourceStrArray[1];
        }
        return toa9902002Para;
    }
}
