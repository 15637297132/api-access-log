package com.edu.api.log.v2.annotation;

import com.edu.api.log.v2.constants.ApiConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Yangzhen
 * @Description
 * @date 2019-03-19 10:53
 **/
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Compare {

    String prefix() default ApiConstants.BLANK;

    String codeKey() default ApiConstants.BLANK;

    String newObjKey() default ApiConstants.BLANK;

    String oldObjKey() default ApiConstants.BLANK;

    String suffix() default ApiConstants.BLANK;
}
