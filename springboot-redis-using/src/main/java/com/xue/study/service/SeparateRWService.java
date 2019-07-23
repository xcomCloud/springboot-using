package com.xue.study.service;

import com.xue.study.redis.RedisHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeparateRWService {
    @Autowired
    private RedisHelper redisHelper;

    public String saveListData(String key){
        for (int i = 0; i < 2; ++i) {
            String data = "data-"+ i;
            redisHelper.setDataObj(data);
            redisHelper.saveListData(key);
        }
        return "save data success.";
    }

    public List<String> getDataList(String key){
        List<String> dataList = redisHelper.getListData(key);
        for (String data : dataList) {
            System.out.println("data is :"+data);
        }
        return dataList;
    }
}
