package com.dhsr.smartid.renshiyewu.domain;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer UserInnerId;
	private String UserId;
	private String UserName;
	private Integer UserTypeInnerId;
	private Integer CompanyInnerId;
	private Integer DepartmentInnerId;
	private String Password;
	private String AreaList;
	private String AppList;
	private String StartTime;
	private String EndTime;
	private Integer DelFlag;
	private String Remark;
	private Integer IdType;
	private String IdNumber;
	private Integer Sex;
	private String Birthday;
	private String Mobile;
	private String Phone;
	private String Email;
	private String Job;
	private String JobLevel;
	private String Education;
	private String ImageUrl;
	private String Nation;
	private String Country;
	private String NativePlace;
	private String Address;
	private String StandbyA;
	private String StandbyB;
	private String StandbyC;
	private String StandbyD;
	private MultipartFile HeadFile;
	private Integer UState;

	public MultipartFile getHeadFile() {
		return HeadFile;
	}

	public void setHeadFile(MultipartFile headFile) {
		HeadFile = headFile;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getUState() {
		return UState;
	}

	public void setUState(Integer uState) {
		UState = uState;
	}



	public User(Integer userInnerId, String userId, String userName, Integer userTypeInnerId, Integer companyInnerId,
			Integer departmentInnerId, String password, String areaList, String appList, String startTime,
			String endTime, Integer delFlag, String remark, Integer idType, String idNumber, Integer sex,
			String birthday, String mobile, String phone, String email, String job, String jobLevel, String education,
			String imageUrl, String nation, String country, String nativePlace, String address, String standbyA,
			String standbyB, String standbyC, String standbyD, MultipartFile headFile, Integer uState) {
		super();
		UserInnerId = userInnerId;
		UserId = userId;
		UserName = userName;
		UserTypeInnerId = userTypeInnerId;
		CompanyInnerId = companyInnerId;
		DepartmentInnerId = departmentInnerId;
		Password = password;
		AreaList = areaList;
		AppList = appList;
		StartTime = startTime;
		EndTime = endTime;
		DelFlag = delFlag;
		Remark = remark;
		IdType = idType;
		IdNumber = idNumber;
		Sex = sex;
		Birthday = birthday;
		Mobile = mobile;
		Phone = phone;
		Email = email;
		Job = job;
		JobLevel = jobLevel;
		Education = education;
		ImageUrl = imageUrl;
		Nation = nation;
		Country = country;
		NativePlace = nativePlace;
		Address = address;
		StandbyA = standbyA;
		StandbyB = standbyB;
		StandbyC = standbyC;
		StandbyD = standbyD;
		HeadFile = headFile;
		UState = uState;
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

	public Integer getUserTypeInnerId() {
		return UserTypeInnerId;
	}

	public void setUserTypeInnerId(Integer userTypeInnerId) {
		UserTypeInnerId = userTypeInnerId;
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

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getAreaList() {
		return AreaList;
	}

	public void setAreaList(String areaList) {
		AreaList = areaList;
	}

	public String getAppList() {
		return AppList;
	}

	public void setAppList(String appList) {
		AppList = appList;
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

	public Integer getDelFlag() {
		return DelFlag;
	}

	public void setDelFlag(Integer delFlag) {
		DelFlag = delFlag;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public Integer getIdType() {
		return IdType;
	}

	public void setIdType(Integer idType) {
		IdType = idType;
	}

	public String getIdNumber() {
		return IdNumber;
	}

	public void setIdNumber(String idNumber) {
		IdNumber = idNumber;
	}

	public Integer getSex() {
		return Sex;
	}

	public void setSex(Integer sex) {
		Sex = sex;
	}

	public String getBirthday() {
		return Birthday;
	}

	public void setBirthday(String birthday) {
		Birthday = birthday;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getJob() {
		return Job;
	}

	public void setJob(String job) {
		Job = job;
	}

	public String getJobLevel() {
		return JobLevel;
	}

	public void setJobLevel(String jobLevel) {
		JobLevel = jobLevel;
	}

	public String getEducation() {
		return Education;
	}

	public void setEducation(String education) {
		Education = education;
	}

	public String getImageUrl() {
		return ImageUrl;
	}

	public void setImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
	}

	public String getNation() {
		return Nation;
	}

	public void setNation(String nation) {
		Nation = nation;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getNativePlace() {
		return NativePlace;
	}

	public void setNativePlace(String nativePlace) {
		NativePlace = nativePlace;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
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

	public String getStandbyC() {
		return StandbyC;
	}

	public void setStandbyC(String standbyC) {
		StandbyC = standbyC;
	}

	public String getStandbyD() {
		return StandbyD;
	}

	public void setStandbyD(String standbyD) {
		StandbyD = standbyD;
	}

	@Override
	public String toString() {
		return "User [UserInnerId=" + UserInnerId + ", UserId=" + UserId + ", UserName=" + UserName
				+ ", UserTypeInnerId=" + UserTypeInnerId + ", CompanyInnerId=" + CompanyInnerId + ", DepartmentInnerId="
				+ DepartmentInnerId + ", Password=" + Password + ", AreaList=" + AreaList + ", AppList=" + AppList
				+ ", StartTime=" + StartTime + ", EndTime=" + EndTime + ", DelFlag=" + DelFlag + ", Remark=" + Remark
				+ ", IdType=" + IdType + ", IdNumber=" + IdNumber + ", Sex=" + Sex + ", Birthday=" + Birthday
				+ ", Mobile=" + Mobile + ", Phone=" + Phone + ", Email=" + Email + ", Job=" + Job + ", JobLevel="
				+ JobLevel + ", Education=" + Education + ", ImageUrl=" + ImageUrl + ", Nation=" + Nation + ", Country="
				+ Country + ", NativePlace=" + NativePlace + ", Address=" + Address + ", StandbyA=" + StandbyA
				+ ", StandbyB=" + StandbyB + ", StandbyC=" + StandbyC + ", StandbyD=" + StandbyD + ", HeadFile="
				+ HeadFile + ", UState=" + UState + "]";
	}


}
