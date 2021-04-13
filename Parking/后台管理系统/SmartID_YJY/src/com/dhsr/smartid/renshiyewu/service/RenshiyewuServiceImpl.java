package com.dhsr.smartid.renshiyewu.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SerializationUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dhsr.smartid.peizhiguanli.dao.PeizhiguanliMapper;
import com.dhsr.smartid.peizhiguanli.domain.App;
import com.dhsr.smartid.peizhiguanli.domain.Area;
import com.dhsr.smartid.peizhiguanli.domain.Configure;
import com.dhsr.smartid.peizhiguanli.domain.Operator;
import com.dhsr.smartid.peizhiguanli.domain.SystemLog;
import com.dhsr.smartid.peizhiguanli.domain.UserType;
import com.dhsr.smartid.renshiyewu.dao.RenshiyewuMapper;
import com.dhsr.smartid.renshiyewu.domain.Company;
import com.dhsr.smartid.renshiyewu.domain.Department;
import com.dhsr.smartid.renshiyewu.domain.Mark;
import com.dhsr.smartid.renshiyewu.domain.MarkLog;
import com.dhsr.smartid.renshiyewu.domain.Mark_User;
import com.dhsr.smartid.renshiyewu.domain.User;
import com.dhsr.smartid.renshiyewu.domain.UserPer;
import com.dhsr.smartid.util.Constant;
import com.dhsr.smartid.util.DataGridModel;
import com.dhsr.smartid.util.ExportExcelUtils;
import com.dhsr.smartid.util.HttpUploadFile;
import com.dhsr.smartid.util.UpLoadExcel;
import com.dhsr.smartid.zhanghuyewu.dao.ZhanghuyewuMapper;
import com.dhsr.smartid.zhanghuyewu.domain.Account;
import com.dhsr.smartid.zhanghuyewu.domain.AccountLog;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 人事业务的Service层
 * 
 * @author qidong
 *
 */
public class RenshiyewuServiceImpl implements RenshiyewuService {

	@Resource
	private RenshiyewuMapper renshiyewuMapper;
	@Resource
	private PeizhiguanliMapper peizhiguanliMapper;
	@Resource
	private ZhanghuyewuMapper zhanghuyewuMapper;

	@Override
	@Transactional
	public String selectCompany(DataGridModel dgm, Company company) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("CompanyInnerId");
			dgm.setOrder("asc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(20);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		map.put("CompanyId", company.getCompanyId());
		map.put("CompanyName", company.getCompanyName());
		map.put("AreaInnerId", company.getAreaInnerId());
		map.put("Remark", company.getRemark());
		map.put("StandbyA", company.getStandbyA());
		map.put("StandbyB", company.getStandbyB());
		map.put("AreaInnerId", company.getAreaInnerId());
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();

		List<Company> CompanyList = renshiyewuMapper.selectCompany(map);
		for (Company company1 : CompanyList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("CompanyInnerId", company1.getCompanyInnerId());
			row.put("CompanyId", company1.getCompanyId());
			row.put("CompanyName", company1.getCompanyName());
			row.put("Remark", company1.getRemark());
			row.put("StandbyA", company1.getStandbyA());
			row.put("StandbyB", company1.getStandbyB());
			row.put("AreaInnerId", company1.getAreaInnerId());
			Area area = peizhiguanliMapper.selectAreaByInnerId(company1.getAreaInnerId());
			row.put("AreaName", area.getAreaName());
			rows.add(row);
		}
		jo.put("total", renshiyewuMapper.selectCompanyTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectCompanyBox() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", 100000000);
		map.put("order", "asc");
		map.put("sort", "CompanyInnerId");
		map.put("start", 0);
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		List<Company> CompanyList = renshiyewuMapper.selectCompany(map);
		for (Company company1 : CompanyList) {
			jo = new JSONObject();
			jo.put("id", company1.getCompanyInnerId());
			jo.put("text", company1.getCompanyName());
			ja.add(jo);
		}
		return ja.toString();
	}

	@Override
	@Transactional
	public String insertCompany(Company company, HttpServletRequest request) {
		Integer result = renshiyewuMapper.insertCompany(company);
		JSONObject jo = new JSONObject();
		if (result > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", 100000000);
			map.put("order", "asc");
			map.put("sort", "CompanyInnerId");
			map.put("start", 0);
			map.put("CompanyIdEqu", company.getCompanyId());
			List<Company> CompanyAfter = renshiyewuMapper.selectCompany(map);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
			SystemLog systemLog = new SystemLog(null, "System_Company", CompanyAfter.get(0).getCompanyInnerId(),
					CompanyAfter.get(0).getCompanyId(), CompanyAfter.get(0).getCompanyName(), 0, null,
					Base64.encodeBase64String(SerializationUtils.serialize(CompanyAfter.get(0))),
					operator.getOperatorInnerId(), sdf.format(new Date()), CompanyAfter.get(0).getRemark());
			Integer result1 = peizhiguanliMapper.insertSystemLog(systemLog);
			if (result1 > 0) {
				jo.put("code", 200);
				jo.put("msg", "添加成功！");
			} else {
				jo.put("code", 500);
				jo.put("msg", "添加失败！");
			}
		} else {
			jo.put("code", 500);
			jo.put("msg", "添加失败！");
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String updateCompany(Company company, HttpServletRequest request) {
		Company beforeCompany = renshiyewuMapper.selectCompanyByInnerId(company.getCompanyInnerId());
		Integer result = renshiyewuMapper.updateCompany(company);
		JSONObject jo = new JSONObject();
		if (result > 0) {
			Company afterCompany = renshiyewuMapper.selectCompanyByInnerId(company.getCompanyInnerId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
			SystemLog systemLog = new SystemLog(null, "System_Company", afterCompany.getCompanyInnerId(),
					afterCompany.getCompanyId(), afterCompany.getCompanyName(), 1,
					Base64.encodeBase64String(SerializationUtils.serialize(beforeCompany)),
					Base64.encodeBase64String(SerializationUtils.serialize(afterCompany)),
					operator.getOperatorInnerId(), sdf.format(new Date()), afterCompany.getRemark());
			Integer result1 = peizhiguanliMapper.insertSystemLog(systemLog);
			if (result1 > 0) {
				jo.put("code", 200);
				jo.put("msg", "修改成功！");
			} else {
				jo.put("code", 500);
				jo.put("msg", "修改失败！");
			}
			jo.put("code", 200);
			jo.put("msg", "修改成功！");
		} else {
			jo.put("code", 500);
			jo.put("msg", "修改失败！");
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectDepartment(Department department, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("CompanyInnerId", department.getCompanyInnerId());
		map.put("UpInnerId", department.getUpInnerId());
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		// Operator operator =
		// (Operator)request.getSession().getAttribute("operatorSession");
		/*
		 * if(!operator.getOperatorId().equals("admin")){ String departments =
		 * operator.getDepartmentList(); map.put("DepartmentInnerIdList",
		 * departments); }
		 */
		List<Department> departmentList = renshiyewuMapper.selectDepartment(map);
		for (Department department1 : departmentList) {
			jo = new JSONObject();
			jo.put("DepartmentInnerId", department1.getDepartmentInnerId());
			jo.put("DepartmentName", department1.getDepartmentName());
			jo.put("DepartmentId", department1.getDepartmentId());
			jo.put("CompanyInnerId", department1.getCompanyInnerId());
			jo.put("DepartmentLevel", department1.getDepartmentLevel());
			jo.put("UpInnerId", department1.getUpInnerId());
			jo.put("Remark", department1.getRemark());
			jo.put("StandbyA", department1.getStandbyA());
			jo.put("StandbyB", department1.getStandbyB());
			jo.put("StandbyC", department1.getStandbyC());
			jo.put("StandbyD", department1.getStandbyD());
			jo.put("iconCls", "icon-bumen");
			map.put("UpInnerId", department1.getDepartmentInnerId());
			List<Department> departmentList1 = renshiyewuMapper.selectDepartment(map);
			if (departmentList1.size() > 0) {
				jo.put("state", "closed");
			} else {
				jo.put("state", "");
			}
			ja.add(jo);
		}
		return ja.toString();
	}

	@Override
	@Transactional
	public String companyDepartmentTree(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", 100000000);
		map.put("order", "asc");
		map.put("sort", "CompanyInnerId");
		map.put("start", 0);
		List<Company> CompanyList = renshiyewuMapper.selectCompany(map);
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		for (Company company : CompanyList) {
			jo = new JSONObject();
			jo.put("id", "company" + company.getCompanyInnerId());
			jo.put("text", company.getCompanyName());
			jo.put("iconCls", "icon-bumen");
			// Operator operator = (Operator)
			// request.getSession().getAttribute("operatorSession");
			/*
			 * if(!operator.getOperatorId().equals("admin")){ String departments
			 * = operator.getDepartmentList(); map.put("DepartmentInnerIdList",
			 * departments); }
			 */
			map.put("CompanyInnerId", company.getCompanyInnerId());
			map.put("UpInnerId", 0);
			List<Department> departmentList = renshiyewuMapper.selectDepartment(map);
			JSONArray depJa = new JSONArray();
			JSONObject depJo = new JSONObject();
			for (Department department : departmentList) {
				depJo = new JSONObject();
				depJo.put("id", "department" + department.getDepartmentInnerId());
				depJo.put("text", department.getDepartmentName());
				Integer size = departmentTree(depJo, department.getDepartmentInnerId());
				if (size > 0) {
					depJo.put("state", "closed");
				} else {
					depJo.put("state", "");
				}
				depJa.add(depJo);
			}
			jo.put("children", depJa);
			if (departmentList.size() > 0) {
				jo.put("state", "closed");
			} else {
				jo.put("state", "");
			}
			ja.add(jo);
		}
		return ja.toString();
	}

	private Integer departmentTree(JSONObject jo, Integer UpInnerId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("UpInnerId", UpInnerId);
		List<Department> departmentList = renshiyewuMapper.selectDepartment(map);
		JSONArray depJa = new JSONArray();
		JSONObject depJo = new JSONObject();
		for (Department department : departmentList) {
			depJo = new JSONObject();
			depJo.put("id", "department" + department.getDepartmentInnerId());
			depJo.put("text", department.getDepartmentName());
			Integer size = departmentTree(depJo, department.getDepartmentInnerId());
			if (size > 0) {
				depJo.put("state", "closed");
			} else {
				depJo.put("state", "");
			}
			depJa.add(depJo);
		}
		jo.put("children", depJa);
		return departmentList.size();
	}

	@Override
	@Transactional
	public String insertDepartment(Department department, HttpServletRequest request) {
		Integer result = renshiyewuMapper.insertDepartment(department);
		JSONObject jo = new JSONObject();
		if (result > 0) {
			Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("DepartmentIdEqu", department.getDepartmentId());
			row.put("OperatorInnerId", operator.getOperatorInnerId());
			List<Department> departments = renshiyewuMapper.selectDepartment(row);
			row.put("DepartmentList", operator.getDepartmentList() + departments.get(0).getDepartmentInnerId() + ",");
			Integer result2 = renshiyewuMapper.updateOperator(row);
			if (result2 > 0) {
				Operator op = renshiyewuMapper.selectOperatorByOperatorInnerId(operator.getOperatorInnerId());
				request.getSession(true).setAttribute("operatorSession", op);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("DepartmentIdEqu", department.getDepartmentId());
				List<Department> departmentAfter = renshiyewuMapper.selectDepartment(map);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SystemLog systemLog = new SystemLog(null, "System_Department",
						departmentAfter.get(0).getDepartmentInnerId(), departmentAfter.get(0).getDepartmentId(),
						departmentAfter.get(0).getDepartmentName(), 0, null,
						Base64.encodeBase64String(SerializationUtils.serialize(departmentAfter.get(0))),
						operator.getOperatorInnerId(), sdf.format(new Date()), departmentAfter.get(0).getRemark());
				Integer result1 = peizhiguanliMapper.insertSystemLog(systemLog);
				if (result1 > 0) {
					jo.put("code", 200);
					jo.put("msg", "添加成功！");
				} else {
					jo.put("code", 500);
					jo.put("msg", "添加失败！");
				}
			} else {
				jo.put("code", 500);
				jo.put("msg", "添加失败！");
			}
		} else {
			jo.put("code", 500);
			jo.put("msg", "添加失败！");
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String updateDepartment(Department department, HttpServletRequest request) {
		Department beforeDepartment = renshiyewuMapper.selectDepartmentByInnerId(department.getDepartmentInnerId());
		Integer result = renshiyewuMapper.updateDepartment(department);
		JSONObject jo = new JSONObject();
		if (result > 0) {
			Department afterDepartment = renshiyewuMapper.selectDepartmentByInnerId(department.getDepartmentInnerId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
			SystemLog systemLog = new SystemLog(null, "System_Department", afterDepartment.getDepartmentInnerId(),
					afterDepartment.getDepartmentId(), afterDepartment.getDepartmentName(), 1,
					Base64.encodeBase64String(SerializationUtils.serialize(beforeDepartment)),
					Base64.encodeBase64String(SerializationUtils.serialize(afterDepartment)),
					operator.getOperatorInnerId(), sdf.format(new Date()), afterDepartment.getRemark());
			Integer result1 = peizhiguanliMapper.insertSystemLog(systemLog);
			if (result1 > 0) {
				jo.put("code", 200);
				jo.put("msg", "修改成功！");
			} else {
				jo.put("code", 500);
				jo.put("msg", "修改失败！");
			}
			jo.put("code", 200);
			jo.put("msg", "修改成功！");
		} else {
			jo.put("code", 500);
			jo.put("msg", "修改失败！");
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectUser(DataGridModel dgm, HttpServletRequest request) {
		String DepartmentList = request.getParameter("DepartmentList");
		String UserTypeList = request.getParameter("UserTypeList");
		String UserId = request.getParameter("UserId");
		String UserName = request.getParameter("UserName");
		String Remark = request.getParameter("Remark");
		String Mobile = request.getParameter("Mobile");
		String StandbyA = request.getParameter("StandbyA");
		String StandbyB = request.getParameter("StandbyB");
		String StandbyC = request.getParameter("StandbyC");
		String StandbyD = request.getParameter("StandbyD");
		Map<String, Object> map = new HashMap<String, Object>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("UserInnerId");
			dgm.setOrder("asc");
		} else {
			if (dgm.getSort().equals("CompanyName")) {
				dgm.setSort("CompanyInnerId");
			} else if (dgm.getSort().equals("DepartmentName")) {
				dgm.setSort("DepartmentInnerId");
			} else if (dgm.getSort().equals("UserTypeName")) {
				dgm.setSort("UserTypeInnerId");
			} else if (dgm.getSort().equals("SexCh")) {
				dgm.setSort("Sex");
			}
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(20);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		map.put("DepartmentList", DepartmentList);
		/*
		 * Operator operator = (Operator)
		 * request.getSession().getAttribute("operatorSession"); StringBuilder
		 * DepartmentInnerId = new StringBuilder();
		 */
		/*
		 * if(operator.getOperatorId().equals("admin")){
		 * map.put("DepartmentList", DepartmentList); }else{ if (DepartmentList
		 * != null) { String departmentList = operator.getDepartmentList();
		 * String[] ccdepartments = departmentList.split(","); // 存储的部门权限
		 * String[] crdepartments = DepartmentList.split(","); // 前台传入的要查询的部门
		 * for (String department : crdepartments) { for (String department1 :
		 * ccdepartments) { if (department.equals(department1)) {
		 * DepartmentInnerId.append(department + ","); break; } } }
		 * map.put("DepartmentList", DepartmentInnerId.toString()); }else{
		 * map.put("DepartmentList", operator.getDepartmentList()); } }
		 */
		map.put("UserTypeList", UserTypeList);
		map.put("UserId", UserId);
		map.put("UserName", UserName);
		map.put("Mobile", Mobile);
		map.put("Remark", Remark);
		map.put("StandbyA", StandbyA);
		map.put("StandbyB", StandbyB);
		map.put("StandbyC", StandbyC);
		map.put("StandbyD", StandbyD);
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<User> UserList = renshiyewuMapper.selectUser(map);
		for (User user1 : UserList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("UserInnerId", user1.getUserInnerId());
			row.put("UserId", user1.getUserId());
			row.put("UserName", user1.getUserName());
			row.put("UserTypeInnerId", user1.getUserTypeInnerId());
			row.put("CompanyInnerId", user1.getCompanyInnerId());
			row.put("DepartmentInnerId", user1.getDepartmentInnerId());
			UserType userType = peizhiguanliMapper.selectUserTypeByInnerId(user1.getUserTypeInnerId());
			if (userType != null) {
				row.put("UserTypeName", userType.getUserTypeName());
			}
			Company company = renshiyewuMapper.selectCompanyByInnerId(user1.getCompanyInnerId());
			if (company != null) {
				row.put("CompanyName", company.getCompanyName());
			}
			Department department = renshiyewuMapper.selectDepartmentByInnerId(user1.getDepartmentInnerId());
			if (department != null) {
				row.put("DepartmentName", department.getDepartmentName());
			}

			row.put("Password", user1.getPassword());
			row.put("AreaList", user1.getAreaList());
			row.put("AppList", user1.getAppList());
			if (user1.getStartTime() != null) {
				row.put("StartTime", user1.getStartTime().substring(0, 19));
			} else {
				row.put("StartTime", user1.getStartTime());
			}
			if (user1.getEndTime() != null) {
				row.put("EndTime", user1.getEndTime().substring(0, 19));
			} else {
				row.put("EndTime", user1.getEndTime());
			}
			row.put("Remark", user1.getRemark());
			row.put("IdType", user1.getIdType());
			row.put("IdNumber", user1.getIdNumber());
			row.put("Sex", user1.getSex());
			if (user1.getSex() != null) {
				if (user1.getSex() == 0) {
					row.put("SexCh", "男");
				} else if (user1.getSex() == 1) {
					row.put("SexCh", "女");
				} else {
					row.put("SexCh", "保密");
				}
			}
			if (user1.getBirthday() != null) {
				row.put("Birthday", user1.getBirthday().substring(0, 10));
			}
			row.put("Mobile", user1.getMobile());
			row.put("Phone", user1.getPhone());
			row.put("Email", user1.getEmail());
			row.put("Job", user1.getJob());
			row.put("JobLevel", user1.getJobLevel());
			row.put("Education", user1.getEducation());
			row.put("ImageUrl", user1.getImageUrl());
			row.put("Nation", user1.getNation());
			row.put("Country", user1.getCountry());
			row.put("NativePlace", user1.getNativePlace());
			row.put("Address", user1.getAddress());
			row.put("StandbyA", user1.getStandbyA());
			row.put("StandbyB", user1.getStandbyB());
			row.put("StandbyC", user1.getStandbyC());
			row.put("StandbyD", user1.getStandbyD());
			if (user1.getUState() != null) {
				if (user1.getUState() == 3) {
					row.put("UState", "有");
				} else {
					row.put("UState", "无");
				}
			} else {
				row.put("UState", "无");
			}

			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("AccountTypeInnerId", 1);
			map1.put("UserInnerId", user1.getUserInnerId());
			Account account = zhanghuyewuMapper.selectAccount(map1);
			if (account != null) {
				row.put("AccountInnerId", account.getAccountInnerId());
				row.put("BeforeMoney", account.getMoney() / 100.0);
			} else {
				row.put("BeforeMoney", "无现金账户");
			}
			map1.put("AccountTypeInnerId", 2);
			account = zhanghuyewuMapper.selectAccount(map1);
			if (account != null) {
				row.put("Subsidy", account.getMoney() / 100.0);
			} else {
				row.put("Subsidy", "无补贴账户");
			}
			map1.put("AccountTypeInnerId", 3);
			account = zhanghuyewuMapper.selectAccount(map1);
			if (account != null) {
				row.put("AccountInnerId1", account.getAccountInnerId());
				row.put("FavorableMoneyBefore", account.getMoney() / 100.0);
			} else {
				row.put("FavorableMoneyBefore", "无次账户");
			}
			Integer Proportion = zhanghuyewuMapper.selectUserTypeDiscount(user1.getUserTypeInnerId());
			if (Proportion != null) {
				if (Proportion == 0) {
					row.put("Proportion", 10000);
				} else {
					row.put("Proportion", Proportion);
				}
			} else {
				row.put("Proportion", 10000);
			}
			rows.add(row);
		}
		jo.put("total", renshiyewuMapper.selectUserTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectUserExcel(HttpServletRequest request) throws Exception {

		String DepartmentList = request.getParameter("DepartmentList");
		String UserTypeList = request.getParameter("UserTypeList");
		String UserId = request.getParameter("UserId");
		String UserName = request.getParameter("UserName");
		String Remark = request.getParameter("Remark");
		String StandbyA = request.getParameter("StandbyA");
		String StandbyB = request.getParameter("StandbyB");
		String StandbyC = request.getParameter("StandbyC");
		String StandbyD = request.getParameter("StandbyD");
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		Map<String, Object> map = new HashMap<String, Object>();
		if (sort == null || sort.equals("")) {
			sort = "UserInnerId";
			order = "asc";
		} else {
			if (sort.equals("CompanyName")) {
				sort = "CompanyInnerId";
			} else if (sort.equals("DepartmentName")) {
				sort = "DepartmentInnerId";
			} else if (sort.equals("UserTypeName")) {
				sort = "UserTypeInnerId";
			} else if (sort.equals("SexCh")) {
				sort = "Sex";
			}
		}
		map.put("rows", 100000000);
		map.put("order", order);
		map.put("sort", sort);
		map.put("start", 0);
		map.put("DepartmentList", DepartmentList);
		map.put("UserTypeList", UserTypeList);
		map.put("UserId", UserId);
		map.put("UserName", UserName);
		map.put("Remark", Remark);
		map.put("StandbyA", StandbyA);
		map.put("StandbyB", StandbyB);
		map.put("StandbyC", StandbyC);
		map.put("StandbyD", StandbyD);
		List<Configure> configureList = peizhiguanliMapper.selectConfigure("user");
		List<User> UserList = renshiyewuMapper.selectUser(map);
		List<List<Object>> data = new ArrayList<List<Object>>();
		int count = 0;
		for (User user : UserList) {
			count++;
			List<Object> rowData = new ArrayList<Object>();
			rowData.add(user.getUserId());
			rowData.add(user.getUserName());
			UserType userType = peizhiguanliMapper.selectUserTypeByInnerId(user.getUserTypeInnerId());
			if (userType != null) {
				rowData.add(userType.getUserTypeName());
			} else {
				rowData.add("人员类型不存在");
			}
			Company company = renshiyewuMapper.selectCompanyByInnerId(user.getCompanyInnerId());
			/*
			 * if (company != null) { rowData.add(company.getCompanyName()); }
			 * else { rowData.add("公司不存在"); }
			 */
			Department department = renshiyewuMapper.selectDepartmentByInnerId(user.getDepartmentInnerId());
			if (department != null) {
				rowData.add(department.getDepartmentName());
			} else {
				rowData.add("公司不存在");
			}
			for (Configure configure : configureList) {
				if (configure.getEnabled() == 0) {
					if (configure.getProperty().equals("Remark")) {
						if (user.getRemark() != null) {
							rowData.add(user.getRemark());
						} else {
							rowData.add(" ");
						}
					} else if (configure.getProperty().equals("IdType")) {
						if (user.getIdType() != null) {
							if (user.getIdType() == 0) {
								rowData.add("身份证");
							} else if (user.getIdType() == 1) {
								rowData.add("学生证");
							} else if (user.getIdType() == 2) {
								rowData.add("港澳证");
							} else if (user.getIdType() == 3) {
								rowData.add("台胞证");
							} else if (user.getIdType() == 4) {
								rowData.add("护照");
							} else {
								rowData.add("其他");
							}
						} else {
							rowData.add(" ");
						}
					} else if (configure.getProperty().equals("IdNumber")) {
						if (user.getIdNumber() != null) {
							rowData.add(user.getIdNumber());
						} else {
							rowData.add(" ");
						}
					} else if (configure.getProperty().equals("Sex")) {
						if (user.getSex() != null) {
							if (user.getSex() == 0) {
								rowData.add("男");
							} else if (user.getSex() == 1) {
								rowData.add("女");
							} else {
								rowData.add("保密");
							}
						} else {
							rowData.add(" ");
						}
					} else if (configure.getProperty().equals("Birthday")) {
						if (user.getBirthday() != null) {
							rowData.add(user.getBirthday().substring(0, 10));
						} else {
							rowData.add(" ");
						}
					} else if (configure.getProperty().equals("Mobile")) {
						if (user.getMobile() != null) {
							rowData.add(user.getMobile());
						} else {
							rowData.add(" ");
						}
					} else if (configure.getProperty().equals("Phone")) {
						if (user.getPhone() != null) {
							rowData.add(user.getPhone());
						} else {
							rowData.add(" ");
						}
					} else if (configure.getProperty().equals("Email")) {
						if (user.getEmail() != null) {
							rowData.add(user.getEmail());
						} else {
							rowData.add(" ");
						}
					} else if (configure.getProperty().equals("Job")) {
						if (user.getJob() != null) {
							rowData.add(user.getJob());
						} else {
							rowData.add(" ");
						}
					} else if (configure.getProperty().equals("JobLevel")) {
						if (user.getJobLevel() != null) {
							rowData.add(user.getJobLevel());
						} else {
							rowData.add(" ");
						}
					} else if (configure.getProperty().equals("Education")) {
						rowData.add(user.getEducation());

					} else if (configure.getProperty().equals("Nation")) {
						if (user.getNation() != null) {
							rowData.add(user.getNation());
						} else {
							rowData.add(" ");
						}
					} else if (configure.getProperty().equals("Country")) {
						if (user.getCountry() != null) {
							rowData.add(user.getCountry());
						} else {
							rowData.add(" ");
						}
					} else if (configure.getProperty().equals("NativePlace")) {
						if (user.getNativePlace() != null) {
							rowData.add(user.getNativePlace());
						} else {
							rowData.add(" ");
						}
					} else if (configure.getProperty().equals("Address")) {
						if (user.getAddress() != null) {
							rowData.add(user.getAddress());
						} else {
							rowData.add(" ");
						}
					} else if (configure.getProperty().equals("StandbyA")) {
						if (user.getStandbyA() != null) {
							rowData.add(user.getStandbyA());
						} else {
							rowData.add(" ");
						}
					} else if (configure.getProperty().equals("StandbyB")) {
						if (user.getStandbyB() != null) {
							rowData.add(user.getStandbyB());
						} else {
							rowData.add(" ");
						}
					} else if (configure.getProperty().equals("StandbyC")) {
						if (user.getStandbyC() != null) {
							rowData.add(user.getStandbyC());
						} else {
							rowData.add(" ");
						}
					} else if (configure.getProperty().equals("StandbyD")) {
						if (user.getStandbyD() != null) {
							rowData.add(user.getStandbyD());
						} else {
							rowData.add(" ");
						}
					}
				}
			}
			data.add(rowData);
		}
		List<Object> rowData = new ArrayList<Object>();
		rowData.add("总计");
		rowData.add(count + "人");
		rowData.add(" ");
		rowData.add(" ");
		for (Configure configure : configureList) {
			if (configure.getEnabled() == 0) {
				rowData.add(" ");
			}
		}
		data.add(rowData);
		String relpath = request.getSession().getServletContext().getRealPath("excel");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS");
		String name = "/User" + sdf2.format(new Date()) + ".xlsx";
		OutputStream out = new FileOutputStream(relpath + name);
		ExportExcelUtils eeu = new ExportExcelUtils();
		XSSFWorkbook workbook = new XSSFWorkbook();
		String header0 = "人员表";
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		String header1 = "制表人：" + operator.getOperatorId() + "    制表时间：" + sdf.format(new Date());
		List<String> headerList = new ArrayList<String>();
		headerList.add(header0);
		headerList.add(header1);
		List<String> headers0 = new ArrayList<String>();
		headers0.add("编号");
		headers0.add("姓名");
		headers0.add("人员类型");
		headers0.add("公司");
		List<Integer> length0 = new ArrayList<Integer>();
		length0.add(15);
		length0.add(15);
		length0.add(15);
		length0.add(20);
		length0.add(20);
		for (Configure configure : configureList) {
			if (configure.getEnabled() == 0) {
				headers0.add(configure.getName());
				length0.add(configure.getLength() / 7);
			}
		}
		eeu.selectUseruserExcel(workbook, 0, "人员表", headerList, headers0, length0, data, out);
		workbook.write(out);
		out.close();
		JSONObject jo = new JSONObject();
		jo.put("code", 200);
		jo.put("msg", "导出成功！");
		jo.put("data", "excel/" + name);
		return jo.toString();
	}

	@Override
	@Transactional
	public String userDepartmentTree(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", 100000000);
		map.put("order", "asc");
		map.put("sort", "CompanyInnerId");
		map.put("start", 0);
		List<Company> CompanyList = renshiyewuMapper.selectCompany(map);
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		for (Company company : CompanyList) {
			jo = new JSONObject();
			jo.put("id", "company" + company.getCompanyInnerId());
			jo.put("text", company.getCompanyName());
			jo.put("iconCls", "icon-bumen");

			map.put("CompanyInnerId", company.getCompanyInnerId());
			map.put("UpInnerId", 0);
			Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
			map.put("DepartmentInnerIdList", operator.getDepartmentList());
			List<Department> departmentList = renshiyewuMapper.selectDepartment(map);
			JSONArray depJa = new JSONArray();
			JSONObject depJo = new JSONObject();
			for (Department department : departmentList) {
				depJo = new JSONObject();
				depJo.put("id", "department" + department.getDepartmentInnerId());
				depJo.put("text", department.getDepartmentName());
				Integer size = userTree(depJo, department.getDepartmentInnerId());

				// Map<String, Object> map1 = new HashMap<String, Object>();
				// map1.put("rows", 100000000);
				// map1.put("order", "asc");
				// map1.put("sort", "UserInnerId");
				// map1.put("start", 0);
				// map1.put("DepartmentInnerId",
				// department.getDepartmentInnerId());
				// List<User> userList = renshiyewuMapper.selectUser(map1);
				// JSONArray userJa = new JSONArray();
				// JSONObject userJo = new JSONObject();
				// for (User user : userList) {
				// userJo = new JSONObject();
				// userJo.put("id", "user" + user.getUserTypeInnerId());
				// userJo.put("text", user.getUserName());
				// userJa.add(userJo);
				// }
				// depJo.put("children", userJa);

				if (size > 0) {
					depJo.put("state", "closed");
				} else {
					depJo.put("state", "");
				}
				depJa.add(depJo);
			}
			jo.put("children", depJa);
			if (departmentList.size() > 0) {
				jo.put("state", "closed");
			} else {
				jo.put("state", "");
			}
			ja.add(jo);
		}
		return ja.toString();
	}

	private Integer userTree(JSONObject jo, Integer UpInnerId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("UpInnerId", UpInnerId);
		List<Department> departmentList = renshiyewuMapper.selectDepartment(map);
		JSONArray depJa = new JSONArray();
		JSONObject depJo = new JSONObject();
		for (Department department : departmentList) {
			depJo = new JSONObject();
			depJo.put("id", "department" + department.getDepartmentInnerId());
			depJo.put("text", department.getDepartmentName());
			Integer size = userTree(depJo, department.getDepartmentInnerId());
			if (size > 0) {
				depJo.put("state", "closed");
			} else {
				depJo.put("state", "");
			}
			depJa.add(depJo);
		}
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("rows", 100000000);
		map1.put("order", "asc");
		map1.put("sort", "UserInnerId");
		map1.put("start", 0);
		map1.put("DepartmentInnerId", UpInnerId);
		List<User> userList = renshiyewuMapper.selectUser(map1);
		JSONObject userJo = new JSONObject();
		for (User user : userList) {
			userJo = new JSONObject();
			userJo.put("id", "user" + user.getUserInnerId());
			userJo.put("text", user.getUserName());
			depJa.add(userJo);
		}

		jo.put("children", depJa);
		return departmentList.size();
	}

	@Override
	@Transactional
	public String insertUser(User user, HttpServletRequest request) throws IOException {
		user.setStandbyD("1");
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		UserType userType = peizhiguanliMapper.selectUserTypeByInnerId(user.getUserTypeInnerId());
		if (userType != null) {
			user.setAreaList(userType.getAreaList());
			user.setAppList(userType.getAppList());
		}
		Department department = renshiyewuMapper.selectDepartmentByInnerId(user.getDepartmentInnerId());
		if (department != null) {
			user.setCompanyInnerId(department.getCompanyInnerId());
		}
		Integer result = renshiyewuMapper.insertUser(user);
		JSONObject jo = new JSONObject();
		if (result > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
			// 在数据库查找此人员
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("rows", 100000000);
			map2.put("order", "asc");
			map2.put("sort", "UserInnerId");
			map2.put("start", 0);
			map2.put("UserIdEqu", user.getUserId());
			List<User> userList = renshiyewuMapper.selectUser(map2);
			// insertDiscount_Consumption(userList.get(0));
			MultipartFile HeadFile = user.getHeadFile();
			if (HeadFile != null) {
				String filename = HeadFile.getOriginalFilename();
				if (!"".equals(filename)) {
					InputStream is = HeadFile.getInputStream();
					String relpath = request.getSession().getServletContext().getRealPath("touxiang");
					File tempFile = new File(relpath);
					if (!tempFile.exists()) {
						tempFile.mkdirs();
					}
					String fileNameType = filename.substring(filename.lastIndexOf('.') + 1);
					filename = userList.get(0).getUserId() + "." + fileNameType;
					FileUtils.copyInputStreamToFile(is, new File(tempFile, filename));
					userList.get(0).setImageUrl("touxiang/" + filename);
				}
			} else {
				userList.get(0).setImageUrl("touxiang/man.png");
			}
			renshiyewuMapper.updateImgUser(userList.get(0));

			SystemLog systemLog = new SystemLog(null, "System_UserInfo", userList.get(0).getUserInnerId(),
					user.getUserId(), user.getUserName(), 0, null,
					Base64.encodeBase64String(SerializationUtils.serialize(userList.get(0))),
					operator.getOperatorInnerId(), sdf.format(new Date()), user.getRemark());
			Integer result1 = peizhiguanliMapper.insertSystemLog(systemLog);
			if (result1 > 0) {
				String[] AppList = user.getAppList().split(",");
				for (String AppInnerId : AppList) {
					if (AppInnerId != null && (!AppInnerId.equals(""))) {
						App app = peizhiguanliMapper.selectAppByInnerId(Integer.valueOf(AppInnerId));
						if (app != null) {
							UserPer userPer = new UserPer();
							userPer.setUserInnerId(userList.get(0).getUserInnerId());
							userPer.setAppInnerId(app.getAppInnerId());
							userPer.setAreaInnerId(app.getAreaInnerId());
							userPer.setStartTime(user.getStartTime());
							userPer.setEndTime(user.getEndTime());
							renshiyewuMapper.insertUserPer(userPer);
						}
					}
				}
				jo.put("code", 200);
				jo.put("msg", "添加成功！");
				renshiyewuMapper.updateUser(userList.get(0));
			} else {
				jo.put("code", 500);
				jo.put("msg", "添加失败！");
			}
		} else {
			jo.put("code", 500);
			jo.put("msg", "添加失败！");
		}
		return jo.toString();
	}

	/*
	 * public int insertDiscount_Consumption(User user){ Integer userTypeInnerId
	 * = user.getUserTypeInnerId(); List<MerchantDiscount> merchantDiscountList
	 * = xaiofeiguanliMapper.selectAllUseConDiscount(); int result = 0; for
	 * (MerchantDiscount merchantDiscount : merchantDiscountList) {
	 * if(merchantDiscount.getUserTypeList().contains(userTypeInnerId+"")){
	 * DiscountConsumption dis = new DiscountConsumption();
	 * dis.setProportion(merchantDiscount.getProportion());
	 * dis.setUserInnerId(user.getUserInnerId());
	 * dis.setMerchantInnerId(merchantDiscount.getMerchantInnerId());
	 * dis.setStandbyC("10000");
	 * dis.setStandbyD(merchantDiscount.getMerchantDiscountInnerId() + ",");
	 * result = xaiofeiguanliMapper.insertDiscountConsumption(dis); } } return
	 * result; }
	 */

	@Override
	@Transactional
	public String updatePWDUser(HttpServletRequest request) {
		Integer UserInnerId = Integer.valueOf(request.getParameter("UserInnerId"));
		String Password = request.getParameter("Password");
		String PasswordNew = request.getParameter("PasswordNew");
		User beforeUser = renshiyewuMapper.selectUserByInnerId(UserInnerId);
		JSONObject jo = new JSONObject();
		if (DigestUtils.md5Hex(Password).equals(beforeUser.getPassword())
				|| Password.equals(beforeUser.getPassword())) {
			User user = new User();
			user.setUserInnerId(UserInnerId);
			user.setPassword(DigestUtils.md5Hex(PasswordNew));
			Integer result = renshiyewuMapper.updatePWDUser(user);
			if (result > 0) {
				User afterUser = renshiyewuMapper.selectUserByInnerId(UserInnerId);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
				SystemLog systemLog = new SystemLog(null, "System_UserInfo", afterUser.getUserInnerId(),
						afterUser.getUserId(), afterUser.getUserName(), 1,
						Base64.encodeBase64String(SerializationUtils.serialize(beforeUser)),
						Base64.encodeBase64String(SerializationUtils.serialize(afterUser)),
						operator.getOperatorInnerId(), sdf.format(new Date()), afterUser.getRemark());
				Integer result1 = peizhiguanliMapper.insertSystemLog(systemLog);
				if (result1 > 0) {
					jo.put("code", 200);
					jo.put("msg", "修改成功！");
				} else {
					jo.put("code", 500);
					jo.put("msg", "修改失败！");
				}
				jo.put("code", 200);
				jo.put("msg", "修改成功！");
			} else {
				jo.put("code", 500);
				jo.put("msg", "修改失败！");
			}
		} else {
			jo.put("code", 500);
			jo.put("msg", "修改失败,原密码不正确！");
		}
		return jo.toString();
	}

	/*
	 * public void updateDiscount_Consumption(User beforeUser,User user){
	 * Integer userTypeInnerId = user.getUserTypeInnerId();
	 * List<MerchantDiscount> merchantDiscountList =
	 * xaiofeiguanliMapper.selectAllUseConDiscount(); int num = 0;
	 * Map<String,Object> map = new HashMap<>(); for (MerchantDiscount
	 * merchantDiscount : merchantDiscountList) {
	 * if(merchantDiscount.getUserTypeList().contains(userTypeInnerId+"")){
	 * map.put("UserInnerId", user.getUserInnerId()); map.put("MerchantInnerId",
	 * merchantDiscount.getMerchantInnerId()); DiscountConsumption
	 * selectDiscountConsumptionByUser =
	 * xaiofeiguanliMapper.selectDiscountConsumptionByUser(map);
	 * if(selectDiscountConsumptionByUser != null){
	 * 
	 * DiscountConsumption dis = new DiscountConsumption();
	 * dis.setProportion(merchantDiscount.getProportion());
	 * dis.setUserInnerId(beforeUser.getUserInnerId());
	 * dis.setMerchantInnerId(merchantDiscount.getMerchantInnerId());
	 * dis.setStandbyC("10000"); // 只记录备用 if (merchantDiscount.getStandbyD() ==
	 * null) { dis.setStandbyD(merchantDiscount.getMerchantDiscountInnerId() +
	 * ","); } else { dis.setStandbyD(merchantDiscount.getStandbyD() +
	 * merchantDiscount.getMerchantDiscountInnerId() + ","); }
	 * xaiofeiguanliMapper.updateDiscountConsumption(dis); }else{
	 * DiscountConsumption dis = new DiscountConsumption();
	 * dis.setProportion(merchantDiscount.getProportion());
	 * dis.setUserInnerId(beforeUser.getUserInnerId());
	 * dis.setMerchantInnerId(merchantDiscount.getMerchantInnerId());
	 * dis.setStandbyC("10000"); // 只记录备用 if (merchantDiscount.getStandbyD() ==
	 * null) { dis.setStandbyD(merchantDiscount.getMerchantDiscountInnerId() +
	 * ","); } else { dis.setStandbyD(merchantDiscount.getStandbyD() +
	 * merchantDiscount.getMerchantDiscountInnerId() + ","); }
	 * xaiofeiguanliMapper.insertDiscountConsumption(dis); } }else{
	 * DiscountConsumption dis = new DiscountConsumption();
	 * dis.setProportion(10000);
	 * dis.setUserInnerId(beforeUser.getUserInnerId());
	 * dis.setMerchantInnerId(merchantDiscount.getMerchantInnerId());
	 * dis.setStandbyC("10000"); // 只记录备用 if (merchantDiscount.getStandbyD() ==
	 * null) { dis.setStandbyD(merchantDiscount.getMerchantDiscountInnerId() +
	 * ","); } else { dis.setStandbyD(merchantDiscount.getStandbyD() +
	 * merchantDiscount.getMerchantDiscountInnerId() + ","); }
	 * xaiofeiguanliMapper.updateDiscountConsumption(dis); } } }
	 */

	@Override
	@Transactional
	public String updateUser(User user, HttpServletRequest request) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("DepartmentInnerId", user.getDepartmentInnerId());
		List<Department> departmentList = renshiyewuMapper.selectDepartment(map);
		if (departmentList.size() > 0) {
			user.setCompanyInnerId(departmentList.get(0).getCompanyInnerId());
		}
		User beforeUser = renshiyewuMapper.selectUserByInnerId(user.getUserInnerId());
		Integer result = renshiyewuMapper.updateUser(user);
		System.out.println(result);
		/*
		 * if(beforeUser.getUserTypeInnerId() != user.getUserTypeInnerId()){
		 * updateDiscount_Consumption(beforeUser,user); }
		 */
		JSONObject jo = new JSONObject();
		if (result > 0) {

			MultipartFile HeadFile = user.getHeadFile();
			if (HeadFile != null) {
				String filename = HeadFile.getOriginalFilename();
				if (!"".equals(filename)) {
					InputStream is = HeadFile.getInputStream();
					String relpath = request.getSession().getServletContext().getRealPath("touxiang");
					File tempFile = new File(relpath);
					if (!tempFile.exists()) {
						tempFile.mkdirs();
					}
					String fileNameType = filename.substring(filename.lastIndexOf('.') + 1);
					filename = user.getUserId() + "." + fileNameType;
					FileUtils.copyInputStreamToFile(is, new File(tempFile, filename));
					user.setImageUrl("touxiang/" + filename);
					renshiyewuMapper.updateImgUser(user);
				}
			}

			User afterUser = renshiyewuMapper.selectUserByInnerId(user.getUserInnerId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
			SystemLog systemLog = new SystemLog(null, "System_UserInfo", afterUser.getUserInnerId(),
					afterUser.getUserId(), afterUser.getUserName(), 1,
					Base64.encodeBase64String(SerializationUtils.serialize(beforeUser)),
					Base64.encodeBase64String(SerializationUtils.serialize(afterUser)), operator.getOperatorInnerId(),
					sdf.format(new Date()), afterUser.getRemark());
			Integer result1 = peizhiguanliMapper.insertSystemLog(systemLog);
			if (result1 > 0) {
				// 查询此人原有权限
				UserPer userPerIn = new UserPer();
				userPerIn.setUserInnerId(afterUser.getUserInnerId());
				List<UserPer> userPerList = renshiyewuMapper.selectUserPer(userPerIn);

				// 新的权限
				String[] AppList = afterUser.getAppList().split(",");
				for (String AppInnerId : AppList) {
					if (AppInnerId != null && (!AppInnerId.equals(""))) {
						App app = peizhiguanliMapper.selectAppByInnerId(Integer.valueOf(AppInnerId));
						if (app != null) {
							int flag = 0;
							for (UserPer userPerOld : userPerList) {
								if (userPerOld.getAppInnerId() == app.getAppInnerId()) {
									// 原来有此权限，更新
									flag = 1;
									userPerOld.setStandbyB("有此权限的标记");
									UserPer userPer = new UserPer();
									userPer.setUserPerInnerId(userPerOld.getUserPerInnerId());
									userPer.setUserInnerId(afterUser.getUserInnerId());
									userPer.setAppInnerId(app.getAppInnerId());
									userPer.setAreaInnerId(app.getAreaInnerId());
									userPer.setStartTime(afterUser.getStartTime());
									userPer.setEndTime(afterUser.getEndTime());
									renshiyewuMapper.updateUserPer(userPer);
									break;
								}
							}
							if (flag == 0) {
								// 无此权限，添加
								UserPer userPer = new UserPer();
								userPer.setUserInnerId(afterUser.getUserInnerId());
								userPer.setAppInnerId(app.getAppInnerId());
								userPer.setAreaInnerId(app.getAreaInnerId());
								userPer.setStartTime(afterUser.getStartTime());
								userPer.setEndTime(afterUser.getEndTime());
								renshiyewuMapper.insertUserPer(userPer);
							}
						}
					}
				}
				for (UserPer userPerOld : userPerList) {
					if (userPerOld.getStandbyB() == null || !userPerOld.getStandbyB().equals("有此权限的标记")) {
						// 已无此权限，删除
						renshiyewuMapper.deleteUserPer(userPerOld.getUserPerInnerId());
					}
				}

				jo.put("code", 200);
				jo.put("msg", "修改成功！");
			} else {
				jo.put("code", 500);
				jo.put("msg", "修改失败！");
			}
			jo.put("code", 200);
			jo.put("msg", "修改成功！");
		} else {
			jo.put("code", 500);
			jo.put("msg", "修改失败！");
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectMark(DataGridModel dgm, HttpServletRequest request) {
		String DepartmentList = request.getParameter("DepartmentList");
		String UserId = request.getParameter("UserId");
		String UserName = request.getParameter("UserName");
		String MarkId = request.getParameter("MarkId");
		String MarkName = request.getParameter("MarkName");
		String Remark = request.getParameter("Remark");
		String MarkState = request.getParameter("MarkState");
		String StandbyA = request.getParameter("StandbyA");
		String StandbyB = request.getParameter("StandbyB");
		Map<String, Object> map = new HashMap<String, Object>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("MarkInnerId");
			dgm.setOrder("asc");
		} else {
			if (dgm.getSort().equals("MarkStateName")) {
				dgm.setSort("MarkState");
			}
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(20);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		map.put("UserId", UserId);
		map.put("DepartmentList", DepartmentList);
		/*
		 * Operator operator = (Operator)
		 * request.getSession().getAttribute("operatorSession"); StringBuilder
		 * DepartmentInnerId = new StringBuilder();
		 * if(operator.getOperatorId().equals("admin")){
		 * map.put("DepartmentList", DepartmentList); }else{ if (DepartmentList
		 * != null) { String departmentList = operator.getDepartmentList();
		 * String[] ccdepartments = departmentList.split(","); // 存储的部门权限
		 * String[] crdepartments = DepartmentList.split(","); // 前台传入的要查询的部门
		 * for (String department : crdepartments) { for (String department1 :
		 * ccdepartments) { if (department.equals(department1)) {
		 * DepartmentInnerId.append(department + ","); break; } } }
		 * map.put("DepartmentList", DepartmentInnerId.toString()); }else{
		 * map.put("DepartmentList", operator.getDepartmentList()); } }
		 */
		map.put("UserName", UserName);
		map.put("MarkIdLike", MarkId);
		map.put("MarkName", MarkName);
		// 控制变量，同时查询人员
		map.put("User", "User");
		map.put("Remark", Remark);
		map.put("MarkStateList", MarkState);
		map.put("StandbyA", StandbyA);
		map.put("StandbyB", StandbyB);
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<Mark_User> markList = renshiyewuMapper.selectMark(map);
		for (Mark_User mark : markList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("UserId", mark.getUserId());
			row.put("UserName", mark.getUserName());
			row.put("DepartmentInnerId", mark.getDepartmentInnerId());
			row.put("DepartmentId", mark.getDepartmentId());
			row.put("DepartmentName", mark.getDepartmentName());
			row.put("CompanyInnerId", mark.getCompanyInnerId());
			row.put("CompanyId", mark.getCompanyId());
			row.put("CompanyName", mark.getCompanyName());
			row.put("MarkInnerId", mark.getMarkInnerId());
			row.put("MarkId", mark.getMarkId());
			row.put("MarkName", mark.getMarkName());
			row.put("MarkState", mark.getMarkState());
			if (mark.getMarkState() != null) {
				if (mark.getMarkState() == 0) {
					row.put("MarkStateName", "正常");
				} else if (mark.getMarkState() == 1) {
					row.put("MarkStateName", "已挂失");
				} else if (mark.getMarkState() == 2) {
					row.put("MarkStateName", "已退卡");
				}
			}
			row.put("MarkTypeInnerId", mark.getMarkTypeInnerId());
			row.put("UserInnerId", mark.getUserInnerId());
			row.put("SendDate", mark.getSendDate());
			row.put("FingerCode1", mark.getFingerCode1());
			row.put("FingerCode2", mark.getFingerCode2());
			row.put("Remark", mark.getRemark());
			row.put("StandbyA", mark.getStandbyA());
			row.put("StandbyB", mark.getStandbyB());
			row.put("StandbyC", mark.getStandbyC());
			row.put("StandbyD", mark.getStandbyD());
			row.put("MarkTypeName", mark.getMarkTypeName());
			row.put("MarkTypeId", mark.getMarkTypeId());
			rows.add(row);
		}
		jo.put("total", renshiyewuMapper.selectMarkTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	@Transactional
	public String insertMark(Mark mark, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", 100000000);
		map.put("order", "asc");
		map.put("sort", "MarkState");
		map.put("start", 0);
		map.put("MarkId", mark.getMarkId());
		map.put("MarkStateList", "0,1");
		JSONObject jo = new JSONObject();
		List<Mark_User> markList = renshiyewuMapper.selectMark(map);
		if (markList.size() > 0) {
			jo.put("code", 501);
			jo.put("msg", "此卡已发行，不能重复发卡！");
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			mark.setSendDate(sdf.format(new Date()));
			Integer UserMarkId = renshiyewuMapper.selectUserMarkId(mark.getUserInnerId());
			if (UserMarkId == null) {
				mark.setUserMarkId(1);
			} else {
				mark.setUserMarkId(UserMarkId + 1);
			}
			Integer result = renshiyewuMapper.insertMark(mark);
			if (result > 0) {
				Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("rows", 100000000);
				map1.put("order", "asc");
				map1.put("sort", "MarkState");
				map1.put("start", 0);
				map1.put("MarkId", mark.getMarkId());
				List<Mark_User> markList1 = renshiyewuMapper.selectMark(map);

				if (!markList1.isEmpty()) {
					SystemLog systemLog = new SystemLog(null, "System_Mark", markList1.get(0).getMarkInnerId(),
							mark.getMarkId(), mark.getMarkName(), 0, null,
							Base64.encodeBase64String(SerializationUtils.serialize(mark)),
							operator.getOperatorInnerId(), sdf.format(new Date()), mark.getRemark());
					Integer result1 = peizhiguanliMapper.insertSystemLog(systemLog);
					if (result1 > 0) {
						jo.put("code", 200);
						jo.put("msg", "添加成功！");
					} else {
						jo.put("code", 500);
						jo.put("msg", "添加失败！");
					}
				} else {
					jo.put("code", 500);
					jo.put("msg", "添加失败！");
				}
			} else {
				jo.put("code", 500);
				jo.put("msg", "添加失败！");
			}
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String insertBuMark(Mark mark, HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", 100000000);
		map.put("order", "asc");
		map.put("sort", "MarkState");
		map.put("start", 0);
		map.put("MarkId", request.getParameter("MarkId"));
		map.put("MarkStateList", "0,1");
		JSONObject jo = new JSONObject();
		List<Mark_User> markList = renshiyewuMapper.selectMark(map);
		if (markList.size() > 0) {
			jo.put("code", 501);
			jo.put("msg", "此卡已发行，不能重复发卡！");
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			mark.setSendDate(sdf.format(new Date()));
			Integer result = renshiyewuMapper.insertBuMark(mark);
			if (result > 0) {
				Integer result2 = 0;
				Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
				if (request.getParameter("HKMoney") != "") {
					Map<String, Object> map1 = new HashMap<>();
					Integer userInnerId = Integer.valueOf(request.getParameter("UserInnerId"));
					User user = renshiyewuMapper.selectUserByInnerId(userInnerId);
					System.out.println(request.getParameter("HKMoney"));
					Integer HKMoney = Integer.valueOf(request.getParameter("HKMoney"));
					map1.put("UserInnerId", userInnerId);
					map1.put("AccountTypeInnerId", 4);
					Account account = zhanghuyewuMapper.selectAccount(map1);
					AccountLog accountLog = new AccountLog();
					accountLog.setAccountInnerId(account.getAccountInnerId());
					accountLog.setUserInnerId(userInnerId);
					accountLog.setDepartmentInnerId(user.getDepartmentInnerId());
					accountLog.setCompanyInnerId(user.getCompanyInnerId());
					accountLog.setAccountTypeInnerId(4);
					accountLog.setMoney(HKMoney);
					accountLog.setBeforeMoney(account.getMoney());
					accountLog.setAfterMoney(HKMoney + account.getMoney());
					accountLog.setInOrOut(0);
					accountLog.setRechargeType(7);
					accountLog.setMoneySource(0);
					accountLog.setOperator("Operator" + operator.getOperatorInnerId());
					accountLog.setPayee("Operator" + operator.getOperatorInnerId());
					accountLog.setAccountDate(sdf.format(new Date()));
					result2 = zhanghuyewuMapper.insertAccountLog(accountLog);
				}
				SystemLog systemLog = new SystemLog(null, "System_Mark", mark.getMarkInnerId(), mark.getMarkId(),
						mark.getMarkName(), 6, null, Base64.encodeBase64String(SerializationUtils.serialize(mark)),
						operator.getOperatorInnerId(), sdf.format(new Date()), mark.getRemark());
				Integer result1 = peizhiguanliMapper.insertSystemLog(systemLog);
				if (result1 > 0) {
					jo.put("code", 200);
					jo.put("msg", "添加成功！");
				} else {
					jo.put("code", 500);
					jo.put("msg", "添加失败！");
				}

			} else {
				jo.put("code", 500);
				jo.put("msg", "添加失败！");
			}
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String updateMark(Mark mark, HttpServletRequest request) {
		Mark beforeMark = renshiyewuMapper.selectMarkByInnerId(mark.getMarkInnerId());
		Integer result = renshiyewuMapper.updateMark(mark);
		JSONObject jo = new JSONObject();
		if (result > 0) {
			Mark afterMark = renshiyewuMapper.selectMarkByInnerId(mark.getMarkInnerId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
			SystemLog systemLog = new SystemLog(null, "System_Mark", afterMark.getMarkInnerId(), afterMark.getMarkId(),
					afterMark.getMarkName(), 1, Base64.encodeBase64String(SerializationUtils.serialize(beforeMark)),
					Base64.encodeBase64String(SerializationUtils.serialize(afterMark)), operator.getOperatorInnerId(),
					sdf.format(new Date()), afterMark.getRemark());
			Integer result1 = peizhiguanliMapper.insertSystemLog(systemLog);
			if (result1 > 0) {
				jo.put("code", 200);
				jo.put("msg", "修改成功！");
			} else {
				jo.put("code", 500);
				jo.put("msg", "修改失败！");
			}
		} else {
			jo.put("code", 500);
			jo.put("msg", "修改失败！");
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String updateMarkState(Mark mark, HttpServletRequest request) {
		Mark beforeMark = renshiyewuMapper.selectMarkByInnerId(mark.getMarkInnerId());
		Integer result = renshiyewuMapper.updateMarkState(mark);
		Mark afterMark = renshiyewuMapper.selectMarkByInnerId(mark.getMarkInnerId());
		JSONObject jo = new JSONObject();
		if (result > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (mark.getMarkState() == 1) {
				Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
				SystemLog systemLog = new SystemLog(null, "System_Mark", beforeMark.getMarkInnerId(),
						beforeMark.getMarkId(), beforeMark.getMarkName(), 3,
						Base64.encodeBase64String(SerializationUtils.serialize(beforeMark)),
						Base64.encodeBase64String(SerializationUtils.serialize(afterMark)),
						operator.getOperatorInnerId(), sdf.format(new Date()), beforeMark.getRemark());
				Integer result1 = peizhiguanliMapper.insertSystemLog(systemLog);
				if (result1 > 0) {
					jo.put("code", 200);
					jo.put("msg", "挂失成功！");
				} else {
					jo.put("code", 500);
					jo.put("msg", "挂失失败！");
				}
			} else if (mark.getMarkState() == 0) {
				Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
				SystemLog systemLog = new SystemLog(null, "System_Mark", beforeMark.getMarkInnerId(),
						beforeMark.getMarkId(), beforeMark.getMarkName(), 4,
						Base64.encodeBase64String(SerializationUtils.serialize(beforeMark)),
						Base64.encodeBase64String(SerializationUtils.serialize(afterMark)),
						operator.getOperatorInnerId(), sdf.format(new Date()), beforeMark.getRemark());
				Integer result1 = peizhiguanliMapper.insertSystemLog(systemLog);
				if (result1 > 0) {
					jo.put("code", 200);
					jo.put("msg", "解挂成功！");
				} else {
					jo.put("code", 500);
					jo.put("msg", "挂失失败！");
				}
			} else if (mark.getMarkState() == 2) {
				Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
				SystemLog systemLog = new SystemLog(null, "System_Mark", beforeMark.getMarkInnerId(),
						beforeMark.getMarkId(), beforeMark.getMarkName(), 5,
						Base64.encodeBase64String(SerializationUtils.serialize(beforeMark)),
						Base64.encodeBase64String(SerializationUtils.serialize(afterMark)),
						operator.getOperatorInnerId(), sdf.format(new Date()), beforeMark.getRemark());
				Integer result1 = peizhiguanliMapper.insertSystemLog(systemLog);
				if (result1 > 0) {
					jo.put("code", 200);
					jo.put("msg", "退卡成功！");
				} else {
					jo.put("code", 500);
					jo.put("msg", "挂失失败！");
				}
			}
		} else {
			jo.put("code", 500);
			if (mark.getMarkState() == 1) {
				jo.put("msg", "挂失失败！");
			} else if (mark.getMarkState() == 0) {
				jo.put("msg", "解挂失败！");
			} else if (mark.getMarkState() == 2) {
				jo.put("msg", "退卡失败！");
			}
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectUserPer(Integer UserInnerId) {
		UserPer userPerIn = new UserPer();
		userPerIn.setUserInnerId(UserInnerId);
		JSONArray ja = new JSONArray();
		List<UserPer> userPerList = renshiyewuMapper.selectUserPer(userPerIn);
		for (UserPer userPer : userPerList) {
			JSONObject jo = new JSONObject();
			jo.put("UserPerInnerId", userPer.getUserPerInnerId());
			jo.put("StartTime", userPer.getStartTime().substring(0, 19));
			jo.put("EndTime", userPer.getEndTime().substring(0, 19));
			User user = renshiyewuMapper.selectUserByInnerId(userPer.getUserInnerId());
			if (user != null) {
				jo.put("UserName", user.getUserName());
			}
			App app = peizhiguanliMapper.selectAppByInnerId(userPer.getAppInnerId());
			if (app != null) {
				jo.put("AppName", app.getAppName());
			} else {
				jo.put("AppName", "无");
			}
			Area area = peizhiguanliMapper.selectAreaByInnerId(userPer.getAreaInnerId());
			if (area != null) {
				jo.put("AreaName", area.getAreaName());
			}
			ja.add(jo);
		}
		return ja.toString();
	}

	@Override
	@Transactional
	public String updateUserPer(String str) {
		String[] strList = str.split(";");
		for (String string : strList) {
			String[] userPerElement = string.split(",");
			if (userPerElement.length == 3) {
				UserPer userPer = renshiyewuMapper.selectUserPerByInnerId(Integer.valueOf(userPerElement[0]));
				if (userPer != null) {
					userPer.setStartTime(userPerElement[1]);
					userPer.setEndTime(userPerElement[2]);
					renshiyewuMapper.updateUserPer(userPer);
				}
			}
		}
		JSONObject jo = new JSONObject();
		jo.put("code", 200);
		jo.put("msg", "修改成功！");
		return jo.toString();
	}

	public List<String[]> readXls(InputStream file) throws IOException {
		// List<String[]> 的元素 行数组String[]为excel中的每一行
		List<String[]> list = new ArrayList<String[]>();
		// 将is流实例到 一个excel流里
		XSSFWorkbook hwk = new XSSFWorkbook(file);
		file.close();
		// 得到book第一个工作薄sheet
		XSSFSheet sh = hwk.getSheetAt(0);
		// 总行数
		int rows = sh.getLastRowNum() + 1 - sh.getFirstRowNum();
		// 从头标题下一行开始
		UpLoadExcel uploadExcel = new UpLoadExcel();
		for (int i = 1; i < rows; i++) {
			XSSFRow row = sh.getRow(i);
			int cols = row.getLastCellNum() + 1 - row.getFirstCellNum(); // 该行的总列数
			String[] str = new String[cols]; // 用来存放该行每一列的值
			for (int j = 0; j < cols; j++) {
				Cell col = row.getCell((short) j);
				String colum = uploadExcel.getValue(col);// 对列值进行处理
				// String colum = UpLoadExcel.getStringCellValue(col);
				Cell colNext = row.getCell((short) (j + 1));
				uploadExcel.getValue(colNext);
				// UpLoadExcel.getStringCellValue(colNext);
				if (col != null) { // 该列不为空，直接读到 行数组里
					str[j] = colum.toString();
				} else { // 该列为空
					// 该列的后面一列不为空，用空字符串占位
					if (colNext != null) {
						Object colValue = "";
						str[j] = colValue.toString();
					}
				}
			}
			list.add(str);
		}
		return list;
	}

	@Override
	@Transactional
	public String showUserExcel(HttpServletRequest request) throws IOException {
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		JSONObject json = new JSONObject();
		String result = "";
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile("fileExcel");
		InputStream fs = multipartFile.getInputStream();
		List<String[]> AllowanceList = readXls(fs);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", 100000000);
		map.put("order", "asc");
		map.put("sort", "UserInnerId");
		map.put("start", 0);
		List<User> userList = renshiyewuMapper.selectUser(map);
		List<String> userIdList = new ArrayList<String>();
		List<String> mobileList = new ArrayList<String>();
		for (String[] allowance : AllowanceList) {
			Map<String, Object> row = new HashMap<String, Object>();
			// Validate 0:正常 1：非法 2：警告
			row.put("Validate", 0);
			for (User user : userList) {
				if (user.getUserId() != null && !user.getUserId().equals("")) {
					userIdList.add(user.getUserId());
				}
				if (user.getMobile() != null && !user.getMobile().equals("")) {
					mobileList.add(user.getMobile());
				}
			}
			if (allowance.length < 1) {
				row.put("UserId", "(人员编号不能为空)");
				row.put("Validate", 1);
			} else if (allowance[0] == null || allowance[0].equals("") || allowance[0].equals(" ")) {
				row.put("UserId", allowance[0] + "(人员编号不能为空)");
				row.put("Validate", 1);
			} else if (userIdList.contains(allowance[0])) {
				row.put("UserId", allowance[0] + "(此人员编号已使用)");
				row.put("Validate", 1);
			} else {
				row.put("UserId", allowance[0]);
				userIdList.add(allowance[0]);
			}

			if (allowance.length < 2) {
				row.put("UserName", "(人员姓名不能为空)");
				row.put("Validate", 1);
			} else if (allowance[1] == null || allowance[1].equals("") || allowance[1].equals(" ")) {
				row.put("UserName", allowance[1] + "(人员姓名不能为空)");
				row.put("Validate", 1);
			} else {
				row.put("UserName", allowance[1]);
			}

			if (allowance.length < 3) {
				row.put("UserTypeName", "(人员类型不能为空)");
				row.put("Validate", 1);
			} else if (allowance[2] == null || allowance[2].equals("") || allowance[2].equals(" ")) {
				row.put("UserTypeName", allowance[2] + "(人员类型不能为空)");
				row.put("Validate", 1);
			} else {
				map.put("sort", "UserTypeInnerId");
				map.put("UserTypeNameEqu", allowance[2]);
				List<UserType> userTypeList = peizhiguanliMapper.selectUserType(map);
				if (userTypeList.size() == 0) {
					row.put("UserTypeName", allowance[2] + "(此人员类型不存在)");
					row.put("Validate", 1);
				} else {
					row.put("UserTypeName", allowance[2]);
				}
			}

			if (allowance.length < 4) {
				row.put("DepartmentId", "(部门不能为空)");
				row.put("Validate", 1);
			} else if (allowance[3] == null || allowance[3].equals("") || allowance[3].equals(" ")) {
				row.put("DepartmentId", allowance[3] + "(部门不能为空)");
				row.put("Validate", 1);
			} else {
				map.put("sort", "DepartmentInnerId");
				map.put("DepartmentIdEqu", allowance[3]);
				List<Department> departmentList = renshiyewuMapper.selectDepartment(map);
				if (departmentList.size() == 0) {
					row.put("DepartmentId", allowance[3] + "(此部门不存在)");
					row.put("Validate", 1);
				} else {
					row.put("DepartmentId", allowance[3]);
				}
			}

			if (allowance[4] == null || allowance[4].equals("") || allowance[4].equals(" ")) {

			} else if (mobileList.contains(allowance[4])) {
				row.put("Mobile", allowance[4] + "(此手机号已使用)");
				row.put("Validate", 1);
			} else {
				row.put("Mobile", allowance[4]);
				mobileList.add(allowance[4]);
			}
			rows.add(row);
		}
		json.put("rows", rows);
		result = json.toString();
		return result;
	}

	@Override
	@Transactional
	public String importUserExcel(HttpServletRequest request) throws Exception {
		JSONObject jsonObjects = new JSONObject();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile("fileExcel");
		InputStream fs = multipartFile.getInputStream();
		List<String[]> AllowanceList = readXls(fs);
		Integer number = renshiyewuMapper.selectuserMaxmum();
		Integer maxUserNums = renshiyewuMapper.selectMaxUserNums();
		if (AllowanceList.size() + number > maxUserNums) {
			jsonObjects.put("code", 500);
			jsonObjects.put("msg", "因导入数据过多，超了最大限制数，所以导入失败！");
		} else {
			for (String[] allowance : AllowanceList) {
				User user = new User();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("rows", 100000000);
				map.put("order", "asc");
				map.put("start", 0);
				map.put("sort", "UserTypeInnerId");
				map.put("UserTypeNameEqu", allowance[2]);
				List<UserType> userTypeList = peizhiguanliMapper.selectUserType(map);

				map.put("sort", "DepartmentInnerId");
				map.put("DepartmentIdEqu", allowance[3]);
				List<Department> departmentList = renshiyewuMapper.selectDepartment(map);
				user.setUserId(allowance[0]);
				user.setUserName(allowance[1]);
				user.setUserTypeInnerId(userTypeList.get(0).getUserTypeInnerId());
				user.setCompanyInnerId(departmentList.get(0).getCompanyInnerId());
				user.setDepartmentInnerId(departmentList.get(0).getDepartmentInnerId());
				user.setPassword(DigestUtils.md5Hex("123456"));
				user.setAreaList(userTypeList.get(0).getAreaList());
				user.setAppList(userTypeList.get(0).getAppList());
				user.setStartTime("2017-01-01 00:00:00");
				user.setEndTime("2050-01-01 00:00:00");
				user.setMobile(allowance[4]);
				Integer result1 = renshiyewuMapper.insertUser(user);
				if (result1 > 0) {
					String[] appList = user.getAppList().split(",");
					for (String AppInnerId : appList) {
						if (AppInnerId != null && (!AppInnerId.equals(""))) {
							App app = peizhiguanliMapper.selectAppByInnerId(Integer.valueOf(AppInnerId));
							if (app != null) {
								UserPer userPer = new UserPer();

								// 在数据库查找此人员
								Map<String, Object> map2 = new HashMap<String, Object>();
								map2.put("rows", 100000000);
								map2.put("order", "asc");
								map2.put("sort", "UserInnerId");
								map2.put("start", 0);
								map2.put("UserIdEqu", user.getUserId());
								List<User> userList = renshiyewuMapper.selectUser(map2);

								userPer.setUserInnerId(userList.get(0).getUserInnerId());
								userPer.setAppInnerId(app.getAppInnerId());
								userPer.setAreaInnerId(app.getAreaInnerId());
								userPer.setStartTime(user.getStartTime());
								userPer.setEndTime(user.getEndTime());
								renshiyewuMapper.insertUserPer(userPer);
								renshiyewuMapper.updateUser(userList.get(0));
							}
						}
					}
				}
				jsonObjects.put("code", 200);
				jsonObjects.put("msg", "导入成功！");

			}
		}
		return jsonObjects.toString();
	}

	@Override
	public String selectuserMaxmum(HttpServletRequest request) {
		JSONObject jo = new JSONObject();
		Integer number = renshiyewuMapper.selectuserMaxmum();
		if (number >= renshiyewuMapper.selectMaxUserNums()) {
			jo.put("code", 500);
			jo.put("msg", "当前人数已为最大限制数！");
		} else {
			jo.put("code", 200);
		}
		return jo.toString();
	}

	@Override
	public String selectMarkLog(HttpServletRequest request, DataGridModel dgm) {
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("LogInnerId");
			dgm.setOrder("desc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(20);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		map.put("DepartmentList", request.getParameter("DepartmentList"));
		map.put("LogTypeList", request.getParameter("LogType"));
		map.put("UserId", request.getParameter("UserId"));
		map.put("UserName", request.getParameter("UserName"));
		map.put("MarkId", request.getParameter("MarkId"));
		map.put("TableName", "System_Mark");
		map.put("MarkName", request.getParameter("MarkName"));
		map.put("Remark", request.getParameter("Remark"));
		String StartTime = "";
		String EndTime = "";
		if (request.getParameter("StartTime") == null || request.getParameter("StartTime").equals("")) {
			StartTime = sdf.format(new Date());
		} else {
			StartTime = request.getParameter("StartTime");
		}
		if (request.getParameter("EndTime") == null || request.getParameter("EndTime").equals("")) {
			EndTime = sdf.format(new Date());
		} else {
			EndTime = request.getParameter("EndTime");
		}
		map.put("StartTime", StartTime + " 00:00:00");
		map.put("EndTime", EndTime + " 23:59:59");

		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<MarkLog> markLogList = renshiyewuMapper.selectMarkLog(map);
		for (MarkLog markLog : markLogList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("DepartmentInnerId", markLog.getDepartmentInnerId());
			row.put("UserName", markLog.getUserName());
			row.put("UserId", markLog.getUserId());
			row.put("DepartmentName", markLog.getDepartmentName());
			row.put("MarkId", markLog.getMarkId());
			row.put("MarkName", markLog.getRowName());
			row.put("MarkTypeName", markLog.getMarkTypeName());
			if (markLog.getLogType() == 3) {
				row.put("MarkLogType", "挂失");
			} else if (markLog.getLogType() == 0) {
				row.put("MarkLogType", "开户");
			} else if (markLog.getLogType() == 4) {
				row.put("MarkLogType", "解挂");
			} else if (markLog.getLogType() == 5) {
				row.put("MarkLogType", "退卡");
			} else if (markLog.getLogType() == 6) {
				row.put("MarkLogType", "补卡");
			}
			row.put("OperatorName", markLog.getOperatorName());
			row.put("OperatorDate", markLog.getLogDatetime().substring(0, 19));
			rows.add(row);
		}
		JSONObject jo = new JSONObject();
		jo.put("total", renshiyewuMapper.selectMarkLogTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	@Transactional
	public String importUserImage(MultipartHttpServletRequest requestFile, HttpServletRequest request) {
		// TODO Auto-generated method stub

		Map<String, Object> mso = new HashMap<String, Object>();

		Map<String, MultipartFile> fileMap = requestFile.getFileMap();
		String path = requestFile.getSession().getServletContext().getRealPath("/");
		System.out.println("path:" + path);
		Date currentTime = new Date();
		long prefix = currentTime.getTime();
		StringBuffer attachIds = new StringBuffer();
		for (Map.Entry<String, MultipartFile> f : fileMap.entrySet()) {
			MultipartFile file = f.getValue();
			if (!isLegalFile(file)) {
				String msg = "is a illegal file";
				throw new RuntimeException(msg);
			}
			String originalFileName = file.getOriginalFilename();
			File fileDir = new File(path + "/touxiang" + File.separator);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}

			File files = new File(path + "/touxiang" + File.separator + originalFileName);
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(files);
				fileOutputStream.write(file.getBytes());
				fileOutputStream.flush();

				attachIds.append(originalFileName + ",");

				int i = renshiyewuMapper.updateImgUserByCode(mso);
				// mso.put("locationUrl", originalFileName);

				String urlAddress = Constant.Camera_Url + "/g/test/";

				// PubFun.getCameraImageHeaderMap(urlAddress,mso,"", "image");

				// String url = "http://192.168.1.30:8061/g/test/";
				String fileName = path + "/touxiang/" + originalFileName;
				Map<String, String> textMap = new HashMap<String, String>();
				// 可以设置多个input的name，value
				textMap.put("tag", "/touxiang/" + originalFileName);
				/* textMap.put("type", "2"); */
				// 设置file的name，路径
				Map<String, String> fileImageMap = new HashMap<String, String>();
				fileImageMap.put("image", fileName);
				String contentType = "";// image/png
				String ret = HttpUploadFile.formUpload(urlAddress, textMap, fileImageMap, contentType);
				System.out.println(ret);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fileOutputStream != null) {
					try {
						fileOutputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
		// System.out.println(attachIds.toString().substring(0,attachIds.toString().length()-1));
		return attachIds.toString().substring(0, attachIds.toString().length() - 1);
	}

	private final String[] fileType = new String[] { ".dat", ".264", ".h264", ".mp4", ".dav", ".MP4", ".AVI", ".ts",
			".avi", ".mpg", ".rmvb", ".flv", ".rm", ".mov", ".wmv", ".JPG", ".bmp", ".png", ".BMP", ".jpg", ".PNG",
			".gif", ".xlsx", ".xls", ".txt", ".pdf", ".doc", ".docx", ".rar", ".zip", ".7z" };

	private boolean isLegalFile(MultipartFile file) {
		String originalFileName = file.getOriginalFilename();
		for (String ft : fileType) {
			if (originalFileName.endsWith(ft)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String SelectQuanxianByOperInnerId(HttpServletRequest request) {
		String FOperInnerId = request.getParameter("FOperInnerId");
		Operator operator = renshiyewuMapper.selectOperatorByOperatorInnerId(Integer.valueOf(FOperInnerId));
		String pageList = operator.getPageList();
		String[] pageLists = pageList.split(",");
		JSONObject jo = new JSONObject();
		jo.put("peizhi", 0);
		jo.put("renshi", 0);
		jo.put("zhanghu", 0);
		jo.put("tingche", 0);
		for (String page : pageLists) {
			if (page.equals("1")) {
				jo.put("peizhi", 1);
			} else if (page.equals("2")) {
				jo.put("renshi", 1);
			} else if (page.equals("3")) {
				jo.put("zhanghu", 1);
			} else if (page.equals("8")) {
				jo.put("tingche", 1);
			}
		}
		return jo.toString();
	}

	@Override
	public Operator selectOperatorByOperId(String userId) {
		return renshiyewuMapper.selectOperatorByOperId(userId);
	}

	@Override
	public String updateuserShouquan(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String userInnerId = request.getParameter("UserInnerId");
		String UState = request.getParameter("UState");
		map.put("UserInnerId", userInnerId);
		map.put("UState", UState);
		int rt = renshiyewuMapper.updateUserShouquan(map);
		JSONObject jo = new JSONObject();
		if (rt > 0) {
			jo.put("code", 200);
			jo.put("msg", "授权成功");
		} else {
			jo.put("code", 500);
			jo.put("msg", "授权失败");
		}
		return jo.toString();
	}


	@Override
	public String updateDepCon(HttpServletRequest request) {
		Map<String,Object> map = new HashMap<>();
		String Flag = request.getParameter("Flag");
		String InnerId = request.getParameter("InnerId");
		String ConPower = request.getParameter("ConPower");
		map.put("Flag", Flag);
		map.put("InnerId", InnerId);
		JSONObject jo = new JSONObject();
		Integer rt = 0;
		if("1".equals(ConPower)){
			// 添加
			if("Company".equals(Flag)){ // 公司
				//首先判断是否存在部门，有则返回失败，没有则新增
				List<Integer> depList = renshiyewuMapper.selectDepInnerIdByCompanyInnerId(Integer.valueOf(InnerId));
				StringBuilder deps = new StringBuilder();
				for (Integer integer : depList) {
					deps.append(integer+",");
				}
				map.put("DepList", deps.toString().substring(0, deps.length()-1) );
				Integer depTot = renshiyewuMapper.selectDepConInfo(map);
				if(depTot > 0){
					jo.put("code", 500);
					jo.put("msg", "此公司下已有部门可用来消费");
					return jo.toString();
				}else{
					map.put("CompanyInnerId", InnerId);
				}
			}else{  // 部门
				// 授权查询部门所在的公司，看是否有其他部门存在，存在则返回失败，不存在新增
				Department dep = renshiyewuMapper.selectDepartmentByInnerId(Integer.valueOf(InnerId));
				List<Integer> depList = renshiyewuMapper.selectDepInnerIdByCompanyInnerId(dep.getCompanyInnerId());
				StringBuilder deps = new StringBuilder();
				for (Integer integer : depList) {
					deps.append(integer+",");
				}
				map.put("DepList", deps.toString().substring(0, deps.length()-1) );
				Integer depTot = renshiyewuMapper.selectDepConInfo(map);
				if(depTot > 0){
					jo.put("code", 500);
					jo.put("msg", "此部门所在公司下已有部门可用来消费");
					return jo.toString();
				}else{
					Integer companyTotal = renshiyewuMapper.selectCompanyConPowerByInnerId(dep.getCompanyInnerId());
					if(companyTotal > 0){
						jo.put("code", 500);
						jo.put("msg", "此部门所在公司可用来消费");
						return jo.toString();
					}else{
						map.put("DepartmentInnerId", InnerId);
					}
				}
			}
			rt = renshiyewuMapper.insertPayInfo(map);
		}else{
			// 删除
			rt = renshiyewuMapper.deletePayInfo(map);
		}
		if(rt > 0){
			jo.put("code", 200);
			jo.put("msg", "授权成功 ");
		}else{
			jo.put("code", 500);
			jo.put("msg", "授权失败 ");
		}
		return jo.toString();
	}

	@Override
	public String CompanyPower(HttpServletRequest request) {
		Map<String,Object> map = new HashMap<>();
		JSONObject jo = new JSONObject();
		map.put("InnerId", request.getParameter("InnerId"));
		map.put("Flag", request.getParameter("Flag"));
		Integer rt = renshiyewuMapper.selectCompanyPower(map);
		if(rt > 0){
			jo.put("Power", 1);
		}else{
			jo.put("Power", 2);
		}
		return jo.toString();
	}
}
