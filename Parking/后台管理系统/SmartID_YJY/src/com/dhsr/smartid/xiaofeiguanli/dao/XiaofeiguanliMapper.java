package com.dhsr.smartid.xiaofeiguanli.dao;

import java.util.List;
import java.util.Map;

import com.dhsr.smartid.xiaofeiguanli.domain.ConDiscount;
import com.dhsr.smartid.xiaofeiguanli.domain.ConLog;
import com.dhsr.smartid.xiaofeiguanli.domain.ConLog_User;
import com.dhsr.smartid.xiaofeiguanli.domain.ConOperatorPer;
import com.dhsr.smartid.xiaofeiguanli.domain.ConStatistics;
import com.dhsr.smartid.xiaofeiguanli.domain.ConTerminal;
import com.dhsr.smartid.xiaofeiguanli.domain.ConType;
import com.dhsr.smartid.xiaofeiguanli.domain.Con_OperatorPer;
import com.dhsr.smartid.xiaofeiguanli.domain.DiscountConsumption;
import com.dhsr.smartid.xiaofeiguanli.domain.InvoiceLog;
import com.dhsr.smartid.xiaofeiguanli.domain.MealPrice;
import com.dhsr.smartid.xiaofeiguanli.domain.MenuList;
import com.dhsr.smartid.xiaofeiguanli.domain.MenuStatistics;
import com.dhsr.smartid.xiaofeiguanli.domain.MenuType;
import com.dhsr.smartid.xiaofeiguanli.domain.Menus;
import com.dhsr.smartid.xiaofeiguanli.domain.Merchant;
import com.dhsr.smartid.xiaofeiguanli.domain.MerchantDiscount;
import com.dhsr.smartid.xiaofeiguanli.domain.TimeRule;

/**
 * 消费管理的Dao层
 * 
 * @author qidong
 *
 */
public interface XiaofeiguanliMapper {
	// 根据主键查询商户
	public Merchant selectMerchantByInnerId(Integer MerchantInnerId);

	// 查询商户
	public List<Merchant> selectMerchant(Map<String, Object> map);

	// 商户总数
	public Integer selectMerchantTotal(Map<String, Object> map);

	// 新增商户
	public Integer insertMerchant(Merchant merchant);

	// 修改商户
	public Integer updateMerchant(Merchant merchant);

	// 根据主键消费类型
	public ConType selectConTypeByInnerId(Integer ConTypeInnerId);

	// 查询消费类型
	public List<ConType> selectConType(Map<String, Object> map);

	// 消费类型总数
	public Integer selectConTypeTotal(Map<String, Object> map);

	// 新增消费类型
	public Integer insertConType(ConType conType);

	// 修改消费类型
	public Integer updateConType(ConType conType);

	// 根据主键查询消费设备
	public ConTerminal selectConTerminalByInnerId(Integer ConTerminalInnerId);

	// 查询消费设备
	public List<ConTerminal> selectConTerminal(Map<String, Object> map);

	// 消费设备总数
	public Integer selectConTerminalTotal(Map<String, Object> map);

	// 新增消费设备
	public Integer insertConTerminal(ConTerminal conTerminal);

	// 修改消费设备
	public Integer updateConTerminal(ConTerminal conTerminal);

	// 根据主键查询消费记录
	public ConLog selectConLogByInnerId(Map<String, Object> map);

	// 查询消费记录
	public List<ConLog_User> selectConLog(Map<String, Object> map);

	// 查询消费记录总数
	public Long selectConLogTotal(Map<String, Object> map);

	// 查询未处理的消费记录
	public List<ConLog> selectConLogUntreated();

	// 修改消费记录
	public Integer updateConLog(ConLog ConLog);

	// 新增消费记录
	public Integer insertConLog(ConLog conLog);

	// 消费统计
	public List<ConStatistics> selectConStatistics(Map<String, Object> map);

	// 查询本月消费
	public Integer selectConStatisticsMonth(Integer UserInnerId);

	// 查询操作员权限
	public List<Con_OperatorPer> selectConOperatorPer(Map<String, Object> map);

	// 查询操作员权限总数
	public Integer selectConOperatorPerTotal(Map<String, Object> map);

	// 新增操作员权限
	public Integer insertConOperatorPer(ConOperatorPer conOperatorPer);

	// 修改操作员权限
	public Integer updateConOperatorPer(ConOperatorPer conOperatorPer);

	// 根据主键查询操作员权限
	public Con_OperatorPer selectConOperatorPerByInnerId(Integer OperatorInnerId);

	// 通过主键查询商户优惠
	public MerchantDiscount selectMerchantDiscountByInnerId(Integer MerchantDiscountInnerId);

	// 查询商户优惠
	public List<MerchantDiscount> selectMerchantDiscount(Map<String, Object> map);

	// 查询未处理的场地商户优惠
	public List<MerchantDiscount> selectMerchantDiscountUntreated(String StandbyD);

	// 商户优惠总数
	public Integer selectMerchantDiscountTotal(Map<String, Object> map);

	// 新增商户优惠
	public Integer insertMerchantDiscount(MerchantDiscount MerchantDiscount);

	// 删除商户优惠
	public Integer deleteMerchantDiscount(MerchantDiscount MerchantDiscount);

	// 修改商户优惠
	public Integer updateMerchantDiscount(MerchantDiscount MerchantDiscount);

	// 查询积分优惠
	public List<ConDiscount> selectConDiscount(Map<String, Object> map);

	// 查询未处理的场地积分优惠
	public List<ConDiscount> selectConDiscountUntreated();

	// 积分优惠总数
	public Integer selectConDiscountTotal(Map<String, Object> map);

	// 新增积分优惠
	public Integer insertConDiscount(ConDiscount ConDiscount);

	// 修改积分优惠
	public Integer updateConDiscount(ConDiscount ConDiscount);

	// 删除积分优惠
	public Integer deleteConDiscount(ConDiscount ConDiscount);

	public DiscountConsumption selectDiscountConsumptionByUser(Map<String, Object> map);

	public Integer insertDiscountConsumption(DiscountConsumption DiscountConsumption);

	public Integer updateDiscountConsumption(DiscountConsumption DiscountConsumption);

	// 通过商户主键查询计时消费
	public List<TimeRule> selectTimeRuleByMerchantInnerId(Integer MerchantInnerId);

	// 查询计时消费
	public List<TimeRule> selectTimeRule(Map<String, Object> map);

	// 计时消费总数
	public Integer selectTimeRuleTotal(Map<String, Object> map);

	// 新增计时消费
	public Integer insertTimeRule(TimeRule TimeRule);

	// 修改计时消费
	public Integer updateTimeRule(TimeRule TimeRule);

	// 查询当前消费设备的数量
	public Integer selectConDeviceNums();

	// 查询消费设备最大数量
	public Integer selectMaxConDeviceNums();

	// 查询讯消费类型，下拉框显示
	public List<MenuType> selectMenuType(Map<String, Object> map);

	// 查询商户的有效菜单
	public List<MenuList> selectMenuList(Map<String, Object> map);

	// 查询菜单总数
	public Integer selectMenuListTotal(Map<String, Object> map);

	// 添加菜单
	public Integer insertMenuList(Map<String, Object> map);

	// 根据操作员查询商户权限
	public String selectConsumptionOper(Integer operatorInnerId);

	// 修改菜单
	public Integer updateMenuList(Map<String, Object> map);

	// 根据主键查询菜品类型
	public MenuType selectFMenuTypeNameByMenuTypeInnerId(Integer fMenuTypeInnerId);

	// 根据主键查询菜单
	public MenuList selectMenuListbyInnerId(Integer innerId);

	public List<MealPrice> selectMealPrice(Map<String, Object> map);

	public Object selectMealPriceTotal(Map<String, Object> map);

	public Integer verifyMealPrice(Map<String, Object> map);

	public Integer insertMealPrice(MealPrice mealPrice);

	public Integer updateMealPrice(MealPrice mealPrice);

	public Integer deleteMealPrice(MealPrice mealPrice);

	// 根据操作员查询商户权限
	public String selectMerchantByOperatorInnerId(Integer OperatorInnerId);

	// 查询菜品类型总数
	public Integer selectMenuTypeTotal(Map<String, Object> map);

	// 新增菜品类型
	public Integer insertMenuType(Map<String, Object> map);

	// 修改菜品类型方法
	public Integer updateMenuType(Map<String, Object> map);

	// 查询商户菜谱最大数量
	public Integer selectMenuTypeNumsByFMerchantInnerId(Integer FMerchantInnerId);

	public Integer selectPeopleTotal();

	public Integer selectMoneyTotal(Integer AccountTypeInnerId);

	public Integer selectTerminalByConTypeInnerId(Integer conTypeInnerId);

	/**
	 * 查询符合条件的菜品
	 * @param map
	 * @return
	 */
	public List<Menus> selectMenus(Map<String, Object> map);

	/**
	 * 根据商户名称查询商户信息
	 * @param map
	 * @return
	 */
	public Merchant selectMerchantByMerchantName(Map<String, Object> map);

	/**
	 * 查询是否存在菜类
	 * @param map
	 * @return
	 */
	public MenuType selectMenuTypeByMenuTypeName(Map<String, Object> map);

	/**
	 * 更新消费记录表
	 * @param map
	 */
	public Integer updateConLogforInvoiceLog(Map<String, Object> map);

	/**
	 * 新增开票记录
	 * @param invoiceLog
	 * @return
	 */
	public Integer insertInvoiceLog(InvoiceLog invoiceLog);

	/**
	 * 根据商户查询设备
	 * @param merchantList
	 * @return
	 */
	public List<Integer> selectTerminalByMerchantList(String merchantList);

	/**
	 * 统计菜品
	 * @param map
	 * @return
	 */
	public List<MenuStatistics> selectConMenuStatistics(Map<String, Object> map);

	/**
	 * 统计临时卡消费
	 * @param map
	 * @return
	 */
	public List<ConStatistics> selectLSConStatistics(Map<String, Object> map);

	/**
	 * 查询所有商户的优惠
	 * @return
	 */
	public List<MerchantDiscount> selectAllUseConDiscount();


}
