package com.catbot.Aspects;


import com.catbot.annotation.Listener;
import com.catbot.annotation.MiraiEventListener;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @BelongsProject: MiraiBot
 * @BelongsPackage: com.catbot.annotation
 * @Author: mi
 * @CreateTime: 2023/9/4 17:34
 * @Description:
 * @Version: 1.0
 */

@Component
@Slf4j
@SuppressWarnings({"all"})
public class MiraiEventListenerProcessor {


    /**
     * 这段代码是一个事件监听器的处理器，它的主要功能是扫描Spring应用上下文中带有@MiraiEventListener注解的Bean，并为这些Bean注册事件监听器。当事件触发时，它会根据@Filter注解中的条件来决定是否调用相应的方法。
     * 虽然这段代码没有显式地使用AOP（切面）来实现，但它本身具有一定的切面特性，因为它通过扫描Bean并根据注解条件来触发方法调用，这与AOP的思想有些类似。所以，可以说这个代码在某种程度上实现了一种简单的事件驱动的切面。
     * 如果你认为需要更多的切面特性，例如在方法执行前后执行额外的逻辑，你可以考虑使用Spring的AOP来实现更复杂的切面功能。这将允许你更精细地控制方法的执行和事件的处理。
     */
    private final ApplicationContext applicationContext;

    @Autowired
    public MiraiEventListenerProcessor(ApplicationContext applicationContext, Bot bot) {
        this.applicationContext = applicationContext;
        registerEventListeners();
    }


    // 注册事件监听器
    public void register(Class<? extends Event> T, Method method, boolean useRegex, String value, Object bean) {
        GlobalEventChannel.INSTANCE.subscribeAlways(T, event -> {
            try {
                // 如果事件类型是 MessageEvent，那么需要根据正则表达式来匹配消息内容
                if (event instanceof MessageEvent messageEvent) {
                    String messageContent = messageEvent.getMessage().contentToString();
                    if (useRegex) {
                        // 使用正则表达式匹配消息内容
                        if (messageContent.matches(".*%s.*".formatted(value))) {
                            method.invoke(bean, event);
                        }
                    } else {
                        // 不使用正则表达式，直接比较消息内容
                        if (value.isEmpty() || messageContent.equals(value)) {
                            method.invoke(bean, event);
                        }
                    }
                } else {
                    method.invoke(bean, event);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });
    }

    public void registerEventListeners() {
        applicationContext.getBeansWithAnnotation(MiraiEventListener.class).forEach((beanName, bean) -> {
            Class<?> clazz = bean.getClass();
            Method[] methods = clazz.getDeclaredMethods();
            for (var method : methods) {
                if (!method.isAnnotationPresent(Listener.class)) continue;
                Listener filterAnnotation = method.getAnnotation(Listener.class);
                String value = filterAnnotation.value();
                boolean useRegex = filterAnnotation.useRegex();
                Class<? extends Event> aClass = filterAnnotation.method();
                register(aClass, method, useRegex, value, bean);
            }
        });
    }
}
