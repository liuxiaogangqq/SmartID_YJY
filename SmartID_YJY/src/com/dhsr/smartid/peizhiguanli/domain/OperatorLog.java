package com.dhsr.smartid.peizhiguanli.domain;

public class OperatorLog {
	private Integer OperatorLogInnerId;
	private Integer OperatorInnerId;
	private String OperatorId;
	private String OperatorName;
	private String Password;
	private Integer LogType;
	private String LogDatetime;
	private String LoginIp;
	private String Remark;

	public OperatorLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OperatorLog(Integer operatorLogInnerId, Integer operatorInnerId, String operatorId, String operatorName, String password, Integer logType, String logDatetime, String loginIp, String remark) {
		super();
		OperatorLogInnerId = operatorLogInnerId;
		OperatorInnerId = operatorInnerId;
		OperatorId = operatorId;
		OperatorName = operatorName;
		Password = password;
		LogType = logType;
		LogDatetime = logDatetime;
		LoginIp = loginIp;
		Remark = remark;
	}

	public Integer getOperatorLogInnerId() {
		return OperatorLogInnerId;
	}

	public void setOperatorLogInnerId(Integer operatorLogInnerId) {
		OperatorLogInnerId = operatorLogInnerId;
	}

	public Integer getOperatorInnerId() {
		return OperatorInnerId;
	}

	public void setOperatorInnerId(Integer operatorInnerId) {
		OperatorInnerId = operatorInnerId;
	}

	public String getOperatorId() {
		return OperatorId;
	}

	public void setOperatorId(String operatorId) {
		OperatorId = operatorId;
	}

	public String getOperatorName() {
		return OperatorName;
	}

	public void setOperatorName(String operatorName) {
		OperatorName = operatorName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public Integer getLogType() {
		return LogType;
	}

	public void setLogType(Integer logType) {
		LogType = logType;
	}

	public String getLogDatetime() {
		return LogDatetime;
	}

	public void setLogDatetime(String logDatetime) {
		LogDatetime = logDatetime;
	}

	public String getLoginIp() {
		return LoginIp;
	}

	public void setLoginIp(String loginIp) {
		LoginIp = loginIp;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	@Override
	public String toString() {
		return "OperatorLog [OperatorLogInnerId=" + OperatorLogInnerId + ", OperatorInnerId=" + OperatorInnerId + ", OperatorId=" + OperatorId + ", OperatorName=" + OperatorName + ", Password=" + Password + ", LogType=" + LogType + ", LogDatetime=" + LogDatetime + ", LoginIp=" + LoginIp + ", Remark=" + Remark + "]";
	}

}
