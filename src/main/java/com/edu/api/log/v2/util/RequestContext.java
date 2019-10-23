package com.edu.api.log.v2.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yangzhen
 * @Description
 * @date 2019-03-22 10:50
 **/

@Component
public class RequestContext {

    private ThreadLocal<Map<String, Object>> requestThreadLocal = new InheritableThreadLocal<>();

    private Map<String, Object> get() {
        Map<String, Object> requestContext = requestThreadLocal.get();

        if (requestContext == null) {
            requestContext = new HashMap<>();
            requestThreadLocal.set(requestContext);
        }
        return requestContext;
    }

    /**
     * 移除
     */
    public void remove() {
        requestThreadLocal.remove();
    }

    public void setValue(String key, Object object) {
        Map<String, Object> requestContext = get();
        requestContext.put(key, object);
    }

    public <T> T getValue(String key) {
        Map<String, Object> requestContext = get();
        return (T) requestContext.get(key);
    }

    public Object getValueObj(String key) {
        Map<String, Object> requestContext = get();
        return requestContext.get(key);
    }

}
