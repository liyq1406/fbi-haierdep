package org.fbi.endpoint.sbs.model.form.ac;

import org.fbi.endpoint.sbs.domain.SOFFormBody;

/**
 * 贷款开户 对应SBS-8124交易
 */
public class T101 extends SOFFormBody {

    {
        fieldTypes = new int[]{1, 1, 1, 1};
        fieldLengths = new int[]{18, 72, 8, 4};
    }

    private String ACTNUM;       // 账号
    private String CUSNAM;       // 客户名称
    private String OPNDAT;       // 建立日期
    private String AMDTLR;       // 操作员

    public String getACTNUM() {
        return ACTNUM;
    }

    public void setACTNUM(String ACTNUM) {
        this.ACTNUM = ACTNUM;
    }

    public String getCUSNAM() {
        return CUSNAM;
    }

    public void setCUSNAM(String CUSNAM) {
        this.CUSNAM = CUSNAM;
    }

    public String getOPNDAT() {
        return OPNDAT;
    }

    public void setOPNDAT(String OPNDAT) {
        this.OPNDAT = OPNDAT;
    }

    public String getAMDTLR() {
        return AMDTLR;
    }

    public void setAMDTLR(String AMDTLR) {
        this.AMDTLR = AMDTLR;
    }
}
