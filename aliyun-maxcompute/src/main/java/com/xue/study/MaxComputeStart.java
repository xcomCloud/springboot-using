package com.xue.study;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Author: mf015
 * Date: 2020/3/23 0023
 */
@SpringBootApplication
public class MaxComputeStart {
    private static final Logger LOGGER = LoggerFactory.getLogger(MaxComputeStart.class);

    public static void main(String[] args) {
        SpringApplication.run(MaxComputeStart.class, args);
        LOGGER.info("#### welcome aliyun-maxcompute ####");
    }
}
