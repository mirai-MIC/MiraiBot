package com.catbot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解类，用于监听mirai事件
 * 需要上级类标签(MiraiEventListener)才能使用
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Listener {

    /**
     * 触发命令参数
     */
    String value() default "";

    MatchType matchType() default MatchType.DEFAULT;

    /**
     * 需要处理的事件类型
     * (此bot框架下，所有插件或者bot操作，全部都需要标记bot事件类型才能触发)
     */
    Class<?> method();

    /**
     * 是否启用正则
     */
    boolean useRegex() default false;

}