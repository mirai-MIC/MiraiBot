package com.catbot.config.data;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @BelongsProject: MiraiBot
 * @BelongsPackage: com.catbot.config.data
 * @Author: mi
 * @CreateTime: 2023/10/8 15:48
 * @Description:
 * @Version: 1.0
 */

@Configuration
@Getter
@ConfigurationProperties(prefix = "model.setting")
public class ModelData {
    @Value("${model.setting.hostUrl}")
    private String hostUrl;
    @Value("${model.setting.appid}")
    private String appid;
    @Value("${model.setting.apiSecret}")
    private String apiSecret;
    @Value("${model.setting.apiKey}")
    private String apiKey;
}
