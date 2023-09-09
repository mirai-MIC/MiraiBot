package com.catbot.plugins;

import com.catbot.annotation.Listener;
import com.catbot.annotation.MiraiEventListener;
import com.catbot.plugins.data.MusicData;
import com.catbot.plugins.data.MusicShareData;
import com.catbot.utils.OK3HttpClient;
import com.catbot.utils.PatternUtils;
import com.catbot.utils.VideoUploadUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

import static com.catbot.utils.OK3HttpClient.httpGetAsync;
import static com.catbot.utils.SendMsgUtils.*;

@Component
@Slf4j
@MiraiEventListener
public class plugs {
    @Listener(value = "测试", method = GroupMessageEvent.class)
    public void sndMessage(GroupMessageEvent event) throws IOException {
//        https://api.yujn.cn/api/ndym.php?type=json
        String s = OK3HttpClient.httpGet("https://xiaobai.klizi.cn/API/video/ks_yanzhi.php?data=&type=json&lx=%E7%BE%8E%E5%A5%B3", null, null);
        JsonObject jsonObject = new Gson().fromJson(s.substring("json:".length()), JsonObject.class);
        JsonObject asJsonObject = jsonObject.get("meta").getAsJsonObject().getAsJsonObject("news").getAsJsonObject();
        String preview = asJsonObject.get("preview").getAsString();
        String url = asJsonObject.get("jumpUrl").getAsString();
        ShortVideo shortVideo = VideoUploadUtil.uploadVideoAndThumbnail(event.getGroup(), url, preview, "");
        sendAsync(event.getGroup(), shortVideo);
    }


    //    @Listener(method = GroupMessageEvent.class, value = "图片", matchType = MatchType.DEFAULT)
//    public void sedImage(GroupMessageEvent event) throws IOException {
////        Image image = event.getGroup().uploadImage(ExternalResource.create(new URL("https://api.yimian.xyz/img?display=false").openStream()));
////        event.getGroup().sendMessage(image);
//        String s = event.getMessage().contentToString();
//        MessageChain chain = new MessageChainBuilder()
//                .append("Hello ")
//                .append("mirai!")
//                .build();
//        MessageSource.quote(chain).
//    }
    @Listener(method = GroupMessageEvent.class, value = "回复")
    public void testSendQuote(GroupMessageEvent event) {
        MessageChainBuilder singleMessages = new MessageChainBuilder();
        singleMessages.append(new QuoteReply(event.getMessage()));
        singleMessages.append("Hello ");
        event.getGroup().sendMessage(singleMessages.build()).recallIn(1000L);
    }

    @Listener(method = GroupMessageEvent.class, value = "撤回")
    public void testSendQuoteRe(GroupMessageEvent event) {
        MessageSource.recall(event.getMessage());
//        event.getGroup().get()
//        MessageSource.recall();

    }


    @Listener(method = GroupMessageEvent.class, value = "禁言", useRegex = true)
    public void Mute(GroupMessageEvent event) {
        String[] parts = event.getMessage().contentToString().split("\\s+");
        int lastPart = Integer.parseInt(parts[parts.length - 1]);
        isMute(event, lastPart);
    }

    @Listener(method = GroupMessageEvent.class, value = "解除禁言", useRegex = true)
    public void UnMute(GroupMessageEvent event) {
        isUnMute(event);
    }


    @Listener(value = "/点歌 ", method = GroupMessageEvent.class, useRegex = true)
    public void MusicSend(GroupMessageEvent event) {
        System.out.println("触发");
        String text = event.getMessage().contentToString();
        String pattern = PatternUtils.getPattern(text, "/点歌\\s+(.*)");
        var params = new HashMap<String, Object>();
        params.put("msg", pattern);
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
                sendAsync(event.getGroup(), musicShare);
            }
        }, error -> {
            event.getGroup().sendMessage("Error: " + error.getMessage());
        });

    }

    @Listener(value = "看小姐姐", method = GroupMessageEvent.class)
    public void Video(GroupMessageEvent event) throws IOException {
        try {
            String s = OK3HttpClient.httpGet("https://api.xn--ei1aa.cn/API/ks_new.php", null, null);
//            https://api.xn--ei1aa.cn/API/ks_new.php
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
