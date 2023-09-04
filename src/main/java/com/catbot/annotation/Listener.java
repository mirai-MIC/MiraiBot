package com.catbot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Listener {
    String value() default "";

    MatchType matchType() default MatchType.DEFAULT;
    Class<?> method(); // 事件类型


}