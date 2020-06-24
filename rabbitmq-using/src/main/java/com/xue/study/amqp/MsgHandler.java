package com.xue.study.amqp;

import com.xue.study.config.RabbitConf;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author: mf015
 * Date: 2020/6/1 0001
 *
 * 废弃类
 */
//@Component
public class MsgHandler {
//    private static final Logger LOGGER = LoggerFactory.getLogger(MsgHandler.class);
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    @Autowired
//    private RabbitConf rabbitConf;
//
//    public void sendMsg(JSONObject msg) {
//        String exchange = rabbitConf.exchangeMsgFlt;
//        String routingKey = rabbitConf.routeFltInfo;
//
//        rabbitTemplate.convertAndSend(exchange, routingKey, msg);
//    }
//
//    @RabbitListener(queues = "QFltInfo")
//    public void onMessage(Message messag) {
//        this.consumer(String.valueOf(messag));
//    }
//
//    private void consumer(String msg) {
//        LOGGER.info("msg=" + msg);
//    }

/////////////////////////////////////////////////////////////////////////////////////bak
//    public void sendMsg(JSONObject msg) {
////        CorrelationData correlationData = new CorrelationData();
////        String taskId = "1001";
////        correlationData.setId(taskId);
////        Message message = MessageBuilder.withBody(msg.getBytes()).build();
////        rabbitTemplate.convertAndSend("flt-key", message, correlationData);
//
//        String exchange = rabbitConf.exchangeMsgFlt;
//        String routingKey = rabbitConf.routeFltInfo;
//
//        rabbitTemplate.convertAndSend(exchange, routingKey, msg);
//        System.out.println("----send over----");
//    }
//
//    @RabbitListener(queues = "QFltInfo")
//    public void onMessage(Message messag) {
//        this.consumer(String.valueOf(messag));
//
////        try {
////            channel.basicAck(delivery, false);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//    }
//
//    private void consumer(String msg) {
//        LOGGER.info("msg=" + msg);
//    }

}
