package com.dhsr.smartid.xiaofeiguanli.domain;

public class DiscountConsumption {
	private Integer UserInnerId;
	private Integer MerchantInnerId;
	private Integer Proportion;
	private String Remark;
	private String StandbyA;
	private String StandbyB;
	private String StandbyC;
	private String StandbyD;

	public DiscountConsumption() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DiscountConsumption(Integer userInnerId, Integer merchantInnerId, Integer proportion, String remark, String standbyA, String standbyB, String standbyC, String standbyD) {
		super();
		UserInnerId = userInnerId;
		MerchantInnerId = merchantInnerId;
		Proportion = proportion;
		Remark = remark;
		StandbyA = standbyA;
		StandbyB = standbyB;
		StandbyC = standbyC;
		StandbyD = standbyD;
	}

	public Integer getUserInnerId() {
		return UserInnerId;
	}

	public void setUserInnerId(Integer userInnerId) {
		UserInnerId = userInnerId;
	}

	public Integer getMerchantInnerId() {
		return MerchantInnerId;
	}

	public void setMerchantInnerId(Integer merchantInnerId) {
		MerchantInnerId = merchantInnerId;
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
		return "DiscountConsumption [UserInnerId=" + UserInnerId + ", MerchantInnerId=" + MerchantInnerId + ", Proportion=" + Proportion + ", Remark=" + Remark + ", StandbyA=" + StandbyA + ", StandbyB=" + StandbyB + ", StandbyC=" + StandbyC + ", StandbyD=" + StandbyD + "]";
	}

}
