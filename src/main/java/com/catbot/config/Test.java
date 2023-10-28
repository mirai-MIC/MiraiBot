package com.catbot.config;


import kotlin.coroutines.CoroutineContext;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MessageEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject: MiraiBot
 * @BelongsPackage: com.catbot.config
 * @Author: mi
 * @CreateTime: 2023/10/9 0:07
 * @Description:
 * @Version: 1.0
 */

@Slf4j
@Component
public class Test extends SimpleListenerHost {
    private MiraiConfiguration miraiConfiguration;
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        // 在此处处理事件处理时抛出的异常
    }

    @NotNull
    @EventHandler
    public ListeningStatus onMessage1(@NotNull MessageEvent event) throws Exception {
        // 在收到消息事件时，发送 "received" 消息
        String s = event.getMessage().contentToString();
        log.info("----> %s".formatted(s));
        return ListeningStatus.LISTENING; // 表示继续监听事件
    }


    public void setMiraiConfiguration() {
    }
}
