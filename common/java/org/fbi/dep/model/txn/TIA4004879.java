package org.fbi.dep.model.txn;

import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.base.TIABody;
import org.fbi.dep.model.base.TIAHeader;

/**
 * Created by XIANGYANG on 2015-9-9.
 * µ½ÕËÍ¨Öª£¨4879£©
 */

public class TIA4004879 extends TIA {
    public Header header = new Header();
    public Body body = new Body();

    @Override
    public TIAHeader getHeader() {
        return header;
    }

    @Override
    public TIABody getBody() {
        return body;
    }

    public static class Header extends TIAHeader {

    }

    public static class Body extends TIABody {
        public String Version;
        public String TxCode;
        public String FuncCode;
        public String Channel;
        public String SubCenterId;
        public String NodeId;
        public String TellerId;
        public String TxSeqId;
        public String TxDate1;
        public String TxTime1;
        public String UserId;

        public String OperatorUserId;
        public String RecCount;
        public String AcctId;

        public String AcctHostSeqId;
        public String TxDate2;
        public String OutAcctId;
        public String OutAcctName;
        public String OutBranchName;
        public String InAcctId;
        public String InAcctName;
        public String InBranchName;
        public String TxAmount;
        public String AcctBal;
        public String AvBal;
        public String CAcctBal;
        public String CTxAmount;
        public String CurCode;
        public String DCFlag;
        public String AbstractStr;
        public String TxTime2;
        public String VoucherId;
        public String VoucherType;
        public String CoSeqId;
        public String Abstract;
        public String BankNodeId;
        public String CCBSTellerId;
        public String BankIndex;
        public String OutComAcctId;
        public String OutComName;
        public String Reserve1;
        public String Reserve2;
    }
}
