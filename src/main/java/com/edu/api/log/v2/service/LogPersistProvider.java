package com.edu.api.log.v2.service;

import java.io.Serializable;

/**
 * 提供日志持久化方式
 *
 * @author Yangzhen
 * @Description
 * @date 2019-03-22 16:34
 **/
public interface LogPersistProvider {

    /**
     * 持久化日志，Serializable默认为ApiLogModel
     *
     * @param serializable
     */
    void persist(Serializable serializable);
}
