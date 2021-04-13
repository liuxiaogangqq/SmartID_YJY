package com.dhsr.smartid.xiaofeiguanli.domain;

import java.io.Serializable;

public class ConType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer ConTypeInnerId;
	private String ConTypeId;
	private String ConTypeName;
	private String BeginTime;
	private String EndTime;
	private Integer DelFlag;
	private String Remark;
	private String StandbyA;
	private String StandbyB;

	public ConType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConType(Integer conTypeInnerId, String conTypeId, String conTypeName, String beginTime, String endTime,
			Integer delFlag, String remark, String standbyA, String standbyB) {
		super();
		ConTypeInnerId = conTypeInnerId;
		ConTypeId = conTypeId;
		ConTypeName = conTypeName;
		BeginTime = beginTime;
		EndTime = endTime;
		DelFlag = delFlag;
		Remark = remark;
		StandbyA = standbyA;
		StandbyB = standbyB;
	}

	public Integer getConTypeInnerId() {
		return ConTypeInnerId;
	}

	public void setConTypeInnerId(Integer conTypeInnerId) {
		ConTypeInnerId = conTypeInnerId;
	}

	public String getConTypeId() {
		return ConTypeId;
	}

	public void setConTypeId(String conTypeId) {
		ConTypeId = conTypeId;
	}

	public String getConTypeName() {
		return ConTypeName;
	}

	public void setConTypeName(String conTypeName) {
		ConTypeName = conTypeName;
	}

	public String getBeginTime() {
		return BeginTime;
	}

	public void setBeginTime(String beginTime) {
		BeginTime = beginTime;
	}

	public String getEndTime() {
		return EndTime;
	}

	public void setEndTime(String endTime) {
		EndTime = endTime;
	}

	public Integer getDelFlag() {
		return DelFlag;
	}

	public void setDelFlag(Integer delFlag) {
		DelFlag = delFlag;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ConType [ConTypeInnerId=" + ConTypeInnerId + ", ConTypeId=" + ConTypeId + ", ConTypeName=" + ConTypeName
				+ ", BeginTime=" + BeginTime + ", EndTime=" + EndTime + ", DelFlag=" + DelFlag + ", Remark=" + Remark
				+ ", StandbyA=" + StandbyA + ", StandbyB=" + StandbyB + "]";
	}


}
