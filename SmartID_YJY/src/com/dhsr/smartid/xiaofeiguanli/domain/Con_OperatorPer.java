package com.dhsr.smartid.xiaofeiguanli.domain;

public class Con_OperatorPer {
	private Integer OperatorInnerId;
	private String OperatorId;
	private String OperatorName;
	private String MerchantList;
	private String ConTerminalList;
	private String Remark;
	private String StandbyA;
	private String StandbyB;

	public Con_OperatorPer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Con_OperatorPer(Integer operatorInnerId, String operatorId, String operatorName, String merchantList, String conTerminalList, String remark, String standbyA, String standbyB) {
		super();
		OperatorInnerId = operatorInnerId;
		OperatorId = operatorId;
		OperatorName = operatorName;
		MerchantList = merchantList;
		ConTerminalList = conTerminalList;
		Remark = remark;
		StandbyA = standbyA;
		StandbyB = standbyB;
	}

	public Integer getOperatorInnerId() {
		return OperatorInnerId;
	}

	public void setOperatorInnerId(Integer operatorInnerId) {
		OperatorInnerId = operatorInnerId;
	}

	public String getOperatorId() {
		return OperatorId;
	}

	public void setOperatorId(String operatorId) {
		OperatorId = operatorId;
	}

	public String getOperatorName() {
		return OperatorName;
	}

	public void setOperatorName(String operatorName) {
		OperatorName = operatorName;
	}

	public String getMerchantList() {
		return MerchantList;
	}

	public void setMerchantList(String merchantList) {
		MerchantList = merchantList;
	}

	public String getConTerminalList() {
		return ConTerminalList;
	}

	public void setConTerminalList(String conTerminalList) {
		ConTerminalList = conTerminalList;
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

	@Override
	public String toString() {
		return "Con_OperatorPer [OperatorInnerId=" + OperatorInnerId + ", OperatorId=" + OperatorId + ", OperatorName=" + OperatorName + ", MerchantList=" + MerchantList + ", ConTerminalList=" + ConTerminalList + ", Remark=" + Remark + ", StandbyA=" + StandbyA + ", StandbyB=" + StandbyB + "]";
	}

}
