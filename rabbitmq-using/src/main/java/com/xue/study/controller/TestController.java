package com.xue.study.controller;

import com.xue.study.amqp.MsgCenter;
import com.xue.study.amqp.MsgHandler;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: mf015
 * Date: 2020/6/1 0001
 */
@RestController
public class TestController {
    @Autowired
    private MsgCenter msgCenter;

    @PostMapping(value = "send1")
    public String testSend1(@RequestBody JSONObject msg) {
        msgCenter.send(msg);
        return "send-ok";
    }
}
