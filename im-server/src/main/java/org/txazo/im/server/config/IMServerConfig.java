package org.txazo.im.server.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class IMServerConfig {

    @Value("${im.handler.logging.enable:false}")
    private boolean handlerLoggingEnable;

    @Value("${im.server.port:9000}")
    private Integer port;

    @Value("${im.server.socket.backlog:1024}")
    private Integer backlog;

    @Value("${im.server.idle.max-times:3}")
    private Integer idleMaxTimes;

    @Value("${im.server.heartbeat.interval:10}")
    private Integer heartbeatInterval;

    @Value("${im.server.heartbeat.resp.interval:10}")
    private Integer heartbeatRespInterval;

    @Value("${im.server.netty.boosGroup.threads:1}")
    private Integer boosGroupThreads;

    @Value("${im.server.netty.workerGroup.threads:5}")
    private Integer workerGroupThreads;

    @Value("${im.server.netty.businessGroup.threads:20}")
    private Integer businessThreads;

    @Value("${im.server.zk.servers:}")
    private String zkServers;

}
