package com.dhsr.wx.cp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * <p>
 * 系统菜单
 * </p>
 *
 * @author liuxiaogang123
 * @since 2019-03-18
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系统内部菜单
     */
    private Integer MenuInnerId;
    /**
     * 上级菜单内部id
     */
    private Integer ParentInnerId;
    /**
     * 菜单编号
     */
    private String MenuCode;
    /**
     * 页面编号
     */
    private String PageCode;
    /**
     * 菜单名称
     */
    private String MenuName;
    /**
     * 菜单类型
     */
    private Integer MenuType;
    /**
     * 菜单网址
     */
    private String MenuUrl;
    /**
     * 菜单图标
     */
    private String MenuIcon;
    /**
     * 菜单序号
     */
    private Integer MenuNo;

    /**
     * 子菜单集合 非表字段
     */
    private List<Menu> childMenu;


    public Integer getMenuInnerId() {
        return MenuInnerId;
    }

    public void setMenuInnerId(Integer MenuInnerId) {
        this.MenuInnerId = MenuInnerId;
    }

    public Integer getParentInnerId() {
        return ParentInnerId;
    }

    public void setParentInnerId(Integer ParentInnerId) {
        this.ParentInnerId = ParentInnerId;
    }

    public String getMenuCode() {
        return MenuCode;
    }

    public void setMenuCode(String MenuCode) {
        this.MenuCode = MenuCode;
    }

    public String getPageCode() {
        return PageCode;
    }

    public void setPageCode(String PageCode) {
        this.PageCode = PageCode;
    }

    public String getMenuName() {
        return MenuName;
    }

    public void setMenuName(String MenuName) {
        this.MenuName = MenuName;
    }

    public Integer getMenuType() {
        return MenuType;
    }

    public void setMenuType(Integer MenuType) {
        this.MenuType = MenuType;
    }

    public String getMenuUrl() {
        return MenuUrl;
    }

    public void setMenuUrl(String MenuUrl) {
        this.MenuUrl = MenuUrl;
    }

    public String getMenuIcon() {
        return MenuIcon;
    }

    public void setMenuIcon(String MenuIcon) {
        this.MenuIcon = MenuIcon;
    }

    public Integer getMenuNo() {
        return MenuNo;
    }

    public void setMenuNo(Integer MenuNo) {
        this.MenuNo = MenuNo;
    }

    public List<Menu> getChildMenu() {
        return childMenu;
    }

    public void setChildMenu(List<Menu> childMenu) {
        this.childMenu = childMenu;
    }

    @Override
    public String toString() {
        return "Menu{" +
            "MenuInnerId=" + MenuInnerId +
            ", ParentInnerId=" + ParentInnerId +
            ", MenuCode=" + MenuCode +
            ", PageCode=" + PageCode +
            ", MenuName=" + MenuName +
            ", MenuType=" + MenuType +
            ", MenuUrl=" + MenuUrl +
            ", MenuIcon=" + MenuIcon +
            ", MenuNo=" + MenuNo +
            "}";
    }
}
