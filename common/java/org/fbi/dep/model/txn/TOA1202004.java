package org.fbi.dep.model.txn;

import org.fbi.dep.model.base.TOA;
import org.fbi.dep.model.base.TOABody;
import org.fbi.dep.model.base.TOAHeader;

import java.io.Serializable;

/**
 * 通联：实时查询交易结果
 * User: wanglichao
 * Date: 2015-06-25
 */
public class TOA1202004 extends TOA implements Serializable {
    public Header header = new Header();
    public Body body = new Body();

    @Override
    public TOAHeader getHeader() {
        return header;
    }

    @Override
    public TOABody getBody() {
        return body;
    }

    //===================================================================
    public static class Header extends TOAHeader {
    }

    public static class Body extends TOABody {
        public String BATCHID;
        public String SN;
        public String TRXDIR;
        public String SETTDAY;
        public String FINTIME;
        public String SUBMITTIME;
        public String ACCOUNT_NO;
        public String ACCOUNT_NAME;
        public String AMOUNT;
        public String CUST_USERID;
        public String REMARK;
        public String RET_CODE;
        public String ERR_MSG;
    }
}
