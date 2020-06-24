package com.xue.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Author: mf015
 * Date: 2019/12/24 0024
 *
 * @AutoWired
 * @Vaue
 *  --都是用到时时候去加载，有的话就是单例的。。。
 *
 */
@SpringBootApplication
public class ConfigStart {
    public static void main(String[] args) {

        SpringApplication.run(ConfigStart.class, args);
    }
}
