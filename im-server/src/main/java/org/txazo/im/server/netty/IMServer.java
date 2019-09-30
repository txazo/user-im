package org.txazo.im.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.txazo.im.common.registry.IMServerRegistry;
import org.txazo.im.server.config.IMServerConfig;
import org.txazo.im.server.netty.handler.IMServerRegisterHandler;

import javax.annotation.PostConstruct;

@Slf4j
public class IMServer {

    @Autowired
    private IMServerConfig serverConfig;

    @Autowired
    private IMServerRegistry imServerRegistry;

    @PostConstruct
    private void init() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(serverConfig.getBoosGroupThreads());
        NioEventLoopGroup wokerGroup = new NioEventLoopGroup(serverConfig.getWorkerGroupThreads());

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, wokerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, serverConfig.getBacklog())
                .handler(new ChannelInitializer<ServerSocketChannel>() {

                    @Override
                    protected void initChannel(ServerSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new IMServerRegisterHandler(imServerRegistry));
                    }

                })
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {

                    }

                });

        bind(serverBootstrap, serverConfig.getPort());
    }

    private void bind(final ServerBootstrap serverBootstrap, final int port) {
        log.debug("IMServer binding to port {}", port);
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                serverBootstrap.attr(AttributeKey.newInstance(""), port);
                log.debug("IMServer binded to port {}", port);
            } else {
                log.debug("IMServer bind to port {} failed", port);
                log.debug("IMServer rebinding ...");
                bind(serverBootstrap, port + 1);
            }
        });
    }

}
