package com.edu.api.log.v2.aspect;

import com.edu.api.log.v2.annotation.ApiAccess;
import com.edu.api.log.v2.annotation.ApiModule;
import com.edu.api.log.v2.constants.ApiLoginResultEnum;
import com.edu.api.log.v2.constants.ApiOperateResultEnum;
import com.edu.api.log.v2.model.ApiLogModel;
import com.edu.api.log.v2.service.LogPersistProvider;
import com.edu.api.log.v2.service.LogTranslateProvider;
import com.edu.api.log.v2.service.ReturnResultAnalyseProvider;
import com.edu.api.log.v2.util.RequestContext;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author Yangzhen
 * @Description
 * @date 2019-03-19 10:54
 **/
@Aspect
@Component
public class ApiAccessAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiAccessAspect.class);

    @Resource
    private RequestContext requestContext;

    @Resource
    private LogTranslateProvider logTranslateService;

    @Resource
    private ReturnResultAnalyseProvider returnResultAnalyseService;

    @Resource
    private LogPersistProvider logPersistService;

    @Pointcut(value = "@annotation(com.edu.api.log.v2.annotation.ApiAccess)")
    public void apiAccess() {

    }

    /**
     * 处理登出接口，在调用业务代码之前，必须要获取到用户的信息，否则会有问题
     *
     * @param jp
     */
    @Before("apiAccess()")
    public void doLogout(JoinPoint jp) {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();

        ApiAccess apiAccess = method.getAnnotation(ApiAccess.class);
        if (apiAccess.logout()) {
            Class<?> clazz = method.getDeclaringClass();
            ApiModule apiModule = clazz.getAnnotation(ApiModule.class);
            ApiLogModel apiLogModel = logTranslateService.logTranslate(apiModule, apiAccess);
            requestContext.setValue("currentUserLogoutKey", apiLogModel);
        }

    }

    /**
     * 正常返回时调用，包括返回值为void，return null，return Object
     * 当且仅当不是登录接口时有效，除了登出接口外，其他接口都先执行业务，再保存日志
     *
     * @param jp
     */
    @AfterReturning(value = "apiAccess()", returning = "result")
    public void doAfterReturning(JoinPoint jp, Object result) {

        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        ApiAccess apiAccess = method.getAnnotation(ApiAccess.class);
        try {
            Class<?> clazz = method.getDeclaringClass();
            ApiModule apiModule = clazz.getAnnotation(ApiModule.class);
            ApiLogModel apiLogModel = null;
            if (!apiAccess.logout()) {
                apiLogModel = logTranslateService.logTranslate(apiModule, apiAccess);
                returnResultAnalyseService.analyseResult(result, apiLogModel);
            } else {
                apiLogModel = requestContext.getValue("currentUserLogoutKey");
                String dataIdKey = requestContext.getValue("dataIdKey");
                apiLogModel.setDataId(dataIdKey);
                returnResultAnalyseService.analyseLogout(result, apiLogModel);
            }

            LOGGER.info(JSON.toJSONString(apiLogModel, SerializerFeature.WriteMapNullValue));

            logPersistService.persist(apiLogModel);
        } catch (Exception e) {
            LOGGER.error("@AfterReturning Unexpected error is {}", e.getMessage());
        } finally {
            try {
            } catch (Exception e) {
            } finally {
                requestContext.remove();
            }
        }
    }

    /**
     * 抛出异常时调用
     *
     * @param jp
     * @param ex
     */
    @AfterThrowing(value = "apiAccess()", throwing = "ex")
    public void doAfterThrowing(JoinPoint jp, Exception ex) {

        try {
            MethodSignature signature = (MethodSignature) jp.getSignature();
            Method method = signature.getMethod();
            ApiAccess apiAccess = method.getAnnotation(ApiAccess.class);
            Class<?> clazz = method.getDeclaringClass();
            ApiModule apiModule = clazz.getAnnotation(ApiModule.class);
            ApiLogModel apiLogModel = null;
            if (!apiAccess.logout()) {
                apiLogModel = logTranslateService.logTranslate(apiModule, apiAccess);
                apiLogModel.setLoginResult(ApiLoginResultEnum.LOGIN_SUCCESS_IDX);
            } else {
                apiLogModel = requestContext.getValue("currentUserLogoutKey");
                apiLogModel.setLoginResult(ApiLoginResultEnum.LOGOUT_FAILED_IDX);
                String dataIdKey = requestContext.getValue("dataIdKey");
                apiLogModel.setDataId(dataIdKey);
            }
            apiLogModel.setOperateResult(ApiOperateResultEnum.OPERATE_FAILED_IDX);
            apiLogModel.setOperateFailLog("系统异常");
            LOGGER.info(JSON.toJSONString(apiLogModel, SerializerFeature.WriteMapNullValue));
            logPersistService.persist(apiLogModel);
        } catch (Exception e) {
            LOGGER.error("@AfterThrowing Unexpected error is {}", e.getMessage());
        } finally {
            requestContext.remove();
        }
    }
}

