package com.catbot.plugins;

import com.catbot.annotation.Filter;
import com.catbot.annotation.MatchType;
import com.catbot.annotation.MiraiListener;
import com.catbot.plugins.data.MusicData;
import com.catbot.plugins.data.MusicShareData;
import com.catbot.utils.OK3HttpClient;
import com.catbot.utils.PatternUtils;
import com.catbot.utils.SendMsgUtils;
import com.catbot.utils.VideoUploadUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import kotlinx.coroutines.GlobalScope;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import static com.catbot.utils.OK3HttpClient.httpGetAsync;
import static com.catbot.utils.SendMsgUtils.sendAsync;
import static com.catbot.utils.SendMsgUtils.uploadAndCreateImage;

@Component
@MiraiListener
@Slf4j
public class plugs {
    @Filter(value = "测试", matchType = MatchType.DEFAULT)
    public void sndMessage(GroupMessageEvent event) throws IOException {
        At at = new At(event.getSender().getId());
        MessageChain singleMessages = new MessageChainBuilder()
                .append(at)
                .append(uploadAndCreateImage(event.getGroup(), "https://api.lizh.cc/api/ipsign/"))
                .append("\n")
                .build();
        event.getGroup().sendMessage(singleMessages);
    }

    @Filter(value = "你好")
    public void send(GroupMessageEvent event) {

        SendMsgUtils.sendMsg(event.getGroup(), "你好");
//        SendMsgUtils.sendMsg(event.getGroup(),);
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

        httpGetAsync("https://xiaoapi.cn/API/yy_sq.php", params, null, result -> {
            MusicData musicData = new Gson().fromJson(result, MusicData.class);
            if (musicData.getCode() == 200) {
                musicShareData.setTitle(musicData.getName());
                musicShareData.setSummary(musicData.getSinger());
                musicShareData.setJumpUrl("https://music.163.com/");
                musicShareData.setMusicUrl(musicData.getUrl());
                musicShareData.setPictureUrl(musicData.getCover());
                MusicShare musicShare = new MusicShare(MusicKind.NeteaseCloudMusic, musicShareData.getTitle(), musicShareData.getSummary(), musicShareData.getJumpUrl(), musicShareData.getPictureUrl(), musicShareData.getMusicUrl());
                event.getGroup().sendMessage(musicShare);
            }
        }, error -> {
            event.getGroup().sendMessage("Error: " + error.getMessage());
        });
    }

    @Filter(value = "看小姐姐")
    public void Video(GroupMessageEvent event) throws IOException {
        String s = OK3HttpClient.httpGet("https://api.xn--ei1aa.cn/API/ks_new.php", null, null);
        JsonObject jsonObject = new Gson().fromJson(s, JsonObject.class);
        JsonObject asJsonObject = jsonObject.get("data").getAsJsonObject();
        String cover = asJsonObject.get("Cover").getAsString();
        String Video = asJsonObject.get("Video").getAsString();
        ShortVideo shortVideo = VideoUploadUtil.uploadVideoAndThumbnail(event.getGroup(), Video, cover, "");
        sendAsync(event.getGroup(),shortVideo);
    }
}
