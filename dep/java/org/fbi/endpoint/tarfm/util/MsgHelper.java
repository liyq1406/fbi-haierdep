package org.fbi.endpoint.tarfm.util;

import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.txn.TIA1001001;

/**
 * Created by Lichao.W At 2015/7/22 13:33
 * wanglichao@163.com
 */
public class MsgHelper {

    //构建房产中心报文格式
    public String buildMsg(String msg){
        String buildMsg ="";

        return buildMsg;
    }

    //左对齐 右补空格
    public String getLeftSpaceStr(String strValue, int totleBytesLen) {
        if(strValue == null) strValue = "";
        if (strValue.getBytes().length < totleBytesLen) {
            int spacelen = totleBytesLen - strValue.getBytes().length;
            for (int i = 0; i < spacelen; i++) {
                strValue += " ";
            }
        }
        return strValue;
    }
}
