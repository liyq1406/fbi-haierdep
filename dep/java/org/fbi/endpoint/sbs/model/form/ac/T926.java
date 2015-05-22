package org.fbi.endpoint.sbs.model.form.ac;

import org.fbi.endpoint.sbs.domain.AssembleModel;
import org.fbi.endpoint.sbs.domain.SOFFormBody;

import java.util.ArrayList;
import java.util.List;

/**
 *  8854 查询账户对账单明细
 */

public class T926 extends SOFFormBody {

    private List<Bean> beanList = new ArrayList<Bean>();
    private FormBodyHeader formBodyHeader = new FormBodyHeader();

    @Override
    public void assembleFields(int offset, byte[] buffer) {
        formBodyHeader.assembleFields(offset,buffer);
        int index = offset + 59;
        int beanLength = 98;
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
            fieldTypes = new int[]{1, 1, 1, 1, 1, 1, 1};
            fieldLengths = new int[]{1, 6, 6, 6, 17, 6, 17};
        }

        private String FLOFLG;   //后续标识
        private String TOTCNT;   //总记录数
        private String CURCNT;   //本包内记录数
        private String DBTCNT;   //借方总笔数
        private String DBTAMT;   //借方总金额
        private String CRTCNT;   //贷方总笔数
        private String CRTAMT;   //贷方总金额

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

        public String getDBTCNT() {
            return DBTCNT;
        }

        public void setDBTCNT(String DBTCNT) {
            this.DBTCNT = DBTCNT;
        }

        public String getDBTAMT() {
            return DBTAMT;
        }

        public void setDBTAMT(String DBTAMT) {
            this.DBTAMT = DBTAMT;
        }

        public String getCRTCNT() {
            return CRTCNT;
        }

        public void setCRTCNT(String CRTCNT) {
            this.CRTCNT = CRTCNT;
        }

        public String getCRTAMT() {
            return CRTAMT;
        }

        public void setCRTAMT(String CRTAMT) {
            this.CRTAMT = CRTAMT;
        }
    }


    public class Bean extends AssembleModel {
        {
            fieldTypes = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
            fieldLengths = new int[]{7, 4, 3, 8, 4, 4, 2, 17, 17, 32};
        }

        private String CUSIDT;          // 客户号
        private String APCODE;          // 核算码
        private String CURCDE;          // 货币号
        private String STMDAT;          // 交易日期
        private String TLRNUM;          // 交易柜员
        private String VCHSET;          // 传票套号
        private String SETSEQ;          // 传票套内顺序号
        private String TXNAMT;          // 交易金额
        private String LASBAL;          // 交易后余额
        private String FURINF;          // 摘要

        public String getCUSIDT() {
            return CUSIDT;
        }

        public void setCUSIDT(String CUSIDT) {
            this.CUSIDT = CUSIDT;
        }

        public String getAPCODE() {
            return APCODE;
        }

        public void setAPCODE(String APCODE) {
            this.APCODE = APCODE;
        }

        public String getCURCDE() {
            return CURCDE;
        }

        public void setCURCDE(String CURCDE) {
            this.CURCDE = CURCDE;
        }

        public String getSTMDAT() {
            return STMDAT;
        }

        public void setSTMDAT(String STMDAT) {
            this.STMDAT = STMDAT;
        }

        public String getTLRNUM() {
            return TLRNUM;
        }

        public void setTLRNUM(String TLRNUM) {
            this.TLRNUM = TLRNUM;
        }

        public String getVCHSET() {
            return VCHSET;
        }

        public void setVCHSET(String VCHSET) {
            this.VCHSET = VCHSET;
        }

        public String getSETSEQ() {
            return SETSEQ;
        }

        public void setSETSEQ(String SETSEQ) {
            this.SETSEQ = SETSEQ;
        }

        public String getTXNAMT() {
            return TXNAMT;
        }

        public void setTXNAMT(String TXNAMT) {
            this.TXNAMT = TXNAMT;
        }

        public String getLASBAL() {
            return LASBAL;
        }

        public void setLASBAL(String LASBAL) {
            this.LASBAL = LASBAL;
        }

        public String getFURINF() {
            return FURINF;
        }

        public void setFURINF(String FURINF) {
            this.FURINF = FURINF;
        }
    }

    public List<Bean> getBeanList() {
        return beanList;
    }

    public FormBodyHeader getFormBodyHeader() {
        return formBodyHeader;
    }
}
