package org.fbi.dep.txn;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.component.jms.JmsBytesClient;
import org.fbi.dep.enums.SBSFormCode;
import org.fbi.dep.model.txn.TiaXml9009002;
import org.fbi.dep.model.txn.ToaXml9009002;
import org.fbi.dep.transform.SbsTxnDataTransform;
import org.fbi.dep.util.JaxbHelper;
import org.fbi.dep.util.PropertyManager;
import org.fbi.dep.util.StringPad;
import org.fbi.endpoint.mbp.domain.ClientRequestHead;
import org.fbi.endpoint.mbp.domain.transactrequest.TransactRequestParam;
import org.fbi.endpoint.mbp.domain.transactrequest.TransactRequestRoot;
import org.fbi.endpoint.sbs.core.FebResponse;
import org.fbi.endpoint.sbs.domain.SOFForm;
import org.fbi.endpoint.sbs.model.form.ac.T531;
import org.fbi.endpoint.sbs.model.form.ac.T999;

/**
 * 交易处理 SBS-MBP
 */
public class Txn9009002Processor extends AbstractTxnProcessor {

    public String process(String userid, String msgData) {

        String rtnXml = "";

        // 先发起SBS交易
        // byte[] sbsReqMsg = new TiaXml9009002Transform().run(msgData, userid);
        TiaXml9009002 tia = (TiaXml9009002) (new TiaXml9009002().getTia(msgData));
        String termID = PropertyManager.getProperty("sbs.termid." + userid);
        if (StringUtils.isEmpty(termID)) {
            termID = "MT01";
        }
        byte[] sbsReqMsg = SbsTxnDataTransform.convertToTxnN120(tia, termID);
        // SBS
        byte[] bytes = new JmsBytesClient().sendRecivMsg("900", "fcdep", "fcdep", "9009002", userid,
                "queue.dep.core.fcdep.sbs", "queue.dep.core.sbs.fcdep", sbsReqMsg);
        // 解析SBS报文
        FebResponse response = new FebResponse();
        response.init(bytes);
        String formCode = response.getFormCodes().get(0);
        // bean -> txn bean
        ToaXml9009002 toa = new ToaXml9009002();
        toa.INFO.RET_CODE = formCode;

        if ("T531".equalsIgnoreCase(formCode)) {
            toa.INFO.RET_MSG = "交易成功";
            SOFForm form = response.getSofForms().get(0);
            T531 t531 = (T531) form.getFormBody();
            // 落地则直接返回
            if ("01".equals(t531.getACTTYP1())) {
                copyFormBodyToToa(t531, toa);
            } else {
                // TODO 不落地时，发往MBP

            }

        } else if ("T999".equalsIgnoreCase(formCode)) {
            toa.INFO.RET_MSG = "交易成功";
            SOFForm form = response.getSofForms().get(0);
            T999 t999 = (T999) form.getFormBody();
            if ("01".equals(t999.getACTTYP1())) {
                copyFormBodyToToa(t999, toa);
            } else {
                // TODO 不落地时，发往MBP
            }
        } else {
            toa.INFO.RET_MSG = SBSFormCode.valueOfAlias(formCode).getTitle();
            if (StringUtils.isEmpty(toa.INFO.RET_MSG)) {
                toa.INFO.RET_MSG = "交易失败";
            }
        }
        return toa.toString();
    }

    private String assembleMbpMsg(TiaXml9009002 tia) {

        StringBuilder builder = new StringBuilder();
        builder.append(StringPad.rightPad4ChineseToByteLength("Transact", 32, " "));
        builder.append(StringPad.rightPad4ChineseToByteLength("SBS", 16, " "));
        builder.append(StringPad.rightPad4ChineseToByteLength("105", 16, " "));

        // TODO 拼接报文
        String xml = "";
        TransactRequestRoot clientReqBean = new TransactRequestRoot();
        ClientRequestHead clientReqHead = new ClientRequestHead();
        TransactRequestParam clientReqParam = new TransactRequestParam();
        clientReqBean.setHead(clientReqHead);
        clientReqBean.setParam(clientReqParam);
//        clientReqHead.setOpBankCode(tia.BODY);

        // TODO

        return new JaxbHelper().beanToXml(TransactRequestRoot.class, clientReqBean);
    }


}
