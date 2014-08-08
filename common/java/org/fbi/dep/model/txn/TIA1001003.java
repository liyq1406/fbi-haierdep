package org.fbi.dep.model.txn;

import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.base.TIABody;
import org.fbi.dep.model.base.TIAHeader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ������������ʴ���
 */

public class TIA1001003 extends TIA {
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
        public BodyHeader TRANS_SUM = new BodyHeader();
        public List<BodyDetail> TRANS_DETAILS = new ArrayList<BodyDetail>();

        public static class BodyHeader implements Serializable {
            public String TOTAL_ITEM = "1";
            public String TOTAL_SUM = "000";
        }

        public static class BodyDetail implements Serializable {
            public String SN = "";
            public String E_USER_CODE = "";
            public String BANK_CODE = "";
            public String ACCOUNT_TYPE = "";
            public String ACCOUNT_NO = "";
            public String ACCOUNT_NAME = "";
            public String PROVINCE = "";
            public String CITY = "";
            public String BANK_NAME = "";

            public String ACCOUNT_PROP = "0";   // 0˽�ˣ�1��˾������ʱ��Ĭ��Ϊ˽��0��
            public String AMOUNT = "";          // ��λ:Ԫ
            public String CURRENCY = "";        // �������� ����ң�CNY, ��Ԫ��HKD����Ԫ��USD������ʱ��Ĭ��Ϊ����ҡ�
            public String PROTOCOL = "";
            public String PROTOCOL_USERID = "";
            public String ID_TYPE = "";
            public String ID = "";
            public String TEL = "";
            public String RECKON_ACCOUNT = "";   // �̻�����Ҫ��һ��Ľ������㵽��ͬ�˻�ʱ��д
            public String CUST_USERID = "";
            public String REMARK = "";
            public String RESERVE1 = "";
            public String RESERVE2 = "";
        }
    }
}
