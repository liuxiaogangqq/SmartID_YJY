package com.dhsr.smartid.tingchexitong.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.dhsr.smartid.peizhiguanli.domain.Operator;
import com.dhsr.smartid.peizhiguanli.domain.UserType;
import com.dhsr.smartid.renshiyewu.dao.RenshiyewuMapper;
import com.dhsr.smartid.renshiyewu.domain.Department;
import com.dhsr.smartid.renshiyewu.domain.User;
import com.dhsr.smartid.tingchexitong.dao.TingcheMapper;
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
import com.dhsr.smartid.util.DataGridModel;
import com.dhsr.smartid.util.ExportExcelUtils;
import com.dhsr.smartid.util.HttpClintUtil;
import com.dhsr.smartid.util.UpLoadExcel;
import com.dhsr.smartid.xiaofeiguanli.dao.XiaofeiguanliMapper;
import com.dhsr.smartid.xiaofeiguanli.domain.ConLog;
import com.dhsr.smartid.xiaofeiguanli.domain.ConLog_User;
import com.dhsr.smartid.xiaofeiguanli.domain.ConStatistics;
import com.dhsr.smartid.zhanghuyewu.dao.ZhanghuyewuMapper;
import com.dhsr.smartid.zhanghuyewu.domain.Account;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TingcheServiceImpl implements TingcheService{

	@Resource
	private TingcheMapper tingcheMapper;
	@Resource
	private RenshiyewuMapper renshiyewuMapper;
	@Resource
	private XiaofeiguanliMapper xiaofeiguanliMapper;
	@Resource
	private ZhanghuyewuMapper zhanghuyewuMapper;
	
	private static String ip = "http://127.0.0.1:8077/LockService/v1/LockService/";
	private static String lfip = "http://172.16.61.55:9988/Parking/Handheld/";
	private boolean tbFlag=true;
	
	@Override
	public String selectParkInfo(DataGridModel dgm, ParkInfo parkInfo) {
		Map<String,Object> map = new HashMap<>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("ParkInfoInnerId");
			dgm.setOrder("asc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(15);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		map.put("ParkInfoId", parkInfo.getParkInfoId());
		map.put("ParkInfoName", parkInfo.getParkInfoName());
		map.put("Remark", parkInfo.getRemark());
		List<ParkInfo> parkInfoList = tingcheMapper.selectParkInfo(map);
		List<Map<String,Object>> rows = new ArrayList<Map<String,Object>>();
		for (ParkInfo parkInfo2 : parkInfoList) {
			Map<String,Object> row = new HashMap<>();
			row.put("ParkInfoInnerId", parkInfo2.getParkInfoInnerId());
			row.put("ParkInfoId", parkInfo2.getParkInfoId());
			row.put("ParkInfoName", parkInfo2.getParkInfoName());
			row.put("CarsNumber", parkInfo2.getCarsNumber());
			row.put("CurrentCarNumber", parkInfo2.getCurrentCarNumber());
			row.put("VisNumber", parkInfo2.getVisNumber());
			row.put("CurrentVisNumber", parkInfo2.getCurrentVisNumber());
			row.put("Remark", parkInfo2.getRemark());
			rows.add(row);
		}
		JSONObject jo = new JSONObject();
		jo.put("total", tingcheMapper.selectParkInfoTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	public String insertParkInfo(ParkInfo parkInfo) {
		int rt = tingcheMapper.insertParkInfo(parkInfo);
		JSONObject jo = new JSONObject();
		if(rt>0){
			jo.put("code", 200);
			jo.put("msg", "添加成功");
		}else{
			jo.put("code", 500);
			jo.put("msg", "添加失败");
		}
		return jo.toString();
	}

	@Override
	public String updateParkInfo(ParkInfo parkInfo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ParkInfo oldParkInfo = tingcheMapper.selectParkInfoByInnerId(parkInfo.getParkInfoInnerId());
		int cha = oldParkInfo.getCarsNumber() - parkInfo.getCarsNumber();
		if(oldParkInfo.getCurrentCarNumber() - cha <= 0){
			parkInfo.setCurrentCarNumber(0);
		}else{
			parkInfo.setCurrentCarNumber(oldParkInfo.getCurrentCarNumber() - cha);
		}
		int cha2 = oldParkInfo.getVisNumber() - parkInfo.getVisNumber();
		if(oldParkInfo.getCurrentVisNumber() - cha2 <= 0){
			parkInfo.setCurrentVisNumber(0);
		}else{
			parkInfo.setCurrentVisNumber(oldParkInfo.getCurrentVisNumber() - cha2);
		}
		
		int rt = tingcheMapper.updateParkInfo(parkInfo);
		JSONObject jo = new JSONObject();
		if(rt>0){
			List<ParkDate> parkNums = tingcheMapper.selectParkDataOneMonth(sdf.format(new Date())+" 00:00:00");
			for (ParkDate parkDate : parkNums) {
				if(parkDate.getParkType() == 2){
					parkDate.setParkResidue(parkDate.getParkResidue()-cha);
					parkDate.setParkNum(parkDate.getParkNum()-cha);
					tingcheMapper.updateParkDate(parkDate);
				}else{
					parkDate.setParkResidue(parkDate.getParkResidue()-cha2);
					parkDate.setParkNum(parkDate.getParkNum()-cha2);
					tingcheMapper.updateParkDate(parkDate);
				}
			}
			jo.put("code", 200);
			jo.put("msg", "修改成功");
		}else{
			jo.put("code", 500);
			jo.put("msg", "修改失败");
		}
		return jo.toString();
	}

	@Override
	public String selectuserCarInfo(DataGridModel dgm, HttpServletRequest request) {
		String UserId = request.getParameter("UserId");
		String UserName = request.getParameter("UserName");
		String CarNumber = request.getParameter("CarNumber");
		String DepartmentInnerId = request.getParameter("DepartmentInnerId");
		String CarType = request.getParameter("CarType");
		Map<String,Object> map = new HashMap<>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("CarInfoInnerId");
			dgm.setOrder("asc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(15);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		map.put("UserId", UserId);
		map.put("UserName", UserName);
		map.put("CarNumber", CarNumber);
		map.put("DepartmentInnerId", DepartmentInnerId);
		map.put("CarTypeInnerId", CarType);
		List<UserCarInfo> userCarList = tingcheMapper.selectUserCarInfo(map);
		List<Map<String,Object>> rows = new ArrayList<>();
		for (UserCarInfo userCarInfo : userCarList) {
			Map<String,Object> row = new HashMap<>();
			row.put("UserId", userCarInfo.getUserId());
			row.put("UserInnerId", userCarInfo.getUserInnerId());
			row.put("UserName", userCarInfo.getUserName());
			row.put("DepartmentName", userCarInfo.getDepartmentName());
			row.put("CarNumber", userCarInfo.getCarNumber());
			row.put("CarNumber1", userCarInfo.getCarNumber1());
			row.put("CarNumber2", userCarInfo.getCarNumber2());
			row.put("CarNumber3", userCarInfo.getCarNumber3());
			row.put("CarNumber4", userCarInfo.getCarNumber4());
			if(userCarInfo.getStartTime() != null){
				row.put("StartTime", userCarInfo.getStartTime().substring(0, 19));
			}else{
				row.put("StartTime", userCarInfo.getStartTime());
			}
			if(userCarInfo.getEndTime() != null){
				row.put("EndTime", userCarInfo.getEndTime().substring(0, 19));
			}else{
				row.put("EndTime", userCarInfo.getEndTime());
			}
			row.put("CarTypeInnerId", userCarInfo.getCarTypeInnerId());
			row.put("CarTypeName", userCarInfo.getCarTypeName());
			row.put("DepartmentInnerId", userCarInfo.getDepartmentInnerId());
			row.put("CarInfoInnerId", userCarInfo.getCarInfoInnerId());
			row.put("ParkingNum", userCarInfo.getParkingNum());
			row.put("ParkInfo", userCarInfo.getParkInfo());
			if(userCarInfo.getParkInfo() != null){
				if(userCarInfo.getParkInfo() == 1001){
					row.put("ParkInfoName", "地面长期车");
				}else if(userCarInfo.getParkInfo() == 1007){
					row.put("ParkInfoName", "B1地库长期车");
				}else if(userCarInfo.getParkInfo() == 1008){
					row.put("ParkInfoName", "E2地库长期车");
				}else{
					row.put("ParkInfoName", "");
				}
			}
			rows.add(row);
		}
		JSONObject jo = new JSONObject();
		jo.put("total", tingcheMapper.selectUserCarInfoTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}
	
	 //  %JAVA_HOME%\jre\lib\security\local_policy.jar;%JAVA_HOME%\jre\lib\security\US_export_policy.jar;

	@Override
	public String insertCarType(CarType carType) {
		int rt = tingcheMapper.insertCarType(carType);
		JSONObject jo = new JSONObject();
		if(rt>0){
			jo.put("code", 200);
			jo.put("msg", "添加成功");
		}else{
			jo.put("code", 500);
			jo.put("msg", "添加失败");
		}
		return jo.toString();
	}
	
	@Override
	public String updateCarType(CarType carType) {
		int rt = tingcheMapper.updateCarType(carType);
		JSONObject jo = new JSONObject();
		if(rt>0){
			jo.put("code", 200);
			jo.put("msg", "修改成功");
		}else{
			jo.put("code", 500);
			jo.put("msg", "修改失败");
		}
		return jo.toString();
	}

	@Override
	public String selectCarType(DataGridModel dgm, HttpServletRequest request) {
		Map<String,Object> map = new HashMap<>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("CarTypeInnerId");
			dgm.setOrder("asc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(15);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		String CarTypeId = request.getParameter("CarTypeId");
		String CarTypeName = request.getParameter("CarTypeName");
		String Remark = request.getParameter("Remark");
		map.put("CarTypeId", CarTypeId);
		map.put("CarTypeName", CarTypeName);
		map.put("Remark", Remark);
		List<CarType> carTypeList = tingcheMapper.selectCarType(map);
		List<Map<String,Object>> rows = new ArrayList<>();
		for (CarType carType : carTypeList) {
			Map<String,Object> row = new HashMap<>();
			row.put("CarTypeId", carType.getCarTypeId());
			row.put("CarTypeName", carType.getCarTypeName());
			row.put("Remark", carType.getRemark());
			row.put("CarTypeInnerId", carType.getCarTypeInnerId());
			rows.add(row);
		}
		JSONObject jo = new JSONObject();
		jo.put("total", tingcheMapper.selectCarTypeTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	public String selectCarTypeBox(DataGridModel dgm, HttpServletRequest request) {
		Map<String,Object> map = new HashMap<>();
		map.put("rows", 10000);
		map.put("order", "asc");
		map.put("sort", "CarTypeInnerId");
		map.put("start", 0);
		List<CarType> carTypeList = tingcheMapper.selectCarType(map);
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		for (CarType carType : carTypeList) {
			jo = new JSONObject();
			jo.put("id", carType.getCarTypeInnerId());
			jo.put("text", carType.getCarTypeName());
			ja.add(jo);
		}
		return ja.toString();
		
	}

	@Override
	@Transactional
	public String insertCarInfo(CarInfo carInfo, HttpServletRequest request) {
		JSONObject jo = new JSONObject();
		String ParkInfo = request.getParameter("ParkInfo");
		UserCarInfo UsercarInfo1 = null;
		UserCarInfo UsercarInfo2 = null;
		UserCarInfo UsercarInfo3 = null;
		UserCarInfo UsercarInfo4 = null;
		UserCarInfo UsercarInfo = tingcheMapper.selectUserCarInfoByCarNumber(carInfo.getCarNumber());
		if(!carInfo.getCarNumber1().equals("")){
			UsercarInfo1 = tingcheMapper.selectUserCarInfoByCarNumber(carInfo.getCarNumber1());
		}if(!carInfo.getCarNumber2().equals("")){
			UsercarInfo2 = tingcheMapper.selectUserCarInfoByCarNumber(carInfo.getCarNumber2());
		}if(!carInfo.getCarNumber3().equals("")){
			UsercarInfo3 = tingcheMapper.selectUserCarInfoByCarNumber(carInfo.getCarNumber3());
		}if(!carInfo.getCarNumber4().equals("")){
			UsercarInfo4 = tingcheMapper.selectUserCarInfoByCarNumber(carInfo.getCarNumber4());
		}
		if(UsercarInfo != null){
			jo.put("code", 501);
			jo.put("msg", "已存在该车牌号，请核对后再试");
		}else if(UsercarInfo1 != null){
			jo.put("code", 501);
			jo.put("msg", "已存在备用车牌1，请核对后再试");
		}else if(UsercarInfo2 != null){
			jo.put("code", 501);
			jo.put("msg", "已存在备用车牌2，请核对后再试");
		}else if(UsercarInfo3 != null){
			jo.put("code", 501);
			jo.put("msg", "已存在备用车牌3，请核对后再试");
		}else if(UsercarInfo4 != null){
			jo.put("code", 501);
			jo.put("msg", "已存在备用车牌4，请核对后再试");
		}else{
			int rt = tingcheMapper.insertCarInfo(carInfo);
			if(tbFlag){
				Map<String,String> map = new HashMap<>();
				map.put("CarCode", carInfo.getCarNumber());
				map.put("CarCode1", carInfo.getCarNumber1());
				map.put("CarCode2", carInfo.getCarNumber2());
				map.put("CarCode3", carInfo.getCarNumber3());
				map.put("CarCode4", carInfo.getCarNumber4());
				map.put("ParkingLot", carInfo.getParkingNum()+"");
				UserCarInfo UserCar = tingcheMapper.selectUserCarInfoByCarNumber(carInfo.getCarNumber());
				map.put("UserId", UserCar.getUserId());
				map.put("UserName", UserCar.getUserName());
				map.put("StartTime", UserCar.getStartTime());
				map.put("EndTime", UserCar.getEndTime());
				map.put("EndTime", UserCar.getEndTime());
				map.put("ChargeRuleID", ParkInfo);
				String json = JSON.toJSONString(map);
				String doPost = HttpClintUtil.doPostJson(ip+"insertCarInfo", json);
				JSONObject fromObject = JSONObject.fromObject(doPost);
				Map<String, Object> datas = (Map) fromObject;
				if(datas.get("status").toString().equals("200")){
					map.put("CarInfoInnerId", carInfo.getCarInfoInnerId()+"");
					map.put("LFInnerId", datas.get("message").toString());
					tingcheMapper.updateCarInfoLFInnerId(map);
					if(rt>0){
						jo.put("code", 200);
						jo.put("msg", "添加成功");
					}else{
						jo.put("code", 500);
						jo.put("msg", "添加失败");
					}
				}else{
					jo.put("code", 500);
					jo.put("msg", "同步失败");
				}
			}else{
				if(rt>0){
					jo.put("code", 200);
					jo.put("msg", "添加成功");
				}else{
					jo.put("code", 500);
					jo.put("msg", "添加失败");
				}
			}
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String updateCarInfo(CarInfo carInfo, HttpServletRequest request) {
		JSONObject jo = new JSONObject();
		Map<String,Object> row = new HashMap<>();
		row.put("CarInfoInnerId", carInfo.getCarInfoInnerId());
		UserCarInfo UsercarInfo1 = null;
		UserCarInfo UsercarInfo2 = null;
		UserCarInfo UsercarInfo3 = null;
		UserCarInfo UsercarInfo4 = null;
		row.put("carCode", carInfo.getCarNumber());
		UserCarInfo UsercarInfo = tingcheMapper.selectUserCarInfoByCarNumberNew(row);
		if(!carInfo.getCarNumber1().equals("")){
			row.put("carCode", carInfo.getCarNumber1());
			UsercarInfo1 = tingcheMapper.selectUserCarInfoByCarNumberNew(row);
		}if(!carInfo.getCarNumber2().equals("")){
			row.put("carCode", carInfo.getCarNumber2());
			UsercarInfo2 = tingcheMapper.selectUserCarInfoByCarNumberNew(row);
		}if(!carInfo.getCarNumber3().equals("")){
			row.put("carCode", carInfo.getCarNumber3());
			UsercarInfo3 = tingcheMapper.selectUserCarInfoByCarNumberNew(row);
		}if(!carInfo.getCarNumber4().equals("")){
			row.put("carCode", carInfo.getCarNumber4());
			UsercarInfo4 = tingcheMapper.selectUserCarInfoByCarNumberNew(row);
		}
		if(UsercarInfo != null){
			jo.put("code", 501);
			jo.put("msg", "已存在该车牌号，请核对后再试");
		}else if(UsercarInfo1 != null){
			jo.put("code", 501);
			jo.put("msg", "已存在备用车牌1，请核对后再试");
		}else if(UsercarInfo2 != null){
			jo.put("code", 501);
			jo.put("msg", "已存在备用车牌2，请核对后再试");
		}else if(UsercarInfo3 != null){
			jo.put("code", 501);
			jo.put("msg", "已存在备用车牌3，请核对后再试");
		}else if(UsercarInfo4 != null){
			jo.put("code", 501);
			jo.put("msg", "已存在备用车牌4，请核对后再试");
		}else{
			int rt = tingcheMapper.updateCarInfo(carInfo);
			if(tbFlag){
				String ParkInfo = request.getParameter("ParkInfo");
				if(rt>0){
					Map<String,String> map = new HashMap<>();
					carInfo = tingcheMapper.selectCarInfoByCarInfoInnerId(carInfo.getCarInfoInnerId());
					map.put("CarCode", carInfo.getCarNumber());
					map.put("CarCode1", carInfo.getCarNumber1());
					map.put("CarCode2", carInfo.getCarNumber2());
					map.put("CarCode3", carInfo.getCarNumber3());
					map.put("CarCode4", carInfo.getCarNumber4());
					map.put("ParkingLot", carInfo.getParkingNum()+"");
					map.put("StartTime", carInfo.getStartTime());
					map.put("EndTime", carInfo.getEndTime());
					map.put("RecordID", carInfo.getLFInnerId());
					map.put("ChargeRuleID", ParkInfo);
					String json = JSON.toJSONString(map);
					String doPost = HttpClintUtil.doPostJson(ip+"updateCarInfo", json);
					JSONObject fromObject = JSONObject.fromObject(doPost);
					Map<String, Object> datas = (Map) fromObject;
					if(datas.get("status").toString().equals("200")){
						jo.put("code", 200);
						jo.put("msg", "修改成功");
					}else{
						jo.put("code", 200);
					    jo.put("msg", "同步失败");
					}
				}else{
					jo.put("code", 500);
					jo.put("msg", "修改失败");
				}
			}else{
				if(rt>0){
					jo.put("code", 200);
					jo.put("msg", "修改成功");
				}else{
					jo.put("code", 500);
					jo.put("msg", "修改失败");
				}
			}
		}
		return jo.toString();
	}

	@Override
	public String insertPassRules(PassRules passRules, HttpServletRequest request) {
		int rt = tingcheMapper.insertPassRules(passRules);
		JSONObject jo = new JSONObject();
		if(rt>0){
			jo.put("code", 200);
			jo.put("msg", "添加成功");
		}else{
			jo.put("code", 500);
			jo.put("msg", "添加失败");
		}
		return jo.toString();
	}

	@Override
	public String updatePassRules(PassRules passRules, HttpServletRequest request) {
		int rt = tingcheMapper.updatePassRules(passRules);
		JSONObject jo = new JSONObject();
		if(rt>0){
			jo.put("code", 200);
			jo.put("msg", "修改成功");
		}else{
			jo.put("code", 500);
			jo.put("msg", "修改失败");
		}
		return jo.toString();
	}

	@Override
	public String selectPassRules(DataGridModel dgm, HttpServletRequest request) {
		Map<String,Object> map = new HashMap<>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("PassRulesInnerId");
			dgm.setOrder("asc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(15);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		String PassRulesId = request.getParameter("PassRulesId");
		String PassRulesName = request.getParameter("PassRulesName");
		String Remark = request.getParameter("Remark");
		map.put("PassRulesId", PassRulesId);
		map.put("PassRulesName", PassRulesName);
		map.put("Remark", Remark);
		List<PassRules> passRulesList = tingcheMapper.selectPassRules(map);
		List<Map<String,Object>> rows = new ArrayList<>();
		for (PassRules passRules : passRulesList) {
			Map<String,Object> row = new HashMap<>();
			row.put("PassRulesInnerId", passRules.getPassRulesInnerId());
			row.put("PassRulesId", passRules.getPassRulesId());
			row.put("PassRulesName", passRules.getPassRulesName());
			row.put("StartTime", passRules.getStartTime().substring(0, 8));
			row.put("EndTime", passRules.getEndTime().substring(0, 8));
			row.put("Remark", passRules.getRemark());
			row.put("FreeTime", passRules.getFreeTime());
			row.put("Money", passRules.getMoney());
			row.put("CarTypeInnerId", passRules.getCarTypeInnerId());
			CarType carType = tingcheMapper.selectCarTypeByInnerId(passRules.getCarTypeInnerId());
			if(carType != null){
				row.put("CarTypeName", carType.getCarTypeName());
			}else{
				row.put("CarTypeName", "");
			}
			rows.add(row);
		}
		JSONObject jo = new JSONObject();
		jo.put("rows", rows);
		jo.put("total", tingcheMapper.selectPassRulesTotal(map));
		return jo.toString();
	}

	
	@Override
	public String selectAttendanceHoliday(HttpServletRequest request,
			DataGridModel dgm) {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy");
		String year = request.getParameter("year");
		Map<String, Object> map = new HashMap<String, Object>();
		if(year== null || year.equals("")){
			year = sdf.format(new Date());
		}
		map.put("year", year);
		List<Holiday> listHoliday = tingcheMapper.selectAttendanceHoliday(map);
		JSONArray ja = new JSONArray();
		for (Holiday holiday : listHoliday) {
			ja.add(holiday.getStartDate().substring(0, 10));
		}
		return ja.toString();
	}

	@Transactional
	@Override
	public String insertAttendanceHoliday(HttpServletRequest request,Holiday attendance_Holiday) {
		JSONObject jsonObject = new JSONObject();
		String[] split = attendance_Holiday.getStartDate().split(",");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("year", split[0].substring(2, 6));
		List<Holiday> listHoliday = tingcheMapper.selectAttendanceHoliday(map);
		List<String> insHoliday = new ArrayList<>();
		for (String string : split) {
			int numtime = 0;
			string = string.replace("[", "").replace("]", "");
			attendance_Holiday.setStartDate(string.substring(1, 11));
			String time = string.substring(1, 11).replace("[", "").replace("]", "")+" 00:00:00";
			for (int i= 0;i<listHoliday.size();i++) {
				if(listHoliday.get(i).getStartDate().substring(0, 19).equals(time)){
					numtime =0;
					listHoliday.remove(i);
					break;
				}else{
					numtime ++;
				}
			}
			if(listHoliday.size()<=0){
				numtime ++;
			}
			if(numtime > 0){
				insHoliday.add(string);
			}
		}
		for (String string : insHoliday) {
			string = string.replace("[", "").replace("]", "");
			attendance_Holiday.setStartDate(string.substring(1, 11));
			String time = string.substring(1, 11).replace("[", "").replace("]", "")+" 00:00:00";
			attendance_Holiday.setStartDate(time);
			Integer rt = tingcheMapper.insertAttendanceHoliday(attendance_Holiday);
			if(rt <= 0){
				jsonObject.put("code", 500);
				jsonObject.put("msg", "设定失败！");
				return jsonObject.toString();
			}
		}
		for (Holiday holiday : listHoliday) {
			Integer result = tingcheMapper.DelAttendanceHoliday(holiday.getHolidayInnerId());
			if(result <= 0){
				jsonObject.put("code", 500);
				jsonObject.put("msg", "设定失败！");
				return jsonObject.toString();
			}
		}
		jsonObject.put("code", 200);
		jsonObject.put("msg", "设定成功！");
		return jsonObject.toString();
	}

	@Transactional
	@Override
	public String updateAttendanceHoliday(HttpServletRequest request,Holiday attendance_Holiday) {
		JSONObject jsonObject = new JSONObject();
		Integer baseId = tingcheMapper.updateAttendanceHoliday(attendance_Holiday);

		if (baseId != null && baseId > 0) {
			jsonObject.put("code", 200);
			jsonObject.put("msg", "修改成功！");
		} else {
			jsonObject.put("code", 500);
			jsonObject.put("msg", "修改失败！");
		}
		return jsonObject.toString();
	}

	
	
	@Override
	public String delelteAttendanceHoliday(HttpServletRequest request,Integer HolidayInnerId) {
		Integer result = tingcheMapper.DelAttendanceHoliday(HolidayInnerId);
		JSONObject jsonObject = new JSONObject();
		if (result != null && result > 0) {
			jsonObject.put("code", 200);
			jsonObject.put("msg", "删除成功！");
		} else {
			jsonObject.put("code", 500);
			jsonObject.put("msg", "删除失败！");
		}
		return jsonObject.toString();
	}

	@Override
	public String selectReservationCar(HttpServletRequest request, DataGridModel dgm) {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		String StartTime = request.getParameter("StartTime");
		String EndTime = request.getParameter("EndTime");
		String CarNumber = request.getParameter("CarNumber");
		Map<String,Object> map = new HashMap<>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("RecordInnerId");
			dgm.setOrder("desc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(15);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		if(StartTime != null && !StartTime.equals("") && !StartTime.equals(" 00:00:00")){
			map.put("StartTime", StartTime);
		}else{
			map.put("StartTime", sdf.format(new Date())+" 00:00:00");
		}
		if(EndTime != null && !EndTime.equals("") && !EndTime.equals(" 23:59:00")){
			map.put("EndTime", EndTime);
		}else{
			map.put("EndTime", sdf.format(new Date())+" 23:59:00");
		}
		map.put("CarNumber", CarNumber);
		List<OrderRecord> reservationList = tingcheMapper.selectVisCarInfo(map);
		List<Map<String,Object>> rows = new ArrayList<>();
		for (OrderRecord reservationCar : reservationList) {
			Map<String,Object> row = new HashMap<>();
			row.put("RecordInnerId", reservationCar.getRecordInnerId());
			row.put("VisitorCompany", reservationCar.getVisitorCompany());
			row.put("VisitorName", reservationCar.getVisitorName());
			row.put("VisitorCarCode", reservationCar.getVisitorCarCode());
			if(reservationCar.getVisitorBeginTime() != null){
				row.put("VisitorBeginTime", reservationCar.getVisitorBeginTime().substring(0, 19));
			}else{
				row.put("VisitorBeginTime", reservationCar.getVisitorBeginTime());
			}
			if(reservationCar.getVisitorEndTime() != null){
				row.put("VisitorEndTime", reservationCar.getVisitorEndTime().substring(0, 19));
			}else{
				row.put("VisitorEndTime", reservationCar.getVisitorEndTime());
			}
			if(reservationCar.getOrderType() == 1){
				row.put("OrderTypeName", "协助预约");
			}else if(reservationCar.getOrderType() == 2){
				row.put("OrderTypeName", "自主预约");
			}else if(reservationCar.getOrderType() == 3){
				row.put("OrderTypeName", "平台预约");
			}
			if(reservationCar.getVisitorState() == 0){
				row.put("VisitorState", "申请中");
			}else if(reservationCar.getVisitorState() == 1){
				row.put("VisitorState", "待入场");
			}else if(reservationCar.getVisitorState() == 2){
				row.put("VisitorState", "拒绝");
			}else if(reservationCar.getVisitorState() == 3){
				row.put("VisitorState", "已取消");
			}else if(reservationCar.getVisitorState() == 4){
				row.put("VisitorState", "已进场");
			}else if(reservationCar.getVisitorState() == 5){
				row.put("VisitorState", "已支付");
			}else if(reservationCar.getVisitorState() == 6){
				row.put("VisitorState", "已完成");
			}else if (reservationCar.getVisitorState() == 7) {
				row.put("VisitorState", "已出场");
			} else if (reservationCar.getVisitorState() == 8) {
				row.put("VisitorState", "已逾期");
			} else if (reservationCar.getVisitorState() == 9) {
				row.put("VisitorState", "待支付");
			} else{
				row.put("VisitorState", "");
			}
			row.put("VisitorStateInnerId", reservationCar.getVisitorState());
			row.put("UserName", reservationCar.getUserName());
			row.put("DepartMentName", reservationCar.getDepartMentName());
			if(reservationCar.getRecordTime() != null){
				row.put("RecordTime", reservationCar.getRecordTime().substring(0, 19));
			}
			rows.add(row);
		}
		int total = tingcheMapper.selectReservationCarTotal(map);
		JSONObject jo = new JSONObject();
		jo.put("total", total);
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	public String selectGDCarRecord(HttpServletRequest request, DataGridModel dgm) {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		String StartTime = request.getParameter("StartTime");
		String EndTime = request.getParameter("EndTime");
		String CarNumber = request.getParameter("CarNumber");
		String UserId = request.getParameter("UserId");
		String Username = request.getParameter("UserName");
		Map<String,Object> map = new HashMap<>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("CarRecordInnerId");
			dgm.setOrder("desc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(15);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		if(StartTime == null || StartTime.equals("")){
			map.put("StartTime", sdf.format(new Date())+" 00:00:00");
		}else{
			map.put("StartTime", StartTime);
		}
		if(EndTime == null || EndTime.equals("")){
			map.put("EndTime", sdf.format(new Date())+" 23:59:00");
		}else{
			map.put("EndTime", EndTime);
		}
		map.put("CarNumber", CarNumber);
		map.put("UserId", UserId);
		map.put("UserName", Username);
		List<Map<String,Object>> rows = new ArrayList<>();
		List<CarRecord_User> carRecordList = tingcheMapper.selectGDCarRecord_User(map);
		for (CarRecord_User carRecord_User : carRecordList) {
			Map<String,Object> row = new HashMap<>();
			row.put("UserId", carRecord_User.getUserId());
			row.put("UserName", carRecord_User.getUserName());
			row.put("CarNumber", carRecord_User.getCarNumber());
			row.put("EntryTime", carRecord_User.getEntryTime());
			row.put("DepartmentName", carRecord_User.getDepartmentName());
			if(carRecord_User.getInOrOut() == 0){
				row.put("InOrOut", "进");
			}else{
				row.put("InOrOut", "出");
			}
			rows.add(row);
		}
		JSONObject jo = new JSONObject();
		jo.put("total", tingcheMapper.selectGDCarRecord_UserTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	public String selectYYCarRecord(HttpServletRequest request, DataGridModel dgm) {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		String StartTime = request.getParameter("StartTime");
		String EndTime = request.getParameter("EndTime");
		String CarNumber = request.getParameter("CarNumber");
		String Username = request.getParameter("UserName");
		Map<String,Object> map = new HashMap<>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("CarRecordInnerId");
			dgm.setOrder("desc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(15);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		if(StartTime == null || StartTime.equals("")){
			map.put("StartTime", sdf.format(new Date())+" 00:00:00");
		}else{
			map.put("StartTime", StartTime);
		}
		if(EndTime == null || EndTime.equals("")){
			map.put("EndTime", sdf.format(new Date())+" 23:59:00");
		}else{
			map.put("EndTime", EndTime);
		}
		map.put("CarNumber", CarNumber);
		map.put("UserName", Username);
		map.put("Type", "1,2,3");
		List<Map<String,Object>> rows = new ArrayList<>();
		List<YYCarRecord_User> carRecordList = tingcheMapper.selectYYCarRecord_User(map);
		for (YYCarRecord_User carRecord_User : carRecordList) {
			Map<String,Object> row = new HashMap<>();
			row.put("VisitorCompany", carRecord_User.getVisitorCompany());
			row.put("CarNumber", carRecord_User.getCarNumber());
			row.put("EntryTime", carRecord_User.getEntryTime());
			row.put("VisitorName", carRecord_User.getVisitorName());
			if(carRecord_User.getInOrOut() == 0){
				row.put("InOrOut", "进");
			}else{
				row.put("InOrOut", "出");
			}
			rows.add(row);
		}
		JSONObject jo = new JSONObject();
		jo.put("total", tingcheMapper.selectYYCarRecord_UserTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	public String StopCharging(HttpServletRequest request) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONObject jo = new JSONObject();
		Map<String,Object> map = new HashMap<>();
		map.put("DateTime", sdf.format(new Date()));
		map.put("CarNumber", request.getParameter("CarNumber"));
		ChargeRecord chargeRecord = tingcheMapper.selectChargeRecord(map);
		if(chargeRecord != null){
			chargeRecord.setOutTime(sdf.format(new Date()));
			tingcheMapper.updateChargeRecord(chargeRecord);
			//发送查询金额接口
			Map<String,String> row = new HashMap<>();
			if(chargeRecord.getInTime() != null && chargeRecord.getOutTime() != null){
				row.put("beginTime", chargeRecord.getInTime());
				row.put("endTime", chargeRecord.getOutTime());
				String doPost = HttpClintUtil.doPost(HttpClintUtil.ip, row);
				JSONObject  jasonObject = JSONObject.fromObject(doPost);
			    Map<String,Object> rt = (Map<String,Object>)jasonObject;
			    int money = 0;
			    if(rt.get("resCode").toString().equals("0")){
			    	money = Integer.valueOf(rt.get("totalMoney").toString());
			    }
				//扣费
			    if(money > 0){
			    	Integer visRecordInnerId = chargeRecord.getVisRecordInnerId();
			    	OrderRecord orderRecord = tingcheMapper.selectOrderRecordByVinnerId(visRecordInnerId);
			    	User user = renshiyewuMapper.selectDepUserInfo(orderRecord.getUserInnerId());
			    	//查询金额
			    	map.put("UserInnerId", user.getUserInnerId());
			    	map.put("AccountTypeInnerId", 1);
			    	Account account = zhanghuyewuMapper.selectAccount(map);
			    	ConLog conLog = new ConLog();
			    	conLog.setMarkInnerId(0);
					conLog.setMarkId("停车收费");
					conLog.setMarkTypeInnerId(1);
					conLog.setUserInnerId(user.getUserInnerId());
					conLog.setDepartmentInnerId(user.getDepartmentInnerId());
					conLog.setCompanyInnerId(user.getCompanyInnerId());
					conLog.setConTerminalInnerId(1);
					conLog.setTerminalTypeInnerId(1);
					conLog.setMerchantInnerId(1);
					conLog.setAppInnerId(1);
					conLog.setAreaInnerId(1);
					conLog.setPersonMoney(money);
					conLog.setAllowanceMoney(0);
					conLog.setNumberMoney(0);
					conLog.setMoney(money);
					conLog.setDiscountMoney(money);
					conLog.setProportion(10000);
					conLog.setConDatetime(sdf.format(new Date()));
					conLog.setUploadDatetime(sdf.format(new Date()));
					conLog.setConTypeInnerId(1);
					conLog.setConPattern(5);
					conLog.setConWay(0);
					conLog.setLimitTimes(0);
					conLog.setOffLine(1);
					conLog.setPersonAFMoney(account.getMoney() - money);
					conLog.setPersonBFMoney(account.getMoney());
					conLog.setNumberBFMoney(0);
					conLog.setNumberAFMoney(0);
					conLog.setAllowanceAFMoney(0);
					conLog.setAllowanceBFMoney(0);
					conLog.setRules("");
					conLog.setStandbyB("0");
					int result = xiaofeiguanliMapper.insertConLog(conLog);
					if(result > 0){
						jo.put("code", "200");
						jo.put("msg", "结束成功，已扣费");
					}else{
						jo.put("code", "200");
						jo.put("msg", "结束成功，扣费失败");
					}
			    }else{
			    	jo.put("code", "200");
					jo.put("msg", "结束成功");
			    }
			}else{
				jo.put("code", "200");
				jo.put("msg", "结束成功,此记录为异常记录，请处理");
			}
		}else{
			//创建收费记录
			chargeRecord = new ChargeRecord();
			chargeRecord.setCarNumber(request.getParameter("CarNumber"));
			chargeRecord.setRecDateTime(sdf.format(new Date()).substring(0, 10));
			chargeRecord.setOutTime(sdf.format(new Date()));
			chargeRecord.setCarType(1);
			chargeRecord.setChargeState(1);
			tingcheMapper.insertChargeRecord(chargeRecord);
			jo.put("code", "200");
			jo.put("msg", "结束计费，此记录为异常记录，请处理");
		}
		return jo.toString();
	}

	@Override
	public ChargeRecord selectChargeRecordByInnerId(Integer chargeRecordInnerId) {
		return tingcheMapper.selectChargeRecordByInnerId(chargeRecordInnerId);
	}

	@Override
	public String selectYCChargeRecord(HttpServletRequest request, DataGridModel dgm) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String StartTime = request.getParameter("StartTime");
		String EndTime = request.getParameter("EndTime");
		String CarNumber = request.getParameter("CarNumber");
		Map<String,Object> map = new HashMap<>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("ChargeRecordInnerId");
			dgm.setOrder("desc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(15);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		if(StartTime == null || StartTime.equals("")){
			map.put("StartTime", sdf.format(new Date())+" 00:00:00");
		}else{
			map.put("StartTime", StartTime);
		}
		if(EndTime == null || EndTime.equals("")){
			map.put("EndTime", sdf.format(new Date())+" 23:59:00");
		}else{
			map.put("EndTime", EndTime);
		}
		map.put("CarNumber", CarNumber);
		List<Map<String,Object>> rows = new ArrayList<>();
		List<VisChargeRecordInfo> carRecordList = tingcheMapper.selectYCChargeRecord(map);
		for (VisChargeRecordInfo chargeRecord : carRecordList) {
			Map<String,Object> row = new HashMap<>();
			row.put("ChargeRecordInnerId", chargeRecord.getChargeRecordInnerId());
			row.put("VisitorName", chargeRecord.getVisitorName());
			row.put("VisitorCompany", chargeRecord.getVisitorCompany());
			row.put("CarNumber", chargeRecord.getCarNumber());
			if(chargeRecord.getInTime() != null){
				row.put("InTime", chargeRecord.getInTime().substring(0, 19));
			}else{
				row.put("InTime", chargeRecord.getInTime());
			}
			if(chargeRecord.getOutTime() != null){
				row.put("OutTime", chargeRecord.getOutTime().substring(0, 19));
			}else{
				row.put("OutTime", chargeRecord.getOutTime());
			}
			if(chargeRecord.getChargeState() == 1){
				row.put("ChargeStateName", "异常记录");
			}else if(chargeRecord.getChargeState() == 2){
				row.put("ChargeStateName", "待收费");
			}else if(chargeRecord.getChargeState() == 3){
				row.put("ChargeStateName", "已完成");
			}
			
			rows.add(row);
		}
		JSONObject jo = new JSONObject();
		jo.put("total", tingcheMapper.selectYCChargeRecordTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	public String updateYCchargeRecordInfo(HttpServletRequest request) {
		String ChargeRecordInnerId = request.getParameter("ChargeRecordInnerId");
		String InTime = request.getParameter("InTime");
		String OutTime = request.getParameter("OutTime");
		ChargeRecord chargeRecord = tingcheMapper.selectChargeRecordByInnerId(Integer.valueOf(ChargeRecordInnerId));
		chargeRecord.setInTime(InTime);
		chargeRecord.setOutTime(OutTime);
		chargeRecord.setChargeState(2);
		int rt = tingcheMapper.updateChargeRecord(chargeRecord);
		JSONObject jo = new JSONObject();
		if(rt > 0){
			jo.put("code", 200);
			jo.put("msg", "处理成功");
		}else{
			jo.put("code", 500);
			jo.put("msg", "处理失败");
		}
		return jo.toString();
	}

	@Override
	public String selectCarconLog(HttpServletRequest request, DataGridModel dgm) {
		Map<String, Object> map = new HashMap<String, Object>();
		String UserName = request.getParameter("UserName");
		String UserId = request.getParameter("UserId");
		String departmentInnerId = request.getParameter("DepartmentInnerId");
		map.put("DepartmentInnerId", departmentInnerId);
		String StartTime = request.getParameter("StartTime");
		String EndTime = request.getParameter("EndTime");
		String ConWay = request.getParameter("ConWay");
		String ConPattern = request.getParameter("ConPattern");
		String Flag = request.getParameter("Flag");
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("ConLogInnerId");
			dgm.setOrder("asc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(100000000);
		}
		String[] sortS = dgm.getSort().split(",");
		String[] orderS = dgm.getOrder().split(",");
		String sortString = "";
		for (int i = 0; i < sortS.length; i++) {
			if (i == sortS.length - 1) {
				sortString += sortS[i] + " " + orderS[i];
			} else {
				sortString += sortS[i] + " " + orderS[i] + ",";
			}
		}
		map.put("MerchantList", "");
		map.put("rows", dgm.getRows());
		map.put("sort", sortString);
		map.put("start", dgm.getStart());
		map.put("UserId", UserId);
		map.put("UserName", UserName);
		map.put("StartTime", StartTime);
		map.put("EndTime", EndTime);
		map.put("TimeType", "ConDatetime");
		map.put("ConWay", ConWay);
		map.put("ConPattern", ConPattern);
		if(Flag == null || Flag.equals("")){
			Flag = "1";
		}
		map.put("Flag", Flag);
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<ConLog_User> ConLogList = tingcheMapper.selectConLog(map);
		for (ConLog_User conLog : ConLogList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("ConLogInnerId", conLog.getConLogInnerId());
			row.put("UserId", conLog.getUserId());
			row.put("Remark", conLog.getRemark());
			row.put("CompanyName", conLog.getCompanyName());
			row.put("DepartmentName", conLog.getDepartmentName());
			row.put("UserName", conLog.getUserName());
			row.put("PersonMoney", conLog.getPersonMoney() / 100.0);
			row.put("AllowanceMoney", conLog.getAllowanceMoney() / 100.0);
			/*row.put("NumberMoney", conLog.getNumberMoney() / 100.0);*/
			row.put("Money", conLog.getMoney() / 100.0);
			row.put("DiscountMoney", conLog.getDiscountMoney() / 100.0);
			row.put("Proportion", conLog.getProportion() / 100);
			row.put("ConDatetime", conLog.getConDatetime().substring(0, 19));
			row.put("UploadDatetime", conLog.getUploadDatetime().substring(0, 19));
			row.put("ConTerminalName", conLog.getConTerminalName());
			row.put("MerchantName", conLog.getMerchantName());
			row.put("AllowanceAFMoney", conLog.getAllowanceAFMoney() / 100.0);
			row.put("AllowanceBFMoney", conLog.getAllowanceBFMoney() / 100.0);
			row.put("PersonAFMoney", conLog.getPersonAFMoney() / 100.0);
			row.put("PersonBFMoney", conLog.getPersonBFMoney() / 100.0);
			/*row.put("NumberAFMoney", conLog.getNumberAFMoney() / 100.0);
			row.put("NumberBFMoney", conLog.getNumberBFMoney() / 100.0);*/
			row.put("Rules", conLog.getRules());
			row.put("StandbyA", conLog.getStandbyA());
			row.put("StandbyB", conLog.getStandbyB());
			row.put("StandbyC", conLog.getStandbyC());
			row.put("StandbyD", conLog.getStandbyD());
			rows.add(row);
		}
		Long total = tingcheMapper.selectConLogTotal(map);
		if (total > 0 && dgm.getRows() * dgm.getPage() >= total) {
			map.put("rows", 100000000);
			map.put("start", 0);
			List<ConLog_User> ConLogList1 = tingcheMapper.selectConLog(map);
			Long PersonMoneySum = (long) 0;
			Long AllowanceMoneySum = (long) 0;
			/*Long NumberMoneySum = (long) 0;*/
			Long MoneySum = (long) 0;
			Long DiscountMoneySum = (long) 0;
			Long Count = (long) 0;
			for (ConLog_User conLog : ConLogList1) {
				Count++;
				PersonMoneySum += conLog.getPersonMoney();
				AllowanceMoneySum += conLog.getAllowanceMoney();
				/*NumberMoneySum += conLog.getNumberMoney();*/
				MoneySum += conLog.getMoney();
				DiscountMoneySum += conLog.getDiscountMoney();
			}
			// 最后一行合计
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("UserId", "合计");
			row.put("PersonMoney", PersonMoneySum / 100.0);
			row.put("AllowanceMoney", AllowanceMoneySum / 100.0);
			/*row.put("FavorableMoney", NumberMoneySum / 100.0);*/
			row.put("Money", MoneySum / 100.0);
			row.put("DiscountMoney", DiscountMoneySum / 100.0);
			row.put("UserName", Count + "人");
			rows.add(row);
		}
		jo.put("total", total);
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	public String selectSYYCarRecord(HttpServletRequest request, DataGridModel dgm) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String StartTime = sdf.format(new Date())+" 00:00:00";
		String EndTime = sdf.format(new Date())+" 23:59:00";
		Map<String, Object> map = new HashMap<>();
		map.put("rows", 8);
		map.put("order", "desc");
		map.put("sort", "CarRecordInnerId");
		map.put("start", 0);
		map.put("StartTime", StartTime);
		map.put("EndTime", EndTime);
		map.put("Type", "1,3");
		List<Map<String, Object>> rows = new ArrayList<>();
		List<YYCarRecord_User> carRecordList = tingcheMapper.selectYYCarRecord_User(map);
		for (YYCarRecord_User carRecord_User : carRecordList) {
			Map<String, Object> row = new HashMap<>();
			row.put("VisitorCompany", carRecord_User.getVisitorCompany());
			row.put("CarNumber", carRecord_User.getCarNumber());
			row.put("EntryTime", carRecord_User.getEntryTime());
			row.put("VisitorName", carRecord_User.getVisitorName());
			if (carRecord_User.getInOrOut() == 0) {
				row.put("InOrOut", "进");
			} else {
				row.put("InOrOut", "出");
			}
			rows.add(row);
		}
		JSONObject jo = new JSONObject();
		jo.put("total", tingcheMapper.selectYYCarRecord_UserTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}
	@Override
	public String selectSLSCarRecord(HttpServletRequest request, DataGridModel dgm) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String StartTime = sdf.format(new Date())+" 00:00:00";
		String EndTime = sdf.format(new Date())+" 23:59:00";
		Map<String,Object> map = new HashMap<>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("CarRecordInnerId");
			dgm.setOrder("desc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(15);
		}
		map.put("rows", 8);
		map.put("order", "desc");
		map.put("sort", "CarRecordInnerId");
		map.put("start", 0);
		map.put("StartTime", StartTime);
		map.put("EndTime", EndTime);
		List<Map<String,Object>> rows = new ArrayList<>();
		List<YYCarRecord_User> carRecordList = tingcheMapper.selectLSCarRecord_User(map);
		for (YYCarRecord_User carRecord_User : carRecordList) {
			Map<String,Object> row = new HashMap<>();
			row.put("VisitorCompany", carRecord_User.getVisitorCompany());
			row.put("CarNumber", carRecord_User.getCarNumber());
			row.put("EntryTime", carRecord_User.getEntryTime());
			row.put("VisitorName", carRecord_User.getVisitorName());
			if (carRecord_User.getInOrOut() == 0) {
				row.put("InOrOut", "进");
			} else {
				row.put("InOrOut", "出");
			}
			rows.add(row);
		}
		JSONObject jo = new JSONObject();
		jo.put("total", tingcheMapper.selectLSCarRecord_UserTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	public String selectcarStopShengyu(HttpServletRequest request) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		JSONObject jo = new JSONObject();
		Map<String,Object> map = new HashMap<>();
		map.put("ParkDate", sdf.format(new Date())+" 00:00:00");
		map.put("ParkType", 1);
		ParkDate parkDate = tingcheMapper.selectParkDate(map);
		jo.put("fangke", parkDate.getParkResidue());
		Integer a = parkDate.getParkNum() -parkDate.getParkResidue();
		map.put("ParkType", 2);
		ParkDate parkDate2 = tingcheMapper.selectParkDate(map);
		jo.put("yuangong", parkDate2.getParkResidue());
		int zhanyong = a+(parkDate2.getParkNum() -parkDate2.getParkResidue());
		jo.put("zhanyong", zhanyong);
		//查询异常记录总数
		map.clear();
		int yichang = tingcheMapper.selectYCChargeRecordTotal(map);
		jo.put("yichang", yichang);
		jo.put("code", 200);
		return jo.toString();
	}

	@Override
	public String selectCarInfoExcel(HttpServletRequest request) throws Exception {
		String UserId = request.getParameter("UserId");
		String UserName = request.getParameter("UserName");
		String CarNumber = request.getParameter("CarNumber");
		String DepartmentInnerId = request.getParameter("DepartmentInnerId");
		String CarType = request.getParameter("CarType");
		Map<String,Object> map = new HashMap<>();
		map.put("rows",1000000);
		map.put("order", "asc");
		map.put("sort", "CarInfoInnerId");
		map.put("start", 0);
		map.put("UserId", UserId);
		map.put("UserName", UserName);
		map.put("CarNumber", CarNumber);
		map.put("DepartmentInnerId", DepartmentInnerId);
		map.put("CarTypeInnerId", CarType);
		List<UserCarInfo> userCarList = tingcheMapper.selectUserCarInfo(map);
		List<List<Object>> data = new ArrayList<List<Object>>();
		for (UserCarInfo userCarInfo : userCarList) {
			List<Object> rowData = new ArrayList<Object>();
			rowData.add(userCarInfo.getUserId());
			rowData.add(userCarInfo.getUserName());
			rowData.add(userCarInfo.getDepartmentName());
			rowData.add(userCarInfo.getCarNumber());
			if(userCarInfo.getStartTime() != null){
				rowData.add(userCarInfo.getStartTime().substring(0, 19));
			}else{
				rowData.add(userCarInfo.getStartTime());
			}
			if(userCarInfo.getEndTime() != null){
				rowData.add(userCarInfo.getEndTime().substring(0, 19));
			}else{
				rowData.add(userCarInfo.getEndTime());
			}
			rowData.add(userCarInfo.getCarTypeName());
			if(userCarInfo.getParkInfo() != null){
				if(userCarInfo.getParkInfo() == 1001){
					rowData.add("地面长期车");
				}else if(userCarInfo.getParkInfo() == 1007){
					rowData.add("B1地库长期车");
				}else if(userCarInfo.getParkInfo() == 1008){
					rowData.add("E2地库长期车");
				}else{
					rowData.add("");
				}
			}else{
				rowData.add("");
			}
			rowData.add(userCarInfo.getCarNumber1());
			rowData.add(userCarInfo.getCarNumber2());
			rowData.add(userCarInfo.getCarNumber3());
			rowData.add(userCarInfo.getCarNumber4());
			rowData.add(userCarInfo.getParkingNum());
			data.add(rowData);
		}
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		String relpath = request.getSession().getServletContext().getRealPath("excel");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS");
		String name = "/CarInfo" + sdf2.format(new Date()) + ".xlsx";
		OutputStream out = new FileOutputStream(relpath + name);
		ExportExcelUtils eeu = new ExportExcelUtils();
		XSSFWorkbook workbook = new XSSFWorkbook();
		List<String> headerList = new ArrayList<String>();
		headerList.add("车辆信息表");
		headerList.add("制表人：" + operator.getOperatorId() + "    制表时间：" + sdf.format(new Date()));
		List<String> headers0 = new ArrayList<String>();
		headers0.add("人员编号");
		headers0.add("人员姓名");
		headers0.add("部门");
		headers0.add("车牌号");
		headers0.add("开始时间");
		headers0.add("结束时间");
		headers0.add("车辆类型");
		headers0.add("停车场");
		headers0.add("备用车牌1");
		headers0.add("备用车牌2");
		headers0.add("备用车牌3");
		headers0.add("备用车牌4");
		headers0.add("车位数量");
		List<Integer> length0 = new ArrayList<Integer>();
		length0.add(20);
		length0.add(20);
		length0.add(20);
		length0.add(20);
		length0.add(20);
		length0.add(20);
		length0.add(20);
		length0.add(20);
		length0.add(20);
		length0.add(20);
		length0.add(20);
		length0.add(20);
		length0.add(20);
		eeu.selectUseruserExcel(workbook, 0, "车辆信息表", headerList, headers0, length0, data, out);
		workbook.write(out);
		out.close();
		JSONObject jo = new JSONObject();
		jo.put("code", 200);
		jo.put("msg", "导出成功！");
		jo.put("data", "excel/" + name);
		return jo.toString();
	}

	@Override
	public String selectYuyuecheExcel(HttpServletRequest request) throws Exception {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		String StartTime = request.getParameter("StartTime");
		String EndTime = request.getParameter("EndTime");
		String CarNumber = request.getParameter("CarNumber");
		Map<String,Object> map = new HashMap<>();
		map.put("rows", 1000000);
		map.put("order", "desc");
		map.put("sort", "RecordInnerId");
		map.put("start", 0);
		if(StartTime != null && !StartTime.equals("") && !StartTime.equals(" 00:00:00")){
			map.put("StartTime", StartTime);
		}else{
			map.put("StartTime", sdf1.format(new Date())+" 00:00:00");
		}
		if(EndTime != null && !EndTime.equals("") && !EndTime.equals(" 23:59:00")){
			map.put("EndTime", EndTime);
		}else{
			map.put("EndTime", sdf1.format(new Date())+" 23:59:00");
		}
		map.put("CarNumber", CarNumber);
		List<OrderRecord> reservationList = tingcheMapper.selectVisCarInfo(map);
		List<List<Object>> data = new ArrayList<List<Object>>();
		for (OrderRecord reservationCar : reservationList) {
			List<Object> rowData = new ArrayList<Object>();
			//rowData.add(reservationCar.getRecordInnerId());
			rowData.add(reservationCar.getVisitorName());
			rowData.add(reservationCar.getVisitorCompany());
			rowData.add(reservationCar.getVisitorCarCode());
			if (reservationCar.getVisitorBeginTime() != null) {
				rowData.add(reservationCar.getVisitorBeginTime().substring(0, 19));
			} else {
				rowData.add(reservationCar.getVisitorBeginTime());
			}
			if (reservationCar.getVisitorEndTime() != null) {
				rowData.add(reservationCar.getVisitorEndTime().substring(0, 19));
			} else {
				rowData.add(reservationCar.getVisitorEndTime());
			}
			if (reservationCar.getOrderType() == 1) {
				rowData.add("协助预约");
			} else if (reservationCar.getOrderType() == 2) {
				rowData.add("自主预约");
			}else if (reservationCar.getOrderType() == 2) {
				rowData.add("平台预约");
			}
			rowData.add(reservationCar.getUserName());
			rowData.add(reservationCar.getDepartMentName());
			rowData.add(reservationCar.getRecordTime().substring(0, 19));
			if (reservationCar.getVisitorState() == 0) {
				rowData.add("申请中");
			} else if (reservationCar.getVisitorState() == 1) {
				rowData.add("待入场");
			} else if (reservationCar.getVisitorState() == 2) {
				rowData.add("拒绝");
			} else if (reservationCar.getVisitorState() == 3) {
				rowData.add("已取消");
			} else if (reservationCar.getVisitorState() == 4) {
				rowData.add("已进场");
			} else if (reservationCar.getVisitorState() == 5) {
				rowData.add("已支付");
			} else if (reservationCar.getVisitorState() == 6) {
				rowData.add("已完成");
			} else if (reservationCar.getVisitorState() == 7) {
				rowData.add("已出场");
			} else if (reservationCar.getVisitorState() == 8) {
				rowData.add("已逾期");
			} else if (reservationCar.getVisitorState() == 9) {
				rowData.add("待支付");
			} else{
				rowData.add("");
			}
			data.add(rowData);
		}
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		String relpath = request.getSession().getServletContext().getRealPath("excel");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS");
		String name = "/CarInfo" + sdf2.format(new Date()) + ".xlsx";
		OutputStream out = new FileOutputStream(relpath + name);
		ExportExcelUtils eeu = new ExportExcelUtils();
		XSSFWorkbook workbook = new XSSFWorkbook();
		List<String> headerList = new ArrayList<String>();
		headerList.add("预约车辆信息表");
		headerList.add("制表人：" + operator.getOperatorId() + "    制表时间：" + sdf.format(new Date()));
		headerList.add("查询开始时间：" + StartTime + "    查询结束时间：" + EndTime);
		List<String> headers0 = new ArrayList<String>();
		headers0.add("预约人姓名");
		headers0.add("预约人公司");
		headers0.add("预约人车牌号");
		headers0.add("开始时间");
		headers0.add("结束时间");
		headers0.add("预约类型");
		headers0.add("协助人");
		headers0.add("协助部门");
		headers0.add("预约时间");
		headers0.add("预约状态");
		List<Integer> length0 = new ArrayList<Integer>();
		length0.add(20);
		length0.add(20);
		length0.add(20);
		length0.add(20);
		length0.add(20);
		length0.add(20);
		length0.add(20);
		length0.add(20);
		length0.add(20);
		length0.add(20);
		eeu.selectUseruserExcel(workbook, 0, "预约车辆信息表", headerList, headers0, length0, data, out);
		workbook.write(out);
		out.close();
		JSONObject jo = new JSONObject();
		jo.put("code", 200);
		jo.put("msg", "导出成功！");
		jo.put("data", "excel/" + name);
		return jo.toString();
	}

	@Override
	public String insertVisitor_OrderRecord(HttpServletRequest request) {
		String CarTypeInnerId = request.getParameter("CarTypeInnerId");
		String CarNumber = request.getParameter("CarNumber");
		String StartTime = request.getParameter("StartTime");
		String CompanyName = request.getParameter("CompanyName");
		String UserName = request.getParameter("UserName");
		String VisThing = request.getParameter("VisThing");
		String EndTime = request.getParameter("EndTime");
		String GetMoney = request.getParameter("GetMoney");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("CarTypeInnerId", CarTypeInnerId);
		map.put("CarNumber", CarNumber);
		map.put("StartTime", StartTime);
		map.put("EndTime", EndTime);
		map.put("GetMoney", GetMoney);
		map.put("CompanyName", CompanyName);
		map.put("UserName", UserName);
		map.put("VisThing", VisThing);
		
		JSONObject jo = new JSONObject();

		/*Map<String, Object> result = EditBlackOrWhiteCar(map);
		if (result.get("resCode").toString().equals("0")) {*/
			jo.put("code", "200");
			jo.put("msg", "预约成功");
			int rt = tingcheMapper.insertVisitor_OrderRecord(map);
			if (rt <= 0) {
				jo.put("code", "500");
				jo.put("msg", "预约失败");
			}

		/*} else {
			jo.put("code", "500");
			jo.put("msg", result.get("resMsg").toString());
		}
*/
		return jo.toString();
	}
	
	public Map<String,Object> EditBlackOrWhiteCar(Map<String,Object> map){
		Map<String,Object> result = new HashMap<>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Map<String,String> row = new HashMap<>();
			row.put("carCode", map.get("CarNumber").toString());
			String StartTime = map.get("StartTime").toString();
			long stime = sdf.parse(StartTime).getTime() - 120*60*1000;
			long etime = 0;
			if(map.get("GetMoney").toString().equals("1")){
				etime = sdf.parse(StartTime).getTime() + 30*60*1000;
			}else{
				String EndTime = map.get("EndTime").toString();
				etime = sdf.parse(EndTime).getTime();
			}
			Date date = new Date();
			date.setTime(stime);
			row.put("beginTime", sdf.format(date));
			date.setTime(etime);
			row.put("endTime", sdf.format(date));
			row.put("type", 9+"");
			row.put("act", 1+"");
			System.out.println(row.toString());
			JSONObject objectJSON =  JSONObject.fromObject(row);
			System.out.println(objectJSON.toString());
			String doPost = HttpClintUtil.doPostJson(lfip+"EditBlackOrWhiteCar", objectJSON.toString());
			JSONObject fromObject = JSONObject.fromObject(doPost);
			result = (Map)fromObject;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String selectTSCarRecord(HttpServletRequest request, DataGridModel dgm) {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		String StartTime = request.getParameter("StartTime");
		String EndTime = request.getParameter("EndTime");
		String CarNumber = request.getParameter("CarNumber");
		Map<String,Object> map = new HashMap<>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("CarRecordInnerId");
			dgm.setOrder("desc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(15);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		if(StartTime == null || StartTime.equals("")){
			map.put("StartTime", sdf.format(new Date())+" 00:00:00");
		}else{
			map.put("StartTime", StartTime);
		}
		if(EndTime == null || EndTime.equals("")){
			map.put("EndTime", sdf.format(new Date())+" 23:59:00");
		}else{
			map.put("EndTime", EndTime);
		}
		map.put("CarNumber", CarNumber);
		List<Map<String,Object>> rows = new ArrayList<>();
		List<CarRecord> carRecordList = tingcheMapper.selectTSCarRecord(map);
		for (CarRecord carRecord_User : carRecordList) {
			Map<String,Object> row = new HashMap<>();
			row.put("CarNumber", carRecord_User.getCarNumber());
			row.put("EntryTime", carRecord_User.getEntryTime());
			if(carRecord_User.getInOrOut() == 0){
				row.put("InOrOut", "进");
			}else{
				row.put("InOrOut", "出");
			}
			rows.add(row);
		}
		JSONObject jo = new JSONObject();
		jo.put("total", tingcheMapper.selectTSCarRecordTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	public String selectChargeRecordInfo(HttpServletRequest request,DataGridModel dgm) {
		String StartTime = request.getParameter("StartTime");
		String EndTime = request.getParameter("EndTime");
		String CarNumber = request.getParameter("CarNumber");
		String CarType = request.getParameter("CarType");
		String ChargeType = request.getParameter("ChargeType");
		String UserName = request.getParameter("UserName");
		String DepName = request.getParameter("DepName");
		Map<String,Object> map = new HashMap<>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("ChargeRecordInnerId");
			dgm.setOrder("desc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(15);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		map.put("StartTime", StartTime);
		map.put("EndTime", EndTime);
		map.put("CarNumber", CarNumber);
		map.put("CarType", CarType);
		map.put("ChargeType", ChargeType);
		map.put("UserName", UserName);
		map.put("DepName", DepName);
		List<ChargeRecord> chargeRecordList = tingcheMapper.selectChargeRecordInfo(map);
		List<Map<String,Object>> rows = new ArrayList<>();
		Integer moneyTotal = 0;
		for (ChargeRecord chargeRecord : chargeRecordList) {
			Map<String,Object> row = new HashMap<>();
			row.put("ChargeRecordInnerId", chargeRecord.getChargeRecordInnerId());
			row.put("CarNumber", chargeRecord.getCarNumber());
			if(chargeRecord.getInTime() != null){
				row.put("InTime", chargeRecord.getInTime().substring(0, 19));
			}else{
				row.put("InTime", chargeRecord.getInTime());
			}
			if(chargeRecord.getOutTime() != null){
				row.put("OutTime", chargeRecord.getOutTime().substring(0, 19));
			}else{
				row.put("OutTime", chargeRecord.getOutTime());
			}
			if(chargeRecord.getMoney() != null){
				row.put("Money", chargeRecord.getMoney()/100.0);
			}else{
				row.put("Money", 0);
			}
			if(chargeRecord.getCarType() == 0 ){
				row.put("CarType", "固定车");
				if(chargeRecord.getName() != null){
					row.put("Name", chargeRecord.getName());
					row.put("DepName", chargeRecord.getDepName());
				}else{
					UserCarInfo UserInfo = tingcheMapper.selectUserCarByCarNumber(chargeRecord.getCarNumber());
					if(UserInfo != null){
						row.put("Name", UserInfo.getUserName());
						row.put("DepName", UserInfo.getDepartmentName());
					}else{
						row.put("Name", "未知");
						row.put("DepName", "未知");
					}
				}
			}else if(chargeRecord.getCarType() == 1){
				row.put("CarType", "预约车");
				if(chargeRecord.getName() != null){
					row.put("Name", chargeRecord.getName());
					row.put("DepName", chargeRecord.getDepName());
				}else{
				OrderRecord orderRecord = tingcheMapper.selectOrderRecordByVinnerId(chargeRecord.getVisRecordInnerId());
				if(orderRecord != null){
					row.put("Name", orderRecord.getUserName());
					row.put("DepName", orderRecord.getDepartMentName());
				}else{
					row.put("Name", "未知");
					row.put("DepName", "未知");
				}}
			}else if(chargeRecord.getCarType() == 2 ){
				row.put("CarType", "特殊车辆");
				if(chargeRecord.getName() != null){
					row.put("Name", chargeRecord.getName());
					row.put("DepName", chargeRecord.getDepName());
				}else{
				OrderRecord orderRecord = tingcheMapper.selectOrderRecordByVinnerId(chargeRecord.getVisRecordInnerId());
					if(orderRecord != null){
						row.put("Name", orderRecord.getVisitorName());
						row.put("DepName", orderRecord.getVisitorCompany());
					}else{
						row.put("Name", "特殊车辆");
						row.put("DepName", "特殊车辆");
					}
				}
			}else if(chargeRecord.getCarType() == 3 ){
				row.put("CarType", "协助预约");
				if(chargeRecord.getName() != null){
					row.put("Name", chargeRecord.getName());
					row.put("DepName", chargeRecord.getDepName());
				}else{
					OrderRecord orderRecord = tingcheMapper.selectOrderRecordByVinnerId(chargeRecord.getVisRecordInnerId());
					if(orderRecord != null){
						row.put("Name", orderRecord.getVisitorName());
						row.put("DepName", orderRecord.getVisitorCompany());
					}else{
						row.put("Name", "协助预约");
						row.put("DepName", "协助预约");
					}
				}
			}
			row.put("ChargeTime", chargeRecord.getChargeTime());
			if(chargeRecord.getChargeType() != null){
				if(chargeRecord.getChargeType() == 1){
					row.put("ChargeType", "人工收费");
				} else if(chargeRecord.getChargeType() == 2){
					row.put("ChargeType", "自主缴费");
				}else if(chargeRecord.getChargeType() == 3){
					row.put("ChargeType", "自动扣除");
				}else if(chargeRecord.getChargeType() == 0){
					row.put("ChargeType", "逾期未支付");
				}else{
					row.put("ChargeType", "未知");
				}
			}
			rows.add(row);
		}
		Integer total = tingcheMapper.selectChargeRecordInfoTotal(map);
		if (total > 0 && dgm.getRows() * dgm.getPage() >= total) {
			map.put("rows", 100000000);
			map.put("start", 0);
			List<ChargeRecord> chargeRecordList1 = tingcheMapper.selectChargeRecordInfo(map);
			Long PersonMoneySum = (long) 0;
			Long Count = (long) 0;
			for (ChargeRecord conLog : chargeRecordList1) {
				Count++;
				if(conLog.getMoney() != null){
					PersonMoneySum += conLog.getMoney();
				}
			}
			// 最后一行合计
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("Name", "合计");
			row.put("DepName", Count + "人");
			row.put("Money", PersonMoneySum / 100.0);
			rows.add(row);
		}
		JSONObject jo = new JSONObject();
		jo.put("rows", rows);
		jo.put("total", total);
		return jo.toString();
	}

	@Override
	public String updateCarInfoTime(HttpServletRequest request) {
		String CarInfoInnerId = request.getParameter("CarInfoInnerId");
		String StartTime = request.getParameter("StartTime");
		String EndTime = request.getParameter("EndTime");
		String[] split = CarInfoInnerId.split(",");
		StringBuilder sb = new StringBuilder();
		for (String string : split) {
			CarInfo carInfo = tingcheMapper.selectCarInfoByCarInfoInnerId(Integer.valueOf(string));
			carInfo.setStartTime(StartTime);
			carInfo.setEndTime(EndTime);
			tingcheMapper.updateCarInfo(carInfo);
			if(tbFlag){
				Map<String,String> map = new HashMap<>();
				carInfo = tingcheMapper.selectCarInfoByCarInfoInnerId(carInfo.getCarInfoInnerId());
				map.put("CarCode", carInfo.getCarNumber());
				map.put("StartTime", carInfo.getStartTime());
				map.put("EndTime", carInfo.getEndTime());
				map.put("RecordID", carInfo.getLFInnerId());
				String json = JSON.toJSONString(map);
				String doPost = HttpClintUtil.doPostJson(ip+"updateCarInfo", json);
				JSONObject fromObject = JSONObject.fromObject(doPost);
				Map<String, Object> datas = (Map) fromObject;
				if(datas.get("status").toString().equals("200")){
				}else{
					sb.append(carInfo.getCarNumber()+"、");
				}
			}
		}
		JSONObject jo = new JSONObject();
		if(sb.toString().length() > 0){
			jo.put("code", 500);
			jo.put("msg", sb.toString()+"同步失败");
		}else{
			jo.put("code", 200);
			jo.put("msg", "修改成功");
		}
		return jo.toString();
	}

	@Override
	public String selectTcConStatistics(HttpServletRequest request) {
		String UserName = request.getParameter("UserName");
		String UserId = request.getParameter("UserId");
		String DepartmentInnerId = request.getParameter("DepartmentInnerId");
		String StartTime = request.getParameter("StartTime");
		String EndTime = request.getParameter("EndTime");
		String RowName = request.getParameter("RowName");
		Map<String,Object> map = new HashMap<>();
		map.put("UserName", UserName);
		map.put("UserId", UserId);
		map.put("DepartmentInnerId", DepartmentInnerId);
		map.put("StartTime", StartTime);
		map.put("EndTime", EndTime);
		map.put("RowName", RowName);
		List<Map<String,Object>> rows = new ArrayList<>();
		JSONObject jo = new JSONObject();
		if(RowName == null || RowName.equals("")){
			RowName = "ConCompanyInnerId";
		}
		if(RowName.equals("ConCompanyInnerId")){
			List<ConStatistics> conList = tingcheMapper.selectTcConStatistics(map);
			for (ConStatistics conStatistics : conList) {
				Map<String,Object> row = new HashMap<>();
				row.put("PersonMoney", conStatistics.getPersonMoney()/100.0);
				Department dep = renshiyewuMapper.selectAllDepartmentByInnerId(Integer.valueOf(conStatistics.getRowName()));
				row.put("RowName", dep.getDepartmentName());
				rows.add(row);
			}
			jo.put("rows", rows);
		}else{
			List<ConStatistics> conList = tingcheMapper.selectVisConStatistics(map);
			for (ConStatistics conStatistics : conList) {
				Map<String,Object> row = new HashMap<>();
				row.put("PersonMoney", conStatistics.getPersonMoney()/100.0);
				if(conStatistics.getRowName() == null){
					continue;
				}
				Department dep = renshiyewuMapper.selectDepartmentByInnerId(Integer.valueOf(conStatistics.getRowName()));
				row.put("RowName", dep.getDepartmentName());
				rows.add(row);
			}
			jo.put("rows", rows);
		}
		return jo.toString();
	}
	
	public List<String[]> readXls(InputStream file) throws IOException {
		// List<String[]> 的元素 行数组String[]为excel中的每一行
		List<String[]> list = new ArrayList<String[]>();
		// 将is流实例到 一个excel流里
		XSSFWorkbook hwk = new XSSFWorkbook(file);
		file.close();
		// 得到book第一个工作薄sheet
		XSSFSheet sh = hwk.getSheetAt(0);
		// 总行数
		int rows = sh.getLastRowNum() + 1 - sh.getFirstRowNum();
		// 从头标题下一行开始
		UpLoadExcel uploadExcel = new UpLoadExcel();
		for (int i = 1; i < rows; i++) {
			XSSFRow row = sh.getRow(i);
			int cols = row.getLastCellNum() + 1 - row.getFirstCellNum(); // 该行的总列数
			String[] str = new String[cols]; // 用来存放该行每一列的值
			for (int j = 0; j < cols; j++) {
				Cell col = row.getCell((short) j);
				String colum = uploadExcel.getValue(col);// 对列值进行处理
				// String colum = UpLoadExcel.getStringCellValue(col);
				Cell colNext = row.getCell((short) (j + 1));
				uploadExcel.getValue(colNext);
				// UpLoadExcel.getStringCellValue(colNext);
				if (col != null) { // 该列不为空，直接读到 行数组里
					str[j] = colum.toString();
				} else { // 该列为空
					// 该列的后面一列不为空，用空字符串占位
					if (colNext != null) {
						Object colValue = "";
						str[j] = colValue.toString();
					}
				}
			}
			list.add(str);
		}
		return list;
	}

	@Override
	@Transactional
	public String showCarExcel(HttpServletRequest request) throws IOException {
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		JSONObject json = new JSONObject();
		String result = "";
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile("fileExcel");
		InputStream fs = multipartFile.getInputStream();
		List<String[]> AllowanceList = readXls(fs);

		Map<String, Object> map = new HashMap<String, Object>();
		for (String[] allowance : AllowanceList) {
			Map<String, Object> row = new HashMap<String, Object>();
			// Validate 0:正常 1：非法 2：警告
			row.put("Validate", 0);
			//人员姓名
			if (allowance.length < 1) {
				row.put("UserId", "(人员编号不能为空)");
				row.put("Validate", 1);
			} else if (allowance[0] == null || allowance[0].equals("") || allowance[0].equals(" ")) {
				row.put("UserId", allowance[0] + "(人员编号不能为空)");
				row.put("Validate", 1);
			}else {
				row.put("UserId", allowance[0]);
			}
			//	 车牌号
			if (allowance.length < 2) {
				row.put("UserName", "(车牌号不能为空)");
				row.put("Validate", 1);
			} else if (allowance[1] == null || allowance[1].equals("") || allowance[1].equals(" ")) {
				row.put("UserName", allowance[1] + "(车牌号不能为空)");
				row.put("Validate", 1);
			} else {
				UserCarInfo usercarInfo = tingcheMapper.selectUserCarInfoByCarNumber(allowance[1]);
				if(usercarInfo != null ){
					row.put("UserName", allowance[1]+"(车牌号已存在)");
					row.put("Validate", 1);
				}else{
					row.put("UserName", allowance[1]);
				}
				
			}

			// 车辆类型
			if (allowance.length < 3) {
				row.put("CarTypeName", "(车辆类型编号不能为空)");
				row.put("Validate", 1);
			} else if (allowance[2] == null || allowance[2].equals("") || allowance[2].equals(" ")) {
				row.put("CarTypeName", allowance[2] + "(车辆类型编号不能为空)");
				row.put("Validate", 1);
			} else {
				map.put("rows", 1);
				map.put("order", "asc");
				map.put("sort", "CarTypeInnerId");
				map.put("start", 0);
				map.put("CarTypeId", allowance[2]);
				List<CarType> carTypeList = tingcheMapper.selectCarType(map);
				if(carTypeList.size() > 0){
					row.put("CarTypeName", allowance[2]);
				}else{
					row.put("CarTypeName", allowance[2] + "(车辆类型编号不存在)");
					row.put("Validate", 1);
				}
			}

			// 开始时间
			if (allowance.length < 4) {
				row.put("StartTime", "(开始时间不能为空)");
				row.put("Validate", 1);
			} else if (allowance[3] == null || allowance[3].equals("") || allowance[3].equals(" ")) {
				row.put("StartTime", allowance[3] + "(开始时间不能为空)");
				row.put("Validate", 1);
			} else {
				row.put("StartTime", allowance[3]);
			}
			// 结束时间
			if (allowance.length < 5) {
				row.put("EndTime", "(开始时间不能为空)");
				row.put("Validate", 1);
			} else if (allowance[4] == null || allowance[4].equals("") || allowance[4].equals(" ")) {
				row.put("EndTime", allowance[4] + "(开始时间不能为空)");
				row.put("Validate", 1);
			} else {
				row.put("EndTime", allowance[4]);
			}
			rows.add(row);
		}
		json.put("rows", rows);
		result = json.toString();
		return result;
	}

	@Override
	public String selectChargeRecordInfoExcel(HttpServletRequest request) throws Exception {
		String StartTime = request.getParameter("StartTime");
		String EndTime = request.getParameter("EndTime");
		String CarNumber = request.getParameter("CarNumber");
		String CarType = request.getParameter("CarType");
		String ChargeType = request.getParameter("ChargeType");
		String UserName = request.getParameter("UserName");
		String DepName = request.getParameter("DepName");
		Map<String,Object> map = new HashMap<>();
		map.put("rows", 100000);
		map.put("order", "desc");
		map.put("sort", "ChargeRecordInnerId");
		map.put("start", 0);
		map.put("StartTime", StartTime);
		map.put("EndTime", EndTime);
		map.put("CarNumber", CarNumber);
		map.put("CarType", CarType);
		map.put("ChargeType", ChargeType);
		map.put("UserName", UserName);
		map.put("DepName", DepName);
		List<ChargeRecord> chargeRecordList = tingcheMapper.selectChargeRecordInfo(map);
		Integer moneyTotal = 0;
		Long PersonMoneySum = (long) 0;
		Long Count = (long) 0;

		List<List<Object>> data = new ArrayList<List<Object>>();
		for (ChargeRecord chargeRecord : chargeRecordList) {
			List<Object> rowData = new ArrayList<Object>();
			Map<String,Object> row = new HashMap<>();
			if(chargeRecord.getCarType() == 0 ){
				if(chargeRecord.getName() != null){
					rowData.add(chargeRecord.getName());
					rowData.add(chargeRecord.getDepName());
				}else{
					UserCarInfo UserInfo = tingcheMapper.selectUserCarByCarNumber(chargeRecord.getCarNumber());
					if(UserInfo != null){
						rowData.add(UserInfo.getUserName());
						rowData.add(UserInfo.getDepartmentName());
					}else{
						rowData.add("未知");
						rowData.add("未知");
					}
				}
				rowData.add("固定车");
			}else if(chargeRecord.getCarType() == 1){
				if(chargeRecord.getName() != null){
					rowData.add(chargeRecord.getName());
					rowData.add(chargeRecord.getDepName());
				}else{
					OrderRecord orderRecord = tingcheMapper.selectOrderRecordByVinnerId(chargeRecord.getVisRecordInnerId());
					if(orderRecord != null){
						rowData.add(orderRecord.getUserName());
						rowData.add(orderRecord.getDepartMentName());
					}else{
						rowData.add("未知");
						rowData.add("未知");
					}
				}
				rowData.add("预约车");
			}else if(chargeRecord.getCarType() == 2 ){
				if(chargeRecord.getName() != null){
					rowData.add(chargeRecord.getName());
					rowData.add(chargeRecord.getDepName());
				}else{
				OrderRecord orderRecord = tingcheMapper.selectOrderRecordByVinnerId(chargeRecord.getVisRecordInnerId());
					if(orderRecord != null){
						rowData.add(orderRecord.getVisitorName());
						rowData.add(orderRecord.getVisitorCompany());
					}else{
						rowData.add("特殊车辆");
						rowData.add("特殊车辆");
					}
				}
				rowData.add("特殊车辆");
			}else if(chargeRecord.getCarType() == 3 ){
				if(chargeRecord.getName() != null){
					rowData.add(chargeRecord.getName());
					rowData.add(chargeRecord.getDepName());
				}else{
					OrderRecord orderRecord = tingcheMapper.selectOrderRecordByVinnerId(chargeRecord.getVisRecordInnerId());
					if(orderRecord != null){
						rowData.add(orderRecord.getVisitorName());
						rowData.add(orderRecord.getVisitorCompany());
					}else{
						rowData.add("协助预约");
						rowData.add("协助预约");
					}
				}
				rowData.add("协助预约");
			}
			rowData.add(chargeRecord.getCarNumber());
			if(chargeRecord.getInTime() != null){
				rowData.add(chargeRecord.getInTime().substring(0, 19));
			}else{
				rowData.add(chargeRecord.getInTime());
			}
			if(chargeRecord.getOutTime() != null){
				rowData.add(chargeRecord.getOutTime().substring(0, 19));
			}else{
				rowData.add(chargeRecord.getOutTime());
			}
			if(chargeRecord.getMoney() != null){
				rowData.add(chargeRecord.getMoney()/100.0);
			}else{
				rowData.add(0);
			}
			
			rowData.add(chargeRecord.getChargeTime());
			if(chargeRecord.getChargeType() != null){
				if(chargeRecord.getChargeType() == 1){
					rowData.add("人工收费");
				} else if(chargeRecord.getChargeType() == 2){
					rowData.add("自主缴费");
				}else if(chargeRecord.getChargeType() == 3){
					rowData.add("自动扣除");
				}else if(chargeRecord.getChargeType() == 0){
					rowData.add("逾期未支付");
				}else{
					rowData.add("未知");
				}
			}
			if(chargeRecord.getMoney() != null){
				PersonMoneySum += chargeRecord.getMoney();
			}
			Count++;
			data.add(rowData);
		}
		
		// 最后一行合计
		List<Object> rowData = new ArrayList<>();
		rowData.add("合计");
		rowData.add(Count+"人");
		rowData.add(" ");
		rowData.add(" ");
		rowData.add(" ");
		rowData.add(" ");
		rowData.add(PersonMoneySum / 100.0);
		rowData.add(" ");
		rowData.add(" ");
		data.add(rowData);
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		String relpath = request.getSession().getServletContext().getRealPath("excel");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS");
		String name = "/ChargeRecordInfo" + sdf2.format(new Date()) + ".xlsx";
		OutputStream out = new FileOutputStream(relpath + name);
		ExportExcelUtils eeu = new ExportExcelUtils();
		XSSFWorkbook workbook = new XSSFWorkbook();
		List<String> headerList = new ArrayList<String>();
		headerList.add("车辆收费记录表");
		headerList.add("制表人：" + operator.getOperatorId() + "    制表时间：" + sdf.format(new Date()));
		headerList.add("查询开始时间：" + StartTime + "    查询结束时间：" + EndTime);
		List<String> headers0 = new ArrayList<String>();
		headers0.add("姓名");
		headers0.add("部门");
		headers0.add("车辆类型");
		headers0.add("车牌号");
		headers0.add("进入时间");
		headers0.add("结束时间");
		headers0.add("收费金额");
		headers0.add("收费时间");
		headers0.add("收费方式");
		List<Integer> length0 = new ArrayList<Integer>();
		length0.add(20);
		length0.add(20);
		length0.add(20);
		length0.add(20);
		length0.add(20);
		length0.add(20);
		length0.add(20);
		length0.add(20);
		length0.add(20);
		eeu.selectUseruserExcel(workbook, 0, "车辆收费记录表", headerList, headers0, length0, data, out);
		workbook.write(out);
		out.close();
		JSONObject jo = new JSONObject();
		jo.put("code", 200);
		jo.put("msg", "导出成功！");
		jo.put("data", "excel/" + name);
		return jo.toString();
	}

	@Override
	public String updateVisState(HttpServletRequest request) {
		String RecordInnerId = request.getParameter("RecordInnerId");
		String VisState = request.getParameter("VisState");
		Map<String,Object> map = new HashMap<>();
		map.put("RecordInnerId", RecordInnerId);
		map.put("VisState", VisState);
		Integer rt = tingcheMapper.updateVisRecordState(map);
		JSONObject jo = new JSONObject();
		if(rt > 0){
			jo.put("code", 200);
			jo.put("msg", "修改成功");
		}else{
			jo.put("code", 500);
			jo.put("msg", "修改失败");
		}
		return jo.toString();
	}

	@Override
	public String selectParkNumberDate(HttpServletRequest request, DataGridModel dgm) {
		String DateTime = request.getParameter("DateTime");
		Map<String,Object> map = new HashMap<>();
		if(DateTime != null && !DateTime.equals("")){
			map.put("DateTime", DateTime.substring(0, 10)+" 00:00:00");
			map.put("rows", 1);
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String format = sdf.format(new Date());
			map.put("DateTime", format+" 00:00:00");
			map.put("rows", 30);
		}
		List<ParkDate> dates = tingcheMapper.selectParkDateInfo(map); 
		List<Map<String,Object>> rows = new ArrayList<>();
		for (ParkDate parkDate : dates) {
			Map<String,Object> row = new HashMap<>();
			row.put("ParkDate", parkDate.getParkDate().substring(0, 10));
			row.put("ParkNum", parkDate.getParkNum());
			row.put("ParkResidue", parkDate.getParkResidue());
			if(parkDate.getParkType() == 1){
				row.put("ParkTypeName", "访客车位");
			}else{
				row.put("ParkTypeName", "员工车位");
			}
			row.put("ParkType", parkDate.getParkType());
			rows.add(row);
		}
		JSONObject jo = new JSONObject();
		jo.put("rows",rows);
		return jo.toString();
	}

	@Override
	public String updateParkDateNumber(HttpServletRequest request) {
		String ParkNum = request.getParameter("ParkNum");
		String ParkResidue = request.getParameter("ParkResidue");
		String ParkType = request.getParameter("ParkType");
		String ParkDate = request.getParameter("ParkDate");
		ParkDate parkDate = new ParkDate();
		parkDate.setParkDate(ParkDate);
		parkDate.setParkNum(Integer.valueOf(ParkNum));
		parkDate.setParkType(Integer.valueOf(ParkType));
		parkDate.setParkResidue(Integer.valueOf(ParkResidue));
		Integer rt = tingcheMapper.updateParkDate(parkDate);
		JSONObject jo = new JSONObject();
		if(rt > 0){
			jo.put("code", 200);
			jo.put("msg", "修改成功");
		}else{
			jo.put("code", 500);
			jo.put("msg", "修改失败");
		}
		return jo.toString();
	}
}
