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
 * 通知存款-支取结清9009506
 * (对应SBS-a13a交易)
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
            toa.getBODY().setTELLER(t399.getTELLER());   // 柜员代码
            toa.getBODY().setTXNDAT(t399.getTXNDAT());   // 交易日期
            toa.getBODY().setVCHSET(t399.getVCHSET());   // 传票套号
            toa.getBODY().setACTTY(t399.getACTTY());     // 账户类别
            toa.getBODY().setIPTAC(t399.getIPTAC());     // 帐户号
            toa.getBODY().setACTNAM(t399.getACTNAM());   // 帐户名
            toa.getBODY().setCCYNAM(t399.getCCYNAM());   // 货币中文名称
            toa.getBODY().setAPCDE1(t399.getAPCDE1());   // 期内
            toa.getBODY().setTXNAMT1(t399.getTXNAMT1()); // 取款金额
            toa.getBODY().setOPNIRT(t399.getOPNIRT());   // 利率
            toa.getBODY().setININT(t399.getININT());     // 期内利息
            toa.getBODY().setAPCDE2(t399.getAPCDE2());   // 期外
            toa.getBODY().setTXNAMT2(t399.getTXNAMT2()); // 期外计息本金
            toa.getBODY().setSAVIRT(t399.getSAVIRT());   // 期外利率
            toa.getBODY().setOUTINT(t399.getOUTINT());   // 期外利息
            toa.getBODY().setAPCDE3(t399.getAPCDE3());   // 备用
            toa.getBODY().setTXNAMT3(t399.getTXNAMT3()); // 备用
            toa.getBODY().setVALIRT(t399.getVALIRT());   // 备用
            toa.getBODY().setVALINT(t399.getVALINT());   // 备用
            toa.getBODY().setAPCDE4(t399.getAPCDE4());   // 备用
            toa.getBODY().setTXNAMT4(t399.getTXNAMT4()); // 备用
            toa.getBODY().setFEERAT(t399.getFEERAT());   // 备用
            toa.getBODY().setFEEAMT(t399.getFEEAMT());   // 备用
            toa.getBODY().setPIVINT1(t399.getPIVINT1()); // 利息总额
            toa.getBODY().setPIVINT2(t399.getPIVINT2()); // 计税利息
            toa.getBODY().setTAXRATE(t399.getTAXRATE()); // 税率
            toa.getBODY().setTAXAMT(t399.getTAXAMT());   // 税额
            toa.getBODY().setTOTINT(t399.getTOTINT());   // 实付利息总额
            toa.getBODY().setDATHED(t399.getDATHED());   // 起息日:
            toa.getBODY().setVALDAT(t399.getVALDAT());   // 起息日期
            toa.getBODY().setTOTAMT(t399.getTOTAMT());   // 支付本息总金额
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
