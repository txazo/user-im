package org.txazo.im.common.protocol;

import com.google.protobuf.AbstractMessage;

import java.util.HashMap;
import java.util.Map;

public abstract class CommandType {

    public static final byte HeartbeatRequest = 1;
    public static final byte HeartbeatResponse = 2;

    public static final Map<Class<? extends AbstractMessage>, Byte> MESSAGE_COMMAND_MAP = new HashMap<>();

    static {
        MESSAGE_COMMAND_MAP.put(MessageBody.HeartbeatRequestPacket.class, HeartbeatRequest);
        MESSAGE_COMMAND_MAP.put(MessageBody.HeartbeatResponsePacket.class, HeartbeatResponse);
    }

    public static Byte getCommandType(Class<? extends AbstractMessage> messageType) {
        return MESSAGE_COMMAND_MAP.get(messageType);
    }

}
