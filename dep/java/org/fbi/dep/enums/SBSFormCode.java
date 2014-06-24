package org.fbi.dep.enums;

import java.util.Hashtable;

public enum SBSFormCode implements EnumApp {
    F705("F705", "����ϵͳ��δ����"),
    T999("T999", "�����ظ�"),
    M612("M612", "ҵ�����ڲ���"),
    M311("M311", "�������������д�"),
    M103("M103", "���ʻ�������"),
    M309("M309", "�˻�����"),
    M104("M104", "��¼������"),
    MZZZ("MZZZ", "���׽������"),
    OTHER("9999", "��������"),

    SERVER_EXCEPTION("9000", "�������쳣");

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
