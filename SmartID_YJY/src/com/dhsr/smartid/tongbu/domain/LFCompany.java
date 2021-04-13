package com.dhsr.smartid.tongbu.domain;

public class LFCompany {
    private String unitname;
	private String unitcode;
	private String pk_corp;
	private String fathercorp;
	private String showorder;
	public LFCompany(String unitname, String unitcode, String pk_corp, String fathercorp, String showorder) {
		super();
		this.unitname = unitname;
		this.unitcode = unitcode;
		this.pk_corp = pk_corp;
		this.fathercorp = fathercorp;
		this.showorder = showorder;
	}
	public LFCompany() {
		super();
	}
	public String getUnitname() {
		return unitname;
	}
	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}
	public String getUnitcode() {
		return unitcode;
	}
	public void setUnitcode(String unitcode) {
		this.unitcode = unitcode;
	}
	public String getPk_corp() {
		return pk_corp;
	}
	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}
	public String getFathercorp() {
		return fathercorp;
	}
	public void setFathercorp(String fathercorp) {
		this.fathercorp = fathercorp;
	}
	public String getShoworder() {
		return showorder;
	}
	public void setShoworder(String showorder) {
		this.showorder = showorder;
	}
	@Override
	public String toString() {
		return "LFCompany [unitname=" + unitname + ", unitcode=" + unitcode + ", pk_corp=" + pk_corp + ", fathercorp="
				+ fathercorp + ", showorder=" + showorder + "]";
	}
	
}
