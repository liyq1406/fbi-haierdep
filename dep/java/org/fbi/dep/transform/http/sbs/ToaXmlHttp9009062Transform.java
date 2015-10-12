package org.fbi.dep.transform.http.sbs;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.com.ToolUtil;
import org.fbi.dep.enums.SBSFormCode;
import org.fbi.dep.enums.TxnStatus;
import org.fbi.dep.model.txn.ToaXml9009062;
import org.fbi.dep.transform.AbstractToaBytesTransform;
import org.fbi.endpoint.sbs.core.FebResponse;
import org.fbi.endpoint.sbs.domain.SOFForm;
import org.fbi.endpoint.sbs.model.form.ac.T108;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by XIANGYANG on 2015-5-11.
 * sbs 9009062 -> 8118 根据账号查询账户信息
 */

public class ToaXmlHttp9009062Transform extends AbstractToaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(ToaXmlHttp9009062Transform.class);

    public String transform(byte[] bytes) {
        FebResponse response = new FebResponse();
        response.init(bytes);
        String formCode = ToolUtil.SbsRtnCodeToDepRtnCode("9009062",response.getFormCodes().get(0));
        // bean -> txn bean
        ToaXml9009062 toa = new ToaXml9009062();
        toa.INFO.RET_CODE = formCode;
        if (TxnStatus.TXN_SUCCESS.getCode().equalsIgnoreCase(formCode)) {
            toa.INFO.RET_MSG = TxnStatus.TXN_SUCCESS.getTitle();
            SOFForm form = response.getSofForms().get(0);
            T108 t108 = (T108) form.getFormBody();
            toa.BODY.ORGIDT=t108.ORGIDT;
            toa.BODY.ACTNUM=t108.ACTNUM;
            toa.BODY.ACTNAM=t108.ACTNAM;
            toa.BODY.BOKBAL=t108.BOKBAL;
            toa.BODY.AVABAL=t108.AVABAL;
            toa.BODY.DIFBAL=t108.DIFBAL;
            toa.BODY.CIFBAL=t108.CIFBAL;
            toa.BODY.MAVBAL=t108.MAVBAL;
            toa.BODY.YAVBAL=t108.YAVBAL;
            toa.BODY.DDRAMT=t108.DDRAMT;
            toa.BODY.DCRAMT=t108.DCRAMT;
            toa.BODY.DDRCNT=t108.DDRCNT;
            toa.BODY.DCRCNT=t108.DCRCNT;
            toa.BODY.MDRAMT=t108.MDRAMT;
            toa.BODY.MCRAMT=t108.MCRAMT;
            toa.BODY.MDRCNT=t108.MDRCNT;
            toa.BODY.MCRCNT=t108.MCRCNT;
            toa.BODY.YDRAMT=t108.YDRAMT;
            toa.BODY.YCRAMT=t108.YCRAMT;
            toa.BODY.YDRCNT=t108.YDRCNT;
            toa.BODY.YCRCNT=t108.YCRCNT;
            toa.BODY.DINRAT=t108.DINRAT;
            toa.BODY.CINRAT=t108.CINRAT;
            toa.BODY.DRATSF=t108.DRATSF;
            toa.BODY.CRATSF=t108.CRATSF;
            toa.BODY.DACINT=t108.DACINT;
            toa.BODY.CACINT=t108.CACINT;
            toa.BODY.DAMINT=t108.DAMINT;
            toa.BODY.CAMINT=t108.CAMINT;
            toa.BODY.INTFLG=t108.INTFLG;
            toa.BODY.INTCYC=t108.INTCYC;
            toa.BODY.INTTRA=t108.INTTRA;
            toa.BODY.LINTDT=t108.LINTDT;
            toa.BODY.LINDTH=t108.LINDTH;
            toa.BODY.STMCYC=t108.STMCYC;
            toa.BODY.STMCDT=t108.STMCDT;
            toa.BODY.STMFMT=t108.STMFMT;
            toa.BODY.STMSHT=t108.STMSHT;
            toa.BODY.STMDEP=t108.STMDEP;
            toa.BODY.STMADD=t108.STMADD;
            toa.BODY.STMZIP=t108.STMZIP;
            toa.BODY.LEGCYC=t108.LEGCYC;
            toa.BODY.LEGCDT=t108.LEGCDT;
            toa.BODY.LEGFMT=t108.LEGFMT;
            toa.BODY.LEGSHT=t108.LEGSHT;
            toa.BODY.LEGDEP=t108.LEGDEP;
            toa.BODY.LEGADD=t108.LEGADD;
            toa.BODY.LEGZIP=t108.LEGZIP;
            toa.BODY.ACTTYP=t108.ACTTYP;
            toa.BODY.ACTGLC=t108.ACTGLC;
            toa.BODY.ACTPLC=t108.ACTPLC;
            toa.BODY.DEPNUM=t108.DEPNUM;
            toa.BODY.LENTDT=t108.LENTDT;
            toa.BODY.DRACCM=t108.DRACCM;
            toa.BODY.CRACCM=t108.CRACCM;
            toa.BODY.CQEFLG=t108.CQEFLG;
            toa.BODY.BALLIM=t108.BALLIM;
            toa.BODY.OVELIM=t108.OVELIM;
            toa.BODY.OVEEXP=t108.OVEEXP;
            toa.BODY.OPNDAT=t108.OPNDAT;
            toa.BODY.CLSDAT=t108.CLSDAT;
            toa.BODY.REGSTS=t108.REGSTS;
            toa.BODY.FRZSTS=t108.FRZSTS;
            toa.BODY.ACTSTS=t108.ACTSTS;
            toa.BODY.AMDTLR=t108.AMDTLR;
            toa.BODY.UPDDAT=t108.UPDDAT;
            toa.BODY.RECSTS=t108.RECSTS;
        } else {
            toa.INFO.RET_MSG = SBSFormCode.valueOfAlias(formCode).getTitle();
            if (StringUtils.isEmpty(toa.INFO.RET_MSG)) {
                toa.INFO.RET_MSG = TxnStatus.TXN_FAILED.getTitle();
            }
        }
        return toa.toString();
    }
}
