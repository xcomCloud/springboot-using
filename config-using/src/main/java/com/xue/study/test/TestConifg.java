package com.xue.study.test;

import com.xue.study.config.YamlConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Author: mf015
 * Date: 2019/12/24 0024
 */
@Component
public class TestConifg {

    @Value("${test.name}")
    private String name;

    private final static String initName = YamlConfig.NAME;

    public void test(){
        System.out.println("name=="+name);
        System.out.println("test()--initName=="+initName);
    }


    public void test1(){
        System.out.println("test1()--initName=="+initName);
        System.out.println("Constant--initName=="+YamlConfig.NAME);
    }
}
