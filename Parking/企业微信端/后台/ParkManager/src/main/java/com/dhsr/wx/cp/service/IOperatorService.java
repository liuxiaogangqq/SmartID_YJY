package com.dhsr.wx.cp.service;

import com.alibaba.fastjson.JSONObject;
import com.dhsr.wx.cp.entity.Operator;

import java.util.Map;

/**
 * <p>
 * 操作员 服务类
 * </p>
 *
 * @author liuxiaogang123
 * @since 2019-03-18
 */
public interface IOperatorService {
    /**
     * 获取用户信息根据条件
     *
     * @param map
     * @return
     */
    Operator selectByMap(Map<String, Object> map);


    /**
     * 验证登录用户手机和密码
     *
     * @param requestJson
     * @return
     * @throws Exception
     */
    Map<String, Object> checkUserIdAndPasswd(JSONObject requestJson) throws Exception;

    /**
     * 验证登录用户编号
     *
     * @param requestJson
     * @return
     * @throws Exception
     */
    Map<String, Object> checkUserIdAndPasswdOA(JSONObject requestJson) throws Exception;

    /**
     * 获取登录用户的菜单信息
     *
     * @param operator
     * @return
     */
    Map<String, Object> getLoginUserAndMenuInfo(Operator operator);


}
