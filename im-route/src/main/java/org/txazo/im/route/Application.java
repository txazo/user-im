package org.txazo.im.route;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.setProperty("log4j.skipJansi", "false");
        SpringApplication.run(Application.class, args);
    }

}
