package org.txazo.im.server.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.txazo.im.common.zk.IMServerRegistry;
import org.txazo.im.common.util.IPUtil;
import org.txazo.im.server.config.IMServerConfig;

public class IMServerRegisterHandler extends ChannelInboundHandlerAdapter {

    private IMServerConfig imServerConfig;

    private IMServerRegistry imServerRegistry;

    public IMServerRegisterHandler(IMServerConfig imServerConfig, IMServerRegistry imServerRegistry) {
        this.imServerConfig = imServerConfig;
        this.imServerRegistry = imServerRegistry;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        imServerRegistry.registerIMServer(IPUtil.getIP(), imServerConfig.getPort());
        super.channelActive(ctx);
    }

}
