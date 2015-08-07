package org.fbi.dep.enums;

import java.util.Hashtable;

 /*
    6001 SBS返回：主机系统尚未开启。[F705]
    6002 SBS返回：业务日期不符。[M612]
    6003 SBS返回：交易资料输入有错。[M311]
    6004 SBS返回：交易重复。[T999]
    6005 SBS返回：该帐户不存在。[M103]
    6006 SBS返回：账户余额不足。[M309]
    6007 SBS返回：记录不存在。[M104]
    6008 SBS返回：交易结果不明。[MZZZ]
    6999 SBS返回：其它错误。[…]
     */
public enum EnuSysTypeCode implements EnumApp {
     UNIONPAY("100", "银联"),
     ALLINPAY("120", "通联"),
     SBS("900", "SBS核心记账系统"),
     FIP("910", "FIP资金交换平台"),
     TA_FDC("990", "泰安房地产资金监管系统");

    private String code = null;
    private String title = null;
    private static Hashtable<String, EnuSysTypeCode> aliasEnums;

    EnuSysTypeCode(String code, String title) {
        this.init(code, title);
    }

    @SuppressWarnings("unchecked")
    private void init(String code, String title) {
        this.code = code;
        this.title = title;
        synchronized (this.getClass()) {
            if (aliasEnums == null) {
                aliasEnums = new Hashtable();
            }
        }
        aliasEnums.put(code, this);
        aliasEnums.put(title, this);
    }

    public static EnuSysTypeCode valueOfAlias(String alias) {
        return aliasEnums.get(alias);
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public String toRtnMsg() {
        return this.code + "|" + this.title;
    }
}
