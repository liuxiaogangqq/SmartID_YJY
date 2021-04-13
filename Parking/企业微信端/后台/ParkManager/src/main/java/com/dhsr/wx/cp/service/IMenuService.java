package com.dhsr.wx.cp.service;


import com.dhsr.wx.cp.entity.Menu;

import java.util.List;

/**
 * <p>
 * 系统菜单 服务类
 * </p>
 *
 * @author liuxiaogang123
 * @since 2019-03-18
 */
public interface IMenuService {
    /**
     * 根据角色取菜单
     *
     * @param role
     * @return
     */
    List<Menu> findMenuByRoleCode(int role);

    /**
     * 整理menu菜单
     *
     * @param pId
     * @param list
     * @return
     */
    List<Menu> treeMenuList(String pId, List<Menu> list);
}
