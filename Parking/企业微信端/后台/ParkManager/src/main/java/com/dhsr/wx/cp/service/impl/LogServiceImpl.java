package com.dhsr.wx.cp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dhsr.wx.cp.entity.OperatorLog;
import com.dhsr.wx.cp.mapper.LogMapper;
import com.dhsr.wx.cp.service.ILogService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 操作日志 服务实现类
 * </p>
 *
 * @author liuxiaogang123
 * @since 2019-03-18
 */
@Service
public class LogServiceImpl implements ILogService {
    @Resource
    private LogMapper logMapper;

    @Override
    public int insertOperator(OperatorLog operatorLog) {
        return logMapper.insertOperator(operatorLog);
    }


}
