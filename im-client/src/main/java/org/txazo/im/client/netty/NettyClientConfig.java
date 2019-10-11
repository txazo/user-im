package org.txazo.im.client.netty;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.Data;
import org.txazo.im.common.netty.IMThreadFactory;

@Data
public class NettyClientConfig {

    private boolean handlerLoggingEnable = false;

    // Netty连接超时时间
    private int connectTimeoutMillis = 5000;

    // 最大重连次数
    private int maxReconnectTimes = 5;

    // 重连间隔
    private int reconnectInterval = 1;

    // 心跳间隔
    private int heartbeatInterval = 1;

    private Integer idleMaxTimes = 3;

    // 工作线程池
    private NioEventLoopGroup wokerGroup;

    // 业务线程池
    private NioEventLoopGroup businessGroup;

    public NettyClientConfig(int workerGroupThreads, int businessGroupThreads) {
        this.wokerGroup = new NioEventLoopGroup(workerGroupThreads, new IMThreadFactory("nio-worker-group"));
        this.businessGroup = new NioEventLoopGroup(businessGroupThreads, new IMThreadFactory("nio-business-group"));
    }

}
