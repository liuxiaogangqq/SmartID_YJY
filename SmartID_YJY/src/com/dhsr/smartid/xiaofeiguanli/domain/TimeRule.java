package com.dhsr.smartid.xiaofeiguanli.domain;

public class TimeRule {
	private Integer TimeRuleInnerId;
	private String TimeRuleId;
	private String TimeRuleName;
	private Integer MerchantInnerId;
	private Integer Minutes;
	private Integer Money;
	private Integer Intpart;
	private Integer Timeout;
	private Integer MinMoney;
	private Integer Freetime;
	private Integer DelFlag;
	private String Remark;
	private String StandbyA;
	private String StandbyB;
	private String StandbyC;
	private String StandbyD;

	public TimeRule() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TimeRule(Integer timeRuleInnerId, String timeRuleId, String timeRuleName, Integer merchantInnerId, Integer minutes, Integer money, Integer intpart, Integer timeout, Integer minMoney, Integer freetime, Integer delFlag, String remark, String standbyA, String standbyB, String standbyC, String standbyD) {
		super();
		TimeRuleInnerId = timeRuleInnerId;
		TimeRuleId = timeRuleId;
		TimeRuleName = timeRuleName;
		MerchantInnerId = merchantInnerId;
		Minutes = minutes;
		Money = money;
		Intpart = intpart;
		Timeout = timeout;
		MinMoney = minMoney;
		Freetime = freetime;
		DelFlag = delFlag;
		Remark = remark;
		StandbyA = standbyA;
		StandbyB = standbyB;
		StandbyC = standbyC;
		StandbyD = standbyD;
	}

	public Integer getTimeRuleInnerId() {
		return TimeRuleInnerId;
	}

	public void setTimeRuleInnerId(Integer timeRuleInnerId) {
		TimeRuleInnerId = timeRuleInnerId;
	}

	public String getTimeRuleId() {
		return TimeRuleId;
	}

	public void setTimeRuleId(String timeRuleId) {
		TimeRuleId = timeRuleId;
	}

	public String getTimeRuleName() {
		return TimeRuleName;
	}

	public void setTimeRuleName(String timeRuleName) {
		TimeRuleName = timeRuleName;
	}

	public Integer getMerchantInnerId() {
		return MerchantInnerId;
	}

	public void setMerchantInnerId(Integer merchantInnerId) {
		MerchantInnerId = merchantInnerId;
	}

	public Integer getMinutes() {
		return Minutes;
	}

	public void setMinutes(Integer minutes) {
		Minutes = minutes;
	}

	public Integer getMoney() {
		return Money;
	}

	public void setMoney(Integer money) {
		Money = money;
	}

	public Integer getIntpart() {
		return Intpart;
	}

	public void setIntpart(Integer intpart) {
		Intpart = intpart;
	}

	public Integer getTimeout() {
		return Timeout;
	}

	public void setTimeout(Integer timeout) {
		Timeout = timeout;
	}

	public Integer getMinMoney() {
		return MinMoney;
	}

	public void setMinMoney(Integer minMoney) {
		MinMoney = minMoney;
	}

	public Integer getFreetime() {
		return Freetime;
	}

	public void setFreetime(Integer freetime) {
		Freetime = freetime;
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
		return "TimeRule [TimeRuleInnerId=" + TimeRuleInnerId + ", TimeRuleId=" + TimeRuleId + ", TimeRuleName=" + TimeRuleName + ", MerchantInnerId=" + MerchantInnerId + ", Minutes=" + Minutes + ", Money=" + Money + ", Intpart=" + Intpart + ", Timeout=" + Timeout + ", MinMoney=" + MinMoney + ", Freetime=" + Freetime + ", DelFlag=" + DelFlag + ", Remark=" + Remark + ", StandbyA=" + StandbyA + ", StandbyB=" + StandbyB + ", StandbyC=" + StandbyC + ", StandbyD=" + StandbyD + "]";
	}

}
