package com.xue.study.controller;

import com.xue.study.mongo.MongoHelper;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestConroller {

    @Autowired
    private MongoHelper mongoHelper;

    @RequestMapping(value = "test", method = RequestMethod.POST)
    public JSONObject test(@RequestBody String pathId) {
        JSONObject json = new JSONObject();

        try {
            mongoHelper.upsertData(pathId);
            json.put("message", "success");
        } catch (Exception e) {
            json.put("message", e.getMessage());
        }
        return json;
    }

    @RequestMapping(value = "get", method = RequestMethod.POST)
    public JSONObject getPoints(@RequestBody String pathId) {
        return mongoHelper.findPointsById(pathId);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public JSONObject save(@RequestBody JSONObject params) {
        String id = params.getString("id");
        String command = params.getString("command");

        JSONObject json = new JSONObject();
        try {
            mongoHelper.upsertPointsData(id, command);
            json.put("message", "success");
        } catch (Exception e) {
            json.put("message", e.getMessage());
        }
        return json;
    }

    @RequestMapping(value = "find", method = RequestMethod.POST)
    public JSONObject find(@RequestBody JSONObject params) {
        String id = params.getString("id");
        String command = params.getString("command");

        return mongoHelper.findPointsById(id, command);
    }
}
