package com.catbot.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @BelongsProject: MiraiBot
 * @BelongsPackage: com.catbot.utils
 * @Author: mi
 * @CreateTime: 2023/5/31 14:11
 * @Description:
 * @Version: 1.0
 */


public class PatternUtils {
    public static String getPattern(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        String group = null;
        if (matcher.find()) {
            group = matcher.group(1);
        }
        return group;
    }


}
