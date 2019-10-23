package com.edu.api.log.v2.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yangzhen
 * @Description
 * @date 2019-10-10 11:04
 **/
public enum ApiLoginResultEnum {

    LOGIN_SUCCESS(0x0, "登录成功"), LOGIN_FAILED(0x1, "登录失败"), LOGOUT_SUCCESS(0x2, "登出成功"), LOGOUT_FAILED(0x3, "登出失败");

    private int state;

    private String desc;

    public static final int LOGIN_SUCCESS_IDX = 0x0;
    public static final int LOGIN_FAILED_IDX = 0x1;
    public static final int LOGOUT_SUCCESS_IDX = 0x2;
    public static final int LOGOUT_FAILED_IDX = 0x3;

    public static final Map<Integer, String> LOGIN_RESULT_MAP = new HashMap<>();

    static {
        ApiLoginResultEnum[] values = ApiLoginResultEnum.values();
        for (ApiLoginResultEnum value : values) {
            LOGIN_RESULT_MAP.put(value.getState(), value.getDesc());
        }
    }

    private ApiLoginResultEnum(int state, String desc) {
        this.state = state;
        this.desc = desc;
    }

    public int getState() {
        return state;
    }

    public String getDesc() {
        return desc;
    }

    public static String getLoginResultDesc(int state) {
        return LOGIN_RESULT_MAP.get(state);
    }
}
