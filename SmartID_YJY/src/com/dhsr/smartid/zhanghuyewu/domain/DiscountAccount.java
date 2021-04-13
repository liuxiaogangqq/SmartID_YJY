package com.dhsr.smartid.zhanghuyewu.domain;

public class DiscountAccount {
	private Integer UserTypeInnerId;
	private Integer Proportion;
	private String Remark;
	private String StandbyA;
	private String StandbyB;
	private String StandbyC;
	private String StandbyD;

	public DiscountAccount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DiscountAccount(Integer userTypeInnerId, Integer proportion, String remark, String standbyA, String standbyB, String standbyC, String standbyD) {
		super();
		UserTypeInnerId = userTypeInnerId;
		Proportion = proportion;
		Remark = remark;
		StandbyA = standbyA;
		StandbyB = standbyB;
		StandbyC = standbyC;
		StandbyD = standbyD;
	}

	public Integer getUserTypeInnerId() {
		return UserTypeInnerId;
	}

	public void setUserTypeInnerId(Integer userTypeInnerId) {
		UserTypeInnerId = userTypeInnerId;
	}

	public Integer getProportion() {
		return Proportion;
	}

	public void setProportion(Integer proportion) {
		Proportion = proportion;
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
		return "DiscountAccount [UserTypeInnerId=" + UserTypeInnerId + ", Proportion=" + Proportion + ", Remark=" + Remark + ", StandbyA=" + StandbyA + ", StandbyB=" + StandbyB + ", StandbyC=" + StandbyC + ", StandbyD=" + StandbyD + "]";
	}

}
