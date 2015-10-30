package org.fbi.dep.transform.http.sbs;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.com.ToolUtil;
import org.fbi.dep.enums.SBSFormCode;
import org.fbi.dep.enums.TxnStatus;
import org.fbi.dep.model.txn.sbs.ToaXml9009505;
import org.fbi.dep.transform.AbstractToaBytesTransform;
import org.fbi.endpoint.sbs.core.FebResponse;
import org.fbi.endpoint.sbs.domain.SOFForm;
import org.fbi.endpoint.sbs.model.form.re.T220;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

/**
 * ֪ͨ���-����֪ͨ9009505
 * (��ӦSBS-a121����)
 * �ý������ڳ���������������֪ͨ��Ƭ
 */

public class ToaXmlHttp9009505Transform extends AbstractToaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(ToaXmlHttp9009505Transform.class);

    public String transform(byte[] bytes) {
        FebResponse response = new FebResponse();
        response.init(bytes);
        String formCode = ToolUtil.SbsRtnCodeToDepRtnCode("9009505", response.getFormCodes().get(0));
        // bean -> txn bean
        ToaXml9009505 toa = new ToaXml9009505();
        toa.getINFO().setRET_CODE(formCode);
        if (TxnStatus.TXN_SUCCESS.getCode().equalsIgnoreCase(formCode)) {
            DecimalFormat df = new DecimalFormat("#000000000000000.00");
            toa.getINFO().setRET_MSG(TxnStatus.TXN_SUCCESS.getTitle());
            SOFForm form = response.getSofForms().get(0);
            T220 t220 = (T220) form.getFormBody();
            toa.getBODY().setTELLER(t220.getTELLER()); // ��Ա����
            toa.getBODY().setTXNDAT(t220.getTXNDAT()); // ��������
            toa.getBODY().setACTTY(t220.getACTTY());   // �˻����
            toa.getBODY().setIPTAC(t220.getIPTAC());   // �ʺ�
            toa.getBODY().setADVDAT(t220.getADVDAT()); // ֪ͨ����
            toa.getBODY().setACTNAM(t220.getACTNAM()); // ����
            toa.getBODY().setINTCUR(t220.getINTCUR()); // �ұ�
            toa.getBODY().setTXNAMT(df.format(t220.getTXNAMT())); // ֪ͨ���
            toa.getBODY().setADVNUM(t220.getADVNUM()); // ֪ͨ����
            toa.getBODY().setREMARK(t220.getREMARK()); // ��ע
        } else {
            try {
                toa.getINFO().setRET_MSG(new String(SBSFormCode.valueOfAlias(response.getFormCodes().get(0)).getTitle().getBytes(), "GBK"));
                if (StringUtils.isEmpty(toa.getINFO().getRET_MSG())) {
                    toa.getINFO().setRET_MSG(TxnStatus.TXN_FAILED.getTitle());
                }
            }catch (NullPointerException e) {
                toa.getINFO().setRET_MSG(TxnStatus.TXN_FAILED.getTitle());
            } catch (UnsupportedEncodingException e) {
                logger.error("�ַ�������", e);
            }
        }
        return toa.toString();
    }
}