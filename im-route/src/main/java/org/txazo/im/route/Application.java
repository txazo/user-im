package org.txazo.im.route;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.txazo.im.common.util.Log4jUtil;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        Log4jUtil.initConfig();
        SpringApplication.run(Application.class, args);
    }

}
