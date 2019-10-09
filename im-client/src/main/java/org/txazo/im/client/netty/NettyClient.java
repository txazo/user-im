package org.txazo.im.client.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class NettyClient {

    private volatile boolean closed = false;

    private String host;
    private int port;
    private int reconnectTimes = 0;

    private NettyClientConfig nettyClientConfig;

    private Channel channel;

    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

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
                        ch.pipeline()
                                .addLast(nettyClientConfig.getBusinessGroup(), nettyClientConfig.getLoggingHandler())
                                .addLast(nettyClientConfig.getBusinessGroup(), new ChannelInboundHandlerAdapter() {

                                    @Override
                                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                        reconnectTimes = 0;
                                        super.channelActive(ctx);
                                    }

                                    @Override
                                    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                        if (!closed && reconnectTimes < nettyClientConfig.getMaxReconnectTimes()) {
                                            reconnectTimes++;
                                            ctx.channel().eventLoop().schedule(() -> connect(true),
                                                    ((int) Math.pow(2, nettyClientConfig.getReconnectInterval())) * reconnectTimes, TimeUnit.SECONDS);
                                        } else {
                                            ctx.close();
                                        }
                                        super.channelInactive(ctx);
                                    }

                                });
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
