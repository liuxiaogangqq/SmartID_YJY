package com.dhsr.smartid.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.dhsr.smartid.renshiyewu.dao.RenshiyewuMapper;
import com.dhsr.smartid.renshiyewu.domain.User;
import com.dhsr.smartid.tingchexitong.dao.TingcheMapper;
import com.dhsr.smartid.tingchexitong.domain.CarInfo;
import com.dhsr.smartid.tingchexitong.domain.CarRecord;
import com.dhsr.smartid.tingchexitong.domain.OrderRecord;
import com.dhsr.smartid.tingchexitong.domain.UserCarInfo;
import com.dhsr.smartid.util.HttpClintUtil;
import com.mysql.jdbc.log.Log;

import net.sf.json.JSONObject;

//@Component
public class CarRecordTask {
	
	@Resource
	private TingcheMapper tingcheMapper;
	@Resource
	private RenshiyewuMapper renshiyewuMapper;
	
	@Scheduled(cron = "0/15 * * * * ?")
	private void getRecord() {
		
		try{
		Map<String ,Object > map = new HashMap<>();
		Integer RecordInnerId = tingcheMapper.selectMaxRecordInnerId();
		if(RecordInnerId!=null){
			RecordInnerId = RecordInnerId -500;
		}else{
			RecordInnerId=70136 ;
		}
		map.put("RecordID", RecordInnerId);
		String json = JSON.toJSONString(map);
		String doPost = HttpClintUtil.doPostJson("http://127.0.0.1:8077/LockService/v1/LockService/getCarRecord", json);
		JSONObject fromObject = JSONObject.fromObject(doPost);
		Map<String, Object> datas = (Map) fromObject;
		if(datas.get("status").toString().equals("200")){
			List<Map<String,Object>> carRecordList = (List<Map<String, Object>>) datas.get("result");
			for (Map<String, Object> row : carRecordList) {
				if(row.get("CarCode").toString().equals("无车牌")){
					continue;
				}
				if(row.get("ChannelID").toString().contains("1") || row.get("ChannelID").toString().contains("2")){
					CarRecord carRecord = new CarRecord();
					carRecord.setCarNumber(row.get("CarCode").toString());
					carRecord.setInOrOut(Integer.valueOf(row.get("InOrOut").toString()));
					carRecord.setChannelId(row.get("ChannelID").toString());
					carRecord.setEntryTime(row.get("Crdtm").toString());
					carRecord.setRecordID(Integer.valueOf(row.get("RecordID").toString()));
					carRecord.setFlag(0);
					Integer selectCarRecordTot = tingcheMapper.selectCarRecordTot(carRecord);
					if(selectCarRecordTot <=0 ){
						UserCarInfo carInfo = tingcheMapper.selectUserCarInfoByCarNumber(row.get("CarCode").toString());
						if(carInfo == null){
							map.put("rows", "100");
							map.put("order", "desc");  
							map.put("sort", "RecordInnerId");
							map.put("start", "0");
							map.put("CarNumber", row.get("CarCode").toString());
							map.put("VisitorState", "1,4,5,7");
							List<OrderRecord> selectVisCarInfo = tingcheMapper.selectVisCarInfo(map);
							if(selectVisCarInfo.size()>0){
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								if(Integer.valueOf(row.get("InOrOut").toString()) == 0){ 
									// 入
									map.put("VisitorState", "1,7");
									map.put("time", row.get("Crdtm").toString());
									OrderRecord orderRecord = tingcheMapper.selectOrderRecordByMap(map);
									if(orderRecord != null ){
										if(orderRecord.getOrderType() == 1 ){
											//协助
											carRecord.setType(1);
										}else if(orderRecord.getOrderType() == 2 ){
											//临时
											carRecord.setType(2);
										}else {
											// 平台预约
											carRecord.setType(3);
										}
										carRecord.setStandbyB(orderRecord.getRecordInnerId()+"");
										map.put("RecordInnerId", orderRecord.getRecordInnerId());
										map.put("VisitorState", "4");
										tingcheMapper.updateChargeRecordState(map);
									}else{
										// 不在预约时间段内
										carRecord.setType(4);
									}
								}else{
									// 出
									map.put("VisitorState", "4,5");
									OrderRecord orderRecord = tingcheMapper.selectOrderRecordByMap(map);
									if(orderRecord != null ){
										if(selectVisCarInfo.get(0).getOrderType() == 1 ){
											//协助
											carRecord.setType(1);
										}else if(selectVisCarInfo.get(0).getOrderType() == 2 ){
											//临时
											carRecord.setType(2);
										}else {
											// 平台预约
											carRecord.setType(3);
										}
										carRecord.setStandbyB(orderRecord.getRecordInnerId()+"");
										map.put("RecordInnerId", orderRecord.getRecordInnerId());
										if(orderRecord.getOrderType() == 2){
											map.put("VisitorState", "6");
											tingcheMapper.updateChargeRecordState(map);
										}else{
											map.put("VisitorState", "7");
											tingcheMapper.updateChargeRecordState(map);
										}
									}else{
										// 不在预约时间段内
										carRecord.setType(4);
									}
								}
							}else{
								//特殊车辆
								carRecord.setType(4);
							}
						}else{
							// 固定车
							carRecord.setType(0);
						}
						
						tingcheMapper.insertCarRecord(carRecord);
						// 需开放
						sendCarRecord(carRecord);
						if(carRecord.getType() == 1 || carRecord.getType() == 2 ){
							Map<String,Object> ppt = new HashMap<>();
							if(carRecord.getInOrOut() == 0){
								ppt.put("inOrOut", 1);
							}else{
								ppt.put("inOrOut", 2);
							}
							String standbyB = carRecord.getStandbyB();
							OrderRecord VOrder = tingcheMapper.selectOrderRecordByVinnerId(Integer.valueOf(standbyB));
							User u = renshiyewuMapper.selectUserByInnerId(VOrder.getUserInnerId());
							ppt.put("userByD", u.getStandbyD());
							ppt.put("recTime", carRecord.getEntryTime());
							ppt.put("carCode", carRecord.getCarNumber());
							String json1 = JSON.toJSONString(ppt);
							String doPostJson = HttpClintUtil.doPostJson("http://parking.biad.com.cn/ParkInfo/sendInOrOutRec", json1);
							//String doPostJson = HttpClintUtil.doPostJson("http://172.18.51.12:8000/sireyun/car/record/sendCarRecord", json1);
							System.out.println(doPostJson);  
						}
					}
				}
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron = "0 30 12 * * ?")
	private void deleteCarInfo() {
		// 查询过期车辆信息
		List<CarInfo> carInfoList = tingcheMapper.selectGQCarInfo();
		StringBuilder carSb = new StringBuilder();
		StringBuilder carInnerId = new StringBuilder();
		for (CarInfo carInfo : carInfoList) {
			if(carInfo.getLFInnerId() != null){
				carSb.append(carInfo.getLFInnerId()+",");
			}
			carInnerId.append(carInfo.getCarInfoInnerId()+",");
		}
		Map<String ,Object > map = new HashMap<>();
		map.put("carInnerId", carInnerId.toString());
		tingcheMapper.deleteCarInfo(map);
		map.clear();
		map.put("CarSb", carSb.toString().substring(0, carSb.toString().length()-1));
		String json = JSON.toJSONString(map);
		HttpClintUtil.doPostJson("http://127.0.0.1:8077/LockService/v1/LockService/deleteCarInfo", json);
	}
	
	public void sendCarRecord(CarRecord carRecord){
		Map<String,Object> ppt = new HashMap<>();
		ppt.put("CarNumber",carRecord.getCarNumber());
		ppt.put("EntryTime",carRecord.getEntryTime());
		ppt.put("InOrOut",carRecord.getInOrOut());
		ppt.put("Channel",carRecord.getChannelId());
		ppt.put("CarType",carRecord.getType());
		String json = JSON.toJSONString(ppt);
		String doPostJson = HttpClintUtil.doPostJson("http://172.18.51.12:39003/sireyun/car/record/sendCarRecord", json);
		System.out.println(doPostJson);
	}
	
	@Scheduled(cron = "12 10 */3 * * ?")
	private void CarChargeInfo() {
		List<CarInfo> carInfoList = tingcheMapper.selectGQCarInfo();
		StringBuilder carSb = new StringBuilder();
		StringBuilder carInnerId = new StringBuilder();
		for (CarInfo carInfo : carInfoList) {
			if(carInfo.getLFInnerId() != null){
				carSb.append(carInfo.getLFInnerId()+",");
			}
			carInnerId.append(carInfo.getCarInfoInnerId()+",");
		}
		Map<String ,Object > map = new HashMap<>();
		map.put("carInnerId", carInnerId.toString());
		tingcheMapper.deleteCarInfo(map);
		map.clear();
		map.put("CarSb", carSb.toString().substring(0, carSb.toString().length()-1));
		String json = JSON.toJSONString(map);
		HttpClintUtil.doPostJson("http://127.0.0.1:8077/LockService/v1/LockService/deleteCarInfo", json);
	}
	
}
