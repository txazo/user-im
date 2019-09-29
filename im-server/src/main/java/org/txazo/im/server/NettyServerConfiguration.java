package org.txazo.im.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.txazo.im.server.netty.NettyServer;
import org.txazo.im.server.netty.context.ChannelContextManager;

@Configuration
public class NettyServerConfiguration {

    @Bean
    public NettyServer nettyServer() {
        return new NettyServer();
    }

    @Bean
    public ChannelContextManager channelContextManager() {
        return new ChannelContextManager();
    }

}
