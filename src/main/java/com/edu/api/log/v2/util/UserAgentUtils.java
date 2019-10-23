package com.edu.api.log.v2.util;

import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Yangzhen
 * @Description
 * @date 2019-09-30 14:52
 **/
public class UserAgentUtils {

    /**
     * 获取用户代理
     *
     * @param request
     * @return
     */
    public static UserAgent getUserAgent(HttpServletRequest request) {
        //获取浏览器信息
        String ua = request.getHeader("User-Agent");
        //转成UserAgent对象
        UserAgent userAgent = UserAgent.parseUserAgentString(ua);
        return userAgent;
    }

    /**
     * 获取浏览器名称
     *
     * @param request
     * @return
     */
    public static String getBrowserName(HttpServletRequest request) {
        UserAgent userAgent = getUserAgent(request);
        Browser browser = userAgent.getBrowser();
        return browser.getName();
    }

    /**
     * 获取系统名称
     *
     * @param request
     * @return
     */
    public static String getSystemName(HttpServletRequest request) {
        UserAgent userAgent = getUserAgent(request);
        OperatingSystem os = userAgent.getOperatingSystem();
        return os.getName();
    }

}
