package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.enums.SBSFormCode;
import org.fbi.dep.model.txn.ToaXml9009060;
import org.fbi.dep.model.txn.ToaXml9009061;
import org.fbi.endpoint.sbs.core.FebResponse;
import org.fbi.endpoint.sbs.domain.SOFForm;
import org.fbi.endpoint.sbs.model.form.ac.T925;
import org.fbi.endpoint.sbs.model.form.ac.T928;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * 根据客户证件信息查询账户列表 8856 -> 9009061
 */
public class ToaXml9009061Transform extends AbstractToaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(ToaXml9009061Transform.class);

    public String transform(byte[] bytes) {

        FebResponse response = new FebResponse();
        response.init(bytes);
        String formCode = response.getFormCodes().get(0);
        // bean -> txn bean
        ToaXml9009061 toa = new ToaXml9009061();
        toa.INFO.RET_CODE = formCode;
        SOFForm form = response.getSofForms().get(0);
        if ("T928".equalsIgnoreCase(response.getFormCodes().get(0))) {
            toa.INFO.RET_MSG = "交易成功";
            T928 t928 = (T928) form.getFormBody();
            toa.BODY.TOTCNT = t928.getFormBodyHeader().getTOTCNT();
            toa.BODY.CURCNT = t928.getFormBodyHeader().getCURCNT();
            toa.BODY.TRANS_DETAILS = new ArrayList<ToaXml9009061.Body.BodyDetail>();
            for (T928.Bean bean : t928.getBeanList()) {
                ToaXml9009061.Body.BodyDetail detail = new ToaXml9009061.Body.BodyDetail();
                detail.CUSACT = bean.getCUSACT();
                detail.ACTTYP = bean.getACTTYP();
                detail.CUSIDT = bean.getCUSIDT();
                detail.CUSNAM = bean.getCUSNAM();
                detail.APCODE = bean.getAPCODE();
                toa.BODY.TRANS_DETAILS.add(detail);
            }
        } else {
            toa.INFO.RET_MSG = SBSFormCode.valueOfAlias(formCode).getTitle();
            if (StringUtils.isEmpty(toa.INFO.RET_MSG)) {
                toa.INFO.RET_MSG = "交易失败";
            }
        }
        return toa.toString();
    }
}
