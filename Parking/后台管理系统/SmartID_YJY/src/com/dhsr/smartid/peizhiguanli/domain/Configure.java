package com.dhsr.smartid.peizhiguanli.domain;

public class Configure {
	private String Page;
	private String Property;
	private Integer Enabled;
	private Integer EditName;
	private String InitialName;
	private String Name;
	private Integer Length;

	public Configure() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Configure(String page, String property, Integer enabled, Integer editName, String initialName, String name, Integer length) {
		super();
		Page = page;
		Property = property;
		Enabled = enabled;
		EditName = editName;
		InitialName = initialName;
		Name = name;
		Length = length;
	}

	public String getPage() {
		return Page;
	}

	public void setPage(String page) {
		Page = page;
	}

	public String getProperty() {
		return Property;
	}

	public void setProperty(String property) {
		Property = property;
	}

	public Integer getEnabled() {
		return Enabled;
	}

	public void setEnabled(Integer enabled) {
		Enabled = enabled;
	}

	public Integer getEditName() {
		return EditName;
	}

	public void setEditName(Integer editName) {
		EditName = editName;
	}

	public String getInitialName() {
		return InitialName;
	}

	public void setInitialName(String initialName) {
		InitialName = initialName;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Integer getLength() {
		return Length;
	}

	public void setLength(Integer length) {
		Length = length;
	}

	@Override
	public String toString() {
		return "Configure [Page=" + Page + ", Property=" + Property + ", Enabled=" + Enabled + ", EditName=" + EditName + ", InitialName=" + InitialName + ", Name=" + Name + ", Length=" + Length + "]";
	}

}
