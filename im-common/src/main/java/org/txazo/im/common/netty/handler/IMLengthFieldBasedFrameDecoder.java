package org.txazo.im.common.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.txazo.im.common.protocol.Packet;

/**
 * 数据包格式定义
 * <p>
 * 1. 魔数(4 byte): 0xabcdefff
 * 2. 版本号(1 byte)
 * 3. 压缩标识(1 byte)
 * 4. 序列化标识(1 byte)
 * 5. 消息类型(1 byte)
 * 6. body长度(2 byte)
 * 7. body
 */
public class IMLengthFieldBasedFrameDecoder extends LengthFieldBasedFrameDecoder {

    private static final int lengthFieldOffset = 4 + 1 + 1 + 1 + 1;
    private static final int lengthFieldLength = 2;
    private static final int maxFrameLength = (1 << (lengthFieldLength * 8)) - 1 + lengthFieldOffset + lengthFieldLength;

    public IMLengthFieldBasedFrameDecoder() {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in.getInt(in.readerIndex()) != Packet.MAGIC) {
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx, in);
    }

}
