package org.fbi.dep.component.jms;

import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class JmsRfmSktClient {
    private static Logger logger = LoggerFactory.getLogger(JmsRfmSktClient.class);

    public static final String HOSTIP = PropertyManager.getProperty("socket.rfm.ip");
    public static final int HOSTPORT = PropertyManager.getIntProperty("socket.rfm.port");
    public static final int TIMEOUT = PropertyManager.getIntProperty("socket.rfm.timeout");

    public String processTxn(String datagram) throws Exception {
        // 开始执行时间
        long startTime = System.currentTimeMillis();
        // 定义SOCKET
        Socket socket = new Socket(HOSTIP, HOSTPORT);
        socket.setKeepAlive(true);
        socket.setSoTimeout(TIMEOUT);
        OutputStream os = socket.getOutputStream();
        // 发送数据包
        os.write(datagram.getBytes("GB2312"));
        os.flush();

        // 接收返回的数据
        InputStream is = socket.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        // 输入流读取字节
        byte[] msgBytes = new byte[5000];
        bis.read(msgBytes);
        String resDatagram = new String(msgBytes);
        is.close();
        bis.close();
        socket.close();
        long endTime = System.currentTimeMillis();
        logger.info("耗时：" + (endTime - startTime)+"ms");
        return resDatagram;
    }

    public static void main(String[] args) throws Exception{
        String reqmsg = "0000350000|9988|555555     |汉字会不会乱码99|";
        String rtnmsg = new JmsRfmSktClient().processTxn(reqmsg);
        System.out.println("房产中心返回报文："+rtnmsg);
    }
}
