package com.xue.study.kafka;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Author: mf015
 * Date: 2019/12/6 0006
 */
public class Test {
    public static void main(String[] args) {

        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < 100; ++i) {
            json.put("msgId", "10101011");
            json.put("msgContent", "aabbccdd");
            json.put("count", i);
            jsonArray.add(json);
        }

        System.out.println(jsonArray.toString());
        KafkaUtil.sendMsgToKafka(jsonArray.toString());

        KafkaUtil.closeKafkaProducer();


        KafkaUtil.getMsgFromKafka();

        KafkaUtil.closeKafkaConsumer();


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).start();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).start();
    }
}
