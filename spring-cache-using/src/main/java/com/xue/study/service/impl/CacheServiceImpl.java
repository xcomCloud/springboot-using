package com.xue.study.service.impl;

import com.xue.study.service.CacheService;
import net.sf.json.JSONObject;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Author: mf015
 * Date: 2020/8/21 0021
 */
@Service
@CacheConfig(cacheNames = {"myCache"})
public class CacheServiceImpl implements CacheService {

    @Cacheable()
    public JSONObject me1(String id) {
        System.out.println(id);
        System.out.println("-----------------");

        JSONObject fileObj = new JSONObject();
        fileObj.put("1", "2301");
        fileObj.put("2", "2302");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("file", fileObj);
        return jsonObject;
    }
}
