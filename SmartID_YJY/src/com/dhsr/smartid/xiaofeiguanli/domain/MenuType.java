package com.dhsr.smartid.xiaofeiguanli.domain;

import java.io.Serializable;

public class MenuType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer FMenuTypeInnerId;
	private String FMenuTypeId;
	private String FMenuTypeName;
	private Integer DelFlag;
	private Integer FMerchantInnerId;
	private Integer FEnableFlag;
	private String FRemark;
	private String FStandbyA;
	private String FStandbyB;
	private String FStandbyC;
	private String FStandbyD;
	public MenuType() {
		super();
	}
	public MenuType(Integer fMenuTypeInnerId, String fMenuTypeId, String fMenuTypeName, Integer delFlag,
			Integer fMerchantInnerId, Integer fEnableFlag, String fRemark, String fStandbyA, String fStandbyB,
			String fStandbyC, String fStandbyD) {
		super();
		FMenuTypeInnerId = fMenuTypeInnerId;
		FMenuTypeId = fMenuTypeId;
		FMenuTypeName = fMenuTypeName;
		DelFlag = delFlag;
		FMerchantInnerId = fMerchantInnerId;
		FEnableFlag = fEnableFlag;
		FRemark = fRemark;
		FStandbyA = fStandbyA;
		FStandbyB = fStandbyB;
		FStandbyC = fStandbyC;
		FStandbyD = fStandbyD;
	}
	public Integer getFMenuTypeInnerId() {
		return FMenuTypeInnerId;
	}
	public void setFMenuTypeInnerId(Integer fMenuTypeInnerId) {
		FMenuTypeInnerId = fMenuTypeInnerId;
	}
	public String getFMenuTypeId() {
		return FMenuTypeId;
	}
	public void setFMenuTypeId(String fMenuTypeId) {
		FMenuTypeId = fMenuTypeId;
	}
	public String getFMenuTypeName() {
		return FMenuTypeName;
	}
	public void setFMenuTypeName(String fMenuTypeName) {
		FMenuTypeName = fMenuTypeName;
	}
	public Integer getDelFlag() {
		return DelFlag;
	}
	public void setDelFlag(Integer delFlag) {
		DelFlag = delFlag;
	}
	public Integer getFMerchantInnerId() {
		return FMerchantInnerId;
	}
	public void setFMerchantInnerId(Integer fMerchantInnerId) {
		FMerchantInnerId = fMerchantInnerId;
	}
	public Integer getFEnableFlag() {
		return FEnableFlag;
	}
	public void setFEnableFlag(Integer fEnableFlag) {
		FEnableFlag = fEnableFlag;
	}
	public String getFRemark() {
		return FRemark;
	}
	public void setFRemark(String fRemark) {
		FRemark = fRemark;
	}
	public String getFStandbyA() {
		return FStandbyA;
	}
	public void setFStandbyA(String fStandbyA) {
		FStandbyA = fStandbyA;
	}
	public String getFStandbyB() {
		return FStandbyB;
	}
	public void setFStandbyB(String fStandbyB) {
		FStandbyB = fStandbyB;
	}
	public String getFStandbyC() {
		return FStandbyC;
	}
	public void setFStandbyC(String fStandbyC) {
		FStandbyC = fStandbyC;
	}
	public String getFStandbyD() {
		return FStandbyD;
	}
	public void setFStandbyD(String fStandbyD) {
		FStandbyD = fStandbyD;
	}
	@Override
	public String toString() {
		return "MenuType [FMenuTypeInnerId=" + FMenuTypeInnerId + ", FMenuTypeId=" + FMenuTypeId + ", FMenuTypeName="
				+ FMenuTypeName + ", DelFlag=" + DelFlag + ", FMerchantInnerId=" + FMerchantInnerId + ", FEnableFlag="
				+ FEnableFlag + ", FRemark=" + FRemark + ", FStandbyA=" + FStandbyA + ", FStandbyB=" + FStandbyB
				+ ", FStandbyC=" + FStandbyC + ", FStandbyD=" + FStandbyD + "]";
	}
}
