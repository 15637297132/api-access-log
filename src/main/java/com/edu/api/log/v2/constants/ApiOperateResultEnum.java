package com.edu.api.log.v2.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yangzhen
 * @Description
 * @date 2019-10-10 11:04
 **/
public enum ApiOperateResultEnum {

    OPERATE_SUCCESS(0x0, "登录成功"), OPERATE_FAILED(0x1, "登录失败");

    private int state;

    private String desc;

    /**
     * 操作成功
     */
    public static final int OPERATE_SUCCESS_IDX = 0x0;
    /**
     * 操作失败
     */
    public static final int OPERATE_FAILED_IDX = 0x1;

    public static final Map<Integer, String> OPERATE_RESULT_MAP = new HashMap<>();

    static {
        ApiOperateResultEnum[] values = ApiOperateResultEnum.values();
        for (ApiOperateResultEnum value : values) {
            OPERATE_RESULT_MAP.put(value.getState(), value.getDesc());
        }
    }

    private ApiOperateResultEnum(int state, String desc) {
        this.state = state;
        this.desc = desc;
    }

    public int getState() {
        return state;
    }

    public String getDesc() {
        return desc;
    }

    public static String getOperateResultDesc(int state) {
        return OPERATE_RESULT_MAP.get(state);
    }
}
