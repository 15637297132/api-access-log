package com.edu.api.log.v2.annotation;

import com.edu.api.log.v2.constants.ActionTypeEnum;

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
public @interface ApiAction {

    /**
     * 请求类型
     */
    ActionTypeEnum actionType() default ActionTypeEnum.NON;

    /**
     * 拼接内容
     */
    Spell[] spell() default @Spell;

    /**
     * 比较内容，actionType=PUT时，比较两个对象的值（可选），当compare和spell同时存在时，优先compare拼接
     *
     * @return
     */
    Compare compare() default @Compare;

}
