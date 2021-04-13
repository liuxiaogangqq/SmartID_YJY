package com.dhsr.smartid.tongbu.domain;

import java.sql.Blob;

public class LFUser {
    private String psncode; /*人员编码*/
	private String pk_corp; /*部门主键*/
	private String pk_deptdoc; /*部门主键*/
	private String psnname; /*姓名*/
	private Blob photo; /*照片*/
	private String sex; /*性别*/
	private String birthdate; /*出生日期*/
	private String mobile;/*手机*/
	private String officephone; /*办公电话*/
	private String spec; /*现从事专业*/
	private String glzxgw1; /*管理职系岗位一*/
	private String glzxgw2;
	private String glzxgw3;
	private String jsglzxgw1;/*技术管理职系岗位*/
	private String jsglzxgw2;
	private String jsglzxgw3;
	private String xmglzxgw; /*项目管理职系岗位*/
	private String jszxgw; /*技术职系岗位*/
	private String znzxgw; /*职能职系岗位*/
	private String zd;
	private String zj;
	private String education;
	private String school;
	private String psnclassname; /*人员类别*/
	private String joinworkdate;/*参加工作时间*/
	private String indutydate;/*到职时间*/
	private String user_code;/*用户编号*/
	private String id;/*身份证*/
	private String prepsncode;/*曾用人员编码*/
	private String managermark; /**项目经理标识*/
	private String email1;/*公司邮箱*/
	private String midmark;
	private String email2;
	private String deptshoworder;/*部门排序号*/
	private String psnshoworder;/*公司 排序号*/
	public LFUser() {
		super();
	}
	public LFUser(String psncode, String pk_corp, String pk_deptdoc, String psnname, Blob photo, String sex,
			String birthdate, String mobile, String officephone, String spec, String glzxgw1, String glzxgw2,
			String glzxgw3, String jsglzxgw1, String jsglzxgw2, String jsglzxgw3, String xmglzxgw, String jszxgw,
			String znzxgw, String zd, String zj, String education, String school, String psnclassname,
			String joinworkdate, String indutydate, String user_code, String id, String prepsncode, String managermark,
			String email1, String midmark, String email2, String deptshoworder, String psnshoworder) {
		super();
		this.psncode = psncode;
		this.pk_corp = pk_corp;
		this.pk_deptdoc = pk_deptdoc;
		this.psnname = psnname;
		this.photo = photo;
		this.sex = sex;
		this.birthdate = birthdate;
		this.mobile = mobile;
		this.officephone = officephone;
		this.spec = spec;
		this.glzxgw1 = glzxgw1;
		this.glzxgw2 = glzxgw2;
		this.glzxgw3 = glzxgw3;
		this.jsglzxgw1 = jsglzxgw1;
		this.jsglzxgw2 = jsglzxgw2;
		this.jsglzxgw3 = jsglzxgw3;
		this.xmglzxgw = xmglzxgw;
		this.jszxgw = jszxgw;
		this.znzxgw = znzxgw;
		this.zd = zd;
		this.zj = zj;
		this.education = education;
		this.school = school;
		this.psnclassname = psnclassname;
		this.joinworkdate = joinworkdate;
		this.indutydate = indutydate;
		this.user_code = user_code;
		this.id = id;
		this.prepsncode = prepsncode;
		this.managermark = managermark;
		this.email1 = email1;
		this.midmark = midmark;
		this.email2 = email2;
		this.deptshoworder = deptshoworder;
		this.psnshoworder = psnshoworder;
	}
	public String getPsncode() {
		return psncode;
	}
	public void setPsncode(String psncode) {
		this.psncode = psncode;
	}
	public String getPk_corp() {
		return pk_corp;
	}
	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}
	public String getPk_deptdoc() {
		return pk_deptdoc;
	}
	public void setPk_deptdoc(String pk_deptdoc) {
		this.pk_deptdoc = pk_deptdoc;
	}
	public String getPsnname() {
		return psnname;
	}
	public void setPsnname(String psnname) {
		this.psnname = psnname;
	}
	public Blob getPhoto() {
		return photo;
	}
	public void setPhoto(Blob photo) {
		this.photo = photo;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOfficephone() {
		return officephone;
	}
	public void setOfficephone(String officephone) {
		this.officephone = officephone;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getGlzxgw1() {
		return glzxgw1;
	}
	public void setGlzxgw1(String glzxgw1) {
		this.glzxgw1 = glzxgw1;
	}
	public String getGlzxgw2() {
		return glzxgw2;
	}
	public void setGlzxgw2(String glzxgw2) {
		this.glzxgw2 = glzxgw2;
	}
	public String getGlzxgw3() {
		return glzxgw3;
	}
	public void setGlzxgw3(String glzxgw3) {
		this.glzxgw3 = glzxgw3;
	}
	public String getJsglzxgw1() {
		return jsglzxgw1;
	}
	public void setJsglzxgw1(String jsglzxgw1) {
		this.jsglzxgw1 = jsglzxgw1;
	}
	public String getJsglzxgw2() {
		return jsglzxgw2;
	}
	public void setJsglzxgw2(String jsglzxgw2) {
		this.jsglzxgw2 = jsglzxgw2;
	}
	public String getJsglzxgw3() {
		return jsglzxgw3;
	}
	public void setJsglzxgw3(String jsglzxgw3) {
		this.jsglzxgw3 = jsglzxgw3;
	}
	public String getXmglzxgw() {
		return xmglzxgw;
	}
	public void setXmglzxgw(String xmglzxgw) {
		this.xmglzxgw = xmglzxgw;
	}
	public String getJszxgw() {
		return jszxgw;
	}
	public void setJszxgw(String jszxgw) {
		this.jszxgw = jszxgw;
	}
	public String getZnzxgw() {
		return znzxgw;
	}
	public void setZnzxgw(String znzxgw) {
		this.znzxgw = znzxgw;
	}
	public String getZd() {
		return zd;
	}
	public void setZd(String zd) {
		this.zd = zd;
	}
	public String getZj() {
		return zj;
	}
	public void setZj(String zj) {
		this.zj = zj;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getPsnclassname() {
		return psnclassname;
	}
	public void setPsnclassname(String psnclassname) {
		this.psnclassname = psnclassname;
	}
	public String getJoinworkdate() {
		return joinworkdate;
	}
	public void setJoinworkdate(String joinworkdate) {
		this.joinworkdate = joinworkdate;
	}
	public String getIndutydate() {
		return indutydate;
	}
	public void setIndutydate(String indutydate) {
		this.indutydate = indutydate;
	}
	public String getUser_code() {
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPrepsncode() {
		return prepsncode;
	}
	public void setPrepsncode(String prepsncode) {
		this.prepsncode = prepsncode;
	}
	public String getManagermark() {
		return managermark;
	}
	public void setManagermark(String managermark) {
		this.managermark = managermark;
	}
	public String getEmail1() {
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	public String getMidmark() {
		return midmark;
	}
	public void setMidmark(String midmark) {
		this.midmark = midmark;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	public String getDeptshoworder() {
		return deptshoworder;
	}
	public void setDeptshoworder(String deptshoworder) {
		this.deptshoworder = deptshoworder;
	}
	public String getPsnshoworder() {
		return psnshoworder;
	}
	public void setPsnshoworder(String psnshoworder) {
		this.psnshoworder = psnshoworder;
	}
	@Override
	public String toString() {
		return "LFUser [psncode=" + psncode + ", pk_corp=" + pk_corp + ", pk_deptdoc=" + pk_deptdoc + ", psnname="
				+ psnname + ", photo=" + photo + ", sex=" + sex + ", birthdate=" + birthdate + ", mobile=" + mobile
				+ ", officephone=" + officephone + ", spec=" + spec + ", glzxgw1=" + glzxgw1 + ", glzxgw2=" + glzxgw2
				+ ", glzxgw3=" + glzxgw3 + ", jsglzxgw1=" + jsglzxgw1 + ", jsglzxgw2=" + jsglzxgw2 + ", jsglzxgw3="
				+ jsglzxgw3 + ", xmglzxgw=" + xmglzxgw + ", jszxgw=" + jszxgw + ", znzxgw=" + znzxgw + ", zd=" + zd
				+ ", zj=" + zj + ", education=" + education + ", school=" + school + ", psnclassname=" + psnclassname
				+ ", joinworkdate=" + joinworkdate + ", indutydate=" + indutydate + ", user_code=" + user_code + ", id="
				+ id + ", prepsncode=" + prepsncode + ", managermark=" + managermark + ", email1=" + email1
				+ ", midmark=" + midmark + ", email2=" + email2 + ", deptshoworder=" + deptshoworder + ", psnshoworder="
				+ psnshoworder + "]";
	}
	
}
