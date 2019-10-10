package org.txazo.im.server.netty.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.txazo.im.server.netty.channel.ChannelManager;

@ChannelHandler.Sharable
public class ChannelInitializationHandler extends ChannelInboundHandlerAdapter {

    private ChannelManager channelContextManager;

    public ChannelInitializationHandler(ChannelManager channelContextManager) {
        this.channelContextManager = channelContextManager;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

}
