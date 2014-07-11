package org.fbi.dep.model.txn;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.fbi.dep.model.base.TiaXml;
import org.fbi.dep.model.base.TiaXmlInfo;

import java.io.Serializable;

/**
 * SBS同业到账通知
 */

@XStreamAlias("ROOT")
public class TiaXml9009101 extends TiaXml {
    public TiaXmlInfo INFO;
    public Body BODY;

    public static class Body implements Serializable {

        public String BANKSN;        // 银行流水号	C(16)	超过16位，保留后16位	可空
        public String BNKDAT;        // BNKDAT	委托日期	C(8)	YYYYMMDD 银行交易日期	非空
        public String BNKTIM;        // BNKTIM	委托时间	C(6)	hhmmss   银行交易时间	可空

        public String PBKNUM;        // 付款行行号	C(12)		可空
        public String PBKNAM;        // 付款行名称	C(80)		可空
        public String PAYACT;        // 付款方帐号	C(40)		可空
        public String PAYNAM;        // 付款方户名	C(150)		可空

        public String IBKACT;        // 同业账号	C(22)		非空
        public String IBKNUM;        // 同业行行号	C(12)		非空
        public String IBKNAM;        // 同业行名称	C(80)		可空
        public String RETAUX;        // 用途	C(150)		可空

        public String TXNAMT;        // 交易金额	S9(13).99	S表示正负号，占一位	非空
        public String DCTYPE;        // 借贷标志	C(1)	D-借，C-贷     非空

        public String RECACT;        // 收款方帐号	C(35)		非空
        public String RECNAM;        // 收款方户名	C(150)		可空

    }

    @Override
    public TiaXml getTia(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(TiaXml9009101.class);
        return (TiaXml9009101) xs.fromXML(xml);
    }
}
