package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.enums.SBSFormCode;
import org.fbi.dep.model.base.TOA;
import org.fbi.dep.model.txn.Toa900012602;
import org.fbi.dep.model.txn.Toa900012701;
import org.fbi.endpoint.sbs.core.FebResponse;
import org.fbi.endpoint.sbs.domain.SOFForm;
import org.fbi.endpoint.sbs.model.form.ac.T929;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        if ("T846".equalsIgnoreCase(formCode)) {
            toa.header.RETURN_CODE="0000";
            toa.header.RETURN_MSG = "交易成功";
            SOFForm form = response.getSofForms().get(0);
            T929 t929 = (T929) form.getFormBody();
            toa.body.TOTCNT = t929.getFormBodyHeader().getTOTCNT();
            toa.body.CURCNT = t929.getFormBodyHeader().getCURCNT();
            for (T929.Bean bean : t929.getBeanList()) {
                Toa900012701.BodyDetail detail = new Toa900012701.BodyDetail();
                detail.ACTNUM = bean.getACTNUM();
                detail.ACTNAM = bean.getBENACT();
                detail.BOKBAL = bean.getERYTIM();
                detail.AVABAL = bean.getFBTIDX();
                detail.FRZSTS = bean.getFEEAMT();
                detail.ACTSTS = bean.getINTAMT();
                detail.RECSTS = bean.getTXNAMT();
                toa.body.DETAILS.add(detail);
            }
        } else {
            toa.header.RETURN_MSG = SBSFormCode.valueOfAlias(formCode).getTitle();
            if (StringUtils.isEmpty(toa.header.RETURN_MSG)) {
                toa.header.RETURN_MSG = "交易失败";
            }
        }
        return toa;
    }
}
