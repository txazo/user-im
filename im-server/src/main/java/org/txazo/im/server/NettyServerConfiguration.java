package org.txazo.im.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.txazo.im.server.netty.NettyServer;

@Configuration
public class NettyServerConfiguration {

    @Bean
    public NettyServer nettyServer() {
        return new NettyServer();
    }

}
