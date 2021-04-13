package com.dhsr.smartid.peizhiguanli.domain;

public class MarkType {
	private Integer MarkTypeInnerId;
	private String MarkTypeId;
	private String MarkTypeName;
	private String Remark;
	private Integer DelFlag;
	private String StandbyA;
	private String StandbyB;

	public MarkType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MarkType(Integer markTypeInnerId, String markTypeId, String markTypeName, String remark, Integer delFlag, String standbyA, String standbyB) {
		super();
		MarkTypeInnerId = markTypeInnerId;
		MarkTypeId = markTypeId;
		MarkTypeName = markTypeName;
		Remark = remark;
		DelFlag = delFlag;
		StandbyA = standbyA;
		StandbyB = standbyB;
	}

	public Integer getMarkTypeInnerId() {
		return MarkTypeInnerId;
	}

	public void setMarkTypeInnerId(Integer markTypeInnerId) {
		MarkTypeInnerId = markTypeInnerId;
	}

	public String getMarkTypeId() {
		return MarkTypeId;
	}

	public void setMarkTypeId(String markTypeId) {
		MarkTypeId = markTypeId;
	}

	public String getMarkTypeName() {
		return MarkTypeName;
	}

	public void setMarkTypeName(String markTypeName) {
		MarkTypeName = markTypeName;
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
		return "MarkType [MarkTypeInnerId=" + MarkTypeInnerId + ", MarkTypeId=" + MarkTypeId + ", MarkTypeName=" + MarkTypeName + ", Remark=" + Remark + ", DelFlag=" + DelFlag + ", StandbyA=" + StandbyA + ", StandbyB=" + StandbyB + "]";
	}

}
