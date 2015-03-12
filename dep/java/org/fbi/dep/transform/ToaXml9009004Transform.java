package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.enums.SBSFormCode;
import org.fbi.dep.model.txn.ToaXml9009001;
import org.fbi.dep.model.txn.ToaXml9009004;
import org.fbi.endpoint.sbs.core.FebResponse;
import org.fbi.endpoint.sbs.core.SBSResponse4SingleRecord;
import org.fbi.endpoint.sbs.domain.SOFForm;
import org.fbi.endpoint.sbs.model.form.re.T531;
import org.fbi.endpoint.sbs.model.form.re.T999;
import org.fbi.endpoint.sbs.txn.Taa41SOFDataDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 13-4-2
 * Time: 下午11:04
 * To change this template use File | Settings | File Templates.
 */
/*
SBS-内转再内转aa4b
 */
public class ToaXml9009004Transform extends AbstractToaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(ToaXml9009004Transform.class);

    public String transform(byte[] bytes) {

        FebResponse response = new FebResponse();
        response.init(bytes);
        String formCode = response.getFormCodes().get(0);
        // bean -> txn bean
        ToaXml9009004 toa = new ToaXml9009004();
        toa.INFO.RET_CODE = formCode;

        if ("T531".equalsIgnoreCase(formCode)) {
            toa.INFO.RET_MSG = "交易成功";
            SOFForm form = response.getSofForms().get(0);
            T531 t531 = (T531) form.getFormBody();
            copyFormBodyToToa(t531, toa);
            toa.INFO.REQ_SN = t531.getSECNUM();

        } else if ("T999".equalsIgnoreCase(formCode)) {
            toa.INFO.RET_MSG = "交易成功";
            SOFForm form = response.getSofForms().get(0);
            T999 t999 = (T999) form.getFormBody();
            copyFormBodyToToa(t999, toa);
        } else {
            toa.INFO.RET_MSG = SBSFormCode.valueOfAlias(formCode).getTitle();
            if (StringUtils.isEmpty(toa.INFO.RET_MSG)) {
                toa.INFO.RET_MSG = "交易失败";
            }
        }
        return toa.toString();
    }
}
