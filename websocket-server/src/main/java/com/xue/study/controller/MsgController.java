package com.xue.study.controller;

import com.xue.study.service.MsgService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class MsgController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgController.class);

    @Autowired
    private MsgService msgService;

    @RequestMapping(value = "/websocket-server/api/v1/send-msg", method = RequestMethod.POST)
    public JSONObject sendMsg(@RequestBody JSONObject params){
        System.out.println("new msg is coming...");

        JSONObject ret = new JSONObject();

        /**
         * 入参校验
         */
        //包含必须键
        String[] requiredParams = {"clients", "message"};
        for (Object param : requiredParams) {
            if(!params.containsKey(param)){
                LOGGER.error("params miss required param:" + param.toString());
                ret.put("message","miss required param :"+param.toString());
                return ret;
            }
        }
        //要求键值不为空
        String[] notEmptyParams = {"clients", "message"};
        for (Object param: notEmptyParams) {
            if(ObjectUtils.isEmpty(params.get(param))){
                LOGGER.error(String.format("param [%s] is empty,", param));
                ret.put("message", String.format("param [%s] is empty,", param));
                return ret;
            }
        }

        return msgService.sendMsgByPoint(params);

        //群发 暂时保留
        //return msgService.sendMsgByGroup(params);
    }
}
