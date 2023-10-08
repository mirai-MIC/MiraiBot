package com.catbot.config;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.auth.BotAuthorization;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
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

    /**
     *
     *
     * <ul>
     *     <li>
     *         <span style="font-size:20px;">通过 @Bean 注解，将 login 方法注入到 Spring 容器中</span>
     *     </li>
     *     <li>
     *         <span style="font-size:15px;>通过 [BotFactory.INSTANCE.newBot] 方法创建一个 Bot 对象</span>
     *     </li>
     *     <li>
     *         <span style="font-size:15px;>通过 BotAuthorization.byPassword 方法创建一个 BotAuthorization 对象</span>
     *     </li>
     *     <li>
     *         <span style="font-size:15px;>通过 botConfiguration.setProtocol 方法设置协议为 Android 手机</span>
     *     </li>
     *
     *     <li>
     *         <span style="font-size:15px;>通过 botConfiguration.fileBasedDeviceInfo 方法设置设备信息</span>
     *     </li>
     *     <li>
     *         <span style="font-size:15px;>通过 botConfiguration.autoReconnectOnForceOffline 方法设置在被挤下线时自动重连</span>
     *     </li>
     *     <li>
     *         <span style="font-size:15px;>通过 botConfiguration.noNetworkLog 方法设置不打印网络日志</span>
     *     </li>
     *     <li>
     *         <span style="font-size:15px;>通过 botConfiguration.setBotLoggerSupplier 方法设置第三方日志框架</span>
     *     </li>
     * </ul>
     */
    @Bean(initMethod = "login")
    public Bot login() {
        return BotFactory.INSTANCE.newBot(qqNumber, BotAuthorization.byPassword(password), botConfiguration -> {
            botConfiguration.setProtocol(BotConfiguration.MiraiProtocol.ANDROID_PHONE);
            botConfiguration.fileBasedDeviceInfo(deviceInfo);
            botConfiguration.autoReconnectOnForceOffline();
            botConfiguration.noNetworkLog();
            botConfiguration.setBotLoggerSupplier(bot -> new SettingMiraiLogger());
        });
    }
}