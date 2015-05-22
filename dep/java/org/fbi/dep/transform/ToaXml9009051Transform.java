package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.enums.SBSFormCode;
import org.fbi.dep.model.txn.ToaXml9009051;
import org.fbi.endpoint.sbs.core.FebResponse;
import org.fbi.endpoint.sbs.domain.SOFForm;
import org.fbi.endpoint.sbs.model.form.ac.T926;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created by XIANGYANG on 2015-5-21.
 * SBS 9009051 -> 8854 账户当日交易明细查询
 */

public class ToaXml9009051Transform extends AbstractToaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(ToaXml9009051Transform.class);

    public String transform(byte[] bytes) {
        FebResponse response = new FebResponse();
        response.init(bytes);
        String formCode = response.getFormCodes().get(0);
        // bean -> txn bean
        ToaXml9009051 toa = new ToaXml9009051();
        toa.INFO.RET_CODE = formCode;
        if ("T926".equalsIgnoreCase(formCode)) {
            toa.INFO.RET_MSG = "交易成功";
            SOFForm form = response.getSofForms().get(0);
            T926 t926 = (T926) form.getFormBody();
            toa.BODY.TOTCNT = t926.getFormBodyHeader().getTOTCNT();
            toa.BODY.CURCNT = t926.getFormBodyHeader().getCURCNT();
            toa.BODY.DBTCNT = t926.getFormBodyHeader().getDBTCNT();
            toa.BODY.DBTAMT = t926.getFormBodyHeader().getDBTAMT();
            toa.BODY.CRTCNT = t926.getFormBodyHeader().getCRTCNT();
            toa.BODY.CRTAMT = t926.getFormBodyHeader().getCRTAMT();
            toa.BODY.DETAILS = new ArrayList<ToaXml9009051.Body.BodyDetail>();
            for (T926.Bean bean : t926.getBeanList()) {
                ToaXml9009051.Body.BodyDetail detail = new ToaXml9009051.Body.BodyDetail();
                detail.CUSIDT = bean.getCUSIDT();
                detail.APCODE = bean.getAPCODE();
                detail.CURCDE = bean.getCURCDE();
                detail.STMDAT = bean.getSTMDAT();
                detail.TLRNUM = bean.getTLRNUM();
                detail.VCHSET = bean.getVCHSET();
                detail.SETSEQ = bean.getSETSEQ();
                detail.TXNAMT = bean.getTXNAMT();
                detail.LASBAL = bean.getLASBAL();
                detail.FURINF = bean.getFURINF();
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
