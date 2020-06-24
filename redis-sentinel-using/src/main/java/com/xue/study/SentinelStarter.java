package com.xue.study;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Author: mf015
 * Date: 2020/5/6 0006
 */
@SpringBootApplication
public class SentinelStarter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SentinelStarter.class);

    public static void main(String[] args) {
        SpringApplication.run(SentinelStarter.class);
        LOGGER.info("#### welcome to redis-sentinel-using ####");
    }
}
