import chinapay.Base64;
import chinapay.PrivateKey;
import chinapay.SecureLink;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.fbi.dep.helper.ChinapayShTxnHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2014-06-26.
 */
public class ChinapaySingleClientTest {

    static final String MerId = ChinapayShTxnHandler.SINGLE_MER_ID;

    public static void main(String[] args) {

        // TODO 测试
        singleCut();
//        singleQry();
    }

    public static void singleCut() {

        String transDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String orderNo = "3310100010096803";
        String transType = "0003";
        String openBankId = "0105";
        String cardType = "0";

        String cardNo = "622566776666323280";
        String usrName = encodeToUnicode("张三"); // unicode

        String certType = "01";
        String certId = "370202198601011118";
        String curyId = "156";
        String transAmt = "000000000100";
        String priv1 = encodeToUnicode("yuliu");
        String version = "20100831";
        String gateId = "7008";
        String msg = ChinapayShTxnHandler.SINGLE_MER_ID + transDate + orderNo + transType + openBankId + cardType
                + cardNo + usrName + certType + certId + curyId + transAmt + priv1 + version + gateId;
        System.out.println("要签名的数据：" + msg);


        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("merId", ChinapayShTxnHandler.SINGLE_MER_ID));
        nvps.add(new BasicNameValuePair("transDate", transDate));
        nvps.add(new BasicNameValuePair("orderNo", orderNo));
        nvps.add(new BasicNameValuePair("transType", transType));
        nvps.add(new BasicNameValuePair("openBankId", openBankId));
        nvps.add(new BasicNameValuePair("cardType", cardType));
        nvps.add(new BasicNameValuePair("cardNo", cardNo));
        nvps.add(new BasicNameValuePair("usrName", usrName));
        nvps.add(new BasicNameValuePair("certType", certType));
        nvps.add(new BasicNameValuePair("certId", certId));
        nvps.add(new BasicNameValuePair("curyId", curyId));

        nvps.add(new BasicNameValuePair("transAmt", transAmt));
        nvps.add(new BasicNameValuePair("purpose", "purpose"));
        nvps.add(new BasicNameValuePair("priv1", priv1));
        nvps.add(new BasicNameValuePair("version", version));
        nvps.add(new BasicNameValuePair("gateId", gateId));

        try {
            Map<String, String> response = new ChinapayShTxnHandler().singleCut(msg, nvps);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void singleQry() {
        String transType = "0003";
        String orderNo = "3310100010096802";
        String transDate = new SimpleDateFormat("yyyyMMdd").format(new Date());;
        String version = "20100831";
        String msg = MerId + transType + orderNo + transDate + version;

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("merId", ChinapayShTxnHandler.SINGLE_MER_ID));
        nvps.add(new BasicNameValuePair("transType", transType));
        nvps.add(new BasicNameValuePair("transDate", transDate));
        nvps.add(new BasicNameValuePair("orderNo", orderNo));
        nvps.add(new BasicNameValuePair("version", version));
        nvps.add(new BasicNameValuePair("version", version));
        nvps.add(new BasicNameValuePair("priv1", "priv1"));
        try {
            Map<String, String> response = new ChinapayShTxnHandler().singleQry(msg, nvps);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String encodeToUnicode(String str) {
        str = (str == null ? "" : str);
        String tmp;
        StringBuffer sb = new StringBuffer(1000);
        char c;
        int i, j;
        sb.setLength(0);
        for (i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            sb.append("\\u");
            j = (c >>> 8); //取出高8位
            tmp = Integer.toHexString(j);
            if (tmp.length() == 1)
                sb.append("0");
            sb.append(tmp);
            j = (c & 0xFF); //取出低8位
            tmp = Integer.toHexString(j);
            if (tmp.length() == 1)
                sb.append("0");
            sb.append(tmp);

        }
        return (new String(sb));
    }

    public static String decodeToStr(String str) {
        str = (str == null ? "" : str);
        if (str.indexOf("\\u") == -1)//如果不是unicode码则原样返回
            return str;

        StringBuffer sb = new StringBuffer(1000);

        for (int i = 0; i < str.length() - 6; ) {
            String strTemp = str.substring(i, i + 6);
            String value = strTemp.substring(2);
            int c = 0;
            for (int j = 0; j < value.length(); j++) {
                char tempChar = value.charAt(j);
                int t = 0;
                switch (tempChar) {
                    case 'a':
                        t = 10;
                        break;
                    case 'b':
                        t = 11;
                        break;
                    case 'c':
                        t = 12;
                        break;
                    case 'd':
                        t = 13;
                        break;
                    case 'e':
                        t = 14;
                        break;
                    case 'f':
                        t = 15;
                        break;
                    default:
                        t = tempChar - 48;
                        break;
                }

                c += t * ((int) Math.pow(16, (value.length() - j - 1)));
            }
            sb.append((char) c);
            i = i + 6;
        }
        return sb.toString();
    }
}
