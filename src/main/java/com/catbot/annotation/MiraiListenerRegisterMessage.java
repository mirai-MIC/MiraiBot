package com.catbot.annotation;//package com.catbot.annotation;

import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.*;
import net.mamoe.mirai.event.events.*;
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
 * @CreateTime: 2023/5/30 22:33
 * @Description:
 * @Version: 1.0
 */

//
//@Component
//public class MiraiListenerRegisterMessage implements BeanPostProcessor {
//    private final Bot bot;
//
//    public MiraiListenerRegisterMessage(Bot bot) {
//        this.bot = bot;
//    }
//
//    @Override
//    public Object postProcessBeforeInitialization(Object bean, @NotNull String beanName) throws BeansException {
//        if (bean.getClass().isAnnotationPresent(MiraiListener.class)) {
//            ListenerHost listenerHost = new SimpleListenerHost() {
//                @EventHandler
//                public void handleMessageEvent(GroupMessageEvent event) throws InvocationTargetException, IllegalAccessException {
//                    Method[] methods = bean.getClass().getMethods();
//                    for (Method method : methods) {
//                        if (method.isAnnotationPresent(Filter.class)) {
//                            Filter filterAnnotation = method.getAnnotation(Filter.class);
//                            String value = filterAnnotation.value();
//                            MatchType matchType = filterAnnotation.matchType();
//                            // 检查value是否为空，如果为空则不进行匹配
//                            if (value.isEmpty()) {
//                                method.invoke(bean, event);
//                            }
//                            // 如果matchType未指定，默认使用精确匹配
//                            if (matchType == MatchType.DEFAULT) {
//                                if (event.getMessage().contentToString().equals(value)) {
//                                    method.invoke(bean, event);
//                                    break;
//                                }
//                            } else if (matchType == MatchType.REGEX_CONTAINS) {
//                                if (event.getMessage().contentToString().matches(".*" + value + ".*")) {
//                                    method.invoke(bean, event);
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                }
//
//
//
//            };
//            EventChannel<BotEvent> eventChannel = bot.getEventChannel();
//            eventChannel.registerListenerHost(listenerHost);
//        }
//        return bean;
//    }
//}

@Component
@Slf4j
public class MiraiListenerRegisterMessage implements BeanPostProcessor {
    private final Bot bot;

    public MiraiListenerRegisterMessage(Bot bot) {
        this.bot = bot;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, @NotNull String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(MiraiListener.class)) {
            ListenerHost listenerHost = new SimpleListenerHost() {
                @EventHandler
                public void handleMessageEvent(MessageEvent event) {
                    Method[] methods = bean.getClass().getMethods();
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Filter.class)) {
                            Filter filterAnnotation = method.getAnnotation(Filter.class);
                            String value = filterAnnotation.value();
                            MatchType matchType = filterAnnotation.matchType();
                            // 检查value是否为空，如果为空则不进行匹配
                            if (!value.isEmpty()) {
                                if (matchType == MatchType.DEFAULT) {
                                    if (event.getMessage().contentToString().equals(value)) {
                                        invokeMethod(method, bean, event);
                                    }
                                }
//                                else if (matchType == MatchType.REGEX_CONTAINS) {
//                                    if (event.getMessage().contentToString().matches(".*" + value + ".*")) {
//                                        invokeMethod(method, bean, event);
//                                    }
//                                }
                            }
                        }
                    }
                }
                @EventHandler
                public void JoinGroup(BotInvitedJoinGroupRequestEvent event){
                    Method[] methods = bean.getClass().getMethods();
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Filter.class)) {
                            Filter filterAnnotation = method.getAnnotation(Filter.class);
                            MatchType matchType = filterAnnotation.matchType();
                            if (matchType==MatchType.JOIN_GROUP){
                                invokeMethod(method,bean,event);
                            }
                        }
                    }
                }

            };
            EventChannel<BotEvent> eventChannel = bot.getEventChannel();
            eventChannel.registerListenerHost(listenerHost);
        }
        return bean;
    }

    private void invokeMethod(Method method, Object bean, Event event) {
        try {
            method.invoke(bean, event);
        } catch (Exception e) {
            log.error("Error invoking method: " + method.getName(), e);
        }
    }

}