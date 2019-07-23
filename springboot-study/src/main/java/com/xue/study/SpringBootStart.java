package com.xue.study;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootStart {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringBootStart.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootStart.class, args);
        LOGGER.info("### welcome to springboot-study ###");
    }
}
