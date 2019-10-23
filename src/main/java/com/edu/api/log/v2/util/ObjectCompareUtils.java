package com.edu.api.log.v2.util;

import com.edu.api.log.v2.annotation.model.ChangeFromTo;
import com.edu.api.log.v2.annotation.model.CustomBean;
import com.edu.api.log.v2.annotation.model.FieldDefineName;
import com.edu.api.log.v2.constants.ApiConstants;
import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;

/**
 * @author Yangzhen
 * @date 2019/9/29 13:39
 **/
public class ObjectCompareUtils {

    /**
     * 非包装类型会转换成包装类型
     *
     * @param newObj 新对象
     * @param oldObj 旧对象
     * @return
     */
    public static String diffField(Object newObj, Object oldObj) {

        if (newObj == null || oldObj == null) {
            throw new IllegalArgumentException("Comparing objects cannot be empty");
        }

        if (!newObj.getClass().equals(oldObj.getClass())) {
            throw new IllegalArgumentException("Type mismatch between two objects");
        }

        Class newObjClass = newObj.getClass();
        Class oldObjClass = oldObj.getClass();

//        boolean newObjClassAnnotationPresent = newObjClass.isAnnotationPresent(CustomBean.class);
//        boolean oldObjClassAnnotationPresent = oldObjClass.isAnnotationPresent(CustomBean.class);
//
//        if (!newObjClassAnnotationPresent || !oldObjClassAnnotationPresent) {
//            throw new IllegalArgumentException("Object must exist clife.sleep.commons.api.log.v2.annotation.CustomBean annotation");
//        }
        StringBuilder builder = new StringBuilder();
        try {
            if (newObjClass.equals(oldObjClass)) {
                Field[] fields = newObj.getClass().getDeclaredFields();

                for (Field field : fields) {
                    // 过滤了static和final修饰的属性，注意：String中只有hash属性没有这两个修饰符
                    if (!(Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers()))) {
//                        if (field.isAnnotationPresent(IgnoreCompare.class)) {
//                            continue;
//                        }
                        if (!field.isAnnotationPresent(ChangeFromTo.class) && !field.isAnnotationPresent(FieldDefineName.class)) {
                            continue;
                        }
                        Class<?> clazz = field.getType();
                        field.setAccessible(true);
                        Object newValue = field.get(newObj);
                        Object oldValue = field.get(oldObj);
                        boolean checkResult = checkContinue(newValue, oldValue, field.getName());
                        if (checkResult) {
                            continue;
                        }

                        boolean unequal = false;
                        if (Collection.class.isAssignableFrom(clazz)) {
                            continue;
                        } else if (Map.class.isAssignableFrom(clazz)) {
                            continue;
                        } else {
//                            if (clazz.isAnnotationPresent(CustomBean.class)) {
//                                continue;
//                            } else
                            if (newValue.equals(oldValue)) {
                                // 基本数据类型也用equals比较，因为field.get获取的基本数据类型是包装类型
                                System.out.println(field.getName() + ":" + JSON.toJSONString(newValue) + " == " + JSON.toJSONString(oldValue));
                                continue;
                            } else {
                                unequal = true;
                            }
                        }
                        if (unequal) {
                            ChangeFromTo changeFromTo = field.getAnnotation(ChangeFromTo.class);
                            String change = "";
                            if (changeFromTo != null) {
                                FieldDefineName fieldDefineName = changeFromTo.fieldDefineName();
                                change = fieldDefineName.name() + changeFromTo.prefix() + oldValue + changeFromTo.action() + newValue + changeFromTo.suffix();
                            } else {
                                FieldDefineName fieldDefineName = field.getAnnotation(FieldDefineName.class);
                                if (fieldDefineName != null) {
                                    change = fieldDefineName.name();
                                }
                            }
                            builder.append(change).append("、");

                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (builder.length() > ApiConstants.ARG_LENGTH_0) {
            int lastIndexOf = builder.lastIndexOf("、");
            if (lastIndexOf >= ApiConstants.ARG_LENGTH_0) {
                builder.deleteCharAt(lastIndexOf);
            }
        }
        return builder.toString();
    }

    private static boolean checkContinue(Object newValue, Object oldValue, String fieldName) {
        if (newValue == null && oldValue == null) {
            System.out.println(fieldName + " newValue is null , oldValue is null");
            return true;
        }

        if (newValue == null && oldValue != null) {
            if (isCustomBean(oldValue)) {
                System.out.println(fieldName + " newValue is null , oldValue not null but is a CustomBean");
                return true;
            }
        }

        if (newValue != null && oldValue == null) {
            if (isCustomBean(newValue)) {
                System.out.println(fieldName + " newValue not null but is a CustomBean , oldValue is null");
                return true;
            }
        }

        if (newValue != null && oldValue != null) {
            if (isCustomBean(newValue) || isCustomBean(oldValue)) {
                System.out.println(fieldName + " newValue , oldValue are CustomBean");
                return true;
            }
        }
        return false;
    }

    private static boolean isCustomBean(Object object) {
        return object.getClass().isAnnotationPresent(CustomBean.class);
    }
}
