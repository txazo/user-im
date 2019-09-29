package org.txazo.im.common.protocol;

import lombok.Data;

@Data
public class LoginRequestPacket extends Packet {

    private Integer userId;

    private String token;

    @Override
    public byte getCommand() {
        return Command.LOGIN_REQUEST;
    }

}
