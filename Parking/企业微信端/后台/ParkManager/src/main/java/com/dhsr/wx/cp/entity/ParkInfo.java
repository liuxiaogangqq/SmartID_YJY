package com.dhsr.wx.cp.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @ProjectName: ParkManager
 * @Package: com.dhsr.wx.cp.entity
 * @ClassName: 停车场信息
 * @Description: java类作用描述
 * @Author: liuxiaogang
 * @CreateDate: 2019-08-15 9:37
 * @UpdateUser: liuxiaogang
 * @UpdateDate: 2019-08-15 9:37
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
public class ParkInfo implements Serializable {

    /**
     * 车位主键
     */
    private Integer ParkInfoInnerId;
    /**
     * 车位编号
     */
    private String ParkInfoId;
    /**
     * 车位名称
     */
    private String ParkInfoName;
    /**
     * 备注
     */
    private String Remark;
    /**
     * 车位数量
     */
    private Integer CarsNumber;
    /**
     * 当前剩余车位
     */
    private Integer CurrentNumber;

}
