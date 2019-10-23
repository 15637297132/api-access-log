package com.edu.api.log.v2.constants;

/**
 * @author Yangzhen
 * @Description
 * @date 2019-10-10 10:53
 **/
public enum ApiLogTypeEnum {

    LOGON(0x1), OPERATE(0x2);

    private int logType;

    private ApiLogTypeEnum(int logType) {
        this.logType = logType;
    }

    public static final int LOGON_LOG_TYPE = 0x1;

    public static final int OPERATE_LOG_TYPE = 0x2;

    public int getLogType() {
        return logType;
    }
}
