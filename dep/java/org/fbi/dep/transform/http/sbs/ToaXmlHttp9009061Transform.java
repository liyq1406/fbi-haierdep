package org.fbi.dep.transform.http.sbs;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.com.ToolUtil;
import org.fbi.dep.enums.SBSFormCode;
import org.fbi.dep.enums.TxnStatus;
import org.fbi.dep.model.txn.ToaXml9009061;
import org.fbi.dep.transform.AbstractToaBytesTransform;
import org.fbi.endpoint.sbs.core.FebResponse;
import org.fbi.endpoint.sbs.domain.SOFForm;
import org.fbi.endpoint.sbs.model.form.ac.T928;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * 根据客户证件信息查询账户列表 8856 -> 9009061
 */
public class ToaXmlHttp9009061Transform extends AbstractToaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(ToaXmlHttp9009061Transform.class);

    public String transform(byte[] bytes) {
        FebResponse response = new FebResponse();
        response.init(bytes);
        String formCode = ToolUtil.SbsRtnCodeToDepRtnCode("9009061", response.getFormCodes().get(0));
        // bean -> txn bean
        ToaXml9009061 toa = new ToaXml9009061();
        toa.INFO.RET_CODE = formCode;
        if (TxnStatus.TXN_SUCCESS.getCode().equalsIgnoreCase(formCode)) {
            toa.INFO.RET_MSG = TxnStatus.TXN_SUCCESS.getTitle();
            SOFForm form = response.getSofForms().get(0);
            T928 t928 = (T928) form.getFormBody();
            toa.BODY.TOTCNT = t928.getFormBodyHeader().getTOTCNT();
            toa.BODY.CURCNT = t928.getFormBodyHeader().getCURCNT();
            toa.BODY.DETAILS = new ArrayList<ToaXml9009061.Body.BodyDetail>();
            for (T928.Bean bean : t928.getBeanList()) {
                ToaXml9009061.Body.BodyDetail detail = new ToaXml9009061.Body.BodyDetail();
                detail.CUSACT = bean.getCUSACT();
                detail.ACTTYP = bean.getACTTYP();
                detail.CUSIDT = bean.getCUSIDT();
                detail.CUSNAM = bean.getCUSNAM();
                detail.APCODE = bean.getAPCODE();
                toa.BODY.DETAILS.add(detail);
            }
        } else {
//            toa.INFO.RET_MSG = SBSFormCode.valueOfAlias(formCode).getTitle();
            try {
                toa.INFO.RET_MSG = new String(SBSFormCode.valueOfAlias(response.getFormCodes().get(0)).getTitle().getBytes(), "GBK");
                if (StringUtils.isEmpty(toa.INFO.RET_MSG)) {
                    toa.INFO.RET_MSG = TxnStatus.TXN_FAILED.getTitle();
                }
            }catch (NullPointerException e) {
                toa.INFO.RET_MSG = TxnStatus.TXN_FAILED.getTitle();
            } catch (UnsupportedEncodingException e) {
                logger.error("字符集错误", e);
            }
        }
        return toa.toString();
    }
}
