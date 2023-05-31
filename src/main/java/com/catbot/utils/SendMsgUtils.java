package com.catbot.utils;

import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.internal.deps.okhttp3.OkHttpClient;
import net.mamoe.mirai.internal.deps.okhttp3.Request;
import net.mamoe.mirai.internal.deps.okhttp3.Response;
import net.mamoe.mirai.internal.deps.okhttp3.ResponseBody;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @BelongsProject: MiraiBot
 * @BelongsPackage: com.catbot.utils
 * @Author: mi
 * @CreateTime: 2023/5/31 11:52
 * @Description:
 * @Version: 1.0
 */


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
}
