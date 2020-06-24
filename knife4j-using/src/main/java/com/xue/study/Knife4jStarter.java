package com.xue.study;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Author: mf015
 * Date: 2020/5/5 0005
 */
@SpringBootApplication
public class Knife4jStarter {
    private static final Logger LOGGER = LoggerFactory.getLogger(Knife4jStarter.class);

    public static void main(String[] args) {
        SpringApplication.run(Knife4jStarter.class, args);
        LOGGER.info("#### welcome knife4j-using #####");
    }
}
