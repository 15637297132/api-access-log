package com.edu.api.log.v2.annotation;

import com.edu.api.log.v2.constants.ApiLogTypeEnum;

import java.lang.annotation.*;

/**
 * @author Yangzhen
 * @Description
 * @date 2019-03-19 10:53
 **/
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiAccess {

    /**
     * 请求动作
     *
     * @return
     */
    ApiAction action() default @ApiAction;

    /**
     * 日志类型
     */
    int logType() default ApiLogTypeEnum.OPERATE_LOG_TYPE;

    /**
     * 操作标题
     *
     * @return
     */
    String operateTitle();

    /**
     * 操作方法
     *
     * @return
     */
    String[] operateMethod();

    /**
     * operateMethod的可选值，例如operateMethod = {"","启用","禁用"}，你需要指定当前操作是启用还是禁用，
     * 指定key，将key和value（下标）存入RequestContext
     */
    String indexKeyForOperateMethod() default "";

    /**
     * 指定操作的数据的id的key
     * 将key、value存入RequestContext
     *
     * @return
     */
    String dataIdKey();

    /**
     * 处理登出操作，因为用户信息只能在执行登出操作之前获取，否则可能会出现问题
     * 登出接口必须要设置此参数为true
     *
     * @return
     */
    boolean logout() default false;

}
