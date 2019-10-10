package org.txazo.im.common.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.txazo.im.common.netty.ReconnectCallback;

import java.util.concurrent.TimeUnit;

@Slf4j
public class BreakLineReconnectHandler extends ChannelInboundHandlerAdapter {

    // 重连次数
    private int reconnectTimes = 0;
    // 最大重连次数
    private final int maxReconnectTimes;
    // 重连间隔
    private final int reconnectInterval;

    private final ReconnectCallback reconnectCallback;

    public BreakLineReconnectHandler(int maxReconnectTimes, int reconnectInterval, ReconnectCallback reconnectCallback) {
        this.maxReconnectTimes = maxReconnectTimes;
        this.reconnectInterval = reconnectInterval;
        this.reconnectCallback = reconnectCallback;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        reconnectTimes = 0;
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (reconnectTimes < maxReconnectTimes) {
            reconnectTimes++;
            ctx.channel().eventLoop().schedule(() -> reconnectCallback,
                    ((int) Math.pow(2, reconnectInterval)) * reconnectTimes, TimeUnit.SECONDS);
        } else {
            log.debug("IMClient closed after reconnect failed");
            ctx.close();
        }
        super.channelInactive(ctx);
    }

}
