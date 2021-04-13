package com.dhsr.smartid.xiaofeiguanli.domain;

public class ConOperatorPer {
	private Integer OperatorInnerId;
	private String MerchantList;
	private String ConTerminalList;
	private String Remark;
	private String StandbyA;
	private String StandbyB;

	public ConOperatorPer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConOperatorPer(Integer operatorInnerId, String merchantList, String conTerminalList, String remark, String standbyA, String standbyB) {
		super();
		OperatorInnerId = operatorInnerId;
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
		return "ConOperatorPer [OperatorInnerId=" + OperatorInnerId + ", MerchantList=" + MerchantList + ", ConTerminalList=" + ConTerminalList + ", Remark=" + Remark + ", StandbyA=" + StandbyA + ", StandbyB=" + StandbyB + "]";
	}

}
