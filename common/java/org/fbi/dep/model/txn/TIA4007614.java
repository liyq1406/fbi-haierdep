package org.fbi.dep.model.txn;

import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.base.TIABody;
import org.fbi.dep.model.base.TIAHeader;

/**
 * Created by XIANGYANG on 2015-9-9.
 * 总分账户到账通知（7614）
 */

public class TIA4007614 extends TIA {
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
        public String bankSerial;      //银行流水号
        public String tradeDate;       //建行交易日期
        public String payAccount;      //付款方账号
        public String payAccountName;  //付款方户名
        public String apAmount;        //交易金额
        public String toCompanyCode;   //收款公司编号
        public String recCompanyName;  //收款人姓名
        public String recAccount;      //虚拟户对应内部账号
        public String voucherFlag;     //是否工厂标志
        public String purpose;         //打款方式
        public String dcFlag;          //借贷标志
    }
}
