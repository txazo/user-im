package org.txazo.im.server.netty.channel;

import java.util.HashMap;
import java.util.Map;

public class ChannelManager {

    private Map<Integer, ChannelContext> contextMap = new HashMap<>();

    public void put(Integer userId, ChannelContext channelContext) {
        contextMap.put(userId, channelContext);
    }

}
