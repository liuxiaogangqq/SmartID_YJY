package com.dhsr.smartid.tingchexitong.domain;

public class VisChargeRecordInfo {
    private Integer ChargeRecordInnerId;
	private String RecDateTime;
	private String CarNumber;
	private String InTime;
	private String OutTime;
	private Integer Money;
	private Integer CarType;
	private String CarTypeName;
	private Integer ChargeState;
	private String OrderNo;
	private String StandbyA;
	private String ChargeTime;
	private Integer ChargeType;
	private String StandbyB;
	private Integer VisRecordInnerId;
	private String VisitorName;
	private String VisitorIDCode;
	private String VisitorCompany;
	public VisChargeRecordInfo() {
		super();
	}
	public VisChargeRecordInfo(Integer chargeRecordInnerId, String recDateTime, String carNumber, String inTime,
			String outTime, Integer money, Integer carType, String carTypeName, Integer chargeState, String orderNo,
			String standbyA, String chargeTime, Integer chargeType, String standbyB, Integer visRecordInnerId,
			String visitorName, String visitorIDCode, String visitorCompany) {
		super();
		ChargeRecordInnerId = chargeRecordInnerId;
		RecDateTime = recDateTime;
		CarNumber = carNumber;
		InTime = inTime;
		OutTime = outTime;
		Money = money;
		CarType = carType;
		CarTypeName = carTypeName;
		ChargeState = chargeState;
		OrderNo = orderNo;
		StandbyA = standbyA;
		ChargeTime = chargeTime;
		ChargeType = chargeType;
		StandbyB = standbyB;
		VisRecordInnerId = visRecordInnerId;
		VisitorName = visitorName;
		VisitorIDCode = visitorIDCode;
		VisitorCompany = visitorCompany;
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
	public String getCarTypeName() {
		return CarTypeName;
	}
	public void setCarTypeName(String carTypeName) {
		CarTypeName = carTypeName;
	}
	public Integer getChargeState() {
		return ChargeState;
	}
	public void setChargeState(Integer chargeState) {
		ChargeState = chargeState;
	}
	public String getOrderNo() {
		return OrderNo;
	}
	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}
	public String getStandbyA() {
		return StandbyA;
	}
	public void setStandbyA(String standbyA) {
		StandbyA = standbyA;
	}
	public String getChargeTime() {
		return ChargeTime;
	}
	public void setChargeTime(String chargeTime) {
		ChargeTime = chargeTime;
	}
	public Integer getChargeType() {
		return ChargeType;
	}
	public void setChargeType(Integer chargeType) {
		ChargeType = chargeType;
	}
	public String getStandbyB() {
		return StandbyB;
	}
	public void setStandbyB(String standbyB) {
		StandbyB = standbyB;
	}
	public Integer getVisRecordInnerId() {
		return VisRecordInnerId;
	}
	public void setVisRecordInnerId(Integer visRecordInnerId) {
		VisRecordInnerId = visRecordInnerId;
	}
	public String getVisitorName() {
		return VisitorName;
	}
	public void setVisitorName(String visitorName) {
		VisitorName = visitorName;
	}
	public String getVisitorIDCode() {
		return VisitorIDCode;
	}
	public void setVisitorIDCode(String visitorIDCode) {
		VisitorIDCode = visitorIDCode;
	}
	public String getVisitorCompany() {
		return VisitorCompany;
	}
	public void setVisitorCompany(String visitorCompany) {
		VisitorCompany = visitorCompany;
	}
	@Override
	public String toString() {
		return "VisChargeRecordInfo [ChargeRecordInnerId=" + ChargeRecordInnerId + ", RecDateTime=" + RecDateTime
				+ ", CarNumber=" + CarNumber + ", InTime=" + InTime + ", OutTime=" + OutTime + ", Money=" + Money
				+ ", CarType=" + CarType + ", CarTypeName=" + CarTypeName + ", ChargeState=" + ChargeState
				+ ", OrderNo=" + OrderNo + ", StandbyA=" + StandbyA + ", ChargeTime=" + ChargeTime + ", ChargeType="
				+ ChargeType + ", StandbyB=" + StandbyB + ", VisRecordInnerId=" + VisRecordInnerId + ", VisitorName="
				+ VisitorName + ", VisitorIDCode=" + VisitorIDCode + ", VisitorCompany=" + VisitorCompany + "]";
	}
	
}
