package com.edu.api.log.v2.util;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpServletRequest 工具
 *
 * @author Yangzhen
 * @date 2019/9/30 15:01
 **/
public class RequestUtils {

    public static final String X_Forwarded_For = "X-Forwarded-For";
    public static final String unknown = "unknown";
    public static final String Proxy_Client_IP = "Proxy-Client-IP";
    public static final String WL_Proxy_Client_IP = "WL-Proxy-Client-IP";
    public static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
    public static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";
    public static final String LOCAL = ".";
    public static final String LOCAL_ = ",";
    public static final String LOCAL_IP = "127.0.0.1";

    /**
     * 获取客户端IP地址，此方法用在proxy环境中
     *
     * @param
     * @return IP
     */
    public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader(X_Forwarded_For);
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader(Proxy_Client_IP);
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader(WL_Proxy_Client_IP);
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HTTP_CLIENT_IP);
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HTTP_X_FORWARDED_FOR);
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.indexOf(LOCAL) == -1) {
            ip = LOCAL_IP;
        }

        // 如果存在多个反向代理，获得的IP是一个用逗号分隔的IP集合，取第一个
        if (ip != null && ip.indexOf(LOCAL_) != -1) {
            String s[] = ip.split(LOCAL_);
            ip = s[0];
        }
        return ip;
    }

    /**
     * 获得项目路径名
     *
     * @param request 客户端请求对象
     * @return 项目路径名，如：/ProjectName
     */
    public static String getContextPath(HttpServletRequest request) {
        return request.getContextPath();
    }

}
