package com.dhsr.smartid.renshiyewu.domain;

public class UserPer {
	private Integer UserPerInnerId;
	private Integer UserInnerId;
	private Integer AppInnerId;
	private Integer AreaInnerId;
	private String StartTime;
	private String EndTime;
	private String Remark;
	private String StandbyA;
	private String StandbyB;

	public UserPer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserPer(Integer userPerInnerId, Integer userInnerId, Integer appInnerId, Integer areaInnerId, String startTime, String endTime, String remark, String standbyA, String standbyB) {
		super();
		UserPerInnerId = userPerInnerId;
		UserInnerId = userInnerId;
		AppInnerId = appInnerId;
		AreaInnerId = areaInnerId;
		StartTime = startTime;
		EndTime = endTime;
		Remark = remark;
		StandbyA = standbyA;
		StandbyB = standbyB;
	}

	public Integer getUserPerInnerId() {
		return UserPerInnerId;
	}

	public void setUserPerInnerId(Integer userPerInnerId) {
		UserPerInnerId = userPerInnerId;
	}

	public Integer getUserInnerId() {
		return UserInnerId;
	}

	public void setUserInnerId(Integer userInnerId) {
		UserInnerId = userInnerId;
	}

	public Integer getAppInnerId() {
		return AppInnerId;
	}

	public void setAppInnerId(Integer appInnerId) {
		AppInnerId = appInnerId;
	}

	public Integer getAreaInnerId() {
		return AreaInnerId;
	}

	public void setAreaInnerId(Integer areaInnerId) {
		AreaInnerId = areaInnerId;
	}

	public String getStartTime() {
		return StartTime;
	}

	public void setStartTime(String startTime) {
		StartTime = startTime;
	}

	public String getEndTime() {
		return EndTime;
	}

	public void setEndTime(String endTime) {
		EndTime = endTime;
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

	@Override
	public String toString() {
		return "UserPer [UserPerInnerId=" + UserPerInnerId + ", UserInnerId=" + UserInnerId + ", AppInnerId=" + AppInnerId + ", AreaInnerId=" + AreaInnerId + ", StartTime=" + StartTime + ", EndTime=" + EndTime + ", Remark=" + Remark + ", StandbyA=" + StandbyA + ", StandbyB=" + StandbyB + "]";
	}

}
