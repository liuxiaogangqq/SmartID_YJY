package com.dhsr.smartid.xiaofeiguanli.domain;

public class MerchantDiscount {
	private Integer MerchantDiscountInnerId;
	private String MerchantDiscountId;
	private String MerchantDiscountName;
	private Integer MerchantInnerId;
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

	public MerchantDiscount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MerchantDiscount(Integer merchantDiscountInnerId, String merchantDiscountId, String merchantDiscountName, Integer merchantInnerId, Integer proportion, String userTypeList, String beginTime, String endTime, Integer delFlag, String remark, String standbyA, String standbyB, String standbyC, String standbyD) {
		super();
		MerchantDiscountInnerId = merchantDiscountInnerId;
		MerchantDiscountId = merchantDiscountId;
		MerchantDiscountName = merchantDiscountName;
		MerchantInnerId = merchantInnerId;
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

	public Integer getMerchantDiscountInnerId() {
		return MerchantDiscountInnerId;
	}

	public void setMerchantDiscountInnerId(Integer merchantDiscountInnerId) {
		MerchantDiscountInnerId = merchantDiscountInnerId;
	}

	public String getMerchantDiscountId() {
		return MerchantDiscountId;
	}

	public void setMerchantDiscountId(String merchantDiscountId) {
		MerchantDiscountId = merchantDiscountId;
	}

	public String getMerchantDiscountName() {
		return MerchantDiscountName;
	}

	public void setMerchantDiscountName(String merchantDiscountName) {
		MerchantDiscountName = merchantDiscountName;
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
		return "MerchantDiscount [MerchantDiscountInnerId=" + MerchantDiscountInnerId + ", MerchantDiscountId=" + MerchantDiscountId + ", MerchantDiscountName=" + MerchantDiscountName + ", MerchantInnerId=" + MerchantInnerId + ", Proportion=" + Proportion + ", UserTypeList=" + UserTypeList + ", BeginTime=" + BeginTime + ", EndTime=" + EndTime + ", DelFlag=" + DelFlag + ", Remark=" + Remark + ", StandbyA=" + StandbyA + ", StandbyB=" + StandbyB + ", StandbyC=" + StandbyC + ", StandbyD=" + StandbyD + "]";
	}

}
