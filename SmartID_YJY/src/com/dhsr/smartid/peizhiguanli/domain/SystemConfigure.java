package com.dhsr.smartid.peizhiguanli.domain;

public class SystemConfigure {
	private String ConfigureId;
	private String ConfigureName;
	private String ConfigureValue;

	public SystemConfigure() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SystemConfigure(String configureId, String configureName, String configureValue) {
		super();
		ConfigureId = configureId;
		ConfigureName = configureName;
		ConfigureValue = configureValue;
	}

	public String getConfigureId() {
		return ConfigureId;
	}

	public void setConfigureId(String configureId) {
		ConfigureId = configureId;
	}

	public String getConfigureName() {
		return ConfigureName;
	}

	public void setConfigureName(String configureName) {
		ConfigureName = configureName;
	}

	public String getConfigureValue() {
		return ConfigureValue;
	}

	public void setConfigureValue(String configureValue) {
		ConfigureValue = configureValue;
	}

	@Override
	public String toString() {
		return "SystemConfigure [ConfigureId=" + ConfigureId + ", ConfigureName=" + ConfigureName + ", ConfigureValue=" + ConfigureValue + "]";
	}

}
