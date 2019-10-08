package org.txazo.im.server.common;

import io.netty.util.AttributeKey;

public abstract class Attributes {

    public static final AttributeKey<String> IP = AttributeKey.newInstance("ip");

    public static final AttributeKey<Integer> PORT = AttributeKey.newInstance("port");

}
