package com.dhsr.smartid.peizhiguanli.domain;

import java.io.Serializable;

public class App implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer AppInnerId;
	private String AppId;
	private String AppName;
	private Integer AppTypeInnerId;
	private Integer AreaInnerId;
	private String Remark;
	private Integer DelFlag;
	private String StandbyA;
	private String StandbyB;
	private String StandbyC;
	private String StandbyD;

	public App() {
		super();
		// TODO Auto-generated constructor stub
	}

	public App(Integer appInnerId, String appId, String appName, Integer appTypeInnerId, Integer areaInnerId, String remark, Integer delFlag, String standbyA, String standbyB, String standbyC, String standbyD) {
		super();
		AppInnerId = appInnerId;
		AppId = appId;
		AppName = appName;
		AppTypeInnerId = appTypeInnerId;
		AreaInnerId = areaInnerId;
		Remark = remark;
		DelFlag = delFlag;
		StandbyA = standbyA;
		StandbyB = standbyB;
		StandbyC = standbyC;
		StandbyD = standbyD;
	}

	public Integer getAppInnerId() {
		return AppInnerId;
	}

	public void setAppInnerId(Integer appInnerId) {
		AppInnerId = appInnerId;
	}

	public String getAppId() {
		return AppId;
	}

	public void setAppId(String appId) {
		AppId = appId;
	}

	public String getAppName() {
		return AppName;
	}

	public void setAppName(String appName) {
		AppName = appName;
	}

	public Integer getAppTypeInnerId() {
		return AppTypeInnerId;
	}

	public void setAppTypeInnerId(Integer appTypeInnerId) {
		AppTypeInnerId = appTypeInnerId;
	}

	public Integer getAreaInnerId() {
		return AreaInnerId;
	}

	public void setAreaInnerId(Integer areaInnerId) {
		AreaInnerId = areaInnerId;
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

	public String getStandbyC() {
		return StandbyC;
	}

	public void setStandbyC(String standbyC) {
		StandbyC = standbyC;
	}

	public String getStandbyD() {
		return StandbyD;
	}

	public void setStandbyD(String standbyD) {
		StandbyD = standbyD;
	}

	@Override
	public String toString() {
		return "App [AppInnerId=" + AppInnerId + ", AppId=" + AppId + ", AppName=" + AppName + ", AppTypeInnerId=" + AppTypeInnerId + ", AreaInnerId=" + AreaInnerId + ", Remark=" + Remark + ", DelFlag=" + DelFlag + ", StandbyA=" + StandbyA + ", StandbyB=" + StandbyB + ", StandbyC=" + StandbyC + ", StandbyD=" + StandbyD + "]";
	}

}
