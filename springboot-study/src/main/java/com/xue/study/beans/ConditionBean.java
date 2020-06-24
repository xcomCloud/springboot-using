package com.xue.study.beans;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

/**
 * Author: mf015
 * Date: 2019/10/23 0023
 */

@Component
@ConditionalOnBean(name = "condition")
public class ConditionBean {
}
