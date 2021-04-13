package com.dhsr.smartid.peizhiguanli.domain;

public class TerminalType {
	private Integer TerminalTypeInnerId;
	private String TerminalTypeId;
	private String TerminalTypeName;
	private String Remark;
	private Integer DelFlag;
	private String StandbyA;
	private String StandbyB;

	public TerminalType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TerminalType(Integer terminalTypeInnerId, String terminalTypeId, String terminalTypeName, String remark, Integer delFlag, String standbyA, String standbyB) {
		super();
		TerminalTypeInnerId = terminalTypeInnerId;
		TerminalTypeId = terminalTypeId;
		TerminalTypeName = terminalTypeName;
		Remark = remark;
		DelFlag = delFlag;
		StandbyA = standbyA;
		StandbyB = standbyB;
	}

	public Integer getTerminalTypeInnerId() {
		return TerminalTypeInnerId;
	}

	public void setTerminalTypeInnerId(Integer terminalTypeInnerId) {
		TerminalTypeInnerId = terminalTypeInnerId;
	}

	public String getTerminalTypeId() {
		return TerminalTypeId;
	}

	public void setTerminalTypeId(String terminalTypeId) {
		TerminalTypeId = terminalTypeId;
	}

	public String getTerminalTypeName() {
		return TerminalTypeName;
	}

	public void setTerminalTypeName(String terminalTypeName) {
		TerminalTypeName = terminalTypeName;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public Integer getDelFlag() {
		return DelFlag;
	}

	public void setDelFlag(Integer delFlag) {
		DelFlag = delFlag;
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

	@Override
	public String toString() {
		return "TerminalType [TerminalTypeInnerId=" + TerminalTypeInnerId + ", TerminalTypeId=" + TerminalTypeId + ", TerminalTypeName=" + TerminalTypeName + ", Remark=" + Remark + ", DelFlag=" + DelFlag + ", StandbyA=" + StandbyA + ", StandbyB=" + StandbyB + "]";
	}

}
