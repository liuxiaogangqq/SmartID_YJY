package com.dhsr.wx.cp.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liuxiaogang
 */
public class StringUtil {

    public static final int UNICODE_LEN = 2;


    /**
     * int转换为小端byte[]（高位放在高地址中）
     *
     * @param iValue
     * @return
     */
    public byte[] Int2Bytes_LE(int iValue) {
        byte[] rst = new byte[4];
        // 先写int的最后一个字节
        rst[0] = (byte) (iValue & 0xFF);
        // int 倒数第二个字节
        rst[1] = (byte) ((iValue & 0xFF00) >> 8);
        // int 倒数第三个字节
        rst[2] = (byte) ((iValue & 0xFF0000) >> 16);
        // int 第一个字节
        rst[3] = (byte) ((iValue & 0xFF000000) >> 24);
        return rst;
    }


    /**
     * 转换String为byte[]
     *
     * @param str
     * @return
     */
    public byte[] String2Bytes_LE(String str) {
        if (str == null) {
            return null;
        }
        char[] chars = str.toCharArray();

        byte[] rst = Chars2Bytes_LE(chars);

        return rst;
    }


    /**
     * 转换字符数组为定长byte[]
     *
     * @param chars 字符数组
     * @return 若指定的定长不足返回null, 否则返回byte数组
     */
    public byte[] Chars2Bytes_LE(char[] chars) {
        if (chars == null)
            return null;

        int iCharCount = chars.length;
        byte[] rst = new byte[iCharCount * UNICODE_LEN];
        int i = 0;
        for (i = 0; i < iCharCount; i++) {
            rst[i * 2] = (byte) (chars[i] & 0xFF);
            rst[i * 2 + 1] = (byte) ((chars[i] & 0xFF00) >> 8);
        }

        return rst;
    }


    /**
     * 转换byte数组为int（小端）
     *
     * @return
     * @note 数组长度至少为4，按小端方式转换,即传入的bytes是小端的，按这个规律组织成int
     */
    public int Bytes2Int_LE(byte[] bytes) {
        if (bytes.length < 4)
            return -1;
        int iRst = (bytes[0] & 0xFF);
        iRst |= (bytes[1] & 0xFF) << 8;
        iRst |= (bytes[2] & 0xFF) << 16;
        iRst |= (bytes[3] & 0xFF) << 24;

        return iRst;
    }


    /**
     * 转换byte数组为int（大端）
     *
     * @return
     * @note 数组长度至少为4，按小端方式转换，即传入的bytes是大端的，按这个规律组织成int
     */
    public int Bytes2Int_BE(byte[] bytes) {
        if (bytes.length < 4)
            return -1;
        int iRst = (bytes[0] << 24) & 0xFF;
        iRst |= (bytes[1] << 16) & 0xFF;
        iRst |= (bytes[2] << 8) & 0xFF;
        iRst |= bytes[3] & 0xFF;

        return iRst;
    }


    /**
     * 转换byte数组为Char（小端）
     *
     * @return
     * @note 数组长度至少为2，按小端方式转换
     */
    public char Bytes2Char_LE(byte[] bytes) {
        if (bytes.length < 2)
            return (char) -1;
        int iRst = (bytes[0] & 0xFF);
        iRst |= (bytes[1] & 0xFF) << 8;

        return (char) iRst;
    }


    /**
     * 转换byte数组为char（大端）
     *
     * @return
     * @note 数组长度至少为2，按小端方式转换
     */
    public char Bytes2Char_BE(byte[] bytes) {
        if (bytes.length < 2)
            return (char) -1;
        int iRst = (bytes[0] << 8) & 0xFF;
        iRst |= bytes[1] & 0xFF;

        return (char) iRst;
    }

    public static String pin(String chinese) throws Exception {
        String pinyin = "";
        HanyuPinyinOutputFormat pinyinOutputFormat = new HanyuPinyinOutputFormat();
        pinyinOutputFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        pinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        String[] pinyinArray = null;
        for (char ch : chinese.toCharArray()) {
            pinyinArray = PinyinHelper.toHanyuPinyinStringArray(ch, pinyinOutputFormat);
            pinyin += ComUtil.isEmpty(pinyinArray) ? ch : pinyinArray[0];
        }
        return pinyin;
    }

    /**
     * 获取方法中指定注解的value值返回
     *
     * @param method               方法名
     * @param validationParamValue 注解的类名
     * @return
     */
    public static String getMethodAnnotationOne(Method method, String validationParamValue) {
        String retParam = null;
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                String str = parameterAnnotations[i][j].toString();
                if (str.indexOf(validationParamValue) > 0) {
                    retParam = str.substring(str.indexOf("=") + 1, str.indexOf(")"));
                }
            }
        }
        return retParam;
    }

    public static boolean isValidURLAddress(String url) {
        String pattern = "([h]|[H])([t]|[T])([t]|[T])([p]|[P])([s]|[S]){0,1}://([^:/]+)(:([0-9]+))?(/\\S*)*";
        return url.matches(pattern);
    }

    /**
     * 将utf-8编码的汉字转为中文
     *
     * @param str
     * @return
     * @author zhaoqiang
     */
    public static String utf8Decoding(String str) {
        String result = str;
        try {
            result = URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean checkEmail(String email) {
        if (ComUtil.isEmpty(email)) {
            return false;
        }
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证手机号码，11位数字，1开通，第二位数必须是3456789这些数字之一 *
     *
     * @param mobileNumber
     * @return
     */
    public static boolean checkMobileNumber(String mobileNumber) {
        boolean flag = false;
        try {
            // Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
            Pattern regex = Pattern.compile("^1[345789]\\d{9}$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    /**
     * 补零
     *
     * @param paramV
     * @return
     */
    public static String addZero(String paramV) {
        String num_ = "";
        int paramL = paramV.length();
        if (paramL % 8 == 0) {

        } else {
            int num = 8 - (paramL % 8);
            for (int i = 0; i < num; i++) {
                num_ += "0";
            }
        }
        return num_;
    }

    /**
     * 补零
     *
     * @param
     * @return
     */
    public static int holidayTime(String flagS, int timeV) {
        //最终生成的节假日二进制字符串
        String htV = "";

        switch (timeV) {
            case 1:
                htV = "0000000";
                break;
            case 2:
                htV = "0000001";
                break;
            case 3:
                htV = "0000010";
                break;
            case 4:
                htV = "0000011";
                break;
            case 5:
                htV = "0000100";
                break;
            case 6:
                htV = "0000101";
                break;
            case 7:
                htV = "0000110";
                break;
            case 8:
                htV = "0000111";
                break;

        }
        htV = flagS + htV;
        return Integer.parseInt(htV, 2);
    }

/*    public static void main(String[] args) {
        String binary = "10000000";
        System.out.println(Integer.parseInt(binary,2));
    }*/

    public static String toString(String binary) {
        String[] tempStr = binary.split(" ");
        char[] tempChar = new char[tempStr.length];
        for (int i = 0; i < tempStr.length; i++) {
            tempChar[i] = BinstrToChar(tempStr[i]);
        }
        return String.valueOf(tempChar);
    }


    //将二进制字符串转换成int数组
    public static int[] BinstrToIntArray(String binStr) {
        char[] temp = binStr.toCharArray();
        int[] result = new int[temp.length];
        for (int i = 0; i < temp.length; i++) {
            result[i] = temp[i] - 48;
        }
        return result;
    }


    //将二进制转换成字符
    public static char BinstrToChar(String binStr) {
        int[] temp = BinstrToIntArray(binStr);
        int sum = 0;
        for (int i = 0; i < temp.length; i++) {
            sum += temp[temp.length - 1 - i] << i;
        }
        return (char) sum;
    }

    /**
     * 找出两个集合中不同的元素
     *
     * @param collmax
     * @param collmin
     * @return
     */
    public static Collection getDifferent(Collection collmax, Collection collmin) {
        //使用LinkedList防止差异过大时,元素拷贝
        Collection csReturn = new LinkedList();
        Collection max = collmax;
        Collection min = collmin;
        //先比较大小,这样会减少后续map的if判断次数
        if (collmax.size() < collmin.size()) {
            max = collmin;
            min = collmax;
        }
        //直接指定大小,防止再散列
        Map<Object, Integer> map = new HashMap<Object, Integer>(max.size());
        for (Object object : max) {
            map.put(object, 1);
        }
        for (Object object : min) {
            if (map.get(object) == null) {
                csReturn.add(object);
            } else {
                map.put(object, 2);
            }
        }
        for (Map.Entry<Object, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                csReturn.add(entry.getKey());
            }
        }
        return csReturn;
    }

    /**
     * 找出两个集合中相同的元素
     *
     * @param collmax
     * @param collmin
     * @return
     */
    public static Collection getSame(Collection collmax, Collection collmin) {
        //使用LinkedList防止差异过大时,元素拷贝
        Collection csReturn = new LinkedList();
        Collection max = collmax;
        Collection min = collmin;
        //先比较大小,这样会减少后续map的if判断次数
        if (collmax.size() < collmin.size()) {
            max = collmin;
            min = collmax;
        }
        //直接指定大小,防止再散列
        Map<Object, Integer> map = new HashMap<Object, Integer>(max.size());
        for (Object object : max) {
            map.put(object, 1);
        }
        for (Object object : min) {
            if (map.get(object) != null) {
                csReturn.add(object);
            }
        }
        return csReturn;
    }

    /**
     * 获取两个集合的不同元素,去除重复
     *
     * @param collmax
     * @param collmin
     * @return
     */
    public static Collection getDiffentNoDuplicate(Collection collmax, Collection collmin) {
        return new HashSet(getDifferent(collmax, collmin));
    }

    /**
     * 获取两个集合的不同元素,去除重复，只取第一个集合的不重复元素
     *
     * @param collmax
     * @param collmin
     * @return
     */
    public static Collection getDiffentNoList(Collection collmax, Collection collmin) {
        //使用LinkedList防止差异过大时,元素拷贝
        Collection csReturn = new LinkedList();
        Map<Object, Integer> map = new HashMap<Object, Integer>(collmax.size());

        for (Object object : collmax) {
            map.put(object, 1);
        }
        for (Object object : collmin) {
            if (map.get(object) != null) {
                map.put(object, 2);
            }
        }
        for (Map.Entry<Object, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                csReturn.add(entry.getKey());
            }
        }
        return csReturn;
    }

    public static String timeString(String timeInfo, int lenghtCount) {

        int timeLen = timeInfo.length();
        String returnStr = timeInfo;
        int countSize = timeLen / 24;

        for (; countSize < 19; countSize++) {

            returnStr += "000000000000000000000000";
        }
        return returnStr;
    }

    public static String decodeString(String str) {

        str = HighLowHex(spaceHex(str));


        return str;

    }

    public static String decodeHexString(String str) {

        str = HighLowHex(spaceHex(str));

        String value = new BigInteger(str, 16).toString();

        return value;

    }

    private static String spaceHex(String str) {

        char[] array = str.toCharArray();

        if (str.length() <= 2) return str;

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < array.length; i++) {

            int start = i + 1;

            if (start % 2 == 0) {

                buffer.append(array[i]).append(" ");

            } else {

                buffer.append(array[i]);

            }

        }

        return buffer.toString();

    }

    private static String HighLowHex(String str) {

        if (str.trim().length() <= 2) return str;

        List<String> list = Arrays.asList(str.split(" "));

        Collections.reverse(list);

        StringBuffer stringBuffer = new StringBuffer();

        for (String string : list) {

            stringBuffer.append(string);

        }

        return stringBuffer.toString();

    }

    public static void main(String[] args) throws Exception {
        List<String> num1 = new ArrayList<>();
        List<String> num2 = new ArrayList<>();


       /* Date orderDateStart = new SimpleDateFormat(DateTimeUtil.FMT_yyyyMMddHHmmssS_17).parse("2019071614375800");
        String accessTime = new SimpleDateFormat(DateTimeUtil.FMT_yyyyMMddHHmmss).format(orderDateStart);

        System.out.println(accessTime);*/


        String str = "1,2,3,4,5";

        String[] strings = str.split(",");

        List<String> stringList = Arrays.asList(strings);


        stringList.forEach(t -> {

            System.out.println(t);


        });

 /*       for (int i = 0; i < 1000000; i++) {
            num1.add(i);
        }

        for (int i = 19999; i < 2009999; i++) {
            num2.add(i);
        } */
     /*   for (int i = 0; i < 100000; i++) {
            num1.add(i);
        }

        for (int i = 19; i < 200000; i++) {
            num2.add(i);
        }*/
/*
        num1.add("12");
        num1.add("8");
        num2.add("12");
        num2.add("8");


        System.out.println("num1长度：" + num1.size());
        System.out.println("num2长度：" + num2.size());

        long startTime = System.currentTimeMillis();
        Collection different = StringUtil.getDiffentNoList(num1, num2);
        System.out.println("共耗时：" + (System.currentTimeMillis() - startTime) + "毫秒");
        System.out.println("不同元素共有：" + different.size());

        StringUtil c = new StringUtil();
        System.out.println(Long.parseLong("54636d2a", 16));
        System.out.println(c.decodeHexString("d0c7cb42"));*/

    }


  /*  public void  searchMax(){
        long a = unsafe.allocateMemory(8);
        try {
            unsafe.putLong(a, 0x0102030405060708L);
            byte b = unsafe.getByte(a);
            switch (b) {
                case 0x01: byteOrder = ByteOrder.BIG_ENDIAN;     break;
                case 0x08: byteOrder = ByteOrder.LITTLE_ENDIAN;  break;
                default:
                    assert false;
                    byteOrder = null;
            }
        } finally {
            unsafe.freeMemory(a);
        }
    }*/

}
