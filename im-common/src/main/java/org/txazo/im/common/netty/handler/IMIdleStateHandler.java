package org.txazo.im.common.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

public class IMIdleStateHandler extends IdleStateHandler {

    private int idleTimes = 0;
    private int maxIdleTimes;

    public IMIdleStateHandler(int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds, int maxIdleTimes) {
        super(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds);
        this.maxIdleTimes = maxIdleTimes;
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        idleTimes++;
        if (idleTimes > maxIdleTimes) {
            ctx.channel().close();
        } else {
            super.channelIdle(ctx, evt);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        idleTimes = 0;
        super.channelRead(ctx, msg);
    }

}
