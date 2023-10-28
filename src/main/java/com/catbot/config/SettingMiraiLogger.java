package com.catbot.config;

import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.utils.MiraiLoggerPlatformBase;
import org.jetbrains.annotations.Nullable;

/**
 * <h1>自定义 Mirai 日志类</h1>
 * 通过继承 MiraiLoggerPlatformBase 类，实现自定义日志类
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
