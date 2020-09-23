package com.xue.study;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Author: mf015
 * Date: 2020/8/21 0021
 */
@SpringBootApplication
@EnableCaching
public class SpringCacheStarter {
    public static final Logger LOGGER = LoggerFactory.getLogger(SpringCacheStarter.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringCacheStarter.class, args);
        LOGGER.info("#### spring-cache-using started ####");
    }
}