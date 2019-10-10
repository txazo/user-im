package org.txazo.im.common.protocol;

import io.netty.buffer.ByteBuf;

public class PacketCodec {

    public void encode(ByteBuf byteBuf, Packet packet) {
        byteBuf.writeInt(Packet.MAGIC);
        byteBuf.writeByte(Packet.VERSION_1);
    }

    public Packet decode(ByteBuf byteBuf) {
        return null;
    }

}
