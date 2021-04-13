package com.dhsr.smartid.tingchexitong.domain;

public class CarInfo {
	private Integer CarInfoInnerId;   
	private String CarNumber;	
	private String CarNumber1;	
	private String CarNumber2;	
	private String CarNumber3;	
	private String CarNumber4;	
	private Integer UserInnerId;	
	private Integer CarTypeInnerId;	
	private String StartTime;	
	private String EndTime;	
	private String Remark;	
	private Integer DelFlag;
	private Integer ParkingNum;
	private String LFInnerId;
	private Integer ParkInfo;
	public CarInfo() {
		super();
	}
	public CarInfo(Integer carInfoInnerId, String carNumber, Integer userInnerId, Integer carTypeInnerId,
			String startTime, String endTime, String remark, Integer delFlag, String lFInnerId, String carNumber1
			, String carNumber2, String carNumber3, String carNumber4) {
		super();
		CarInfoInnerId = carInfoInnerId;
		CarNumber = carNumber;
		CarNumber1 = carNumber1;
		CarNumber2 = carNumber2;
		CarNumber3 = carNumber3;
		CarNumber4 = carNumber4;
		UserInnerId = userInnerId;
		CarTypeInnerId = carTypeInnerId;
		StartTime = startTime;
		EndTime = endTime;
		Remark = remark;
		DelFlag = delFlag;
		LFInnerId = lFInnerId;
	}
	
	public Integer getParkInfo() {
		return ParkInfo;
	}
	public void setParkInfo(Integer parkInfo) {
		ParkInfo = parkInfo;
	}
	public Integer getParkingNum() {
		return ParkingNum;
	}
	public void setParkingNum(Integer parkingNum) {
		ParkingNum = parkingNum;
	}
	public String getCarNumber1() {
		return CarNumber1;
	}
	public void setCarNumber1(String carNumber1) {
		CarNumber1 = carNumber1;
	}
	public String getCarNumber2() {
		return CarNumber2;
	}
	public void setCarNumber2(String carNumber2) {
		CarNumber2 = carNumber2;
	}
	public String getCarNumber3() {
		return CarNumber3;
	}
	public void setCarNumber3(String carNumber3) {
		CarNumber3 = carNumber3;
	}
	public String getCarNumber4() {
		return CarNumber4;
	}
	public void setCarNumber4(String carNumber4) {
		CarNumber4 = carNumber4;
	}
	public Integer getCarInfoInnerId() {
		return CarInfoInnerId;
	}
	public void setCarInfoInnerId(Integer carInfoInnerId) {
		CarInfoInnerId = carInfoInnerId;
	}
	public String getCarNumber() {
		return CarNumber;
	}
	public void setCarNumber(String carNumber) {
		CarNumber = carNumber;
	}
	public Integer getUserInnerId() {
		return UserInnerId;
	}
	public void setUserInnerId(Integer userInnerId) {
		UserInnerId = userInnerId;
	}
	public Integer getCarTypeInnerId() {
		return CarTypeInnerId;
	}
	public void setCarTypeInnerId(Integer carTypeInnerId) {
		CarTypeInnerId = carTypeInnerId;
	}
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	public String getEndTime() {
		return EndTime;
	}
	public void setEndTime(String endTime) {
		EndTime = endTime;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public Integer getDelFlag() {
		return DelFlag;
	}
	public void setDelFlag(Integer delFlag) {
		DelFlag = delFlag;
	}
	public String getLFInnerId() {
		return LFInnerId;
	}
	public void setLFInnerId(String lFInnerId) {
		LFInnerId = lFInnerId;
	}
	@Override
	public String toString() {
		return "CarInfo [CarInfoInnerId=" + CarInfoInnerId + ", CarNumber=" + CarNumber + ", CarNumber1=" + CarNumber1
				+ ", CarNumber2=" + CarNumber2 + ", CarNumber3=" + CarNumber3 + ", CarNumber4=" + CarNumber4
				+ ", UserInnerId=" + UserInnerId + ", CarTypeInnerId=" + CarTypeInnerId + ", StartTime=" + StartTime
				+ ", EndTime=" + EndTime + ", Remark=" + Remark + ", DelFlag=" + DelFlag + ", LFInnerId=" + LFInnerId
				+ "]";
	}

	
}
