package org.fbi.endpoint.sbs.model.form.re;

import org.fbi.endpoint.sbs.domain.AssembleModel;
import org.fbi.endpoint.sbs.domain.SOFFormBody;

import java.util.ArrayList;
import java.util.List;

/**
 *  a113 通知存款通知信息查询
 */
public class T555 extends SOFFormBody {

    private List<Bean> beanList = new ArrayList<Bean>();
    private FormBodyHeader formBodyHeader = new FormBodyHeader();

    @Override
    public void assembleFields(int offset, byte[] buffer) {
        formBodyHeader.assembleFields(offset,buffer);
        int index = offset + 12;
        int beanLength = 171;
        do {
            Bean bean = new Bean();
            bean.assembleFields(index, buffer);
            beanList.add(bean);
            index += beanLength;
        } while (index < buffer.length);
    }

    public class FormBodyHeader extends AssembleModel {
        {
            fieldTypes = new int[]{1, 1};
            fieldLengths = new int[]{6, 6};
        }

        private String TOTCNT;   //总记录数
        private String CURCNT;   //本包内记录数


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
            fieldTypes = new int[]{ 1, 1, 1, 1, 1, 1, 1, 1, 1 };
            fieldLengths = new int[]{ 18, 18, 16, 10, 16, 10, 1, 10, 72 };
        }

        private String BOKNUM; // 账户号
        private String ACTNUM; // 账号
        private String ADVNAM; // 通知单号
        private String ADVDAT; // 通知日期
        private String ADVAMT; // 通知金额
        private String DCDDAT; // 协定提款日
        private String ADVFLG; // 通知状态
        private String CNCDAT; // 撤销通知日期
        private String ACTNAM; // 户名

        public String getBOKNUM() {
            return BOKNUM;
        }

        public void setBOKNUM(String BOKNUM) {
            this.BOKNUM = BOKNUM;
        }

        public String getACTNUM() {
            return ACTNUM;
        }

        public void setACTNUM(String ACTNUM) {
            this.ACTNUM = ACTNUM;
        }

        public String getADVNAM() {
            return ADVNAM;
        }

        public void setADVNAM(String ADVNAM) {
            this.ADVNAM = ADVNAM;
        }

        public String getADVDAT() {
            return ADVDAT;
        }

        public void setADVDAT(String ADVDAT) {
            this.ADVDAT = ADVDAT;
        }

        public String getADVAMT() {
            return ADVAMT;
        }

        public void setADVAMT(String ADVAMT) {
            this.ADVAMT = ADVAMT;
        }

        public String getDCDDAT() {
            return DCDDAT;
        }

        public void setDCDDAT(String DCDDAT) {
            this.DCDDAT = DCDDAT;
        }

        public String getADVFLG() {
            return ADVFLG;
        }

        public void setADVFLG(String ADVFLG) {
            this.ADVFLG = ADVFLG;
        }

        public String getCNCDAT() {
            return CNCDAT;
        }

        public void setCNCDAT(String CNCDAT) {
            this.CNCDAT = CNCDAT;
        }

        public String getACTNAM() {
            return ACTNAM;
        }

        public void setACTNAM(String ACTNAM) {
            this.ACTNAM = ACTNAM;
        }
    }

    public List<Bean> getBeanList() {
        return beanList;
    }

    public FormBodyHeader getFormBodyHeader() {
        return formBodyHeader;
    }
}
