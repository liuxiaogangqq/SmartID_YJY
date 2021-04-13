package com.dhsr.smartid.renshiyewu.dao;

import java.util.List;
import java.util.Map;

import com.dhsr.smartid.peizhiguanli.domain.Operator;
import com.dhsr.smartid.renshiyewu.domain.Company;
import com.dhsr.smartid.renshiyewu.domain.Department;
import com.dhsr.smartid.renshiyewu.domain.Mark;
import com.dhsr.smartid.renshiyewu.domain.MarkLog;
import com.dhsr.smartid.renshiyewu.domain.Mark_User;
import com.dhsr.smartid.renshiyewu.domain.User;
import com.dhsr.smartid.renshiyewu.domain.UserPer;

/**
 * 人事业务的Dao层
 * 
 * @author qidong
 *
 */
public interface RenshiyewuMapper {

	// 根据主键查询公司
	public Company selectCompanyByInnerId(Integer CompanyInnerId);

	// 查询公司
	public List<Company> selectCompany(Map<String, Object> map);

	// 公司总数
	public Integer selectCompanyTotal(Map<String, Object> map);

	// 新增公司
	public Integer insertCompany(Company company);

	// 修改公司
	public Integer updateCompany(Company company);

	// 根据主键查询部门
	public Department selectDepartmentByInnerId(Integer DepartmentInnerId);

	// 查询部门
	public List<Department> selectDepartment(Map<String, Object> map);

	// 查询所有子部门
	public List<Integer> selectAllSonDepartment(Integer DepartmentInnerId);

	// 新增部门
	public Integer insertDepartment(Department department);

	// 修改部门
	public Integer updateDepartment(Department department);

	// 从太极同步修改部门
	public Integer updateDepartmentLDAP(Department department);

	// 通过主键查询人员
	public User selectUserByInnerId(Integer UserInnerId);

	// 查询人员
	public List<User> selectUser(Map<String, Object> map);

	// 查询未处理人员
	public List<User> selectUserUntreated();

	// 人员总数
	public Integer selectUserTotal(Map<String, Object> map);

	// 新增人员
	public Integer insertUser(User user);

	// 修改人员
	public Integer updateUser(User user);

	// 修改密码
	public Integer updatePWDUser(User user);

	// 修改头像
	public Integer updateImgUser(User user);

	// 通过主键查询标识
	public Mark selectMarkByInnerId(Integer MarkInnerId);

	// 查询标识
	public List<Mark_User> selectMark(Map<String, Object> map);

	// 标识总数
	public Integer selectMarkTotal(Map<String, Object> map);

	// 按人员查询标识
	public List<Integer> searchMarkByUser(Integer UserInnerId);

	// 查询一个人的最大标识
	public Integer selectUserMarkId(Integer UserInnerId);

	// 新增标识
	public Integer insertMark(Mark mark);

	//补卡
	Integer insertBuMark(Mark mark);
	
	// 修改标识
	public Integer updateMark(Mark mark);

	// 改变标识状态
	public Integer updateMarkState(Mark mark);

	// 新增人员权限
	public Integer insertUserPer(UserPer userPer);

	// 修改人员权限
	public Integer updateUserPer(UserPer userPer);

	// 查询人员权限
	public List<UserPer> selectUserPer(UserPer userPer);

	// 根据主键查询人员权限
	public UserPer selectUserPerByInnerId(Integer UserPerInnerId);

	// 删除人员权限
	public Integer deleteUserPer(Integer UserPerInnerId);

	// 查询当前最大人数
	public Integer selectuserMaxmum();

	//查询最大人数限制
	public Integer selectMaxUserNums();

	// 查询卡片操作日志
	public List<MarkLog> selectMarkLog(Map<String, Object> map);

	// 查询卡片操作总数
	public Integer selectMarkLogTotal(Map<String, Object> map);
	
	//根据用户编号修改图片
	public Integer updateImgUserByCode(Map<String, Object> map);

	// 更新管理员部门权限
	public Integer updateOperator(Map<String, Object> row);

	// 查询操作员
	public Operator selectOperatorByOperatorInnerId(Integer operatorInnerId);

	/**
	 * 根据操作员id查询操作员信息 
	 * @param userId
	 * @return
	 */
	public Operator selectOperatorByOperId(String userId);

	/**
	 * 根据部门编号查询部门信息
	 * @param pk_deptdoc
	 * @return
	 */
	public Department selectDepartmentByDepartmentId(String departmentId);

	/**
	 * 根据人事主键查询公司信息
	 * @param pk_corp
	 * @return
	 */
	public Company selectCompanyByStandByA(String pk_corp);

	/**
	 * 根据人员类型和部门查询人员信息
	 * @param userInnerId
	 * @return
	 */
	public User selectDepUserInfo(Integer userInnerId);

	/**
	 * 预约授权
	 * @param map
	 * @return
	 */
	public int updateUserShouquan(Map<String, Object> map);

	public List<User> selectUserNew(Map<String, Object> map);

	public void updateDepartmentUser(Department dep);

	public Integer selectCompanyPower(Map<String, Object> map);

	public Integer insertPayInfo(Map<String, Object> map);

	public Integer deletePayInfo(Map<String, Object> map);

	/**
	 * 查询公司下所有部门主键
	 * @param innerId
	 * @return
	 */
	public List<Integer> selectDepInnerIdByCompanyInnerId(Integer innerId);

	/**
	 * 查询部门存在可消费总数
	 * @param map
	 * @return
	 */
	public Integer selectDepConInfo(Map<String, Object> map);

	/**
	 * 查询公司可消费总数
	 * @param companyInnerId
	 * @return
	 */
	public Integer selectCompanyConPowerByInnerId(Integer companyInnerId);

	public Department selectAllDepartmentByInnerId(Integer valueOf);


}
