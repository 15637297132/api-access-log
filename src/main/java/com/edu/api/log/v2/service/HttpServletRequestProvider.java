package com.edu.api.log.v2.service;

import javax.servlet.http.HttpServletRequest;

/**
 * 提供HttpServletRequest
 *
 * @author Yangzhen
 * @Description
 * @date 2019-10-11 10:24
 **/
public interface HttpServletRequestProvider {

    /**
     * 提供HttpServletRequest的实现
     *
     * @return
     */
    HttpServletRequest getHttpServletRequest();
}
