package com.xue.study.controller;

import com.xue.study.redis.RedisHelper;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: mf015
 * Date: 2020/5/6 0006
 */
@RestController
public class TestController {

    @Autowired
    private RedisHelper redisHelper;

    @PostMapping(value = "/test-save")
    public JSONObject save(@RequestBody JSONObject params){
        JSONObject resp = new JSONObject();

        String sn = params.getString("sn");
        redisHelper.set(sn, params);
        resp.put("message", "save-success");
        return resp;
    }

    @GetMapping("get-all")
    public JSONObject getAll(){
        return redisHelper.get();
    }

    @GetMapping("query-cond")
    public JSONObject get1(String sn, String parts){
        return redisHelper.get1(sn, parts);
    }
}
