package com.dhsr.smartid.tingchexitong.domain;

public class YYCarRecord_User {
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
	private String StandbyA;
	private String StandbyB;
	private String VisitorName;
	private String VisitorCompany;
	public YYCarRecord_User() {
		super();
	}
	public YYCarRecord_User(Integer carRecordInnerId, String carNumber, String entryTime, Integer inOrOut,
			String channelId, String channelName, String imagePath, String gUID, Integer type, Integer flag,
			String standbyA, String standbyB, String visitorName, String visitorCompany) {
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
		StandbyA = standbyA;
		StandbyB = standbyB;
		VisitorName = visitorName;
		VisitorCompany = visitorCompany;
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
	public String getVisitorName() {
		return VisitorName;
	}
	public void setVisitorName(String visitorName) {
		VisitorName = visitorName;
	}
	public String getVisitorCompany() {
		return VisitorCompany;
	}
	public void setVisitorCompany(String visitorCompany) {
		VisitorCompany = visitorCompany;
	}
	@Override
	public String toString() {
		return "YYCarRecord_User [CarRecordInnerId=" + CarRecordInnerId + ", CarNumber=" + CarNumber + ", EntryTime="
				+ EntryTime + ", InOrOut=" + InOrOut + ", ChannelId=" + ChannelId + ", ChannelName=" + ChannelName
				+ ", ImagePath=" + ImagePath + ", GUID=" + GUID + ", Type=" + Type + ", Flag=" + Flag + ", StandbyA="
				+ StandbyA + ", StandbyB=" + StandbyB + ", VisitorName=" + VisitorName + ", VisitorCompany="
				+ VisitorCompany + "]";
	}
}
