package com.dhsr.smartid.peizhiguanli.domain;

public class AppType {
	private Integer AppTypeInnerId;
	private String AppTypeId;
	private String AppTypeName;
	private String Remark;
	private Integer DelFlag;
	private String StandbyA;
	private String StandbyB;

	public AppType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AppType(Integer appTypeInnerId, String appTypeId, String appTypeName, String remark, Integer delFlag, String standbyA, String standbyB) {
		super();
		AppTypeInnerId = appTypeInnerId;
		AppTypeId = appTypeId;
		AppTypeName = appTypeName;
		Remark = remark;
		DelFlag = delFlag;
		StandbyA = standbyA;
		StandbyB = standbyB;
	}

	public Integer getAppTypeInnerId() {
		return AppTypeInnerId;
	}

	public void setAppTypeInnerId(Integer appTypeInnerId) {
		AppTypeInnerId = appTypeInnerId;
	}

	public String getAppTypeId() {
		return AppTypeId;
	}

	public void setAppTypeId(String appTypeId) {
		AppTypeId = appTypeId;
	}

	public String getAppTypeName() {
		return AppTypeName;
	}

	public void setAppTypeName(String appTypeName) {
		AppTypeName = appTypeName;
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
		return "AppType [AppTypeInnerId=" + AppTypeInnerId + ", AppTypeId=" + AppTypeId + ", AppTypeName=" + AppTypeName + ", Remark=" + Remark + ", DelFlag=" + DelFlag + ", StandbyA=" + StandbyA + ", StandbyB=" + StandbyB + "]";
	}

}
