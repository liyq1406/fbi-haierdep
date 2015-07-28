package org.fbi.dep.transform;

import org.fbi.dep.model.base.TOA;
import org.fbi.dep.model.txn.TOA9901001;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-2-14
 * Time: ����11:59
 * To change this template use File | Settings | File Templates.
 */
public class TOA9901001Transform extends AbstractToaTransform {

    @Override
    public TOA9901001 transform(String tiaStrDatagram, String txCode) {
        TOA9901001 toa9901001 = convertStrToBean(tiaStrDatagram);
        return toa9901001;
    }

    private TOA9901001 convertStrToBean(String strPara) {
        TOA9901001 toa9901001Para=new TOA9901001();
        /*01	���	                4   0000��ʾ�ɹ�
          02	Ԥ���ʽ���ƽ̨��ˮ	16
          03	Ԥ��������	            255
          04	Ԥ����Ŀ��ַ            128
          05    Ԥ����Ŀ����            128
        */
        strPara=strPara.replace("|","");
        int i=strPara.length();
        toa9901001Para.header.RETURN_CODE = strPara.substring(6,10);
        toa9901001Para.header.REQ_SN= strPara.substring(10,26);
        toa9901001Para.body.PRE_SALE_PER_NAME= strPara.substring(26,281);
        toa9901001Para.body.PRE_SALE_PRO_ADDR= strPara.substring(281,409);
        toa9901001Para.body.PRE_SALE_PRO_NAME= strPara.substring(409,537);
        return toa9901001Para;
    }
}
