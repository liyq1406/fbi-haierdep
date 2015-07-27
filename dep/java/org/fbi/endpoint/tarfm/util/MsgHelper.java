package org.fbi.endpoint.tarfm.util;

import org.fbi.dep.model.base.TIA;
import org.fbi.dep.model.base.TOA;
import org.fbi.dep.model.txn.TIA1001001;
import org.fbi.dep.model.txn.TIA9901001;
import org.fbi.dep.model.txn.TOA9901001;
import org.jboss.netty.util.internal.StringUtil;

/**
 * Created by Lichao.W At 2015/7/22 13:33
 * wanglichao@163.com
 */
public class MsgHelper {

    //构建房产中心报文格式
    public String buildMsg(Object obj){
        String buildMsg ="";

        if (obj instanceof TIA9901001){
            String sendStr = getLeftSpaceStr("0532",6)+"|"+getLeftSpaceStr(((TIA9901001) obj).header.TX_CODE,20)+"|"+
                    getLeftSpaceStr(((TIA9901001) obj).header.APP_ID,2)+"|"+
                    getLeftSpaceStr(((TIA9901001) obj).header.REQ_SN,14)+"|"+"bbbbbbbbbboooooooody"+"|";
            return  sendStr;
        }
        return buildMsg;
    }

    //构建成rfm需要的bean
     public TOA buildObj(String rtnmsg,String txCode){
        if ("9901001".equals(txCode)) {
            TOA9901001 toa9901001 = new TOA9901001();
            toa9901001.body.PRE_SALE_PRO_NAME = "";
            return toa9901001;
        }
        return null;
    }

    //左对齐 不够的又补空格
    public String getLeftSpaceStr(String strValue, int totleBytesLen) {
        if(strValue == null) strValue = "";
        if (strValue.getBytes().length < totleBytesLen) {
            int spacelen = totleBytesLen - strValue.getBytes().length;
            for (int i = 0; i < spacelen; i++) {
                strValue += " ";
            }
        }else if (strValue.getBytes().length > totleBytesLen){
            throw new RuntimeException("bean转换成String长度超过规定长度！");
        }
        return strValue;
    }

    public String getStr(){
        String strVale = "";

        return strVale;
    }
}
