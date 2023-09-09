package com.catbot.Exception;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * @BelongsProject: MiraiBot
 * @BelongsPackage: com.catbot.Exception
 * @Author: mi
 * @CreateTime: 2023/9/4 13:33
 * @Description:
 * @Version: 1.0
 */

@Configuration
public class MyExceptionHandler {
    @EventListener
    public void handleException(Exception exception) {
        // 在这里处理异常逻辑，例如记录日志或发送错误消息
        System.out.println(exception.getMessage());
    }
}
