package com.dhsr.smartid.tingchexitong.domain;

public class CarRecord_User {
    private Integer CarRecordInnerId;
	private String CarNumber;
	private String EntryTime;
	private Integer InOrOut;
	private String ChannelId;
	private String ChannelName;
	private String ImagePath;
	private String GUID;
	private Integer Type;
	private Integer Flag;
	private String StandbyB;
	private String StandbyA;
	private Integer CarTypeInnerId;
	private String StartTime;
	private String EndTime;
	private String UserId;
	private String UserName;
	private Integer DepartmentInnerId;
	private String DepartmentName;
	private Integer UserInnerId;
	public CarRecord_User() {
		super();
	}
	public CarRecord_User(Integer carRecordInnerId, String carNumber, String entryTime, Integer inOrOut,
			String channelId, String channelName, String imagePath, String gUID, Integer type, Integer flag,
			String standbyB, String standbyA, Integer carTypeInnerId, String startTime, String endTime, String userId,
			String userName, Integer departmentInnerId, String departmentName, Integer userInnerId) {
		super();
		CarRecordInnerId = carRecordInnerId;
		CarNumber = carNumber;
		EntryTime = entryTime;
		InOrOut = inOrOut;
		ChannelId = channelId;
		ChannelName = channelName;
		ImagePath = imagePath;
		GUID = gUID;
		Type = type;
		Flag = flag;
		StandbyB = standbyB;
		StandbyA = standbyA;
		CarTypeInnerId = carTypeInnerId;
		StartTime = startTime;
		EndTime = endTime;
		UserId = userId;
		UserName = userName;
		DepartmentInnerId = departmentInnerId;
		DepartmentName = departmentName;
		UserInnerId = userInnerId;
	}
	
	public Integer getUserInnerId() {
		return UserInnerId;
	}
	public void setUserInnerId(Integer userInnerId) {
		UserInnerId = userInnerId;
	}
	public Integer getCarRecordInnerId() {
		return CarRecordInnerId;
	}
	public void setCarRecordInnerId(Integer carRecordInnerId) {
		CarRecordInnerId = carRecordInnerId;
	}
	public String getCarNumber() {
		return CarNumber;
	}
	public void setCarNumber(String carNumber) {
		CarNumber = carNumber;
	}
	public String getEntryTime() {
		return EntryTime;
	}
	public void setEntryTime(String entryTime) {
		EntryTime = entryTime;
	}
	public Integer getInOrOut() {
		return InOrOut;
	}
	public void setInOrOut(Integer inOrOut) {
		InOrOut = inOrOut;
	}
	public String getChannelId() {
		return ChannelId;
	}
	public void setChannelId(String channelId) {
		ChannelId = channelId;
	}
	public String getChannelName() {
		return ChannelName;
	}
	public void setChannelName(String channelName) {
		ChannelName = channelName;
	}
	public String getImagePath() {
		return ImagePath;
	}
	public void setImagePath(String imagePath) {
		ImagePath = imagePath;
	}
	public String getGUID() {
		return GUID;
	}
	public void setGUID(String gUID) {
		GUID = gUID;
	}
	public Integer getType() {
		return Type;
	}
	public void setType(Integer type) {
		Type = type;
	}
	public Integer getFlag() {
		return Flag;
	}
	public void setFlag(Integer flag) {
		Flag = flag;
	}
	public String getStandbyB() {
		return StandbyB;
	}
	public void setStandbyB(String standbyB) {
		StandbyB = standbyB;
	}
	public String getStandbyA() {
		return StandbyA;
	}
	public void setStandbyA(String standbyA) {
		StandbyA = standbyA;
	}
	public Integer getCarTypeInnerId() {
		return CarTypeInnerId;
	}
	public void setCarTypeInnerId(Integer carTypeInnerId) {
		CarTypeInnerId = carTypeInnerId;
	}
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	public String getEndTime() {
		return EndTime;
	}
	public void setEndTime(String endTime) {
		EndTime = endTime;
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
	public Integer getDepartmentInnerId() {
		return DepartmentInnerId;
	}
	public void setDepartmentInnerId(Integer departmentInnerId) {
		DepartmentInnerId = departmentInnerId;
	}
	public String getDepartmentName() {
		return DepartmentName;
	}
	public void setDepartmentName(String departmentName) {
		DepartmentName = departmentName;
	}
	@Override
	public String toString() {
		return "CarRecord_User [CarRecordInnerId=" + CarRecordInnerId + ", CarNumber=" + CarNumber + ", EntryTime="
				+ EntryTime + ", InOrOut=" + InOrOut + ", ChannelId=" + ChannelId + ", ChannelName=" + ChannelName
				+ ", ImagePath=" + ImagePath + ", GUID=" + GUID + ", Type=" + Type + ", Flag=" + Flag + ", StandbyB="
				+ StandbyB + ", StandbyA=" + StandbyA + ", CarTypeInnerId=" + CarTypeInnerId + ", StartTime="
				+ StartTime + ", EndTime=" + EndTime + ", UserId=" + UserId + ", UserName=" + UserName
				+ ", DepartmentInnerId=" + DepartmentInnerId + ", DepartmentName=" + DepartmentName + ", UserInnerId="
				+ UserInnerId + "]";
	}
	
}
