package com.edu.api.log.v2.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yangzhen
 * @Description
 * @date 2019-09-29 17:34
 **/
public enum ApiModuleEnum {

    DEFAULT(0, "demo"),;

    private static final Map<Integer, String> map = new HashMap<>();

    static {
        ApiModuleEnum[] values = ApiModuleEnum.values();
        for (ApiModuleEnum value : values) {
            map.put(value.moduleId, value.moduleName);
        }
    }

    private ApiModuleEnum(int moduleId, String moduleName) {
        this.moduleId = moduleId;
        this.moduleName = moduleName;
    }

    private int moduleId;

    private String moduleName;

    public String getModuleName(int moduleId) {
        String name = map.get(moduleId);
        return name == null ? "" : name;
    }

    public int getModuleId() {
        return moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }
}
