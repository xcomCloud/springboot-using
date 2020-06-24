package com.xue.study.redis;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class RedisHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisHelper.class);

    @Resource(name = "masterRedisTemp")
    private RedisTemplate redisTempMaster;

    @Resource(name = "slaveRedisTemp")
    private RedisTemplate redisTempSlave;

    private Object dataObj;
    public Object getDataObj() {
        return dataObj;
    }
    public void setDataObj(Object dataObj) {
        this.dataObj = dataObj;
    }

    /**
     * 存list类型
     * @param key
     *
     */
    public void saveListData(String key){
        redisTempMaster.opsForList().rightPush(key, this.getDataObj());
    }

    /**
     * 获取list集合
     * @param key
     * @return
     */
    public List<String> getListData(String key){
        return redisTempSlave.opsForList().range(key, 0, -1);
    }

    public JSONObject saveHash(){
        JSONObject result = new JSONObject();

//        redisTempMaster.opsForHash();
//        redisTempMaster.boundHashOps("q").put("qq", "qqq");
//        redisTempMaster.setHashKeySerializer()
//        redisTempMaster.setHashValueSerializer();

        return result;
    }
}
