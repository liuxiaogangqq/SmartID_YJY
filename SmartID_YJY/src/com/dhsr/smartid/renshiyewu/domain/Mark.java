package com.dhsr.smartid.renshiyewu.domain;

import java.io.Serializable;

public class Mark implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer MarkInnerId;
	private String MarkId;
	private String MarkCode;
	private String MarkName;
	private Integer MarkState;
	private Integer MarkTypeInnerId;
	private Integer UserInnerId;
	private Integer UserMarkId;
	private String SendDate;
	private String FingerCode1;
	private String FingerCode2;
	private Integer DelFlag;
	private String Remark;
	private String StandbyA;
	private String StandbyB;
	private String StandbyC;
	private String StandbyD;
	private Integer MarkMoney;

	public Integer getMarkMoney() {
		return MarkMoney;
	}

	public void setMarkMoney(Integer markMoney) {
		MarkMoney = markMoney;
	}

	public Mark() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Mark(Integer markInnerId, String markId, String markCode, String markName, Integer markState, Integer markTypeInnerId, Integer userInnerId, Integer userMarkId, String sendDate, String fingerCode1, String fingerCode2, Integer delFlag, String remark, String standbyA, String standbyB, String standbyC, String standbyD) {
		super();
		MarkInnerId = markInnerId;
		MarkId = markId;
		MarkCode = markCode;
		MarkName = markName;
		MarkState = markState;
		MarkTypeInnerId = markTypeInnerId;
		UserInnerId = userInnerId;
		UserMarkId = userMarkId;
		SendDate = sendDate;
		FingerCode1 = fingerCode1;
		FingerCode2 = fingerCode2;
		DelFlag = delFlag;
		Remark = remark;
		StandbyA = standbyA;
		StandbyB = standbyB;
		StandbyC = standbyC;
		StandbyD = standbyD;
	}

	public Integer getMarkInnerId() {
		return MarkInnerId;
	}

	public void setMarkInnerId(Integer markInnerId) {
		MarkInnerId = markInnerId;
	}

	public String getMarkId() {
		return MarkId;
	}

	public void setMarkId(String markId) {
		MarkId = markId;
	}

	public String getMarkCode() {
		return MarkCode;
	}

	public void setMarkCode(String markCode) {
		MarkCode = markCode;
	}

	public String getMarkName() {
		return MarkName;
	}

	public void setMarkName(String markName) {
		MarkName = markName;
	}

	public Integer getMarkState() {
		return MarkState;
	}

	public void setMarkState(Integer markState) {
		MarkState = markState;
	}

	public Integer getMarkTypeInnerId() {
		return MarkTypeInnerId;
	}

	public void setMarkTypeInnerId(Integer markTypeInnerId) {
		MarkTypeInnerId = markTypeInnerId;
	}

	public Integer getUserInnerId() {
		return UserInnerId;
	}

	public void setUserInnerId(Integer userInnerId) {
		UserInnerId = userInnerId;
	}

	public Integer getUserMarkId() {
		return UserMarkId;
	}

	public void setUserMarkId(Integer userMarkId) {
		UserMarkId = userMarkId;
	}

	public String getSendDate() {
		return SendDate;
	}

	public void setSendDate(String sendDate) {
		SendDate = sendDate;
	}

	public String getFingerCode1() {
		return FingerCode1;
	}

	public void setFingerCode1(String fingerCode1) {
		FingerCode1 = fingerCode1;
	}

	public String getFingerCode2() {
		return FingerCode2;
	}

	public void setFingerCode2(String fingerCode2) {
		FingerCode2 = fingerCode2;
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
		return "Mark [MarkInnerId=" + MarkInnerId + ", MarkId=" + MarkId
				+ ", MarkCode=" + MarkCode + ", MarkName=" + MarkName
				+ ", MarkState=" + MarkState + ", MarkTypeInnerId="
				+ MarkTypeInnerId + ", UserInnerId=" + UserInnerId
				+ ", UserMarkId=" + UserMarkId + ", SendDate=" + SendDate
				+ ", FingerCode1=" + FingerCode1 + ", FingerCode2="
				+ FingerCode2 + ", DelFlag=" + DelFlag + ", Remark=" + Remark
				+ ", StandbyA=" + StandbyA + ", StandbyB=" + StandbyB
				+ ", StandbyC=" + StandbyC + ", StandbyD=" + StandbyD
				+ ", MarkMoney=" + MarkMoney + "]";
	}

}
