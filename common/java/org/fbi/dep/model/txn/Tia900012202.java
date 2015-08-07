package org.fbi.dep.model.txn;

import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.base.TIABody;
import org.fbi.dep.model.base.TIAHeader;

import java.io.Serializable;

/**
 * 泰安房产资金监管：返还记账到SBS
 * User: hanjianlong
 * Date: 2015-07-16
 */

public class Tia900012202 extends TIA implements Serializable {
    public  Header header = new Header();
    public  Body body = new Body();

    @Override
    public TIAHeader getHeader() {
        return  header;
    }

    @Override
    public TIABody getBody() {
        return  body;
    }

    //====================================================================
    public  static class Header extends TIAHeader {
    }

    public static class Body extends TIABody {
        /*
          01    外围系统流水号
          02    付款账号
          03	交易日期
          04	交易时间
          05    收款账号
          06	交易金额*/
        public String ACTNUM; // 付款账号
        public String STMDAT; // 交易日期
        public String ERYTIM; // 交易时间
        public String BENACT; // 收款账号
        public String TXNAMT; // 交易金额
    }
}
