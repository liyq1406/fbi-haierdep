package org.fbi.dep.model.txn;

import org.fbi.dep.model.base.TOA;
import org.fbi.dep.model.base.TOABody;
import org.fbi.dep.model.base.TOAHeader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * SBS: 账户交易明细查询
 * User: zhanrui
 * Date: 2012-08-08
 */
public class TOA9008853 extends TOA implements Serializable {
    public  Header header = new Header();
    public  Body body = new Body();

    @Override
    public TOAHeader getHeader() {
        return  header;
    }

    @Override
    public TOABody getBody() {
        return  body;
    }

    //====================================================================
    public  static class Header extends TOAHeader {
    }

    public static class Body extends TOABody {
        public String FLOFLG;          //后续报文标识 X(1)  0-无后续包，1-有后续包
        public String TOTCNT;          //总记录数 X(6)   满6位前补0
        public String CURCNT;          //本包内记录数 X(6)   满6位前补0
        public List<BodyDetail> RET_DETAILS = new ArrayList<BodyDetail>();

        //这些记录最多重复循环150次。
        //假设后续标识为0，则本包中最多150条记录；假设后续标识为1，则本包肯定为150条记录
        public static class BodyDetail implements Serializable{
            public String CUSIDT;      //客户号   X(7)
            public String APCODE;      //核算码   X(4)
            public String CURCDE;      //货币号   X(3)
            public String STMDAT;      //交易日期  X(8) YYYYMMDD
            public String TLRNUM;      //交易柜员  X(4)
            public String VCHSET;      //传票套号  X(4)   满4位前补0
            public String SETSEQ;      //传票套内顺序号 X(2) 满2位前补0
            public String TXNAMT;      //交易金额     X(21)   右对齐，左补空格 -Z,ZZZ,ZZZ,ZZZ,ZZ9.99
            public String LASBAL;      //交易后余额   X(21)   右对齐，左补空格 -Z,ZZZ,ZZZ,ZZZ,ZZ9.99
            public String FURINF;      //摘要   X(32)
        }
    }
}
