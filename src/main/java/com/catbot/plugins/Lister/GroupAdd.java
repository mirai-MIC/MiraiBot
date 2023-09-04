package com.catbot.plugins.Lister;

import com.catbot.annotation.Listener;
import com.catbot.annotation.MiraiEventListener;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent;
import net.mamoe.mirai.event.events.GroupEvent;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.event.events.MemberLeaveEvent;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject: MiraiBot
 * @BelongsPackage: com.catbot.plugins.Lister
 * @Author: mi
 * @CreateTime: 2023/6/1 16:35
 * @Description:
 * @Version: 1.0
 */


@Component
@MiraiEventListener
@Slf4j
public class GroupAdd {
    private final Bot bot;

    public GroupAdd(Bot bot) {
        this.bot = bot;
    }

    @Listener(method = BotInvitedJoinGroupRequestEvent.class, value = "")
    public void groupAdd(GroupEvent event) {
        try {
            if (event instanceof MemberJoinEvent joinEvent) {
                // 新成员加入群聊
                // 判断是否为机器人加入群聊的事件
                if (joinEvent.getBot().getId() == bot.getId()) {
                    // 欢迎新成员加入群聊
                    event.getGroup().sendMessage(new PlainText("欢迎新成员加入！"));
                } else {
                    // 其他成员加入群聊
                    event.getGroup().sendMessage(new PlainText("欢迎 " + joinEvent.getMember().getNick() + " 加入！"));
                }
            } else if (event instanceof MemberLeaveEvent leaveEvent) {
                // 成员离开群聊
                // 判断是否为机器人离开群聊的事件
                if (leaveEvent.getBot().getId() == bot.getId()) {
                    // 机器人离开群聊
                    event.getGroup().sendMessage(new PlainText("机器人已离开群聊！"));
                } else {
                    // 其他成员离开群聊
                    event.getGroup().sendMessage(new PlainText(leaveEvent.getMember().getNick() + " 已离开群聊！"));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}