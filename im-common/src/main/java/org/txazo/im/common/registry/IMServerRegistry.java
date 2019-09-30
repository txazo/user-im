package org.txazo.im.common.registry;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;

import javax.annotation.PostConstruct;

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
            zkClient = new ZkClient(zkServers, 5000, 5000);
        } catch (Exception e) {
            log.error(String.format("ZkClient connect to %s failed", zkServers), e);
        }
        log.debug("ZkClient connected to {}", zkServers);

        zkClient.createPersistent(IM_SERVER_ROOT_PATH, true);
    }

    public void registerIMServer(String ip, int port) {
        zkClient.createEphemeralSequential(IM_SERVER_ROOT_PATH + "/" + buildIMServerNodeName(ip, port), null);
    }

    public void unRegisterIMServer(String ip, int port) {
        zkClient.delete(IM_SERVER_ROOT_PATH);
    }

    public void getIMServerList() {

    }

    private static String buildIMServerNodeName(String ip, int port) {
        return ip + ":" + port;
    }

}
