package org.fbi.dep.transform;

import org.fbi.dep.model.txn.Toa9902201;

/**
 * 泰安房产资金监管系统，返还验证翻译
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 15-6-30
 * Time: 下午2:12
 * To change this template use File | Settings | File Templates.
 */
public class Toa9902201Transform extends AbstractToaTransform {

    @Override
    public Toa9902201 transform(String tiaStrDatagram, String txCode) {
        Toa9902201 toa9902201 = convertStrToBean(tiaStrDatagram);
        return toa9902201;
    }

    private Toa9902201 convertStrToBean(String strPara) {
        Toa9902201 toa9902201Para=new Toa9902201();
        /*正确返回：
          01	结果	                4   0000表示成功
          02	监管账号                30
          03    监管账户户名            150
          04	返还金额	            20  以分为单位
          05	业主姓名	            80
          06	证件类型    	        30
          07	证件号码                255
          08    预售资金监管平台流水    16
        */
        /*错误返回：
          01    返回结果                4   0000表示成功
          02    错误原因描述	        60
        */
        String[] sourceStrArray = strPara.split("\\|");
        String strRtnCode=sourceStrArray[0].substring(6,10);
        toa9902201Para.header.RETURN_CODE = strRtnCode;
        if("0000".equals(strRtnCode)) {
            toa9902201Para.body.ACC_ID = sourceStrArray[1];
            toa9902201Para.body.ACC_NAME = sourceStrArray[2];
            toa9902201Para.body.TX_AMT = sourceStrArray[3];
            toa9902201Para.body.OWNER_NAME = sourceStrArray[4];
            toa9902201Para.body.CTFIC_TYPE = sourceStrArray[5];
            toa9902201Para.body.CTFIC_ID = sourceStrArray[6];
            toa9902201Para.header.REQ_SN = sourceStrArray[7];
        }else{
            toa9902201Para.header.RETURN_MSG = sourceStrArray[1];
        }
        return toa9902201Para;
    }
}
