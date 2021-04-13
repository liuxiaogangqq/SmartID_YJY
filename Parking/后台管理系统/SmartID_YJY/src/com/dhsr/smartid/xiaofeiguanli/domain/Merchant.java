package com.dhsr.smartid.xiaofeiguanli.domain;

import java.io.Serializable;

public class Merchant implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer MerchantInnerId;
	private String MerchantId;
	private String MerchantName;
	private Integer AppInnerId;
	private Integer AreaInnerId;
	private Integer MerchantType;
	private Integer DelFlag;
	private String Remark;
	private String StandbyA;
	private String StandbyB;
	private Integer Proportion;

	public Merchant() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Merchant(Integer merchantInnerId, String merchantId, String merchantName, Integer appInnerId,
			Integer areaInnerId, Integer merchantType, Integer delFlag, String remark, String standbyA, String standbyB,
			Integer proportion) {
		super();
		MerchantInnerId = merchantInnerId;
		MerchantId = merchantId;
		MerchantName = merchantName;
		AppInnerId = appInnerId;
		AreaInnerId = areaInnerId;
		MerchantType = merchantType;
		DelFlag = delFlag;
		Remark = remark;
		StandbyA = standbyA;
		StandbyB = standbyB;
		Proportion = proportion;
	}

	public Integer getMerchantInnerId() {
		return MerchantInnerId;
	}

	public void setMerchantInnerId(Integer merchantInnerId) {
		MerchantInnerId = merchantInnerId;
	}

	public String getMerchantId() {
		return MerchantId;
	}

	public void setMerchantId(String merchantId) {
		MerchantId = merchantId;
	}

	public String getMerchantName() {
		return MerchantName;
	}

	public void setMerchantName(String merchantName) {
		MerchantName = merchantName;
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

	public Integer getMerchantType() {
		return MerchantType;
	}

	public void setMerchantType(Integer merchantType) {
		MerchantType = merchantType;
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

	public Integer getProportion() {
		return Proportion;
	}

	public void setProportion(Integer proportion) {
		Proportion = proportion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Merchant [MerchantInnerId=" + MerchantInnerId + ", MerchantId=" + MerchantId + ", MerchantName="
				+ MerchantName + ", AppInnerId=" + AppInnerId + ", AreaInnerId=" + AreaInnerId + ", MerchantType="
				+ MerchantType + ", DelFlag=" + DelFlag + ", Remark=" + Remark + ", StandbyA=" + StandbyA
				+ ", StandbyB=" + StandbyB + ", Proportion=" + Proportion + "]";
	}


}
