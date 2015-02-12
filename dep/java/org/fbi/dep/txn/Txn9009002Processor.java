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
import org.fbi.endpoint.mbp.MbpClient;
import org.fbi.endpoint.mbp.domain.ClientRequestHead;
import org.fbi.endpoint.mbp.domain.transactreponse.TransactResponseRoot;
import org.fbi.endpoint.mbp.domain.transactrequest.TransactRequestParam;
import org.fbi.endpoint.mbp.domain.transactrequest.TransactRequestRoot;
import org.fbi.endpoint.sbs.core.FebResponse;
import org.fbi.endpoint.sbs.domain.SOFForm;
import org.fbi.endpoint.sbs.model.form.re.T531;
import org.fbi.endpoint.sbs.model.form.re.T999;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * ���״��� SBS-MBP
 */
public class Txn9009002Processor extends AbstractTxnProcessor {

    private static Logger logger = LoggerFactory.getLogger(Txn9009002Processor.class);

    public String process(String userid, String msgData) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {

        String rtnXml = "";

        // �ȷ���SBS����
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
        // ����SBS����
        FebResponse response = new FebResponse();
        response.init(bytes);
        String formCode = response.getFormCodes().get(0);
        logger.info("SBS���׷����룺" + formCode);
        // bean -> txn bean
        ToaXml9009002 toa = new ToaXml9009002();
        toa.INFO.RET_CODE = formCode;

        if ("T531".equalsIgnoreCase(formCode)) {
            toa.INFO.RET_MSG = "�������";
            SOFForm form = response.getSofForms().get(0);
            T531 t531 = (T531) form.getFormBody();
            // �����ֱ�ӷ���
            if ("01".equals(t531.getACTTYP1())) {
                copyFormBodyToToa(t531, toa);
            } else {
                // TODO �����ʱ������MBP
                String mbpMsg = assembleMbpMsg(tia);
                // TODO ֱ��������MBP
                MbpClient client = new MbpClient();
                byte[] rtnBytes = client.onSend(mbpMsg.getBytes());
                TransactResponseRoot res = client.convert4Transact(rtnBytes);
                // TODO
                copyFormBodyToToa(t531, toa);
            }

        } else if ("T999".equalsIgnoreCase(formCode)) {
            toa.INFO.RET_MSG = "�������";
            SOFForm form = response.getSofForms().get(0);
            T999 t999 = (T999) form.getFormBody();
            if ("01".equals(t999.getACTTYP1())) {
                copyFormBodyToToa(t999, toa);
            } else {
                // TODO �����ʱ������MBP
                String mbpMsg = assembleMbpMsg(tia);
                logger.info(mbpMsg);
                MbpClient client = new MbpClient();
                byte[] rtnBytes = client.onSend(mbpMsg.getBytes());
                TransactResponseRoot res = client.convert4Transact(rtnBytes);
                // TODO
                copyFormBodyToToa(t999, toa);
            }
        } else {
            toa.INFO.RET_MSG = SBSFormCode.valueOfAlias(formCode).getTitle();
            if (StringUtils.isEmpty(toa.INFO.RET_MSG)) {
                toa.INFO.RET_MSG = "����ʧ��";
            }
        }
        return toa.toString();
    }

    private String assembleMbpMsg(TiaXml9009002 tia) {

        StringBuilder builder = new StringBuilder();
        builder.append(StringPad.rightPad4ChineseToByteLength("Transact", 32, " "));
        builder.append(StringPad.rightPad4ChineseToByteLength("SBS", 16, " "));
        builder.append(StringPad.rightPad4ChineseToByteLength("105", 16, " "));

        // TODO ƴ�ӱ���
        String xml = "";
        TransactRequestRoot clientReqBean = new TransactRequestRoot();
        ClientRequestHead clientReqHead = new ClientRequestHead();
        TransactRequestParam clientReqParam = new TransactRequestParam();
        clientReqBean.setHead(clientReqHead);
        clientReqBean.setParam(clientReqParam);
        // TODO Head�ڱ�����ֵ
        clientReqHead.setOpBankCode("105");
        clientReqHead.setOpName("Transacts");
        clientReqHead.setOpName("Transacts");
        clientReqHead.setOpEntID("SBS");
//        clientReqHead.setOpDate(tia.BODY.);

        /*
        public String ORGIDT;     // ���׻���
        public String FBTACT;     // �ͻ���
        public String ORDTYP;     // ��������
        public String RMTTYP;	  // �������
        public String CUSTYP;	  // ����ʻ�����
        public String FEETYP;	  // �Ƿ����
        public String FEEACT;	  // �����ʻ�
        public String PBKACT;	  // �����˺�
        public String CHQTYP;	  // ֧Ʊ����
        public String CHQPWD;	  // ֧Ʊ����
        public String FUNCDE;	  // ������
        public String ADVNUM;	  // FS��ˮ��
         */
        clientReqParam.setToAccount(tia.BODY.BENACT);
        clientReqParam.setToName(tia.BODY.BENNAM);
        clientReqParam.setToBank(tia.BODY.BENBNK);
        clientReqParam.setReserved1(tia.BODY.CHQNUM);         // ת���˻��ڲ��к�
        clientReqParam.setReserved2(tia.BODY.CHQNUM);         // ת���˻�12λ�к�

        clientReqParam.setFromAccount(tia.BODY.CUSACT);
        clientReqParam.setFromName(tia.BODY.RETNAM);
        clientReqParam.setFromBank(tia.BODY.AGENBK);          // ת����
        clientReqParam.setEnterpriseSerial(tia.BODY.REQNUM);  // �������к�
        clientReqParam.setAmount(tia.BODY.TXNAMT);            // ���
        clientReqParam.setCurrency(tia.BODY.TXNCUR);          // ����
        clientReqParam.setUsage(tia.BODY.RETAUX);             // ��;
        clientReqParam.setTransDate(tia.BODY.ORDDAT);         // ��������

        clientReqParam.setSystem("1");


        // TODO

        return new JaxbHelper().beanToXml(TransactRequestRoot.class, clientReqBean);
    }


}
