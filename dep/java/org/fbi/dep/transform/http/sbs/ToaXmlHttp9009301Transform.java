package org.fbi.dep.transform.http.sbs;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.com.ToolUtil;
import org.fbi.dep.enums.SBSFormCode;
import org.fbi.dep.enums.TxnStatus;
import org.fbi.dep.model.txn.ToaXml9009301;
import org.fbi.dep.transform.AbstractToaBytesTransform;
import org.fbi.endpoint.sbs.core.FebResponse;
import org.fbi.endpoint.sbs.domain.SOFForm;
import org.fbi.endpoint.sbs.model.form.ac.T001;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 建立个人客户信息 8011 -> 9009301
 */
public class ToaXmlHttp9009301Transform extends AbstractToaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(ToaXmlHttp9009301Transform.class);

    public String transform(byte[] bytes) {
        FebResponse response = new FebResponse();
        response.init(bytes);
        String formCode = ToolUtil.SbsRtnCodeToDepRtnCode("9009301", response.getFormCodes().get(0));
        // bean -> txn bean
        ToaXml9009301 toa = new ToaXml9009301();
        toa.INFO.RET_CODE = formCode;
        SOFForm form = response.getSofForms().get(0);
        if (TxnStatus.TXN_SUCCESS.getCode().equalsIgnoreCase(formCode)) {
            toa.INFO.RET_MSG = TxnStatus.TXN_SUCCESS.getTitle();
            T001 t = (T001) form.getFormBody();
            toa.BODY.ORGIDT = t.getORGIDT();
            toa.BODY.DEPNUM = t.getDEPNUM();
            toa.BODY.CUSIDT = t.getCUSIDT();
            toa.BODY.CUSNAM = t.getCUSNAM();
            toa.BODY.OPNDAT = t.getOPNDAT();
            toa.BODY.CLSDAT = t.getCLSDAT();
            toa.BODY.AMDTLR = t.getAMDTLR();
        } else {
            toa.INFO.RET_MSG = SBSFormCode.valueOfAlias(formCode).getTitle();
            if (StringUtils.isEmpty(toa.INFO.RET_MSG)) {
                toa.INFO.RET_MSG = TxnStatus.TXN_FAILED.getTitle();
            }
        }
        return toa.toString();
    }
}
