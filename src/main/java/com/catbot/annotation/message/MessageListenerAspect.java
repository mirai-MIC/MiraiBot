package com.catbot.annotation.message;

import com.catbot.annotation.MessageListener;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.aspectj.lang.annotation.Aspect;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

@Aspect
@Component
public class MessageListenerAspect {

    private final Bot miraiBot;
    private static ApplicationContext applicationContext = null;

    @Autowired
    public MessageListenerAspect(Bot miraiBot, ApplicationContext applicationContext) {
        this.miraiBot = miraiBot;
        MessageListenerAspect.applicationContext = applicationContext;
    }

    @Component
    public static class MessageListenerProcessor implements ApplicationListener<ContextRefreshedEvent> {

        private final MessageListenerAspect messageListenerAspect;

        @Autowired
        public MessageListenerProcessor(MessageListenerAspect messageListenerAspect) {
            this.messageListenerAspect = messageListenerAspect;
        }

        @Override
        public void onApplicationEvent(@NotNull ContextRefreshedEvent event) {
            Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(MessageListener.class);
            for (Object bean : beansWithAnnotation.values()) {
                Class<?> beanClass = bean.getClass();
                Method[] methods = beanClass.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(MessageListener.class)) {
                        messageListenerAspect.registerListener(bean, method);
                    }
                }
            }
        }
    }

    private void registerListener(Object bean, Method method) {
        miraiBot.getEventChannel().registerListenerHost(new MyListenerHost(bean, method));
    }

    private static class MyListenerHost extends SimpleListenerHost {
        private final Object bean;
        private final Method method;

        public MyListenerHost(Object bean, Method method) {
            this.bean = bean;
            this.method = method;
        }

        @EventHandler
        public void handleMessageEvent(GroupMessageEvent groupMessageEvent) throws IllegalAccessException, InvocationTargetException {
            method.invoke(bean, groupMessageEvent);
        }
    }
}

