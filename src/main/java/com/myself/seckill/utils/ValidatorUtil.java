package com.myself.seckill.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: AT
 * @Date: 2021/1/20 9:44 上午
 */
public class ValidatorUtil {

//    private final static Pattern mobile_pattern = Pattern.compile("^1[3456789]d{9}$");
    private static final Pattern CHINA_PATTERN = Pattern.compile("^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$");


    public static boolean isMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        Matcher m = CHINA_PATTERN.matcher(mobile);
        return m.matches();
    }
}
