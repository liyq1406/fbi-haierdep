package org.fbi.dep.transform;

import org.fbi.dep.model.txn.Toa9902101;

/**
 * 泰安房产资金监管系统，划拨验证翻译
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 15-6-30
 * Time: 下午2:12
 * To change this template use File | Settings | File Templates.
 */
public class Toa9902101Transform extends AbstractToaTransform {

    @Override
    public Toa9902101 transform(String tiaStrDatagram, String txCode) {
        Toa9902101 toa9902101 = convertStrToBean(tiaStrDatagram);
        return toa9902101;
    }

    private Toa9902101 convertStrToBean(String strPara) {
        Toa9902101 toa9902101Para=new Toa9902101();
        /*正确返回：
          01	结果	                4   0000表示成功
          02	监管账号                30
          03    监管账户户名            150
          04	划拨金额	            20  以分为单位
          05	收款银行	            90
          06	收款单位账号	        30
          07	收款单位户名	        150
          08	项目名称	            128
          09	开发企业名称	        255
          10    预售资金监管平台流水    16
        */
        /*错误返回：
          01    返回结果                4   0000表示成功
          02    错误原因描述	        60
        */
        String[] sourceStrArray = strPara.split("\\|");
        String strRtnCode=sourceStrArray[0];
        toa9902101Para.header.RETURN_CODE = strRtnCode;
        if("0000".equals(strRtnCode)) {
            toa9902101Para.body.SPVSN_ACC_ID = sourceStrArray[1];
            toa9902101Para.body.SPVSN_ACC_NAME = sourceStrArray[2];
            toa9902101Para.body.TX_AMT = sourceStrArray[3];
            toa9902101Para.body.GERL_BANK = sourceStrArray[4];
            toa9902101Para.body.GERL_ACC_ID = sourceStrArray[5];
            toa9902101Para.body.GERL_ACC_NAME = sourceStrArray[6];
            toa9902101Para.body.PROG_NAME = sourceStrArray[7];
            toa9902101Para.body.COMP_NAME = sourceStrArray[8];
            toa9902101Para.header.REQ_SN = sourceStrArray[9];
        }else{
            toa9902101Para.header.RETURN_MSG = sourceStrArray[1];
        }
        return toa9902101Para;
    }
}
