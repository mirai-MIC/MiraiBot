package com.catbot.annotation.message;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MessageListenerAspectTest {

    @Pointcut("@annotation(com.catbot.annotation.MessageListener)")
    public void messageListenerPointcut() {
    }


    @Before("messageListenerPointcut()")
    public void handleMessageEvent(JoinPoint joinPoint) {
        GroupMessageEvent groupMessageEvent = (GroupMessageEvent) joinPoint.getArgs()[0];
        String message = groupMessageEvent.getMessage().contentToString();
        long senderId = groupMessageEvent.getSender().getId();
        long groupId = groupMessageEvent.getGroup().getId();
        System.out.println("Received message: " + message);
        System.out.println("Sender ID: " + senderId);
        System.out.println("Group ID: " + groupId);
    }
}


