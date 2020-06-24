package com.xue.study;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * func1. 使用配置文件方式  --Spring.xml
 * func2. 使用注解
 *
 */

@SpringBootApplication
public class AmqpUsingStart {
    private static final Logger LOGGER = LoggerFactory.getLogger(AmqpUsingStart.class);

    public static void main(String[] args) {
        SpringApplication.run(AmqpUsingStart.class, args);
        LOGGER.info("### welcome to rabbitmq-using ###");
    }
}
