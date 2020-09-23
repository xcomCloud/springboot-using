package com.xue.study.controller;

import com.xue.study.service.CacheService;
import net.sf.json.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: mf015
 * Date: 2020/8/21 0021
 */
@RestController
public class CacheController {
    @Autowired
    private CacheService cacheService;

    @GetMapping("/m1")
    public JSON m1Test(String id) {
        return cacheService.me1(id);
    }
}
