package org.txazo.im.server.netty.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.txazo.im.common.zk.IMServerRegistry;
import org.txazo.im.common.util.IPUtil;
import org.txazo.im.server.common.Attributes;

@ChannelHandler.Sharable
public class IMServerRegisterHandler extends ChannelInboundHandlerAdapter {

    private IMServerRegistry imServerRegistry;

    public IMServerRegisterHandler(IMServerRegistry imServerRegistry) {
        this.imServerRegistry = imServerRegistry;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        imServerRegistry.registerIMServer(IPUtil.getIP(), ctx.channel().attr(Attributes.PORT).get());
        super.channelActive(ctx);
    }

}
