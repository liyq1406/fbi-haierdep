package org.fbi.dep.model.txn;

import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.base.TIABody;
import org.fbi.dep.model.base.TIAHeader;

import java.io.Serializable;

/**
 * 银联：多笔批量查询交易结果
 * User: xiaobo
 */

public class TIA1202005 extends TIA implements Serializable {
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

    //====================================================================
    public static class Header extends TIAHeader {
    }

    public static class Body extends TIABody {
        public String MERCHANT_ID;                  // 商户代码
        public String QUERY_SN;                  // 要查询的交易流水号 *
        public String STATUS;                     // 状态  交易状态条件, 0成功,1失败, 2全部,3退票 *
        public String TYPE;                     // 查询类型
        public String START_DAY;                     // 开始日
        public String END_DAY;                     // 结束日

    }

}
