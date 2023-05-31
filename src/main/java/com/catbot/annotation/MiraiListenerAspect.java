package com.catbot.annotation;


import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListenerHost;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * @BelongsProject: MiraiBot
 * @BelongsPackage: com.catbot.annotation
 * @Author: mi
 * @CreateTime: 2023/5/31 9:49
 * @Description:
 * @Version: 1.0
 */


@Aspect
@Component
public class MiraiListenerAspect {
    private final Bot bot;

    @Autowired
    public MiraiListenerAspect(Bot bot) {
        this.bot = bot;
    }

    @Before("@annotation(miraiListener)")
    public void registerListenerHost(JoinPoint joinPoint, MiraiListener miraiListener) {
        Object target = joinPoint.getTarget();
        ListenerHost listenerHost = new SimpleListenerHost() {
            @EventHandler
            public void handleMessageEvent(MessageEvent event) throws InvocationTargetException, IllegalAccessException {
                Method[] methods = target.getClass().getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(EventHandler.class)) {
                        method.invoke(target, event);
                        break;
                    }
                }
            }
        };
        EventChannel<BotEvent> eventChannel = bot.getEventChannel();
        eventChannel.registerListenerHost(listenerHost);
    }
}

