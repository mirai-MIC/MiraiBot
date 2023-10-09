package com.catbot.annotation;

import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.events.MessageEvent;

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

    /**
     * 匹配类型
     *
     * @return
     */
    Class<? extends Event> method();

    /**
     * 是否启用正则
     */
    boolean useRegex() default false;

    /**
     * 自定义正则
     *
     * @return
     */
    String autoRegex() default "";

}