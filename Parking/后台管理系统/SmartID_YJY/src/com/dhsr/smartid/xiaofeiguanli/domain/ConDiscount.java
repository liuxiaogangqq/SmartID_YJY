package com.dhsr.smartid.xiaofeiguanli.domain;

public class ConDiscount {
	private Integer ConDiscountInnerId;
	private String ConDiscountId;
	private String ConDiscountName;
	private Integer Proportion1;
	private Integer Money1;
	private Integer Proportion2;
	private Integer Money2;
	private Integer Proportion3;
	private Integer Money3;
	private Integer Proportion4;
	private Integer Money4;
	private Integer Proportion5;
	private Integer Money5;
	private String UserTypeList;
	private Integer DelFlag;
	private String Remark;
	private String StandbyA;
	private String StandbyB;
	private String StandbyC;
	private String StandbyD;

	public ConDiscount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConDiscount(Integer conDiscountInnerId, String conDiscountId, String conDiscountName, Integer proportion1, Integer money1, Integer proportion2, Integer money2, Integer proportion3, Integer money3, Integer proportion4, Integer money4, Integer proportion5, Integer money5, String userTypeList, Integer delFlag, String remark, String standbyA, String standbyB, String standbyC, String standbyD) {
		super();
		ConDiscountInnerId = conDiscountInnerId;
		ConDiscountId = conDiscountId;
		ConDiscountName = conDiscountName;
		Proportion1 = proportion1;
		Money1 = money1;
		Proportion2 = proportion2;
		Money2 = money2;
		Proportion3 = proportion3;
		Money3 = money3;
		Proportion4 = proportion4;
		Money4 = money4;
		Proportion5 = proportion5;
		Money5 = money5;
		UserTypeList = userTypeList;
		DelFlag = delFlag;
		Remark = remark;
		StandbyA = standbyA;
		StandbyB = standbyB;
		StandbyC = standbyC;
		StandbyD = standbyD;
	}

	public Integer getConDiscountInnerId() {
		return ConDiscountInnerId;
	}

	public void setConDiscountInnerId(Integer conDiscountInnerId) {
		ConDiscountInnerId = conDiscountInnerId;
	}

	public String getConDiscountId() {
		return ConDiscountId;
	}

	public void setConDiscountId(String conDiscountId) {
		ConDiscountId = conDiscountId;
	}

	public String getConDiscountName() {
		return ConDiscountName;
	}

	public void setConDiscountName(String conDiscountName) {
		ConDiscountName = conDiscountName;
	}

	public Integer getProportion1() {
		return Proportion1;
	}

	public void setProportion1(Integer proportion1) {
		Proportion1 = proportion1;
	}

	public Integer getMoney1() {
		return Money1;
	}

	public void setMoney1(Integer money1) {
		Money1 = money1;
	}

	public Integer getProportion2() {
		return Proportion2;
	}

	public void setProportion2(Integer proportion2) {
		Proportion2 = proportion2;
	}

	public Integer getMoney2() {
		return Money2;
	}

	public void setMoney2(Integer money2) {
		Money2 = money2;
	}

	public Integer getProportion3() {
		return Proportion3;
	}

	public void setProportion3(Integer proportion3) {
		Proportion3 = proportion3;
	}

	public Integer getMoney3() {
		return Money3;
	}

	public void setMoney3(Integer money3) {
		Money3 = money3;
	}

	public Integer getProportion4() {
		return Proportion4;
	}

	public void setProportion4(Integer proportion4) {
		Proportion4 = proportion4;
	}

	public Integer getMoney4() {
		return Money4;
	}

	public void setMoney4(Integer money4) {
		Money4 = money4;
	}

	public Integer getProportion5() {
		return Proportion5;
	}

	public void setProportion5(Integer proportion5) {
		Proportion5 = proportion5;
	}

	public Integer getMoney5() {
		return Money5;
	}

	public void setMoney5(Integer money5) {
		Money5 = money5;
	}

	public String getUserTypeList() {
		return UserTypeList;
	}

	public void setUserTypeList(String userTypeList) {
		UserTypeList = userTypeList;
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
		return "ConDiscount [ConDiscountInnerId=" + ConDiscountInnerId + ", ConDiscountId=" + ConDiscountId + ", ConDiscountName=" + ConDiscountName + ", Proportion1=" + Proportion1 + ", Money1=" + Money1 + ", Proportion2=" + Proportion2 + ", Money2=" + Money2 + ", Proportion3=" + Proportion3 + ", Money3=" + Money3 + ", Proportion4=" + Proportion4 + ", Money4=" + Money4 + ", Proportion5=" + Proportion5 + ", Money5=" + Money5 + ", UserTypeList=" + UserTypeList + ", DelFlag=" + DelFlag + ", Remark=" + Remark + ", StandbyA=" + StandbyA + ", StandbyB=" + StandbyB + ", StandbyC=" + StandbyC + ", StandbyD=" + StandbyD + "]";
	}

}
