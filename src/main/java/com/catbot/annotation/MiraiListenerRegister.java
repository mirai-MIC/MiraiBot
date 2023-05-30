package com.catbot.annotation;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListenerHost;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @BelongsProject: MiraiBot
 * @BelongsPackage: com.catbot.annotation
 * @Author: mi
 * @CreateTime: 2023/5/30 21:43
 * @Description:
 * @Version: 1.0
 */


@Component
public class MiraiListenerRegister implements BeanPostProcessor {
    private final Bot bot;

    public MiraiListenerRegister(Bot bot) {
        this.bot = bot;
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, @NotNull String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(MiraiListener.class)) {
            ListenerHost listenerHost = new SimpleListenerHost() {
                @EventHandler
                public void handleMessageEvent(MessageEvent event) throws InvocationTargetException, IllegalAccessException {
                    Method[] methods = bean.getClass().getMethods();
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(EventHandler.class)) {
                            method.invoke(bean, event);
                            break;
                        }
                    }
                }
            };
            EventChannel<BotEvent> eventChannel = bot.getEventChannel();
            eventChannel.registerListenerHost(listenerHost);
        }
        return bean;
    }
}
