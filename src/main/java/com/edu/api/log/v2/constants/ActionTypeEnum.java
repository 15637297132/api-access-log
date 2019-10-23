package com.edu.api.log.v2.constants;

/**
 * @author Yangzhen
 * @Description
 * @date 2019-09-29 16:35
 **/
public enum ActionTypeEnum {

    /**
     * 忽略
     */
    NON,
    /**
     * 查询
     */
    GET,
    /**
     * 提交，例如新增数据
     */
    POST,
    /**
     * 更新
     */
    PUT,
    /**
     * 删除
     */
    DELETE,
    /**
     * 修改密码
     */
    PASSWORD,
    /**
     * 禁用启用
     */
    ENABLED;
}
