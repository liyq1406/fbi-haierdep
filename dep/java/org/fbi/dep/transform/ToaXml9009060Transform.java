package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.enums.SBSFormCode;
import org.fbi.dep.model.txn.ToaXml9009060;
import org.fbi.dep.model.txn.ToaXml9009201;
import org.fbi.endpoint.sbs.core.FebResponse;
import org.fbi.endpoint.sbs.core.SBSResponse4SingleRecord;
import org.fbi.endpoint.sbs.domain.SOFForm;
import org.fbi.endpoint.sbs.model.form.ac.T925;
import org.fbi.endpoint.sbs.txn.Taa41SOFDataDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * 根据客户证件信息查询账户列表 8855 -> 9009060
 */
public class ToaXml9009060Transform extends AbstractToaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(ToaXml9009060Transform.class);

    public String transform(byte[] bytes) {

        FebResponse response = new FebResponse();
        response.init(bytes);
        String formCode = response.getFormCodes().get(0);
        // bean -> txn bean
        ToaXml9009060 toa = new ToaXml9009060();
        toa.INFO.RET_CODE = formCode;
        SOFForm form = response.getSofForms().get(0);
        if ("T925".equalsIgnoreCase(response.getFormCodes().get(0))) {
            toa.INFO.RET_MSG = "交易成功";
            T925 t925 = (T925) form.getFormBody();
            toa.BODY.CUSNAM = t925.getFormBodyHeader().getCUSNAM();
            toa.BODY.TOTCNT = t925.getFormBodyHeader().getTOTCNT();
            toa.BODY.CURCNT = t925.getFormBodyHeader().getCURCNT();
            toa.BODY.DETAILS = new ArrayList<ToaXml9009060.Body.BodyDetail>();
            for (T925.Bean bean : t925.getBeanList()) {
                ToaXml9009060.Body.BodyDetail detail = new ToaXml9009060.Body.BodyDetail();
                detail.CUSACT = bean.getCUSACT();
                detail.ACTTYP = bean.getACTTYP();
                detail.BOKBAL = bean.getBOKBAL();
                detail.AVABAL = bean.getAVABAL();
                detail.OPNDAT = bean.getOPNDAT();
                detail.VALDAT = bean.getVALDAT();
                detail.DPTPRD = bean.getDPTPRD();
                detail.EXPDAT = bean.getEXPDAT();
                detail.INTRAT = bean.getINTRAT();
                detail.ACTSTS = bean.getACTSTS();
                toa.BODY.DETAILS.add(detail);
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
