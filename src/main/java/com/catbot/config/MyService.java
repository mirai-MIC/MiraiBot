package com.catbot.config;

import net.mamoe.mirai.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService {
    @Autowired
    private final Bot miraiBot;

    public MyService(MiraiConfiguration miraiConfiguration) {
        miraiBot = miraiConfiguration.login();
    }

}