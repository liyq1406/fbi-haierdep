package org.fbi.dep.component.jms;

import org.fbi.dep.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

/**
 * Created by Lichao.W At 2015/7/22 11:44
 * wanglichao@163.com
 */
public class JmsRfmSktClient {
    private static Logger logger = LoggerFactory.getLogger(JmsRfmSktClient.class);

    public static final String HOSTIP = PropertyManager.getProperty("socket.rfm.ip");
    public static final int HOSTPORT = PropertyManager.getIntProperty("socket.rfm.port");
    public static final int TIMEOUT = PropertyManager.getIntProperty("socket.rfm.timeout");

    public String processTxn(String datagram) throws Exception {

        String strMsg;

        byte[] recvbuf = null;
        // ����SOCKET
        Socket socket=null;
        try{
            socket = new Socket(HOSTIP, HOSTPORT);
            socket.setKeepAlive(true);
            socket.setSoTimeout(TIMEOUT);
        }catch (Exception e){
            strMsg = "����������ʧ��!";
            logger.info(strMsg);
            throw new RuntimeException(strMsg);
        }

        OutputStream os = socket.getOutputStream();
        // �������ݰ�
        os.write(datagram.getBytes("GB2312"));
        os.flush();

        InputStream is = socket.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        try {
            recvbuf = new byte[6];
            int readNum = bis.read(recvbuf);

            if (readNum == -1) {
                strMsg = "�����������ѹر�!";
                logger.info(strMsg);
                throw new RuntimeException(strMsg);
            }
            if (readNum < 6) {
                strMsg = "��ȡ����ͷ���Ȳ��ִ���...";
                logger.info(strMsg);
                throw new RuntimeException(strMsg);
            }

            String strHead=new String(recvbuf);
            int msgLen = Integer.parseInt(strHead);
            recvbuf = new byte[msgLen];

            //TODO
            Thread.sleep(500);

            readNum = 0;//������
            while (readNum < msgLen) {
                readNum += bis.read(recvbuf, readNum, msgLen - readNum);
            }
            is.close();
            bis.close();
            if (readNum != msgLen) {
                strMsg = "���ĳ��ȴ���,����ͷָʾ����:[" + msgLen + "], ʵ�ʻ�ȡ����:[" + readNum + "]";
                logger.info(strMsg);
                throw new RuntimeException(strMsg);
            }
        }catch (Exception e){
            logger.info("�����쳣��",e);
        } finally{
            try {
                socket.close();
            } catch (IOException e) {
                logger.info(e.getMessage());
                throw new RuntimeException(e);
            }
        }
        // ���շ��ص�����
        String resDatagram = new String(recvbuf);
        logger.info("�յ��ķ����������ݣ�"+resDatagram);
        return resDatagram;
    }

    public static void main(String[] args) throws Exception{
        String reqmsg = "0000350000|9988|555555     |���ֻ᲻������99|";
        String rtnmsg = new JmsRfmSktClient().processTxn(reqmsg);
        System.out.println("�������ķ��ر��ģ�"+rtnmsg);
    }
}
