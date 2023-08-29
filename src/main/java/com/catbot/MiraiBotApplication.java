package com.catbot;

import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import xyz.cssxsh.mirai.tool.FixProtocolVersion;

@EnableAspectJAutoProxy
@SpringBootApplication
public class MiraiBotApplication {

    public static void main(String[] args) {
        FixProtocolVersion.fetch(BotConfiguration.MiraiProtocol.ANDROID_PHONE,"8.9.63");
        SpringApplication.run(MiraiBotApplication.class, args);
    }
}
