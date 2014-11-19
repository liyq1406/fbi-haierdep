package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.enums.SBSFormCode;
import org.fbi.dep.model.txn.ToaXml9009401;
import org.fbi.endpoint.sbs.core.FebResponse;
import org.fbi.endpoint.sbs.domain.SOFForm;
import org.fbi.endpoint.sbs.model.form.ac.T101;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对应SBS-8124交易 8124 -> 9009401
 */
public class ToaXml9009401Transform extends AbstractToaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(ToaXml9009401Transform.class);

    public String transform(byte[] bytes) {

        FebResponse response = new FebResponse();
        response.init(bytes);
        String formCode = response.getFormCodes().get(0);
        // bean -> txn bean
        ToaXml9009401 toa = new ToaXml9009401();
        toa.INFO.RET_CODE = formCode;
        SOFForm form = response.getSofForms().get(0);
        if ("T101".equalsIgnoreCase(formCode)) {
            toa.INFO.RET_MSG = "交易成功";
            T101 t = (T101) form.getFormBody();
            toa.BODY.ACTNUM = t.getACTNUM();
            toa.BODY.CUSNAM = t.getCUSNAM();
            toa.BODY.OPNDAT = t.getOPNDAT();
            toa.BODY.AMDTLR = t.getAMDTLR();
        } else {
            SBSFormCode sbsFormCode = SBSFormCode.valueOfAlias(formCode);
            if (form == null || StringUtils.isEmpty(toa.INFO.RET_MSG)) {
                toa.INFO.RET_MSG = "交易失败";
            } else {
                toa.INFO.RET_MSG = sbsFormCode.getTitle();
            }
        }
        return toa.toString();
    }
}
