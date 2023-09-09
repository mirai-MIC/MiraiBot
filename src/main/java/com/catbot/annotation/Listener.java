package com.catbot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Listener {
    /**
     * 触发命令参数
     */
    String value() default "";

    MatchType matchType() default MatchType.DEFAULT;

    /**
     * 事件类型
     */
    Class<?> method();

    /**
     * 是否启用正则
     */
    boolean useRegex() default false;

}