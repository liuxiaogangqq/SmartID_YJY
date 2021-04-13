package com.dhsr.smartid.tingchexitong.domain;

public class ChargeRecord {
    private Integer ChargeRecordInnerId;
	private String RecDateTime;
	private String CarNumber;
	private String InTime;
	private String OutTime;
	private Integer Money;
	private Integer CarType;
	private String StandbyA;
	private String StandbyB;
	private Integer ChargeState;
	private Integer VisRecordInnerId;
	private Integer ChargeType;
	private String ChargeTime;
	private String OrderNo;
	private String Name;
	private String DepName;
	public ChargeRecord() {
		super();
	}
	

	public ChargeRecord(Integer chargeRecordInnerId, String recDateTime, String carNumber, String inTime,
			String outTime, Integer money, Integer carType, String standbyA, String standbyB, Integer chargeState,
			Integer visRecordInnerId, Integer chargeType, String chargeTime, String orderNo, String name,
			String depName) {
		super();
		ChargeRecordInnerId = chargeRecordInnerId;
		RecDateTime = recDateTime;
		CarNumber = carNumber;
		InTime = inTime;
		OutTime = outTime;
		Money = money;
		CarType = carType;
		StandbyA = standbyA;
		StandbyB = standbyB;
		ChargeState = chargeState;
		VisRecordInnerId = visRecordInnerId;
		ChargeType = chargeType;
		ChargeTime = chargeTime;
		OrderNo = orderNo;
		Name = name;
		DepName = depName;
	}

	
	public String getName() {
		return Name;
	}


	public void setName(String name) {
		Name = name;
	}


	public String getDepName() {
		return DepName;
	}


	public void setDepName(String depName) {
		DepName = depName;
	}


	public String getOrderNo() {
		return OrderNo;
	}
	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
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
	public Integer getVisRecordInnerId() {
		return VisRecordInnerId;
	}
	public void setVisRecordInnerId(Integer visRecordInnerId) {
		VisRecordInnerId = visRecordInnerId;
	}
	public Integer getChargeState() {
		return ChargeState;
	}
	public void setChargeState(Integer chargeState) {
		ChargeState = chargeState;
	}
	public Integer getChargeRecordInnerId() {
		return ChargeRecordInnerId;
	}
	public void setChargeRecordInnerId(Integer chargeRecordInnerId) {
		ChargeRecordInnerId = chargeRecordInnerId;
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
	public String getStandbyA() {
		return StandbyA;
	}
	public void setStandbyA(String standbyA) {
		StandbyA = standbyA;
	}
	public String getStandbyB() {
		return StandbyB;
	}
	public void setStandbyB(String standbyB) {
		StandbyB = standbyB;
	}


	@Override
	public String toString() {
		return "ChargeRecord [ChargeRecordInnerId=" + ChargeRecordInnerId + ", RecDateTime=" + RecDateTime
				+ ", CarNumber=" + CarNumber + ", InTime=" + InTime + ", OutTime=" + OutTime + ", Money=" + Money
				+ ", CarType=" + CarType + ", StandbyA=" + StandbyA + ", StandbyB=" + StandbyB + ", ChargeState="
				+ ChargeState + ", VisRecordInnerId=" + VisRecordInnerId + ", ChargeType=" + ChargeType
				+ ", ChargeTime=" + ChargeTime + ", OrderNo=" + OrderNo + ", Name=" + Name + ", DepName=" + DepName
				+ "]";
	}

	
}
