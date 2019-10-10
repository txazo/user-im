package org.txazo.im.common.netty.handler;

import com.google.protobuf.AbstractMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import org.txazo.im.common.constant.IMConstants;
import org.txazo.im.common.protocol.CommandType;
import org.txazo.im.common.protocol.Packet;

@Slf4j
public class ProtoEncoder extends MessageToByteEncoder<AbstractMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractMessage msg, ByteBuf out) throws Exception {
        byte[] messages = msg.toByteArray();
        if (messages.length > IMConstants.MAX_MESSAGE_LENGTH) {
            log.error("Message length is too long than {}", IMConstants.MAX_MESSAGE_LENGTH);
            return;
        }

        Byte command = CommandType.getCommandType(msg.getClass());
        if (command == null) {
            log.error("Message type {} is invalid", msg.getClass());
            return;
        }

        out.writeInt(Packet.MAGIC);
        out.writeByte(Packet.GZIP_NONE);
        out.writeByte(Packet.SERIALIZATION_PROTOBUF);
        out.writeByte(command);
        out.writeShort(messages.length);
        out.writeBytes(messages);
    }

}
