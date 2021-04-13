package com.dhsr.smartid.tingchexitong.domain;

public class RecordMsg {
    private String carCode;
	private String inTime;
	private String passTime;
	private String parkID;
	private String inOrOut;
	private String GUID;
	private String channelID;
	private String channelName;
	private String imagePath;
	public RecordMsg() {
		super();
	}
	public RecordMsg(String carCode, String inTime, String passTime, String parkID, String inOrOut, String gUID,
			String channelID, String channelName, String imagePath) {
		super();
		this.carCode = carCode;
		this.inTime = inTime;
		this.passTime = passTime;
		this.parkID = parkID;
		this.inOrOut = inOrOut;
		GUID = gUID;
		this.channelID = channelID;
		this.channelName = channelName;
		this.imagePath = imagePath;
	}
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	public String getPassTime() {
		return passTime;
	}
	public void setPassTime(String passTime) {
		this.passTime = passTime;
	}
	public String getParkID() {
		return parkID;
	}
	public void setParkID(String parkID) {
		this.parkID = parkID;
	}
	public String getInOrOut() {
		return inOrOut;
	}
	public void setInOrOut(String inOrOut) {
		this.inOrOut = inOrOut;
	}
	public String getGUID() {
		return GUID;
	}
	public void setGUID(String gUID) {
		GUID = gUID;
	}
	public String getChannelID() {
		return channelID;
	}
	public void setChannelID(String channelID) {
		this.channelID = channelID;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	@Override
	public String toString() {
		return "RecordMsg [carCode=" + carCode + ", inTime=" + inTime + ", passTime=" + passTime + ", parkID=" + parkID
				+ ", inOrOut=" + inOrOut + ", GUID=" + GUID + ", channelID=" + channelID + ", channelName="
				+ channelName + ", imagePath=" + imagePath + "]";
	}
	
}
