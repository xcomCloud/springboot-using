package com.xue.study.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
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
 * Date: 2020/6/1 0001
 *
 * 废弃类
 */
@Configuration
public class RabbitConf {
//    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitConf.class);
//
//    @Value("${spring.rabbitmq.addresses}")
//    private String address;
//    @Value("${spring.rabbitmq.username}")
//    private String username;
//    @Value("${spring.rabbitmq.password}")
//    private String password;
//    @Value("${spring.rabbitmq.virtual-host}")
//    private String virtualHost;
//    @Value("${spring.rabbitmq.publisher-returns}")
//    private boolean returnCallBack;
//    @Value("${spring.rabbitmq.publisher-confirms}")
//    private boolean confirmCallBack;
//
//    @Value("${spring.rabbitmq.template.mandatory}")
//    private boolean mandatory;
//
//    //故障消息
//    @Value("${spring.rabbitmq.exchange.msg-fault}")
//    public String exchangeMsgFlt;
//    @Value("${spring.rabbitmq.routing-key.fault-info}")
//    public String routeFltInfo;
//    @Value("${spring.rabbitmq.queue.fault-info}")
//    public String queueFltInfo;
//
//    //建立连接通信
//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setAddresses(this.address);
//        connectionFactory.setUsername(this.username);
//        connectionFactory.setPassword(this.password);
//        connectionFactory.setVirtualHost(this.virtualHost);
//
//        connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CONNECTION);
//        connectionFactory.setConnectionCacheSize(10);
//        connectionFactory.setPublisherReturns(this.returnCallBack);
//        connectionFactory.setPublisherConfirms(this.confirmCallBack);
//        LOGGER.info("rabbitmq connectionFactory :" + connectionFactory);
//        return connectionFactory;
//    }
//
//    //
//    @Bean
//    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
//        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
//        rabbitAdmin.setAutoStartup(true);
//        rabbitAdmin.setIgnoreDeclarationExceptions(false);
//
//        //exchange\routing-key\queue 管理员设置
//        rabbitAdmin.declareExchange(directExchangeMsgFlt());
//        rabbitAdmin.declareQueue(declareQueueFltInfo());
//        rabbitAdmin.declareBinding(bindingDirectExchange());
//
//        return rabbitAdmin;
//    }
//
//    //////////////////
//    /**
//     * 声明交换机、队列、绑定关系
//     *
//     */
//    @Bean
//    public DirectExchange directExchangeMsgFlt(){
//        return new DirectExchange(this.exchangeMsgFlt);
//    }
//
////    public FanoutExchange fanoutExchange(){
////        return new FanoutExchange("");
////    }
////
////    public TopicExchange topicExchange(){
////        return new TopicExchange("");
////    }
//
//    //
//    @Bean
//    public Queue declareQueueFltInfo(){
//        return new Queue(this.queueFltInfo);
//    }
////    public Queue fltInfoDLXQueue(){
////        return new Queue("");
////    }
//
//    //
//    @Bean
//    public Binding bindingDirectExchange(){
//        return BindingBuilder.bind(declareQueueFltInfo()).to(directExchangeMsgFlt()).with(this.routeFltInfo);
//    }
//
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    @Bean
//    public RabbitTemplate rabbitTemplate() {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(this.connectionFactory());
//        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
////        rabbitTemplate.setMandatory(this.mandatory);
//
////        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
////            @Override
////            public void returnedMessage(Message message, int i, String s, String s1, String s2) {
////                LOGGER.info("returnedMessage :" + message);
////            }
////        });
//
////        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
////            @Override
////            public void confirm(CorrelationData correlationData, boolean ack, String s) {
////                LOGGER.info("ack=="+ack);
////                LOGGER.info("confirm :" + correlationData);
////            }
////        });
//
//        return rabbitTemplate;
//    }
//
////    @Bean
//    public AsyncRabbitTemplate asyncRabbitTemplate() {
//        return new AsyncRabbitTemplate(this.rabbitTemplate());
//    }
}
