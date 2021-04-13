package com.dhsr.smartid.renshiyewu.domain;

public class MarkLog {
    private Integer LogType;
	private Integer LogInnerId;
	private Integer RowInnerId;
	private String RowId;
	private String RowName;
	private Integer OperatorInnerId;
	private String LogDatetime;
	private String Remark;
	private String OperatorId;
	private String OperatorName;
	private String UserId;
	private String UserName;
	private Integer UserInnerId;
	private Integer DepartmentInnerId;
	private String MarkId;
	private Integer DelFlag;
	private String DepartmentName;
	private Integer MarkTypeInnerId;
	private String MarkTypeName;
	public MarkLog() {
		super();
	}
	public MarkLog(Integer logType, Integer logInnerId, Integer rowInnerId, String rowId, String rowName,
			Integer operatorInnerId, String logDatetime, String remark, String operatorId, String operatorName,
			String userId, String userName, Integer userInnerId, Integer departmentInnerId, String markId,
			Integer delFlag) {
		super();
		LogType = logType;
		LogInnerId = logInnerId;
		RowInnerId = rowInnerId;
		RowId = rowId;
		RowName = rowName;
		OperatorInnerId = operatorInnerId;
		LogDatetime = logDatetime;
		Remark = remark;
		OperatorId = operatorId;
		OperatorName = operatorName;
		UserId = userId;
		UserName = userName;
		UserInnerId = userInnerId;
		DepartmentInnerId = departmentInnerId;
		MarkId = markId;
		DelFlag = delFlag;
	}
	
	public Integer getMarkTypeInnerId() {
		return MarkTypeInnerId;
	}
	public void setMarkTypeInnerId(Integer markTypeInnerId) {
		MarkTypeInnerId = markTypeInnerId;
	}
	public String getMarkTypeName() {
		return MarkTypeName;
	}
	public void setMarkTypeName(String markTypeName) {
		MarkTypeName = markTypeName;
	}
	public String getDepartmentName() {
		return DepartmentName;
	}
	public void setDepartmentName(String departmentName) {
		DepartmentName = departmentName;
	}
	public Integer getLogType() {
		return LogType;
	}
	public void setLogType(Integer logType) {
		LogType = logType;
	}
	public Integer getLogInnerId() {
		return LogInnerId;
	}
	public void setLogInnerId(Integer logInnerId) {
		LogInnerId = logInnerId;
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
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public Integer getUserInnerId() {
		return UserInnerId;
	}
	public void setUserInnerId(Integer userInnerId) {
		UserInnerId = userInnerId;
	}
	public Integer getDepartmentInnerId() {
		return DepartmentInnerId;
	}
	public void setDepartmentInnerId(Integer departmentInnerId) {
		DepartmentInnerId = departmentInnerId;
	}
	public String getMarkId() {
		return MarkId;
	}
	public void setMarkId(String markId) {
		MarkId = markId;
	}
	public Integer getDelFlag() {
		return DelFlag;
	}
	public void setDelFlag(Integer delFlag) {
		DelFlag = delFlag;
	}
	@Override
	public String toString() {
		return "MarkLog [LogType=" + LogType + ", LogInnerId=" + LogInnerId + ", RowInnerId=" + RowInnerId + ", RowId="
				+ RowId + ", RowName=" + RowName + ", OperatorInnerId=" + OperatorInnerId + ", LogDatetime="
				+ LogDatetime + ", Remark=" + Remark + ", OperatorId=" + OperatorId + ", OperatorName=" + OperatorName
				+ ", UserId=" + UserId + ", UserName=" + UserName + ", UserInnerId=" + UserInnerId
				+ ", DepartmentInnerId=" + DepartmentInnerId + ", MarkId=" + MarkId + ", DelFlag=" + DelFlag + "]";
	}
	
}
