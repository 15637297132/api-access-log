package com.edu.api.log.v2.annotation;

import com.edu.api.log.v2.constants.ApiConstants;

import java.lang.annotation.*;

/**
 * 模块
 *
 * @author Yangzhen
 * @Description
 * @date 2019-03-19 10:53
 **/
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiModule {

    /**
     * 模块id
     */
    int moduleId() default ApiConstants.DEFAULT_0;

    /**
     * 模块名称
     */
    String moduleName() default ApiConstants.BLANK;
}
