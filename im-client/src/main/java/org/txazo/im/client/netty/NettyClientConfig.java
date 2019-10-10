package org.txazo.im.client.netty;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.Data;
import org.txazo.im.common.netty.IMThreadFactory;
import org.txazo.im.common.netty.handler.LoggingHandler;

@Data
public class NettyClientConfig {

    // Netty连接超时时间
    private int connectTimeoutMillis = 5000;

    // 最大重连次数
    private int maxReconnectTimes = 20;

    // 重连间隔
    private int reconnectInterval = 1;

    // 工作线程池
    private NioEventLoopGroup wokerGroup;

    // 业务线程池
    private NioEventLoopGroup businessGroup;

    // 日志记录Handler
    private LoggingHandler loggingHandler = new LoggingHandler();

    public NettyClientConfig(int workerGroupThreads, int businessGroupThreads) {
        this.wokerGroup = new NioEventLoopGroup(workerGroupThreads, new IMThreadFactory("nio-worker-group"));
        this.businessGroup = new NioEventLoopGroup(businessGroupThreads, new IMThreadFactory("nio-business-group"));
    }

}
