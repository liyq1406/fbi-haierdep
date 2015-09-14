package org.fbi.dep.transform;

import org.fbi.dep.model.txn.Toa9902501;

/**
 * 泰安房产资金监管系统，记账结果查询翻译
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 15-6-30
 * Time: 下午2:12
 * To change this template use File | Settings | File Templates.
 */
public class Toa9902501Transform extends AbstractToaTransform {

    @Override
    public Toa9902501 transform(String tiaStrDatagram, String txCode) {
        Toa9902501 toa9902501 = convertStrToBean(tiaStrDatagram);
        return toa9902501;
    }

    private Toa9902501 convertStrToBean(String strPara) {
        Toa9902501 toa9902501Para=new Toa9902501();
        /*正确返回：
          01	结果	                4   0000表示成功
          02	记账结果	            1   0_成功 1_失败
          03	预售资金监管平台记账流水16  业务记账流水
          04	监管银行记账流水	    30  业务记账流水
          05	预售资金监管平台流水	16
        */
        /*错误返回：
          01    返回结果                4   0000表示成功
          02    错误原因描述	        60
        */
        String[] sourceStrArray = strPara.split("\\|");
        String strRtnCode=sourceStrArray[0];
        toa9902501Para.header.RETURN_CODE = strRtnCode;
        if("0000".equals(strRtnCode)) {
            toa9902501Para.body.SPVSN_ACT_RSTL = sourceStrArray[1];
            toa9902501Para.body.FDC_ACT_SN = sourceStrArray[2];
            toa9902501Para.body.FDC_BANK_ACT_SN = sourceStrArray[3];
            toa9902501Para.header.REQ_SN = sourceStrArray[4];
        }else{
            toa9902501Para.header.RETURN_MSG = sourceStrArray[1];
        }
        return toa9902501Para;
    }
}
