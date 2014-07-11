import org.fbi.dep.helper.MD5Helper;
import org.fbi.dep.util.StringPad;

import java.io.*;
import java.net.Socket;

public class SbsSktXml9009101 {
    public static void main(String[] args) {
        try {
            String xmlmsg = "<?xml version=\"1.0\" encoding=\"GBK\"?>" +
                    "<ROOT>" +
                    "<INFO>" +
                    "<TXN_CODE>9009101</TXN_CODE>" +
                    "<REQ_SN>SCF0000324292321</REQ_SN>" +
                    "</INFO>" +
                    "<BODY>" +
                    "<BANKSN>SN9010101010</BANKSN>" +
                    "<BNKDAT>20140711</BNKDAT>" +
                    "<BNKTIM>141401</BNKTIM>" +
                    "<PBKNUM>105</PBKNUM>" +
                    "<PBKNAM>建行</PBKNAM>" +
                    "<PAYACT>60089976543290</PAYACT>" +
                    "<PAYNAM>张三</PAYNAM>" +
                    "<TXNAMT>50</TXNAMT>" +
                    "<DCTYPE>C</DCTYPE>" +

                    "<RECACT>801000000902012001</RECACT>" +
                    "<RECNAM>青岛海尔工贸有限公司</RECNAM>" +
                    "<IBKACT>37101985510059888888</IBKACT>" +
                    "<IBKNUM>105</IBKNUM>" +
                    "<IBKNAM>建行</IBKNAM>" +
                    "<RETAUX>内转</RETAUX>" +
                    "</BODY>" +
                    "</ROOT>";
            String mac = MD5Helper.getMD5String(xmlmsg + "20140711SCFAPP001SCF20140711001");
            String reqmsg = "1.00SCFAPP001 9009101   201407111450050000" +
                    StringPad.rightPad4ChineseToByteLength("系统响应状态信息", 20, " ")
                    + mac + xmlmsg;
            int length = reqmsg.getBytes().length + 8;
            System.out.println("【本地客户端】发送报文总长度：" + length);
            String message = appendStrToLength(String.valueOf(length), " ", 8) + reqmsg;
            System.out.println("发送报文：" + message);
            Socket socket = new Socket("10.143.20.15", 62006);
//            Socket socket = new Socket("127.0.0.1", 62006);
            socket.setSoTimeout(10000);
            OutputStream os = socket.getOutputStream();
            os.write(message.getBytes());
            os.flush();

            InputStream is = socket.getInputStream();
            byte[] bytes = readBytesFromInputStream(is);
            System.out.println("返回报文：" + new String(bytes));
            os.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] readBytesFromInputStream(InputStream is) throws IOException {
        if (is != null) {
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] byteBuffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = 0;
            while ((len = bis.read(byteBuffer)) != -1) {
                baos.write(byteBuffer, 0, len);
            }
            baos.flush();
            bis.close();
            is.close();
            return baos.toByteArray();
        } else
            return null;
    }

    public static String appendStrToLength(String srcStr, String appendStr, int length) {
        int appendLength = length - srcStr.getBytes().length;
        StringBuilder strBuilder = new StringBuilder(srcStr);
        for (int i = 1; i <= appendLength; i++) {
            strBuilder.append(appendStr);
        }
        return strBuilder.toString();
    }
}
