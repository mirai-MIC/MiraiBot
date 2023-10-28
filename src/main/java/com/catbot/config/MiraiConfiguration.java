package com.catbot.config;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.auth.BotAuthorization;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>Mirai 配置类</h1>
 * 通过 @Configuration 注解，将 MiraiConfiguration 类注入到 Spring 容器中
 */
@Configuration
public class MiraiConfiguration {
    // 从配置文件中读取账号
    @Value("${mirai.bot.qq-number}")
    private long qqNumber;
    // 从配置文件中读取密码
    @Value("${mirai.bot.password}")
    private String password;
    // 从配置文件中读取设备信息位置
    @Value("${mirai.bot.device-info}")
    private String deviceInfo;

    @Bean(initMethod = "login")
    public Bot login() {
        return BotFactory.INSTANCE.newBot(qqNumber, BotAuthorization.byPassword(password), botConfiguration -> {
            botConfiguration.setProtocol(BotConfiguration.MiraiProtocol.ANDROID_PAD);
            botConfiguration.fileBasedDeviceInfo(deviceInfo);
            botConfiguration.autoReconnectOnForceOffline();
            botConfiguration.noNetworkLog();
            botConfiguration.setBotLoggerSupplier(bot -> new SettingMiraiLogger());
        });
    }
}