package org.txazo.im.client;

import org.txazo.im.client.netty.NettyClientConfig;

public class Application {

    public static void main(String[] args) {
        System.setProperty("log4j.skipJansi", "false");

        NettyClientConfig nettyClientConfig = new NettyClientConfig(5, 20);
        IMClient imClient = new IMClient("root", "root", nettyClientConfig);
        imClient.start();
    }

}
