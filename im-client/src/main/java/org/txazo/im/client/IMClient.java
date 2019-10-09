package org.txazo.im.client;

import org.txazo.im.client.netty.NettyClient;
import org.txazo.im.client.netty.NettyClientConfig;

public class IMClient {

    private String userName;
    private String password;
    private NettyClientConfig nettyClientConfig;

    private NettyClient nettyClient;

    public IMClient(String userName, String password, NettyClientConfig nettyClientConfig) {
        this.userName = userName;
        this.password = password;
        this.nettyClientConfig = nettyClientConfig;
    }

    public void start() {
        // 登陆获取userId、token

        // 连接IMServer
        connect();
    }

    private void connect() {
        // 分配IMServer
        String host = "127.0.0.1";
        int port = 10030;

        nettyClient = new NettyClient(host, port, nettyClientConfig);
    }

}
