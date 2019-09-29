package org.txazo.im.server.netty.context;

import java.util.HashMap;
import java.util.Map;

public class ChannelContextManager {

    private Map<Integer, ChannelContext> contextMap = new HashMap<>();

    public void put(Integer userId, ChannelContext channelContext) {
        contextMap.put(userId, channelContext);
    }

}
