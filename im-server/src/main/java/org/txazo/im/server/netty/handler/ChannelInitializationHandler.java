package org.txazo.im.server.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.txazo.im.server.netty.context.ChannelContextManager;

public class ChannelInitializationHandler extends ChannelInboundHandlerAdapter {

    private ChannelContextManager channelContextManager;

    public ChannelInitializationHandler(ChannelContextManager channelContextManager) {
        this.channelContextManager = channelContextManager;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

}
