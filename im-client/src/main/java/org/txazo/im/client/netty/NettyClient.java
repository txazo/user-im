package org.txazo.im.client.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.txazo.im.common.netty.handler.*;

@Slf4j
public class NettyClient {

    private volatile boolean closed = false;

    private String host;
    private int port;
    // 重连次数
    private int reconnectTimes = 0;

    private NettyClientConfig nettyClientConfig;

    private Channel channel;

    public NettyClient(String host, int port, NettyClientConfig nettyClientConfig) {
        this.host = host;
        this.port = port;
        this.nettyClientConfig = nettyClientConfig;

        init();
    }

    private void init() {
        connect(false);
    }

    private void connect(boolean reconnect) {
        if (reconnect) {
            log.debug("IMClient reconnecting to IMServer({}:{})", host, port);
        }

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(nettyClientConfig.getWokerGroup())
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, nettyClientConfig.getConnectTimeoutMillis())
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(nettyClientConfig.getBusinessGroup(), LoggingHandler.INSTANCE);
                        ch.pipeline().addLast(nettyClientConfig.getBusinessGroup(), new BreakLineReconnectHandler(nettyClientConfig.getMaxReconnectTimes(), nettyClientConfig.getReconnectInterval(), () -> connect(true)));
                        ch.pipeline().addLast(nettyClientConfig.getBusinessGroup(), new IMIdleStateHandler(nettyClientConfig.getHeartbeatInterval(), 0, 0, nettyClientConfig.getIdleMaxTimes()));
                        ch.pipeline().addLast(nettyClientConfig.getBusinessGroup(), new HeartbeatClientHandler(nettyClientConfig.getHeartbeatInterval()));
                        ch.pipeline().addLast(nettyClientConfig.getBusinessGroup(), new IMLengthFieldBasedFrameDecoder());
                        ch.pipeline().addLast(nettyClientConfig.getBusinessGroup(), new ProtoDecoder());
                        ch.pipeline().addLast(nettyClientConfig.getBusinessGroup(), ProtoEncoder.INSTANCE);
                    }

                });

        bootstrap.connect(host, port).addListener(new GenericFutureListener<ChannelFuture>() {

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    channel = future.channel();
                    log.debug("IMClient connected to IMServer({}:{})", host, port);
                } else {
                    log.debug("IMClient connect to IMServer({}:{}) failed", host, port);
                    future.channel().pipeline().fireChannelInactive();
                }
            }

        });
    }

}
