package org.fbi.dep.startup;

import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Lichao.W At 2015/7/24 8:54
 * wanglichao@163.com
 */
public class RfmServerTest {
    public static void main(String args[]) throws IOException {
        int port = 9876;
        ServerSocket server = new ServerSocket(port);
        while (true) {
            Socket socket = server.accept();
            //每接收到一个Socket就建立一个新的线程来处理它
            new Thread(new Task(socket)).start();
        }
    }

    /**
     * 用来处理Socket请求的
     */
    static class Task implements Runnable {

        private Socket socket;

        public Task(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                handleSocket();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 跟客户端Socket进行通信
         * @throws Exception
         */
        private void handleSocket() throws Exception {
            Reader reader = new InputStreamReader(socket.getInputStream());
            char chars[] = new char[64];
            int len;
            StringBuilder sb = new StringBuilder();
            String temp;
            int index;
            if ((len=reader.read(chars)) != -1) {
                temp = new String(chars, 0, len);
                sb.append(temp);
            }
            String strSBstrSB=sb.toString().replace("|", "").substring(6, 10);
            String rtnmsg;
            if("1001".equals(strSBstrSB)){
                rtnmsg = "0005360000|1000000000000009|"+
                        StringUtils.rightPad("prePerName", 255, " ")+"|" +
                        StringUtils.rightPad("preProAddr", 128, " ")+"|" +
                        StringUtils.rightPad("preProName", 128, " ")+"|";
            }else if("1002".equals(strSBstrSB)){
                rtnmsg = "0000220000|1000000000000009|";
            }else if("2001".equals(strSBstrSB)){
                rtnmsg = "0002270000|0|"+
                        StringUtils.rightPad("10000000", 20, " ")+"|" +
                        StringUtils.rightPad("801000016502013", 30, " ")+"|" +
                        StringUtils.rightPad("青岛海尔空调器有限总公司职工技术协会", 150, " ")+"|"+
                        "1440560429911369|";
            }else if("2002".equals(strSBstrSB)){
                rtnmsg = "0000220000|1000000000000009|";
            }else if("2011".equals(strSBstrSB)){
                rtnmsg = "0000220000|1000000000000009|";
            }else if("2101".equals(strSBstrSB)){
                rtnmsg = "0008830000|" +
                        StringUtils.rightPad("801000241502012001",30," ") +"|"+
                        StringUtils.rightPad("jianguanzhanghao12345678900014户名", 150, " ")+"|" +
                        StringUtils.rightPad("21", 20, " ")+"|" +
                        StringUtils.rightPad("shoukuanyinhang", 90, " ")+"|" +
                        StringUtils.rightPad("801000593102027001",30," ") +"|"+
                        StringUtils.rightPad("shoukuanzhanghao12345678900014户名", 150, " ")+"|" +
                        StringUtils.rightPad("项目名称", 128, " ")+"|" +
                        StringUtils.rightPad("开发企业名称", 255, " ")+"|" +
                        "1000000000000009|";
                /*rtnmsg = "000873|0000|jianguanzhanghao12345678900014|"+
                        StringUtils.rightPad("jianguanzhanghao12345678900014户名", 150, " ")+"|" +
                        StringUtils.rightPad("20800001", 20, " ")+"|" +
                        StringUtils.rightPad("shoukuanyinhang", 90, " ")+"|" +
                        "shoukuanzhanghao12345678900014|" +
                        StringUtils.rightPad("shoukuanzhanghao12345678900014户名", 150, " ")+"|" +
                        StringUtils.rightPad("项目名称", 128, " ")+"|" +
                        StringUtils.rightPad("开发企业名称", 255, " ")+"|" +
                        "1000000000000009";*/
            }else if("2102".equals(strSBstrSB)){
                rtnmsg = "0000220000|1000000000000009|";
            }else if("2111".equals(strSBstrSB)){
                rtnmsg = "0000220000|1000000000000009|";
            }else if("2201".equals(strSBstrSB)){
                rtnmsg = "0005930000|"+
                        StringUtils.rightPad("801000241502012001",30," ") +"|"+
                        StringUtils.rightPad("jianguanzhanghao12345678900014户名", 150, " ")+"|" +
                        StringUtils.rightPad("20", 20, " ")+"|" +
                        StringUtils.rightPad("yezhuxingming", 80, " ")+"|" +
                        StringUtils.rightPad("zhengjianleixing", 30, " ")+"|" +
                        StringUtils.rightPad("开发企业名称", 255, " ")+"|" +
                        "1000000000000009|";
            }else if("2202".equals(strSBstrSB)){
                rtnmsg = "0000220000|1000000000000009|";
            }else if("2211".equals(strSBstrSB)){
                rtnmsg = "0000220000|1000000000000009|";
            }else if("2501".equals(strSBstrSB)){
                rtnmsg = "0000720000|0|pingtaijizhang12|jianguanyinhangjizhangliushui1|1000000000000009|";
            }else{
                rtnmsg = "1234567890111213141516171819202122232425262728293031323334353637383940414243454647484950";
            }
            System.out.println("返回报文：" + rtnmsg);
            Writer writer = new OutputStreamWriter(socket.getOutputStream(),"GB2312");
            writer.write(rtnmsg);
            writer.flush();
            writer.close();
            reader.close();
            socket.close();
        }

    }
}
