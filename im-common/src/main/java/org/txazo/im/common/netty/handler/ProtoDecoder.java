package org.txazo.im.common.netty.handler;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.googlecode.protobuf.format.JsonFormat;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.txazo.im.common.protocol.CommandType;
import org.txazo.im.common.protocol.MessageBody;

import java.util.List;

@Slf4j
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

        AbstractMessage msg = null;

        switch (command) {
            case CommandType.HeartbeatRequest:
                msg = MessageBody.HeartbeatRequestPacket.parseFrom(messages);
                break;
            case CommandType.HeartbeatResponse:
                msg = MessageBody.HeartbeatResponsePacket.parseFrom(messages);
                break;
            default:
        }

        if (msg != null) {
            log.debug("[Recv Packet] {} {}", msg.getClass().getSimpleName(), JsonFormat.printToString(msg));
        }

        return msg;
    }

}
