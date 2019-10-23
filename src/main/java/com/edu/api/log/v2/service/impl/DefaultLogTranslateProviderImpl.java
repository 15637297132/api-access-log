package com.edu.api.log.v2.service.impl;

import com.edu.api.log.v2.annotation.*;
import com.edu.api.log.v2.constants.ActionTypeEnum;
import com.edu.api.log.v2.constants.ApiConstants;
import com.edu.api.log.v2.exception.UnCertifiedException;
import com.edu.api.log.v2.model.ApiLogModel;
import com.edu.api.log.v2.model.LoginInfo;
import com.edu.api.log.v2.service.LogTranslateProvider;
import com.edu.api.log.v2.service.LoginInfoProvider;
import com.edu.api.log.v2.util.Jsr303Utils;
import com.edu.api.log.v2.util.ObjectCompareUtils;
import com.edu.api.log.v2.util.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @author Yangzhen
 * @Description
 * @date 2019-10-08 10:26
 **/
public class DefaultLogTranslateProviderImpl implements LogTranslateProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultLogTranslateProviderImpl.class);

    @Resource
    private LoginInfoProvider loginInfoService;

    @Resource
    private RequestContext requestContext;

    @Override
    public ApiLogModel logTranslate(ApiModule apiModule, ApiAccess apiAccess) {

        ApiLogModel apiLogModel = new ApiLogModel();
        moduleHandle(apiLogModel, apiModule);
        getOperatorInfo(apiLogModel);
        getOperateInfo(apiLogModel, apiAccess);
        apiLogModel.setLogType(apiAccess.logType());
        String dataId = requestContext.getValue(apiAccess.dataIdKey());
        apiLogModel.setDataId(dataId);
        buildOperateLog(apiLogModel, apiAccess);

        return apiLogModel;
    }

    /**
     * 获取模块信息
     *
     * @param apiLogModel
     * @param apiModule
     */
    private void moduleHandle(ApiLogModel apiLogModel, ApiModule apiModule) {
        Integer moduleId = ApiConstants.DEFAULT_0;
        String moduleName = ApiConstants.BLANK;
        if (apiModule != null) {
            moduleId = apiModule.moduleId();
            moduleName = apiModule.moduleName();
        }
        apiLogModel.setModuleId(moduleId);
        apiLogModel.setModuleName(moduleName);
    }

    /**
     * 获取当前登录人信息
     *
     * @param apiLogModel
     */
    private void getOperatorInfo(ApiLogModel apiLogModel) {
        LoginInfo loginInfo = loginInfoService.getLoginInfo();
        boolean result = Jsr303Utils.verifyObjectAttributesDefect(loginInfo);
        if (result) {
            throw new UnCertifiedException("not found any login info");
        }
        apiLogModel.setLoginInfo(loginInfo);
    }

    /**
     * 获取方法操作信息
     *
     * @param apiLogModel
     * @param apiAccess
     */
    private void getOperateInfo(ApiLogModel apiLogModel, ApiAccess apiAccess) {
        apiLogModel.setOperateTitle(apiAccess.operateTitle());
        apiLogModel.setOperateMethod(getOperateMethod(apiAccess));
    }

    /**
     * 获取操作方法信息
     * 1个参数时直接获取
     * 多个参数时根据indexKeyForOperateMethod获取其中的一个
     *
     * @param apiAccess
     * @return
     */
    private String getOperateMethod(ApiAccess apiAccess) {
        String operateMethod = ApiConstants.BLANK;
        String[] operateMethods = apiAccess.operateMethod();
        if (operateMethods.length == ApiConstants.ARG_LENGTH_1) {
            operateMethod = operateMethods[0];
        } else if (operateMethods.length > ApiConstants.ARG_LENGTH_1) {
            String key = apiAccess.indexKeyForOperateMethod();
            if (key == null || key.equals(ApiConstants.BLANK)) {
                throw new IllegalArgumentException("@ApiAccess#operateMethod参数有多个值，请检查@ApiAccess中是否设置了indexKeyForOperateMethod去获取operateMethod的值");
            }
            Integer index = requestContext.getValue(key);
            if (index != null && index >= 0 && index < operateMethods.length) {
                operateMethod = operateMethods[index];
            } else {
                throw new IllegalArgumentException("@ApiAccess#operateMethod参数有多个值，但请求传入的下标，在operateMethod中不存在");
            }
        }
        return operateMethod;
    }

    /**
     * 构建操作日志
     *
     * @param apiLogModel
     * @param apiAccess
     */
    private void buildOperateLog(ApiLogModel apiLogModel, ApiAccess apiAccess) {

        String operateLog = ApiConstants.BLANK;
        ApiAction action = apiAccess.action();
        ActionTypeEnum actionTypeEnum = action.actionType();
        switch (actionTypeEnum) {

            case GET: {
                operateLog = spellHandle(action.spell());
                apiLogModel.setOperateLog(apiLogModel.getLoginInfo().getOperator() + operateLog);
                break;
            }
            case POST: {
                operateLog = spellHandle(action.spell());
                apiLogModel.setOperateLog(apiLogModel.getLoginInfo().getOperator() + operateLog);
                break;
            }
            case PUT: {
                operateLog = putHandle(action.compare(), action.spell());
                apiLogModel.setOperateLog(apiLogModel.getLoginInfo().getOperator() + operateLog);
                break;
            }
            case DELETE: {
                operateLog = spellHandle(action.spell());
                apiLogModel.setOperateLog(apiLogModel.getLoginInfo().getOperator() + operateLog);
                break;
            }
            case PASSWORD: {
                operateLog = spellHandle(action.spell());
                apiLogModel.setOperateLog(apiLogModel.getLoginInfo().getOperator() + operateLog);
                break;
            }
            case ENABLED: {
                operateLog = onOffHandle(apiAccess, action.spell());
                apiLogModel.setOperateLog(apiLogModel.getLoginInfo().getOperator() + operateLog);
                break;
            }
            case NON: {
            }
            default: {
                break;
            }
        }
    }

    /**
     * 拼接操作语句
     *
     * @param spell
     * @return
     */
    private String spellHandle(Spell[] spell) {
        StringBuilder builder = new StringBuilder();
        for (Spell sp : spell) {
            builder.append(sp.prefix()).append(sp.codeKey().equals(ApiConstants.BLANK) ? ApiConstants.BLANK : requestContext.getValue(sp.codeKey())).append(sp.suffix()).append(sp.customString());
            builder.append("。");
        }
        if (builder.length() > ApiConstants.ARG_LENGTH_0) {
            int lastIndexOf = builder.lastIndexOf("。");
            if (lastIndexOf >= ApiConstants.ARG_LENGTH_0) {
                builder.deleteCharAt(lastIndexOf);
            }
        }
        return builder.toString();
    }

    private String putHandle(Compare compare, Spell[] spell) {

        StringBuilder operateLog = new StringBuilder();
        String newObjKey = compare.newObjKey();
        String oldObjKey = compare.oldObjKey();
        boolean diffFieldFlag = false;
        if (!ApiConstants.BLANK.equals(newObjKey) && !ApiConstants.BLANK.equals(oldObjKey)) {
            Object newObj = requestContext.getValue(newObjKey);
            Object oldObj = requestContext.getValue(oldObjKey);
            String diffFieldLog = ObjectCompareUtils.diffField(newObj, oldObj);
            if (!ApiConstants.BLANK.equals(diffFieldLog)) {
                diffFieldFlag = true;
            }
            operateLog.append(compare.prefix()).append(compare.codeKey().equals(ApiConstants.BLANK) ? ApiConstants.BLANK : requestContext.getValue(compare.codeKey())).append(compare.suffix());
            if (diffFieldFlag) {
                operateLog.append("：").append(diffFieldLog);
            }
        }
        String spellLog = spellHandle(spell);
        if (operateLog.length() > ApiConstants.ARG_LENGTH_0 && diffFieldFlag) {
            if (ApiConstants.BLANK.equals(spellLog)) {
                operateLog.append("。");
            } else {
                operateLog.append("；");
                operateLog.append(spellLog);
            }
        } else {
            if (ApiConstants.BLANK.equals(spellLog)) {
                operateLog.append("。");
            } else {
                operateLog.append("：");
                operateLog.append(spellLog);
            }
        }
        return operateLog.toString();
    }

    /**
     * 禁用/启用操作
     *
     * @param apiAccess
     * @param spell
     * @return
     */
    private String onOffHandle(ApiAccess apiAccess, Spell[] spell) {
        String operateMethod = getOperateMethod(apiAccess);
        String spellValue = spellHandle(spell);
        StringBuilder enabled = new StringBuilder();
        enabled.append(operateMethod).append(spellValue);
        return enabled.toString();
    }

}
