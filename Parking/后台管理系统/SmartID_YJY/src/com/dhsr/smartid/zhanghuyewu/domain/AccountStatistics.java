package com.dhsr.smartid.zhanghuyewu.domain;

public class AccountStatistics {
	private Long Money;
	private Integer AccountTypeInnerId;
	private Integer InOrOut;
	private String RowName;

	public AccountStatistics(Long money, Integer accountTypeInnerId, Integer inOrOut, String rowName) {
		super();
		Money = money;
		AccountTypeInnerId = accountTypeInnerId;
		InOrOut = inOrOut;
		RowName = rowName;
	}

	public AccountStatistics() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getMoney() {
		return Money;
	}

	public void setMoney(Long money) {
		Money = money;
	}

	public Integer getAccountTypeInnerId() {
		return AccountTypeInnerId;
	}

	public void setAccountTypeInnerId(Integer accountTypeInnerId) {
		AccountTypeInnerId = accountTypeInnerId;
	}

	public Integer getInOrOut() {
		return InOrOut;
	}

	public void setInOrOut(Integer inOrOut) {
		InOrOut = inOrOut;
	}

	public String getRowName() {
		return RowName;
	}

	public void setRowName(String rowName) {
		RowName = rowName;
	}

	@Override
	public String toString() {
		return "AccountStatistics [Money=" + Money + ", AccountTypeInnerId=" + AccountTypeInnerId + ", InOrOut=" + InOrOut + ", RowName=" + RowName + "]";
	}

}
