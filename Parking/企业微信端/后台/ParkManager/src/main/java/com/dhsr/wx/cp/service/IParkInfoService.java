package com.dhsr.wx.cp.service;

import com.alibaba.fastjson.JSONObject;
import com.dhsr.wx.cp.base.BusinessException;
import com.dhsr.wx.cp.entity.ChargeRecord;
import com.dhsr.wx.cp.entity.Operator;
import com.dhsr.wx.cp.entity.OrderRecord;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import org.junit.jupiter.api.Order;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 停车场服务接口
 * </p>
 *
 * @author liuxiaogang123
 * @since 2019-03-18
 */
public interface IParkInfoService {
    /**
     * 查询停车场剩余车辆和今日访客数量
     *
     * @param operator
     * @param jsonObject
     * @return
     */
    Map<String, Object> searchParkInfo(Operator operator, JSONObject jsonObject);

    /**
     * 获取公司和个人预约记录信息
     *
     * @param operator
     * @param jsonObject
     * @return
     */
    List<OrderRecord> searchCompanyVistor(Operator operator, JSONObject jsonObject);

    /**
     * 获取公司和个人预约记录信息总数
     *
     * @param operator
     * @param jsonObject
     * @return
     */
    Integer searchCompanyVistorCount(Operator operator, JSONObject jsonObject);

    /**
     * 公司预约登记
     *
     * @param operator
     * @param jsonObject
     * @return
     */
    Integer insertCompanyVistor(Operator operator, JSONObject jsonObject) throws Exception;

    /**
     * 员工预约登记
     *
     * @param operator
     * @param jsonObject
     * @return
     */
    Integer insertStafferVistor(Operator operator, JSONObject jsonObject) throws Exception;

    /**
     * 获取预约记录详情信息
     *
     * @param operator
     * @param jsonObject
     * @return
     */
    ChargeRecord searchVistorDetails(Operator operator, JSONObject jsonObject);


    /**
     * 取消预约接口
     */
    Map<String, Object> updateVistorDetailsCancel(Operator operator, JSONObject jsonObject) throws BusinessException;

    /**
     * 提前结束预约接口
     */
    Map<String, Object> updateVistorDetailsOver(Operator operator, JSONObject jsonObject) throws BusinessException;

    /**
     * 查询公司余额
     */

    Map<String, Object> searchDepartmentMoney(Operator operator, JSONObject requestJson);

    /**
     * 查询历史车牌信息
     */

    List<Map<String, Object>> searchHisCarInfo(Operator operator, JSONObject requestJson);

    /**
     * 查询车辆类型
     */
    List<Map<String, Object>> searchCarType(Operator operator);

    /**
     * 查询车辆消费
     */

    Map<String, Object> searchCarConsume(Operator operator, JSONObject jsonObject) throws BusinessException;

    /**
     * 查询车辆消费金额
     */

    Map<String, Object> searchCarFee(Operator operator, JSONObject jsonObject) throws BusinessException;

    /**
     * 微信回调支付
     */
    Map<String, Object> updateWXRecordInfo(WxPayOrderNotifyResult notifyResult) throws BusinessException;

    /**
     * 扣费
     *
     * @param jsonObject
     * @return
     */
   // int updateUserFee(Operator operator, JSONObject jsonObject);

    /**
     * 发送消息
     * @param operator
     * @param jsonObject
     * @return
     */
    Map<String,Object> sendMessage(Operator operator, JSONObject jsonObject);

    /**
     * 车辆剩余
     * @param jsonObject
     * @return
     */
    List<Map<String, Object>> searchParkNumByDate(JSONObject jsonObject);

}
