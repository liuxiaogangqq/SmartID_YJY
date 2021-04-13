package com.dhsr.smartid.xiaofeiguanli.domain;

import java.io.Serializable;

public class MenuList implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer FMenuInnerId;
	private Integer FMenuTypeInnerId;
	private Integer FMenuPrice;
	private Integer FEnableFlag;
	private Integer DelFlag;
	private String FMenuId;
	private String FMenuName;
	private String FRemark;
	private String FStandbyA;
	private String FStandbyB;
	private String FStandbyC;
	private String FStandbyD;
	public MenuList() {
		super();
	}
	public MenuList(Integer fMenuInnerId, Integer fMenuTypeInnerId, Integer fMenuPrice, Integer fEnableFlag,
			Integer delFlag, String fMenuId, String fMenuName, String fRemark, String fStandbyA, String fStandbyB,
			String fStandbyC, String fStandbyD) {
		super();
		FMenuInnerId = fMenuInnerId;
		FMenuTypeInnerId = fMenuTypeInnerId;
		FMenuPrice = fMenuPrice;
		FEnableFlag = fEnableFlag;
		DelFlag = delFlag;
		FMenuId = fMenuId;
		FMenuName = fMenuName;
		FRemark = fRemark;
		FStandbyA = fStandbyA;
		FStandbyB = fStandbyB;
		FStandbyC = fStandbyC;
		FStandbyD = fStandbyD;
	}
	public Integer getFMenuInnerId() {
		return FMenuInnerId;
	}
	public void setFMenuInnerId(Integer fMenuInnerId) {
		FMenuInnerId = fMenuInnerId;
	}
	public Integer getFMenuTypeInnerId() {
		return FMenuTypeInnerId;
	}
	public void setFMenuTypeInnerId(Integer fMenuTypeInnerId) {
		FMenuTypeInnerId = fMenuTypeInnerId;
	}
	public Integer getFMenuPrice() {
		return FMenuPrice;
	}
	public void setFMenuPrice(Integer fMenuPrice) {
		FMenuPrice = fMenuPrice;
	}
	public Integer getFEnableFlag() {
		return FEnableFlag;
	}
	public void setFEnableFlag(Integer fEnableFlag) {
		FEnableFlag = fEnableFlag;
	}
	public Integer getDelFlag() {
		return DelFlag;
	}
	public void setDelFlag(Integer delFlag) {
		DelFlag = delFlag;
	}
	public String getFMenuId() {
		return FMenuId;
	}
	public void setFMenuId(String fMenuId) {
		FMenuId = fMenuId;
	}
	public String getFMenuName() {
		return FMenuName;
	}
	public void setFMenuName(String fMenuName) {
		FMenuName = fMenuName;
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
		return "MenuList [FMenuInnerId=" + FMenuInnerId + ", FMenuTypeInnerId=" + FMenuTypeInnerId + ", FMenuPrice="
				+ FMenuPrice + ", FEnableFlag=" + FEnableFlag + ", DelFlag=" + DelFlag + ", FMenuId=" + FMenuId
				+ ", FMenuName=" + FMenuName + ", FRemark=" + FRemark + ", FStandbyA=" + FStandbyA + ", FStandbyB="
				+ FStandbyB + ", FStandbyC=" + FStandbyC + ", FStandbyD=" + FStandbyD + "]";
	}
	
}
