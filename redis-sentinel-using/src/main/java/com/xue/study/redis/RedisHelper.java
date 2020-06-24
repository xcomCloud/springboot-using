package com.xue.study.redis;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * Author: mf015
 * Date: 2020/5/6 0006
 */
@Component
public class RedisHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisHelper.class);

    @Resource
    private RedisTemplate<String, JSONObject> redisTemplate;

    public void set(String key, JSONObject jsonValue){
        key = "flt_" + key;
        String hk = "faultInfo";
        Iterator it = jsonValue.keys();
        try {
            Map<String, Object> map = new HashMap<>();
            while (it.hasNext()){
//                JSONObject.fromObject(it.next())
//                redisTemplate.opsForHash()
                String next = it.next().toString();
                if("sn".equalsIgnoreCase(next)){
                    map.put(next, jsonValue.getString(next));
                    break;
                }else if("msgTime".equalsIgnoreCase(next)){
                    map.put(next, jsonValue.getString(next));
                    break;
                }else if("fltVRank".equalsIgnoreCase(next)){
                    map.put(next, jsonValue.getInt(next));
                }else {
                    map.put(next, jsonValue.getJSONObject(next));
                }
            }
            redisTemplate.opsForHash().putAll(key, map);
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public void set1(){

    }

    public JSONObject get() {
        JSONArray array = new JSONArray();
        JSONObject result = new JSONObject();
        Iterator it = Objects.requireNonNull(redisTemplate.keys("flt_" + "*")).iterator();
        while (it.hasNext()) {
            String nextKey = it.next().toString();
            Map<Object, Object> map = redisTemplate.opsForHash().entries(nextKey);
            result.put("sn", map.get("sn"));
            result.put("msgTime", map.get("msgTime"));
            result.put("fltVRank", map.get("fltVRank"));
            result.put("faultInfo", map.get("faultInfo"));
            LOGGER.info("**" + result.toString());
            array.add(result);
            result.clear();
        }

        JSONObject resp = new JSONObject();
        resp.put("retcode", 0);
        resp.put("fltList", array);
        array.clear();
        return resp;
    }

    public JSONObject get1(String key, String hk){
        key = "flt_"+key;
        JSONObject hkJson = JSONObject.fromObject(redisTemplate.opsForHash().get(key, hk));
        LOGGER.info("***"+hkJson.toString());
        return null;
    }

}
