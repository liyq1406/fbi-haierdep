package org.fbi.endpoint.sbs.model.form.ac;

import org.fbi.endpoint.sbs.domain.SOFFormBody;

/**
 *  对应SBS-n022交易
 */
public class WB02 extends SOFFormBody {

    {
        fieldTypes = new int[]{1, 1};
        fieldLengths = new int[]{18, 72};
    }

    private String FBTIDX;	// 外围系统流水号
    private String RTNMSG;   // 银行返回信息

    public String getFBTIDX() {
        return FBTIDX;
    }

    public void setFBTIDX(String FBTIDX) {
        this.FBTIDX = FBTIDX;
    }

    public String getRTNMSG() {
        return RTNMSG;
    }

    public void setRTNMSG(String RTNMSG) {
        this.RTNMSG = RTNMSG;
    }
}
