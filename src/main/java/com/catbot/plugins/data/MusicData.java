package com.catbot.plugins.data;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject: MiraiBot
 * @BelongsPackage: com.catbot.plugins.data
 * @Author: mi
 * @CreateTime: 2023/5/31 14:08
 * @Description:
 * @Version: 1.0
 */


@NoArgsConstructor
@Data
public class MusicData {

    @SerializedName("code")
    private int code;
    @SerializedName("cover")
    private String cover;
    @SerializedName("name")
    private String name;
    @SerializedName("singer")
    private String singer;
    @SerializedName("quality")
    private String quality;
    @SerializedName("url")
    private String url;
    @SerializedName("tips")
    private String tips;
}
