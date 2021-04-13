package com.dhsr.wx.cp.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @ProjectName: weixin-java-cp-demo
 * @Package: com.dhsr.wx.cp.entity
 * @ClassName: ${TYPE_NAME}
 * @Description: 车辆类型表
 * @Author: liuxiaogang
 * @CreateDate: 2019-08-22 18:55
 * @UpdateUser: liuxiaogang
 * @UpdateDate: 2019-08-22 18:55
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
public class ParkCartype implements Serializable {

    /**
     * 车辆类型内部id
     */
    private Integer CarTypeInnerId;
    /**
     * 类型编号
     */
    private String CarTypeId;
    /**
     * 类型名称
     */
    private String CarTypeName;
    /**
     * 备注
     */
    private String Remark;
    /**
     * 删除标识
     */
    private Integer DelFlag;
}
