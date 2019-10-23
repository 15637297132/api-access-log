package com.edu.api.log.v2.service;

import com.edu.api.log.v2.annotation.ApiAccess;
import com.edu.api.log.v2.annotation.ApiModule;
import com.edu.api.log.v2.model.ApiLogModel;

/**
 * 注解转换日志
 *
 * @author Yangzhen
 * @Description
 * @date 2019-10-08 9:59
 **/
public interface LogTranslateProvider {

    /**
     * 转换注解日志为ApiLogModel
     *
     * @param apiModule
     * @param apiAccess
     * @return
     */
    ApiLogModel logTranslate(ApiModule apiModule, ApiAccess apiAccess);
}
