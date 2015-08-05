package org.fbi.dep.transform;

import org.fbi.dep.model.txn.Toa9902101;

/**
 * ̩�������ʽ���ϵͳ��������֤����
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 15-6-30
 * Time: ����2:12
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
        /*��ȷ���أ�
          01	���	                4   0000��ʾ�ɹ�
          02	����˺�                30
          03    ����˻�����            150
          04	�������	            20  �Է�Ϊ��λ
          05	�տ�����	            90
          06	�տλ�˺�	        30
          07	�տλ����	        150
          08	��Ŀ����	            128
          09	������ҵ����	        255
          10    Ԥ���ʽ���ƽ̨��ˮ    16
        */
        /*���󷵻أ�
          01    ���ؽ��                4   0000��ʾ�ɹ�
          02    ����ԭ������	        60
        */
        strPara=strPara.replace("|","");
        String strRtnCode=strPara.substring(6,10);
        toa9902101Para.header.RETURN_CODE = strRtnCode;
        if("0000".equals(strRtnCode)) {
            toa9902101Para.body.ACC_ID = strPara.substring(10, 40);
            toa9902101Para.body.ACC_NAME = strPara.substring(40, 190);
            toa9902101Para.body.TX_AMT = strPara.substring(190, 210);
            toa9902101Para.body.RECV_BANK = strPara.substring(210, 300);
            toa9902101Para.body.RECV_ACC_ID = strPara.substring(300, 330);
            toa9902101Para.body.RECV_ACC_NAME = strPara.substring(330,480);
            toa9902101Para.body.PROG_NAME = strPara.substring(480, 608);
            toa9902101Para.body.COMP_NAME = strPara.substring(608, 863);
            toa9902101Para.header.REQ_SN = strPara.substring(863, 879);
        }else{
            toa9902101Para.header.RETURN_MSG = strPara.substring(10, 76);
        }
        return toa9902101Para;
    }
}
