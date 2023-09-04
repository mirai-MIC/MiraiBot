package com.catbot.plugins;

import com.catbot.annotation.Listener;
import com.catbot.annotation.MatchType;
import com.catbot.annotation.MiraiEventListener;
import com.catbot.plugins.data.MusicData;
import com.catbot.plugins.data.MusicShareData;
import com.catbot.utils.OK3HttpClient;
import com.catbot.utils.VideoUploadUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.catbot.utils.OK3HttpClient.httpGetAsync;
import static com.catbot.utils.SendMsgUtils.sendAsync;
import static com.catbot.utils.SendMsgUtils.uploadAndCreateImage;

@Component
@Slf4j
@MiraiEventListener
public class plugs {
    @Listener(value = "测试", matchType = MatchType.DEFAULT, method = GroupMessageEvent.class)
    public void sndMessage(GroupMessageEvent event) throws IOException {
        At at = new At(event.getSender().getId());
        MessageChain singleMessages = new MessageChainBuilder()
                .append(at)
                .append(uploadAndCreateImage(event.getGroup(), "https://api.lizh.cc/api/ipsign/"))
                .append("\n")
                .build();
        sendAsync(event.getGroup(),singleMessages);
    }


    @Listener(method = GroupMessageEvent.class)
    public void MusicSend(GroupMessageEvent event) {
        System.out.println("触发");
        String text = event.getMessage().contentToString();
        Pattern pattern = Pattern.compile("/点歌\\s+(.*)");
        Matcher matcher = pattern.matcher(text);

        if (!matcher.find()) return;
        String songName = matcher.group(1);

        var params = new HashMap<String, Object>();
        params.put("msg", songName);
        params.put("type", "json");
        params.put("n", 1);

        MusicShareData musicShareData = new MusicShareData();

        httpGetAsync("https://xiaoapi.cn/API/yy_sq.php", params, null, result -> {
            MusicData musicData = new Gson().fromJson(result, MusicData.class);
            if (musicData.getCode() == 200) {
                musicShareData.setTitle(musicData.getName());
                musicShareData.setSummary(musicData.getSinger());
                musicShareData.setJumpUrl("https://music.163.com/");
                musicShareData.setMusicUrl(musicData.getUrl());
                musicShareData.setPictureUrl(musicData.getCover());
                MusicShare musicShare = new MusicShare(MusicKind.NeteaseCloudMusic, musicShareData.getTitle(), musicShareData.getSummary(), musicShareData.getJumpUrl(), musicShareData.getPictureUrl(), musicShareData.getMusicUrl());
                sendAsync(event.getGroup(),musicShare);
            }
        }, error -> {
            event.getGroup().sendMessage("Error: " + error.getMessage());
        });

    }

    @Listener(value = "看小姐姐", method = GroupMessageEvent.class)
    public void Video(GroupMessageEvent event) throws IOException {
        try {
            String s = OK3HttpClient.httpGet("https://api.xn--ei1aa.cn/API/ks_new.php", null, null);
            JsonObject jsonObject = new Gson().fromJson(s, JsonObject.class);
            JsonObject asJsonObject = jsonObject.get("data").getAsJsonObject();
            String cover = asJsonObject.get("Cover").getAsString();
            String Video = asJsonObject.get("Video").getAsString();
            ShortVideo shortVideo = VideoUploadUtil.uploadVideoAndThumbnail(event.getGroup(), Video, cover, "");
            sendAsync(event.getGroup(), shortVideo);
        } catch (Exception e) {
            log.warn("Error uploading Video %s".formatted(e.getMessage()));
        }
    }
}
