package com.edu.api.log.v2.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据的拼接是prefix + RequestManager.getAttribute(codeKey) + customString + suffix
 * 修改了集团“ + xxx + 自定义字符串 + ”的信息
 * @author Yangzhen
 * @Description
 * @date 2019-03-19 10:53
 **/
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Spell {

    /**
     * prefix = 修改了集团“
     * 修改了集团“xxx”的信息：
     */
    String prefix() default "";

    /**
     * 动态数据的key
     * 动态数据 = RequestManager.getAttribute(codeKey)
     */
    String codeKey() default "";

    /**
     * 自定义字符串内容
     */
    String customString() default "";

    /**
     * suffix = ”的信息
     */
    String suffix() default "";

}
