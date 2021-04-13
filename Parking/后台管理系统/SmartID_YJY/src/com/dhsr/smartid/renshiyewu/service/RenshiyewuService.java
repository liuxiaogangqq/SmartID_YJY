package com.dhsr.smartid.renshiyewu.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dhsr.smartid.peizhiguanli.domain.Operator;
import com.dhsr.smartid.renshiyewu.domain.Company;
import com.dhsr.smartid.renshiyewu.domain.Department;
import com.dhsr.smartid.renshiyewu.domain.Mark;
import com.dhsr.smartid.renshiyewu.domain.User;
import com.dhsr.smartid.util.DataGridModel;

/**
 * 人事业务的Service层
 * 
 * @author qidong
 *
 */
public interface RenshiyewuService {

	// 查询公司
	public String selectCompany(DataGridModel dgm, Company company);

	// 查询公司,显示在下拉框
	public String selectCompanyBox();

	// 新增公司
	public String insertCompany(Company company, HttpServletRequest request);

	// 修改公司
	public String updateCompany(Company company, HttpServletRequest request);

	// 查询部门
	public String selectDepartment(Department department,HttpServletRequest request);

	// 查询公司部门
	public String companyDepartmentTree(HttpServletRequest request);

	// 新增部门
	public String insertDepartment(Department department, HttpServletRequest request);

	// 修改部门
	public String updateDepartment(Department department, HttpServletRequest request);

	// 查询人员
	public String selectUser(DataGridModel dgm, HttpServletRequest request);

	// 人员导出Excel方法
	public String selectUserExcel(HttpServletRequest request) throws Exception;

	// 查询人员部门
	public String userDepartmentTree(HttpServletRequest request);

	// 新增人员
	public String insertUser(User user, HttpServletRequest request) throws IOException;

	// 修改人员
	public String updateUser(User user, HttpServletRequest request) throws IOException;

	// 修改人员密码
	public String updatePWDUser(HttpServletRequest request);

	// 查询标识
	public String selectMark(DataGridModel dgm, HttpServletRequest request);

	// 新增标识
	public String insertMark(Mark mark, HttpServletRequest request);
	
	//补卡
	public String insertBuMark(Mark mark, HttpServletRequest request);

	// 修改标识
	public String updateMark(Mark mark, HttpServletRequest request);

	// 改变标识状态
	public String updateMarkState(Mark mark,HttpServletRequest request);

	// 查询人员权限
	public String selectUserPer(Integer UserInnerId);

	// 修改人员权限
	public String updateUserPer(String str);

	// 人员导入显示
	public String showUserExcel(HttpServletRequest request) throws IOException;

	// 人员导入
	public String importUserExcel(HttpServletRequest request) throws Exception;

	// 查询当前最大人数
	public String selectuserMaxmum(HttpServletRequest request);

	// 查询卡片操作记录
	public String selectMarkLog(HttpServletRequest request, DataGridModel dgm);
	
	//人员导入图片
	public String importUserImage(MultipartHttpServletRequest requestFile,HttpServletRequest request);

	//根据人员查询权限
	public String SelectQuanxianByOperInnerId(HttpServletRequest request);

	/**
	 * 根据登录名查询操作员信息
	 * @param userId
	 * @return
	 */
	public Operator selectOperatorByOperId(String userId);

	/**
	 * 预约授权
	 * @param request
	 * @return
	 */
	public String updateuserShouquan(HttpServletRequest request);

	/**
	 * 修改部门授权信息
	 * @param request
	 * @return
	 */
	public String updateDepCon(HttpServletRequest request);

	public String CompanyPower(HttpServletRequest request);


}
