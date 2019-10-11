package org.txazo.im.server.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.txazo.im.common.protocol.CommandType;
import org.txazo.im.common.protocol.MessageBody;

@Slf4j
public class HeartbeatRequestHandler extends SimpleChannelInboundHandler<MessageBody.HeartbeatRequestPacket> {

    private long lastHeartbeatTimeMillis = -1;
    private final int heartbeatRespInterval;

    public HeartbeatRequestHandler(int heartbeatRespInterval) {
        super();
        this.heartbeatRespInterval = heartbeatRespInterval * 1000;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageBody.HeartbeatRequestPacket msg) throws Exception {
        long currentTimeMillis = System.currentTimeMillis();
        // 限制心跳响应间隔
        if (currentTimeMillis - lastHeartbeatTimeMillis < heartbeatRespInterval) {
            return;
        }

        MessageBody.HeartbeatResponsePacket packet = MessageBody.HeartbeatResponsePacket.newBuilder()
                .setCommand(CommandType.HeartbeatResponse)
                .build();
        ctx.channel().writeAndFlush(packet);
        lastHeartbeatTimeMillis = currentTimeMillis;
    }

}
