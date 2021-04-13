package com.dhsr.wx.cp.mapper;


import com.dhsr.wx.cp.entity.Account;
import com.dhsr.wx.cp.entity.Combotree;
import com.dhsr.wx.cp.entity.Operator;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 操作员 Mapper 接口
 * </p>
 *
 * @author liuxiaogang123
 * @since 2019-03-18
 */
public interface OperatorMapper {

    /**
     * 查询操作员信息参数
     *
     * @param map
     * @return
     */
    Operator selectOperatorByMap(Map<String, Object> map);

    // 查询账户
    public Account selectAccount(Map<String, Object> map);

    public Account selectAccountByUser(Map<String, Object> map);

    //账户充减值
    public int updateAccount(Map<String, Object> map1);
    //更新操作员
    public int updateOperator(Map<String, Object> map1);
    //查询用户信息
    public List<Operator> selectOperatorInfo(Map<String,Object> mso);
}
