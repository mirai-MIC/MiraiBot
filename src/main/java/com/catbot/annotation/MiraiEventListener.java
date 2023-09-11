package com.catbot.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @BelongsProject: MiraiBot
 * @BelongsPackage: com.catbot.annotation
 * @Author: mi
 * @CreateTime: 2023/9/4 17:34
 * @Description: 用于在bot插件或涉及到bot操作的类上标记，让bot这是一个所属于bot的插件
 * @Version: 1.0
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MiraiEventListener {

}
