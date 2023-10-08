package com.catbot.utils;

import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @BelongsProject: MiraiBot
 * @BelongsPackage: com.catbot.utils
 * @Author: mi
 * @CreateTime: 2023/5/31 11:52
 * @Description:
 * @Version: 1.0
 */

@Slf4j
@SuppressWarnings({"all"})
public class SendMsgUtils {
    public SendMsgUtils() {
    }

    /**
     * 禁言功能
     *
     * @param event
     * @param muteTime
     */
    public static void isMute(GroupMessageEvent event, int muteTime) {
        for (SingleMessage singleMessage : event.getMessage()) {
            if (singleMessage instanceof At at) {
                Objects.requireNonNull(event.getGroup().get(at.getTarget())).mute(muteTime);
            }
        }
    }

    /**
     * 结束禁言
     *
     * @param event
     */
    public static void isUnMute(GroupMessageEvent event) {
        for (SingleMessage singleMessage : event.getMessage()) {
            if (singleMessage instanceof At at) {
                Objects.requireNonNull(event.getGroup().get(at.getTarget())).unmute();
            }
        }
    }

    /**
     * # 发送视频
     * <p>
     * <p>
     * 发送视频前操作，下载视频
     *
     * @param event
     * @param client
     * @param url
     * @return
     * @throws IOException
     * @see VideoUploadUtil
     */
    public static ExternalResource downloadExternalResource(okhttp3.OkHttpClient client, String url) throws IOException {
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .build();

        try (okhttp3.Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.error("Failed to download resource: " + response);
            }
            okhttp3.ResponseBody responseBody = Objects.requireNonNull(response.body());
            InputStream inputStream = responseBody.byteStream();

            // 创建 ExternalResource 对象
            ExternalResource resource = ExternalResource.create(inputStream);

            inputStream.close();
            responseBody.close();
            return resource;
        }
    }

    /**
     * @param group   群
     * @param message 所发送的消息
     * @param <T>
     */
    public static <T> CompletableFuture<Void> sendAsync(Group group, T message) {
        return CompletableFuture.runAsync(() -> {
            try {
                if (message instanceof String string) {
                    group.sendMessage(string);
                } else if (message instanceof Message instance) {
                    group.sendMessage(instance);
                } else if (message instanceof MessageContent messageContent) {
                    group.sendMessage(messageContent);
                }
            } catch (Exception e) {
                log.error("Failed to send message: " + e.getMessage());
            }
        });
    }


    /**
     * <h1>异步发送</h1>
     *
     * @param group   机器人收到的群消息的事件
     * @param message 所发送的消息
     * @param <T>
     */
    public static <T> CompletableFuture<Void> sendAsyncReply(GroupMessageEvent group, T message) {
        return CompletableFuture.runAsync(() -> {
            MessageChainBuilder singleMessages = new MessageChainBuilder();
            singleMessages.append(new QuoteReply(group.getMessage()));
            if (message instanceof String messageStr) {
                singleMessages.append(messageStr);
            } else if (message instanceof Message s) {
                singleMessages.append(s);
            } else if (message instanceof Image image) {
                singleMessages.append(image);
            }
            sendAsync(group.getGroup(), singleMessages.build());
        });
    }


    /**
     * <h1>封装转发消息</h1>
     *
     * @param event   机器人收到的群消息的事件
     * @param message 所发送的消息
     * @param <T>
     */
    public static <T> CompletableFuture<Void> MiraiForwardMessageBuild(GroupMessageEvent event, MessageChain message) {
        return CompletableFuture.runAsync(() -> {
            ForwardMessageBuilder iNodes = new ForwardMessageBuilder(event.getGroup());
            iNodes.add(event.getSender().getId(), event.getSenderName(), message);
            sendAsync(event.getGroup(), iNodes.build());
        });
    }

}
