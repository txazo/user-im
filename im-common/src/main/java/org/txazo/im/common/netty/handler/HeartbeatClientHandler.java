package org.txazo.im.common.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.txazo.im.common.protocol.MessageBody;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class HeartbeatClientHandler extends ChannelInboundHandlerAdapter {

    private int heartbeatInterval;
    private ScheduledFuture future;

    public HeartbeatClientHandler(int heartbeatInterval) {
        this.heartbeatInterval = heartbeatInterval;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleHeartbeat(ctx);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        future.cancel(true);
        super.channelInactive(ctx);
    }

    private void scheduleHeartbeat(ChannelHandlerContext ctx) {
        future = ctx.channel().eventLoop().scheduleAtFixedRate(() -> {
            if (ctx.channel().isActive()) {
                MessageBody.HeartbeatRequestPacket packet = MessageBody.HeartbeatRequestPacket.newBuilder()
                        .setCommand(MessageBody.CommandType.HeartbeatRequest)
                        .build();
                ctx.channel().writeAndFlush(packet);
            }
        }, heartbeatInterval, heartbeatInterval, TimeUnit.SECONDS);
    }

}
