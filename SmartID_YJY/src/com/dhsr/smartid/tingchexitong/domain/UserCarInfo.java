package com.dhsr.smartid.tingchexitong.domain;

public class UserCarInfo {
    private Integer UserInnerId;
	private String UserId;
	private String UserName;
	private Integer CompanyInnerId;
	private Integer DepartmentInnerId;
	private Integer DelFlag;
	private Integer CarDelFlag;
	private String CarNumber;
	private String CarNumber1;
	private String CarNumber2;
	private String CarNumber3;
	private String CarNumber4;
	private Integer CarTypeInnerId;
	private String StartTime;
	private String EndTime;
	private String Remark;
	private String DepartmentName;
	private String CarTypeName;
	private Integer CarInfoInnerId;
	private Integer ParkingNum;
	private Integer ParkInfo;
	public UserCarInfo() {
		super();
	}
	public UserCarInfo(Integer userInnerId, String userId, String userName, Integer companyInnerId,
			Integer departmentInnerId, Integer delFlag, Integer carDelFlag, String carNumber, Integer carTypeInnerId,
			String startTime, String endTime, String remark, String departmentName, String carTypeName, String carNumber1
			, String carNumber2, String carNumber3, String carNumber4) {
		super();
		UserInnerId = userInnerId;
		UserId = userId;
		UserName = userName;
		CompanyInnerId = companyInnerId;
		DepartmentInnerId = departmentInnerId;
		DelFlag = delFlag;
		CarDelFlag = carDelFlag;
		CarNumber = carNumber;
		CarNumber1 = carNumber1;
		CarNumber2 = carNumber2;
		CarNumber3 = carNumber3;
		CarNumber4 = carNumber4;
		CarTypeInnerId = carTypeInnerId;
		StartTime = startTime;
		EndTime = endTime;
		Remark = remark;
		DepartmentName = departmentName;
		CarTypeName = carTypeName;
	}
	
	public Integer getParkInfo() {
		return ParkInfo;
	}
	public void setParkInfo(Integer parkInfo) {
		ParkInfo = parkInfo;
	}
	public Integer getParkingNum() {
		return ParkingNum;
	}
	public void setParkingNum(Integer parkingNum) {
		ParkingNum = parkingNum;
	}
	public String getCarNumber1() {
		return CarNumber1;
	}
	public void setCarNumber1(String carNumber1) {
		CarNumber1 = carNumber1;
	}
	public String getCarNumber2() {
		return CarNumber2;
	}
	public void setCarNumber2(String carNumber2) {
		CarNumber2 = carNumber2;
	}
	public String getCarNumber3() {
		return CarNumber3;
	}
	public void setCarNumber3(String carNumber3) {
		CarNumber3 = carNumber3;
	}
	public String getCarNumber4() {
		return CarNumber4;
	}
	public void setCarNumber4(String carNumber4) {
		CarNumber4 = carNumber4;
	}
	public Integer getCarInfoInnerId() {
		return CarInfoInnerId;
	}
	public void setCarInfoInnerId(Integer carInfoInnerId) {
		CarInfoInnerId = carInfoInnerId;
	}
	public Integer getUserInnerId() {
		return UserInnerId;
	}
	public void setUserInnerId(Integer userInnerId) {
		UserInnerId = userInnerId;
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
	public Integer getCompanyInnerId() {
		return CompanyInnerId;
	}
	public void setCompanyInnerId(Integer companyInnerId) {
		CompanyInnerId = companyInnerId;
	}
	public Integer getDepartmentInnerId() {
		return DepartmentInnerId;
	}
	public void setDepartmentInnerId(Integer departmentInnerId) {
		DepartmentInnerId = departmentInnerId;
	}
	public Integer getDelFlag() {
		return DelFlag;
	}
	public void setDelFlag(Integer delFlag) {
		DelFlag = delFlag;
	}
	public Integer getCarDelFlag() {
		return CarDelFlag;
	}
	public void setCarDelFlag(Integer carDelFlag) {
		CarDelFlag = carDelFlag;
	}
	public String getCarNumber() {
		return CarNumber;
	}
	public void setCarNumber(String carNumber) {
		CarNumber = carNumber;
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
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public String getDepartmentName() {
		return DepartmentName;
	}
	public void setDepartmentName(String departmentName) {
		DepartmentName = departmentName;
	}
	public String getCarTypeName() {
		return CarTypeName;
	}
	public void setCarTypeName(String carTypeName) {
		CarTypeName = carTypeName;
	}
	@Override
	public String toString() {
		return "UserCarInfo [UserInnerId=" + UserInnerId + ", UserId=" + UserId + ", UserName=" + UserName
				+ ", CompanyInnerId=" + CompanyInnerId + ", DepartmentInnerId=" + DepartmentInnerId + ", DelFlag="
				+ DelFlag + ", CarDelFlag=" + CarDelFlag + ", CarNumber=" + CarNumber + ", CarTypeInnerId="
				+ CarTypeInnerId + ", StartTime=" + StartTime + ", EndTime=" + EndTime + ", Remark=" + Remark
				+ ", DepartmentName=" + DepartmentName + ", CarTypeName=" + CarTypeName + "]";
	}
	
}
