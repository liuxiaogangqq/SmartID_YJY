package com.dhsr.smartid.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.dhsr.smartid.peizhiguanli.dao.PeizhiguanliMapper;
import com.dhsr.smartid.peizhiguanli.domain.UserType;
import com.dhsr.smartid.renshiyewu.dao.RenshiyewuMapper;
import com.dhsr.smartid.renshiyewu.domain.Company;
import com.dhsr.smartid.renshiyewu.domain.Department;
import com.dhsr.smartid.renshiyewu.domain.User;
import com.dhsr.smartid.tongbu.dao.TongbuMapper;
import com.dhsr.smartid.tongbu.domain.LFCompany;
import com.dhsr.smartid.tongbu.domain.LFDep;
import com.dhsr.smartid.tongbu.domain.LFUser;
import com.dhsr.smartid.util.LogInfo;

//@Component
public class RenshiTaskController {
	
	@Resource
	private TongbuMapper tongbuMapper;
	@Resource
	private RenshiyewuMapper renshiyewuMapper;
	@Resource
	private PeizhiguanliMapper peizhiguanliMapper;
	
	@Scheduled(cron = "0 15 */5 * * ?")
	public void tongbugongsi(){
		try {
			System.out.println("公司同步****");
			List<LFCompany> companyList = tongbuMapper.selectCompany();
			//LogInfo.logInfo("公司信息："+companyList.toString());
			Map<String,Object> map = new HashMap<>();
			map.put("rows", 1000);
			map.put("order", "asc");
			map.put("sort", "CompanyInnerId");
			map.put("start", 0);
			Company company = new Company();
			for (LFCompany lfCompany : companyList) {
				company.setAreaInnerId(1);
				company.setCompanyName(lfCompany.getUnitname());
				company.setCompanyId(lfCompany.getUnitcode());
				map.put("StandbyA", lfCompany.getPk_corp());
				company.setDelFlag(0);
				company.setStandbyA(lfCompany.getPk_corp());
				company.setStandbyB(lfCompany.getFathercorp());
				List<Company> selectCompany = renshiyewuMapper.selectCompany(map);
				if(selectCompany.isEmpty()){
					renshiyewuMapper.insertCompany(company);
					User user = new User();
					user.setUserId(company.getCompanyId());
					user.setUserName(company.getCompanyName());
					user.setUserTypeInnerId(-1);
					user.setDepartmentInnerId(0);
					user.setCompanyInnerId(company.getCompanyInnerId());
					user.setStartTime("2019-08-01 00:00:00");
					user.setEndTime("2069-08-01 00:00:00");
					user.setAppList("1");
					user.setAreaList("1");
					user.setPassword("1111");
					user.setUState(1);
					renshiyewuMapper.insertUser(user); 
				}else{
					company.setCompanyInnerId(selectCompany.get(0).getCompanyInnerId());
					renshiyewuMapper.updateCompany(company);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron = "0 17 */5 * * ?")
	public void tongbubumen(){
		try {
			System.out.println("部门同步****");
			List<LFDep> depList = tongbuMapper.selectDepartment();
			//LogInfo.logInfo("部门信息："+depList.toString());
			Map<String,Object> map = new HashMap<>();
			map.put("rows", 1000);
			map.put("order", "asc");
			map.put("start", 0);
			for (LFDep lFDep : depList) {
				Department dep = new Department();
				map.put("StandbyA", lFDep.getPk_corp());
				map.put("sort", "CompanyInnerId");
				List<Company> companyList = renshiyewuMapper.selectCompany(map);
				if(companyList.isEmpty()){
					System.out.println(map);
					continue;
				}
				map.put("CompanyInnerId", companyList.get(0).getCompanyInnerId());
				dep.setCompanyInnerId(companyList.get(0).getCompanyInnerId());
				map.put("sort", "DepartmentInnerId");
				if(lFDep.getDeptclass().equals("1")){
					firstDep(map,dep,lFDep);
				}else if(lFDep.getDeptclass().equals("2")){
					Department firstDep = firstDep(map,dep,lFDep);
					secondDep(map,dep,lFDep,firstDep.getDepartmentInnerId());
				}else if(lFDep.getDeptclass().equals("3")){
					Department firstDep = firstDep(map,dep,lFDep);
					Department secondDep = secondDep(map,dep,lFDep,firstDep.getDepartmentInnerId());
					threeDep(map,dep,lFDep,secondDep.getDepartmentInnerId());
				}else{
					Department firstDep = firstDep(map,dep,lFDep);
					Department secondDep = secondDep(map,dep,lFDep,firstDep.getDepartmentInnerId());
					Department threeDep = threeDep(map,dep,lFDep,secondDep.getDepartmentInnerId());
					fourthDep(map,dep,lFDep,threeDep.getDepartmentInnerId());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Department firstDep(Map<String,Object> map,Department dep,LFDep lfDep){
		//map.put("DepartmentNameEqu", lfDep.getFirst_deptname());
		map.put("DepartmentIdEqu", lfDep.getPk_firstdept());
		List<Department> department = renshiyewuMapper.selectDepartment(map);
		dep.setDepartmentId(lfDep.getPk_firstdept());
		dep.setDepartmentLevel(1);
		dep.setDepartmentName(lfDep.getFirst_deptname());
		dep.setUpInnerId(0);
		if(department.isEmpty()){
			try {
				renshiyewuMapper.insertDepartment(dep);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(dep);
				e.printStackTrace();
			}
			User user = new User();
			user.setUserId(dep.getDepartmentId());
			user.setUserName(dep.getDepartmentName());
			user.setUserTypeInnerId(-1);
			user.setDepartmentInnerId(dep.getDepartmentInnerId());
			user.setCompanyInnerId(dep.getCompanyInnerId());
			user.setStartTime("2019-08-01 00:00:00");
			user.setEndTime("2069-08-01 00:00:00");
			user.setAppList("1");
			user.setAreaList("1");
			user.setPassword("1111");
			user.setUState(1);
			renshiyewuMapper.insertUser(user);
		}else{
			dep.setDepartmentInnerId(department.get(0).getDepartmentInnerId());
			renshiyewuMapper.updateDepartment(dep);
			renshiyewuMapper.updateDepartmentUser(dep);
		}
		return dep;
	}
	
	private Department secondDep(Map<String,Object> map,Department dep,LFDep lfDep,int upInnerId){
		//map.put("DepartmentNameEqu", lfDep.getSecond_deptname());
		map.put("DepartmentIdEqu", lfDep.getPk_seconddept());
		List<Department> department = renshiyewuMapper.selectDepartment(map);
		dep.setDepartmentId(lfDep.getPk_seconddept());
		dep.setDepartmentLevel(2);
		dep.setDepartmentName(lfDep.getSecond_deptname());
		dep.setUpInnerId(upInnerId);
		if(department.isEmpty()){
			try {
				renshiyewuMapper.insertDepartment(dep);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(dep);
				e.printStackTrace();
			}
			User user = new User();
			user.setUserId(dep.getDepartmentId());
			user.setUserName(dep.getDepartmentName());
			user.setUserTypeInnerId(-1);
			user.setDepartmentInnerId(dep.getDepartmentInnerId());
			user.setCompanyInnerId(dep.getCompanyInnerId());
			user.setStartTime("2019-08-01 00:00:00");
			user.setEndTime("2069-08-01 00:00:00");
			user.setAppList("1");
			user.setAreaList("1");
			user.setPassword("1111");
			user.setUState(1);
			renshiyewuMapper.insertUser(user);
		}else{
			dep.setDepartmentInnerId(department.get(0).getDepartmentInnerId());
			renshiyewuMapper.updateDepartment(dep);
			renshiyewuMapper.updateDepartmentUser(dep);
		}
		return dep;
	}
	
	private Department threeDep(Map<String,Object> map,Department dep,LFDep lfDep,int upInnerId){
		//map.put("DepartmentNameEqu", lfDep.getThird_deptname());
		map.put("DepartmentIdEqu", lfDep.getPk_thirddept());
		List<Department> department = renshiyewuMapper.selectDepartment(map);
		dep.setDepartmentId(lfDep.getPk_thirddept());
		dep.setDepartmentLevel(3);
		dep.setDepartmentName(lfDep.getThird_deptname());
		dep.setUpInnerId(upInnerId);
		if(department.isEmpty()){
			try {
				renshiyewuMapper.insertDepartment(dep);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(dep);
				e.printStackTrace();
			}
			User user = new User();
			user.setUserId(dep.getDepartmentId());
			user.setUserName(dep.getDepartmentName());
			user.setUserTypeInnerId(-1);
			user.setDepartmentInnerId(dep.getDepartmentInnerId());
			user.setCompanyInnerId(dep.getCompanyInnerId());
			user.setStartTime("2019-08-01 00:00:00");
			user.setEndTime("2069-08-01 00:00:00");
			user.setAppList("1");
			user.setAreaList("1");
			user.setPassword("1111");
			user.setUState(1);
			renshiyewuMapper.insertUser(user);
		}else{
			dep.setDepartmentInnerId(department.get(0).getDepartmentInnerId());
			renshiyewuMapper.updateDepartment(dep);
			renshiyewuMapper.updateDepartmentUser(dep);
		}
		return dep;
	}
	
	private Department fourthDep(Map<String,Object> map,Department dep,LFDep lfDep,int upInnerId){
		//map.put("DepartmentNameEqu", lfDep.getFourth_deptname());
		map.put("DepartmentIdEqu", lfDep.getPk_fourtdept());
		List<Department> department = renshiyewuMapper.selectDepartment(map);
		dep.setDepartmentId(lfDep.getPk_fourtdept());
		dep.setDepartmentLevel(4);
		dep.setDepartmentName(lfDep.getFourth_deptname());
		dep.setUpInnerId(upInnerId);
		if(department.isEmpty()){
			try {
				renshiyewuMapper.insertDepartment(dep);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(dep);
				e.printStackTrace();
			}
			User user = new User();
			user.setUserId(dep.getDepartmentId());
			user.setUserName(dep.getDepartmentName());
			user.setUserTypeInnerId(-1);
			user.setDepartmentInnerId(dep.getDepartmentInnerId());
			user.setCompanyInnerId(dep.getCompanyInnerId());
			user.setStartTime("2019-08-01 00:00:00");
			user.setEndTime("2069-08-01 00:00:00");
			user.setAppList("1");
			user.setAreaList("1");
			user.setPassword("1111");
			user.setUState(1);
			renshiyewuMapper.insertUser(user);
		}else{
			dep.setDepartmentInnerId(department.get(0).getDepartmentInnerId());
			renshiyewuMapper.updateDepartment(dep);
			renshiyewuMapper.updateDepartmentUser(dep);
		}
		return dep;
	}
	
	@Scheduled(cron = "0 20 */5 * * ?")
	public void tongburenyuan(){
		LFUser a = new LFUser();
		try {
			System.out.println("人员同步****");
			List<LFUser> userList = tongbuMapper.selectUserInfo();
			LogInfo.logInfo("人员信息："+userList.toString());
			Map<String,Object> map = new HashMap<>();
			map.put("rows", 1000);
			map.put("order", "asc");
			map.put("sort", "UserInnerId");
			map.put("start", 0);
			for (LFUser lfUser : userList) {
				a=lfUser;
				map.put("UserId", lfUser.getPsncode());
				Department dep = renshiyewuMapper.selectDepartmentByDepartmentId(lfUser.getPk_deptdoc());
				if(dep == null){
					System.out.println(lfUser.getPk_deptdoc());
					continue ;
				}
				//map.put("DepartmentInnerId", dep.getDepartmentInnerId());
				List<User> selectUser = renshiyewuMapper.selectUser(map);
				User user = new User();
				user.setUserId(lfUser.getPsncode());
				user.setUserName(lfUser.getPsnname());
				user.setDepartmentInnerId(dep.getDepartmentInnerId());
				Company company = renshiyewuMapper.selectCompanyByStandByA(lfUser.getPk_corp());
				user.setCompanyInnerId(company.getCompanyInnerId());
				UserType userType = peizhiguanliMapper.selectUserTypeByName(lfUser.getPsnclassname());
				if(userType == null){
					userType = new UserType();
					userType.setUserTypeName(lfUser.getPsnclassname());
					userType.setUserTypeId(lfUser.getPsnclassname());
					userType.setAreaList("1");
					userType.setAppList("1");
					peizhiguanliMapper.insertUserType(userType);
				}
				user.setUserTypeInnerId(userType.getUserTypeInnerId());
				user.setAreaList("1");
				user.setAppList("1");
				user.setStartTime(lfUser.getJoinworkdate());
				user.setEndTime(lfUser.getIndutydate());
				if(lfUser.getId() != null){
					user.setIdType(0);
					user.setIdNumber(lfUser.getId());
				}
				if(lfUser.getSex() != null){
					if(lfUser.getSex().equals("男")){
						user.setSex(0);
					}else if(lfUser.getSex().equals("女")){
						user.setSex(1);
					}else{
						user.setSex(2);
					}
				}else{
					user.setSex(2);
				}
				user.setBirthday(lfUser.getBirthdate());
				user.setMobile(lfUser.getMobile());
				user.setEmail(lfUser.getEmail1());
				user.setEducation(lfUser.getEducation());
				user.setUState(0);
				if(selectUser.isEmpty()){
					renshiyewuMapper.insertUser(user);
				}else{
					user.setUserInnerId(selectUser.get(0).getUserInnerId());
					renshiyewuMapper.updateUser(user);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
