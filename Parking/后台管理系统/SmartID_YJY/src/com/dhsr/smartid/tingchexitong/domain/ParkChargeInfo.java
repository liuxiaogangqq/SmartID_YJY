package com.dhsr.smartid.tingchexitong.domain;

public class ParkChargeInfo {
    private Integer ChargeRecordInnerId;
	private Integer UserInnerId;
	private String VisitorName;
	private Integer UserTypeInnerId;
	private String UserName;
	private Integer DepartmentInnerId;
	private String RecDateTime;
	private String CarNumber;
	private String InTime;
	private String OutTime;
	private Integer Money;
	private Integer CarType;
	private Integer ChargeState;
	private Integer VisRecordInnerId;
	private Integer ChargeType;
	private String ChargeTime;
	private String DepartmentName;
	public ParkChargeInfo() {
		super();
	}
	public ParkChargeInfo(Integer chargeRecordInnerId, Integer userInnerId, String visitorName, Integer userTypeInnerId,
			String userName, Integer departmentInnerId, String recDateTime, String carNumber, String inTime,
			String outTime, Integer money, Integer carType, Integer chargeState, Integer visRecordInnerId,
			Integer chargeType, String chargeTime, String departmentName) {
		super();
		ChargeRecordInnerId = chargeRecordInnerId;
		UserInnerId = userInnerId;
		VisitorName = visitorName;
		UserTypeInnerId = userTypeInnerId;
		UserName = userName;
		DepartmentInnerId = departmentInnerId;
		RecDateTime = recDateTime;
		CarNumber = carNumber;
		InTime = inTime;
		OutTime = outTime;
		Money = money;
		CarType = carType;
		ChargeState = chargeState;
		VisRecordInnerId = visRecordInnerId;
		ChargeType = chargeType;
		ChargeTime = chargeTime;
		DepartmentName = departmentName;
	}
	public Integer getChargeRecordInnerId() {
		return ChargeRecordInnerId;
	}
	public void setChargeRecordInnerId(Integer chargeRecordInnerId) {
		ChargeRecordInnerId = chargeRecordInnerId;
	}
	public Integer getUserInnerId() {
		return UserInnerId;
	}
	public void setUserInnerId(Integer userInnerId) {
		UserInnerId = userInnerId;
	}
	public String getVisitorName() {
		return VisitorName;
	}
	public void setVisitorName(String visitorName) {
		VisitorName = visitorName;
	}
	public Integer getUserTypeInnerId() {
		return UserTypeInnerId;
	}
	public void setUserTypeInnerId(Integer userTypeInnerId) {
		UserTypeInnerId = userTypeInnerId;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public Integer getDepartmentInnerId() {
		return DepartmentInnerId;
	}
	public void setDepartmentInnerId(Integer departmentInnerId) {
		DepartmentInnerId = departmentInnerId;
	}
	public String getRecDateTime() {
		return RecDateTime;
	}
	public void setRecDateTime(String recDateTime) {
		RecDateTime = recDateTime;
	}
	public String getCarNumber() {
		return CarNumber;
	}
	public void setCarNumber(String carNumber) {
		CarNumber = carNumber;
	}
	public String getInTime() {
		return InTime;
	}
	public void setInTime(String inTime) {
		InTime = inTime;
	}
	public String getOutTime() {
		return OutTime;
	}
	public void setOutTime(String outTime) {
		OutTime = outTime;
	}
	public Integer getMoney() {
		return Money;
	}
	public void setMoney(Integer money) {
		Money = money;
	}
	public Integer getCarType() {
		return CarType;
	}
	public void setCarType(Integer carType) {
		CarType = carType;
	}
	public Integer getChargeState() {
		return ChargeState;
	}
	public void setChargeState(Integer chargeState) {
		ChargeState = chargeState;
	}
	public Integer getVisRecordInnerId() {
		return VisRecordInnerId;
	}
	public void setVisRecordInnerId(Integer visRecordInnerId) {
		VisRecordInnerId = visRecordInnerId;
	}
	public Integer getChargeType() {
		return ChargeType;
	}
	public void setChargeType(Integer chargeType) {
		ChargeType = chargeType;
	}
	public String getChargeTime() {
		return ChargeTime;
	}
	public void setChargeTime(String chargeTime) {
		ChargeTime = chargeTime;
	}
	public String getDepartmentName() {
		return DepartmentName;
	}
	public void setDepartmentName(String departmentName) {
		DepartmentName = departmentName;
	}
	@Override
	public String toString() {
		return "ParkChargeInfo [ChargeRecordInnerId=" + ChargeRecordInnerId + ", UserInnerId=" + UserInnerId
				+ ", VisitorName=" + VisitorName + ", UserTypeInnerId=" + UserTypeInnerId + ", UserName=" + UserName
				+ ", DepartmentInnerId=" + DepartmentInnerId + ", RecDateTime=" + RecDateTime + ", CarNumber="
				+ CarNumber + ", InTime=" + InTime + ", OutTime=" + OutTime + ", Money=" + Money + ", CarType="
				+ CarType + ", ChargeState=" + ChargeState + ", VisRecordInnerId=" + VisRecordInnerId + ", ChargeType="
				+ ChargeType + ", ChargeTime=" + ChargeTime + ", DepartmentName=" + DepartmentName + "]";
	}
	
}
