package com.dhsr.smartid.tingchexitong.domain;

import java.io.Serializable;

public class PassRules  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer PassRulesInnerId;
	private String PassRulesId;
	private String PassRulesName;
	private String StartTime;
	private String EndTime;
	private String Remark;
	private Integer DelFlag;
	private Integer FreeTime;
	private Integer Money;
	private Integer CarTypeInnerId;
	public PassRules() {
		super();
	}
	
	public PassRules(Integer passRulesInnerId, String passRulesId, String passRulesName, String startTime,
			String endTime, String remark, Integer delFlag, Integer freeTime, Integer money, Integer carTypeInnerId) {
		super();
		PassRulesInnerId = passRulesInnerId;
		PassRulesId = passRulesId;
		PassRulesName = passRulesName;
		StartTime = startTime;
		EndTime = endTime;
		Remark = remark;
		DelFlag = delFlag;
		FreeTime = freeTime;
		Money = money;
		CarTypeInnerId = carTypeInnerId;
	}

	public Integer getFreeTime() {
		return FreeTime;
	}
	public void setFreeTime(Integer freeTime) {
		FreeTime = freeTime;
	}
	public Integer getMoney() {
		return Money;
	}
	public void setMoney(Integer money) {
		Money = money;
	}
	public Integer getCarTypeInnerId() {
		return CarTypeInnerId;
	}
	public void setCarTypeInnerId(Integer carTypeInnerId) {
		CarTypeInnerId = carTypeInnerId;
	}
	public Integer getPassRulesInnerId() {
		return PassRulesInnerId;
	}
	public void setPassRulesInnerId(Integer passRulesInnerId) {
		PassRulesInnerId = passRulesInnerId;
	}
	public String getPassRulesId() {
		return PassRulesId;
	}
	public void setPassRulesId(String passRulesId) {
		PassRulesId = passRulesId;
	}
	public String getPassRulesName() {
		return PassRulesName;
	}
	public void setPassRulesName(String passRulesName) {
		PassRulesName = passRulesName;
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
	public Integer getDelFlag() {
		return DelFlag;
	}
	public void setDelFlag(Integer delFlag) {
		DelFlag = delFlag;
	}

	@Override
	public String toString() {
		return "PassRules [PassRulesInnerId=" + PassRulesInnerId + ", PassRulesId=" + PassRulesId + ", PassRulesName="
				+ PassRulesName + ", StartTime=" + StartTime + ", EndTime=" + EndTime + ", Remark=" + Remark
				+ ", DelFlag=" + DelFlag + ", FreeTime=" + FreeTime + ", Money=" + Money + ", CarTypeInnerId="
				+ CarTypeInnerId + "]";
	}
	
}
