package org.txazo.im.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.txazo.im.common.zk.IMServerRegistry;
import org.txazo.im.server.netty.IMServer;
import org.txazo.im.server.netty.context.ChannelContextManager;

@Configuration
public class IMServerConfiguration {

    @Autowired
    private IMServerConfig imServerConfig;

    @Bean
    public IMServer nettyServer() {
        return new IMServer();
    }

    @Bean
    public ChannelContextManager channelContextManager() {
        return new ChannelContextManager();
    }

    @Bean
    public IMServerRegistry imServerRegistry() {
        return new IMServerRegistry(imServerConfig.getZkServers());
    }

}
