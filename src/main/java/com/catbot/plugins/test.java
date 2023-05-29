package com.catbot.plugins;

import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.stereotype.Component;

@Component
public class test {

    public void sndMessage(){
        GlobalEventChannel.INSTANCE.subscribeAlways(MessageEvent.class,event1 -> {
            System.out.println(event1.getMessage().contentToString());
        });
    }
}
