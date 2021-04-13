package com.dhsr.smartid.xiaofeiguanli.domain;

public class MealPrice {
	private Integer MealPriceInnerId;
	private String UserTypeList;
	private String MerchantList;
	private Integer BreakfastFirst;
	private Integer BreakfastNext;
	private Integer BreakfastNums;
	private Integer LunchFirst;
	private Integer LunchNext;
	private Integer LunchNums;
	private Integer DinnerFirst;
	private Integer DinnerNext;
	private Integer DinnerNums;
	private Integer NightFirst;
	private Integer NightNext;
	private Integer NightNums;
	private Integer DelFlag;
	private String Remark;
	private String StandbyA;
	private String StandbyB;
	private String StandbyC;
	private String StandbyD;

	public MealPrice() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MealPrice(Integer mealPriceInnerId, String userTypeList, String merchantList, Integer breakfastFirst,
			Integer breakfastNext, Integer breakfastNums, Integer lunchFirst, Integer lunchNext, Integer lunchNums,
			Integer dinnerFirst, Integer dinnerNext, Integer dinnerNums, Integer nightFirst, Integer nightNext,
			Integer nightNums, Integer delFlag, String remark, String standbyA, String standbyB, String standbyC,
			String standbyD) {
		super();
		MealPriceInnerId = mealPriceInnerId;
		UserTypeList = userTypeList;
		MerchantList = merchantList;
		BreakfastFirst = breakfastFirst;
		BreakfastNext = breakfastNext;
		BreakfastNums = breakfastNums;
		LunchFirst = lunchFirst;
		LunchNext = lunchNext;
		LunchNums = lunchNums;
		DinnerFirst = dinnerFirst;
		DinnerNext = dinnerNext;
		DinnerNums = dinnerNums;
		NightFirst = nightFirst;
		NightNext = nightNext;
		NightNums = nightNums;
		DelFlag = delFlag;
		Remark = remark;
		StandbyA = standbyA;
		StandbyB = standbyB;
		StandbyC = standbyC;
		StandbyD = standbyD;
	}

	public Integer getMealPriceInnerId() {
		return MealPriceInnerId;
	}

	public void setMealPriceInnerId(Integer mealPriceInnerId) {
		MealPriceInnerId = mealPriceInnerId;
	}

	public String getUserTypeList() {
		return UserTypeList;
	}

	public void setUserTypeList(String userTypeList) {
		UserTypeList = userTypeList;
	}

	public String getMerchantList() {
		return MerchantList;
	}

	public void setMerchantList(String merchantList) {
		MerchantList = merchantList;
	}

	public Integer getBreakfastFirst() {
		return BreakfastFirst;
	}

	public void setBreakfastFirst(Integer breakfastFirst) {
		BreakfastFirst = breakfastFirst;
	}

	public Integer getBreakfastNext() {
		return BreakfastNext;
	}

	public void setBreakfastNext(Integer breakfastNext) {
		BreakfastNext = breakfastNext;
	}

	public Integer getBreakfastNums() {
		return BreakfastNums;
	}

	public void setBreakfastNums(Integer breakfastNums) {
		BreakfastNums = breakfastNums;
	}

	public Integer getLunchFirst() {
		return LunchFirst;
	}

	public void setLunchFirst(Integer lunchFirst) {
		LunchFirst = lunchFirst;
	}

	public Integer getLunchNext() {
		return LunchNext;
	}

	public void setLunchNext(Integer lunchNext) {
		LunchNext = lunchNext;
	}

	public Integer getLunchNums() {
		return LunchNums;
	}

	public void setLunchNums(Integer lunchNums) {
		LunchNums = lunchNums;
	}

	public Integer getDinnerFirst() {
		return DinnerFirst;
	}

	public void setDinnerFirst(Integer dinnerFirst) {
		DinnerFirst = dinnerFirst;
	}

	public Integer getDinnerNext() {
		return DinnerNext;
	}

	public void setDinnerNext(Integer dinnerNext) {
		DinnerNext = dinnerNext;
	}

	public Integer getDinnerNums() {
		return DinnerNums;
	}

	public void setDinnerNums(Integer dinnerNums) {
		DinnerNums = dinnerNums;
	}

	public Integer getNightFirst() {
		return NightFirst;
	}

	public void setNightFirst(Integer nightFirst) {
		NightFirst = nightFirst;
	}

	public Integer getNightNext() {
		return NightNext;
	}

	public void setNightNext(Integer nightNext) {
		NightNext = nightNext;
	}

	public Integer getNightNums() {
		return NightNums;
	}

	public void setNightNums(Integer nightNums) {
		NightNums = nightNums;
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
		return "MealPrice [MealPriceInnerId=" + MealPriceInnerId + ", UserTypeList=" + UserTypeList + ", MerchantList="
				+ MerchantList + ", BreakfastFirst=" + BreakfastFirst + ", BreakfastNext=" + BreakfastNext
				+ ", BreakfastNums=" + BreakfastNums + ", LunchFirst=" + LunchFirst + ", LunchNext=" + LunchNext
				+ ", LunchNums=" + LunchNums + ", DinnerFirst=" + DinnerFirst + ", DinnerNext=" + DinnerNext
				+ ", DinnerNums=" + DinnerNums + ", NightFirst=" + NightFirst + ", NightNext=" + NightNext
				+ ", NightNums=" + NightNums + ", DelFlag=" + DelFlag + ", Remark=" + Remark + ", StandbyA=" + StandbyA
				+ ", StandbyB=" + StandbyB + ", StandbyC=" + StandbyC + ", StandbyD=" + StandbyD + "]";
	}


}
