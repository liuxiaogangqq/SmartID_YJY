package com.dhsr.smartid.tingchexitong.dao;

import java.util.List;
import java.util.Map;

import com.dhsr.smartid.tingchexitong.domain.CarInfo;
import com.dhsr.smartid.tingchexitong.domain.CarRecord;
import com.dhsr.smartid.tingchexitong.domain.CarRecord_User;
import com.dhsr.smartid.tingchexitong.domain.CarType;
import com.dhsr.smartid.tingchexitong.domain.ChargeRecord;
import com.dhsr.smartid.tingchexitong.domain.Holiday;
import com.dhsr.smartid.tingchexitong.domain.OrderRecord;
import com.dhsr.smartid.tingchexitong.domain.ParkDate;
import com.dhsr.smartid.tingchexitong.domain.ParkInfo;
import com.dhsr.smartid.tingchexitong.domain.PassRules;
import com.dhsr.smartid.tingchexitong.domain.UserCarInfo;
import com.dhsr.smartid.tingchexitong.domain.VisChargeRecordInfo;
import com.dhsr.smartid.tingchexitong.domain.YYCarRecord_User;
import com.dhsr.smartid.xiaofeiguanli.domain.ConLog_User;
import com.dhsr.smartid.xiaofeiguanli.domain.ConStatistics;

public interface TingcheMapper {

	/**
	 * 查询停车场信息
	 * @param map
	 * @return
	 */
	List<ParkInfo> selectParkInfo(Map<String, Object> map);

	/**
	 * 查询停车场总数
	 * @param map
	 * @return
	 */
	int selectParkInfoTotal(Map<String, Object> map);

	/**
	 * 新增停车场信息
	 * @param parkInfo
	 * @return
	 */
	int insertParkInfo(ParkInfo parkInfo);

	/**
	 * 更新停车场信息
	 * @param parkInfo
	 * @return
	 */
	int updateParkInfo(ParkInfo parkInfo);

	/**
	 * 根据主键查询停车场信息
	 * @param innerId
	 * @return
	 */
	ParkInfo selectParkInfoByInnerId(Integer innerId);

	/**
	 * 查询车辆 信息
	 * @param map
	 * @return
	 */
	List<UserCarInfo> selectUserCarInfo(Map<String, Object> map);

	/**
	 * 查询车辆信息总数
	 * @param map
	 * @return
	 */
	int selectUserCarInfoTotal(Map<String, Object> map);

	/**
	 * 新增车辆类型
	 * @param carType
	 * @return
	 */
	int insertCarType(CarType carType);

	/**
	 * 修改车辆类型
	 * @param carType
	 * @return
	 */
	int updateCarType(CarType carType);

	/**
	 * 查询车辆类型
	 * @param map
	 * @return
	 */
	List<CarType> selectCarType(Map<String, Object> map);

	/**
	 * 查询车辆类型总数
	 * @param map
	 * @return
	 */
	int selectCarTypeTotal(Map<String, Object> map);

	/**
	 * 根据主键查询车辆类型
	 * @param innerId
	 * @return
	 */
	CarType selectCarTypeByInnerId(Integer innerId);

	/**
	 * 新增车辆信息
	 * @param carInfo
	 * @return
	 */
	int insertCarInfo(CarInfo carInfo);

	/**
	 * 修改车辆信息
	 * @param carInfo
	 * @return
	 */
	int updateCarInfo(CarInfo carInfo);

	/**
	 * 新增通行规则
	 * @param passRules
	 * @return
	 */
	int insertPassRules(PassRules passRules);

	/**
	 * 修改通行规则
	 * @param passRules
	 * @return
	 */
	int updatePassRules(PassRules passRules);

	/**
	 * 查询通行规则
	 * @param map
	 * @return
	 */
	List<PassRules> selectPassRules(Map<String, Object> map);

	/**
	 * 查询通行规则总数
	 * @param map
	 * @return
	 */
	int selectPassRulesTotal(Map<String, Object> map);

	/**
	 * 添加节假日
	 * @param attendance_Holiday
	 * @return
	 */
	Integer insertAttendanceHoliday(Holiday attendance_Holiday);

	/**
	 * 更新节假日
	 * @param attendance_Holiday
	 * @return
	 */
	Integer updateAttendanceHoliday(Holiday attendance_Holiday);

	/**
	 * 查询节假日信息
	 * @param map
	 * @return
	 */
	List<Holiday> selectAttendanceHoliday(Map<String, Object> map);

	/**
	 * 查询节假日总数
	 * @param map
	 * @return
	 */
	int selectAttendanceHolidayCount(Map<String, Object> map);

	/**
	 * 删除节假日
	 * @param holidayInnerId
	 * @return
	 */
	Integer DelAttendanceHoliday(Integer holidayInnerId);


	/**
	 * 根据车牌查询车辆信息
	 * @param carCode
	 * @return
	 */
	UserCarInfo selectUserCarInfoByCarNumber(String carCode);

	/**
	 * 查询车辆通行记录
	 * @param map
	 * @return
	 */
	List<CarRecord> selectCarRecord(Map<String, Object> map);

	/**
	 * 查询是否有收费记录
	 * @param row
	 * @return
	 */
	int selectChargeRecordTotal(Map<String, Object> row);

	/**
	 * 查询预约车信息
	 * @param row
	 * @return
	 */
	List<OrderRecord> selectVisCarInfo(Map<String, Object> row);

	/**
	 * 查询未交费的停车记录
	 * @param row
	 * @return
	 */
	ChargeRecord selectChargeRecord(Map<String, Object> row);

	/**
	 * 更新车辆收费记录
	 * @param chargeRecord
	 */
	int updateChargeRecord(ChargeRecord chargeRecord);

	/**
	 * 查询预约车总数
	 * @param row
	 * @return
	 */
	int selectReservationCarTotal(Map<String, Object> row);

	/**
	 * 新增车辆收费记录
	 * @param chargeRecord
	 */
	void insertChargeRecord(ChargeRecord chargeRecord);

	/**
	 * 查询固定车辆通行记录
	 * @param map
	 * @return
	 */
	List<CarRecord_User> selectGDCarRecord_User(Map<String, Object> map);

	/**
	 * 查询固定车通行记录总数
	 * @param map
	 * @return
	 */
	int selectGDCarRecord_UserTotal(Map<String, Object> map);

	/**
	 * 查询预约车通行记录
	 * @param map
	 * @return
	 */
	List<YYCarRecord_User> selectYYCarRecord_User(Map<String, Object> map);

	/**
	 * 查询预约车通行记录总数
	 * @param map
	 * @return
	 */
	int selectYYCarRecord_UserTotal(Map<String, Object> map);

	/**
	 * 根据主键查询预约信息
	 * @param visRecordInnerId
	 * @return
	 */
	OrderRecord selectOrderRecordByVinnerId(Integer visRecordInnerId);

	/**
	 * 查询待收费的信息
	 * @param map
	 * @return
	 */
	List<ChargeRecord> selectChargeRecordWaitPayMoney();

	/**
	 * 根据车牌号查询进出记录
	 * @param carNumber
	 * @return
	 */
	CarRecord selectCarRecordByCarNumber(String carNumber);

	/**
	 * 根据主键查询通行规则
	 * @param carType
	 * @return
	 */
	List<PassRules> selectPassRulesByInnerId(Integer carType);

	/**
	 * 查询最大的通行记录id
	 * @return
	 */
	Integer selectMaxRecordInnerId();

	/**
	 * 添加车辆进出记录
	 * @param carRecord
	 */
	void insertCarRecord(CarRecord carRecord);

	/**
	 * 查询未完成的收费记录
	 * @param row
	 * @return
	 */
	ChargeRecord selectWWCChargeRecord(Map<String, Object> row);

	/**
	 * 根据主键查询收费记录
	 * @param chargeRecordInnerId
	 * @return
	 */
	ChargeRecord selectChargeRecordByInnerId(Integer chargeRecordInnerId);

	/**
	 * 更新访客车辆状态
	 * @param map
	 */
	void updateChargeRecordState(Map<String, Object> map);

	/**
	 * 查询异常收费记录信息
	 * @param map
	 * @return
	 */
	List<VisChargeRecordInfo> selectYCChargeRecord(Map<String, Object> map);

	/**
	 * 查询异常记录总数
	 * @param map
	 * @return
	 */
	int selectYCChargeRecordTotal(Map<String, Object> map);

	/**
	 * 查询班车收费记录
	 * @param map
	 * @return
	 */
	List<ConLog_User> selectConLog(Map<String, Object> map);

	/**
	 * 查询班车收费记录总数
	 * @param map
	 * @return
	 */
	Long selectConLogTotal(Map<String, Object> map);

	/**
	 * 查询临时车通行记录
	 * @param map
	 * @return
	 */
	List<YYCarRecord_User> selectLSCarRecord_User(Map<String, Object> map);

	/**
	 * 查询临时车通行记录总数
	 * @param map
	 * @return
	 */
	int selectLSCarRecord_UserTotal(Map<String, Object> map);

	/**
	 * 更新立方主键
	 * @param map
	 */
	void updateCarInfoLFInnerId(Map<String, String> map);

	/**
	 * 根据主键查询车辆信息
	 * @param carInfoInnerId
	 * @return
	 */
	CarInfo selectCarInfoByCarInfoInnerId(Integer carInfoInnerId);

	/**
	 * 平台新增特殊车辆预约
	 * @param map
	 * @return
	 */
	int insertVisitor_OrderRecord(Map<String, Object> map);

	/**
	 * 根据车牌查询最近的预约记录
	 * @param carNumber
	 * @return
	 */
	OrderRecord selectVisInfoByCarNumber(String carNumber);

	/**
	 * 更新进出记录
	 * @param carRecord
	 */
	void updateCarRecord(CarRecord carRecord);

	/**
	 * 查询收费状态为空的记录
	 * @return
	 */
	List<ChargeRecord> selectChargeRecordState();

	/**
	 * 根据主键查询通行规则
	 * @param innerId
	 * @return
	 */
	PassRules selectPassRuleByInnerId(Integer innerId);

	/**
	 * 查询特殊车辆通行记录
	 * @param map
	 * @return
	 */
	List<CarRecord> selectTSCarRecord(Map<String, Object> map);

	int selectTSCarRecordTotal(Map<String, Object> map);

	/**
	 * 查询收费记录
	 * @param map
	 * @return
	 */
	List<ChargeRecord> selectChargeRecordInfo(Map<String, Object> map);

	/**
	 * 查询收费记录总数
	 * @param map
	 * @return
	 */
	Integer selectChargeRecordInfoTotal(Map<String, Object> map);

	/**
	 * 查询最后一笔缴费记录
	 * @param carNumber
	 * @return
	 */
	ChargeRecord selectChargeRecordMax(String carNumber);

	/**
	 * 查询一段时间内的进出记录
	 * @param map
	 * @return
	 */
	List<CarRecord> selectCarRecordByCarNumberAndTime(Map<String, Object> map);

	/**
	 * 查询是否是公务车
	 * @param carNumber
	 * @return
	 */
	Integer selectGWCarInfo(String carNumber);

	List<ConStatistics> selectTcConStatistics(Map<String, Object> map);

	List<ConStatistics> selectVisConStatistics(Map<String, Object> map);

	/**
	 * 修改校验车牌信息
	 * @param carNumber
	 * @return
	 */
	UserCarInfo selectUserCarInfoByCarNumberNew(Map<String,Object> row);

	/**
	 * 查询过期车辆信息
	 * @return
	 */
	List<CarInfo> selectGQCarInfo();

	/**
	 * 查询待入场预约记录
	 * @param map
	 * @return
	 */
	OrderRecord selectOrderRecordByMap(Map<String, Object> map);

	/**
	 * 删除
	 * @param carInfo
	 */
	void deleteCarInfo(Map<String ,Object > map);

	/**
	 * 查询每天的车位数量
	 * @param map
	 * @return
	 */
	ParkDate selectParkDate(Map<String, Object> map);

	/**
	 * 更新每天车位数量
	 * @param parkDate
	 */
	Integer updateParkDate(ParkDate parkDate);

	/**
	 * 查询每天停车场剩余车位信息
	 * @return
	 */
	List<ParkDate> selectParkDataOneMonth(String todayDate);

	List<Integer> selectPayDepartment();

	List<Integer> selectPayCompany();

	Integer selectCarRecordTot(CarRecord carRecord);

	/**
	 * 查询公务车总数
	 * @param carNumber
	 * @return
	 */
	Integer selectGWTotal(String carNumber);

	ChargeRecord selectChargeRecordInfoByMap(Map<String, Object> map2);

	Integer insertLFChargeInfo(Map<String, Object> lfMap);

	Integer selectLFChargeInfo(Integer LFRecordId);

	Integer selectLFMaxId();

	UserCarInfo selectUserCarByCarNumber(String carNumber);

	/**
	 * 修改预约状态
	 * @param map
	 * @return
	 */
	Integer updateVisRecordState(Map<String, Object> map);

	/**
	 * 查询车位数量信息
	 * @param map
	 * @return
	 */
	List<ParkDate> selectParkDateInfo(Map<String, Object> map);

	/**
	 * 修改车位数量信息
	 * @param map
	 * @return
	 */
	Integer updateParkDateInfo(Map<String, Object> map);


}
