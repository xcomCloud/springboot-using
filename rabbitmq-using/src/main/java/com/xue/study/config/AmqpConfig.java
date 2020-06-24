package com.xue.study.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: mf015
 * Date: 2020/6/3 0003
 */
@Configuration
public class AmqpConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitConf.class);

    @Value("${spring.rabbitmq.addresses}")
    private String address;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    @Value("${spring.rabbitmq.publisher-confirms}")
    private boolean publishConfirm;
    @Value("${spring.rabbitmq.publisher-returns}")
    private boolean publishReturn;
//    @Value("${spring.rabbitmq.listener.direct.acknowledge-mode}")
//    private String ackMode;

    //故障消息
    @Value("${spring.rabbitmq.exchange.msg-fault}")
    public String exchangeMsgFlt;
    @Value("${spring.rabbitmq.routing-key.fault-info}")
    public String routeFltInfo;
    @Value("${spring.rabbitmq.queue.fault-info}")
    public String queueFltInfo;

    //建立连接通信
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(this.address);
        connectionFactory.setUsername(this.username);
        connectionFactory.setPassword(this.password);
        connectionFactory.setVirtualHost(this.virtualHost);

        connectionFactory.setPublisherConfirms(this.publishConfirm);
        connectionFactory.setPublisherReturns(this.publishReturn);
        connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CONNECTION);
        connectionFactory.setConnectionCacheSize(10);

        LOGGER.info("rabbitmq connectionFactory :" + connectionFactory);
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        rabbitAdmin.setIgnoreDeclarationExceptions(false);

        rabbitAdmin.declareExchange(directExchangeMsgFlt());
        rabbitAdmin.declareQueue(declareQueueFltInfo());
        rabbitAdmin.declareBinding(bindingDirectExchange());
        return rabbitAdmin;
    }

    @Bean
    public DirectExchange directExchangeMsgFlt() {
        return new DirectExchange(this.exchangeMsgFlt);
    }

    @Bean
    public Queue declareQueueFltInfo() {
        return new Queue(this.queueFltInfo);
    }

    @Bean
    public Binding bindingDirectExchange() {
        return BindingBuilder.bind(declareQueueFltInfo()).to(directExchangeMsgFlt()).with(this.routeFltInfo);
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(this.connectionFactory());
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        //消息发送失败重新返回队列
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            LOGGER.error("send message to queue fail: message={}, replyCode={}, replyText={}, exchange={}, routingKey={}",
                    message, replyCode, replyText, exchange, routingKey);
        });
        rabbitTemplate.setConfirmCallback((message, ack, cause) -> {
            if (!ack) {
                LOGGER.error("send msg to exchange fail: message={}, cause={}", message, cause);
            }
        });

//        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
//            @Override
//            public void confirm(CorrelationData correlationData, boolean confirm, String s) {
//                //发送消息到交换机失败
//                if(!confirm){
//                    LOGGER.error("confirm fail!");
//                    if(null != correlationData){
//                        LOGGER.info("confirm missed msg:" +
//                                new String(Objects.requireNonNull(correlationData.getReturnedMessage()).getBody(), StandardCharsets.UTF_8));
//                    }
//                }
//            }
//        });

//        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
//            @Override
//            public void returnedMessage(Message message, int i, String s, String s1, String s2) {
//                //发送到队列失败
//                LOGGER.info("returnedMessage-message="+new String(message.getBody()));
//            }
//        });
//        rabbitTemplate.containerAckMode(AcknowledgeMode.MANUAL);
        return rabbitTemplate;
    }
}
