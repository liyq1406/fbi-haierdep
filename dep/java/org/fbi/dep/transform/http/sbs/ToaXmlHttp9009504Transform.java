package org.fbi.dep.transform.http.sbs;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.com.ToolUtil;
import org.fbi.dep.enums.SBSFormCode;
import org.fbi.dep.enums.TxnStatus;
import org.fbi.dep.model.txn.sbs.ToaXml9009504;
import org.fbi.dep.transform.AbstractToaBytesTransform;
import org.fbi.endpoint.sbs.core.FebResponse;
import org.fbi.endpoint.sbs.domain.SOFForm;
import org.fbi.endpoint.sbs.model.form.re.T220;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

/**
 * 通知存款-提款通知9009504
 * (对应SBS-a111交易)
 * 本交易用于七天通知存款提取款项前的提款通知，客户办理通知时应填写通知单
 */

public class ToaXmlHttp9009504Transform extends AbstractToaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(ToaXmlHttp9009504Transform.class);

    public String transform(byte[] bytes) {
        FebResponse response = new FebResponse();
        response.init(bytes);
        String formCode = ToolUtil.SbsRtnCodeToDepRtnCode("9009504", response.getFormCodes().get(0));
        // bean -> txn bean
        ToaXml9009504 toa = new ToaXml9009504();
        toa.getINFO().setRET_CODE(formCode);
        if (TxnStatus.TXN_SUCCESS.getCode().equalsIgnoreCase(formCode)) {
            DecimalFormat df = new DecimalFormat("#000000000000000.00");
            toa.getINFO().setRET_MSG(TxnStatus.TXN_SUCCESS.getTitle());
            SOFForm form = response.getSofForms().get(0);
            T220 t220 = (T220) form.getFormBody();
            toa.getBODY().setTELLER(t220.getTELLER()); // 柜员代码
            toa.getBODY().setTXNDAT(t220.getTXNDAT()); // 交易日期
            toa.getBODY().setACTTY(t220.getACTTY());   // 账户类别
            toa.getBODY().setIPTAC(t220.getIPTAC());   // 帐号
            toa.getBODY().setADVDAT(t220.getADVDAT()); // 通知日期
            toa.getBODY().setACTNAM(t220.getACTNAM()); // 户名
            toa.getBODY().setINTCUR(t220.getINTCUR()); // 币别
            toa.getBODY().setTXNAMT(df.format(t220.getTXNAMT())); // 通知金额
            toa.getBODY().setADVNUM(t220.getADVNUM()); // 通知单号
            toa.getBODY().setREMARK(t220.getREMARK()); // 备注
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
