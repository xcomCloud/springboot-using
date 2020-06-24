package com.xue.study.service.impl;

import com.xue.study.mapper.EnumUsingMapper;
import com.xue.study.service.EnumUsingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: mf015
 * Date: 2019/10/31 0031
 */
@Service
public class EnumUsingServiceImpl implements EnumUsingService {

    @Autowired
    private EnumUsingMapper enumUsingMapper;

    @Override
    public List<String> getGoodsType() {
        return enumUsingMapper.getGoodsType();
    }
}
