package com.xue.study.controller;

import com.xue.study.test.TestConifg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Author: mf015
 * Date: 2019/12/24 0024
 */
@Controller
public class TestConfigController {

    @Autowired
    private TestConifg testConifg;

    @GetMapping(value = "test")
    public String test(){

        testConifg.test();

        testConifg.test1();

        return "success";
    }
}
