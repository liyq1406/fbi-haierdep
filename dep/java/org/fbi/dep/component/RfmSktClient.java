package org.fbi.dep.component;

import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

/**
 * Created by Lichao.W At 2015/7/22 11:44
 * wanglichao@163.com
 */
public class RfmSktClient {
    private static Logger logger = LoggerFactory.getLogger(RfmSktClient.class);

    public static final String HOSTIP = PropertyManager.getProperty("socket.rfm.ip");
    public static final int HOSTPORT = PropertyManager.getIntProperty("socket.rfm.port");
    public static final int TIMEOUT = PropertyManager.getIntProperty("socket.rfm.timeout");

    public String processTxn(String datagram) throws Exception {
        // ��ʼִ��ʱ��
        long startTime = System.currentTimeMillis();
        // ����SOCKET
        Socket socket = new Socket(HOSTIP, HOSTPORT);
        socket.setKeepAlive(true);
        socket.setSoTimeout(TIMEOUT);
        OutputStream os = socket.getOutputStream();
        // �������ݰ�
        os.write(datagram.getBytes("GB2312"));
        os.flush();

        // ���շ��ص�����
        InputStream is = socket.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        // ��������ȡ�ֽ�
        byte[] msgBytes = new byte[5000];
        bis.read(msgBytes);
        String resDatagram = new String(msgBytes);
        is.close();
        bis.close();
        socket.close();
        long endTime = System.currentTimeMillis();
        logger.info("��ʱ��" + (endTime - startTime)+"ms");
        return resDatagram;
    }

    public static void main(String[] args) throws Exception{
        String reqmsg = "0000350000|9988|555555     |���ֻ᲻������99|";
        String rtnmsg = new RfmSktClient().processTxn(reqmsg);
        System.out.println("�������ķ��ر��ģ�"+rtnmsg);
    }
}