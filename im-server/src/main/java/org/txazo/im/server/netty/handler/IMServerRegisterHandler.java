package org.txazo.im.server.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.txazo.im.common.registry.IMServerRegistry;

public class IMServerRegisterHandler extends ChannelInboundHandlerAdapter {

    private IMServerRegistry imServerRegistry;

    public IMServerRegisterHandler(IMServerRegistry imServerRegistry) {
        this.imServerRegistry = imServerRegistry;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        imServerRegistry.registerIMServer("", 0);
        super.channelActive(ctx);
    }

}
