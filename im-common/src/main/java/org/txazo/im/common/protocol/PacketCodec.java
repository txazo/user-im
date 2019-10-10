package org.txazo.im.common.protocol;

import com.google.protobuf.DynamicMessage;
import io.netty.buffer.ByteBuf;

public class PacketCodec {

    private Class<? extends DynamicMessage> type;

    public void encode(ByteBuf byteBuf, Packet packet) {
        byteBuf.writeInt(Packet.MAGIC);
        byteBuf.writeByte(Packet.VERSION_1);
    }

    public Packet decode(ByteBuf byteBuf) {
        return null;
    }

}
