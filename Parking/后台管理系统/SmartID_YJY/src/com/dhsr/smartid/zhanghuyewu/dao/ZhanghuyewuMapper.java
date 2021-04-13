package com.dhsr.smartid.zhanghuyewu.dao;

import java.util.List;
import java.util.Map;

import com.dhsr.smartid.renshiyewu.domain.User;
import com.dhsr.smartid.zhanghuyewu.domain.Account;
import com.dhsr.smartid.zhanghuyewu.domain.AccountDiscount;
import com.dhsr.smartid.zhanghuyewu.domain.AccountLog;
import com.dhsr.smartid.zhanghuyewu.domain.AccountLog_User;
import com.dhsr.smartid.zhanghuyewu.domain.AccountStatistics;
import com.dhsr.smartid.zhanghuyewu.domain.DiscountAccount;
import com.dhsr.smartid.zhanghuyewu.domain.Payment;
import com.dhsr.smartid.zhanghuyewu.domain.Settlement;

/**
 * 账户业务的Dao层
 * 
 * @author qidong
 *
 */
public interface ZhanghuyewuMapper {

	// 查询账户
	public Account selectAccount(Map<String, Object> map);

	// 添加充值记录
	public Integer insertAccountLog(AccountLog accountLog);

	// 通过主键查询充值记录
	public AccountLog_User selectAccountLog_UserByInnerId(Integer AccountLogInnerId);

	// 查询充值记录
	public List<AccountLog_User> selectAccountLog(Map<String, Object> map);

	// 充值记录总数
	public Integer selectAccountLogTotal(Map<String, Object> map);

	// 充值统计
	public List<AccountStatistics> selectAccountStatistics(Map<String, Object> map);

	// 通过主键查询充值优惠
	public AccountDiscount selectAccountDiscountByInnerId(Integer AccountDiscountInnerId);

	// 查询充值优惠
	public List<AccountDiscount> selectAccountDiscount(Map<String, Object> map);

	// 查询未处理的充值优惠
	public List<AccountDiscount> selectAccountDiscountUntreated(String StandbyD);

	// 充值优惠总数
	public Integer selectAccountDiscountTotal(Map<String, Object> map);

	// 新增充值优惠
	public Integer insertAccountDiscount(AccountDiscount AccountDiscount);

	// 删除充值优惠
	public Integer deleteAccountDiscount(AccountDiscount AccountDiscount);

	// 修改充值优惠
	public Integer updateAccountDiscount(AccountDiscount AccountDiscount);

	// 获取人员优惠
	public Integer selectUserTypeDiscount(Integer UserTypeInnerId);

	public DiscountAccount selectDiscountAccountByUserType(Integer UserTypeInnerId);

	public List<DiscountAccount> selectDiscountAccount();

	public Integer insertDiscountAccount(DiscountAccount DiscountAccount);

	public Integer updateDiscountAccount(DiscountAccount DiscountAccount);
	
	Integer selectAccount4(Integer UserInnerId);

	// 查询结算结果
	public List<Settlement> selectSettlement(Map<String, Object> map);

	/**
	 * 查询财务商户结算记录
	 * @param map
	 * @return
	 */
	public List<Payment> selectPayment(Map<String, Object> map);

	/**
	 * 更新财务付款状态
	 * @param map
	 * @return
	 */
	public int updatePayment(Map<String, Object> map);

	public int updateAccount(Map<String, Object> map1);

	public List<AccountStatistics> selectLSAccountStatistics(Map<String, Object> map);

	/**
	 * 查询临时人员的充减值记录情况
	 * @param map
	 * @return
	 */
	public List<AccountLog_User> selectlsAccountLog(Map<String, Object> map);

	/**
	 * 查询临时卡人员充减值记录总数
	 * @param map
	 * @return
	 */
	public Integer selectlsAccountLogTotal(Map<String, Object> map);

	public Integer selectAccountLogByAccountLog(AccountLog accountLog);

	/**
	 * 查询部门账户
	 */
	public List<User> selectUser(Map<String, Object> map);

	public Object selectUserTotal(Map<String, Object> map);

}
