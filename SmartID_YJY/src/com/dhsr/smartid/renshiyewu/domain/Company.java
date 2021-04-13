package com.dhsr.smartid.renshiyewu.domain;

import java.io.Serializable;

/**
 * 公司实体类
 * 
 * @author qidong
 *
 */
public class Company implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer CompanyInnerId;
	private String CompanyId;
	private String CompanyName;
	private String Remark;
	private Integer DelFlag;
	private String StandbyA;
	private String StandbyB;
	private Integer AreaInnerId;

	public Company() {
		super();
	}

	public Company(Integer companyInnerId, String companyId, String companyName, String remark, Integer delFlag,
			String standbyA, String standbyB, Integer areaInnerId) {
		super();
		CompanyInnerId = companyInnerId;
		CompanyId = companyId;
		CompanyName = companyName;
		Remark = remark;
		DelFlag = delFlag;
		StandbyA = standbyA;
		StandbyB = standbyB;
		AreaInnerId = areaInnerId;
	}

	public Integer getCompanyInnerId() {
		return CompanyInnerId;
	}

	public void setCompanyInnerId(Integer companyInnerId) {
		CompanyInnerId = companyInnerId;
	}

	public String getCompanyId() {
		return CompanyId;
	}

	public void setCompanyId(String companyId) {
		CompanyId = companyId;
	}

	public String getCompanyName() {
		return CompanyName;
	}

	public void setCompanyName(String companyName) {
		CompanyName = companyName;
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

	public Integer getAreaInnerId() {
		return AreaInnerId;
	}

	public void setAreaInnerId(Integer areaInnerId) {
		AreaInnerId = areaInnerId;
	}

	@Override
	public String toString() {
		return "Company [CompanyInnerId=" + CompanyInnerId + ", CompanyId=" + CompanyId + ", CompanyName=" + CompanyName
				+ ", Remark=" + Remark + ", DelFlag=" + DelFlag + ", StandbyA=" + StandbyA + ", StandbyB=" + StandbyB
				+ ", AreaInnerId=" + AreaInnerId + "]";
	}


}
