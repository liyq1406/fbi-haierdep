package org.fbi.endpoint.tarfm;

import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

/**
 * Created by Lichao.W At 2015/7/22 11:44
 * wanglichao@163.com
 */
public class RfmClient {
    private static Logger logger = LoggerFactory.getLogger(RfmClient.class);

    public static final String HOSTIP = PropertyManager.getProperty("TARFM_HOSTIP");
    public static final int HOSTPORT = PropertyManager.getIntProperty("TARFM_HOSTPORT");
    public static final int TIMEOUT = PropertyManager.getIntProperty("TARFM_TIMEOUT");

    public String processTxn(String datagram) throws Exception {
        long startTime = System.currentTimeMillis();
        Socket socket = new Socket(HOSTIP, HOSTPORT);
        socket.setKeepAlive(true);
        socket.setSoTimeout(TIMEOUT);
        OutputStream os = socket.getOutputStream();
        os.write(datagram.getBytes("GB2312"));
        os.flush();

        InputStream is = socket.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);

        byte[] headerBytes = new byte[10];
        is.read(headerBytes);
        byte[] lengthBytes = new byte[6];
        System.arraycopy(headerBytes, 0, lengthBytes, 0, 6);
        int strLength = Integer.parseInt(new String(lengthBytes).trim());
        logger.info("RFM报文正文长度: " + strLength);
        byte[] strBytes = new byte[strLength];
        int available = 0;
        int readIndex = 0;

        while (readIndex < strLength) {
            int toRead = 0;
            available = is.available();
            if (available == 0) continue;
            if (strLength - readIndex >= available) {
                toRead = available;
            } else {
                toRead = strLength - readIndex;
            }
//            logger.info("toRead:" + toRead);
            byte[] buf = new byte[toRead];
            is.read(buf);
            System.arraycopy(buf, 0, strBytes, readIndex, buf.length);
            readIndex += toRead;
        }
        String resDatagram = new String(headerBytes) + new String(strBytes);
        is.close();
        bis.close();
        socket.close();
        long endTime = System.currentTimeMillis();
        logger.info("耗时：" + (endTime - startTime)+"ms");
        return resDatagram;
    }

    public static void main(String[] args) throws Exception{
        String reqmsg = "123456|9988|555555     |汉字会不会乱码99|";
        String rtnmsg = new RfmClient().processTxn(reqmsg);
        System.out.println("房产中心返回报文："+rtnmsg);
    }
}
