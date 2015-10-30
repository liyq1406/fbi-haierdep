package org.fbi.dep.com;

import org.fbi.dep.enums.TxnStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class ToolUtil {

    private static final Logger logger = LoggerFactory.getLogger(ToolUtil.class);

    /**
     * @param strSbsRtnCodePara 从SBS返回的返回码
     * @return 经过DEP包装后的返回码
     */
    public static String SbsRtnCodeToDepRtnCode(String strTxnCodePara,String strSbsRtnCodePara) {
        String strDepRtnCodeTmp=null;
        String strTxnTypeTmp=null;

        // ------将交易区分类别------
        // 单笔内部转账,单笔内部转账再转账,账户余额查询,账户当日交易明细查询,账户历史交易明细查询,
        // 根据客户证件信息查询账户列表,根据客户证件信息查询账号列表,根据账号查询账户信息,
        // 同业到账通知交易,总分账户到账通知交易,同业到账通知对账交易,二代支付手续费入账,建立个人客户信息,贷款开户
        if(("9009001,9009004,9009050,9009051,9009052," +
            "9009060,9009061,9009062," +
                "9009501,9009502,9009503,9009504,9009505,9009506,9009507,9009508," +
            "9009101,9009102,9009103,9009201,9009301,9009401").indexOf(strTxnCodePara)>=0){
            strTxnTypeTmp="ONLY_SBS";
        }else
        // 单笔对外支付,银联单笔代收
        if("9009002,9009010".indexOf(strTxnCodePara)>=0){
            strTxnTypeTmp="SBS_TO_OUT_EXE";
        }else
        // 单笔对外支付结果查询
        if("9009003".indexOf(strTxnCodePara)>=0){
            strTxnTypeTmp="SBS_TO_OUT_QRY";
        }

        // ------根据交易类别转换DEP返回码------
        // SBS能够直接回应的
        if("ONLY_SBS".equals(strTxnTypeTmp)){
            if (strSbsRtnCodePara.startsWith("T")||
                    (strSbsRtnCodePara.startsWith("W"))) {
                strDepRtnCodeTmp=TxnStatus.TXN_SUCCESS.getCode();
            }else {
                strDepRtnCodeTmp=TxnStatus.TXN_FAILED.getCode();
            }
        }else
        // SBS不能够直接回应的 对外支付
        if("SBS_TO_OUT_EXE".equals(strTxnTypeTmp)) {
            ArrayList<String> failRtnCodeListTmp=new ArrayList<String>();
            failRtnCodeListTmp.add("F001");
            failRtnCodeListTmp.add("F032");
            failRtnCodeListTmp.add("F033");
            failRtnCodeListTmp.add("F034");
            failRtnCodeListTmp.add("F035");
            failRtnCodeListTmp.add("F036");
            failRtnCodeListTmp.add("F037");
            failRtnCodeListTmp.add("F038");
            failRtnCodeListTmp.add("F127");
            failRtnCodeListTmp.add("F705");
            failRtnCodeListTmp.add("F711");
            failRtnCodeListTmp.add("M057");
            failRtnCodeListTmp.add("M102");
            failRtnCodeListTmp.add("M103");
            failRtnCodeListTmp.add("M104");
            failRtnCodeListTmp.add("M108");
            failRtnCodeListTmp.add("M115");
            failRtnCodeListTmp.add("M259");
            failRtnCodeListTmp.add("M309");
            failRtnCodeListTmp.add("M310");
            failRtnCodeListTmp.add("M311");
            failRtnCodeListTmp.add("M313");
            failRtnCodeListTmp.add("M316");
            failRtnCodeListTmp.add("M409");
            failRtnCodeListTmp.add("M612");
            failRtnCodeListTmp.add("M767");
            failRtnCodeListTmp.add("M801");
            failRtnCodeListTmp.add("M802");
            failRtnCodeListTmp.add("M803");
            failRtnCodeListTmp.add("M808");
            failRtnCodeListTmp.add("M901");
            failRtnCodeListTmp.add("M923");
            failRtnCodeListTmp.add("M930");
            failRtnCodeListTmp.add("M931");
            failRtnCodeListTmp.add("M947");
            failRtnCodeListTmp.add("MH32");
            failRtnCodeListTmp.add("MZZZ");
            if (strSbsRtnCodePara.startsWith("T")) {
                strDepRtnCodeTmp = TxnStatus.TXN_SUCCESS.getCode();
            } else if (failRtnCodeListTmp.contains(strSbsRtnCodePara)) {
                strDepRtnCodeTmp = TxnStatus.TXN_FAILED.getCode();
            } else {
                strDepRtnCodeTmp = TxnStatus.TXN_QRY_PEND.getCode();
            }
        }else
        // SBS不能够直接回应的 对外结果查询
        if("SBS_TO_OUT_QRY".equals(strTxnTypeTmp)) {
            if (!strSbsRtnCodePara.startsWith("T")) {
                strDepRtnCodeTmp = TxnStatus.TXN_SUCCESS.getCode();
            } else if (strSbsRtnCodePara.equals("WB02")) {
                strDepRtnCodeTmp = TxnStatus.TXN_FAILED.getCode();
            } else {
                strDepRtnCodeTmp = TxnStatus.TXN_QRY_PEND.getCode();
            }
        }
        return strDepRtnCodeTmp;
    }
}

