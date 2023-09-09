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

import java.io.IOException;

import static com.catbot.utils.SendMsgUtils.downloadExternalResource;

public class VideoUploadUtil {

    public static ShortVideo uploadVideoAndThumbnail(Contact sender, String videoUrl, String thumbnailUrl, String videoName) throws IOException {
        OkHttpClient client = new OkHttpClient();
        // 下载视频
        ExternalResource videoResource = downloadExternalResource(client, videoUrl);
        ExternalResource thumbnailResource = null;
        if (thumbnailUrl != null) {
            // 下载缩略图
            thumbnailResource = downloadExternalResource(client, thumbnailUrl);
        }

        // 上传视频和缩略图
        assert thumbnailResource != null;
        ShortVideo shortVideo = sender.uploadShortVideo(thumbnailResource, videoResource, videoName);
        videoResource.close();
        thumbnailResource.close();
        return shortVideo;
    }

}

