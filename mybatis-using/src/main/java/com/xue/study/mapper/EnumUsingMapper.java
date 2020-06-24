package com.xue.study.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Author: mf015
 * Date: 2019/10/31 0031
 */
@Mapper
public interface EnumUsingMapper {
    List<String> getGoodsType();
}
