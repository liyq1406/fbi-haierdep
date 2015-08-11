package org.fbi.endpoint.sbs.model.form.ac;

import org.fbi.endpoint.sbs.domain.AssembleModel;
import org.fbi.endpoint.sbs.domain.SOFFormBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 8874 日间明细对账应答报文
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 15-8-11
 * Time: 下午2:12
 * To change this template use File | Settings | File Templates.
 */
public class T929 extends SOFFormBody {

    private List<Bean> beanList = new ArrayList<Bean>();
    private FormBodyHeader formBodyHeader = new FormBodyHeader();

    @Override
    public void assembleFields(int offset, byte[] buffer) {
        formBodyHeader.assembleFields(offset,buffer);
        int index = offset + 13;
        int beanLength = 129;
        int i=0;
        int b=0;
        do {
            Bean bean = new Bean();
            bean.assembleFields(index, buffer);
            beanList.add(bean);
            index += beanLength;
            b=++i;
        } while (index < buffer.length);
    }

    public class FormBodyHeader extends AssembleModel {
        {
            fieldTypes = new int[]{1, 1, 1};
            fieldLengths = new int[]{1, 6, 6};
        }

        private String FLOFLG;   //后续标识
        private String TOTCNT;   //总记录数
        private String CURCNT;   //本包内记录数

        public String getFLOFLG() {
            return FLOFLG;
        }

        public void setFLOFLG(String FLOFLG) {
            this.FLOFLG = FLOFLG;
        }

        public String getTOTCNT() {
            return TOTCNT;
        }

        public void setTOTCNT(String TOTCNT) {
            this.TOTCNT = TOTCNT;
        }

        public String getCURCNT() {
            return CURCNT;
        }

        public void setCURCNT(String CURCNT) {
            this.CURCNT = CURCNT;
        }
    }

    public class Bean extends AssembleModel {
        {
            fieldTypes = new int[]{1, 1, 1, 1, 1, 1, 1};
            fieldLengths = new int[]{18, 22, 16, 16, 16, 35, 6};
        }

        private String FBTIDX;          // 外围系统流水号
        private String ACTNUM;          // 付款账号
        private String TXNAMT;          // 交易金额（贷款交易时，本金）
        private String INTAMT;          // 利息金额
        private String FEEAMT;          // 手续费金额
        private String BENACT;          // 对方账号
        private String ERYTIM;          // 交易时间

        public String getFBTIDX() {
            return FBTIDX;
        }

        public void setFBTIDX(String FBTIDX) {
            this.FBTIDX = FBTIDX;
        }

        public String getACTNUM() {
            return ACTNUM;
        }

        public void setACTNUM(String ACTNUM) {
            this.ACTNUM = ACTNUM;
        }

        public String getTXNAMT() {
            return TXNAMT;
        }

        public void setTXNAMT(String TXNAMT) {
            this.TXNAMT = TXNAMT;
        }

        public String getINTAMT() {
            return INTAMT;
        }

        public void setINTAMT(String INTAMT) {
            this.INTAMT = INTAMT;
        }

        public String getFEEAMT() {
            return FEEAMT;
        }

        public void setFEEAMT(String FEEAMT) {
            this.FEEAMT = FEEAMT;
        }

        public String getBENACT() {
            return BENACT;
        }

        public void setBENACT(String BENACT) {
            this.BENACT = BENACT;
        }

        public String getERYTIM() {
            return ERYTIM;
        }

        public void setERYTIM(String ERYTIM) {
            this.ERYTIM = ERYTIM;
        }
    }

    public List<Bean> getBeanList() {
        return beanList;
    }

    public FormBodyHeader getFormBodyHeader() {
        return formBodyHeader;
    }
}
