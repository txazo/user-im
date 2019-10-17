package org.txazo.im.common.protocol;

import com.google.protobuf.AbstractMessage;

import java.util.HashMap;
import java.util.Map;

public abstract class CommandType {

    public static final byte HeartbeatRequest = 1;
    public static final byte HeartbeatResponse = 2;
    public static final byte AuthRequest = 3;
    public static final byte AuthResponse = 4;

    public static final Map<Class<? extends AbstractMessage>, Byte> MESSAGE_COMMAND_MAP = new HashMap<>();

    static {
        MESSAGE_COMMAND_MAP.put(MessageBody.HeartbeatRequestPacket.class, HeartbeatRequest);
        MESSAGE_COMMAND_MAP.put(MessageBody.HeartbeatResponsePacket.class, HeartbeatResponse);
        MESSAGE_COMMAND_MAP.put(MessageBody.AuthRequestPacket.class, AuthRequest);
        MESSAGE_COMMAND_MAP.put(MessageBody.AuthResponsePacket.class, AuthResponse);
    }

    public static Byte getCommandType(Class<? extends AbstractMessage> messageType) {
        return MESSAGE_COMMAND_MAP.get(messageType);
    }

}
