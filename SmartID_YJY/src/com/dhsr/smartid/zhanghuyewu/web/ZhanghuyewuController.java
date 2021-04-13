package com.dhsr.smartid.zhanghuyewu.web;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dhsr.smartid.base.web.BaseController;
import com.dhsr.smartid.peizhiguanli.domain.Operator;
import com.dhsr.smartid.util.DataGridModel;
import com.dhsr.smartid.zhanghuyewu.domain.AccountDiscount;
import com.dhsr.smartid.zhanghuyewu.domain.AccountLog;
import com.dhsr.smartid.zhanghuyewu.service.ZhanghuyewuService;

/**
 * 账户业务的Web层
 * 
 * @author qidong
 *
 */
@Controller
public class ZhanghuyewuController extends BaseController {

	@Resource
	private ZhanghuyewuService zhanghuyewuService;

	private Boolean checkJurisdiction(Operator operator, String pageRightId) {
		Boolean result = false;
		String[] PageList = operator.getPageList().split(",");
		for (String Page : PageList) {
			if (Page.indexOf(pageRightId) == 0) {
				result = true;
				break;
			}
		}
		return result;
	}

	@RequestMapping(value = "/zhywmain", method = RequestMethod.GET)
	public String zhywmain(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "3");
			if (result) {
				return "/zhywzx/zhywmain";
			} else {
				model.put("noSystem", "您没有进入账户业务中心的权限");
				return "/index";
			}
		} else {
			model.put("backLogin", "login.html");
			return "/zhywzx/zhywmain";
		}
	}

	@RequestMapping(value = "/zhywright", method = RequestMethod.GET)
	public String zhywright() {
		return "/zhywzx/zhywright";
	}

	@RequestMapping(value = "/zhywsidebar", method = RequestMethod.GET)
	public String zhywsidebar() {
		return "/zhywzx/zhywsidebar";
	}

	@RequestMapping(value = "/zhywtop", method = RequestMethod.GET)
	public String zhywtop() {
		return "/zhywzx/zhywtop";
	}

	// 读卡充/减值页面
	@RequestMapping(value = "/readCardRecharge", method = RequestMethod.GET)
	public String readCardRecharge(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "301");
			if (result) {
				return "/zhywzx/readCardRecharge";
			} else {
				model.put("noPage", "您没有进入读卡充/减值的权限！");
				return "/zhywzx/zhywmain";
			}
		} else {
			return "redirect:zhywmain.html";
		}
	}

	// 查询充/减值页面
	@RequestMapping(value = "/queryRecharge", method = RequestMethod.GET)
	public String queryRecharge(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "302");
			if (result) {
				return "/zhywzx/queryRecharge";
			} else {
				model.put("noPage", "您没有进入账户充/减值的权限！");
				return "/zhywzx/zhywmain";
			}
		} else {
			return "redirect:zhywmain.html";
		}
	}
	
	// 查询充/减值页面
	@RequestMapping(value = "/DepRecharge", method = RequestMethod.GET)
	public String DepRecharge(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "302");
			if (result) {
				return "/zhywzx/DepRecharge";
			} else {
				model.put("noPage", "您没有进入部门充减值的权限！");
				return "/zhywzx/zhywmain";
			}
		} else {
			return "redirect:zhywmain.html";
		}
	}

	// 充/减值查询页面
	@RequestMapping(value = "/accountLog", method = RequestMethod.GET)
	public String accountLog(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "303");
			if (result) {
				return "/zhywzx/accountLog";
			} else {
				model.put("noPage", "您没有进入充/减值查询的权限！");
				return "/zhywzx/zhywmain";
			}
		} else {
			return "redirect:zhywmain.html";
		}
	}

	// 充/减值统计页面
	@RequestMapping(value = "/accountStatistics", method = RequestMethod.GET)
	public String accountStatistics(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "304");
			if (result) {
				return "/zhywzx/accountStatistics";
			} else {
				model.put("noPage", "您没有进入充/减值统计的权限！");
				return "/zhywzx/zhywmain";
			}
		} else {
			return "redirect:zhywmain.html";
		}
	}

	// 补助导入页面
	@RequestMapping(value = "/importAllowance", method = RequestMethod.GET)
	public String importAllowance(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "305");
			if (result) {
				return "/zhywzx/importAllowance";
			} else {
				model.put("noPage", "您没有进入补贴导入的权限！");
				return "/zhywzx/zhywmain";
			}
		} else {
			return "redirect:zhywmain.html";
		}
	}
	
	// 补助导入页面
	@RequestMapping(value = "/importNumber", method = RequestMethod.GET)
	public String importNumber(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "308");
			if (result) {
				return "/zhywzx/importNumber";
			} else {
				model.put("noPage", "您没有进入次充值导入的权限！");
				return "/zhywzx/zhywmain";
			}
		} else {
			return "redirect:zhywmain.html";
		}
	}

	// 离职退款页面
	@RequestMapping(value = "/dimission", method = RequestMethod.GET)
	public String dimission(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "306");
			if (result) {
				return "/zhywzx/dimission";
			} else {
				model.put("noPage", "您没有进入离职退款的权限！");
				return "/zhywzx/zhywmain";
			}
		} else {
			return "redirect:zhywmain.html";
		}
	}

	// 充值优惠页面
	@RequestMapping(value = "/accountDiscount", method = RequestMethod.GET)
	public String accountDiscount(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "307");
			if (result) {
				return "/zhywzx/accountDiscount";
			} else {
				model.put("noPage", "您没有进入充值优惠的权限！");
				return "/zhywzx/zhywmain";
			}
		} else {
			return "redirect:zhywmain.html";
		}
	}
	
	// 充值优惠页面
	@RequestMapping(value = "/Settlement", method = RequestMethod.GET)
	public String Settlement(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "309");
			if (result) {
				return "/zhywzx/Settlement";
			} else {
				model.put("noPage", "您没有进入日结信息的权限！");
				return "/zhywzx/zhywmain";
			}
		} else {
			return "redirect:zhywmain.html";
		}
	}
	// 财务商户结算页面
	@RequestMapping(value = "/payment", method = RequestMethod.GET)
	public String payment(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "310");
			if (result) {
				return "/zhywzx/payment";
			} else {
				model.put("noPage", "您没有进入财务/商户结算的权限！");
				return "/zhywzx/zhywmain";
			}
		} else {
			return "redirect:zhywmain.html";
		}
	}
	
	// 临时卡充值统计界面
	@RequestMapping(value = "/lsAccountStatistics", method = RequestMethod.GET)
	public String lsAccountStatistics(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "311");
			if (result) {
				return "/zhywzx/lsAccountStatistics";
			} else {
				model.put("noPage", "您没有进入临时卡充值统计的权限！");
				return "/zhywzx/zhywmain";
			}
		} else {
			return "redirect:zhywmain.html";
		}
	}
	
	// 临时卡充值查询界面
	@RequestMapping(value = "/lsAccountLog", method = RequestMethod.GET)
	public String lsAccountLog(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "312");
			if (result) {
				return "/zhywzx/lsAccountLog";
			} else {
				model.put("noPage", "您没有进入临时卡充值查询的权限！");
				return "/zhywzx/zhywmain";
			}
		} else {
			return "redirect:zhywmain.html";
		}
	}

	// 读卡
	@RequestMapping(value = "/readCard", method = RequestMethod.POST)
	public void readCard(HttpServletRequest request, HttpServletResponse response, String MarkId) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = zhanghuyewuService.readCard(MarkId,request);
		renderText(response, result);
	}

	// 添加充值记录
	@RequestMapping(value = "/accountLog/insert", method = RequestMethod.POST)
	public void insertAccountLog(HttpServletRequest request, HttpServletResponse response, AccountLog accountLog) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = zhanghuyewuService.insertAccountLog(accountLog, request);
		renderText(response, result);
	}

	// 添加充值记录1
	@RequestMapping(value = "/accountLog1/insert", method = RequestMethod.POST)
	public void insertAccountLog1(HttpServletRequest request, HttpServletResponse response, AccountLog accountLog, Integer AccountInnerId1) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = zhanghuyewuService.insertAccountLog1(accountLog, request, AccountInnerId1);
		renderText(response, result);
	}

	// 离职退款
	@RequestMapping(value = "/dimission/insert", method = RequestMethod.POST)
	public void insertDimission(HttpServletRequest request, HttpServletResponse response, AccountLog accountLog) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = zhanghuyewuService.insertDimission(request);
		renderText(response, result);
	}

	// 查询充值记录方法
	@RequestMapping(value = "/accountLog/select", method = RequestMethod.POST)
	public void selectAccountLog(HttpServletRequest request, HttpServletResponse response, DataGridModel dgm, ModelMap model) throws IOException {
		response.setCharacterEncoding("UTF-8");
		String result = zhanghuyewuService.selectAccountLog(dgm, request);
		renderText(response, result);
	}
	

	// 导出充值记录方法
	@RequestMapping(value = "/accountLogExcel/select", method = RequestMethod.POST)
	public void selectAccountLogExcel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		response.setCharacterEncoding("UTF-8");
		String result = zhanghuyewuService.selectAccountLogExcel(request);
		renderText(response, result);
	}

	// 查询充值记录方法
	@RequestMapping(value = "/lsAccountLog/select", method = RequestMethod.POST)
	public void selectlsAccountLog(HttpServletRequest request, HttpServletResponse response, DataGridModel dgm, ModelMap model) throws IOException {
		response.setCharacterEncoding("UTF-8");
		String result = zhanghuyewuService.selectlsAccountLog(dgm, request);
		renderText(response, result);
	}
	
	// 导出充值记录方法
	@RequestMapping(value = "/lsAccountLogExcel/select", method = RequestMethod.POST)
	public void selectlsAccountLogExcel(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		String result = zhanghuyewuService.selectlsAccountLogExcel(request);
		renderText(response, result);
	}

	// 统计充值记录方法
	@RequestMapping(value = "/accountStatistics/select", method = RequestMethod.POST)
	public void selectAccountStatistics(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		response.setCharacterEncoding("UTF-8");
		String result = zhanghuyewuService.selectAccountStatistics(request);
		renderText(response, result);
	}
	
	// 统计充值记录方法
	@RequestMapping(value = "/lsAccountStatistics/select", method = RequestMethod.POST)
	public void selectLSAccountStatistics(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		response.setCharacterEncoding("UTF-8");
		String result = zhanghuyewuService.selectLSAccountStatistics(request);
		renderText(response, result);
	}

	// 导出统计充值方法
	@RequestMapping(value = "/accountStatisticsExcel/select", method = RequestMethod.POST)
	public void selectAccountStatisticsExcel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		response.setCharacterEncoding("UTF-8");
		String result = zhanghuyewuService.selectAccountStatisticsExcel(request);
		renderText(response, result);
	}

	// 补贴导入验证
	@RequestMapping(value = "/showAllowanceExcel.html", method = RequestMethod.POST)
	public void showAllowanceExcel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		response.setCharacterEncoding("UTF-8");
		String result = zhanghuyewuService.showAllowanceExcel(request);
		renderText(response, result);
	}
	// 次导入验证
	@RequestMapping(value = "/showNumberExcel.html", method = RequestMethod.POST)
	public void showNumberExcel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		response.setCharacterEncoding("UTF-8");
		String result = zhanghuyewuService.showNumberExcel(request);
		renderText(response, result);
	}

	// 补贴导入
	@RequestMapping(value = "/importAllowanceExcel.html", method = RequestMethod.POST)
	public void importAllowanceExcel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		response.setCharacterEncoding("UTF-8");
		String result = zhanghuyewuService.importAllowanceExcel(request);
		renderText(response, result);
	}
	
	// 次数导入
	@RequestMapping(value = "/importNumberExcel.html", method = RequestMethod.POST)
	public void importNumberExcel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		response.setCharacterEncoding("UTF-8");
		String result = zhanghuyewuService.importNumberExcel(request);
		renderText(response, result);
	}

	// 补贴减值导入
	@RequestMapping(value = "/importAllowanceExcelAubtract.html", method = RequestMethod.POST)
	public void importAllowanceExcelAubtract(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		response.setCharacterEncoding("UTF-8");
		String result = zhanghuyewuService.importAllowanceExcelAubtract(request);
		renderText(response, result);
	}
	
	// 次数减值导入
	@RequestMapping(value = "/importNumberExcelAubtract.html", method = RequestMethod.POST)
	public void importNumberExcelAubtract(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		response.setCharacterEncoding("UTF-8");
		String result = zhanghuyewuService.importNumberExcelAubtract(request);
		renderText(response, result);
	}

	// 查询操作人方法，在下拉框显示
	@RequestMapping(value = "/operatorAll/select", method = RequestMethod.POST)
	public void selectOperatorAll(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = zhanghuyewuService.selectOperatorAll();
		renderText(response, result);
	}

	// 查询收款账户方法，在下拉框显示
	@RequestMapping(value = "/payeeAll/select", method = RequestMethod.POST)
	public void selectPayeeAll(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = zhanghuyewuService.selectPayeeAll();
		renderText(response, result);
	}

	// 查询充值优惠方法，在前台显示
	@RequestMapping(value = "/AccountDiscount/select", method = RequestMethod.POST)
	public void selectAccountDiscount(HttpServletRequest request, HttpServletResponse response, DataGridModel dgm, ModelMap modelMap) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = zhanghuyewuService.selectAccountDiscount(dgm, request);
		renderText(response, result);
	}

	// 新增充值优惠方法
	@RequestMapping(value = "/AccountDiscount/insert", method = RequestMethod.POST)
	public void insertAccountDiscount(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, AccountDiscount AccountDiscount) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = zhanghuyewuService.insertAccountDiscount(AccountDiscount, request);
		renderText(response, result);
	}

	// 删除充值优惠方法
	@RequestMapping(value = "/AccountDiscount/delete", method = RequestMethod.POST)
	public void deleteAccountDiscount(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, AccountDiscount AccountDiscount) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = zhanghuyewuService.deleteAccountDiscount(AccountDiscount, request);
		renderText(response, result);
	}

	// 修改充值优惠方法
	@RequestMapping(value = "/AccountDiscount/update", method = RequestMethod.POST)
	public void updateAccountDiscount(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, AccountDiscount AccountDiscount) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = zhanghuyewuService.updateAccountDiscount(AccountDiscount, request);
		renderText(response, result);
	}
	
	// 修改充值优惠方法
	@RequestMapping(value = "/Settlement/select", method = RequestMethod.POST)
	public void selectSettlement(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, AccountDiscount AccountDiscount) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = zhanghuyewuService.selectSettlement(AccountDiscount, request);
		renderText(response, result);
	}
	
	// 查询财务商户结算记录
	@RequestMapping(value = "/paymentLog/select", method = RequestMethod.POST)
	public void selectPayment(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = zhanghuyewuService.selectPayment(request);
		renderText(response, result);
	}
	
	// 查询财务商户结算记录表头
	@RequestMapping(value = "/paymentTree/select", method = RequestMethod.POST)
	public void selectPaymentTree(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = zhanghuyewuService.selectPaymentTree(request);
		renderText(response, result);
	}
	
	// 更新财务商户结算记录付款结果
	@RequestMapping(value = "/fukuanPayment/update", method = RequestMethod.POST)
	public void updatePayment(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = zhanghuyewuService.updatePayment(request);
		renderText(response, result);
	}
	
	// 更新财务商户结算记录付款结果
	@RequestMapping(value = "/Depuser/select", method = RequestMethod.POST)
	public void selectDepUser(HttpServletRequest request, HttpServletResponse response,DataGridModel dgm) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = zhanghuyewuService.selectDepUser(request,dgm);
		renderText(response, result);
	}

}
