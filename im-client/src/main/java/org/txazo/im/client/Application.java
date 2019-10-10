package org.txazo.im.client;

import org.txazo.im.client.netty.NettyClientConfig;
import org.txazo.im.common.util.Log4jUtil;

public class Application {

    public static void main(String[] args) {
        Log4jUtil.initConfig();

        NettyClientConfig nettyClientConfig = new NettyClientConfig(5, 20);
        IMClient imClient = new IMClient("root", "root", nettyClientConfig);
        imClient.start();
    }

}
