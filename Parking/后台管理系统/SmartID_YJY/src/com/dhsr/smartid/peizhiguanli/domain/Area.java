package com.dhsr.smartid.peizhiguanli.domain;

import java.io.Serializable;

public class Area implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer AreaInnerId;
	private String AreaId;
	private String AreaName;
	private String Remark;
	private Integer DelFlag;
	private String StandbyA;
	private String StandbyB;
	private String StandbyC;
	private String StandbyD;

	public Area() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Area(Integer areaInnerId, String areaId, String areaName, String remark, Integer delFlag, String standbyA, String standbyB, String standbyC, String standbyD) {
		super();
		AreaInnerId = areaInnerId;
		AreaId = areaId;
		AreaName = areaName;
		Remark = remark;
		DelFlag = delFlag;
		StandbyA = standbyA;
		StandbyB = standbyB;
		StandbyC = standbyC;
		StandbyD = standbyD;
	}

	public Integer getAreaInnerId() {
		return AreaInnerId;
	}

	public void setAreaInnerId(Integer areaInnerId) {
		AreaInnerId = areaInnerId;
	}

	public String getAreaId() {
		return AreaId;
	}

	public void setAreaId(String areaId) {
		AreaId = areaId;
	}

	public String getAreaName() {
		return AreaName;
	}

	public void setAreaName(String areaName) {
		AreaName = areaName;
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
		return "Area [AreaInnerId=" + AreaInnerId + ", AreaId=" + AreaId + ", AreaName=" + AreaName + ", Remark=" + Remark + ", DelFlag=" + DelFlag + ", StandbyA=" + StandbyA + ", StandbyB=" + StandbyB + ", StandbyC=" + StandbyC + ", StandbyD=" + StandbyD + "]";
	}

}
