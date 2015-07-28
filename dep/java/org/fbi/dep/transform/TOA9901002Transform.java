package org.fbi.dep.transform;

import org.fbi.dep.model.txn.TOA9901002;

/**
 * ̩�������ʽ���ϵͳ�������ܷ���
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 15-6-30
 * Time: ����2:12
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
        /*01	���	                4   0000��ʾ�ɹ�
          02	Ԥ���ʽ���ƽ̨��ˮ	16
        */
        strPara=strPara.replace("|","");
        int i=strPara.length();
        TOA9901002Para.header.RETURN_CODE = strPara.substring(6,10);
        TOA9901002Para.header.REQ_SN= strPara.substring(10,26);
        return TOA9901002Para;
    }
}
