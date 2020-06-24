package com.xue.study.amqp;

import com.xue.study.config.AmqpConfig;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author: mf015
 * Date: 2020/6/3 0003
 */
@Component
public class Producer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private AmqpConfig amqpConfig;

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    @Autowired
//    private AsyncRabbitTemplate asyncRabbitTemplate;

    public void send(JSONObject msg) {
        LOGGER.info("send msg :" + msg);
        rabbitTemplate.setExchange(amqpConfig.exchangeMsgFlt);
        rabbitTemplate.setRoutingKey(amqpConfig.routeFltInfo);
        rabbitTemplate.convertAndSend(msg);
    }

//    @Override
//    public void confirm(CorrelationData correlationData, boolean confirm, String s) {
//        LOGGER.info("confirm : " + confirm);
//        if (!confirm) {
//            LOGGER.error("send msg fail:" + correlationData + "[|]" + correlationData.getReturnedMessage());
//        }
//    }
//
//    @Override
//    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
//        LOGGER.info("**returnedMessage:" + new String(message.getBody(), StandardCharsets.UTF_8));
//    }
}
