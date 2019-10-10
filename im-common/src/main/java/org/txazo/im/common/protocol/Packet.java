package org.txazo.im.common.protocol;

import lombok.Data;

@Data
public abstract class Packet {

    // 魔数
    public static final int MAGIC = 0xabcdefff;

    // 版本号v1
    public static final byte VERSION_1 = 1;

    // 压缩标识-不压缩
    public static final byte GZIP_NONE = 1;

    // 序列化标识-ProtoBuf
    public static final byte SERIALIZATION_PROTOBUF = 1;

}
