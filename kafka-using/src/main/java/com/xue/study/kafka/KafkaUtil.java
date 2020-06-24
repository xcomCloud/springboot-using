package com.xue.study.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;

/**
 * Author: mf015
 * Date: 2019/12/6 0006
 *
 * 链接:
 * https://blog.csdn.net/u013144287/article/details/76277295
 */
public class KafkaUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(KafkaUtil.class);

    private static final String TOPIC = "ct_beta_2";
    private static final String ADDRESS = "47.105.195.242:30092";

    private static Producer<String, String> producer;
    private static Consumer<String, String> consumer;
    private KafkaUtil() {
    }

    /**
     * 生产者，注意kafka生产者不能够从代码上生成主题，只有在服务器上用命令生成
     */
    static {
        Properties props = new Properties();
        props.put("bootstrap.servers", ADDRESS);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(props);
        System.out.println("producer is :"+producer);
    }

    /**
     * 消费者
     */
    static {
        Properties props = new Properties();
        props.put("bootstrap.servers", ADDRESS);//服务器ip:端口号，集群用逗号分隔
        props.put("group.id", "group_1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "earliest");//todo
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(TOPIC));
        System.out.println("consumer is :"+consumer);
    }

    /**
     * 发送对象消息 至kafka上,调用json转化为json字符串，应为kafka存储的是String。
     * @param msg
     */
    public static void sendMsgToKafka(String msg) { //String可以转model
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, msg);

        //发送方式1：直接发送
        producer.send(record);

        //发送方式2：
//        try {
//            RecordMetadata recordMetadata = producer.send(record).get();
//            System.out.println(recordMetadata.topic());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //发送方式3: 回调
//        new Callback() {
//            @Override
//            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
//                System.out.println("callback.");
//                if(null != e){
//                    System.out.println("e::"+e);
//                }
//            }
//        }

        System.out.println("===== 发送完成*"+ record + "============");
    }

    /**
     * 从kafka上接收对象消息，将json字符串转化为对象，便于获取消息的时候可以使用get方法获取。
     */
    public static void getMsgFromKafka() {
        System.out.println("==========consume coming===============");
        while (true) {
            ConsumerRecords<String, String> records = KafkaUtil.getKafkaConsumer().poll(1000);
            System.out.println("***records===" + records);
            if (records.count() > 0) {
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("---------开始消费--------------");
                    System.out.println(record+"*"+record.value());
                }
            }
        }
    }

    public static Consumer<String, String> getKafkaConsumer() {
        return consumer;
    }

    public static void closeKafkaProducer() {
        producer.close();
    }


    public static void closeKafkaConsumer() {
        consumer.close();
    }
}
