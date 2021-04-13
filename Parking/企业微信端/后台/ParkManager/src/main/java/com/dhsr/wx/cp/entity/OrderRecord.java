package com.dhsr.wx.cp.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @ProjectName: ParkManager
 * @Package: com.dhsr.wx.cp.entity
 * @ClassName: 访客预约表
 * @Description: java类作用描述
 * @Author: liuxiaogang
 * @CreateDate: 2019-08-15 16:04
 * @UpdateUser: liuxiaogang
 * @UpdateDate: 2019-08-15 16:04
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
public class OrderRecord implements Serializable {
    /**
     * 记录内部id
     */
    private Integer RecordInnerId;
    /**
     * 访客信息内部id
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
     * 访客证件编号
     */
    private String VisitorIDCode;
    /**
     * 访客公司
     */
    private String VisitorCompany;
    /**
     * 被访人内部id
     */
    private Integer UserInnerId;
    /**
     * 访客事由
     */
    private String VisitorThing;
    /**
     * 访客头像
     */
    private String VisitorPhoto;
    /**
     * 访客进入图片
     */
    private String VisitorInPhoto;
    /**
     * 访客数量
     */
    private String VisitorNum;
    /**
     * 访客车牌号
     */
    private String VisitorCarCode;
    /**
     * 预约开始时间
     */
    private String VisitorBeginTime;
    /**
     * 预约结束时间
     */
    private String VisitorEndTime;
    /**
     * 记录时间
     */
    private String RecordTime;
    /**
     * 预约类型;预约类型 1：协助预约 2：自主预约
     */
    private Integer OrderType;
    /**
     * 预约码
     */
    private Integer OrderCode;
    /**
     * 被访人姓名
     */
    private String UserName;
    /**
     * 被访问部门
     */
    private String DepartMentName;
    /**
     * 申请状态;申请状态   0 申请中    1同意    2拒绝    3已取消  4 已进场   5 已支付  6已完成
     */
    private Integer VisitorState;
    /**
     * 状态切换时间
     */
    private String ApproveTime;
    /**
     * 拍照状态
     */
    private Integer CameraState;
    /**
     * 备注
     */
    private String Remark;
    /**
     * 卡号
     */
    private String CardId;
    /**
     * 二维码标识字
     */
    private String ACInfo;

    /**
     * 车辆类型
     */
    private Integer CarTypeInnerId;
    /**
     * 消费金额
     */
    private Integer Money;
}
