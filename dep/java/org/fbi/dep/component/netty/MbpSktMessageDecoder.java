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
public class MbpSktMessageDecoder extends FrameDecoder implements ChannelHandlerFactory {
    public static final int LENGTH = 8;
    private static Logger logger = LoggerFactory.getLogger(MbpSktMessageDecoder.class);


    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        if (buffer.readableBytes() < LENGTH) {
            return null;
        }
        byte[] lengthBytes = new byte[LENGTH];
        buffer.getBytes(buffer.readerIndex(), lengthBytes);
        int dataLength = Integer.parseInt(new String(lengthBytes).trim());
        logger.info("Length: " + dataLength);
        if (dataLength == 0) {
            throw new RuntimeException("【报文长度】字段不能为0");
        }
        if (buffer.readableBytes() < dataLength + LENGTH) {
            return null;
        }
        if (buffer.readableBytes() > dataLength + LENGTH) {
           logger.info("**************************************");
        }

        buffer.skipBytes(LENGTH);
        byte[] msgBytes = new byte[dataLength];
        buffer.readBytes(msgBytes);
        return msgBytes;
    }

    @Override
    public ChannelHandler newChannelHandler() {
        return new MbpSktMessageDecoder();
    }
}
