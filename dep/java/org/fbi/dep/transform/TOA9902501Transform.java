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
        strPara=strPara.replace("|","");
        String strRtnCode=strPara.substring(6,10);
        toa9902501Para.header.RETURN_CODE = strRtnCode;
        if("0000".equals(strRtnCode)) {
            toa9902501Para.body.ACT_RSTL = strPara.substring(10, 11);
            toa9902501Para.body.FDC_ACT_SN = strPara.substring(11, 27);
            toa9902501Para.body.FDC_BANK_ACT_SN = strPara.substring(27, 57);
            toa9902501Para.header.REQ_SN = strPara.substring(57, 73);
        }else{
            toa9902501Para.header.RETURN_MSG = strPara.substring(10, 76);
        }
        return toa9902501Para;
    }
}
