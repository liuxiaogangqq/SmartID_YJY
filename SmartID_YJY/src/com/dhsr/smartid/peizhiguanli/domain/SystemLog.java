package com.dhsr.smartid.peizhiguanli.domain;

public class SystemLog {
	private Integer LogInnerId;
	private String TableName;
	private Integer RowInnerId;
	private String RowId;
	private String RowName;
	private Integer LogType;
	private String BeforeData;
	private String AfterData;
	private Integer OperatorInnerId;
	private String LogDatetime;
	private String Remark;

	public SystemLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SystemLog(Integer logInnerId, String tableName, Integer rowInnerId, String rowId, String rowName, Integer logType, String beforeData, String afterData, Integer operatorInnerId, String logDatetime, String remark) {
		super();
		LogInnerId = logInnerId;
		TableName = tableName;
		RowInnerId = rowInnerId;
		RowId = rowId;
		RowName = rowName;
		LogType = logType;
		BeforeData = beforeData;
		AfterData = afterData;
		OperatorInnerId = operatorInnerId;
		LogDatetime = logDatetime;
		Remark = remark;
	}

	public Integer getLogInnerId() {
		return LogInnerId;
	}

	public void setLogInnerId(Integer logInnerId) {
		LogInnerId = logInnerId;
	}

	public String getTableName() {
		return TableName;
	}

	public void setTableName(String tableName) {
		TableName = tableName;
	}

	public Integer getRowInnerId() {
		return RowInnerId;
	}

	public void setRowInnerId(Integer rowInnerId) {
		RowInnerId = rowInnerId;
	}

	public String getRowId() {
		return RowId;
	}

	public void setRowId(String rowId) {
		RowId = rowId;
	}

	public String getRowName() {
		return RowName;
	}

	public void setRowName(String rowName) {
		RowName = rowName;
	}

	public Integer getLogType() {
		return LogType;
	}

	public void setLogType(Integer logType) {
		LogType = logType;
	}

	public String getBeforeData() {
		return BeforeData;
	}

	public void setBeforeData(String beforeData) {
		BeforeData = beforeData;
	}

	public String getAfterData() {
		return AfterData;
	}

	public void setAfterData(String afterData) {
		AfterData = afterData;
	}

	public Integer getOperatorInnerId() {
		return OperatorInnerId;
	}

	public void setOperatorInnerId(Integer operatorInnerId) {
		OperatorInnerId = operatorInnerId;
	}

	public String getLogDatetime() {
		return LogDatetime;
	}

	public void setLogDatetime(String logDatetime) {
		LogDatetime = logDatetime;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	@Override
	public String toString() {
		return "SystemLog [LogInnerId=" + LogInnerId + ", TableName=" + TableName + ", RowInnerId=" + RowInnerId + ", RowId=" + RowId + ", RowName=" + RowName + ", LogType=" + LogType + ", BeforeData=" + BeforeData + ", AfterData=" + AfterData + ", OperatorInnerId=" + OperatorInnerId + ", LogDatetime=" + LogDatetime + ", Remark=" + Remark + "]";
	}

}
