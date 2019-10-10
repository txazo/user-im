package org.txazo.im.server.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class IMServerConfig {

    @Value("${im.server.port:9000}")
    private Integer port;

    @Value("${im.server.socket.backlog:1024}")
    private Integer backlog;

    @Value("${im.server.idle.max-times:3}")
    private Integer idleMaxTimes;

    @Value("${im.server.netty.boosGroup.threads:1}")
    private Integer boosGroupThreads;

    @Value("${im.server.netty.workerGroup.threads:5}")
    private Integer workerGroupThreads;

    @Value("${im.server.zk.servers:}")
    private String zkServers;

}
