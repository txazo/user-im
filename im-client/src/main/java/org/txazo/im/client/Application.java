package org.txazo.im.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    /**
     * VM options: -Dlog4j.skipJansi=false
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
