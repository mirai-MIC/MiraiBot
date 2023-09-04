package com.catbot.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @BelongsProject: MiraiBot
 * @BelongsPackage: com.catbot.annotation
 * @Author: mi
 * @CreateTime: 2023/9/4 17:34
 * @Description:
 * @Version: 1.0
 */


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MiraiEventListener {

}
