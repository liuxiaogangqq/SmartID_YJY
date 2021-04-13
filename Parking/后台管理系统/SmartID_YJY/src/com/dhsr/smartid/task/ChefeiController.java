package com.dhsr.smartid.task;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.dhsr.smartid.base.web.BaseController;
import com.dhsr.smartid.renshiyewu.dao.RenshiyewuMapper;
import com.dhsr.smartid.renshiyewu.domain.Department;
import com.dhsr.smartid.renshiyewu.domain.User;
import com.dhsr.smartid.tingchexitong.dao.TingcheMapper;
import com.dhsr.smartid.tingchexitong.domain.CarRecord;
import com.dhsr.smartid.tingchexitong.domain.ChargeRecord;
import com.dhsr.smartid.tingchexitong.domain.Holiday;
import com.dhsr.smartid.tingchexitong.domain.OrderRecord;
import com.dhsr.smartid.tingchexitong.domain.ParkDate;
import com.dhsr.smartid.tingchexitong.domain.ParkInfo;
import com.dhsr.smartid.tingchexitong.domain.PassRules;
import com.dhsr.smartid.tingchexitong.domain.UserCarInfo;
import com.dhsr.smartid.tingchexitong.web.TingcheController;
import com.dhsr.smartid.util.HttpClintUtil;
import com.dhsr.smartid.util.LogInfo;
import com.dhsr.smartid.xiaofeiguanli.dao.XiaofeiguanliMapper;
import com.dhsr.smartid.xiaofeiguanli.domain.ConLog;
import com.dhsr.smartid.zhanghuyewu.dao.ZhanghuyewuMapper;
import com.dhsr.smartid.zhanghuyewu.domain.Account;

import net.sf.json.JSONObject;

//@Component
public class ChefeiController extends BaseController {

	@Resource
	private TingcheMapper tingcheMapper;
	@Resource
	private RenshiyewuMapper renshiyewuMapper;
	@Resource
	private XiaofeiguanliMapper xiaofeiguanliMapper;
	@Resource
	private ZhanghuyewuMapper zhanghuyewuMapper;
	private boolean ifEnd;
	private static String lfip = "http://172.16.61.55:9988/Parking/Handheld/";
	
	@Scheduled(cron = "*/15 * * * * ?")
	public void AnalysisRecord() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//查询未同步的通行记录
		Map<String,Object> map = new HashMap<>();
		map.put("rows", 1000);
		map.put("order", "desc");
		map.put("sort", "CarRecordInnerId");
		map.put("start", 0);
		map.put("Flag", 0);
		List<CarRecord> carRecordList = tingcheMapper.selectCarRecord(map);
		for (CarRecord carRecord : carRecordList) {
			try {
				Map<String,Object> row = new HashMap<>();
				row.put("DateTime", sdf.format(new Date()));
				row.put("CarNumber", carRecord.getCarNumber());
				if(carRecord.getInOrOut() == 0){
					//进场  首先判断是否有未完成的收费记录
					row.put("VisInnerId", carRecord.getStandbyB());
					ChargeRecord chargeRecord = tingcheMapper.selectWWCChargeRecord(row);
					if(chargeRecord != null){
						if(chargeRecord.getInTime() == null){
							long outtime = sdf.parse(chargeRecord.getOutTime()).getTime(); 
							long entrytime = sdf.parse(carRecord.getEntryTime()).getTime();
							if(entrytime < outtime){
								chargeRecord.setInTime(carRecord.getEntryTime());
								tingcheMapper.updateChargeRecord(chargeRecord);
							}
						}else{
							long intime = sdf.parse(chargeRecord.getInTime()).getTime();
							long entrytime = sdf.parse(carRecord.getEntryTime()).getTime();
							if(entrytime < intime){
								chargeRecord.setInTime(carRecord.getEntryTime());
								tingcheMapper.updateChargeRecord(chargeRecord);
							}
						}
					}else{
						createInChargeRecord(sdf,carRecord,row);
					}
				}else{
					//出场  首先查询车辆收费记录
					ChargeRecord chargeRecord = tingcheMapper.selectWWCChargeRecord(row);
					if(chargeRecord != null){
						if(chargeRecord.getOutTime() != null){
							long outtime = sdf.parse(chargeRecord.getOutTime()).getTime();
							long entrytime = sdf.parse(carRecord.getEntryTime()).getTime();
							if(entrytime > outtime){
								chargeRecord.setOutTime(carRecord.getEntryTime());
								if(ifEnd(chargeRecord)){
									if(chargeRecord.getCarType() == 3){
										OrderRecord visInfo = tingcheMapper.selectOrderRecordByVinnerId(chargeRecord.getVisRecordInnerId());
										long time = sdf.parse(visInfo.getVisitorEndTime()).getTime();
										long currentTimeMillis = System.currentTimeMillis();
										if(time < currentTimeMillis){
											chargeRecord.setChargeState(2);
										}
									}else{
										chargeRecord.setChargeState(2);
									}
								}
								tingcheMapper.updateChargeRecord(chargeRecord);
							}
						}else{
							chargeRecord.setOutTime(carRecord.getEntryTime());
							if(ifEnd(chargeRecord)){
								if(chargeRecord.getCarType() == 3){
									LogInfo.logInfo("错误+====="+chargeRecord.getVisRecordInnerId());
									OrderRecord visInfo = tingcheMapper.selectOrderRecordByVinnerId(chargeRecord.getVisRecordInnerId());
									long time = sdf.parse(visInfo.getVisitorEndTime()).getTime();
									long currentTimeMillis = System.currentTimeMillis();
									if(time < currentTimeMillis){
										chargeRecord.setChargeState(2);
									}
								}else if(chargeRecord.getCarType() == 1){
									if(chargeRecord.getChargeState() != null){
										if(chargeRecord.getChargeState() == 3){
											chargeRecord.setChargeState(3);
										}
									}else{
										chargeRecord.setChargeState(2);
									}
									
								}else{
									chargeRecord.setChargeState(2);
								}
							}
							tingcheMapper.updateChargeRecord(chargeRecord);
						}
					}else{
						//未找到进场记录
						createOutChargeRecord(sdf,carRecord,row);
					}
					
				}
				tingcheMapper.updateCarRecord(carRecord);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Scheduled(cron = "5 */1 * * * ?")
	public void fenxiChargeRecord() throws Exception{
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<ChargeRecord> chargeRecordList = tingcheMapper.selectChargeRecordState();
			for (ChargeRecord chargeRecord : chargeRecordList) {
				if(chargeRecord.getCarNumber().equals("冀FS526X")){
					System.out.println(11);
				}
				if(ifEnd(chargeRecord)){
					// 查询最后一次通行记录 
					CarRecord carRecord = tingcheMapper.selectCarRecordByCarNumber(chargeRecord.getCarNumber());
					if(carRecord.getInOrOut() == 1){
						// 最后一次进入是离开
						if(chargeRecord.getCarType() == 3){
							// 车辆类型是协助预约
							OrderRecord visInfo = tingcheMapper.selectOrderRecordByVinnerId(chargeRecord.getVisRecordInnerId());
							long time = sdf.parse(visInfo.getVisitorEndTime()).getTime();
							long currentTimeMillis = System.currentTimeMillis();
							if(time <= currentTimeMillis){
								chargeRecord.setChargeState(2);
								chargeRecord.setOutTime(visInfo.getVisitorEndTime());
								
								tingcheMapper.updateChargeRecord(chargeRecord);
							}
						}else{
							//自主预约
							if(chargeRecord.getOutTime() == null){
								chargeRecord.setOutTime(carRecord.getEntryTime());
							}
							chargeRecord.setChargeState(2);
							tingcheMapper.updateChargeRecord(chargeRecord);
						}
					}else{
						// 最后一次通行记录是进入
						long entryTime = sdf.parse(chargeRecord.getInTime()).getTime();
						long nowTime = new Date().getTime();
						if(nowTime - entryTime > 7*24*60*60*1000){
							chargeRecord.setChargeState(1);
							tingcheMapper.updateChargeRecord(chargeRecord);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private boolean ifEnd(ChargeRecord chargeRecord){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		boolean flag = false;
		try {
			OrderRecord visInfo = new OrderRecord();
			// 判断有没有预约
			if(chargeRecord.getVisRecordInnerId() != null){
				visInfo = tingcheMapper.selectOrderRecordByVinnerId(chargeRecord.getVisRecordInnerId());
				if(visInfo.getVisitorEndTime() == null){
					flag = true;
				}else{
					long visendtime = sdf.parse(visInfo.getVisitorEndTime()).getTime();
					if(chargeRecord.getOutTime() != null){
						if(visInfo.getOrderType() == 2){
							// 自主预约
							flag = true;
						}else{
							long chargeouttime = sdf.parse(chargeRecord.getOutTime()).getTime();
							if(chargeouttime>visendtime){
								flag = true;
							}else{
								if(new Date().getTime() > visendtime){
									flag = true;
								}else {
									flag = false;
								}
							}
						}
					}else{
						flag = true;
					}
				}
			}else{
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag ;
	}
	
	private void createInChargeRecord(SimpleDateFormat sdf,CarRecord carRecord,Map<String,Object> row){
		ChargeRecord chargeRecord = new ChargeRecord();
		chargeRecord.setRecDateTime(sdf.format(new Date()).substring(0, 10));
		chargeRecord.setCarNumber(carRecord.getCarNumber());
		chargeRecord.setInTime(carRecord.getEntryTime());
		row.put("time", sdf.format(new Date()));
		//row.put("VisitorState", "4");
		int t1 = tingcheMapper.selectReservationCarTotal(row); 
		int t2 = tingcheMapper.selectUserCarInfoTotal(row); 
		if (t1 > 0 || carRecord.getStandbyB() != null) {
			OrderRecord orderRecord = new OrderRecord();
			if(carRecord.getStandbyB() != null){
				chargeRecord.setVisRecordInnerId(Integer.valueOf(carRecord.getStandbyB()));
				orderRecord = tingcheMapper.selectOrderRecordByVinnerId(chargeRecord.getVisRecordInnerId());
			}
			if(carRecord.getType() == 1){
				// 协助预约
				chargeRecord.setCarType(3);
				// 查询人员信息
				chargeRecord.setName(orderRecord.getUserName());
				chargeRecord.setDepName(orderRecord.getDepartMentName());
			}else{
				chargeRecord.setName(orderRecord.getVisitorName());
				chargeRecord.setDepName(orderRecord.getVisitorCompany());
				// 自主预约  + 平台预约
				chargeRecord.setCarType(1);
				Map<String, String> row1 = new HashMap<>();
				row1.put("carCode", carRecord.getCarNumber());
				String StartTime = carRecord.getEntryTime();
				
				row1.put("beginTime", StartTime.substring(0, 19));
				// 查询是否是公务车
				Integer gwTotal = tingcheMapper.selectGWTotal(carRecord.getCarNumber());
				if(gwTotal > 0){
					// 是公务车 授权延后两小时 
					try {
						long time = sdf.parse(StartTime).getTime() + 7200000;
						Date date = new Date();
						date.setTime(time);
						row1.put("endTime", sdf.format(date));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					row1.put("endTime", StartTime.substring(0, 19));
				}
				row1.put("type", 9 + "");
				row1.put("act", 1 + "");
				JSONObject objectJSON = JSONObject.fromObject(row1);
				// 去掉立方授权  
				String doPost = HttpClintUtil.doPostJson(lfip + "EditBlackOrWhiteCar", objectJSON.toString());
				System.out.println(doPost);
			}
		} else if (t2 > 0) {
			chargeRecord.setCarType(0);
			// 查询固定车辆使用人
			UserCarInfo UserInfo = tingcheMapper.selectUserCarByCarNumber(chargeRecord.getCarNumber());
			if(UserInfo != null){
				chargeRecord.setName(UserInfo.getUserName());
				chargeRecord.setDepName(UserInfo.getDepartmentName());
			}else{
				chargeRecord.setName("未知");
				chargeRecord.setDepName("未知");
			}
		} else {
			chargeRecord.setName("临时车辆");
			chargeRecord.setDepName("临时车辆");
			chargeRecord.setCarType(2);
		}
		tingcheMapper.insertChargeRecord(chargeRecord);
	}
	
	private void createOutChargeRecord(SimpleDateFormat sdf,CarRecord carRecord,Map<String,Object> row){
		ChargeRecord chargeRecord = new ChargeRecord();
		chargeRecord.setRecDateTime(sdf.format(new Date()).substring(0, 10));
		chargeRecord.setCarNumber(carRecord.getCarNumber());
		chargeRecord.setOutTime(carRecord.getEntryTime());
		chargeRecord.setChargeState(1);
		row.put("time", sdf.format(new Date()));
		//row.put("VisitorState", "0,1,4,5,7");  车辆实际已经出场，
		int t1 = tingcheMapper.selectReservationCarTotal(row); 
		int t2 = tingcheMapper.selectUserCarInfoTotal(row); 
		if(t1 > 0){
			// 预约车
			chargeRecord.setCarType(1);
			//查询最后一笔缴费记录，若为个人预缴费，查询缴费时间到当前时间是否有进入记录，若没有更新离开时间
			ChargeRecord chargeRecordInfo = tingcheMapper.selectChargeRecordMax(carRecord.getCarNumber());
			if(chargeRecordInfo != null){
				if(chargeRecordInfo.getChargeType() != null){
					if(chargeRecordInfo.getChargeType() == 2){
						Map<String,Object> map = new HashMap<>();
						map.put("StartTime", chargeRecordInfo.getOutTime());
						map.put("EndTime", carRecord.getEntryTime());
						map.put("CarNumber", carRecord.getCarNumber());
						List<CarRecord> carRecordList = tingcheMapper.selectCarRecordByCarNumberAndTime(map);
						if(carRecordList.size() >0){
							tingcheMapper.insertChargeRecord(chargeRecord);
						}else{
							chargeRecordInfo.setOutTime(carRecord.getEntryTime());
							Map<String,Object> row2 = new HashMap<>();
							row2.put("CarNumber", chargeRecord.getCarNumber());
							String json = JSON.toJSONString(row2);
							String doPost = HttpClintUtil.doPostJson("http://127.0.0.1:8077/LockService/v1/LockService/getChargeRecord", json);
							JSONObject fromObject = JSONObject.fromObject(doPost);
							Map<String, Object> datas = (Map) fromObject;
							if(datas.get("status").toString().equals("200")){
								List<Map<String,Object>> carRecordList1 = (List<Map<String, Object>>) datas.get("result");
								Map<String, Object> map2 = carRecordList1.get(0);
								chargeRecordInfo.setMoney(Integer.valueOf(map2.get("money").toString())*100+chargeRecordInfo.getMoney());
								chargeRecordInfo.setStandbyB(chargeRecordInfo.getMoney()+"+"+Integer.valueOf(map2.get("money").toString())*100);
							}
							
							tingcheMapper.updateChargeRecord(chargeRecordInfo);
							if(chargeRecordInfo.getVisRecordInnerId() !=  null){
								map.put("ParkDate", sdf.format(new Date()).substring(0, 10) +" 00:00:00");
								map.put("ParkType", 1);
								ParkDate parkDate = tingcheMapper.selectParkDate(map);
								LogInfo.logInfo("车位原数量："+parkDate.toString());
								System.out.println("############"+parkDate.getParkResidue());
								parkDate.setParkResidue(parkDate.getParkResidue()+1);
								tingcheMapper.updateParkDate(parkDate);
								LogInfo.logInfo("车位加1"+parkDate.toString());
								System.out.println("**************"+parkDate.getParkResidue());
							}
						}
					}
				}else{
					
				}
			}
		}else if(t2 > 0){
			// 固定车
			chargeRecord.setCarType(0);
			tingcheMapper.insertChargeRecord(chargeRecord);
		}else{
			chargeRecord.setCarType(2);
			tingcheMapper.insertChargeRecord(chargeRecord);
		}
	}
	
	/**
	 * 查询待收费车辆信息,进行收费
	 * @throws Exception 
	 */
	@Scheduled(cron = "*/30 * * * * ?")
	private void chargeRecord() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<ChargeRecord> chargeRecordList = tingcheMapper.selectChargeRecordWaitPayMoney();
		Map<String,Object> map = new HashMap<>();
		List<Integer> companyInnerIdList = tingcheMapper.selectPayCompany();
		for (ChargeRecord chargeRecord : chargeRecordList) {
			if(chargeRecord.getChargeState() != null){
				if(chargeRecord.getChargeState() == 4 && chargeRecord.getCarType() == 3){
					// 协助预约  逾期收费
 					int money = shoufeiyuqi(chargeRecord);
					OrderRecord visCarInfo = tingcheMapper.selectOrderRecordByVinnerId(chargeRecord.getVisRecordInnerId());
					User user = renshiyewuMapper.selectUserByInnerId(visCarInfo.getUserInnerId());
					List<User> userList = null;
					if(companyInnerIdList.contains(user.getCompanyInnerId())){
						map.put("CompanyInnerId", user.getCompanyInnerId());
						map.put("UserTypeInnerId", "-1");
						map.put("rows", 1);
						userList = renshiyewuMapper.selectUserNew(map);
					}else{
						int searchDep = searchDep(user.getDepartmentInnerId());
						map.put("DepartmentInnerId", searchDep);
						map.put("UserTypeInnerId", "-1");
						map.put("rows", 1);
						userList = renshiyewuMapper.selectUserNew(map);
					}
					int rt = koukuan(userList.get(0),money*100);
					if(rt > 0){
						chargeRecord.setMoney(money*100);
						chargeRecord.setChargeState(3);
						chargeRecord.setChargeTime(sdf.format(new Date()));
						chargeRecord.setChargeType(3);
						chargeRecord.setOrderNo(new Date().getTime()+chargeRecord.getCarNumber());
						tingcheMapper.updateChargeRecord(chargeRecord);
					}
					Map<String,Object> rows = new HashMap<>();
					rows.put("VisitorState", 6);
					rows.put("RecordInnerId", visCarInfo.getRecordInnerId());
					tingcheMapper.updateChargeRecordState(rows);
				}else if(chargeRecord.getChargeState() == 2 && chargeRecord.getCarType() == 3){
					// 协助预约 待收费
					int money = shoufei(chargeRecord);
					if(chargeRecord.getVisRecordInnerId() == null)continue;
					OrderRecord visCarInfo = tingcheMapper.selectOrderRecordByVinnerId(chargeRecord.getVisRecordInnerId());
					User user = renshiyewuMapper.selectUserByInnerId(visCarInfo.getUserInnerId());
					List<User> userList = null;
					if(companyInnerIdList.contains(user.getCompanyInnerId())){
						map.put("CompanyInnerId", user.getCompanyInnerId());
						map.put("UserTypeInnerId", "-1");
						map.put("rows", 1);
						userList = renshiyewuMapper.selectUserNew(map);
					}else{
						int searchDep = searchDep(user.getDepartmentInnerId());
						map.put("DepartmentInnerId", searchDep);
						map.put("UserTypeInnerId", "-1");
						map.put("rows", 1);
						userList = renshiyewuMapper.selectUserNew(map);
					}
					int rt = koukuan(userList.get(0),money*100);
					if(rt > 0){
						chargeRecord.setMoney(money*100);
						chargeRecord.setChargeState(3);
						chargeRecord.setChargeTime(sdf.format(new Date()));
						chargeRecord.setChargeType(3);
						chargeRecord.setOrderNo(new Date().getTime()+chargeRecord.getCarNumber());
						
						Integer tot = tingcheMapper.selectGWCarInfo(chargeRecord.getCarNumber());
						if(tot > 0 && money > 0){
							long st = sdf.parse(chargeRecord.getInTime()).getTime();
							Date date = new Date();
							date.setTime(st - 2*60*60*1000);
							chargeRecord.setInTime(sdf.format(date));
						}
						
						tingcheMapper.updateChargeRecord(chargeRecord);
					}
					Map<String,Object> rows = new HashMap<>();
					rows.put("VisitorState", 6);
					rows.put("RecordInnerId", visCarInfo.getRecordInnerId());
					tingcheMapper.updateChargeRecordState(rows);
					if(chargeRecord.getVisRecordInnerId() != null){
						map.put("ParkDate", sdf.format(new Date()).substring(0, 10) +" 00:00:00");
						map.put("ParkType", 1);
						ParkDate parkDate = tingcheMapper.selectParkDate(map);
						LogInfo.logInfo("车位原数量："+parkDate.toString());
						System.out.println("############"+parkDate.getParkResidue());
						parkDate.setParkResidue(parkDate.getParkResidue()+1);
						tingcheMapper.updateParkDate(parkDate);
						LogInfo.logInfo("车位加1"+parkDate.toString());
						System.out.println("**************"+parkDate.getParkResidue());
					}
				}else if(chargeRecord.getChargeState() == 2 && (chargeRecord.getCarType() == 2 || chargeRecord.getCarType() == 1)){
					searchLFCharge(chargeRecord);
				}
			}else{
				// 查询此车最后 的记录是进还是出，若是进不收费，若是出进行收费
				CarRecord carRecord = tingcheMapper.selectCarRecordByCarNumber(chargeRecord.getCarNumber());
				
				if(carRecord == null){
					if(chargeRecord.getCarType() == 0){
						//固定车
						chargeRecord.setMoney(0);
						chargeRecord.setChargeState(3);
						chargeRecord.setChargeTime(sdf.format(new Date()));
						chargeRecord.setChargeType(3);
						chargeRecord.setOrderNo(new Date().getTime()+chargeRecord.getCarNumber());
						tingcheMapper.updateChargeRecord(chargeRecord);
					}else if(chargeRecord.getCarType() == 3){
						if(!ifEnd(chargeRecord)){
							continue;
						}
						//协助预约
						int money = shoufei(chargeRecord);
						if(chargeRecord.getVisRecordInnerId() == null)continue;
						OrderRecord visCarInfo = tingcheMapper.selectOrderRecordByVinnerId(chargeRecord.getVisRecordInnerId());
						User user = renshiyewuMapper.selectUserByInnerId(visCarInfo.getUserInnerId());
						List<User> userList = null;
						if(companyInnerIdList.contains(user.getCompanyInnerId())){
							map.put("CompanyInnerId", user.getCompanyInnerId());
							map.put("UserTypeInnerId", "-1");
							map.put("rows", 1);
							userList = renshiyewuMapper.selectUserNew(map);
						}else{
							int searchDep = searchDep(user.getDepartmentInnerId());
							map.put("DepartmentInnerId", searchDep);
							map.put("UserTypeInnerId", "-1");
							map.put("rows", 1);
							userList = renshiyewuMapper.selectUserNew(map);
						}
						int rt = koukuan(userList.get(0),money*100);
						if(rt > 0){
							chargeRecord.setMoney(money*100);
							chargeRecord.setChargeState(3);
							chargeRecord.setChargeTime(sdf.format(new Date()));
							
							chargeRecord.setOrderNo(new Date().getTime()+chargeRecord.getCarNumber());
							Integer tot = tingcheMapper.selectGWCarInfo(chargeRecord.getCarNumber());
							if(tot > 0 && money > 0){
								long st = sdf.parse(chargeRecord.getInTime()).getTime();
								Date date = new Date();
								date.setTime(st - 2*60*60*1000);
								chargeRecord.setInTime(sdf.format(date));
							}
							tingcheMapper.updateChargeRecord(chargeRecord);
						}
						Map<String,Object> rows = new HashMap<>();
						rows.put("VisitorState", 6);
						rows.put("RecordInnerId", visCarInfo.getRecordInnerId());
						tingcheMapper.updateChargeRecordState(rows);
						if(chargeRecord.getVisRecordInnerId() != null){
							map.put("ParkDate", sdf.format(new Date()).substring(0, 10) +" 00:00:00");
							map.put("ParkType", 1);
							ParkDate parkDate = tingcheMapper.selectParkDate(map);
							LogInfo.logInfo("车位原数量："+parkDate.toString());
							System.out.println("############"+parkDate.getParkResidue());
							parkDate.setParkResidue(parkDate.getParkResidue()+1);
							tingcheMapper.updateParkDate(parkDate);
							LogInfo.logInfo("车位加1"+parkDate.toString());
							System.out.println("**************"+parkDate.getParkResidue());
						}
					}else{
						
						searchLFCharge(chargeRecord);
					}
				}else{
					if(carRecord.getType() == 1){
						continue;
					}
					if (carRecord.getInOrOut() == 0) {
						// 进    不满足收费
						continue;
					} else {
						// 出    满足收费
						if(chargeRecord.getCarType() == 0){
							//固定车
							chargeRecord.setMoney(0);
							chargeRecord.setChargeState(3);
							chargeRecord.setChargeTime(sdf.format(new Date()));
							chargeRecord.setChargeType(3);
							chargeRecord.setOrderNo(new Date().getTime()+chargeRecord.getCarNumber());
							tingcheMapper.updateChargeRecord(chargeRecord);
						}else if(chargeRecord.getCarType() == 3){
							//协助预约
							if(!ifEnd(chargeRecord)){
								continue;
							}
							int money = shoufei(chargeRecord);
							if(chargeRecord.getVisRecordInnerId() == null)continue;
							OrderRecord visCarInfo = tingcheMapper.selectOrderRecordByVinnerId(chargeRecord.getVisRecordInnerId());
							User user = renshiyewuMapper.selectUserByInnerId(visCarInfo.getUserInnerId());
							List<User> userList = null;
							if(companyInnerIdList.contains(user.getCompanyInnerId())){
								map.put("CompanyInnerId", user.getCompanyInnerId());
								map.put("UserTypeInnerId", "-1");
								map.put("rows", 1);
								userList = renshiyewuMapper.selectUserNew(map);
							}else{
								int searchDep = searchDep(user.getDepartmentInnerId());
								map.put("DepartmentInnerId", searchDep);
								map.put("UserTypeInnerId", "-1");
								map.put("rows", 1);
								userList = renshiyewuMapper.selectUserNew(map);
							}
							int rt = koukuan(userList.get(0),money*100);
							if(rt > 0){
								chargeRecord.setMoney(money*100);
								chargeRecord.setChargeState(3);
								chargeRecord.setChargeTime(sdf.format(new Date()));
								chargeRecord.setChargeType(3);
								chargeRecord.setOrderNo(new Date().getTime()+chargeRecord.getCarNumber());
								
								Integer tot = tingcheMapper.selectGWCarInfo(chargeRecord.getCarNumber());
								if(tot > 0 && money > 0){
									long st = sdf.parse(chargeRecord.getInTime()).getTime();
									Date date = new Date();
									date.setTime(st - 2*60*60*1000);
									chargeRecord.setInTime(sdf.format(date));
								}
								
								tingcheMapper.updateChargeRecord(chargeRecord);
							}
							Map<String,Object> rows = new HashMap<>();
							rows.put("VisitorState", 6);
							rows.put("RecordInnerId", visCarInfo.getRecordInnerId());
							tingcheMapper.updateChargeRecordState(rows);
							if(chargeRecord.getVisRecordInnerId() != null){
								map.put("ParkDate", sdf.format(new Date()).substring(0, 10) +" 00:00:00");
								map.put("ParkType", 1);
								ParkDate parkDate = tingcheMapper.selectParkDate(map);
								LogInfo.logInfo("车位原数量："+parkDate.toString());
								System.out.println("############"+parkDate.getParkResidue());
								parkDate.setParkResidue(parkDate.getParkResidue()+1);
								tingcheMapper.updateParkDate(parkDate);
								LogInfo.logInfo("车位加1"+parkDate.toString());
								System.out.println("**************"+parkDate.getParkResidue());
							}
						}else{
							if(chargeRecord.getOutTime() != null){
								searchLFCharge(chargeRecord);
							}
						}
					}
				}
			}
		}
	}
	
	private int searchLFCharge(ChargeRecord chargeRecord){
		
		int money = 0;
		Map<String ,String > row = new HashMap<>();
		row.put("CarNumber", chargeRecord.getCarNumber());
		row.put("OutTime", chargeRecord.getOutTime());
		String json = JSON.toJSONString(row);
		String doPost = HttpClintUtil.doPostJson("http://127.0.0.1:8077/LockService/v1/LockService/getChargeRecord", json);
		JSONObject fromObject = JSONObject.fromObject(doPost);
		Map<String, Object> datas = (Map) fromObject;
		if(datas.get("status").toString().equals("200")){
			List<Map<String,Object>> carRecordList = (List<Map<String, Object>>) datas.get("result");
			if(carRecordList.size() > 0){
				Map<String, Object> map2 = carRecordList.get(0);
				if(!map2.get("StopTime").toString().equals("0")){
					chargeRecord.setInTime(map2.get("InTime").toString());
					chargeRecord.setOutTime(map2.get("OutTime").toString());
					money = Integer.valueOf(map2.get("money").toString());
					chargeRecord.setMoney(money*100);
					chargeRecord.setChargeState(3);
					chargeRecord.setOrderNo(chargeRecord.getCarNumber()+System.currentTimeMillis());
					chargeRecord.setChargeTime(map2.get("ChargeDate").toString());
					chargeRecord.setChargeType(1);
					tingcheMapper.updateChargeRecord(chargeRecord);
				}
			}else{
				chargeRecord.setMoney(0);
				chargeRecord.setChargeState(3);
				chargeRecord.setOrderNo(chargeRecord.getCarNumber()+System.currentTimeMillis());
				chargeRecord.setChargeTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				chargeRecord.setChargeType(1);
				tingcheMapper.updateChargeRecord(chargeRecord);
			}
		}
		if(chargeRecord.getVisRecordInnerId() != null){
			Map<String,Object> map = new HashMap<>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			map.put("ParkDate", sdf.format(new Date())+" 00:00:00");
			map.put("ParkType", 2);
			ParkDate parkDate = tingcheMapper.selectParkDate(map);
			LogInfo.logInfo("车位原数量："+parkDate.toString());
			System.out.println("############"+parkDate.getParkResidue());
			parkDate.setParkResidue(parkDate.getParkResidue()+1);
			tingcheMapper.updateParkDate(parkDate);
			LogInfo.logInfo("车位加1"+parkDate.toString());
			System.out.println("**************"+parkDate.getParkResidue());
		}
		return money;
	}
	
	private int koukuan(User user,int money){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int rt = 0;
		ConLog conLog = new ConLog();
		// 查询现金账户
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("AccountTypeInnerId", 1);
		map1.put("UserInnerId", user.getUserInnerId());
		Account account = zhanghuyewuMapper.selectAccount(map1);
		conLog.setDiscountMoney(money);
		conLog.setProportion(10000);
		conLog.setNumberMoney(0);
		conLog.setNumberAFMoney(0);
		conLog.setNumberBFMoney(0);
		conLog.setAllowanceAFMoney(0);
		conLog.setAllowanceBFMoney(0);
		conLog.setAllowanceMoney(0);
		conLog.setMoney(money);
		conLog.setPersonMoney(money);
		conLog.setPersonBFMoney(account.getMoney());
		conLog.setPersonAFMoney(account.getMoney()-money);
		conLog.setMarkInnerId(0);
		conLog.setMarkId("0");
		conLog.setMarkTypeInnerId(0);
		conLog.setUserInnerId(user.getUserInnerId());
		conLog.setDepartmentInnerId(user.getDepartmentInnerId());
		conLog.setCompanyInnerId(user.getCompanyInnerId());
		conLog.setConTerminalInnerId(0);
		conLog.setTerminalTypeInnerId(0);
		conLog.setMerchantInnerId(1);
		conLog.setAppInnerId(1);
		conLog.setAreaInnerId(1);
		conLog.setConDatetime(sdf.format(new Date()));
		conLog.setUploadDatetime(sdf.format(new Date()));
		conLog.setConTypeInnerId(1);
		conLog.setConPattern(5);
		conLog.setConWay(5);
		conLog.setLimitTimes(0);
		conLog.setOffLine(1);
		conLog.setOperator(1);
		conLog.setRemark("车辆预约扣款");
		conLog.setRules("");
		Integer result;
		try {
			result = xiaofeiguanliMapper.insertConLog(conLog);
			if (result > 0) {
				map1.put("AccountInnerId", account.getAccountInnerId());
				map1.put("Money", account.getMoney()-money);
				rt = zhanghuyewuMapper.updateAccount(map1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rt;
	}
	
	public int shoufeiyuqi(ChargeRecord chargeRecord) throws Exception{
		List<PassRules> passRuleList = tingcheMapper.selectPassRulesByInnerId(chargeRecord.getCarType());
		int money = 0;
		
		Integer tot = tingcheMapper.selectGWCarInfo(chargeRecord.getCarNumber());
		if(tot > 0){
			return 0;
		}
		
		String startTime = chargeRecord.getInTime();
		String endTime = chargeRecord.getOutTime();
		String startRiqi = startTime.substring(0, 10);
		String endRiqi = endTime.substring(0, 10);
		
		money = baitianshoufei(startTime,endTime,startRiqi+" 00:00:00",startRiqi+" 23:59:59",passRuleList.get(0).getMoney());
		
		return money;
	}
	
	public int shoufei(ChargeRecord chargeRecord) throws Exception{
		List<PassRules> passRuleList = tingcheMapper.selectPassRulesByInnerId(chargeRecord.getCarType());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long cha = sdf.parse(chargeRecord.getInTime()).getTime()+passRuleList.get(0).getFreeTime()*60*1000;
		if(sdf.parse(chargeRecord.getOutTime()).getTime() - cha <= 0){
			return 0;
		}
		int money = 0;
		Integer tot = tingcheMapper.selectGWCarInfo(chargeRecord.getCarNumber());
		if(tot > 0){
			long st = sdf.parse(chargeRecord.getInTime()).getTime();
			long et = sdf.parse(chargeRecord.getOutTime()).getTime();
			if(et - st > 2*60*60*1000){
				Date date = new Date();
				date.setTime(st+2*60*60*1000);
				chargeRecord.setInTime(sdf.format(date));
			}else{
				return money;
			}
		}
		String startTime = chargeRecord.getInTime();
		String endTime = chargeRecord.getOutTime();
		String startRiqi = startTime.substring(0, 10);
		String endRiqi = endTime.substring(0, 10);
		if(startRiqi.equals(endRiqi)){
			if(passRuleList.size() == 1){
				money = baitianshoufei(startTime,endTime,startRiqi+" "+passRuleList.get(0).getStartTime(),startRiqi+" "+passRuleList.get(0).getEndTime(),passRuleList.get(0).getMoney());
			}else{
				for (PassRules passRules : passRuleList) {
					String sDT = passRules.getStartTime().substring(0, 2);
					String eDT = passRules.getEndTime().substring(0, 2);
					if(Integer.valueOf(sDT) > Integer.valueOf(eDT)){
						//夜间
						money += yejianshoufei(startTime,endTime,startRiqi+" "+passRules.getEndTime(),passRules.getMoney(),startRiqi+" "+passRules.getStartTime());
					}else{
						//白天
						money += baitianshoufei(startTime,endTime,startRiqi+" "+passRules.getStartTime(),startRiqi+" "+passRules.getEndTime(),passRules.getMoney());
					}
				}
			}
		}else{
			List<String> dates = getDates(startRiqi,endRiqi);
			for (String string : dates) {
				for (PassRules passRules : passRuleList) {
					String sDT = passRules.getStartTime().substring(0, 2);
					String eDT = passRules.getEndTime().substring(0, 2);
					if(Integer.valueOf(sDT) > Integer.valueOf(eDT)){
						//夜间
						if(string.equals(startRiqi)){
							money += yejianshoufei(startTime,string+" 23:59:59",string+" "+passRules.getEndTime(),passRules.getMoney(),string+" "+passRules.getStartTime());
						}else if(string.equals(endRiqi)){
							money += yejianshoufei(string+" 00:00:00",endTime,string+" "+passRules.getEndTime(),passRules.getMoney(),string+" "+passRules.getStartTime()) -passRules.getMoney();
						}else{
							money = money + yejianshoufei(string+" 00:00:00",string+" 23:59:59",string+" "+passRules.getEndTime(),passRules.getMoney(),string+" "+passRules.getStartTime())-passRules.getMoney();
						}
					}else{
						//白天
						if(string.equals(startRiqi)){
							money += baitianshoufei(startTime, string+" "+passRules.getEndTime(),string+" "+passRules.getStartTime(),string+" "+passRules.getEndTime(),passRules.getMoney());
						}else if(string.equals(endRiqi)){
							money += baitianshoufei(string+" "+passRules.getStartTime(), endTime,string+" "+passRules.getStartTime(),string+" "+passRules.getEndTime(),passRules.getMoney());
						}else{
							money += baitianshoufei(string+" "+passRules.getStartTime(), string+" "+passRules.getEndTime(),string+" "+passRules.getStartTime(),string+" "+passRules.getEndTime(),passRules.getMoney());
						}
					}
				}
			}
		}
		return money;
	}
	
	private int yejianshoufei(String inTime,String outTime,String RulesETime1,int money,String RulesSTime2) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int ssMoney = 0;
		int sxMoney = 0;
		long inTimes = sdf.parse(inTime).getTime();
		long outTimes = sdf.parse(outTime).getTime();
		String hour = outTime.substring(11, 13);
		long RulesETimes1 = sdf.parse(RulesETime1).getTime();
		long RulesSTimes2 = sdf.parse(RulesSTime2).getTime();
		//判断进入时间和开始时间
		if(inTimes <= RulesETimes1){
			//凌晨开始到白天收费开始时间
			if(outTimes < RulesETimes1){
				long nums = (outTimes-inTimes)/(3600*1000*2);
				/*long yu = (outTimes-inTimes)%(3600*1000*2);
				if(yu != 0){
					if(hour.equals("00") || hour.equals("02")|| hour.equals("04")|| hour.equals("06")){
						
					}else{
						yu = (outTimes-inTimes)%(3600*1000*1);
						if(yu != 0){
							nums+=1;
						}
					}
				}*/
				nums+=1;
				ssMoney = (int)nums * money;
			}else{
				long nums = (RulesETimes1-inTimes)/(3600*1000*2);
				/*long yu = (RulesETimes1-inTimes)%(3600*1000*2);
				if(yu != 0){
					nums+=1;
				}*/
				ssMoney = (int)nums * money;
			}
		}
		if(outTimes >= RulesSTimes2){
			//白天收费结束时间 到离开时间结束时间
			long nums = (outTimes-RulesSTimes2)/(3600*1000*2);
			/*long yu = (outTimes-RulesSTimes2)%(3600*1000*2);
			if(yu != 0){
				nums+=1;
			}*/
			sxMoney = (int)nums * money;
		}
		return ssMoney+sxMoney;
	}
	
	private int baitianshoufei(String inTime,String outTime,String RulesSTime,String RulesETime,int money) throws ParseException{
		int sMoney = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long inTimes = sdf.parse(inTime).getTime();
		long outTimes = sdf.parse(outTime).getTime();
		long RulesSTimes = sdf.parse(RulesSTime).getTime();
		long RulesETimes = sdf.parse(RulesETime).getTime();
		if(outTimes < RulesSTimes){
			return sMoney;
		}
		//判断进入时间和开始时间
		if(inTimes > RulesSTimes){
			if(inTimes > RulesETimes){
				return sMoney;
			}else{
				//直接开始收费
				if(outTimes > RulesETimes){
					// 开始时间是进入时间 ，结束时间就是规则结束时间
					long nums = (RulesETimes-inTimes)/(60*1000*15);
					/*long yu = (RulesETimes-inTimes)%(60*1000*15);
					if(yu != 0){
						nums+=1;
					}*/
					sMoney = (int)nums * money;
				}else{
					//开始时间是进入时间，结束时间是离开时间
					long nums = (outTimes-inTimes)/(60*1000*15);
					/*long yu = (outTimes-inTimes)%(60*1000*15);
					if(yu != 0){
						nums+=1;
					}*/
					sMoney = (int)nums * money;
				}
			}
		}else{
			if(outTimes > RulesETimes){
				// 开始时间是规则开始时间 ，结束时间就是规则结束时间
				long nums = (RulesETimes-RulesSTimes)/(60*1000*15);
				/*long yu = (RulesETimes-RulesSTimes)%(60*1000*15);
				if(yu != 0){
					nums+=1;
				}*/
				sMoney = (int)nums * money;
			}else{
				//开始时间是规则开始时间 ，结束时间是离开时间
				long nums = (outTimes-RulesSTimes)/(60*1000*15);
				/*long yu = (outTimes-RulesSTimes)%(60*1000*15);
				if(yu != 0){
					nums+=1;
				}*/
				sMoney = (int)nums * money;
			}
		}
		return sMoney;
	}
	
	public List<String> getDates(String fLeaveStartTime,String fLeaveEndTime) throws Exception {  
		List<String> times = new ArrayList<>();
		fLeaveStartTime = fLeaveStartTime.substring(0, 10)+" 00:00:00";
		fLeaveEndTime = fLeaveEndTime.substring(0, 10)+" 23:59:59";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    long time2 = 0;
	    long time3 = 0;
		try {
			time2 = sdf.parse(fLeaveStartTime).getTime();
			time3 = sdf.parse(fLeaveEndTime).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
	    Long oneDay = 1000 * 60 * 60 * 24L;  
	  
	    while (time2 < time3) {  
	        Date d = new Date(time2);  
	        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	        boolean theWeeked = theWeeked(df.format(d));
	        if(theWeeked){
	        	times.add(df.format(d));  
	        }
	        time2 += oneDay;  
	    }
	    return times;
	}

	
	public boolean theWeeked(String time) throws Exception{
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		boolean flag = false;
		Date nowTime = new Date();
		Calendar cal=Calendar.getInstance();
		cal.setTime(nowTime);
		Map<String,Object> map = new HashMap<>();
		map.put("rows", 100);
		map.put("sort", "HolidayInnerId");
		map.put("order", "asc");
		map.put("start", 0);
		List<Holiday> holidayList = tingcheMapper.selectAttendanceHoliday(map);
		String newtimes = time+" 01:00:00";
		long time2 = sdf1.parse(newtimes).getTime();
		String newTime = sdf2.format(new Date());
		if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
			int num = 0;
			for (Holiday holiday : holidayList) {
				if(holiday.getStandbyA() != null ){
					if(newTime.equals(holiday.getStandbyA())){
						num++;
					}
				}
				if(holiday.getStandbyB() != null ){
					if(newTime.equals(holiday.getStandbyB())){
						num++;
					}
				}
			}
			if(num > 0){
				flag = true;
			}else{
				flag = false;
			}
		}else{
			int num = 0;
			for (Holiday holiday : holidayList) {
				if(holiday.getStartDate() != null && holiday.getEndDate() != null){
					if(time2 >sdf1.parse(holiday.getStartDate()).getTime()  && time2 < sdf1.parse(holiday.getEndDate()).getTime()){
						num ++;
					}
				}
			}
			if(num > 0){
				flag = false;
			}else{
				flag = true;
			}
		}
		return flag;  //false是节假日 true：不是节假日
	}
	
	@RequestMapping(value = "/WeChatPay",method=RequestMethod.GET)
	public void WeChatPay(HttpServletRequest request, HttpServletResponse response) throws Exception{
		JSONObject jo = new JSONObject();
		try {
			response.setCharacterEncoding("utf-8");
			String parameterInTime = request.getParameter("inTime");
			String parameterOutTime = request.getParameter("outTime");
			String parameterCarType = request.getParameter("carType");
			String overdueFlag = request.getParameter("overdueFlag");
			LogInfo.logInfo("进入微信调取收费接口", parameterInTime+"==+"+parameterOutTime+"=="+parameterCarType);
			ChargeRecord chargeRecord = new ChargeRecord();
			chargeRecord.setCarType(Integer.valueOf(parameterCarType));
			chargeRecord.setInTime(parameterInTime);
			chargeRecord.setOutTime(parameterOutTime);
			if(chargeRecord != null){
				int shoufei = 0;
				if(overdueFlag != null && !overdueFlag.equals("")){
					if(overdueFlag.equals("1")){
						shoufei = gerenshoufei(chargeRecord);
					}else{
						shoufei = shoufeiyuqi(chargeRecord);
					}
				}else{
					shoufei = gerenshoufei(chargeRecord);
				}
				LogInfo.logInfo("完成微信调取收费接口,收费金额为：---",shoufei+"分");
				jo.put("money", shoufei);
				jo.put("state", "200");
			}
			renderText(response, jo.toString());
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "StopCharging", e, "微信调取收费");
			jo.put("state", "500");
			jo.put("money", "结算异常");
			renderText(response, jo.toString());
		}
	}
	
	@Scheduled(cron = "0 0 1 1 1 ?")
	public void week() throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Set<String> yearDoubleWeekend = getYearDoubleWeekend(Integer.valueOf(sdf.format(new Date())));
		Holiday holiday = new Holiday();
		for (String string : yearDoubleWeekend) {
			String format = sdf1.format(sdf1.parse(string));
			holiday.setStartDate(format);
			tingcheMapper.insertAttendanceHoliday(holiday);
		}
	}
	
	public static Set<String> getYearDoubleWeekend(int year) {
		Set<String> listDates = new HashSet<String>();
		Calendar calendar = Calendar.getInstance();// 当前日期
		calendar.set(year, 6, 1);
		Calendar nowyear = Calendar.getInstance();
		Calendar nexty = Calendar.getInstance();
		nowyear.set(year, 0, 1);
		nexty.set(year + 1, 0, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -calendar.get(Calendar.DAY_OF_WEEK));// 周六
		Calendar c = (Calendar) calendar.clone();
		for (; calendar.before(nexty) && calendar.after(nowyear); calendar.add(Calendar.DAY_OF_YEAR, -7)) {
			listDates.add(calendar.get(Calendar.YEAR) + "-" + (1 + calendar.get(Calendar.MONTH)) + "-"
					+ calendar.get(Calendar.DATE));
			listDates.add(calendar.get(Calendar.YEAR) + "-" + (1 + calendar.get(Calendar.MONTH)) + "-"
					+ (1 + calendar.get(Calendar.DATE)));
		}
		for (; c.before(nexty) && c.after(nowyear); c.add(Calendar.DAY_OF_YEAR, 7)) {
			listDates.add(c.get(Calendar.YEAR) + "-" + (1 + c.get(Calendar.MONTH)) + "-" + c.get(Calendar.DATE));
			listDates.add(c.get(Calendar.YEAR) + "-" + (1 + c.get(Calendar.MONTH)) + "-" + (1 + c.get(Calendar.DATE)));
		}
		return listDates;
	}
	
	public int searchDep(Integer DepInnerId){
		List<Integer> depList = tingcheMapper.selectPayDepartment();
		if(depList.contains(DepInnerId)){
			return DepInnerId;
		}
		Department depInfo = renshiyewuMapper.selectDepartmentByInnerId(DepInnerId);
		if(depInfo.getDepartmentLevel() == 4){
			// 返回二级部门主键
			depInfo = renshiyewuMapper.selectDepartmentByInnerId(depInfo.getUpInnerId());
			if(depList.contains(depInfo.getDepartmentInnerId())){
				return depInfo.getDepartmentInnerId();
			}
			depInfo = renshiyewuMapper.selectDepartmentByInnerId(depInfo.getUpInnerId());
			return depInfo.getDepartmentInnerId();
		}else if(depInfo.getDepartmentLevel() == 3){
			// 返回二级部门主键
			depInfo = renshiyewuMapper.selectDepartmentByInnerId(depInfo.getUpInnerId());
			return depInfo.getDepartmentInnerId();
		}else if(depInfo.getDepartmentLevel() == 2){
			// 返回二级部门主键
			return depInfo.getDepartmentInnerId();
		}else{
			// 返回所在部门主键
			return depInfo.getDepartmentInnerId();
		}
	}
	
	//@Scheduled(cron = "0 0 */2 * * ?")
	public void rengognshoufei(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String ,Object > row = new HashMap<>();
		Integer record = tingcheMapper.selectLFMaxId();
		if(record != null){
			row.put("ChargeRecord",record);
		}else{
			row.put("ChargeRecord",0);
		}
		String json = JSON.toJSONString(row);
		String doPost = HttpClintUtil.doPostJson("http://127.0.0.1:8077/LockService/v1/LockService/getLFChargeRecord", json);
		JSONObject fromObject = JSONObject.fromObject(doPost);
		Map<String, Object> datas = (Map) fromObject;
		boolean flag = false;
		if(datas.get("status").toString().equals("200")){
			List<Map<String,Object>> carRecordList = (List<Map<String, Object>>) datas.get("result");
			Map<String,Object> lfMap = new HashMap<>();
			Map<String,Object> carMap = new HashMap<>();
			for (Map<String, Object> map : carRecordList) {
				try {
					Integer rt = tingcheMapper.selectLFChargeInfo(Integer.valueOf(map.get("RecordId").toString()));
					if(rt > 0){
						continue;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ChargeRecord chargeRecord = tingcheMapper.selectChargeRecordInfoByMap(map);
				if(chargeRecord != null){
					flag = false;
					if(chargeRecord.getChargeState() != 3){
						flag = true;
					}
					Integer carType = 2;
					carMap.put("CarNumber", map.get("CarCode").toString());
					carMap.put("time", map.get("InTime"));
					OrderRecord selectVisCarInfo = tingcheMapper.selectOrderRecordByMap(carMap);
					if(selectVisCarInfo != null){
						if(selectVisCarInfo.getOrderType() == 1 ){
							//协助
							carType = 1;
						}else if(selectVisCarInfo.getOrderType() == 2 ){
							//临时
							carType = 2;
						}else {
							// 平台预约
							carType = 3;
						}
						chargeRecord.setVisRecordInnerId(selectVisCarInfo.getRecordInnerId());
					}else{
						carType = 2;
					}
					// 判断金额是否为空，若不为空，判断是否一致，不一致新加（人工收费）
					if(chargeRecord.getMoney() != null ){
						Integer VisRecordInnerId = chargeRecord.getVisRecordInnerId();
						if(chargeRecord.getMoney() == Integer.valueOf(map.get("money").toString())*100){
							// 金额相同（立方收费），更新下进入离开时间，和收费状态，收费方式
							chargeRecord.setInTime(map.get("InTime").toString());
							chargeRecord.setOutTime(map.get("OutTime").toString());
							chargeRecord.setChargeType(1);
							chargeRecord.setChargeState(3);
							chargeRecord.setMoney(Integer.valueOf(map.get("money").toString())*100);
							tingcheMapper.updateChargeRecord(chargeRecord);
							lfMap.put("ChargeRecordInnerId", chargeRecord.getChargeRecordInnerId());
						}else{
							// 新建  金额肯定大于0(立方有收费记录)
							chargeRecord = new ChargeRecord();
							chargeRecord.setInTime(map.get("InTime").toString());
							chargeRecord.setOutTime(map.get("OutTime").toString());
							chargeRecord.setChargeType(1);
							chargeRecord.setRecDateTime(map.get("InTime").toString().substring(0,10));
							chargeRecord.setCarNumber(map.get("CarCode").toString());
							chargeRecord.setMoney(Integer.valueOf(map.get("money").toString())*100);
							chargeRecord.setCarType(carType);
							chargeRecord.setChargeState(3);
							chargeRecord.setOrderNo(map.get("CarCode").toString()+System.currentTimeMillis());
							chargeRecord.setChargeTime(map.get("ChargeDate").toString());
							chargeRecord.setVisRecordInnerId(VisRecordInnerId);
							tingcheMapper.insertChargeRecord(chargeRecord);
							lfMap.put("ChargeRecordInnerId", chargeRecord.getChargeRecordInnerId());
						}
					}else{
						// 金额大于0，立方收费，直接更新即可（若为预约车，更改预约状态为完成）
						chargeRecord.setInTime(map.get("InTime").toString());
						chargeRecord.setOutTime(map.get("OutTime").toString());
						chargeRecord.setChargeType(1);
						chargeRecord.setChargeState(3);
						chargeRecord.setCarType(carType);
						chargeRecord.setMoney(Integer.valueOf(map.get("money").toString())*100);
						tingcheMapper.updateChargeRecord(chargeRecord);
					}
					if(flag){
						if(chargeRecord.getVisRecordInnerId() != null){
							map.put("ParkDate", sdf.format(new Date()).substring(0, 10) +" 00:00:00");
							map.put("ParkType", 1);
							ParkDate parkDate = tingcheMapper.selectParkDate(map);
							LogInfo.logInfo("车位原数量："+parkDate.toString());
							System.out.println("############"+parkDate.getParkResidue());
							parkDate.setParkResidue(parkDate.getParkResidue()+1);
							tingcheMapper.updateParkDate(parkDate);
							LogInfo.logInfo("车位加1"+parkDate.toString());
							System.out.println("**************"+parkDate.getParkResidue());
						}
					}
				}else{
					// 新建  金额肯定大于0(立方有收费记录)
					// 查询是否为预约车
					chargeRecord = new ChargeRecord();
					lfMap.put("rows", "100");
					lfMap.put("order", "desc");  
					lfMap.put("sort", "RecordInnerId");
					lfMap.put("start", "0");
					lfMap.put("CarNumber", map.get("CarCode"));
					lfMap.put("time", map.get("InTime"));
					OrderRecord orderRecord = tingcheMapper.selectOrderRecordByMap(lfMap);
					if(orderRecord != null){
						if(orderRecord.getOrderType() == 1 ){
							//协助
							chargeRecord.setCarType(1);
						}else if(orderRecord.getOrderType() == 2 ){
							//临时
							chargeRecord.setCarType(2);
						}else {
							// 平台预约
							chargeRecord.setCarType(3);
						}
						chargeRecord.setVisRecordInnerId(orderRecord.getRecordInnerId());
					}else{
						chargeRecord.setCarType(2);
					}
					chargeRecord.setInTime(map.get("InTime").toString());
					chargeRecord.setOutTime(map.get("OutTime").toString());
					chargeRecord.setChargeType(1);
					chargeRecord.setRecDateTime(map.get("InTime").toString().substring(0,10));
					chargeRecord.setCarNumber(map.get("CarCode").toString());
					chargeRecord.setMoney(Integer.valueOf(map.get("money").toString())*100);
					chargeRecord.setChargeState(3);
					chargeRecord.setOrderNo(map.get("CarCode").toString()+System.currentTimeMillis());
					chargeRecord.setChargeTime(map.get("ChargeDate").toString());
					tingcheMapper.insertChargeRecord(chargeRecord);
					lfMap.put("ChargeRecordInnerId", chargeRecord.getChargeRecordInnerId());
				}
				lfMap.put("LFRecordId", map.get("RecordId"));
				tingcheMapper.insertLFChargeInfo(lfMap);
			}
		}
	}
	
	public int gerenshoufei(ChargeRecord chargeRecord) throws Exception{
		List<PassRules> passRuleList = tingcheMapper.selectPassRulesByInnerId(chargeRecord.getCarType());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long cha = sdf.parse(chargeRecord.getInTime()).getTime()+passRuleList.get(0).getFreeTime()*60*1000;
		if(sdf.parse(chargeRecord.getOutTime()).getTime() - cha <= 0){
			return 0;
		}
		int money = 0;
		Integer tot = tingcheMapper.selectGWCarInfo(chargeRecord.getCarNumber());
		if(tot > 0){
			long st = sdf.parse(chargeRecord.getInTime()).getTime();
			long et = sdf.parse(chargeRecord.getOutTime()).getTime();
			if(et - st > 2*60*60*1000){
				Date date = new Date();
				date.setTime(st+2*60*60*1000);
				chargeRecord.setInTime(sdf.format(date));
			}else{
				return money;
			}
		}
		String startTime = chargeRecord.getInTime();
		String endTime = chargeRecord.getOutTime();
		String startRiqi = startTime.substring(0, 10);
		String endRiqi = endTime.substring(0, 10);
		if(startRiqi.equals(endRiqi)){
			boolean theWeeked = theWeeked(endRiqi);
			if(!theWeeked){
				return 0;
			}
			if(passRuleList.size() == 1){
				money = baitianshoufei(startTime,endTime,startRiqi+" "+passRuleList.get(0).getStartTime(),startRiqi+" "+passRuleList.get(0).getEndTime(),passRuleList.get(0).getMoney());
			}else{
				for (PassRules passRules : passRuleList) {
					String sDT = passRules.getStartTime().substring(0, 2);
					String eDT = passRules.getEndTime().substring(0, 2);
					if(Integer.valueOf(sDT) > Integer.valueOf(eDT)){
						//夜间
						money += yejianshoufei(startTime,endTime,startRiqi+" "+passRules.getEndTime(),passRules.getMoney(),startRiqi+" "+passRules.getStartTime());
					}else{
						//白天
						money += baitianshoufei(startTime,endTime,startRiqi+" "+passRules.getStartTime(),startRiqi+" "+passRules.getEndTime(),passRules.getMoney());
					}
				}
			}
		}else{
			List<String> dates = getDates(startRiqi,endRiqi);
			for (String string : dates) {
				for (PassRules passRules : passRuleList) {
					String sDT = passRules.getStartTime().substring(0, 2);
					String eDT = passRules.getEndTime().substring(0, 2);
					if(Integer.valueOf(sDT) > Integer.valueOf(eDT)){
						//夜间
						if(string.equals(startRiqi)){
							money += yejianshoufei(startTime,string+" 23:59:59",string+" "+passRules.getEndTime(),passRules.getMoney(),string+" "+passRules.getStartTime());
						}else if(string.equals(endRiqi)){
							money += yejianshoufei(string+" 00:00:00",endTime,string+" "+passRules.getEndTime(),passRules.getMoney(),string+" "+passRules.getStartTime()) -passRules.getMoney();
						}else{
							money = money + yejianshoufei(string+" 00:00:00",string+" 23:59:59",string+" "+passRules.getEndTime(),passRules.getMoney(),string+" "+passRules.getStartTime())-passRules.getMoney();
						}
					}else{
						//白天
						if(string.equals(startRiqi)){
							money += baitianshoufei(startTime, string+" "+passRules.getEndTime(),string+" "+passRules.getStartTime(),string+" "+passRules.getEndTime(),passRules.getMoney());
						}else if(string.equals(endRiqi)){
							money += baitianshoufei(string+" "+passRules.getStartTime(), endTime,string+" "+passRules.getStartTime(),string+" "+passRules.getEndTime(),passRules.getMoney());
						}else{
							money += baitianshoufei(string+" "+passRules.getStartTime(), string+" "+passRules.getEndTime(),string+" "+passRules.getStartTime(),string+" "+passRules.getEndTime(),passRules.getMoney());
						}
					}
				}
			}
		}
		return money;
	}
	
}


