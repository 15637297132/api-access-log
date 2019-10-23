package com.edu.api.log.v2.service.impl;

import com.edu.api.log.v2.constants.ApiLoginResultEnum;
import com.edu.api.log.v2.constants.ApiOperateResultEnum;
import com.edu.api.log.v2.model.ApiLogModel;
import com.edu.api.log.v2.service.ReturnResultAnalyseProvider;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author Yangzhen
 * @Description
 * @date 2019-10-08 11:30
 **/
public class DefaultReturnResultAnalyseProviderImpl implements ReturnResultAnalyseProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultReturnResultAnalyseProviderImpl.class);

    @Override
    public void analyseResult(Object result, ApiLogModel apiLogModel) {
        analyse(result, apiLogModel, false);
    }

    @Override
    public void analyseLogout(Object result, ApiLogModel apiLogModel) {
        analyse(result, apiLogModel, true);
    }

    private void analyse(Object result, ApiLogModel apiLogModel, boolean flag) {
        if (result instanceof Map) {
            Map resultMap = (Map) result;
            Object code = resultMap.get("code");
            String codeStr = null;
            if (code != null && StringUtils.isNumeric(codeStr = code.toString()) && Integer.valueOf(codeStr.toString()) == ApiOperateResultEnum.OPERATE_SUCCESS_IDX) {
                apiLogModel.setOperateResult(ApiOperateResultEnum.OPERATE_SUCCESS_IDX);
                if (flag) {
                    apiLogModel.setLoginResult(ApiLoginResultEnum.LOGOUT_SUCCESS_IDX);
                } else {
                    apiLogModel.setLoginResult(ApiLoginResultEnum.LOGIN_SUCCESS_IDX);
                }
            } else {
                apiLogModel.setOperateResult(ApiOperateResultEnum.OPERATE_FAILED_IDX);
                apiLogModel.setOperateFailLog(String.valueOf(resultMap.get("msg")));
                if (flag) {
                    apiLogModel.setLoginResult(ApiLoginResultEnum.LOGOUT_FAILED_IDX);
                } else {
                    apiLogModel.setLoginResult(ApiLoginResultEnum.LOGIN_FAILED_IDX);
                }
            }
        } else {
            apiLogModel.setOperateResult(ApiOperateResultEnum.OPERATE_SUCCESS_IDX);
            if (flag) {
                apiLogModel.setLoginResult(ApiLoginResultEnum.LOGOUT_SUCCESS_IDX);
            } else {
                apiLogModel.setLoginResult(ApiLoginResultEnum.LOGIN_SUCCESS_IDX);
            }
            LOGGER.info("return result is not ResultStatus or Map");
        }
    }
}
