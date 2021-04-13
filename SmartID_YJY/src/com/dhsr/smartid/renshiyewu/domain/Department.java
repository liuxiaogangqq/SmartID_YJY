package com.dhsr.smartid.renshiyewu.domain;

import java.io.Serializable;

public class Department implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer DepartmentInnerId;
	private String DepartmentId;
	private String DepartmentName;
	private Integer CompanyInnerId;
	private Integer UpInnerId;
	private Integer DepartmentLevel;
	private String Remark;
	private Integer DelFlag;
	private String StandbyA;
	private String StandbyB;
	private String StandbyC;
	private String StandbyD;

	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Department(Integer departmentInnerId, String departmentId, String departmentName, Integer companyInnerId, Integer upInnerId, Integer departmentLevel, String remark, Integer delFlag, String standbyA, String standbyB, String standbyC, String standbyD) {
		super();
		DepartmentInnerId = departmentInnerId;
		DepartmentId = departmentId;
		DepartmentName = departmentName;
		CompanyInnerId = companyInnerId;
		UpInnerId = upInnerId;
		DepartmentLevel = departmentLevel;
		Remark = remark;
		DelFlag = delFlag;
		StandbyA = standbyA;
		StandbyB = standbyB;
		StandbyC = standbyC;
		StandbyD = standbyD;
	}

	public Integer getDepartmentInnerId() {
		return DepartmentInnerId;
	}

	public void setDepartmentInnerId(Integer departmentInnerId) {
		DepartmentInnerId = departmentInnerId;
	}

	public String getDepartmentId() {
		return DepartmentId;
	}

	public void setDepartmentId(String departmentId) {
		DepartmentId = departmentId;
	}

	public String getDepartmentName() {
		return DepartmentName;
	}

	public void setDepartmentName(String departmentName) {
		DepartmentName = departmentName;
	}

	public Integer getCompanyInnerId() {
		return CompanyInnerId;
	}

	public void setCompanyInnerId(Integer companyInnerId) {
		CompanyInnerId = companyInnerId;
	}

	public Integer getUpInnerId() {
		return UpInnerId;
	}

	public void setUpInnerId(Integer upInnerId) {
		UpInnerId = upInnerId;
	}

	public Integer getDepartmentLevel() {
		return DepartmentLevel;
	}

	public void setDepartmentLevel(Integer departmentLevel) {
		DepartmentLevel = departmentLevel;
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
		return "Department [DepartmentInnerId=" + DepartmentInnerId + ", DepartmentId=" + DepartmentId + ", DepartmentName=" + DepartmentName + ", CompanyInnerId=" + CompanyInnerId + ", UpInnerId=" + UpInnerId + ", DepartmentLevel=" + DepartmentLevel + ", Remark=" + Remark + ", DelFlag=" + DelFlag + ", StandbyA=" + StandbyA + ", StandbyB=" + StandbyB + ", StandbyC=" + StandbyC + ", StandbyD=" + StandbyD + "]";
	}

}
