package com.dhsr.smartid.zhanghuyewu.domain;

public class AccountDiscount {
	private Integer AccountDiscountInnerId;
	private String AccountDiscountId;
	private String AccountDiscountName;
	private Integer Proportion;
	private String UserTypeList;
	private String BeginTime;
	private String EndTime;
	private Integer DelFlag;
	private String Remark;
	private String StandbyA;
	private String StandbyB;
	private String StandbyC;
	private String StandbyD;

	public AccountDiscount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AccountDiscount(Integer accountDiscountInnerId, String accountDiscountId, String accountDiscountName, Integer proportion, String userTypeList, String beginTime, String endTime, Integer delFlag, String remark, String standbyA, String standbyB, String standbyC, String standbyD) {
		super();
		AccountDiscountInnerId = accountDiscountInnerId;
		AccountDiscountId = accountDiscountId;
		AccountDiscountName = accountDiscountName;
		Proportion = proportion;
		UserTypeList = userTypeList;
		BeginTime = beginTime;
		EndTime = endTime;
		DelFlag = delFlag;
		Remark = remark;
		StandbyA = standbyA;
		StandbyB = standbyB;
		StandbyC = standbyC;
		StandbyD = standbyD;
	}

	public Integer getAccountDiscountInnerId() {
		return AccountDiscountInnerId;
	}

	public void setAccountDiscountInnerId(Integer accountDiscountInnerId) {
		AccountDiscountInnerId = accountDiscountInnerId;
	}

	public String getAccountDiscountId() {
		return AccountDiscountId;
	}

	public void setAccountDiscountId(String accountDiscountId) {
		AccountDiscountId = accountDiscountId;
	}

	public String getAccountDiscountName() {
		return AccountDiscountName;
	}

	public void setAccountDiscountName(String accountDiscountName) {
		AccountDiscountName = accountDiscountName;
	}

	public Integer getProportion() {
		return Proportion;
	}

	public void setProportion(Integer proportion) {
		Proportion = proportion;
	}

	public String getUserTypeList() {
		return UserTypeList;
	}

	public void setUserTypeList(String userTypeList) {
		UserTypeList = userTypeList;
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
		return "AccountDiscount [AccountDiscountInnerId=" + AccountDiscountInnerId + ", AccountDiscountId=" + AccountDiscountId + ", AccountDiscountName=" + AccountDiscountName + ", Proportion=" + Proportion + ", UserTypeList=" + UserTypeList + ", BeginTime=" + BeginTime + ", EndTime=" + EndTime + ", DelFlag=" + DelFlag + ", Remark=" + Remark + ", StandbyA=" + StandbyA + ", StandbyB=" + StandbyB + ", StandbyC=" + StandbyC + ", StandbyD=" + StandbyD + "]";
	}

}
