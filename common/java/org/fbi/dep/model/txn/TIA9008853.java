package org.fbi.dep.model.txn;

import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.base.TIABody;
import org.fbi.dep.model.base.TIAHeader;

import java.io.Serializable;

/**
 * SBS: 账户交易明细查询
 * User: zhanrui
 * Date: 2012-02-14
 */

public class TIA9008853 extends TIA implements Serializable {
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
        public String BATSEQ = "111111";    //批号 固定值
        public String ORGIDT = "010";       //柜员机构号 固定值
        public String DEPNUM = "60";        //柜员部门号 固定值
        public String ORGID3 = "010";       //帐户机构号 固定值
        public String ACTNUM = "";          //帐户帐号   X(22)
        public String BEGDAT = "";          //起始交易日期   格式：YYYYMMDD
        public String ENDDAT = "";          //终止交易日期   格式：YYYYMMDD
        public String BEGNUM = "";          //起始笔数  X(6)  右对齐，左补0
                                            //注意：该交易需做多次请求-应答的交互：
                                            //第一次填0，第n次填写累计n-1次实际收到的记录数+1
                                            //比如，假设总共可查询出123笔明细，并且一个报文最多容纳20笔
                                            //第一次请求：填写000000，收到应答，收到2笔：01-20
                                            //第二次请求：填写000021，收到应答，收到20笔：21-40
                                            //最后一次请求：填写000121，收到应答，收到3笔：121-123
    }
}
