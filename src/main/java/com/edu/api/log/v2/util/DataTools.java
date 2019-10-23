package com.edu.api.log.v2.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Yangzhen
 * @Description
 * @date 2019-09-29 17:25
 **/
public class DataTools {

    public static final String SAMPLE_CODE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final Map<String, Class<?>> BASIC_DATA_TYPE_MAP = new HashMap<>();

    static {
        BASIC_DATA_TYPE_MAP.put("int", Integer.class);
        BASIC_DATA_TYPE_MAP.put("double", Double.class);
        BASIC_DATA_TYPE_MAP.put("long", Long.class);
        BASIC_DATA_TYPE_MAP.put("short", Short.class);
        BASIC_DATA_TYPE_MAP.put("byte", Byte.class);
        BASIC_DATA_TYPE_MAP.put("boolean", Boolean.class);
        BASIC_DATA_TYPE_MAP.put("char", Character.class);
        BASIC_DATA_TYPE_MAP.put("float", Float.class);
    }

    /**
     * 随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(SAMPLE_CODE.length());
            sb.append(SAMPLE_CODE.charAt(number));
        }
        return sb.toString();
    }

    public static Date getNow() {
        return new Date();
    }

    /**
     * 随机数字，转换成对应的包装类型，对于Boolean类型的数据，总是返回false
     *
     * @param length
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T getRandomNumber(int length, Class<?> clazz) throws Exception {

        if (length <= 0) {
            length = 1;
        }

        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length) {
            sb.append(random.nextInt(10));
        }
        String str = sb.toString();
        if (clazz.isPrimitive()) {
            clazz = BASIC_DATA_TYPE_MAP.get(clazz.getName());
        }
        Constructor<?> constructor = clazz.getConstructor(String.class);
        Object result = constructor.newInstance(str);
        return (T) result;
    }

    /**
     * 构造数据，你的对象一定要有无参构造函数
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T newData(Class<?> clazz) {
        try {
            Object obj = clazz.newInstance();
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getName().startsWith("set")) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    Class<?> type = parameterTypes[0];
                    Object value = dispatcher(type);
                    method.invoke(obj, value);
                }
            }
            return (T) obj;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    /**
     * 适配数据类型
     *
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T dispatcher(Class<?> clazz) throws Exception {

        if (isPrimitive(clazz)) {
            return getRandomNumber(0, clazz);
        } else if (clazz.isAssignableFrom(Date.class)) {
            return (T) getNow();
        } else if (clazz.isAssignableFrom(String.class)) {
            return (T) getRandomString(6);
        } else if (clazz.isInterface()) {
            if (Collection.class.isAssignableFrom(clazz)) {
                // 返回的集合不能添加数据
                // return (T) Collections.emptyList();
                return null;
            } else if (Map.class.isAssignableFrom(clazz)) {
                // 返回的集合不能添加数据
                // return (T) Collections.EMPTY_MAP;
                return null;
            }
            return null;
        } else {
            // Object
            return (T) clazz.newInstance();
        }
    }

    /**
     * 判断基本数据类型和包装类型
     *
     * @param clazz
     * @return
     */
    public static boolean isPrimitive(Class<?> clazz) {
        try {
            // 判断基本数据类型
            if (clazz.isPrimitive()) {
                return true;
            }
            // 判断包装类型
            if (clazz.getField("TYPE") != null && ((Class) (clazz.getField("TYPE").get(null))).isPrimitive()) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

}
