package org.txazo.im.common.netty.handler;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.txazo.im.common.protocol.CommandType;
import org.txazo.im.common.protocol.MessageBody;

import java.util.List;

public class ProtoDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.add(codec(in));
    }

    private AbstractMessage codec(ByteBuf in) throws InvalidProtocolBufferException {
        int magic = in.readInt();
        in.readByte();
        in.readByte();
        in.readByte();
        byte command = in.readByte();
        byte[] messages = new byte[in.readShort()];
        in.readBytes(messages);

        switch (command) {
            case CommandType.HeartbeatRequest:
                return MessageBody.HeartbeatRequestPacket.parseFrom(messages);
            case CommandType.HeartbeatResponse:
                return MessageBody.HeartbeatResponsePacket.parseFrom(messages);
            default:
                return null;
        }
    }

}
