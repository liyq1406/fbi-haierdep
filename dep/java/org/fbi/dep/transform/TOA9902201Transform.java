package org.fbi.dep.transform;

import org.fbi.dep.model.txn.Toa9902201;

/**
 * ̩�������ʽ���ϵͳ��������֤����
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 15-6-30
 * Time: ����2:12
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
        /*��ȷ���أ�
          01	���	                4   0000��ʾ�ɹ�
          02	����˺�                30
          03    ����˻�����            150
          04	�������	            20  �Է�Ϊ��λ
          05	ҵ������	            80
          06	֤������    	        30
          07	֤������                255
          08    Ԥ���ʽ���ƽ̨��ˮ    16
        */
        /*���󷵻أ�
          01    ���ؽ��                4   0000��ʾ�ɹ�
          02    ����ԭ������	        60
        */
        strPara=strPara.replace("|","");
        String strRtnCode=strPara.substring(6,10);
        toa9902201Para.header.RETURN_CODE = strRtnCode;
        if("0000".equals(strRtnCode)) {
            toa9902201Para.body.ACC_ID = strPara.substring(10, 40);
            toa9902201Para.body.ACC_NAME = strPara.substring(40, 190);
            toa9902201Para.body.TX_AMT = strPara.substring(190, 210);
            toa9902201Para.body.OWNER_NAME = strPara.substring(210, 290);
            toa9902201Para.body.CTFIC_TYPE = strPara.substring(290, 320);
            toa9902201Para.body.CTFIC_ID = strPara.substring(320,575);
            toa9902201Para.header.REQ_SN = strPara.substring(575, 591);
        }else{
            toa9902201Para.header.RETURN_MSG = strPara.substring(10, 76);
        }
        return toa9902201Para;
    }
}
