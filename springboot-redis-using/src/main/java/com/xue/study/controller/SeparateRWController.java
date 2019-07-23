package com.xue.study.controller;

import com.xue.study.service.SeparateRWService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SeparateRWController {

    @Autowired
    private SeparateRWService separateRWService;

    /**
     * 读写分离，切换redisTemplate;
     * 测试存储
     * @param key
     * @return
     */
    @RequestMapping(value = "test/separate-read-write/save-data", method = RequestMethod.POST)
    @ResponseBody
    public String testSaveData(@RequestBody String key){
        return separateRWService.saveListData(key);
    }

    /**
     * 读写分离，切换redisTemplate;
     * 测试获取
     * @param key
     * @return
     */
    @RequestMapping(value = "test/separate-read-write/get-data", method = RequestMethod.POST)
    @ResponseBody
    public List<String> testGetData(@RequestBody String key){
        return separateRWService.getDataList(key);
    }



}
