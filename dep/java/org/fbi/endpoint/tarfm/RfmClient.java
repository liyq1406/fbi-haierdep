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
        byte[] byteBuffer = new byte[16];
        bis.read(byteBuffer);
        String lengthStr = new String(byteBuffer, 0, 16);
        logger.info("报文正文长度: " + lengthStr);
        int strLength = 256;
        byte[] strBytes = new byte[strLength];
        bis.read(strBytes);
        String resDatagram = new String(byteBuffer) + new String(strBytes);
        is.close();
        bis.close();
        socket.close();
        long endTime = System.currentTimeMillis();
        logger.info("耗时：" + (endTime - startTime));
        return resDatagram;
    }

    public static void main(String[] args) throws Exception{
        String reqmsg = "123456|9988|555555     |8899|";
        String rtnmsg = new RfmClient().processTxn(reqmsg);
        System.out.println(rtnmsg);
    }
}
