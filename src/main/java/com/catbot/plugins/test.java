package com.catbot.plugins;

import com.catbot.annotation.Filter;
import com.catbot.annotation.MatchType;
import com.catbot.annotation.MiraiListener;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.stereotype.Component;

@Component
@MiraiListener
public class test {

    @Filter(value = "测试", matchType = MatchType.DEFAULT)
    public void sndMessage(GroupMessageEvent event) {
        event.getGroup().sendMessage("你好");
    }
}
