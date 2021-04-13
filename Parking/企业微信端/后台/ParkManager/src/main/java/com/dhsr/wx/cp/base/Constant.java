package com.dhsr.wx.cp.base;

import java.util.HashSet;
import java.util.Set;

/**
 * @author liuxiaogang
 */
public class Constant {

    public static final int BYTE_BUFFER = 1024;

    public static Set<String> METHOD_URL_SET = new HashSet<>();
    /**
     * 企业微信号
     */
    public static final int WX_CODE = 1000028; //正式版
   // public static final int WX_CODE = 1000005; //测试

    /**
     * 数据请求返回码
     */
    /**
     * 成功
     */
               //成功
    public static final int RESCODE_SUCCESS = 1000;                //成功
    public static final int RESCODE_SUCCESS_DATA = 1001;        //成功(有返回数据)
    public static final int RESCODE_NOEXIST = 1004;                //查询结果为空
    /**
     * 失败
     */
    public static final int RESCODE_EXCEPTION = 1002;            //请求抛出异常
    public static final int RESCODE_EXCEPTION_DATA = 1008;        //异常带数据
    public static final int RESCODE_NOLOGIN = 1003;                //未登陆状态
    public static final int RESCODE_NOAUTH = 1005;                //无操作权限
    public static final int RESCODE_LOGINEXPIRE = 1009;            //登录过期
    /**
     * token（暂时没有刷新自动token机制，通过重新登录获取）
     */
    public static final int RESCODE_REFTOKEN_MSG = 1006;        //刷新TOKEN(有返回数据)
    public static final int RESCODE_REFTOKEN = 1007;            //刷新TOKEN

    public static final int JWT_ERRCODE_EXPIRE = 4001;            //Token过期
    public static final int JWT_ERRCODE_FAIL = 4002;            //验证不通过

    public static final int HOLIDAY_MAX = 19;            //节假日最大数量
    public static final String SERVER_HOST = "http://172.16.61.55:8080/SmartID_YJY";//现场环境
    // public static final String SERVER_HOST = "http://192.168.1.11:8080/SmartID_YJY";//测试环境
    public static final String PARK_HOST = "http://172.16.61.55:9988/Parking/Handheld/EditBlackOrWhiteCar";//现场环境
    //    public static final String PASSWORD = "admin";			//节假日最大数量
    public static final String POST_CONTENT_TYPE = "application/x-www-form-urlencoded";
    public static final String POST_CONTENT_TYPE_JSON = "application/json;charset=utf-8";
    public static final String POST_CONTENT_TYPE_DATA = "multipart/form-data";
    public static final String POST_CONTENT_TYPE_IMAGE = "image/jpg";

    public static final String CREATE_MONEY_API = SERVER_HOST + "/WeChatPay.html";

    //  public static final String LOCK_ID = "http://192.168.1.166:8085/Nettybjs/";			//验证不通过
    //  public static final String LOCK_ID = "http://192.168.1.111:8085/Nettybjs/";			//验证不通过
    public static final String NOTIFY_URL = "http://parking.biad.com.cn/ParkInfo/notify/order";            //本地

    public static final String CERT_DIR = "C://key//";            //证书文件地址


    /**
     * 用户注册默认角色
     */
    public static final int DEFAULT_REGISTER_ROLE = 5;

    public static final int BUFFER_MULTIPLE = 10;

    //验证码过期时间
    public static final Long PASS_TIME = 50000 * 60 * 1000L;

    //根菜单节点
    public static final String ROOT_MENU = "0";

    //菜单类型，1：菜单  2：按钮操作
    public static final int TYPE_MENU = 1;

    //菜单类型，1：菜单  2：按钮操作
    public static final int TYPE_BUTTON = 2;

    public static boolean isPass = false;

    //启用
    public static final int ENABLE = 1;
    //禁用
    public static final int DISABLE = 0;

    public static class FilePostFix {
        public static final String ZIP_FILE = ".zip";

        public static final String[] IMAGES = {"jpg", "jpeg", "JPG", "JPEG", "gif", "GIF", "bmp", "BMP", "png"};
        public static final String[] ZIP = {"ZIP", "zip", "rar", "RAR"};
        public static final String[] VIDEO = {"mp4", "MP4", "mpg", "mpe", "mpa", "m15", "m1v", "mp2", "rmvb"};
        public static final String[] APK = {"apk", "exe"};
        public static final String[] OFFICE = {"xls", "xlsx", "docx", "doc", "ppt", "pptx"};

    }

    public class FileType {
        public static final int FILE_IMG = 1;
        public static final int FILE_ZIP = 2;
        public static final int FILE_VEDIO = 3;
        public static final int FILE_APK = 4;
        public static final int FIVE_OFFICE = 5;
        public static final String FILE_IMG_DIR = "/img/";
        public static final String FILE_ZIP_DIR = "/zip/";
        public static final String FILE_VEDIO_DIR = "/video/";
        public static final String FILE_APK_DIR = "/apk/";
        public static final String FIVE_OFFICE_DIR = "/office/";
    }

    public class RoleType {
        //超级管理员
        public static final String SYS_ASMIN_ROLE = "sysadmin";
        //管理员
        public static final String ADMIN = "admin";
        //普通用户
        public static final String USER = "user";
    }


}
