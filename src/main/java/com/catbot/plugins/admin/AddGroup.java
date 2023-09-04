package com.catbot.plugins.admin;

import com.catbot.annotation.Filter;
import com.catbot.annotation.MatchType;
import com.catbot.annotation.MiraiListener;
import net.mamoe.mirai.data.RequestEventData;
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.event.events.MemberJoinRequestEvent;
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
@MiraiListener
public class AddGroup {
    @Filter(matchType = MatchType.JOIN_GROUP)
    public void addGroup(BotInvitedJoinGroupRequestEvent event) {
        System.out.println("触发成功");
        System.out.println(event.getInvitorId());
        event.accept();
    }
}
