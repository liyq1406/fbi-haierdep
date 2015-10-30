package org.fbi.dep.transform.http.sbs;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.com.ToolUtil;
import org.fbi.dep.enums.SBSFormCode;
import org.fbi.dep.enums.TxnStatus;
import org.fbi.dep.model.txn.sbs.ToaXml9009506;
import org.fbi.dep.transform.AbstractToaBytesTransform;
import org.fbi.endpoint.sbs.core.FebResponse;
import org.fbi.endpoint.sbs.domain.SOFForm;
import org.fbi.endpoint.sbs.model.form.re.T399;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * ֪ͨ���-֧ȡ����9009506
 * (��ӦSBS-a13a����)
 */

public class ToaXmlHttp9009506Transform extends AbstractToaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(ToaXmlHttp9009506Transform.class);

    public String transform(byte[] bytes) {
        FebResponse response = new FebResponse();
        response.init(bytes);
        String formCode = ToolUtil.SbsRtnCodeToDepRtnCode("9009506", response.getFormCodes().get(0));
        // bean -> txn bean
        ToaXml9009506 toa = new ToaXml9009506();
        toa.getINFO().setRET_CODE(formCode);
        if (TxnStatus.TXN_SUCCESS.getCode().equalsIgnoreCase(formCode)) {
            toa.getINFO().setRET_MSG(TxnStatus.TXN_SUCCESS.getTitle());
            SOFForm form = response.getSofForms().get(0);
            T399 t399 = (T399) form.getFormBody();
            toa.getBODY().setTELLER(t399.getTELLER());   // ��Ա����
            toa.getBODY().setTXNDAT(t399.getTXNDAT());   // ��������
            toa.getBODY().setVCHSET(t399.getVCHSET());   // ��Ʊ�׺�
            toa.getBODY().setACTTY(t399.getACTTY());     // �˻����
            toa.getBODY().setIPTAC(t399.getIPTAC());     // �ʻ���
            toa.getBODY().setACTNAM(t399.getACTNAM());   // �ʻ���
            toa.getBODY().setCCYNAM(t399.getCCYNAM());   // ������������
            toa.getBODY().setAPCDE1(t399.getAPCDE1());   // ����
            toa.getBODY().setTXNAMT1(t399.getTXNAMT1()); // ȡ����
            toa.getBODY().setOPNIRT(t399.getOPNIRT());   // ����
            toa.getBODY().setININT(t399.getININT());     // ������Ϣ
            toa.getBODY().setAPCDE2(t399.getAPCDE2());   // ����
            toa.getBODY().setTXNAMT2(t399.getTXNAMT2()); // �����Ϣ����
            toa.getBODY().setSAVIRT(t399.getSAVIRT());   // ��������
            toa.getBODY().setOUTINT(t399.getOUTINT());   // ������Ϣ
            toa.getBODY().setAPCDE3(t399.getAPCDE3());   // ����
            toa.getBODY().setTXNAMT3(t399.getTXNAMT3()); // ����
            toa.getBODY().setVALIRT(t399.getVALIRT());   // ����
            toa.getBODY().setVALINT(t399.getVALINT());   // ����
            toa.getBODY().setAPCDE4(t399.getAPCDE4());   // ����
            toa.getBODY().setTXNAMT4(t399.getTXNAMT4()); // ����
            toa.getBODY().setFEERAT(t399.getFEERAT());   // ����
            toa.getBODY().setFEEAMT(t399.getFEEAMT());   // ����
            toa.getBODY().setPIVINT1(t399.getPIVINT1()); // ��Ϣ�ܶ�
            toa.getBODY().setPIVINT2(t399.getPIVINT2()); // ��˰��Ϣ
            toa.getBODY().setTAXRATE(t399.getTAXRATE()); // ˰��
            toa.getBODY().setTAXAMT(t399.getTAXAMT());   // ˰��
            toa.getBODY().setTOTINT(t399.getTOTINT());   // ʵ����Ϣ�ܶ�
            toa.getBODY().setDATHED(t399.getDATHED());   // ��Ϣ��:
            toa.getBODY().setVALDAT(t399.getVALDAT());   // ��Ϣ����
            toa.getBODY().setTOTAMT(t399.getTOTAMT());   // ֧����Ϣ�ܽ��
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
