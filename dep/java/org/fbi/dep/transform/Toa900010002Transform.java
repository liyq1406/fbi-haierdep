package org.fbi.dep.transform;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.enums.SBSFormCode;
import org.fbi.dep.model.base.TOA;
import org.fbi.dep.model.txn.Toa900010002;
import org.fbi.dep.model.txn.ToaXml9009001;
import org.fbi.endpoint.sbs.core.SBSResponse4SingleRecord;
import org.fbi.endpoint.sbs.domain.SOFForm;
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
SBS-内转aa41
 */
public class Toa900010002Transform {
    private static Logger logger = LoggerFactory.getLogger(Toa900010002Transform.class);

    public TOA transform(byte[] bytes) {
        SBSResponse4SingleRecord response = new SBSResponse4SingleRecord();
        Taa41SOFDataDetail sofDataDetail = new Taa41SOFDataDetail();
        response.setSofDataDetail(sofDataDetail);
        response.init(bytes);
        logger.info("[sbs]返回码:" + response.getFormcode() +
                " Form信息：" + response.getForminfo() +
                " sbs流水号:" + sofDataDetail.getSEQNUM() +
                " 外围系统流水号:" + sofDataDetail.getSECNUM());
        // bean -> txn bean
        Toa900010002 toa = new Toa900010002();
        toa.header.REQ_SN = (StringUtils.isEmpty(sofDataDetail.getSECNUM()) ? "" : sofDataDetail.getSECNUM().trim());
        toa.header.RETURN_CODE = response.getFormcode();

        if ("T531".equalsIgnoreCase(response.getFormcode())) {
            toa.header.RETURN_CODE="0000";
            toa.header.RETURN_MSG = "交易成功";
        } else {
            try {
                if (!StringUtils.isEmpty(response.getForminfo())) {
                    toa.header.RETURN_MSG = response.getForminfo().trim();
                } else {
                    toa.header.RETURN_MSG = new String(SBSFormCode.valueOfAlias(response.getFormcode()).getTitle().getBytes(), "GBK");
                }
            } catch (NullPointerException e) {
                if (response.getFormcode().startsWith("T") || response.getFormcode().startsWith("t")) {
                    toa.header.RETURN_MSG = "交易成功";
                } else
                    toa.header.RETURN_MSG = SBSFormCode.OTHER.getTitle();
            } catch (UnsupportedEncodingException e) {
                logger.error("字符集错误", e);
            }
        }
        return toa;
    }
}
