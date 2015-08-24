package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.enums.SBSFormCode;
import org.fbi.dep.model.base.TOA;
import org.fbi.dep.model.txn.Toa900012701;
import org.fbi.endpoint.sbs.core.FebResponse;
import org.fbi.endpoint.sbs.domain.SOFForm;
import org.fbi.endpoint.sbs.model.form.ac.M000;
import org.fbi.endpoint.sbs.model.form.ac.T106;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created by hanjianlong on 2015-8-11.
 * SBS 900012701 -> 8119 查询多账户余额应答报文
 */

public class Toa900012701Transform {
    private static Logger logger = LoggerFactory.getLogger(Toa900012701Transform.class);

    public TOA transform(byte[] bytes) {
        FebResponse response = new FebResponse();
        response.init(bytes);
        String formCode = response.getFormCodes().get(0);
        // bean -> txn bean
        Toa900012701 toa = new Toa900012701();
        toa.header.RETURN_CODE = formCode;
        if ("T106".equalsIgnoreCase(formCode)) {
            toa.header.RETURN_CODE="0000";
            toa.header.RETURN_MSG = "交易成功";
            SOFForm form = response.getSofForms().get(0);
            T106 t106 = (T106) form.getFormBody();
            toa.body.TOTCNT = t106.getFormBodyHeader().getTOTCNT();
            toa.body.CURCNT = t106.getFormBodyHeader().getCURCNT();
            toa.body.DETAILS = new ArrayList<Toa900012701.BodyDetail>();
            for (T106.Bean bean: t106.getBeanList()) {
                Toa900012701.BodyDetail detail = new Toa900012701.BodyDetail();
                detail.ACTNUM = bean.getACTNUM();
                detail.ACTNAM = bean.getACTNAM();
                detail.BOKBAL = bean.getBOKBAL();
                detail.AVABAL = bean.getAVABAL();
                detail.FRZSTS = bean.getFRZSTS();
                detail.ACTSTS = bean.getACTSTS();
                detail.RECSTS = bean.getRECSTS();
                toa.body.DETAILS.add(detail);
            }
        } else {
            SOFForm form = response.getSofForms().get(0);
            if(form.getFormHeader().getFormCode().startsWith("M")){
                M000 m000 = (M000)form.getFormBody();
                if(m000.getRTNMSG() != null) {
                    toa.header.RETURN_MSG = SBSFormCode.valueOfAlias(formCode).getTitle() + m000.getRTNMSG();
                } else {
                    toa.header.RETURN_MSG = SBSFormCode.valueOfAlias(formCode).getTitle();
                }
            } else {
                toa.header.RETURN_MSG = SBSFormCode.valueOfAlias(formCode).getTitle();
            }

            if (StringUtils.isEmpty(toa.header.RETURN_MSG)) {
                toa.header.RETURN_MSG = "交易失败";
            }
        }
        return toa;
    }
}
