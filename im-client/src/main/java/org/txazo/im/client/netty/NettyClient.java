package org.txazo.im.client.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class NettyClient {

    private volatile boolean closed = false;

    private int reconnectTimes = 0;
    private int maxReconnectTimes = 10;
    private int reconnectInterval = 1;

    private Channel channel;

    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

    @PostConstruct
    private void init() {
        connect();
    }

    private void connect() {
        NioEventLoopGroup wokerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(wokerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler());
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {

                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                maxReconnectTimes = 0;
                                super.channelActive(ctx);
                            }

                            @Override
                            public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                if (!closed && reconnectTimes < maxReconnectTimes) {
                                    reconnectTimes++;
                                    ctx.channel().eventLoop().schedule(() -> connect(), reconnectInterval * reconnectTimes, TimeUnit.SECONDS);
                                }
                                super.channelInactive(ctx);
                            }

                        });
                    }

                });

        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 10030).addListener(new GenericFutureListener<ChannelFuture>() {

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    log.debug("IMClient connected to IMServer(127.0.0.1:9901)");
                } else {
                    log.debug("IMClient connect to IMServer(127.0.0.1:9901) failed");
                    future.channel().pipeline().fireChannelInactive();
                }
            }

        });

        try {
            channel = channelFuture.sync().channel();
        } catch (Exception e) {
            log.error("sync error", e);
        }

        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.directBuffer();
        byteBuf.writeBytes("HelloWorld".getBytes());
        channel.writeAndFlush(byteBuf);
    }

}
