package com.xue.study.controller;

import com.xue.study.service.EnumUsingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author: mf015
 * Date: 2019/10/31 0031
 */
@RestController
public class EnumUsingController {

    @Autowired
    private EnumUsingService enumUsingService;

    @RequestMapping(value = "all-enum-type")
    public String getAllEnumType(){
        List<String> list = enumUsingService.getGoodsType();
        System.out.println("type list=="+list);
        return "200";
    }
}
