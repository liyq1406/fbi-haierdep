package org.fbi.dep.component.netty;

import org.apache.camel.component.netty.ChannelHandlerFactory;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-3-30
 * Time: 下午10:27
 * To change this template use File | Settings | File Templates.
 */
public class DepSktMessageDecoder extends FrameDecoder implements ChannelHandlerFactory {
    public static final int LENGTH = 8;
    private static Logger logger = LoggerFactory.getLogger(DepSktMessageDecoder.class);


    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        if (buffer.readableBytes() < LENGTH) {
            return null;
        }
        byte[] lengthBytes = new byte[LENGTH];
        buffer.getBytes(buffer.readerIndex(), lengthBytes);
        int dataLength = Integer.parseInt(new String(lengthBytes).trim());
        if (dataLength == 0) {
            throw new RuntimeException("【报文长度】字段不能为0");
        }
        if (buffer.readableBytes() < dataLength) {
            return null;
        }
        if (buffer.readableBytes() > dataLength) {
           logger.info("**************************************");
        }

        buffer.skipBytes(LENGTH);
        logger.info("【DepSktMessageDecoder 接收报文长度】" + dataLength);

        byte[] msgBytes = new byte[dataLength - 8];
        buffer.readBytes(msgBytes);
        return msgBytes;
    }

    @Override
    public ChannelHandler newChannelHandler() {
        return new DepSktMessageDecoder();
    }
}
