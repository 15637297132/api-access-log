package com.edu.api.log.v2.service;

import com.edu.api.log.v2.model.ApiLogModel;

/**
 * 返回值分析，自定义业务返回值判断
 *
 * @author Yangzhen
 * @Description
 * @date 2019-10-08 11:29
 **/
public interface ReturnResultAnalyseProvider {

    /**
     * @param result
     * @param apiLogModel
     * @AfterReturning正常返回时调用，包括返回值为 return void，return null，return Object
     * 实现此方法，需要根据你的逻辑自定义设置
     * ApiLogModel#setOperateResult
     * ApiLogModel#setLoginResult
     * ApiLogModel#setOperateFailLog
     */
    void analyseResult(Object result, ApiLogModel apiLogModel);

    /**
     * 区分login和logout设置ApiLogModel#setLoginResult
     *
     * @param result
     * @param apiLogModel
     */
    void analyseLogout(Object result, ApiLogModel apiLogModel);
}
