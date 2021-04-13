package com.dhsr.smartid.peizhiguanli.domain;

import java.io.Serializable;

public class UserType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer UserTypeInnerId;
	private String UserTypeId;
	private String UserTypeName;
	private String AreaList;
	private String AppList;
	private String Remark;
	private Integer DelFlag;
	private String StandbyA;
	private String StandbyB;

	public UserType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserType(Integer userTypeInnerId, String userTypeId, String userTypeName, String areaList, String appList, String remark, Integer delFlag, String standbyA, String standbyB) {
		super();
		UserTypeInnerId = userTypeInnerId;
		UserTypeId = userTypeId;
		UserTypeName = userTypeName;
		AreaList = areaList;
		AppList = appList;
		Remark = remark;
		DelFlag = delFlag;
		StandbyA = standbyA;
		StandbyB = standbyB;
	}

	public Integer getUserTypeInnerId() {
		return UserTypeInnerId;
	}

	public void setUserTypeInnerId(Integer userTypeInnerId) {
		UserTypeInnerId = userTypeInnerId;
	}

	public String getUserTypeId() {
		return UserTypeId;
	}

	public void setUserTypeId(String userTypeId) {
		UserTypeId = userTypeId;
	}

	public String getUserTypeName() {
		return UserTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		UserTypeName = userTypeName;
	}

	public String getAreaList() {
		return AreaList;
	}

	public void setAreaList(String areaList) {
		AreaList = areaList;
	}

	public String getAppList() {
		return AppList;
	}

	public void setAppList(String appList) {
		AppList = appList;
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
		return "UserType [UserTypeInnerId=" + UserTypeInnerId + ", UserTypeId=" + UserTypeId + ", UserTypeName=" + UserTypeName + ", AreaList=" + AreaList + ", AppList=" + AppList + ", Remark=" + Remark + ", DelFlag=" + DelFlag + ", StandbyA=" + StandbyA + ", StandbyB=" + StandbyB + "]";
	}

}
