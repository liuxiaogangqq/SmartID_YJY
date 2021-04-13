package com.dhsr.smartid.tongbu.domain;

public class LFDep {
    private String pk_firstdept;
	private String first_deptcode;
	private String first_deptname;
	private String showorder1;
	private String pk_seconddept;
	private String second_deptcode;
	private String second_deptname;
	private String showorder2;
	private String pk_thirddept;
	private String third_deptcode;
	private String third_deptname;
	private String showorder3;
	private String pk_fourtdept;
	private String fourth_deptcode;
	private String fourth_deptname;
	private String showorder4;
	private String deptclass;
	private String pk_corp;
	private String unitcode;
	private String unitname;
	public LFDep() {
		super();
	}
	public LFDep(String pk_firstdept, String first_deptcode, String first_deptname, String showorder1,
			String pk_seconddept, String second_deptcode, String second_deptname, String showorder2,
			String pk_thirddept, String third_deptcode, String third_deptname, String showorder3, String pk_fourtdept,
			String fourth_deptcode, String fourth_deptname, String showorder4, String deptclass, String pk_corp,
			String unitcode, String unitname) {
		super();
		this.pk_firstdept = pk_firstdept;
		this.first_deptcode = first_deptcode;
		this.first_deptname = first_deptname;
		this.showorder1 = showorder1;
		this.pk_seconddept = pk_seconddept;
		this.second_deptcode = second_deptcode;
		this.second_deptname = second_deptname;
		this.showorder2 = showorder2;
		this.pk_thirddept = pk_thirddept;
		this.third_deptcode = third_deptcode;
		this.third_deptname = third_deptname;
		this.showorder3 = showorder3;
		this.pk_fourtdept = pk_fourtdept;
		this.fourth_deptcode = fourth_deptcode;
		this.fourth_deptname = fourth_deptname;
		this.showorder4 = showorder4;
		this.deptclass = deptclass;
		this.pk_corp = pk_corp;
		this.unitcode = unitcode;
		this.unitname = unitname;
	}
	public String getPk_firstdept() {
		return pk_firstdept;
	}
	public void setPk_firstdept(String pk_firstdept) {
		this.pk_firstdept = pk_firstdept;
	}
	public String getFirst_deptcode() {
		return first_deptcode;
	}
	public void setFirst_deptcode(String first_deptcode) {
		this.first_deptcode = first_deptcode;
	}
	public String getFirst_deptname() {
		return first_deptname;
	}
	public void setFirst_deptname(String first_deptname) {
		this.first_deptname = first_deptname;
	}
	public String getShoworder1() {
		return showorder1;
	}
	public void setShoworder1(String showorder1) {
		this.showorder1 = showorder1;
	}
	public String getPk_seconddept() {
		return pk_seconddept;
	}
	public void setPk_seconddept(String pk_seconddept) {
		this.pk_seconddept = pk_seconddept;
	}
	public String getSecond_deptcode() {
		return second_deptcode;
	}
	public void setSecond_deptcode(String second_deptcode) {
		this.second_deptcode = second_deptcode;
	}
	public String getSecond_deptname() {
		return second_deptname;
	}
	public void setSecond_deptname(String second_deptname) {
		this.second_deptname = second_deptname;
	}
	public String getShoworder2() {
		return showorder2;
	}
	public void setShoworder2(String showorder2) {
		this.showorder2 = showorder2;
	}
	public String getPk_thirddept() {
		return pk_thirddept;
	}
	public void setPk_thirddept(String pk_thirddept) {
		this.pk_thirddept = pk_thirddept;
	}
	public String getThird_deptcode() {
		return third_deptcode;
	}
	public void setThird_deptcode(String third_deptcode) {
		this.third_deptcode = third_deptcode;
	}
	public String getThird_deptname() {
		return third_deptname;
	}
	public void setThird_deptname(String third_deptname) {
		this.third_deptname = third_deptname;
	}
	public String getShoworder3() {
		return showorder3;
	}
	public void setShoworder3(String showorder3) {
		this.showorder3 = showorder3;
	}
	public String getPk_fourtdept() {
		return pk_fourtdept;
	}
	public void setPk_fourtdept(String pk_fourtdept) {
		this.pk_fourtdept = pk_fourtdept;
	}
	public String getFourth_deptcode() {
		return fourth_deptcode;
	}
	public void setFourth_deptcode(String fourth_deptcode) {
		this.fourth_deptcode = fourth_deptcode;
	}
	public String getFourth_deptname() {
		return fourth_deptname;
	}
	public void setFourth_deptname(String fourth_deptname) {
		this.fourth_deptname = fourth_deptname;
	}
	public String getShoworder4() {
		return showorder4;
	}
	public void setShoworder4(String showorder4) {
		this.showorder4 = showorder4;
	}
	public String getDeptclass() {
		return deptclass;
	}
	public void setDeptclass(String deptclass) {
		this.deptclass = deptclass;
	}
	public String getPk_corp() {
		return pk_corp;
	}
	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}
	public String getUnitcode() {
		return unitcode;
	}
	public void setUnitcode(String unitcode) {
		this.unitcode = unitcode;
	}
	public String getUnitname() {
		return unitname;
	}
	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}
	@Override
	public String toString() {
		return "LFDep [pk_firstdept=" + pk_firstdept + ", first_deptcode=" + first_deptcode + ", first_deptname="
				+ first_deptname + ", showorder1=" + showorder1 + ", pk_seconddept=" + pk_seconddept
				+ ", second_deptcode=" + second_deptcode + ", second_deptname=" + second_deptname + ", showorder2="
				+ showorder2 + ", pk_thirddept=" + pk_thirddept + ", third_deptcode=" + third_deptcode
				+ ", third_deptname=" + third_deptname + ", showorder3=" + showorder3 + ", pk_fourtdept=" + pk_fourtdept
				+ ", fourth_deptcode=" + fourth_deptcode + ", fourth_deptname=" + fourth_deptname + ", showorder4="
				+ showorder4 + ", deptclass=" + deptclass + ", pk_corp=" + pk_corp + ", unitcode=" + unitcode
				+ ", unitname=" + unitname + "]";
	}
	
}
