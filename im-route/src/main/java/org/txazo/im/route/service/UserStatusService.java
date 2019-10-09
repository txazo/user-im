package org.txazo.im.route.service;

public interface UserStatusService {

    public void online(Integer userId, String connectorId);

    public void offline(Integer userId, String connectorId);

    public String getConnector(Integer userId);

}
