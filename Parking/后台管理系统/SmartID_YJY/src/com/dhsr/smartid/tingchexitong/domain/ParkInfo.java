package com.dhsr.smartid.tingchexitong.domain;

import java.io.Serializable;

public class ParkInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer ParkInfoInnerId;
	private String ParkInfoId;
	private String ParkInfoName;
	private String Remark;
	private Integer DelFlag;
	private Integer CarsNumber;
	private Integer CurrentCarNumber;
	private Integer VisNumber;
	private Integer CurrentVisNumber;
	
	
	public ParkInfo() {
		super();
	}


	public ParkInfo(Integer parkInfoInnerId, String parkInfoId, String parkInfoName, String remark, Integer delFlag,
			Integer carsNumber, Integer currentCarNumber, Integer visNumber, Integer currentVisNumber) {
		super();
		ParkInfoInnerId = parkInfoInnerId;
		ParkInfoId = parkInfoId;
		ParkInfoName = parkInfoName;
		Remark = remark;
		DelFlag = delFlag;
		CarsNumber = carsNumber;
		CurrentCarNumber = currentCarNumber;
		VisNumber = visNumber;
		CurrentVisNumber = currentVisNumber;
	}


	public Integer getParkInfoInnerId() {
		return ParkInfoInnerId;
	}


	public void setParkInfoInnerId(Integer parkInfoInnerId) {
		ParkInfoInnerId = parkInfoInnerId;
	}


	public String getParkInfoId() {
		return ParkInfoId;
	}


	public void setParkInfoId(String parkInfoId) {
		ParkInfoId = parkInfoId;
	}


	public String getParkInfoName() {
		return ParkInfoName;
	}


	public void setParkInfoName(String parkInfoName) {
		ParkInfoName = parkInfoName;
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


	public Integer getCarsNumber() {
		return CarsNumber;
	}


	public void setCarsNumber(Integer carsNumber) {
		CarsNumber = carsNumber;
	}


	public Integer getCurrentCarNumber() {
		return CurrentCarNumber;
	}


	public void setCurrentCarNumber(Integer currentCarNumber) {
		CurrentCarNumber = currentCarNumber;
	}


	public Integer getVisNumber() {
		return VisNumber;
	}


	public void setVisNumber(Integer visNumber) {
		VisNumber = visNumber;
	}


	public Integer getCurrentVisNumber() {
		return CurrentVisNumber;
	}


	public void setCurrentVisNumber(Integer currentVisNumber) {
		CurrentVisNumber = currentVisNumber;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "ParkInfo [ParkInfoInnerId=" + ParkInfoInnerId + ", ParkInfoId=" + ParkInfoId + ", ParkInfoName="
				+ ParkInfoName + ", Remark=" + Remark + ", DelFlag=" + DelFlag + ", CarsNumber=" + CarsNumber
				+ ", CurrentCarNumber=" + CurrentCarNumber + ", VisNumber=" + VisNumber + ", CurrentVisNumber="
				+ CurrentVisNumber + "]";
	}
	
}
