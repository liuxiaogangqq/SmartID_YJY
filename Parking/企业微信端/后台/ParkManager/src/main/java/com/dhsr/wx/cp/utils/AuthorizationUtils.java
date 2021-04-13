package com.dhsr.wx.cp.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * java获取mac和机器码，注册码的实现
 */
public class AuthorizationUtils {
    private static final int SPLITLENGTH = 4;
    private static final String SALT = "yunshouhu";

    public static void main(String args[]) throws Exception {
        String code = getMachineCode();

        System.out.println("code:" + code);

        //  String authCode = auth(code);
        System.out.println("机器码：" + code);
        //    System.out.println("注册码：" + authCode);

        // System.out.println("mac:"+getMac());
        // System.out.println("mac2:"+getMac2());


    }


    private static String getSplitString(String str) {
        return getSplitString(str, "-", SPLITLENGTH);
    }

    private static String getSplitString(String str, String split, int length) {
        int len = str.length();
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < len; i++) {
            if (i % length == 0 && i > 0) {
                temp.append(split);
            }
            temp.append(str.charAt(i));
        }
        String[] attrs = temp.toString().split(split);
        StringBuilder finalMachineCode = new StringBuilder();
        for (String attr : attrs) {
            if (attr.length() == length) {
                finalMachineCode.append(attr).append(split);
            }
        }
        String result = finalMachineCode.toString().substring(0,
            finalMachineCode.toString().length() - 1);
        return result;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    private static String getMac() {
        try {
            Enumeration<NetworkInterface> el = NetworkInterface
                .getNetworkInterfaces();
            while (el.hasMoreElements()) {
                byte[] mac = el.nextElement().getHardwareAddress();
                if (mac == null)
                    continue;
                String hexstr = bytesToHexString(mac);
                return getSplitString(hexstr, "-", 2).toUpperCase();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static String getMachineCode() throws Exception {
        Set<String> result = new HashSet<>();
        String mac = getMac();
        System.out.println(mac);
        result.add(mac);
        Properties props = System.getProperties();
        String javaVersion = props.getProperty("java.version");
        result.add(javaVersion);
        String javaVMVersion = props.getProperty("java.vm.version");
        result.add(javaVMVersion);
        String osVersion = props.getProperty("os.version");
        result.add(osVersion);
        String code = DigestUtils.md5Hex(result.toString());
        return getSplitString(code, "-", 4);

    }

}
