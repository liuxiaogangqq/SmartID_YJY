package com.dhsr.smartid.tingchexitong.domain;

public class CarRecord {
    private Integer CarRecordInnerId;
	private String CarNumber;
	private String EntryTime;
	private Integer InOrOut; //0进场  1出场
	private String ChannelId;
	private String ChannelName;
	private String ImagePath;
	private String GUID;
	private String StandbyA;
	private String StandbyB;
	private Integer Type;
	private Integer Flag;
	private Integer RecordID;
	public CarRecord() {
		super();
	}
	public CarRecord(Integer carRecordInnerId, String carNumber, String entryTime, Integer inOrOut, String channelId,
			String channelName, String imagePath, String gUID, String standbyA, String standbyB,Integer type,Integer flag) {
		super();
		CarRecordInnerId = carRecordInnerId;
		CarNumber = carNumber;
		EntryTime = entryTime;
		InOrOut = inOrOut;
		ChannelId = channelId;
		ChannelName = channelName;
		ImagePath = imagePath;
		GUID = gUID;
		StandbyA = standbyA;
		StandbyB = standbyB;
		Type = type;
		Flag = flag;
	}
	
	public Integer getRecordID() {
		return RecordID;
	}
	public void setRecordID(Integer recordID) {
		RecordID = recordID;
	}
	public Integer getFlag() {
		return Flag;
	}
	public void setFlag(Integer flag) {
		Flag = flag;
	}
	public Integer getType() {
		return Type;
	}
	public void setType(Integer type) {
		Type = type;
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
	@Override
	public String toString() {
		return "CarRecord [CarRecordInnerId=" + CarRecordInnerId + ", CarNumber=" + CarNumber + ", EntryTime="
				+ EntryTime + ", InOrOut=" + InOrOut + ", ChannelId=" + ChannelId + ", ChannelName=" + ChannelName
				+ ", ImagePath=" + ImagePath + ", GUID=" + GUID + ", StandbyA=" + StandbyA + ", StandbyB=" + StandbyB
				+ ", Type=" + Type + ", Flag=" + Flag + "]";
	}
}
