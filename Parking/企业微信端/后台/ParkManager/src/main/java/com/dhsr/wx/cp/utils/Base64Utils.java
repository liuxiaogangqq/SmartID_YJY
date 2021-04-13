package com.dhsr.wx.cp.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

/**
 * <p>
 * BASE64编码解码工具包
 * </p>
 * <p>
 * 依赖javabase64-1.3.1.jar
 * </p>
 *
 * @author liuxiaogang
 */
public class Base64Utils {

    /**
     * 文件读取缓冲区大小
     */
    private static final int CACHE_SIZE = 1024;

    /**
     * @param
     * @return
     */
    public static String decode(final String string) {
        try {
            return new String(Base64.decodeBase64(string.getBytes("UTF-8")), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 二进制数据编码为BASE64字符串
     *
     * @param
     * @return
     * @throws Exception
     */
    public static String encode(final String string) {
        try {
            return new String(Base64.encodeBase64(string.getBytes("UTF-8")), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
