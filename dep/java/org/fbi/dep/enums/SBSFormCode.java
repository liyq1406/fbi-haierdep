package org.fbi.dep.enums;

import java.util.Hashtable;

public enum SBSFormCode implements EnumApp {
    F705("F705", "主机系统尚未开启"),
    T999("T999", "交易重复"),
    M612("M612", "业务日期不符"),
    M311("M311", "交易资料输入有错"),
    M103("M103", "该帐户不存在"),
    M309("M309", "账户余额不足"),
    M104("M104", "记录不存在"),
    MZZZ("MZZZ", "交易结果不明"),
    OTHER("9999", "其它错误"),

    SERVER_EXCEPTION("9000", "服务器异常");

    private String code = null;
    private String title = null;
    private static Hashtable<String, SBSFormCode> aliasEnums;

    SBSFormCode(String code, String title) {
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

    public static SBSFormCode valueOfAlias(String alias) {
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
