package com.catbot.plugins;

import com.catbot.annotation.Filter;
import com.catbot.annotation.MatchType;
import com.catbot.annotation.MiraiListener;
import com.catbot.plugins.data.MusicData;
import com.catbot.utils.OK3HttpClient;
import com.catbot.utils.PatternUtils;
import com.catbot.utils.SendMsgUtils;
import com.google.gson.Gson;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component
@MiraiListener
public class plugs {
    @Filter(value = "测试", matchType = MatchType.DEFAULT)
    public void sndMessage(GroupMessageEvent event) throws IOException {
        At at = new At(event.getSender().getId());
        ForwardMessageBuilder iNodes = new ForwardMessageBuilder(event.getSender());
        MessageChain singleMessages = new MessageChainBuilder()
                .append(at)
                .append(SendMsgUtils.uploadAndCreateImage(event.getSender(), "https://api.yimian.xyz/img?display=false"))
                .append("\n")
                .build();
        iNodes.add(event.getSender().getId(), event.getSenderName(), singleMessages);
        event.getGroup().sendMessage(iNodes.build());
    }


    @Filter(value = "/点歌 ", matchType = MatchType.REGEX_CONTAINS)
    public void MusicSend(GroupMessageEvent event) {
        String text = event.getMessage().contentToString();
        String pattern = PatternUtils.getPattern(text, "/点歌\\s+(.*)");
        var params = new HashMap<String, Object>();
        params.put("msg", pattern);
        params.put("type", "json");
        params.put("n", 1);

        MusicShareData musicShareData = new MusicShareData();

        OK3HttpClient.httpGetAsync("https://xiaoapi.cn/API/yy_sq.php", params, null, result -> {
            MusicData musicData = new Gson().fromJson(result, MusicData.class);
            if (musicData.getCode() == 200) {
                musicShareData.setTitle(musicData.getName());
                musicShareData.setSummary(musicData.getSinger());
                musicShareData.setJumpUrl("http://xiaoapi.cn");
                musicShareData.setMusicUrl(musicData.getUrl());
                musicShareData.setPictureUrl(musicData.getCover());

                MusicShare musicShare = new MusicShare(MusicKind.NeteaseCloudMusic, musicShareData.getTitle(), musicShareData.getSummary(), musicShareData.getJumpUrl(), musicShareData.getPictureUrl(), musicShareData.getMusicUrl());
                event.getSender().sendMessage(musicShare);
            }
        }, error -> {
            event.getGroup().sendMessage("Error: " + error.getMessage());
        });

    }

}
