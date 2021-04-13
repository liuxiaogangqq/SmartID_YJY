package com.dhsr.smartid.peizhiguanli.domain;

import java.io.Serializable;

public class Operator implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer OperatorInnerId;
	private String OperatorId;
	private String OperatorName;
	private String Password;
	private String PageList;
	private String AreaList;
	private String AppList;
	private String CompanyList;
	private String DepartmentList;
	private Integer TableSize;
	private String Remark;
	private Integer DelFlag;
	private String StandbyA;
	private String StandbyB;
	private String StandbyC;
	private String StandbyD;

	public Operator() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Operator(Integer operatorInnerId, String operatorId, String operatorName, String password, String pageList, String areaList, String appList, String companyList, String departmentList, Integer tableSize, String remark, Integer delFlag, String standbyA, String standbyB, String standbyC, String standbyD) {
		super();
		OperatorInnerId = operatorInnerId;
		OperatorId = operatorId;
		OperatorName = operatorName;
		Password = password;
		PageList = pageList;
		AreaList = areaList;
		AppList = appList;
		CompanyList = companyList;
		DepartmentList = departmentList;
		TableSize = tableSize;
		Remark = remark;
		DelFlag = delFlag;
		StandbyA = standbyA;
		StandbyB = standbyB;
		StandbyC = standbyC;
		StandbyD = standbyD;
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

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getPageList() {
		return PageList;
	}

	public void setPageList(String pageList) {
		PageList = pageList;
	}

	public String getAreaList() {
		return AreaList;
	}

	public void setAreaList(String areaList) {
		AreaList = areaList;
	}

	public String getAppList() {
		return AppList;
	}

	public void setAppList(String appList) {
		AppList = appList;
	}

	public String getCompanyList() {
		return CompanyList;
	}

	public void setCompanyList(String companyList) {
		CompanyList = companyList;
	}

	public String getDepartmentList() {
		return DepartmentList;
	}

	public void setDepartmentList(String departmentList) {
		DepartmentList = departmentList;
	}

	public Integer getTableSize() {
		return TableSize;
	}

	public void setTableSize(Integer tableSize) {
		TableSize = tableSize;
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
		return "Operator [OperatorInnerId=" + OperatorInnerId + ", OperatorId=" + OperatorId + ", OperatorName=" + OperatorName + ", Password=" + Password + ", PageList=" + PageList + ", AreaList=" + AreaList + ", AppList=" + AppList + ", CompanyList=" + CompanyList + ", DepartmentList=" + DepartmentList + ", TableSize=" + TableSize + ", Remark=" + Remark + ", DelFlag=" + DelFlag + ", StandbyA=" + StandbyA + ", StandbyB=" + StandbyB + ", StandbyC=" + StandbyC + ", StandbyD=" + StandbyD + "]";
	}

}
