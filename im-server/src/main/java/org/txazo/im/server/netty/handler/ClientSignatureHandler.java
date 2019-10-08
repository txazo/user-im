package org.txazo.im.server.netty.handler;

import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientSignatureHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

}
