package com.dhsr.smartid.tingchexitong.domain;

import java.io.Serializable;

public class CarType implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer CarTypeInnerId;
	private String CarTypeId;
	private String CarTypeName;
	private String Remark;
	private Integer DelFlag;
	public CarType() {
		super();
	}
	public CarType(Integer carTypeInnerId, String carTypeId, String carTypeName, String remark, Integer delFlag) {
		super();
		CarTypeInnerId = carTypeInnerId;
		CarTypeId = carTypeId;
		CarTypeName = carTypeName;
		Remark = remark;
		DelFlag = delFlag;
	}
	public Integer getCarTypeInnerId() {
		return CarTypeInnerId;
	}
	public void setCarTypeInnerId(Integer carTypeInnerId) {
		CarTypeInnerId = carTypeInnerId;
	}
	public String getCarTypeId() {
		return CarTypeId;
	}
	public void setCarTypeId(String carTypeId) {
		CarTypeId = carTypeId;
	}
	public String getCarTypeName() {
		return CarTypeName;
	}
	public void setCarTypeName(String carTypeName) {
		CarTypeName = carTypeName;
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
	@Override
	public String toString() {
		return "CarType [CarTypeInnerId=" + CarTypeInnerId + ", CarTypeId=" + CarTypeId + ", CarTypeName=" + CarTypeName
				+ ", Remark=" + Remark + ", DelFlag=" + DelFlag + "]";
	}
	
}
