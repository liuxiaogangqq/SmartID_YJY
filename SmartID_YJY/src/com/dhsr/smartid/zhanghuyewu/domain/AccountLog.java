package com.dhsr.smartid.zhanghuyewu.domain;

public class AccountLog {
	private Integer AccountLogInnerId;
	private Integer AccountInnerId;
	private Integer UserInnerId;
	private Integer DepartmentInnerId;
	private Integer CompanyInnerId;
	private Integer AccountTypeInnerId;
	private Integer Money;
	private Integer BeforeMoney;
	private Integer AfterMoney;
	private Integer InOrOut;
	private Integer RechargeType;
	private Integer MoneySource;
	private String Operator;
	private String Payee;
	private String AccountDate;
	private String StreamCode;
	private String Remark;
	private String StandbyA;
	private String StandbyB;
	private String StandbyC;
	private String StandbyD;

	public AccountLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AccountLog(Integer accountLogInnerId, Integer accountInnerId, Integer userInnerId, Integer departmentInnerId, Integer companyInnerId, Integer accountTypeInnerId, Integer money, Integer beforeMoney, Integer afterMoney, Integer inOrOut, Integer rechargeType, Integer moneySource, String operator, String payee, String accountDate, String streamCode, String remark, String standbyA, String standbyB, String standbyC, String standbyD) {
		super();
		AccountLogInnerId = accountLogInnerId;
		AccountInnerId = accountInnerId;
		UserInnerId = userInnerId;
		DepartmentInnerId = departmentInnerId;
		CompanyInnerId = companyInnerId;
		AccountTypeInnerId = accountTypeInnerId;
		Money = money;
		BeforeMoney = beforeMoney;
		AfterMoney = afterMoney;
		InOrOut = inOrOut;
		RechargeType = rechargeType;
		MoneySource = moneySource;
		Operator = operator;
		Payee = payee;
		AccountDate = accountDate;
		StreamCode = streamCode;
		Remark = remark;
		StandbyA = standbyA;
		StandbyB = standbyB;
		StandbyC = standbyC;
		StandbyD = standbyD;
	}

	public Integer getAccountLogInnerId() {
		return AccountLogInnerId;
	}

	public void setAccountLogInnerId(Integer accountLogInnerId) {
		AccountLogInnerId = accountLogInnerId;
	}

	public Integer getAccountInnerId() {
		return AccountInnerId;
	}

	public void setAccountInnerId(Integer accountInnerId) {
		AccountInnerId = accountInnerId;
	}

	public Integer getUserInnerId() {
		return UserInnerId;
	}

	public void setUserInnerId(Integer userInnerId) {
		UserInnerId = userInnerId;
	}

	public Integer getDepartmentInnerId() {
		return DepartmentInnerId;
	}

	public void setDepartmentInnerId(Integer departmentInnerId) {
		DepartmentInnerId = departmentInnerId;
	}

	public Integer getCompanyInnerId() {
		return CompanyInnerId;
	}

	public void setCompanyInnerId(Integer companyInnerId) {
		CompanyInnerId = companyInnerId;
	}

	public Integer getAccountTypeInnerId() {
		return AccountTypeInnerId;
	}

	public void setAccountTypeInnerId(Integer accountTypeInnerId) {
		AccountTypeInnerId = accountTypeInnerId;
	}

	public Integer getMoney() {
		return Money;
	}

	public void setMoney(Integer money) {
		Money = money;
	}

	public Integer getBeforeMoney() {
		return BeforeMoney;
	}

	public void setBeforeMoney(Integer beforeMoney) {
		BeforeMoney = beforeMoney;
	}

	public Integer getAfterMoney() {
		return AfterMoney;
	}

	public void setAfterMoney(Integer afterMoney) {
		AfterMoney = afterMoney;
	}

	public Integer getInOrOut() {
		return InOrOut;
	}

	public void setInOrOut(Integer inOrOut) {
		InOrOut = inOrOut;
	}

	public Integer getRechargeType() {
		return RechargeType;
	}

	public void setRechargeType(Integer rechargeType) {
		RechargeType = rechargeType;
	}

	public Integer getMoneySource() {
		return MoneySource;
	}

	public void setMoneySource(Integer moneySource) {
		MoneySource = moneySource;
	}

	public String getOperator() {
		return Operator;
	}

	public void setOperator(String operator) {
		Operator = operator;
	}

	public String getPayee() {
		return Payee;
	}

	public void setPayee(String payee) {
		Payee = payee;
	}

	public String getAccountDate() {
		return AccountDate;
	}

	public void setAccountDate(String accountDate) {
		AccountDate = accountDate;
	}

	public String getStreamCode() {
		return StreamCode;
	}

	public void setStreamCode(String streamCode) {
		StreamCode = streamCode;
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
		return "AccountLog [AccountLogInnerId=" + AccountLogInnerId + ", AccountInnerId=" + AccountInnerId + ", UserInnerId=" + UserInnerId + ", DepartmentInnerId=" + DepartmentInnerId + ", CompanyInnerId=" + CompanyInnerId + ", AccountTypeInnerId=" + AccountTypeInnerId + ", Money=" + Money + ", BeforeMoney=" + BeforeMoney + ", AfterMoney=" + AfterMoney + ", InOrOut=" + InOrOut + ", RechargeType=" + RechargeType + ", MoneySource=" + MoneySource + ", Operator=" + Operator + ", Payee=" + Payee + ", AccountDate=" + AccountDate + ", StreamCode=" + StreamCode + ", Remark=" + Remark + ", StandbyA=" + StandbyA + ", StandbyB=" + StandbyB + ", StandbyC=" + StandbyC + ", StandbyD=" + StandbyD + "]";
	}

}
