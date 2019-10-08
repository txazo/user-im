package org.txazo.im.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.txazo.im.client.netty.NettyClient;

@Configuration
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.setProperty("log4j.skipJansi", "false");
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public NettyClient nettyClient() {
        return new NettyClient();
    }

}
