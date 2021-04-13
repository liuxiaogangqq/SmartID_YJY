package com.dhsr.wx.cp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author liuxiaogang(https : / / github.com / binarywang)
 */
@EnableTransactionManagement
@SpringBootApplication
@EnableScheduling
@MapperScan("com.dhsr.wx.cp.mapper")//将项目中对应的mapper类的路径加进来就可以了
public class WxCpDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(WxCpDemoApplication.class, args);
    }
}
