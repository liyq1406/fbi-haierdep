package org.fbi.dep.transform;

import org.fbi.dep.model.txn.Toa9902202;

/**
 * ̩�������ʽ���ϵͳ���������˷���
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 15-6-30
 * Time: ����2:12
 * To change this template use File | Settings | File Templates.
 */
public class Toa9902202Transform extends AbstractToaTransform {

    @Override
    public Toa9902202 transform(String tiaStrDatagram, String txCode) {
        Toa9902202 toa9902202 = convertStrToBean(tiaStrDatagram);
        return toa9902202;
    }

    private Toa9902202 convertStrToBean(String strPara) {
        Toa9902202 toa9902202Para=new Toa9902202();
        /*��ȷ���أ�
          01    ���	                4   0000��ʾ�ɹ�
          02    Ԥ���ʽ���ƽ̨��ˮ	16
        */
        /*���󷵻أ�
          01    ���ؽ��                4   0000��ʾ�ɹ�
          02    ����ԭ������	        60
        */
        strPara=strPara.replace("|","");
        String strRtnCode=strPara.substring(6,10);
        toa9902202Para.header.RETURN_CODE = strRtnCode;
        if("0000".equals(strRtnCode)) {
            toa9902202Para.header.REQ_SN = strPara.substring(10, 26);
        }else{
            toa9902202Para.header.RETURN_MSG = strPara.substring(10, 76);
        }
        return toa9902202Para;
    }
}