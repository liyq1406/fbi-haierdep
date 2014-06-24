package org.fbi.dep.component.netty;

import org.fbi.dep.util.StringPad;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-3-31
 * Time: 下午6:07
 * To change this template use File | Settings | File Templates.
 */
public class DepSktMessageEncoder extends OneToOneEncoder {
    private static Logger logger = LoggerFactory.getLogger(DepSktMessageEncoder.class);

    @Override
    protected Object encode(ChannelHandlerContext channelHandlerContext, Channel channel, Object message) throws Exception {

        byte[] msgbuf = (byte[]) message;
        byte[] msglen = (StringPad.rightPad4ChineseToByteLength("" + (msgbuf.length + 8), 8, " ")).getBytes();

        byte[] bytesResData = new byte[msgbuf.length + 8];
        System.arraycopy(msglen, 0, bytesResData, 0, msglen.length);
        System.arraycopy(msgbuf, 0, bytesResData, msglen.length, msgbuf.length);

        logger.info("【DepSktMessageEncoder 发送报文长度】" + bytesResData.length);
        logger.info("【DepSktMessageEncoder 发送报文】" + new String(bytesResData));
        ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
        buffer.writeBytes(bytesResData);
//        logger.info(format16(bytesResData));
        return buffer;
    }

    private String format16(byte[] buffer) {
        StringBuilder result = new StringBuilder();
        result.append("\n");
        int n = 0;
        byte[] lineBuffer = new byte[16];
        for (byte b : buffer) {
            if (n % 16 == 0) {
                result.append(String.format("%05x: ", n));
                lineBuffer = new byte[16];
            }
            result.append(String.format("%02x ", b));
            lineBuffer[n % 16] = b;
            n++;
            if (n % 16 == 0) {
                result.append(new String(lineBuffer));
                result.append("\n");
            }

            //TODO
            if (n >= 1024) {
                result.append("报文过大，已截断...");
                break;
            }

        }
        for (int k = 0; k < (16 - n % 16); k++) {
            result.append("   ");
        }
        result.append(new String(lineBuffer));
        result.append("\n");
        return result.toString();
    }

}
