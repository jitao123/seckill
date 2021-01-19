package com.myself.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: AT
 * @Date: 2021/1/18 4:17 下午
 */
@Component
public class Md5Util {

    public static String md5(String md5Str) {
        return DigestUtils.md5Hex(md5Str);
    }

    private static final String salt = "1a2b3c4d";

    public static String inputPassToFromPass(String inputPass){
        String str = salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String fromPassToDBpass(String fromPass,String salt){
        String str = salt.charAt(0) + salt.charAt(2) + fromPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDBpass(String inputPass,String salt){
        String fromPass = inputPassToFromPass(inputPass);
        String dBpass = fromPassToDBpass(fromPass, salt);
        return dBpass;
    }


    public static void main(String[] args) {
        //        ce21b747de5af71ab5c2e20ff0a60eea
        System.out.println(" ～～～～～～～"+inputPassToFromPass("123456"));
//        0687f9701bca74827fcefcd7e743d179
        String str = fromPassToDBpass("ce21b747de5af71ab5c2e20ff0a60eea", "1a2b3c4d");
        System.out.println("～～～～～～"+str);
//        0687f9701bca74827fcefcd7e743d179
        String dBpass = inputPassToDBpass("123456", "1a2b3c4d");
        System.out.println("～～～～～～"+dBpass);
    }
}
