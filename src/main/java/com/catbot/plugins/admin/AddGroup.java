package com.catbot.plugins.admin;

import com.catbot.annotation.Listener;
import com.catbot.annotation.MiraiEventListener;
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @BelongsProject: MiraiBot
 * @BelongsPackage: com.catbot.plugins.admin
 * @Author: mi
 * @CreateTime: 2023/9/4 14:42
 * @Description:
 * @Version: 1.0
 */
@Component
@MiraiEventListener
public class AddGroup {

    /**
     * @Description 权限所属人，目前demo案例里面全部按照最狂野的形式展示出来
     */
    private static final Long master = 3092179918L;

    /**
     * 邀请机器人加群
     *
     * @param event
     */
    @Listener(method = BotInvitedJoinGroupRequestEvent.class)
    public void addGroup(BotInvitedJoinGroupRequestEvent event) {
        long id = Objects.requireNonNull(event.getInvitor()).getId();
        if (id != master) return;
        System.out.println("触发成功");
        System.out.println(event.getInvitorId());
        event.accept();
    }
}
