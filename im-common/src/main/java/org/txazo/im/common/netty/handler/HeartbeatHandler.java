package org.txazo.im.common.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.txazo.im.common.protocol.CommandType;
import org.txazo.im.common.protocol.MessageBody;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HeartbeatHandler extends ChannelInboundHandlerAdapter {

    private int heartbeatInterval;
    private ScheduledFuture future;

    public HeartbeatHandler(int heartbeatInterval) {
        this.heartbeatInterval = heartbeatInterval;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleHeartbeat(ctx);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        close();
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    private void scheduleHeartbeat(ChannelHandlerContext ctx) {
        future = ctx.channel().eventLoop().scheduleAtFixedRate(() -> {
            if (ctx.channel().isActive()) {
                MessageBody.HeartbeatRequestPacket packet = MessageBody.HeartbeatRequestPacket.newBuilder()
                        .setCommand(CommandType.HeartbeatRequest)
                        .build();
                ctx.channel().writeAndFlush(packet);
            } else {
                log.warn("Close heartbeat while channel inactive");
                close();
            }
        }, 1, heartbeatInterval, TimeUnit.SECONDS);
    }

    private void close() {
        if (future != null) {
            future.cancel(true);
        }
    }

}
