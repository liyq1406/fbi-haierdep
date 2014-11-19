import org.fbi.dep.helper.MD5Helper;
import org.fbi.dep.util.StringPad;

import java.io.*;
import java.net.Socket;

/**
 * Created by lenovo on 2014-11-11.
 */
public class SmsDepTest {
    public static void main(String[] args) {
        try {
            String xmlmsg = "001115854897347|人生本无定数， 回首已是天涯。";
            int length = xmlmsg.getBytes().length + 8;
            System.out.println("【本地客户端】发送报文总长度：" + length);
            String message = appendStrToLength(String.valueOf(length), " ", 8) + xmlmsg;
            System.out.println("发送报文：" + message);
//            Socket socket = new Socket("127.0.0.1", 61002);
            Socket socket = new Socket("10.143.18.20", 61002);
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
