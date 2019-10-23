package com.edu.api.log.v2.model;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录信息
 *
 * @author Yangzhen
 * @Description
 * @date 2019-09-30 15:18
 **/
public class LoginInfo implements Serializable {
    /**
     * 操作者id
     */
    @NotBlank
    private String operatorId;
    /**
     * 操作者姓名
     */
    @NotBlank
    private String operator;
    /**
     * 操作者用户名
     */
    @NotBlank
    private String username;
    /**
     * 操作时间
     */
    private Date operateTime;
    /**
     * ip
     */
    private String ip;
    /**
     * 地点
     */
    private String location;
    /**
     * 浏览器
     */
    private String browser;
    /**
     * 设备类型
     */
    private String device;

    private String groupId;

    private String subGroupId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSubGroupId() {
        return subGroupId;
    }

    public void setSubGroupId(String subGroupId) {
        this.subGroupId = subGroupId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
