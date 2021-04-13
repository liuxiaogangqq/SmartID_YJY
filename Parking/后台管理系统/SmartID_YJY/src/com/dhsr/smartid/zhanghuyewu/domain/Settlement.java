package com.dhsr.smartid.zhanghuyewu.domain;

public class Settlement {
	private String CashMoney;
	private String SubsidyMoney;
	private String NumberMoney;
	private String CardMoney;
	private Integer PeopleTotal;
	private String FDateTime;
	public Settlement() {
		super();
	}
	public Settlement(String cashMoney, String subsidyMoney, String numberMoney, String cardMoney, Integer peopleTotal,
			String fDateTime) {
		super();
		CashMoney = cashMoney;
		SubsidyMoney = subsidyMoney;
		NumberMoney = numberMoney;
		CardMoney = cardMoney;
		PeopleTotal = peopleTotal;
		FDateTime = fDateTime;
	}
	public String getCashMoney() {
		return CashMoney;
	}
	public void setCashMoney(String cashMoney) {
		CashMoney = cashMoney;
	}
	public String getSubsidyMoney() {
		return SubsidyMoney;
	}
	public void setSubsidyMoney(String subsidyMoney) {
		SubsidyMoney = subsidyMoney;
	}
	public String getNumberMoney() {
		return NumberMoney;
	}
	public void setNumberMoney(String numberMoney) {
		NumberMoney = numberMoney;
	}
	public String getCardMoney() {
		return CardMoney;
	}
	public void setCardMoney(String cardMoney) {
		CardMoney = cardMoney;
	}
	public Integer getPeopleTotal() {
		return PeopleTotal;
	}
	public void setPeopleTotal(Integer peopleTotal) {
		PeopleTotal = peopleTotal;
	}
	public String getFDateTime() {
		return FDateTime;
	}
	public void setFDateTime(String fDateTime) {
		FDateTime = fDateTime;
	}
	@Override
	public String toString() {
		return "Settlement [CashMoney=" + CashMoney + ", SubsidyMoney=" + SubsidyMoney + ", NumberMoney=" + NumberMoney
				+ ", CardMoney=" + CardMoney + ", PeopleTotal=" + PeopleTotal + ", FDateTime=" + FDateTime + "]";
	}
	
}
