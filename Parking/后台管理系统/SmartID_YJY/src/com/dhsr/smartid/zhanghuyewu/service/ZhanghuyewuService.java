package com.dhsr.smartid.zhanghuyewu.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.dhsr.smartid.util.DataGridModel;
import com.dhsr.smartid.zhanghuyewu.domain.AccountDiscount;
import com.dhsr.smartid.zhanghuyewu.domain.AccountLog;

/**
 * 账户业务的Service层
 * 
 * @author qidong
 *
 */
public interface ZhanghuyewuService {

	// 读卡
	public String readCard(String MarkId,HttpServletRequest request);

	// 添加充值记录
	public String insertAccountLog(AccountLog accountLog, HttpServletRequest request);

	// 添加充值记录
	public String insertAccountLog1(AccountLog accountLog, HttpServletRequest request, Integer AccountInnerId1);

	// 离职退款
	public String insertDimission(HttpServletRequest request);

	// 查询充值记录
	public String selectAccountLog(DataGridModel dgm, HttpServletRequest request);

	// 导出充值记录方法
	public String selectAccountLogExcel(HttpServletRequest request) throws Exception;

	// 统计充值记录
	public String selectAccountStatistics(HttpServletRequest request);

	// 导出统计充值
	public String selectAccountStatisticsExcel(HttpServletRequest request) throws Exception;

	// 补贴导入显示
	public String showAllowanceExcel(HttpServletRequest request) throws IOException;

	// 补贴导入
	public String importAllowanceExcel(HttpServletRequest request) throws Exception;

	// 补贴减值导入
	public String importAllowanceExcelAubtract(HttpServletRequest request) throws Exception;

	// 查询操作人方法
	public String selectOperatorAll();

	// 查询收款账户方法
	public String selectPayeeAll();

	// 查询充值优惠
	public String selectAccountDiscount(DataGridModel dgm, HttpServletRequest request);

	// 新增充值优惠
	public String insertAccountDiscount(AccountDiscount AccountDiscount, HttpServletRequest request);

	// 删除充值优惠
	public String deleteAccountDiscount(AccountDiscount AccountDiscount, HttpServletRequest request);

	// 修改充值优惠
	public String updateAccountDiscount(AccountDiscount AccountDiscount, HttpServletRequest request);

	// 次导入验证
	public String showNumberExcel(HttpServletRequest request)throws Exception;

	public String importNumberExcel(HttpServletRequest request) throws Exception;

	public String importNumberExcelAubtract(HttpServletRequest request) throws Exception;

	// 查询汇总账务
	public String selectSettlement(AccountDiscount accountDiscount, HttpServletRequest request);

	/**
	 * 查询财务商户结算记录
	 * @param request
	 * @return
	 */
	public String selectPayment(HttpServletRequest request);

	public String selectPaymentTree(HttpServletRequest request);

	/**
	 * 更新付款状态
	 * @param request
	 * @return
	 */
	public String updatePayment(HttpServletRequest request);

	/**
	 * 统计临时卡充值金额
	 * @param request
	 * @return
	 */
	public String selectLSAccountStatistics(HttpServletRequest request);

	/**
	 * 临时卡 充值查询
	 * @param dgm
	 * @param request
	 * @return
	 */
	public String selectlsAccountLog(DataGridModel dgm, HttpServletRequest request);

	/**
	 * 导出临时卡充减值记录 
	 * @param request
	 * @return
	 */
	public String selectlsAccountLogExcel(HttpServletRequest request) throws Exception ;

	/**
	 * 查询部门账户
	 * @param request
	 * @param dgm
	 * @return
	 */
	public String selectDepUser(HttpServletRequest request, DataGridModel dgm);

}
