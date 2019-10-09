package org.txazo.im.client.netty;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.Data;
import org.txazo.im.common.netty.IMThreadFactory;
import org.txazo.im.common.netty.LoggingHandler;

@Data
public class NettyClientConfig {

    private int connectTimeoutMillis = 5000;

    private int maxReconnectTimes = 1;

    private int reconnectInterval = 1;

    // 工作线程池
    private NioEventLoopGroup wokerGroup;

    // 业务线程池
    private NioEventLoopGroup businessGroup;

    private LoggingHandler loggingHandler = new LoggingHandler();

    public NettyClientConfig(int workerGroupThreads, int businessGroupThreads) {
        this.wokerGroup = new NioEventLoopGroup(workerGroupThreads, new IMThreadFactory("nio-worker-group"));
        this.businessGroup = new NioEventLoopGroup(businessGroupThreads, new IMThreadFactory("nio-business-group"));
    }

}
