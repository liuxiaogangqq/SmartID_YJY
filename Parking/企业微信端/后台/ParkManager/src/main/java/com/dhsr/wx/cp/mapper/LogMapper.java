package com.dhsr.wx.cp.mapper;

import com.dhsr.wx.cp.entity.OperatorLog;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 操作日志 Mapper 接口
 * </p>
 *
 * @author liuxiaogang123
 * @since 2019-03-18
 */
public interface LogMapper {
    int insertOperator(OperatorLog operatorLog);
}
