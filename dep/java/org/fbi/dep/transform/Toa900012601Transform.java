package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.enums.SBSFormCode;
import org.fbi.dep.model.base.TOA;
import org.fbi.dep.model.txn.Toa900012601;
import org.fbi.dep.model.txn.Toa900012602;
import org.fbi.endpoint.sbs.core.FebResponse;
import org.fbi.endpoint.sbs.domain.SOFForm;
import org.fbi.endpoint.sbs.model.form.ac.T846;
import org.fbi.endpoint.sbs.model.form.ac.T929;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hanjianlong on 2015-8-11.
 * SBS 900012601 -> 8872 日间总数对账响应报文
 */

public class Toa900012601Transform {
    private static Logger logger = LoggerFactory.getLogger(Toa900012601Transform.class);

    public TOA transform(byte[] bytes) {
        FebResponse response = new FebResponse();
        response.init(bytes);
        String formCode = response.getFormCodes().get(0);
        // bean -> txn bean
        Toa900012601 toa = new Toa900012601();
        toa.header.RETURN_CODE = formCode;
        if ("T846".equalsIgnoreCase(formCode)) {
            toa.header.RETURN_MSG = "交易成功";
            SOFForm form = response.getSofForms().get(0);
            T846 t846 = (T846) form.getFormBody();
            toa.body.CRAMT = t846.getCRAMT();
            toa.body.CRCNT = t846.getCRCNT();
            toa.body.DRAMT = t846.getDRAMT();
            toa.body.DRCNT = t846.getDRCNT();
        } else {
            toa.header.RETURN_MSG = SBSFormCode.valueOfAlias(formCode).getTitle();
            if (StringUtils.isEmpty(toa.header.RETURN_MSG)) {
                toa.header.RETURN_MSG = "交易失败";
            }
        }
        return toa;
    }
}
