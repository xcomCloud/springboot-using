package com.xue.study.amqp;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Author: mf015
 * Date: 2020/6/1 0001
 */
@Component
public class Consumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    @RabbitListener(queues = "QFltInfo")
    public void onMessageFltInfo(Message msg, Channel channel) {
        if (ack(msg, channel)) {
            // TODO: 2020/6/4
            //  业务处理 service
            //
            //  0004
            LOGGER.info("msg =" + new String(msg.getBody(), StandardCharsets.UTF_8));
        }
    }

    private boolean ack(Message message, Channel channel) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
            return true;
        } catch (IOException e) {
            //丢弃该消息
//            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true, false);

            LOGGER.error("ack fail, message={}.", message);
            return false;
        }
    }

//    @RabbitListener(queues = "QfltList")
//    public void onMessageFltList(String msg){
//        LOGGER.info("flt list msg="+msg);
//    }
}
