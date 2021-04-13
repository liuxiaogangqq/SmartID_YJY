package com.dhsr.smartid.xiaofeiguanli.domain;

public class ConStatistics {
	private Long PersonMoney;
	private Long AllowanceMoney;
	private Long NumberMoney;
	private Long Money;
	private Long DiscountMoney;
	private Long Proportion;
	private Integer MerchantInnerId;
	private Integer AppInnerId;
	private Integer AreaInnerId;
	private String RowName;

	public ConStatistics() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConStatistics(Long personMoney, Long allowanceMoney, Long numberMoney, Long money, Long discountMoney,
			Long proportion, Integer merchantInnerId, Integer appInnerId, Integer areaInnerId, String rowName) {
		super();
		PersonMoney = personMoney;
		AllowanceMoney = allowanceMoney;
		NumberMoney = numberMoney;
		Money = money;
		DiscountMoney = discountMoney;
		Proportion = proportion;
		MerchantInnerId = merchantInnerId;
		AppInnerId = appInnerId;
		AreaInnerId = areaInnerId;
		RowName = rowName;
	}

	public Long getPersonMoney() {
		return PersonMoney;
	}

	public void setPersonMoney(Long personMoney) {
		PersonMoney = personMoney;
	}

	public Long getAllowanceMoney() {
		return AllowanceMoney;
	}

	public void setAllowanceMoney(Long allowanceMoney) {
		AllowanceMoney = allowanceMoney;
	}

	public Long getNumberMoney() {
		return NumberMoney;
	}

	public void setNumberMoney(Long numberMoney) {
		NumberMoney = numberMoney;
	}

	public Long getMoney() {
		return Money;
	}

	public void setMoney(Long money) {
		Money = money;
	}

	public Long getDiscountMoney() {
		return DiscountMoney;
	}

	public void setDiscountMoney(Long discountMoney) {
		DiscountMoney = discountMoney;
	}

	public Long getProportion() {
		return Proportion;
	}

	public void setProportion(Long proportion) {
		Proportion = proportion;
	}

	public Integer getMerchantInnerId() {
		return MerchantInnerId;
	}

	public void setMerchantInnerId(Integer merchantInnerId) {
		MerchantInnerId = merchantInnerId;
	}

	public Integer getAppInnerId() {
		return AppInnerId;
	}

	public void setAppInnerId(Integer appInnerId) {
		AppInnerId = appInnerId;
	}

	public Integer getAreaInnerId() {
		return AreaInnerId;
	}

	public void setAreaInnerId(Integer areaInnerId) {
		AreaInnerId = areaInnerId;
	}

	public String getRowName() {
		return RowName;
	}

	public void setRowName(String rowName) {
		RowName = rowName;
	}

	@Override
	public String toString() {
		return "ConStatistics [PersonMoney=" + PersonMoney + ", AllowanceMoney=" + AllowanceMoney + ", NumberMoney="
				+ NumberMoney + ", Money=" + Money + ", DiscountMoney=" + DiscountMoney + ", Proportion=" + Proportion
				+ ", MerchantInnerId=" + MerchantInnerId + ", AppInnerId=" + AppInnerId + ", AreaInnerId=" + AreaInnerId
				+ ", RowName=" + RowName + "]";
	}


}
