package org.fbi.dep.txn;

import org.apache.commons.lang.StringUtils;
import org.fbi.dep.component.jms.JmsBytesClient;
import org.fbi.dep.enums.SBSFormCode;
import org.fbi.dep.model.txn.TiaXml9009002;
import org.fbi.dep.model.txn.TiaXml9009003;
import org.fbi.dep.model.txn.ToaXml9009002;
import org.fbi.dep.model.txn.ToaXml9009003;
import org.fbi.dep.transform.SbsTxnDataTransform;
import org.fbi.dep.util.JaxbHelper;
import org.fbi.dep.util.PropertyManager;
import org.fbi.endpoint.mbp.domain.ClientRequestHead;
import org.fbi.endpoint.mbp.domain.queryresultrequest.QueryResultRequestParam;
import org.fbi.endpoint.mbp.domain.queryresultrequest.QueryResultRequestRoot;
import org.fbi.endpoint.mbp.domain.transactrequest.TransactRequestParam;
import org.fbi.endpoint.mbp.domain.transactrequest.TransactRequestRoot;
import org.fbi.endpoint.sbs.core.FebResponse;
import org.fbi.endpoint.sbs.domain.SOFForm;
import org.fbi.endpoint.sbs.model.form.ac.T531;
import org.fbi.endpoint.sbs.model.form.ac.T543;
import org.fbi.endpoint.sbs.model.form.ac.T999;
import org.fbi.endpoint.sbs.model.form.ac.WB02;

/**
 * 交易处理 单笔支付结果查询 SBS-MBP
 */
public class Txn9009003Processor extends AbstractTxnProcessor {

    public String process(String userid, String msgData) {

        String rtnXml = "";

        // 先发起SBS交易
        // byte[] sbsReqMsg = new TiaXml9009002Transform().run(msgData, userid);
        TiaXml9009003 tia = (TiaXml9009003) (new TiaXml9009003().getTia(msgData));
        String termID = PropertyManager.getProperty("sbs.termid." + userid);
        if (StringUtils.isEmpty(termID)) {
            termID = "MT01";
        }
        byte[] sbsReqMsg = SbsTxnDataTransform.convertToTxnN022(tia, termID);
        // SBS
        byte[] bytes = new JmsBytesClient().sendRecivMsg("900", "fcdep", "fcdep", "9009002", userid,
                "queue.dep.core.fcdep.sbs", "queue.dep.core.sbs.fcdep", sbsReqMsg);
        // 解析SBS报文
        FebResponse response = new FebResponse();
        response.init(bytes);
        String formCode = response.getFormCodes().get(0);
        // bean -> txn bean
        ToaXml9009003 toa = new ToaXml9009003();
        toa.INFO.RET_CODE = formCode;

        if ("T543".equalsIgnoreCase(formCode)) {
            toa.INFO.RET_MSG = "交易成功";
            SOFForm form = response.getSofForms().get(0);
            T543 t531 = (T543) form.getFormBody();
            // TODO

        } else if ("WB02".equalsIgnoreCase(formCode)) {
            toa.INFO.RET_MSG = "交易成功";
            SOFForm form = response.getSofForms().get(0);
            WB02 wb02 = (WB02) form.getFormBody();
            // TODO
        } else {
            toa.INFO.RET_MSG = SBSFormCode.valueOfAlias(formCode).getTitle();
            if (StringUtils.isEmpty(toa.INFO.RET_MSG)) {
                toa.INFO.RET_MSG = "交易失败";
            }
        }
        return toa.toString();
    }

    private String assembleMbpMsg(TiaXml9009003 tia) {
        String xml = "";
        QueryResultRequestRoot clientReqBean = new QueryResultRequestRoot();
        ClientRequestHead clientReqHead = new ClientRequestHead();
        QueryResultRequestParam clientReqParam = new QueryResultRequestParam();
        clientReqBean.setHead(clientReqHead);
        clientReqBean.setParam(clientReqParam);

        // TODO

        return new JaxbHelper().beanToXml(QueryResultRequestRoot.class, clientReqBean);
    }
}
