package com.dhsr.smartid.xiaofeiguanli.domain;

public class MenuStatistics {
	private String MenuName;
	private Integer Number;
	public MenuStatistics() {
		super();
	}
	public MenuStatistics(String menuName, Integer number) {
		super();
		MenuName = menuName;
		Number = number;
	}
	public String getMenuName() {
		return MenuName;
	}
	public void setMenuName(String menuName) {
		MenuName = menuName;
	}
	public Integer getNumber() {
		return Number;
	}
	public void setNumber(Integer number) {
		Number = number;
	}
	@Override
	public String toString() {
		return "MenuStatistics [MenuName=" + MenuName + ", number=" + Number + "]";
	};
	
}
