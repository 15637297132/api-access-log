package com.edu.api.log.v2.constants;

/**
 * @author Yangzhen
 * @Description
 * @date 2019-03-01 14:05
 **/
public class ApiAccessConstants {

    /**
     * web session的key
     */
    public static final String WEB_SESSION_KEY = "user";

    /**
     * app session的key
     */
    public static final String APP_SESSION_KEY = "accessToken";

    /**
     * 系统换行符
     */
    public static final String NEWLINE_CHARACTER = System.lineSeparator();

    /**
     * 登录日志
     */
    public static final int SYSTEM_LOG_TYPE = 0x1;

    /**
     * 操作日志
     */
    public static final int OPERATE_LOG_TYPE = 0x2;

    /**
     * 空字符串
     */
    public static final String BLANK = "";

    /**
     * web session的属性
     */
    public static final String USER_ID_ATTR = "userId";

    public static final String PLATFORM_ID_ATTR = "platformId";

    public static final String GROUP_ID_ATTR = "groupId";

    public static final String SUB_GROUP_ID_ATTR = "subGroupId";

    public static final String NICK_NAME_ATTR = "nickName";

    public static final String USER_NAME_ATTR = "userName";

    /**
     * app session的属性
     */
    public static final String APP_ID_ATTR = "appId";

    /**
     * app session的属性
     */
    public static final String REFRESH_TOKEN_ATTR = "refreshToken";

    /**
     * 参数下标，默认-1
     */
    public static final int ARG_INDEX = 0xffffffff;

    public static final String DEFAULT_USER_NAME = "visitor";

    /**
     * 默认用户id
     */
    public static final long DEFAULT_USER_ID = 0x0L;

    /**
     * 访问成功
     */
    public static final int API_ACCESS_SUCCESS = 0x1;

    /**
     * 访问失败
     */
    public static final int API_ACCESS_FAIL = 0x0;

    public static final int WEB_AUTH_TYPE = 0x1;

    public static final int APP_AUTH_TYPE = 0x2;

}
