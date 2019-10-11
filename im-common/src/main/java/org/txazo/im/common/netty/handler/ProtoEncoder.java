package org.txazo.im.common.netty.handler;

import com.google.protobuf.AbstractMessage;
import com.googlecode.protobuf.format.JsonFormat;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import org.txazo.im.common.constant.IMConstants;
import org.txazo.im.common.protocol.CommandType;
import org.txazo.im.common.protocol.Packet;

@Slf4j
@ChannelHandler.Sharable
public class ProtoEncoder extends MessageToByteEncoder<AbstractMessage> {

    public static final ProtoEncoder INSTANCE = new ProtoEncoder();

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

        log.debug("[Send Packet] {} {}", msg.getClass().getSimpleName(), JsonFormat.printToString(msg));

        out.writeInt(Packet.MAGIC);
        out.writeByte(Packet.VERSION_1);
        out.writeByte(Packet.GZIP_NONE);
        out.writeByte(Packet.SERIALIZATION_PROTOBUF);
        out.writeByte(command);
        out.writeShort(messages.length);
        out.writeBytes(messages);
    }

}
