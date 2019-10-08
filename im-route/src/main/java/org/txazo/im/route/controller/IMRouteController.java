package org.txazo.im.route.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.txazo.im.common.zk.IMServerRegistry;

@RestController
@RequestMapping("/im/route")
public class IMRouteController {

    @Autowired
    private IMServerRegistry imServerRegistry;

    @ResponseBody
    @RequestMapping(value = "/serverList", method = RequestMethod.GET)
    public Object serverList() {
        return imServerRegistry.getIMServerList();
    }

}
