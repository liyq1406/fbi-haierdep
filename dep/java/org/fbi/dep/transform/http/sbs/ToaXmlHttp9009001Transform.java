package org.fbi.dep.transform.http.sbs;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.com.ToolUtil;
import org.fbi.dep.enums.SBSFormCode;
import org.fbi.dep.enums.TxnStatus;
import org.fbi.dep.model.txn.ToaXml9009001;
import org.fbi.dep.transform.AbstractToaBytesTransform;
import org.fbi.endpoint.sbs.core.SBSResponse4SingleRecord;
import org.fbi.endpoint.sbs.txn.Taa41SOFDataDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 13-4-2
 * Time: ����11:04
 * To change this template use File | Settings | File Templates.
 */
/*
sbs-��תaa41
 */
public class ToaXmlHttp9009001Transform extends AbstractToaBytesTransform {
    private static Logger logger = LoggerFactory.getLogger(ToaXmlHttp9009001Transform.class);

    public String transform(byte[] bytes) {
        SBSResponse4SingleRecord response = new SBSResponse4SingleRecord();
        Taa41SOFDataDetail sofDataDetail = new Taa41SOFDataDetail();
        response.setSofDataDetail(sofDataDetail);
        response.init(bytes);
        logger.info("[sbs]������:" + response.getFormcode() +
                " Form��Ϣ��" + response.getForminfo() +
                " sbs��ˮ��:" + sofDataDetail.getSEQNUM() +
                " ��Χϵͳ��ˮ��:" + sofDataDetail.getSECNUM());
        String formCode = ToolUtil.SbsRtnCodeToDepRtnCode("9009001", response.getFormcode());
        // bean -> txn bean
        ToaXml9009001 toa = new ToaXml9009001();
        toa.INFO.REQ_SN = (StringUtils.isEmpty(sofDataDetail.getSECNUM()) ? "" : sofDataDetail.getSECNUM().trim());
        toa.INFO.RET_CODE = formCode;
        if (TxnStatus.TXN_SUCCESS.getCode().equalsIgnoreCase(toa.INFO.RET_CODE )) {
            toa.INFO.RET_MSG = TxnStatus.TXN_SUCCESS.getTitle();
        } else {
            try {
                if (!StringUtils.isEmpty(response.getForminfo())) {
                    toa.INFO.RET_MSG = response.getForminfo().trim();
                } else {
                    toa.INFO.RET_MSG = new String(SBSFormCode.valueOfAlias(response.getFormcode()).getTitle().getBytes(), "GBK");
                }
            } catch (NullPointerException e) {
                toa.INFO.RET_MSG = TxnStatus.TXN_FAILED.getTitle();
            } catch (UnsupportedEncodingException e) {
                logger.error("�ַ�������", e);
            }
        }
        return toa.toString();
    }
}
