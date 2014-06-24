package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.enums.SBSFormCode;
import org.fbi.dep.enums.TxnRtnCode;
import org.fbi.dep.model.base.ToaXmlHeader;
import org.fbi.dep.model.base.ToaXmlInfo;
import org.fbi.dep.model.txn.ToaXml1003001;
import org.fbi.dep.model.txn.ToaXml9009001;
import org.fbi.endpoint.sbs.core.SBSResponse4SingleRecord;
import org.fbi.endpoint.sbs.core.SOFHeader;
import org.fbi.endpoint.sbs.txn.Taa41SOFDataDetail;
import org.fbi.endpoint.unionpay.txn.domain.T200001Toa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 13-4-2
 * Time: 下午11:04
 * To change this template use File | Settings | File Templates.
 */
/*
SBS-内转aa41
 */
public class ToaXml9009001Transform extends AbstractToaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(ToaXml9009001Transform.class);

    public String transform(byte[] bytes) {

        SBSResponse4SingleRecord response = new SBSResponse4SingleRecord();
        Taa41SOFDataDetail sofDataDetail = new Taa41SOFDataDetail();
        response.setSofDataDetail(sofDataDetail);
        response.init(bytes);
        logger.info("[sbs]返回码:" + response.getFormcode() +
                " Form信息：" + response.getForminfo() +
                " sbs流水号:" + sofDataDetail.getSEQNUM() +
                " 外围系统流水号:" + sofDataDetail.getSECNUM());
        // bean -> txn bean
        ToaXml9009001 toa = new ToaXml9009001();
        toa.INFO.REQ_SN = (StringUtils.isEmpty(sofDataDetail.getSECNUM()) ? "" : sofDataDetail.getSECNUM().trim());
        toa.INFO.RET_CODE = response.getFormcode();
        if ("T531".equalsIgnoreCase(response.getFormcode())) {
            toa.INFO.RET_MSG = "交易成功";
        } else {
            try {
                if (!StringUtils.isEmpty(response.getForminfo())) {
                    toa.INFO.RET_MSG = response.getForminfo().trim();
                } else {
                    toa.INFO.RET_MSG = new String(SBSFormCode.valueOfAlias(response.getFormcode()).getTitle().getBytes(), "GBK");
                }
            } catch (NullPointerException e) {
                if (response.getFormcode().startsWith("T") || response.getFormcode().startsWith("t")) {
                    toa.INFO.RET_MSG = "交易成功";
                } else
                    toa.INFO.RET_MSG = SBSFormCode.OTHER.getTitle();
            } catch (UnsupportedEncodingException e) {
                logger.error("字符集错误", e);
            }
        }
       /* if (StringUtils.isEmpty(toa.INFO.RET_MSG)) {
            if (response.getFormcode().startsWith("T") || response.getFormcode().startsWith("t")) {
                toa.INFO.RET_MSG = "交易成功";
            } else
                toa.INFO.RET_MSG = SBSFormCode.OTHER.getTitle();
        }*/
        return toa.toString();
    }
}
