package com.dhsr.wx.cp.service;

import com.alibaba.fastjson.JSONObject;
import com.dhsr.wx.cp.entity.OperatorLog;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 操作日志 服务类
 * </p>
 *
 * @author liuxiaogang123
 * @since 2019-03-18
 */
public interface ILogService {

    int insertOperator(OperatorLog operatorLog);
}
