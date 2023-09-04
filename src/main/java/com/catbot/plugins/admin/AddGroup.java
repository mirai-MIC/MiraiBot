package com.catbot.plugins.admin;

import com.catbot.annotation.Listener;
import com.catbot.annotation.MatchType;
import com.catbot.annotation.MiraiEventListener;
import net.mamoe.mirai.event.ListenerHost;
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject: MiraiBot
 * @BelongsPackage: com.catbot.plugins.admin
 * @Author: mi
 * @CreateTime: 2023/9/4 14:42
 * @Description:
 * @Version: 1.0
 */
@Component
//@MiraiListener
@MiraiEventListener
public class AddGroup implements ListenerHost {
    @Listener(matchType = MatchType.JOIN_GROUP, method = BotInvitedJoinGroupRequestEvent.class)
    public void addGroup(BotInvitedJoinGroupRequestEvent event) {
        System.out.println("触发成功");
        System.out.println(event.getInvitorId());
        event.accept();
    }
    

}
