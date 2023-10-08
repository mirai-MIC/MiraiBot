package com.catbot.config;

import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.utils.MiraiLoggerPlatformBase;
import org.jetbrains.annotations.Nullable;

/**
 * @BelongsProject: MiraiBot
 * @BelongsPackage: com.catbot.config
 * @Author: mi
 * @CreateTime: 2023/10/7 23:41
 * @Description:
 * @Version: 1.0
 */

@Slf4j
public class SettingMiraiLogger extends MiraiLoggerPlatformBase {
    @Nullable
    @Override
    public String getIdentity() {
        return SettingMiraiLogger.log.getName();
    }

    @Override
    protected void debug0(@Nullable String s, @Nullable Throwable throwable) {
        log.debug(s, throwable);
    }

    @Override
    protected void error0(@Nullable String s, @Nullable Throwable throwable) {
        log.error(s, throwable);
    }

    @Override
    protected void info0(@Nullable String s, @Nullable Throwable throwable) {
        log.info(s, throwable);
    }

    @Override
    protected void verbose0(@Nullable String s, @Nullable Throwable throwable) {
        log.info(s, throwable);
    }

    @Override
    protected void warning0(@Nullable String s, @Nullable Throwable throwable) {
        log.warn(s, throwable);
    }
}
