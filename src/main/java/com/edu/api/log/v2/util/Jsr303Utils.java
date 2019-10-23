package com.edu.api.log.v2.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.engine.PathImpl;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Yangzhen
 * @Description
 * @date 2019-10-09 13:55
 **/
public class Jsr303Utils {

    /**
     * 校验对象是否缺失，并返回错误消息
     *
     * @param obj
     * @return
     */
    public static String verifyObjectAttributesDefectWithErrorMsg(Object obj) {

        if (null == obj) {
            return "verify object can't be empty";
        }
        Set<ConstraintViolation<Object>> validResult = Validation.buildDefaultValidatorFactory().getValidator().validate(obj);
        if (null != validResult && validResult.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (Iterator<ConstraintViolation<Object>> iterator = validResult.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<Object> constraintViolation = (ConstraintViolation<Object>) iterator.next();
                PathImpl pathImpl = (PathImpl) constraintViolation.getPropertyPath();
                if (StringUtils.isNotBlank(constraintViolation.getMessage())) {
                    sb.append(pathImpl.toString()).append(constraintViolation.getMessage()).append("、");
                } else {
                    sb.append(constraintViolation.getPropertyPath().toString()).append("不合法、");
                }
            }
            if (sb.lastIndexOf("、") == sb.length() - 1) {
                sb.delete(sb.length() - 1, sb.length());
            }
            return sb.toString();
        }
        return null;
    }


    /**
     * 校验对象属性是否缺失
     *
     * @param obj
     * @return
     */
    public static boolean verifyObjectAttributesDefect(Object obj) {

        if (null == obj) {
            return true;
        }
        Set<ConstraintViolation<Object>> validResult = Validation.buildDefaultValidatorFactory().getValidator().validate(obj);
        if (null != validResult && validResult.size() > 0) {
            for (Iterator<ConstraintViolation<Object>> iterator = validResult.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<Object> constraintViolation = (ConstraintViolation<Object>) iterator.next();
                if (constraintViolation != null) {
                    return true;
                }
            }
        }
        return false;
    }
}
