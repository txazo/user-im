package org.txazo.im.common.util;

public abstract class Log4jUtil {

    public static void initConfig() {
        System.setProperty("log4j.skipJansi", "false");
    }

}
