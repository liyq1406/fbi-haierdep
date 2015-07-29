package org.fbi.dep.transform;

import org.fbi.dep.model.txn.Toa9902001;

/**
 * ̩�������ʽ���ϵͳ��������֤����
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 15-6-30
 * Time: ����2:12
 * To change this template use File | Settings | File Templates.
 */
public class Toa9902001Transform extends AbstractToaTransform {

    @Override
    public Toa9902001 transform(String tiaStrDatagram, String txCode) {
        Toa9902001 toa9902001 = convertStrToBean(tiaStrDatagram);
        return toa9902001;
    }

    private Toa9902001 convertStrToBean(String strPara) {
        Toa9902001 toa9902001Para=new Toa9902001();
        /*��ȷ���أ�
          01	���	                4   0000��ʾ�ɹ�
          02	�ʻ����	            1   0����ܻ���
          03	������	            20  �Է�Ϊ��λ
          04	���ר���˺�            30
          05    ���ר������            150
          06    Ԥ���ʽ���ƽ̨��ˮ    16
        */
        /*���󷵻أ�
          01    ���ؽ��                4   0000��ʾ�ɹ�
          02    ����ԭ������	        60
        */
        strPara=strPara.replace("|","");
        String strRtnCode=strPara.substring(6,10);
        toa9902001Para.header.RETURN_CODE = strRtnCode;
        if("0000".equals(strRtnCode)) {
            toa9902001Para.body.ACC_TYPE = strPara.substring(10, 11);
            toa9902001Para.body.TX_AMT = strPara.substring(11, 31);
            toa9902001Para.body.ACC_ID = strPara.substring(31, 61);
            toa9902001Para.body.ACC_NAME = strPara.substring(61, 211);
            toa9902001Para.header.REQ_SN = strPara.substring(211, 361);
        }else{
            toa9902001Para.header.RETURN_MSG = strPara.substring(10, 76);
        }
        return toa9902001Para;
    }
}
