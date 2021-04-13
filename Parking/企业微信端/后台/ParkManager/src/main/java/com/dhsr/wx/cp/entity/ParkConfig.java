package com.dhsr.wx.cp.entity;

import lombok.*;

/**
 * @ProjectName: weixin-java-cp-demo
 * @Package: com.dhsr.wx.cp.entity
 * @ClassName: ${TYPE_NAME}
 * @Description: 配置表
 * @Author: liuxiaogang
 * @CreateDate: 2019-08-21 20:41
 * @UpdateUser: liuxiaogang
 * @UpdateDate: 2019-08-21 20:41
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
public class ParkConfig {

    /**
     * 配置内部id
     */
    private Integer ConfigInnerId;
    /**
     * 最小余额
     */
    private Integer minMoney;
    /**
     * 公司预约车不收费时间
     */
    private Integer MoMoneyATime;
    /**
     * 更新人公司预约公务车车不收费时间
     */
    private Integer NoMoneyBTime;
}
