package com.dhsr.wx.cp.mapper;

import com.dhsr.wx.cp.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 停车场接口
 * </p>
 *
 * @author liuxiaogang123
 * @since 2019-03-18
 */
public interface ParkInfoMapper {

    /**
     * 查询车位剩余数量
     *
     * @return
     */
    List<Integer> searchParkNum(Map<String, Object> mso);

    /**
     * 查询今日访客数量
     *
     * @param mso
     * @return
     */
    Integer searchVisitorNum(Map<String, Object> mso);

    /**
     * 查询公司或个人预约记录
     *
     * @param mso
     * @return
     */
    List<OrderRecord> searchOrderRecord(Map<String, Object> mso);

    /**
     * 查询公司或个人预约记录集合
     *
     * @param mso
     * @return
     */
    List<OrderRecord> searchOrderRecordList(Map<String, Object> mso);

    /**
     * 统计公司或个人预约记录
     *
     * @param mso
     * @return
     */
    Integer searchOrderRecordCount(Map<String, Object> mso);

    /**
     * 统计公司或个人预约记录
     *
     * @param mso
     * @return
     */
    Integer searchOrderTime(Map<String, Object> mso);

    /**
     * 插入访客预约
     *
     * @param orderRecord
     * @return
     */
    Integer insertOrderRecord(OrderRecord orderRecord);

    /**
     * 插入访客信息
     *
     * @param visitorUserInfo
     * @return
     */
    Integer insertVisitorUserInfo(VisitorUserInfo visitorUserInfo);

    /**
     * 查询访客信息
     *
     * @param mso
     * @return
     */
    List<VisitorUserInfo> searchVisitorUserInfo(Map<String, Object> mso);

    /**
     * 查看记录详情
     *
     * @param mso
     * @return
     */
    ChargeRecord searchRecordDetails(Map<String, Object> mso);

    /**
     * 修改记录状态
     *
     * @param mso
     * @return
     */
    Integer updateRecordStates(Map<String, Object> mso);

    /**
     * 修改记录发送状态
     *
     * @param mso
     * @return
     */
    Integer updateRecordAC(Map<String, Object> mso);

    /**
     * 查询公司余额
     *
     * @param DepartmentInnerId
     * @return
     */
    Integer searchDepartmentMoney(Integer DepartmentInnerId);

    /**
     * 查询停车场配置
     *
     * @return
     */
    ParkConfig searchConfigInfo();

    /**
     * 查询车辆类型
     *
     * @param mso
     * @return
     */
    List<ParkCartype> searchCarType(Map<String, Object> mso);

    /**
     * 查询车辆类型
     *
     * @param mso
     * @return
     */
    List<ChargeRecord> searchChargeRecord(Map<String, Object> mso);

    /**
     * 更新订单状态
     *
     * @param mso
     * @return
     */
    int updateRecordInfo(Map<String, Object> mso);

    /**
     * 更新订单状态
     *
     * @param mso
     * @return
     */
    int updateRecordInfoByNo(Map<String, Object> mso);

    /**
     * 插入停车消费记录
     *
     * @param chargeRecord
     * @return
     */
    int insertChargeRecord(ChargeRecord chargeRecord);

    /**
     * 添加车位
     *
     * @param mso
     * @return
     */
    int updateParkNumAdd(Map<String, Object> mso);

    /**
     * 减车位
     *
     * @param mso
     * @return
     */
    int updateParkNumDel(Map<String, Object> mso);

    /**
     * 更新金额
     *
     * @param mso
     * @return
     */
    int updateRecordMoney(Map<String, Object> mso);

    /**
     * 查询是否是公务车
     *
     * @param CarNumber
     * @return
     */
    int searchGwCarInfo(String CarNumber);

    // 新增消费记录
    public Integer insertConLog(ConLog conLog);

    Department searchDepartmentUpTwo(Map<String, Object> mso);

    /**
     * 查询部门小于最低金额的人
     * @param mso
     * @return
     */
    List<Department> selectDeptMoney(Map<String, Object> mso);

    /**
     * 查找部门子节点
     * @param UpInnerId
     * @return
     */
    List<Integer> selectDeptChid(@Param("UpInnerId") Integer UpInnerId);


    /**
     * 插入车辆剩余情况
     * @param mso
     * @return
     */
    int insertParkDate(Map<String, Object> mso);
    /**
     * 车辆剩余情况
     * @param mso
     * @return
     */
    Map<String, Object> searchParkNumByDate(Map<String, Object> mso);

    /**
     * 查询车辆记录
     * @param mso
     * @return
     */
    Map<String, Object> selectCarRecord(Map<String, Object> mso);

    /**
     * 车辆剩余情况
     * @param mso
     * @return
     */
    List<Map<String, Object>> searchParkNumBy(Map<String, Object> mso);

    /**
     * 查询部门和公司信息
     * @param mso
     * @return
     */
    Integer selectPayInfo(Map<String,Object> mso);

    /**
     * 查询配置列表
     * @return
     */
    List<Integer> selectPayInfoList();

    /**
     * 查询父节点
     * @param DepartmentInnerId
     * @return
     */
    Integer selectDeptParent(@Param("DepartmentInnerId") Integer DepartmentInnerId);

    /**
     * 查询历史车辆
     * @param mso
     * @return
     */
    List<Map<String, Object>> searchOrderRecordHis(Map<String, Object> mso);

}
