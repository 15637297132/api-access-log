package com.edu.api.log.v2.model;

import java.io.Serializable;

/**
 * @author Yangzhen
 * @Description
 * @date 2019-09-29 15:43
 **/
public class ApiLogModel implements Serializable {

    private LoginInfo loginInfo;

    /**
     * dataId相同的数据不一定是一类数据，配合operateModuleId来区分
     */
    private String dataId;
    /**
     * 操作模块id
     */
    private Integer moduleId;
    /**
     * 操作模块名称
     */
    private String moduleName;
    /**
     * 操作标题
     */
    private String operateTitle;
    /**
     * 操作方法
     */
    private String operateMethod;
    /**
     * 操作详细
     */
    private String operateLog;
    /**
     * 操作结果（0：成功，1：失败）
     */
    private Integer operateResult;
    /**
     * 操作失败记录
     */
    private String operateFailLog;
    /**
     * 登录结果（0：登录成功，1：登录失败，2：登出成功，3：登出失败）
     */
    private Integer loginResult;

    /**
     * 日志类型（1：登录日志；2：操作日志）
     */
    private Integer logType;

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getOperateTitle() {
        return operateTitle;
    }

    public void setOperateTitle(String operateTitle) {
        this.operateTitle = operateTitle;
    }

    public String getOperateMethod() {
        return operateMethod;
    }

    public void setOperateMethod(String operateMethod) {
        this.operateMethod = operateMethod;
    }

    public String getOperateLog() {
        return operateLog;
    }

    public void setOperateLog(String operateLog) {
        this.operateLog = operateLog;
    }

    public Integer getOperateResult() {
        return operateResult;
    }

    public void setOperateResult(Integer operateResult) {
        this.operateResult = operateResult;
    }

    public String getOperateFailLog() {
        return operateFailLog;
    }

    public void setOperateFailLog(String operateFailLog) {
        this.operateFailLog = operateFailLog;
    }

    public Integer getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(Integer loginResult) {
        this.loginResult = loginResult;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }
}
