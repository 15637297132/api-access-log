package com.edu.api.log.v2.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yangzhen
 * @Description
 * @date 2019-03-22 16:29
 **/
public class ApiBeanUtils {

    /**
     * 对象转map
     *
     * @param obj
     * @return
     */
    public static String getValue(String argName, final Object obj) {
        if (StringUtils.isBlank(argName) || obj == null) {
            return "";
        }
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                if (property.getName().equals(argName)) {
                    Object result = property.getReadMethod().invoke(obj);
                    return result == null ? "" : result.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj.toString();
    }

    public static Map bean2Map(final Object obj) {

        try {
            return BeanUtils.describe(obj);
        }catch (Exception e){

        }
        return new HashMap(0);
    }


    public static String ifnull(final Object obj) {
        if (obj == null) {
            return "";
        }
        return obj.toString();
    }

    public static Long ifnull2Long(final Object obj) {

        if (obj == null || !StringUtils.isNumeric(obj.toString())) {
            return 0L;
        }

        return Long.valueOf(obj.toString());
    }

    public static Integer ifnull2Integer(final Object obj) {

        if (obj == null || !StringUtils.isNumeric(obj.toString())) {
            return 0;
        }

        return Integer.valueOf(obj.toString());
    }

    public static String ifnull2IntegerStr(final Object obj) {

        if (obj == null || !StringUtils.isNumeric(obj.toString())) {
            return "0";
        }
        return obj.toString();
    }

    /**
     * 构建请求参数列表
     *
     * @param paramterNames
     * @param objs
     * @return
     */
    public static String getMethodRequestParams(String[] paramterNames, Object[] objs) {

        if (paramterNames == null || paramterNames.length == 0 || objs == null || objs.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < paramterNames.length; i++) {
            if (objs[i] instanceof ServletRequest || objs[i] instanceof ServletResponse) {
                continue;
            }
            sb.append(paramterNames[i]).append("=").append(JSON.toJSONString(objs[i])).append("&");
        }
        if (sb.length() == 0) {
            return "";
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
