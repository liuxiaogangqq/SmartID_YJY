package com.dhsr.smartid.xiaofeiguanli.domain;

import java.io.Serializable;

public class ConTerminal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer ConTerminalInnerId;
	private String ConTerminalId;
	private String ConTerminalName;
	private Integer MerchantInnerId;
	private Integer AppInnerId;
	private Integer AreaInnerId;
	private Integer TerminalTypeInnerId;
	private String ComId;
	private String ComSerials;
	private String ConTypeList;
	private Integer DelFlag;
	private String Remark;
	private String StandbyA;
	private String StandbyB;
	private String StandbyC;
	private String StandbyD;

	public ConTerminal() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConTerminal(Integer conTerminalInnerId, String conTerminalId, String conTerminalName, Integer merchantInnerId, Integer appInnerId, Integer areaInnerId, Integer terminalTypeInnerId, String comId, String comSerials, String conTypeList, Integer delFlag, String remark, String standbyA, String standbyB, String standbyC, String standbyD) {
		super();
		ConTerminalInnerId = conTerminalInnerId;
		ConTerminalId = conTerminalId;
		ConTerminalName = conTerminalName;
		MerchantInnerId = merchantInnerId;
		AppInnerId = appInnerId;
		AreaInnerId = areaInnerId;
		TerminalTypeInnerId = terminalTypeInnerId;
		ComId = comId;
		ComSerials = comSerials;
		ConTypeList = conTypeList;
		DelFlag = delFlag;
		Remark = remark;
		StandbyA = standbyA;
		StandbyB = standbyB;
		StandbyC = standbyC;
		StandbyD = standbyD;
	}

	public Integer getConTerminalInnerId() {
		return ConTerminalInnerId;
	}

	public void setConTerminalInnerId(Integer conTerminalInnerId) {
		ConTerminalInnerId = conTerminalInnerId;
	}

	public String getConTerminalId() {
		return ConTerminalId;
	}

	public void setConTerminalId(String conTerminalId) {
		ConTerminalId = conTerminalId;
	}

	public String getConTerminalName() {
		return ConTerminalName;
	}

	public void setConTerminalName(String conTerminalName) {
		ConTerminalName = conTerminalName;
	}

	public Integer getMerchantInnerId() {
		return MerchantInnerId;
	}

	public void setMerchantInnerId(Integer merchantInnerId) {
		MerchantInnerId = merchantInnerId;
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

	public Integer getTerminalTypeInnerId() {
		return TerminalTypeInnerId;
	}

	public void setTerminalTypeInnerId(Integer terminalTypeInnerId) {
		TerminalTypeInnerId = terminalTypeInnerId;
	}

	public String getComId() {
		return ComId;
	}

	public void setComId(String comId) {
		ComId = comId;
	}

	public String getComSerials() {
		return ComSerials;
	}

	public void setComSerials(String comSerials) {
		ComSerials = comSerials;
	}

	public String getConTypeList() {
		return ConTypeList;
	}

	public void setConTypeList(String conTypeList) {
		ConTypeList = conTypeList;
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
		return "ConTerminal [ConTerminalInnerId=" + ConTerminalInnerId + ", ConTerminalId=" + ConTerminalId + ", ConTerminalName=" + ConTerminalName + ", MerchantInnerId=" + MerchantInnerId + ", AppInnerId=" + AppInnerId + ", AreaInnerId=" + AreaInnerId + ", TerminalTypeInnerId=" + TerminalTypeInnerId + ", ComId=" + ComId + ", ComSerials=" + ComSerials + ", ConTypeList=" + ConTypeList + ", DelFlag=" + DelFlag + ", Remark=" + Remark + ", StandbyA=" + StandbyA + ", StandbyB=" + StandbyB + ", StandbyC=" + StandbyC + ", StandbyD=" + StandbyD + "]";
	}

}
