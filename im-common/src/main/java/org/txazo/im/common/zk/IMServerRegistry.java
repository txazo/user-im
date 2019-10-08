package org.txazo.im.common.zk;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
public class IMServerRegistry {

    public static final String IM_SERVER_ROOT_PATH = "/im/server/list";

    private String zkServers;
    private ZkClient zkClient;

    public IMServerRegistry(String zkServers) {
        this.zkServers = zkServers;
    }

    @PostConstruct
    private void init() {
        try {
            zkClient = new ZkClient(zkServers, 5000, 5000, new JsonSerializer());
        } catch (Exception e) {
            log.error(String.format("ZkClient connect to %s failed", zkServers), e);
        }
        log.debug("ZkClient connected to {}", zkServers);

        if (!zkClient.exists(IM_SERVER_ROOT_PATH)) {
            zkClient.createPersistent(IM_SERVER_ROOT_PATH, true);
        }
    }

    public void registerIMServer(String ip, int port) {
        zkClient.createEphemeral(buildIMServerNodePath(ip, port), new IMServerRegistryBean(ip, port));
        log.debug("IMServer {}:{} registered to ZkClient", ip, port);
    }

    public void unRegisterIMServer(String ip, int port) {
        zkClient.delete(buildIMServerNodePath(ip, port));
    }

    public List<String> getIMServerList() {
        return zkClient.getChildren(IM_SERVER_ROOT_PATH);
    }

    private static String buildIMServerNodePath(String ip, int port) {
        return IM_SERVER_ROOT_PATH + "/" + ip + ":" + port;
    }

}
