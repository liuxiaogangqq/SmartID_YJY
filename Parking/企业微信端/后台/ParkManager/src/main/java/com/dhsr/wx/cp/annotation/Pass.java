package com.dhsr.wx.cp.annotation;


import java.lang.annotation.*;

/**
 * 在Controller方法上加入该注解不会验证身份
 *
 * @author : Created by liuxiaogang on 2018/12/12.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Pass {

}
