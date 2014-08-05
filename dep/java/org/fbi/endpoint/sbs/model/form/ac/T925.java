package org.fbi.endpoint.sbs.model.form.ac;

import org.fbi.endpoint.sbs.domain.AssembleModel;
import org.fbi.endpoint.sbs.domain.SOFFormBody;

import java.util.ArrayList;
import java.util.List;

/**
 *  8855 根据客户证件信息查询账户列表
 */
public class T925 extends SOFFormBody {

    private List<Bean> beanList = new ArrayList<Bean>();
    private FormBodyHeader formBodyHeader = new FormBodyHeader();

    @Override
    public void assembleFields(int offset, byte[] buffer) {
        formBodyHeader.assembleFields(offset,buffer);
        int index = offset + 72;
        int beanLength = 96;
        do {
            Bean bean = new Bean();
            bean.assembleFields(index, buffer);
            beanList.add(bean);
            index += beanLength;
        } while (index < buffer.length);
    }

    public class FormBodyHeader extends AssembleModel {
        {
            fieldTypes = new int[]{1, 1, 1};
            fieldLengths = new int[]{60, 6, 6};
        }

        private String CUSNAM;   // 户名
        private String TOTCNT;   //总记录数
        private String CURCNT;   //本包内记录数


        public String getCUSNAM() {
            return CUSNAM;
        }

        public void setCUSNAM(String CUSNAM) {
            this.CUSNAM = CUSNAM;
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
            fieldTypes = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
            fieldLengths = new int[]{18, 1, 17, 17, 10, 10, 2, 10, 10, 1};
        }

        private String CUSACT;          // 账号
        private String ACTTYP;          // 账户种类
        private String BOKBAL;          // 余额
        private String AVABAL;          // 可用余额
        private String OPNDAT;          // 开户日
        private String VALDAT;          // 起息日
        private String DPTPRD;          // 存期月数
        private String EXPDAT;          // 到期日
        private String INTRAT;          // 利率
        private String ACTSTS;          // 状态

        public String getCUSACT() {
            return CUSACT;
        }

        public void setCUSACT(String CUSACT) {
            this.CUSACT = CUSACT;
        }

        public String getACTTYP() {
            return ACTTYP;
        }

        public void setACTTYP(String ACTTYP) {
            this.ACTTYP = ACTTYP;
        }

        public String getBOKBAL() {
            return BOKBAL;
        }

        public void setBOKBAL(String BOKBAL) {
            this.BOKBAL = BOKBAL;
        }

        public String getAVABAL() {
            return AVABAL;
        }

        public void setAVABAL(String AVABAL) {
            this.AVABAL = AVABAL;
        }

        public String getOPNDAT() {
            return OPNDAT;
        }

        public void setOPNDAT(String OPNDAT) {
            this.OPNDAT = OPNDAT;
        }

        public String getVALDAT() {
            return VALDAT;
        }

        public void setVALDAT(String VALDAT) {
            this.VALDAT = VALDAT;
        }

        public String getDPTPRD() {
            return DPTPRD;
        }

        public void setDPTPRD(String DPTPRD) {
            this.DPTPRD = DPTPRD;
        }

        public String getEXPDAT() {
            return EXPDAT;
        }

        public void setEXPDAT(String EXPDAT) {
            this.EXPDAT = EXPDAT;
        }

        public String getINTRAT() {
            return INTRAT;
        }

        public void setINTRAT(String INTRAT) {
            this.INTRAT = INTRAT;
        }

        public String getACTSTS() {
            return ACTSTS;
        }

        public void setACTSTS(String ACTSTS) {
            this.ACTSTS = ACTSTS;
        }
    }

    public List<Bean> getBeanList() {
        return beanList;
    }

    public FormBodyHeader getFormBodyHeader() {
        return formBodyHeader;
    }
}
