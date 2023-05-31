package com.catbot.plugins;

import lombok.Data;

/**
 * @BelongsProject: MiraiBot
 * @BelongsPackage: com.catbot.plugins
 * @Author: mi
 * @CreateTime: 2023/5/31 13:04
 * @Description:
 * @Version: 1.0
 */


@Data
public class MusicShareData {
    private String title;
    private String summary;
    private String jumpUrl;
    private String pictureUrl;
    private String musicUrl;
}
