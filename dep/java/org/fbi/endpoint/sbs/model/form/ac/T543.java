package org.fbi.endpoint.sbs.model.form.ac;

import org.fbi.endpoint.sbs.domain.SOFFormBody;

/**
 *  对应SBS-n022交易
 */
public class T543 extends SOFFormBody {

    {
        fieldTypes = new int[]{1, 1, 1, 1, 1};
        fieldLengths = new int[]{4, 4, 2, 32, 18};
    }

    private String TLRNUM;	// 柜员号
    private String VCHSET;	// 传票套号
    private String SETSEQ;	// 传票套内顺序号
    private String TXNSEQ;   // 银行流水号
    private String FBTIDX;	// 外围系统流水号

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

    public String getTXNSEQ() {
        return TXNSEQ;
    }

    public void setTXNSEQ(String TXNSEQ) {
        this.TXNSEQ = TXNSEQ;
    }

    public String getFBTIDX() {
        return FBTIDX;
    }

    public void setFBTIDX(String FBTIDX) {
        this.FBTIDX = FBTIDX;
    }
}
