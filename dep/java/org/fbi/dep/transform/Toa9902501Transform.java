package org.fbi.dep.transform;

import org.fbi.dep.model.txn.Toa9902501;

/**
 * ̩�������ʽ���ϵͳ�����˽����ѯ����
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 15-6-30
 * Time: ����2:12
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
        /*��ȷ���أ�
          01	���	                4   0000��ʾ�ɹ�
          02	���˽��	            1   0_�ɹ� 1_ʧ��
          03	Ԥ���ʽ���ƽ̨������ˮ16  ҵ�������ˮ
          04	������м�����ˮ	    30  ҵ�������ˮ
          05	Ԥ���ʽ���ƽ̨��ˮ	16
        */
        /*���󷵻أ�
          01    ���ؽ��                4   0000��ʾ�ɹ�
          02    ����ԭ������	        60
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
