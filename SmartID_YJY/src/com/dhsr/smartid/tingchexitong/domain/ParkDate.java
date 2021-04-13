package com.dhsr.smartid.tingchexitong.domain;

public class ParkDate {
	private Integer ParkType;
	private Integer ParkNum;
	private Integer ParkResidue;
	private String ParkDate;
	public ParkDate() {
		super();
	}
	public ParkDate(Integer parkType, Integer parkNum, Integer parkResidue, String parkDate) {
		super();
		ParkType = parkType;
		ParkNum = parkNum;
		ParkResidue = parkResidue;
		ParkDate = parkDate;
	}
	public Integer getParkType() {
		return ParkType;
	}
	public void setParkType(Integer parkType) {
		ParkType = parkType;
	}
	public Integer getParkNum() {
		return ParkNum;
	}
	public void setParkNum(Integer parkNum) {
		ParkNum = parkNum;
	}
	public Integer getParkResidue() {
		return ParkResidue;
	}
	public void setParkResidue(Integer parkResidue) {
		ParkResidue = parkResidue;
	}
	public String getParkDate() {
		return ParkDate;
	}
	public void setParkDate(String parkDate) {
		ParkDate = parkDate;
	}
	@Override
	public String toString() {
		return "ParkDate [ParkType=" + ParkType + ", ParkNum=" + ParkNum + ", ParkResidue=" + ParkResidue
				+ ", ParkDate=" + ParkDate + "]";
	}
	
}
