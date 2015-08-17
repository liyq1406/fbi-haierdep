package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.enums.SBSFormCode;
import org.fbi.dep.model.base.TOA;
import org.fbi.dep.model.txn.Toa900012602;
import org.fbi.endpoint.sbs.core.FebResponse;
import org.fbi.endpoint.sbs.domain.SOFForm;
import org.fbi.endpoint.sbs.model.form.ac.T929;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created by hanjianlong on 2015-8-11.
 * SBS 900012602 -> 8874 日间明细对账应答报文
 */

public class Toa900012602Transform{
    private static Logger logger = LoggerFactory.getLogger(Toa900012602Transform.class);

    public TOA transform(byte[] bytes) {
        FebResponse response = new FebResponse();
        response.init(bytes);
        String formCode = response.getFormCodes().get(0);
        // bean -> txn bean
        Toa900012602 toa = new Toa900012602();
        toa.header.RETURN_CODE = formCode;
        if ("T846".equalsIgnoreCase(formCode)) {
            toa.header.RETURN_CODE="0000";
            toa.header.RETURN_MSG = "交易成功";
            SOFForm form = response.getSofForms().get(0);
            T929 t929 = (T929) form.getFormBody();
            toa.body.FLOFLG = t929.getFormBodyHeader().getFLOFLG();
            toa.body.TOTCNT = t929.getFormBodyHeader().getTOTCNT();
            toa.body.CURCNT = t929.getFormBodyHeader().getCURCNT();
            for (T929.Bean bean : t929.getBeanList()) {
                Toa900012602.BodyDetail detail = new Toa900012602.BodyDetail();
                detail.ACTNUM = bean.getACTNUM();
                detail.BENACT = bean.getBENACT();
                detail.ERYTIM = bean.getERYTIM();
                detail.FBTIDX = bean.getFBTIDX();
                detail.FEEAMT = bean.getFEEAMT();
                detail.INTAMT = bean.getINTAMT();
                detail.TXNAMT = bean.getTXNAMT();
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
