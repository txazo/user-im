package org.txazo.im.route.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class IMRouteConfig {

    @Value("${im.server.zk.servers:}")
    private String zkServers;

}
