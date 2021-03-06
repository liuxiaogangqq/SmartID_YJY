package com.dhsr.wx.cp.utils;

import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;

/**
 * @author liuxiaogang
 */
public class BigDecimalUtils {

    /**
     * 保留N位小数,N位之后的全部去掉<br>
     *
     * @param current  : 当前需要改变的
     * @param position : 位数
     * @return
     */
    public static BigDecimal setScaleDown(BigDecimal current, int position) {
        return current.setScale(position, BigDecimal.ROUND_DOWN);
    }

    /**
     * 判断是否是一个正数
     * <p>正数定义：比0大的实数叫正数[,正数前面常有一个符号"+",通常可以省略不写.</p>
     *
     * @param bigDecimal
     * @return
     */
    public static boolean isPositiveNumber(BigDecimal bigDecimal) {
        return bigDecimal.compareTo(BigDecimal.ZERO) == 1;
    }

    /**
     * 判断是否不是一个正数
     *
     * @param bigDecimal
     * @return
     */
    public static boolean isNotPositiveNumber(BigDecimal bigDecimal) {
        return !isPositiveNumber(bigDecimal);
    }

    /**
     * 判断两个参数是否相等
     *
     * @param before
     * @param after
     * @return
     */
    public static boolean isEqual(BigDecimal before, BigDecimal after) {
        return before.compareTo(after) == 0;
    }

    /**
     * 判断两个参数是否相等
     *
     * @param before
     * @param after
     * @return
     */
    public static boolean isEqual(BigDecimal before, String after) {
        return isEqual(before, NumberUtils.createBigDecimal(after));
    }


    public static boolean isNotEqual(BigDecimal before, String after) {
        return isNotEqual(before, NumberUtils.createBigDecimal(after));
    }


    /**
     * 判断两个参数是否不相等
     *
     * @param before
     * @param after
     * @return
     */
    public static boolean isNotEqual(BigDecimal before, BigDecimal after) {
        return !isEqual(before, after);
    }

    /**
     * 乘法计算
     *
     * @param before
     * @param after
     * @return
     */
    public static BigDecimal multiply(BigDecimal before, int after) {
        return before.multiply(NumberUtils.createBigDecimal(String.valueOf(after)));
    }

    /**
     * 减法
     *
     * @param before
     * @param after
     * @return
     */
    public static BigDecimal subtract(BigDecimal before, String after) {
        return before.subtract(NumberUtils.createBigDecimal(after));
    }

    public static BigDecimal subtract(BigDecimal before, BigDecimal after) {
        return before.subtract(after);
    }

    /**
     * 加法
     *
     * @param before
     * @param after
     * @return
     */
    public static BigDecimal add(BigDecimal before, String after) {
        return before.add(NumberUtils.createBigDecimal(after));
    }

    /**
     * 加法
     *
     * @param before
     * @param after
     * @return
     */
    public static BigDecimal add(BigDecimal before, BigDecimal after) {
        return before.add(after);
    }

    public static String lowHigh(int var0) {
        int var1 = 1;
        int var2 = var0 >> 8;
        int var3 = var0 & 255;
        String var4 = Integer.toHexString(var2);
        String var5 = Integer.toHexString(var3);
        if (var4.length() > 2) {
            do {
                if (var1 > 1) {
                    var2 >>= 8;
                }
                var4 = Integer.toHexString(var2 >> 8);
                var5 = var5 + Integer.toHexString(var2 & 255);
                ++var1;
            } while (var4.length() > 2);
        }
        if (var4.length() < 2) {
            var4 = "0" + var4;
        }
        if (var5.length() < 2) {
            var5 = "0" + var5;
        }
        return var5 + var4;
    }

    public static void main(String[] msg) {

        System.out.println(lowHigh(961836110) + "====");

    }
}
