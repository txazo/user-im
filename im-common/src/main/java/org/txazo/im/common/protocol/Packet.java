package org.txazo.im.common.protocol;

import lombok.Data;

@Data
public abstract class Packet {

    private byte version;

    public abstract byte getCommand();

}
