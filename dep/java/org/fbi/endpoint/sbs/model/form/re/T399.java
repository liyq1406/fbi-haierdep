package org.fbi.endpoint.sbs.model.form.re;

import org.fbi.endpoint.sbs.domain.SOFFormBody;

/**
 * 通知存款-支取结清9009506
 * (对应SBS-a13a交易)
 */
public class T399 extends SOFFormBody {

    {
        fieldTypes = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        fieldLengths = new int[]{ 4, 8, 4, 4, 16, 2, 22, 8, 4, 16, 9, 16, 4, 16, 9, 16, 4, 16,
                9, 16, 4, 16, 9, 16, 16, 16, 6, 16, 16, 7, 8, 16 };
    }

    private String TXNCDE;  // 交易码
    private String TXNDAT;  // 交易日期（固定长度yyyyMMdd）
    private String TELLER;  // 柜员代码
    private String VCHSET;  // 传票套号
    private String ACTNAM;  // 帐户名
    private String ACTTY;   // 账户类别
    private String IPTAC;   // 帐户号
    private String CCYNAM;  // 货币中文名称
    private String APCDE1;  // 期内
    private String TXNAMT1; // 取款金额（##############9.99）
    private String OPNIRT;  // 利率（##9.999999）
    private String ININT;   // 期内利息（##############9.99）
    private String APCDE2;  // 期外
    private String TXNAMT2; // 期外计息本金（##############9.99）
    private String SAVIRT;  // 期外利率（##9.999999）
    private String OUTINT;  // 期外利息（##############9.99）
    private String APCDE3;  // 备用
    private String TXNAMT3; // 备用（##############9.99）
    private String VALIRT;  // 备用（##9.999999）
    private String VALINT;  // 备用（##############9.99）
    private String APCDE4;  // 备用
    private String TXNAMT4; // 备用（##############9.99）
    private String FEERAT;  // 备用（##9.999999）
    private String FEEAMT;  // 备用（##############9.99）
    private String PIVINT1; // 利息总额（##############9.99）
    private String PIVINT2; // 计税利息（##############9.99）
    private String TAXRATE; // 税率（##9.999999）
    private String TAXAMT;  // 税额（##############9.99）
    private String TOTINT;  // 实付利息总额（##############9.99）
    private String DATHED;  // 起息日:
    private String VALDAT;  // 起息日期（固定长 yyyyMMdd）
    private String TOTAMT;  // 支付本息总金额（##############9.99）

    public String getTXNCDE() {
        return TXNCDE;
    }

    public void setTXNCDE(String TXNCDE) {
        this.TXNCDE = TXNCDE;
    }

    public String getTELLER() {
        return TELLER;
    }

    public void setTELLER(String TELLER) {
        this.TELLER = TELLER;
    }

    public String getTXNDAT() {
        return TXNDAT;
    }

    public void setTXNDAT(String TXNDAT) {
        this.TXNDAT = TXNDAT;
    }

    public String getVCHSET() {
        return VCHSET;
    }

    public void setVCHSET(String VCHSET) {
        this.VCHSET = VCHSET;
    }

    public String getACTTY() {
        return ACTTY;
    }

    public void setACTTY(String ACTTY) {
        this.ACTTY = ACTTY;
    }

    public String getIPTAC() {
        return IPTAC;
    }

    public void setIPTAC(String IPTAC) {
        this.IPTAC = IPTAC;
    }

    public String getACTNAM() {
        return ACTNAM;
    }

    public void setACTNAM(String ACTNAM) {
        this.ACTNAM = ACTNAM;
    }

    public String getCCYNAM() {
        return CCYNAM;
    }

    public void setCCYNAM(String CCYNAM) {
        this.CCYNAM = CCYNAM;
    }

    public String getAPCDE1() {
        return APCDE1;
    }

    public void setAPCDE1(String APCDE1) {
        this.APCDE1 = APCDE1;
    }

    public String getTXNAMT1() {
        return TXNAMT1;
    }

    public void setTXNAMT1(String TXNAMT1) {
        this.TXNAMT1 = TXNAMT1;
    }

    public String getOPNIRT() {
        return OPNIRT;
    }

    public void setOPNIRT(String OPNIRT) {
        this.OPNIRT = OPNIRT;
    }

    public String getININT() {
        return ININT;
    }

    public void setININT(String ININT) {
        this.ININT = ININT;
    }

    public String getAPCDE2() {
        return APCDE2;
    }

    public void setAPCDE2(String APCDE2) {
        this.APCDE2 = APCDE2;
    }

    public String getTXNAMT2() {
        return TXNAMT2;
    }

    public void setTXNAMT2(String TXNAMT2) {
        this.TXNAMT2 = TXNAMT2;
    }

    public String getSAVIRT() {
        return SAVIRT;
    }

    public void setSAVIRT(String SAVIRT) {
        this.SAVIRT = SAVIRT;
    }

    public String getOUTINT() {
        return OUTINT;
    }

    public void setOUTINT(String OUTINT) {
        this.OUTINT = OUTINT;
    }

    public String getAPCDE3() {
        return APCDE3;
    }

    public void setAPCDE3(String APCDE3) {
        this.APCDE3 = APCDE3;
    }

    public String getTXNAMT3() {
        return TXNAMT3;
    }

    public void setTXNAMT3(String TXNAMT3) {
        this.TXNAMT3 = TXNAMT3;
    }

    public String getVALIRT() {
        return VALIRT;
    }

    public void setVALIRT(String VALIRT) {
        this.VALIRT = VALIRT;
    }

    public String getVALINT() {
        return VALINT;
    }

    public void setVALINT(String VALINT) {
        this.VALINT = VALINT;
    }

    public String getAPCDE4() {
        return APCDE4;
    }

    public void setAPCDE4(String APCDE4) {
        this.APCDE4 = APCDE4;
    }

    public String getTXNAMT4() {
        return TXNAMT4;
    }

    public void setTXNAMT4(String TXNAMT4) {
        this.TXNAMT4 = TXNAMT4;
    }

    public String getFEERAT() {
        return FEERAT;
    }

    public void setFEERAT(String FEERAT) {
        this.FEERAT = FEERAT;
    }

    public String getFEEAMT() {
        return FEEAMT;
    }

    public void setFEEAMT(String FEEAMT) {
        this.FEEAMT = FEEAMT;
    }

    public String getPIVINT1() {
        return PIVINT1;
    }

    public void setPIVINT1(String PIVINT1) {
        this.PIVINT1 = PIVINT1;
    }

    public String getPIVINT2() {
        return PIVINT2;
    }

    public void setPIVINT2(String PIVINT2) {
        this.PIVINT2 = PIVINT2;
    }

    public String getTAXRATE() {
        return TAXRATE;
    }

    public void setTAXRATE(String TAXRATE) {
        this.TAXRATE = TAXRATE;
    }

    public String getTAXAMT() {
        return TAXAMT;
    }

    public void setTAXAMT(String TAXAMT) {
        this.TAXAMT = TAXAMT;
    }

    public String getTOTINT() {
        return TOTINT;
    }

    public void setTOTINT(String TOTINT) {
        this.TOTINT = TOTINT;
    }

    public String getDATHED() {
        return DATHED;
    }

    public void setDATHED(String DATHED) {
        this.DATHED = DATHED;
    }

    public String getVALDAT() {
        return VALDAT;
    }

    public void setVALDAT(String VALDAT) {
        this.VALDAT = VALDAT;
    }

    public String getTOTAMT() {
        return TOTAMT;
    }

    public void setTOTAMT(String TOTAMT) {
        this.TOTAMT = TOTAMT;
    }
}
