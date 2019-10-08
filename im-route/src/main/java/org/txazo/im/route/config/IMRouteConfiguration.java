package org.txazo.im.route.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.txazo.im.common.zk.IMServerRegistry;

@Configuration
public class IMRouteConfiguration {

    @Autowired
    private IMRouteConfig imRouteConfig;

    @Bean
    public IMServerRegistry imServerRegistry() {
        return new IMServerRegistry(imRouteConfig.getZkServers());
    }

}
