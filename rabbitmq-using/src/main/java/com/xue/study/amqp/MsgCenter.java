package com.xue.study.amqp;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Author: mf015
 * Date: 2020/6/3 0003
 */
@Service
public class MsgCenter {
    @Autowired
    private Producer msgProducer;

    public void send(JSONObject msg) {
        msgProducer.send(msg);
    }
}
