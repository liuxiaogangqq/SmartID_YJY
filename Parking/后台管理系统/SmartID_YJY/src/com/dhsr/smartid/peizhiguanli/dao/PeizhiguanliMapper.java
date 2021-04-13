package com.dhsr.smartid.peizhiguanli.dao;

import java.util.List;
import java.util.Map;

import com.dhsr.smartid.peizhiguanli.domain.App;
import com.dhsr.smartid.peizhiguanli.domain.AppType;
import com.dhsr.smartid.peizhiguanli.domain.Area;
import com.dhsr.smartid.peizhiguanli.domain.Configure;
import com.dhsr.smartid.peizhiguanli.domain.MarkType;
import com.dhsr.smartid.peizhiguanli.domain.Operator;
import com.dhsr.smartid.peizhiguanli.domain.OperatorLog;
import com.dhsr.smartid.peizhiguanli.domain.PageRight;
import com.dhsr.smartid.peizhiguanli.domain.SysTerminal;
import com.dhsr.smartid.peizhiguanli.domain.SystemConfigure;
import com.dhsr.smartid.peizhiguanli.domain.SystemLog;
import com.dhsr.smartid.peizhiguanli.domain.TerminalType;
import com.dhsr.smartid.peizhiguanli.domain.UserType;

/**
 * 配置管理的Dao层
 * 
 * @author qidong
 *
 */
public interface PeizhiguanliMapper {
	// 查询页面配置
	public List<Configure> selectConfigure(String Page);

	// 修改页面配置
	public Integer updateConfigure(Configure configure);

	// 查询系统配置
	public List<SystemConfigure> selectSystemConfigure();

	// 根据编号查询系统配置
	public SystemConfigure selectSystemConfigureById(String ConfigureId);

	// 修改系统配置
	public Integer updateSystemConfigure(SystemConfigure systemConfigure);

	// 根据主键查询区域
	public Area selectAreaByInnerId(Integer AreaInnerId);

	// 查询区域
	public List<Area> selectArea(Map<String, Object> map);

	// 区域总数
	public Integer selectAreaTotal(Map<String, Object> map);

	// 新增区域
	public Integer insertArea(Area area);

	// 修改区域
	public Integer updateArea(Area area);

	// 根据主键查询应用类型
	public AppType selectAppTypeByInnerId(Integer AppTypeInnerId);

	// 查询应用类型
	public List<AppType> selectAppType(Map<String, Object> map);

	// 根据主键查询识类型
	public MarkType selectMarkTypeByInnerId(Integer MarkTypeInnerId);

	// 查询标识类型
	public List<MarkType> selectMarkType(Map<String, Object> map);

	// 根据主键查询终端类型
	public TerminalType selectTerminalTypeByInnerId(Integer TerminalTypeInnerId);

	// 查询终端类型
	public List<TerminalType> selectTerminalType(Map<String, Object> map);

	// 通过主键查询应用
	public App selectAppByInnerId(Integer AppInnerId);

	// 查询应用
	public List<App> selectApp(Map<String, Object> map);

	// 应用总数
	public Integer selectAppTotal(Map<String, Object> map);

	// 新增应用
	public Integer insertApp(App app);

	// 修改应用
	public Integer updateApp(App app);

	// 根据用户名密码查询操作员
	public Operator selectOperatorByIdAndPwd(Operator operator);

	// 根据主键查询操作员
	public Operator selectOperatorByInnerId(Integer OperatorInnerId);

	// 查询操作员
	public List<Operator> selectOperator(Map<String, Object> map);

	// 应用操作员
	public Integer selectOperatorTotal(Map<String, Object> map);

	// 新增操作员
	public Integer insertOperator(Operator operator);

	// 修改操作员
	public Integer updateOperator(Operator operator);

	// 修改操作员
	public Integer updatePWDOperator(Operator operator);

	// 根据主键查询人员类型
	public UserType selectUserTypeByInnerId(Integer UserTypeInnerId);

	// 查询人员类型
	public List<UserType> selectUserType(Map<String, Object> map);

	// 人员类型总数
	public Integer selectUserTypeTotal(Map<String, Object> map);

	// 新增人员类型
	public Integer insertUserType(UserType userType);

	// 修改人员类型
	public Integer updateUserType(UserType userType);

	// 查询页面权限
	public List<PageRight> selectPageRight(PageRight pageRight);

	// 新增操作员登录记录
	public Integer insertOperatorLog(OperatorLog operatorLog);

	// 新增平台日志记录
	public Integer insertSystemLog(SystemLog systemLog);

	// 根据主键查询平台设备
	public SysTerminal selectSysTerminalByInnerId(Integer SysTerminalInnerId);

	// 查询平台设备
	public List<SysTerminal> selectSysTerminal(Map<String, Object> map);

	// 消费平台总数
	public Integer selectSysTerminalTotal(Map<String, Object> map);

	// 新增平台设备
	public Integer insertSysTerminal(SysTerminal sysTerminal);

	// 修改平台设备
	public Integer updateSysTerminal(SysTerminal sysTerminal);

	// 根据操作员id查询商户权限
	public String selectMerchantListbyOperatorInnerId(Integer operatorInnerId);

	// 查询页面名称
	public String selectPageRightByPageRightId(Integer pageRightId);

	// 根据主键查询部门名称
	public String selectDepartmentNameByDepartmentInnerId(Integer DepartmentInnerId);

	// 更新管理员商户权限
	//public Integer updateConOperatorPer(ConOperatorPer conOperatorPer);

	public List<App> selectAppAll();

	/**
	 * 根据名称查询人员类型
	 * @param psnclassname
	 * @return
	 */
	public UserType selectUserTypeByName(String psnclassname);
}
