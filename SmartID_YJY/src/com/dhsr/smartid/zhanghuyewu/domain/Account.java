package com.dhsr.smartid.zhanghuyewu.domain;

public class Account {
	private Integer AccountInnerId;
	private Integer AccountTypeInnerId;
	private Integer UserInnerId;
	private Integer Money;
	private Integer AccountState;
	private String Remark;
	private String StandbyA;
	private String StandbyB;

	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Account(Integer accountInnerId, Integer accountTypeInnerId, Integer userInnerId, Integer money, Integer accountState, String remark, String standbyA, String standbyB) {
		super();
		AccountInnerId = accountInnerId;
		AccountTypeInnerId = accountTypeInnerId;
		UserInnerId = userInnerId;
		Money = money;
		AccountState = accountState;
		Remark = remark;
		StandbyA = standbyA;
		StandbyB = standbyB;
	}

	public Integer getAccountInnerId() {
		return AccountInnerId;
	}

	public void setAccountInnerId(Integer accountInnerId) {
		AccountInnerId = accountInnerId;
	}

	public Integer getAccountTypeInnerId() {
		return AccountTypeInnerId;
	}

	public void setAccountTypeInnerId(Integer accountTypeInnerId) {
		AccountTypeInnerId = accountTypeInnerId;
	}

	public Integer getUserInnerId() {
		return UserInnerId;
	}

	public void setUserInnerId(Integer userInnerId) {
		UserInnerId = userInnerId;
	}

	public Integer getMoney() {
		return Money;
	}

	public void setMoney(Integer money) {
		Money = money;
	}

	public Integer getAccountState() {
		return AccountState;
	}

	public void setAccountState(Integer accountState) {
		AccountState = accountState;
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
		return "Account [AccountInnerId=" + AccountInnerId + ", AccountTypeInnerId=" + AccountTypeInnerId + ", UserInnerId=" + UserInnerId + ", Money=" + Money + ", AccountState=" + AccountState + ", Remark=" + Remark + ", StandbyA=" + StandbyA + ", StandbyB=" + StandbyB + "]";
	}

}
