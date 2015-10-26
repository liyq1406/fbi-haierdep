package org.fbi.endpoint.sbs.model.form.re;

import org.fbi.endpoint.sbs.domain.SOFFormBody;

/**
 * SBS返回M开头的错误信息
 */
public class M000 extends SOFFormBody {

    {
        fieldTypes = new int[]{1};
        //fieldLengths = new int[]{99};
    }

    public String getRTNMSG() {
        return RTNMSG;
    }

    public void setRTNMSG(String RTNMSG) {
        this.RTNMSG = RTNMSG;
    }

    private String RTNMSG;       // 返回消息
}
