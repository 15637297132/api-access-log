package com.edu.api.log.v2.service;

import com.edu.api.log.v2.model.LoginInfo;

/**
 * @author Yangzhen
 * @Description
 * @date 2019-09-30 15:16
 **/
public interface LoginInfoService {

    /**
     * 获取登录信息
     *
     * @return
     */
    LoginInfo getLoginInfo();
}
