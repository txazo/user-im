package org.txazo.im.common.zk;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IMServerRegistryBean {

    private String ip;

    private Integer port;

    public IMServerRegistryBean(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

}
