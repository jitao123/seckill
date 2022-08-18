package com.myself.seckill.utils;

import cn.hutool.core.util.StrUtil;
import lombok.Cleanup;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
    public static void main(String[] args) throws IOException {
        String inFilePath = "";
        String outFilePath = "";
        @Cleanup FileInputStream fileInputStream = new FileInputStream(inFilePath);
        @Cleanup BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outFilePath));
        if (StrUtil.isNotEmpty(inFilePath)) {
            byte[] bytes = new byte[1024 * 10];
            int read = 0;
            while ((read = fileInputStream.read(bytes)) != -1) {
                bufferedOutputStream.write(bytes, 0, read);
            }
        }
    }
}
