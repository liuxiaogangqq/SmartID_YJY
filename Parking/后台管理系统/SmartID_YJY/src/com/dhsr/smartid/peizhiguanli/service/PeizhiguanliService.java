package com.dhsr.smartid.peizhiguanli.service;

import javax.servlet.http.HttpServletRequest;

import com.dhsr.smartid.peizhiguanli.domain.App;
import com.dhsr.smartid.peizhiguanli.domain.Area;
import com.dhsr.smartid.peizhiguanli.domain.Configure;
import com.dhsr.smartid.peizhiguanli.domain.Operator;
import com.dhsr.smartid.peizhiguanli.domain.SysTerminal;
import com.dhsr.smartid.peizhiguanli.domain.SystemConfigure;
import com.dhsr.smartid.peizhiguanli.domain.UserType;
import com.dhsr.smartid.util.DataGridModel;

/**
 * 配置管理的Service层
 * 
 * @author qidong
 *
 */
public interface PeizhiguanliService {
	// 查询页面配置
	public String selectConfigure(String Page);

	// 修改页面配置
	public String updateConfigure(Configure configure);

	// 查询系统配置
	public String selectSystemConfigure();

	// 根据编号查询系统配置
	public String selectSystemConfigureById(String ConfigureId);

	// 修改系统配置
	public String updateSystemConfigure(SystemConfigure systemConfigure);

	// 查询区域
	public String selectArea(DataGridModel dgm, Area area);

	// 查询区域,显示在下拉框
	public String selectAreaBox();

	// 新增区域
	public String insertArea(Area area, HttpServletRequest request);

	// 修改区域
	public String updateArea(Area area, HttpServletRequest request);

	// 查询应用
	public String selectApp(DataGridModel dgm, App app);

	// 查询应用,显示在下拉框
	public String selectAppBox(Integer AppTypeInnerId);

	// 新增应用
	public String insertApp(App app, HttpServletRequest request);

	// 修改应用
	public String updateApp(App app, HttpServletRequest request);

	// 查询应用类型,显示在下拉框
	public String selectAppTypeBox();

	// 查询标识类型,显示在下拉框
	public String selectMarkTypeBox();

	// 查询终端类型,显示在下拉框
	public String selectTerminalTypeBox();

	// 查询操作员
	public String selectOperator(DataGridModel dgm, Operator operator, HttpServletRequest request);

	// 查询当前操作员
	public String selectNowOperator(HttpServletRequest request);

	// 查询操作员,显示在下拉框
	public String selectOperatorBox();

	// 新增操作员
	public String insertOperator(Operator operator, HttpServletRequest request);

	// 修改操作员
	public String updateOperator(Operator operator, HttpServletRequest request);

	// 修改操作员密码
	public String updatePWDOperator(HttpServletRequest request);

	// 查询区域应用方法，在组合树显示
	public String selectAreaAppTree(Integer AppTypeInnerId);

	// 查询人员类型
	public String selectUserType(DataGridModel dgm, UserType userType);

	// 查询人员类型,显示在下拉框
	public String selectUserTypeBox();

	// 新增人员类型
	public String insertUserType(UserType userType, HttpServletRequest request);

	// 修改人员类型
	public String updateUserType(UserType userType, HttpServletRequest request);

	// 查询页面权限树方法
	public String pageRightTree();

	// 查询设备
	public String selectSysTerminal(DataGridModel dgm, SysTerminal sysTerminal);

	// 查询设备,显示在下拉框
	public String selectSysTerminalBox();

	// 新增设备
	public String insertSysTerminal(SysTerminal sysTerminal, HttpServletRequest request);

	// 修改设备
	public String updateSysTerminal(SysTerminal sysTerminal, HttpServletRequest request);
}
