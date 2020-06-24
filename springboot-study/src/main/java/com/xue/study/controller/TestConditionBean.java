package com.xue.study.controller;

import com.xue.study.beans.ConditionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: mf015
 * Date: 2019/10/23 0023
 */
@RestController
public class TestConditionBean {

    @Autowired
    private ConditionBean conditionBean;

    /**
     * 结论：@ConditionOnBean(name="condition") OK，
     * 只有存在时才创建；
     *
     * @return
     */
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String test(){
        System.out.println("conditionBean::"+conditionBean.toString());
        return "";
    }


}
