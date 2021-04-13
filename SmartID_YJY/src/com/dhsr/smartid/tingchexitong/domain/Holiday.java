package com.dhsr.smartid.tingchexitong.domain;

public class Holiday {
    private Integer HolidayInnerId;
	private String HolidayName;
	private String StartDate;
	private String EndDate;
	private Integer ISFills;
	private String Remark;
	private Integer DelFlag;
	private String StandbyA;
	private String StandbyB;
	public Holiday() {
		super();
	}
	public Holiday(Integer holidayInnerId, String holidayName, String startDate, String endDate, Integer iSFills,
			String remark, Integer delFlag, String standbyA, String standbyB) {
		super();
		HolidayInnerId = holidayInnerId;
		HolidayName = holidayName;
		StartDate = startDate;
		EndDate = endDate;
		ISFills = iSFills;
		Remark = remark;
		DelFlag = delFlag;
		StandbyA = standbyA;
		StandbyB = standbyB;
	}
	public Integer getHolidayInnerId() {
		return HolidayInnerId;
	}
	public void setHolidayInnerId(Integer holidayInnerId) {
		HolidayInnerId = holidayInnerId;
	}
	public String getHolidayName() {
		return HolidayName;
	}
	public void setHolidayName(String holidayName) {
		HolidayName = holidayName;
	}
	public String getStartDate() {
		return StartDate;
	}
	public void setStartDate(String startDate) {
		StartDate = startDate;
	}
	public String getEndDate() {
		return EndDate;
	}
	public void setEndDate(String endDate) {
		EndDate = endDate;
	}
	public Integer getISFills() {
		return ISFills;
	}
	public void setISFills(Integer iSFills) {
		ISFills = iSFills;
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
		return "Holiday [HolidayInnerId=" + HolidayInnerId + ", HolidayName=" + HolidayName + ", StartDate=" + StartDate
				+ ", EndDate=" + EndDate + ", ISFills=" + ISFills + ", Remark=" + Remark + ", DelFlag=" + DelFlag
				+ ", StandbyA=" + StandbyA + ", StandbyB=" + StandbyB + "]";
	}
	
}
