package org.fbi.dep.transform.http.sbs;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.com.ToolUtil;
import org.fbi.dep.enums.SBSFormCode;
import org.fbi.dep.enums.TxnStatus;
import org.fbi.dep.model.txn.sbs.ToaXml9009507;
import org.fbi.dep.transform.AbstractToaBytesTransform;
import org.fbi.endpoint.sbs.core.FebResponse;
import org.fbi.endpoint.sbs.domain.SOFForm;
import org.fbi.endpoint.sbs.model.form.re.T555;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * 根据证件查询-通知存款提款通知9009507
 * (对应SBS-a113交易)
 * 本交易用于七天通知存款提取款项前的提款通知，客户办理通知时应填写通知单
 */

public class ToaXmlHttp9009507Transform extends AbstractToaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(ToaXmlHttp9009507Transform.class);

    public String transform(byte[] bytes) {
        FebResponse response = new FebResponse();
        response.init(bytes);
        String formCode = ToolUtil.SbsRtnCodeToDepRtnCode("9009507", response.getFormCodes().get(0));
        // bean -> txn bean
        ToaXml9009507 toa = new ToaXml9009507();
        toa.getINFO().setRET_CODE(formCode);
        if (TxnStatus.TXN_SUCCESS.getCode().equalsIgnoreCase(formCode)) {
            toa.getINFO().setRET_MSG(TxnStatus.TXN_SUCCESS.getTitle());
            SOFForm form = response.getSofForms().get(0);
            T555 t555 = (T555) form.getFormBody();
            toa.getBODY().setTOTCNT(t555.getFormBodyHeader().getTOTCNT());
            toa.getBODY().setCURCNT(t555.getFormBodyHeader().getCURCNT());
            toa.getBODY().setDETAILS(new ArrayList<ToaXml9009507.Body.BodyDetail>());
            for (T555.Bean bean : t555.getBeanList()) {
                ToaXml9009507.Body.BodyDetail detail = new ToaXml9009507.Body.BodyDetail();
                detail.setBOKNUM(bean.getBOKNUM());
                detail.setACTNUM(bean.getACTNUM());
                detail.setADVNAM(bean.getADVNAM());
                detail.setADVDAT(bean.getADVDAT().replace("-", ""));
                detail.setADVAMT(bean.getADVAMT());
                detail.setDCDDAT(bean.getDCDDAT().replace("-", ""));
                detail.setADVFLG(bean.getADVFLG());
                detail.setCNCDAT(bean.getCNCDAT().replace("-", ""));
                detail.setACTNAM(bean.getACTNAM());
                toa.getBODY().getDETAILS().add(detail);
            }
        } else {
            try {
                toa.getINFO().setRET_MSG(new String(SBSFormCode.valueOfAlias(response.getFormCodes().get(0)).getTitle().getBytes(), "GBK"));
                if (StringUtils.isEmpty(toa.getINFO().getRET_MSG())) {
                    toa.getINFO().setRET_MSG(TxnStatus.TXN_FAILED.getTitle());
                }
            }catch (NullPointerException e) {
                toa.getINFO().setRET_MSG(TxnStatus.TXN_FAILED.getTitle());
            } catch (UnsupportedEncodingException e) {
                logger.error("字符集错误", e);
            }
        }
        return toa.toString();
    }
}
