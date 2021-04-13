package com.dhsr.smartid.peizhiguanli.domain;

import java.io.Serializable;

public class SysTerminal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer SysTerminalInnerId;
	private String SysTerminalId;
	private String SysTerminalName;
	private Integer AppInnerId;
	private Integer AreaInnerId;
	private Integer TerminalTypeInnerId;
	private String ComId;
	private String ComSerials;
	private Integer DelFlag;
	private String Remark;
	private String StandbyA;
	private String StandbyB;
	private String StandbyC;
	private String StandbyD;

	public SysTerminal() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SysTerminal(Integer sysTerminalInnerId, String sysTerminalId, String sysTerminalName, Integer appInnerId, Integer areaInnerId, Integer terminalTypeInnerId, String comId, String comSerials, Integer delFlag, String remark, String standbyA, String standbyB, String standbyC, String standbyD) {
		super();
		SysTerminalInnerId = sysTerminalInnerId;
		SysTerminalId = sysTerminalId;
		SysTerminalName = sysTerminalName;
		AppInnerId = appInnerId;
		AreaInnerId = areaInnerId;
		TerminalTypeInnerId = terminalTypeInnerId;
		ComId = comId;
		ComSerials = comSerials;
		DelFlag = delFlag;
		Remark = remark;
		StandbyA = standbyA;
		StandbyB = standbyB;
		StandbyC = standbyC;
		StandbyD = standbyD;
	}

	public Integer getSysTerminalInnerId() {
		return SysTerminalInnerId;
	}

	public void setSysTerminalInnerId(Integer sysTerminalInnerId) {
		SysTerminalInnerId = sysTerminalInnerId;
	}

	public String getSysTerminalId() {
		return SysTerminalId;
	}

	public void setSysTerminalId(String sysTerminalId) {
		SysTerminalId = sysTerminalId;
	}

	public String getSysTerminalName() {
		return SysTerminalName;
	}

	public void setSysTerminalName(String sysTerminalName) {
		SysTerminalName = sysTerminalName;
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
		return "SysTerminal [SysTerminalInnerId=" + SysTerminalInnerId + ", SysTerminalId=" + SysTerminalId + ", SysTerminalName=" + SysTerminalName + ", AppInnerId=" + AppInnerId + ", AreaInnerId=" + AreaInnerId + ", TerminalTypeInnerId=" + TerminalTypeInnerId + ", ComId=" + ComId + ", ComSerials=" + ComSerials + ", DelFlag=" + DelFlag + ", Remark=" + Remark + ", StandbyA=" + StandbyA + ", StandbyB=" + StandbyB + ", StandbyC=" + StandbyC + ", StandbyD=" + StandbyD + "]";
	}

}
