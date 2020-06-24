package com.xue.study.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Author: mf015
 * Date: 2019/12/24 0024
 */
@Configuration
public class YamlConfig {

    public static String NAME ;

    @Value("${test.name}")
    private String name;

    @PostConstruct
    void init(){
        NAME = name;
    }
}
