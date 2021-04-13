package com.dhsr.smartid.tingchexitong.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.dhsr.smartid.tingchexitong.domain.CarInfo;
import com.dhsr.smartid.tingchexitong.domain.CarType;
import com.dhsr.smartid.tingchexitong.domain.ChargeRecord;
import com.dhsr.smartid.tingchexitong.domain.Holiday;
import com.dhsr.smartid.tingchexitong.domain.ParkInfo;
import com.dhsr.smartid.tingchexitong.domain.PassRules;
import com.dhsr.smartid.util.DataGridModel;

public interface TingcheService {

	/**
	 * 查询停车场信息
	 * @param dgm
	 * @param parkInfo
	 * @return
	 */
	String selectParkInfo(DataGridModel dgm, ParkInfo parkInfo);

	/**
	 * 新增停车场信息
	 * @param parkInfo
	 * @return
	 */
	String insertParkInfo(ParkInfo parkInfo);

	/**
	 * 更新停车场信息
	 * @param parkInfo
	 * @return
	 */
	String updateParkInfo(ParkInfo parkInfo);

	/**
	 * 查询车辆信息
	 * @param dgm
	 * @param request
	 * @return
	 */
	String selectuserCarInfo(DataGridModel dgm, HttpServletRequest request);

	/**
	 * 新增车辆类型
	 * @param carType
	 * @return
	 */
	String insertCarType(CarType carType);

	/**
	 * 修改车辆类型
	 * @param carType
	 * @return
	 */
	String updateCarType(CarType carType);

	/**
	 * 查询车辆类型
	 * @param dgm
	 * @param request
	 * @return
	 */
	String selectCarType(DataGridModel dgm, HttpServletRequest request);

	/**
	 * 查询车辆类型，下拉框显示
	 * @param dgm
	 * @param request
	 * @return
	 */
	String selectCarTypeBox(DataGridModel dgm, HttpServletRequest request);

	/**
	 * 新增车辆信息
	 * @param carInfo
	 * @param request
	 * @return
	 */
	String insertCarInfo(CarInfo carInfo, HttpServletRequest request);

	/**
	 * 修改车辆信息
	 * @param carInfo
	 * @param request
	 * @return
	 */
	String updateCarInfo(CarInfo carInfo, HttpServletRequest request);

	/**
	 * 新增通行规则
	 * @param passRules
	 * @param request
	 * @return
	 */
	String insertPassRules(PassRules passRules, HttpServletRequest request);

	/**
	 * 更新通行规则
	 * @param passRules
	 * @param request
	 * @return
	 */
	String updatePassRules(PassRules passRules, HttpServletRequest request);

	/**
	 * 查询通行规则
	 * @param dgm
	 * @param request
	 * @return
	 */
	String selectPassRules(DataGridModel dgm, HttpServletRequest request);

	/**
	 * 查询节假日
	 * @param request
	 * @param dgm
	 * @return
	 */
	String selectAttendanceHoliday(HttpServletRequest request, DataGridModel dgm);

	/**
	 * 新增节假日
	 * @param request
	 * @param holiday
	 * @return
	 */
	String insertAttendanceHoliday(HttpServletRequest request, Holiday holiday);

	/**
	 * 更新节假日
	 * @param request
	 * @param holiday
	 * @return
	 */
	String updateAttendanceHoliday(HttpServletRequest request, Holiday holiday);

	/**
	 * 删除节假日
	 * @param request
	 * @param holidayInnerId
	 * @return
	 */
	String delelteAttendanceHoliday(HttpServletRequest request, Integer holidayInnerId);

	/**
	 * 存储车辆的通行记录
	 * @param request
	 * @return
	 *//*
	String insertCarRecord(HttpServletRequest request);*/

	/**
	 * 查询预约车辆信息
	 * @param request
	 * @param dgm
	 * @return
	 */
	String selectReservationCar(HttpServletRequest request, DataGridModel dgm);

	/**
	 * 查询固定车通行记录
	 * @param request
	 * @param dgm
	 * @return
	 */
	String selectGDCarRecord(HttpServletRequest request, DataGridModel dgm);

	/**
	 * 查询预约车信息
	 * @param request
	 * @param dgm
	 * @return
	 */
	String selectYYCarRecord(HttpServletRequest request, DataGridModel dgm);

	/**
	 * 提前停止预约计费
	 * @param request
	 * @return
	 */
	String StopCharging(HttpServletRequest request);

	/**
	 * 根据主键查询收费记录
	 * @param chargeRecordInnerId
	 * @return
	 */
	ChargeRecord selectChargeRecordByInnerId(Integer chargeRecordInnerId);

	/**
	 * 异常收费记录查询
	 * @param request
	 * @param dgm
	 * @return
	 */
	String selectYCChargeRecord(HttpServletRequest request, DataGridModel dgm);

	/**
	 * 处理异常收费记录
	 * @param request
	 * @return
	 */
	String updateYCchargeRecordInfo(HttpServletRequest request);

	/**
	 * 查询消费记录
	 * @param request
	 * @param dgm
	 * @return
	 */
	String selectCarconLog(HttpServletRequest request, DataGridModel dgm);

	/**
	 * 查询首页预约车进出记录
	 * @param request
	 * @param dgm
	 * @return
	 */
	String selectSYYCarRecord(HttpServletRequest request, DataGridModel dgm);

	/**
	 * 查询临时车通行记录
	 * @param request
	 * @param dgm
	 * @return
	 */
	String selectSLSCarRecord(HttpServletRequest request, DataGridModel dgm);

	/**
	 * 查询车位剩余信息
	 * @param request
	 * @return
	 */
	String selectcarStopShengyu(HttpServletRequest request);

	/**
	 * 导出固定车辆信息
	 * @param request
	 * @return
	 */
	String selectCarInfoExcel(HttpServletRequest request) throws Exception;

	/**
	 * 导出预约车辆信息
	 * @param request
	 * @return
	 */
	String selectYuyuecheExcel(HttpServletRequest request) throws Exception;

	/**
	 * 平台新增预约临时车辆信息
	 * @param request
	 * @return
	 */
	String insertVisitor_OrderRecord(HttpServletRequest request);

	/**
	 * 查询特殊车辆通行记录
	 * @param request
	 * @param dgm
	 * @return
	 */
	String selectTSCarRecord(HttpServletRequest request, DataGridModel dgm);

	/**
	 * 查询所有收费记录
	 * @param request
	 * @return
	 */
	String selectChargeRecordInfo(HttpServletRequest request,DataGridModel dgm);

	String updateCarInfoTime(HttpServletRequest request);

	String selectTcConStatistics(HttpServletRequest request);

	/**
	 * 车辆信息导入校验
	 * @param request
	 * @return
	 */
	String showCarExcel(HttpServletRequest request) throws IOException ;

	/**
	 * 导出车辆收费信息表
	 * @param request
	 * @return
	 */
	String selectChargeRecordInfoExcel(HttpServletRequest request)throws Exception;

	/**
	 * 修改预约状态信息
	 * @param request
	 * @return
	 */
	String updateVisState(HttpServletRequest request);

	/**
	 * 查询一个月内车位数量
	 * @param request
	 * @param dgm
	 * @return
	 */
	String selectParkNumberDate(HttpServletRequest request, DataGridModel dgm);

	/**
	 * 修改车位数量信息
	 * @param request
	 * @return
	 */
	String updateParkDateNumber(HttpServletRequest request);

}
