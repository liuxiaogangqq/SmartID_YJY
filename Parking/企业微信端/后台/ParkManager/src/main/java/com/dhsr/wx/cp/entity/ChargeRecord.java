package com.dhsr.wx.cp.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @ProjectName: weixin-java-cp-demo
 * @Package: com.dhsr.wx.cp.entity
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: liuxiaogang
 * @CreateDate: 2019-08-20 12:14
 * @UpdateUser: liuxiaogang
 * @UpdateDate: 2019-08-20 12:14
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
public class ChargeRecord implements Serializable {


    /**
     * 消费记录表id
     */
    private Integer ChargeRecordInnerId;
    /**
     * 消费时间
     */
    private String RecDateTime;
    /**
     * 车牌号
     */
    private String CarNumber;
    /**
     * 进入时间
     */
    private String InTime;
    /**
     * 出场时间
     */
    private String OutTime;
    /**
     * 费用
     */
    private Integer Money;
    /**
     * 车辆类型
     */
    private Integer CarType;
    /**
     * 缴费状态
     */
    private Integer ChargeState;
    /**
     * 备用1
     */
    private String StandbyA;
    /**
     * 支付时间
     */
    private String ChargeTime;
    /**
     * 备用2
     */
    private String StandbyB;
    /**
     * 访客记录主键
     */
    private Integer VisRecordInnerId;
    /**
     * 支付类型
     */
    private Integer ChargeType;
    /**
     * 订单号
     */
    private String OrderNo;


}
