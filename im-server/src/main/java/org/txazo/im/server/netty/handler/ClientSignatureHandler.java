package org.txazo.im.server.netty.handler;

import io.netty.channel.*;

@ChannelHandler.Sharable
public class ClientSignatureHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

}
