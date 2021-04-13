package com.dhsr.wx.cp.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @ProjectName: ParkManager
 * @Package: com.dhsr.wx.cp.entity
 * @ClassName: 访客表
 * @Description: java类作用描述
 * @Author: liuxiaogang
 * @CreateDate: 2019-08-15 17:58
 * @UpdateUser: liuxiaogang
 * @UpdateDate: 2019-08-15 17:58
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2019</p>
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class VisitorUserInfo implements Serializable {

    /**
     * 访客内部id
     */
    private Integer VisitorInfoInnerId;
    /**
     * 访客姓名
     */
    private String VisitorName;
    /**
     * 访客邮箱
     */
    private String VisitorEmail;
    /**
     * 访客性别
     */
    private Integer VisitorSex;
    /**
     * 证件类型
     */
    private Integer VisitorIDType;
    /**
     * 证件编号
     */
    private String VisitorIDCode;
    /**
     * 出生日期
     */
    private String VisitorBirthday;
    /**
     * 车牌号
     */
    private String VisitorCar;
    /**
     * 手机号
     */
    private String VisitorPhone;
    /**
     * 访客密码
     */
    private String VisitorPwd;
    /**
     * 密码状态
     */
    private Integer VisitorPwdStates;
    /**
     * 访客状态
     */
    private Integer VisitorState;
    /**
     * 访客禁用标识;0正常1禁用
     */
    private Integer VisitorLock;
    /**
     * 创建日期
     */
    private String VisitorCreateDate;
    /**
     * 有效日期
     */
    private String VisitorVerifyDate;
    /**
     * 访客单位
     */
    private String VisitorCompany;
    /**
     * 访客地址
     */
    private String VisitorAddress;
    /**
     * 访客位置
     */
    private String VisitorPosition;
    /**
     * 微信名称
     */
    private String VisitorNikeName;
    /**
     * 坐标
     */
    private String VisitorLocation;
    /**
     * 删除标识
     */
    private Integer DelFlag;
    /**
     * 备用
     */
    private String StandByA;


}
