package com.catbot.config;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.auth.BotAuthorization;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@EnableConfigurationProperties
public class MiraiConfiguration {
    @Value("${mirai.bot.qq-number}")
    private long qqNumber;
    @Value("${mirai.bot.password}")
    private String password;

    @Value("${mirai.bot.device-info}")
    private String deviceInfo;

    @Bean(initMethod = "login")
    public Bot login() {
        Bot bot = BotFactory.INSTANCE.newBot(qqNumber, BotAuthorization.byPassword(password));
        bot.getConfiguration().setProtocol(BotConfiguration.MiraiProtocol.ANDROID_PHONE);
        bot.getConfiguration().fileBasedDeviceInfo(deviceInfo);
        return bot;
    }
}