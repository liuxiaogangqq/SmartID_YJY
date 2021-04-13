package com.dhsr.smartid.tingchexitong.domain;

public class OrderRecord {
    private Integer RecordInnerId;
	private Integer VisitorInfoInnerId;
	private String VisitorName;
	private String VisitorEmail;
	private String VisitorIDCode;
	private String VisitorCompany;
	private Integer UserInnerId;
	private String VisitorThing;
	private String VisitorPhoto;
	private String VisitorInPhoto;
	private Integer VisitorNum;
	private Integer CarTypeInnerId;
	private String VisitorCarCode;
	private String VisitorBeginTime;
	private String VisitorEndTime;
	private String RecordTime;
	private Integer OrderType; //预约类型 1：协助预约 2：自主预约
	private String OrderCode;
	private String UserName;
	private String DepartMentName;
	private Integer VisitorState; //申请状态   0 申请中    1同意    2拒绝    3已取消  4 已进场   5 已支付  6已完成
	private String ApproveTime;
	private Integer CameraState; // 拍照状态
	private String Remark;
	private String CardId;
	private String ACInfo;
	public OrderRecord() {
		super();
	}
	public OrderRecord(Integer recordInnerId, Integer visitorInfoInnerId, String visitorName, String visitorEmail,
			String visitorIDCode, String visitorCompany, Integer userInnerId, String visitorThing, String visitorPhoto,
			String visitorInPhoto, Integer visitorNum, Integer carTypeInnerId, String visitorCarCode,
			String visitorBeginTime, String visitorEndTime, String recordTime, Integer orderType, String orderCode,
			String userName, String departMentName, Integer visitorState, String approveTime, Integer cameraState,
			String remark, String cardId, String aCInfo) {
		super();
		RecordInnerId = recordInnerId;
		VisitorInfoInnerId = visitorInfoInnerId;
		VisitorName = visitorName;
		VisitorEmail = visitorEmail;
		VisitorIDCode = visitorIDCode;
		VisitorCompany = visitorCompany;
		UserInnerId = userInnerId;
		VisitorThing = visitorThing;
		VisitorPhoto = visitorPhoto;
		VisitorInPhoto = visitorInPhoto;
		VisitorNum = visitorNum;
		CarTypeInnerId = carTypeInnerId;
		VisitorCarCode = visitorCarCode;
		VisitorBeginTime = visitorBeginTime;
		VisitorEndTime = visitorEndTime;
		RecordTime = recordTime;
		OrderType = orderType;
		OrderCode = orderCode;
		UserName = userName;
		DepartMentName = departMentName;
		VisitorState = visitorState;
		ApproveTime = approveTime;
		CameraState = cameraState;
		Remark = remark;
		CardId = cardId;
		ACInfo = aCInfo;
	}
	public Integer getRecordInnerId() {
		return RecordInnerId;
	}
	public void setRecordInnerId(Integer recordInnerId) {
		RecordInnerId = recordInnerId;
	}
	public Integer getVisitorInfoInnerId() {
		return VisitorInfoInnerId;
	}
	public void setVisitorInfoInnerId(Integer visitorInfoInnerId) {
		VisitorInfoInnerId = visitorInfoInnerId;
	}
	public String getVisitorName() {
		return VisitorName;
	}
	public void setVisitorName(String visitorName) {
		VisitorName = visitorName;
	}
	public String getVisitorEmail() {
		return VisitorEmail;
	}
	public void setVisitorEmail(String visitorEmail) {
		VisitorEmail = visitorEmail;
	}
	public String getVisitorIDCode() {
		return VisitorIDCode;
	}
	public void setVisitorIDCode(String visitorIDCode) {
		VisitorIDCode = visitorIDCode;
	}
	public String getVisitorCompany() {
		return VisitorCompany;
	}
	public void setVisitorCompany(String visitorCompany) {
		VisitorCompany = visitorCompany;
	}
	public Integer getUserInnerId() {
		return UserInnerId;
	}
	public void setUserInnerId(Integer userInnerId) {
		UserInnerId = userInnerId;
	}
	public String getVisitorThing() {
		return VisitorThing;
	}
	public void setVisitorThing(String visitorThing) {
		VisitorThing = visitorThing;
	}
	public String getVisitorPhoto() {
		return VisitorPhoto;
	}
	public void setVisitorPhoto(String visitorPhoto) {
		VisitorPhoto = visitorPhoto;
	}
	public String getVisitorInPhoto() {
		return VisitorInPhoto;
	}
	public void setVisitorInPhoto(String visitorInPhoto) {
		VisitorInPhoto = visitorInPhoto;
	}
	public Integer getVisitorNum() {
		return VisitorNum;
	}
	public void setVisitorNum(Integer visitorNum) {
		VisitorNum = visitorNum;
	}
	public Integer getCarTypeInnerId() {
		return CarTypeInnerId;
	}
	public void setCarTypeInnerId(Integer carTypeInnerId) {
		CarTypeInnerId = carTypeInnerId;
	}
	public String getVisitorCarCode() {
		return VisitorCarCode;
	}
	public void setVisitorCarCode(String visitorCarCode) {
		VisitorCarCode = visitorCarCode;
	}
	public String getVisitorBeginTime() {
		return VisitorBeginTime;
	}
	public void setVisitorBeginTime(String visitorBeginTime) {
		VisitorBeginTime = visitorBeginTime;
	}
	public String getVisitorEndTime() {
		return VisitorEndTime;
	}
	public void setVisitorEndTime(String visitorEndTime) {
		VisitorEndTime = visitorEndTime;
	}
	public String getRecordTime() {
		return RecordTime;
	}
	public void setRecordTime(String recordTime) {
		RecordTime = recordTime;
	}
	public Integer getOrderType() {
		return OrderType;
	}
	public void setOrderType(Integer orderType) {
		OrderType = orderType;
	}
	public String getOrderCode() {
		return OrderCode;
	}
	public void setOrderCode(String orderCode) {
		OrderCode = orderCode;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getDepartMentName() {
		return DepartMentName;
	}
	public void setDepartMentName(String departMentName) {
		DepartMentName = departMentName;
	}
	public Integer getVisitorState() {
		return VisitorState;
	}
	public void setVisitorState(Integer visitorState) {
		VisitorState = visitorState;
	}
	public String getApproveTime() {
		return ApproveTime;
	}
	public void setApproveTime(String approveTime) {
		ApproveTime = approveTime;
	}
	public Integer getCameraState() {
		return CameraState;
	}
	public void setCameraState(Integer cameraState) {
		CameraState = cameraState;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public String getCardId() {
		return CardId;
	}
	public void setCardId(String cardId) {
		CardId = cardId;
	}
	public String getACInfo() {
		return ACInfo;
	}
	public void setACInfo(String aCInfo) {
		ACInfo = aCInfo;
	}
	@Override
	public String toString() {
		return "OrderRecord [RecordInnerId=" + RecordInnerId + ", VisitorInfoInnerId=" + VisitorInfoInnerId
				+ ", VisitorName=" + VisitorName + ", VisitorEmail=" + VisitorEmail + ", VisitorIDCode=" + VisitorIDCode
				+ ", VisitorCompany=" + VisitorCompany + ", UserInnerId=" + UserInnerId + ", VisitorThing="
				+ VisitorThing + ", VisitorPhoto=" + VisitorPhoto + ", VisitorInPhoto=" + VisitorInPhoto
				+ ", VisitorNum=" + VisitorNum + ", CarTypeInnerId=" + CarTypeInnerId + ", VisitorCarCode="
				+ VisitorCarCode + ", VisitorBeginTime=" + VisitorBeginTime + ", VisitorEndTime=" + VisitorEndTime
				+ ", RecordTime=" + RecordTime + ", OrderType=" + OrderType + ", OrderCode=" + OrderCode + ", UserName="
				+ UserName + ", DepartMentName=" + DepartMentName + ", VisitorState=" + VisitorState + ", ApproveTime="
				+ ApproveTime + ", CameraState=" + CameraState + ", Remark=" + Remark + ", CardId=" + CardId
				+ ", ACInfo=" + ACInfo + "]";
	}
}
