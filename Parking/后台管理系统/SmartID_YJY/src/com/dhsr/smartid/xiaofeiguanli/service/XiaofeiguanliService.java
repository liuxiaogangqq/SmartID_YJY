package com.dhsr.smartid.xiaofeiguanli.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.dhsr.smartid.util.DataGridModel;
import com.dhsr.smartid.xiaofeiguanli.domain.ConDiscount;
import com.dhsr.smartid.xiaofeiguanli.domain.ConOperatorPer;
import com.dhsr.smartid.xiaofeiguanli.domain.ConTerminal;
import com.dhsr.smartid.xiaofeiguanli.domain.ConType;
import com.dhsr.smartid.xiaofeiguanli.domain.Con_OperatorPer;
import com.dhsr.smartid.xiaofeiguanli.domain.MealPrice;
import com.dhsr.smartid.xiaofeiguanli.domain.Merchant;
import com.dhsr.smartid.xiaofeiguanli.domain.MerchantDiscount;
import com.dhsr.smartid.xiaofeiguanli.domain.TimeRule;

/**
 * 消费管理的Service层
 * 
 * @author qidong
 *
 */
public interface XiaofeiguanliService {
	// 查询商户
	public String selectMerchant(DataGridModel dgm, Merchant merchant);

	// 查询商户,显示在下拉框
	public String selectMerchantBox();

	// 新增商户
	public String insertMerchant(Merchant merchant, HttpServletRequest request);

	// 修改商户
	public String updateMerchant(Merchant merchant, HttpServletRequest request);

	// 查询消费类型
	public String selectConType(DataGridModel dgm, ConType conType);

	// 查询消费类型,显示在下拉框
	public String selectConTypeBox();

	// 新增消费类型
	public String insertConType(ConType conType, HttpServletRequest request);

	// 修改消费类型
	public String updateConType(ConType conType, HttpServletRequest request);

	// 查询消费设备
	public String selectConTerminal(DataGridModel dgm, ConTerminal conTerminal);

	// 查询消费设备,显示在下拉框
	public String selectConTerminalBox();

	// 新增消费设备
	public String insertConTerminal(ConTerminal conTerminal, HttpServletRequest request);

	// 修改消费设备
	public String updateConTerminal(ConTerminal conTerminal, HttpServletRequest request);

	// 查询商户应用方法，在组合树显示
	public String selectMerchantAppTree();

	// 查询设备商户方法，在组合树显示
	public String selectConTerminalMerchantTree();

	// 查询消费记录
	public String selectConLog(DataGridModel dgm, HttpServletRequest request);

	// 导出消费记录方法
	public String selectConLogExcel(HttpServletRequest request) throws Exception;

	// 新增消费记录
	public String insertConLog(HttpServletRequest request);

	// 消费统计方法
	public String selectConStatistics(HttpServletRequest request);

	// 导出消费统计方法
	public String selectConStatisticsExcel(HttpServletRequest request) throws Exception;

	// 查询操作员权限
	public String selectConOperatorPer(DataGridModel dgm, Con_OperatorPer con_OperatorPer);

	// 修改操作员权限
	public String updateConOperatorPer(ConOperatorPer conOperatorPer, HttpServletRequest request);

	// 查询部门消费
	// public String selectDepartmentCon(HttpServletRequest request);

	// 查询商户优惠
	public String selectMerchantDiscount(DataGridModel dgm, HttpServletRequest request);

	// 新增商户优惠
	public String insertMerchantDiscount(MerchantDiscount MerchantDiscount, HttpServletRequest request);

	// 删除商户优惠
	public String deleteMerchantDiscount(MerchantDiscount MerchantDiscount, HttpServletRequest request);

	// 修改商户优惠
	public String updateMerchantDiscount(MerchantDiscount MerchantDiscount, HttpServletRequest request);

	// 查询积分优惠
	public String selectConDiscount(DataGridModel dgm, HttpServletRequest request);

	// 新增积分优惠
	public String insertConDiscount(ConDiscount ConDiscount, HttpServletRequest request);

	// 删除积分优惠
	public String deleteConDiscount(ConDiscount ConDiscount, HttpServletRequest request);

	// 查询计时消费
	public String selectTimeRule(DataGridModel dgm, HttpServletRequest request);

	// 新增计时消费
	public String insertTimeRule(TimeRule TimeRule, HttpServletRequest request);

	// 修改计时消费
	public String updateTimeRule(TimeRule TimeRule, HttpServletRequest request);

	public String selectConDeviceNums(TimeRule timeRule, HttpServletRequest request);

	// 查询菜品类型，下拉框显示
	public String selectMenuTypeBox(HttpServletRequest request);

	// 查询菜单列表
	public String selectMenuList(HttpServletRequest request, DataGridModel dgm);

	// 添加菜单
	public String insertMenuList(HttpServletRequest request, DataGridModel dgm);

	// 更新菜单
	public String updateMenuList(HttpServletRequest request, DataGridModel dgm);

	// 查询餐次价格
	public String selectMealPrice(DataGridModel dgm, HttpServletRequest request);

	// 新增餐次价格
	public String insertMealPrice(MealPrice mealPrice, HttpServletRequest request);

	// 删除餐次价格
	public String deleteMealPrice(MealPrice mealPrice, HttpServletRequest request);

	// 更新餐次价格
	public String updateMealPrice(MealPrice mealPrice, HttpServletRequest request);

	// 查询菜类前台显示
	public String selectFMenuType(DataGridModel dgm, HttpServletRequest request);

	// 新增菜品类型
	public String insertMenuType(DataGridModel dgm, HttpServletRequest request);

	// 修改菜品类型方法
	public String updateMenuType(DataGridModel dgm, HttpServletRequest request);

	// 查询菜品类型
	public String menuTypeBox(HttpServletRequest request);

	// 查询设备对应的消费类型
	public String selectTerminalByContype(DataGridModel dgm, HttpServletRequest request);

	// 导入菜品校验显示
	public String showMenusExcel(HttpServletRequest request) throws IOException;

	/**
	 * 导入菜品
	 * @param request
	 * @return
	 */
	public String importMenusExcel(HttpServletRequest request) throws Exception;

	/**
	 * 查询开票记录
	 * @param request
	 * @param dgm
	 * @return
	 */
	public String selectInvoiceLog(HttpServletRequest request, DataGridModel dgm);

	/**
	 * 更新开票信息
	 * @param request
	 * @return
	 */
	public String updateInvoiceLog(HttpServletRequest request);

	/**
	 * 统计菜品消费
	 * @param request
	 * @return
	 */
	public String selectConMenu(HttpServletRequest request);

	/**
	 * 查询临时卡统计
	 * @param request
	 * @return
	 */
	public String selectLSConStatistics(HttpServletRequest request);


}
