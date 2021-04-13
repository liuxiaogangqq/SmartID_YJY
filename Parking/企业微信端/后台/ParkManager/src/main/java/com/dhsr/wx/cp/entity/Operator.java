package com.dhsr.wx.cp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 人员信息
 * </p>
 *
 * @author liuxiaogang123
 * @since 2019-03-18
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Operator implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 人员主键
     */

    private Integer UserInnerId;
    /**
     * 人员编号
     */
    private String UserId;
    /**
     * 人员姓名
     */
    private String UserName;
    /**
     * 人员类型
     */
    private Integer UserTypeInnerId;
    /**
     * 人员类型名称
     */
    private String UserTypeName;
    /**
     * 公司内部id
     */
    private Integer CompanyInnerId;
    /**
     * 公司名称
     */
    private String CompanyName;
    /**
     * 删除标识
     */
    private Integer DelFlag;
    /**
     * 部门内部id
     */
    private Integer DepartmentInnerId;
    /**
     * 部门名称
     */
    private String DepartmentName;
    /**
     * 0 正常   1在岗  2离职 3 协助预约
     */
    private Integer UState;
    /**
     * 性别0：保密 1：男 2：女
     */
    private Integer Sex;
    /**
     * 邮箱
     */
    private String Email;
    /**
     * 区域权限
     */
    private String AreaList;
    /**
     * 应用权限
     */
    private String AppList;
    /**
     * 手机号
     */
    private String Mobile;
    /**
     * 头像路径
     */
    private String ImageUrl;
    /**
     * UserID
     */
    private String StandbyD;

    /**
     * 非表字段
     */
    @ApiModelProperty(value = "请求头Authorization中token数据")
    private String Token;


}
