package com.catbot.utils;

/**
 * @BelongsProject: MiraiBot
 * @BelongsPackage: com.catbot.utils
 * @Author: mi
 * @CreateTime: 2023/9/4 12:55
 * @Description:
 * @Version: 1.0
 */


import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.ShortVideo;
import net.mamoe.mirai.utils.ExternalResource;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static com.catbot.utils.SendMsgUtils.downloadExternalResource;

public class VideoUploadUtil {

    public static ShortVideo uploadVideoAndThumbnail(Contact sender, String videoUrl, String thumbnailUrl, String videoName) throws IOException {
        OkHttpClient client = new OkHttpClient();

        // 下载视频
        ExternalResource videoResource = downloadExternalResource(client, videoUrl);

        // 下载缩略图
        ExternalResource thumbnailResource = downloadExternalResource(client, thumbnailUrl);

        // 上传视频和缩略图
        ShortVideo shortVideo = sender.uploadShortVideo(thumbnailResource, videoResource, videoName);
        videoResource.close();
        thumbnailResource.close();
        return shortVideo;
    }

//    private static ExternalResource downloadExternalResource(OkHttpClient client, String url) throws IOException {
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) {
//                throw new IOException("Failed to download resource: " + response);
//            }
//            ResponseBody responseBody = Objects.requireNonNull(response.body());
//            InputStream inputStream = responseBody.byteStream();
//
//            // 创建 ExternalResource 对象
//            ExternalResource resource = ExternalResource.create(inputStream);
//
//            inputStream.close();
//            responseBody.close();
//            return resource;
//        }
//    }
}

