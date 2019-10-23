package com.edu.api.log.v2.annotation.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ChangeFromTo {

    /**
     * 集团名称（#名称1#改为了#名称2#）
     *
     * @return
     */
    FieldDefineName fieldDefineName();

    String prefix() default "";

    String action() default "";

    String suffix() default "";
}
