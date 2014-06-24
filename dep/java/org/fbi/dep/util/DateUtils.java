package org.fbi.dep.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-11-16
 * Time: 20:58:24
 * To change this template use File | Settings | File Templates.
 */
public class DateUtils {

    static private SimpleDateFormat sdfdatetime14 = new SimpleDateFormat("yyyyMMddHHmmss");
    static private SimpleDateFormat sdfdatetime18 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static private SimpleDateFormat sdfdate8 = new SimpleDateFormat("yyyyMMdd");
    static private SimpleDateFormat sdftime6 = new SimpleDateFormat("HHmmss");

    public static String getDatetime14() {
        return sdfdatetime14.format(new Date());
    }

    public static String getDatetime18() {
        return sdfdatetime18.format(new Date());
    }

    public static String getDate8() {
        return sdfdate8.format(new Date());
    }

    public static String getTime6() {
        return sdftime6.format(new Date());
    }
}
