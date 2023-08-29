package com.catbot.utils;

import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.internal.deps.okhttp3.OkHttpClient;
import net.mamoe.mirai.internal.deps.okhttp3.Request;
import net.mamoe.mirai.internal.deps.okhttp3.Response;
import net.mamoe.mirai.internal.deps.okhttp3.ResponseBody;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.utils.ExternalResource;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: MiraiBot
 * @BelongsPackage: com.catbot.utils
 * @Author: mi
 * @CreateTime: 2023/5/31 11:52
 * @Description:
 * @Version: 1.0
 */

@Slf4j
public class SendMsgUtils {
    public SendMsgUtils() {
    }

    public static Image uploadAndCreateImage(Contact sender, @NotNull String imageUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(imageUrl)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to download image: " + response);
            }
            ResponseBody responseBody = Objects.requireNonNull(response.body());
            InputStream inputStream = responseBody.byteStream();
            Image image = sender.uploadImage(ExternalResource.create(inputStream));
            inputStream.close();
            responseBody.close();
            return image;
        }
    }
    @NotNull
    public static MessageReceipt<?> sendMsg(Group group, String messages) {
        return group.sendMessage(messages);

    }
    /**
     * httpclient，获取url
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static byte[] getUrlByByte(String url) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .callTimeout(60, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Connection", "keep-alive")
                .build();

        return Objects.requireNonNull(client.newCall(request).execute().body()).bytes();
    }
}
