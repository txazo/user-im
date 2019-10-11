package org.txazo.im.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.txazo.im.common.netty.handler.*;
import org.txazo.im.common.netty.IMThreadFactory;
import org.txazo.im.common.zk.IMServerRegistry;
import org.txazo.im.server.common.Attributes;
import org.txazo.im.server.config.IMServerConfig;
import org.txazo.im.server.netty.handler.HeartbeatRequestHandler;
import org.txazo.im.server.netty.handler.IMServerRegisterHandler;

import javax.annotation.PostConstruct;

@Slf4j
public class IMServer {

    @Autowired
    private IMServerConfig serverConfig;

    @Autowired
    private IMServerRegistry imServerRegistry;

    @Autowired
    private IMServerRegisterHandler imServerRegisterHandler;

    @PostConstruct
    private void init() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(serverConfig.getBoosGroupThreads(), new IMThreadFactory("nio-boss-group"));
        NioEventLoopGroup wokerGroup = new NioEventLoopGroup(serverConfig.getWorkerGroupThreads(), new IMThreadFactory("nio-worker-group"));
        NioEventLoopGroup businessGroup = new NioEventLoopGroup(serverConfig.getBusinessThreads(), new IMThreadFactory("nio-business-group"));

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, wokerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, serverConfig.getBacklog())
                .handler(new ChannelInitializer<ServerSocketChannel>() {

                    @Override
                    protected void initChannel(ServerSocketChannel ch) throws Exception {
                        if (serverConfig.isHandlerLoggingEnable()) {
                            ch.pipeline().addLast(businessGroup, LoggingHandler.INSTANCE);
                        }
                        ch.pipeline().addLast(businessGroup, imServerRegisterHandler);
                    }

                })
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        if (serverConfig.isHandlerLoggingEnable()) {
                            ch.pipeline().addLast(businessGroup, LoggingHandler.INSTANCE);
                        }
                        ch.pipeline().addLast(businessGroup, new IMIdleStateHandler(serverConfig.getHeartbeatInterval(), 0, 0, serverConfig.getIdleMaxTimes()));
                        ch.pipeline().addLast(businessGroup, new IMLengthFieldBasedFrameDecoder());
                        ch.pipeline().addLast(businessGroup, new ProtoDecoder());
                        ch.pipeline().addLast(businessGroup, new HeartbeatRequestHandler());
                        ch.pipeline().addLast(businessGroup, ProtoEncoder.INSTANCE);
                    }

                });

        bind(serverBootstrap, serverConfig.getPort());
    }

    private void bind(final ServerBootstrap serverBootstrap, final int port) {
        log.info("IMServer binding to port {}", port);
        serverBootstrap.bind(port).addListener(new GenericFutureListener<ChannelFuture>() {

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    future.channel().attr(Attributes.PORT).set(port);
                    log.info("IMServer binded to port {}", port);
                } else {
                    log.info("IMServer bind to port {} failed", port);
                    log.info("IMServer rebinding ...");
                    bind(serverBootstrap, port + 1);
                }
            }

        });
    }

}
