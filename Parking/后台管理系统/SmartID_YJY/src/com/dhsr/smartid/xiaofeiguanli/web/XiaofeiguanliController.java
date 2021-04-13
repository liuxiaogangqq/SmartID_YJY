package com.dhsr.smartid.xiaofeiguanli.web;

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
import com.dhsr.smartid.xiaofeiguanli.domain.ConDiscount;
import com.dhsr.smartid.xiaofeiguanli.domain.ConOperatorPer;
import com.dhsr.smartid.xiaofeiguanli.domain.ConTerminal;
import com.dhsr.smartid.xiaofeiguanli.domain.ConType;
import com.dhsr.smartid.xiaofeiguanli.domain.Con_OperatorPer;
import com.dhsr.smartid.xiaofeiguanli.domain.MealPrice;
import com.dhsr.smartid.xiaofeiguanli.domain.Merchant;
import com.dhsr.smartid.xiaofeiguanli.domain.MerchantDiscount;
import com.dhsr.smartid.xiaofeiguanli.domain.TimeRule;
import com.dhsr.smartid.xiaofeiguanli.service.XiaofeiguanliService;

/**
 * 消费管理的Web层
 * 
 * @author qidong
 *
 */
@Controller
public class XiaofeiguanliController extends BaseController {

	@Resource
	private XiaofeiguanliService xiaofeiguanliService;

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

	@RequestMapping(value = "/xfglmain", method = RequestMethod.GET)
	public String xfglmain(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "5");
			if (result) {
				return "/xfglxt/xfglmain";
			} else {
				model.put("noSystem", "您没有进入消费管理系统的权限");
				return "/index";
			}
		} else {
			model.put("backLogin", "login.html");
			return "/xfglxt/xfglmain";
		}
	}

	@RequestMapping(value = "/xfglright", method = RequestMethod.GET)
	public String xfglright() {
		return "/xfglxt/xfglright";
	}

	@RequestMapping(value = "/xfglsidebar", method = RequestMethod.GET)
	public String xfglsidebar() {
		return "/xfglxt/xfglsidebar";
	}

	@RequestMapping(value = "/xfgltop", method = RequestMethod.GET)
	public String xfgltop() {
		return "/xfglxt/xfgltop";
	}

	/*// 操作员权限管理页面
	@RequestMapping(value = "/conOperatorPer", method = RequestMethod.GET)
	public String conOperatorPer(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "506");
			if (result) {
				return "/xfglxt/conOperatorPer";
			} else {
				model.put("noPage", "您没有进入操作员管理的权限！");
				return "/xfglxt/xfglmain";
			}
		} else {
			return "redirect:xfglmain.html";
		}
	}*/

	// 商户管理页面
	@RequestMapping(value = "/merchant", method = RequestMethod.GET)
	public String merchant(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "501");
			if (result) {
				return "/xfglxt/merchant";
			} else {
				model.put("noPage", "您没有进入商户管理的权限！");
				return "/xfglxt/xfglmain";
			}
		} else {
			return "redirect:xfglmain.html";
		}
	}

	// 商户菜品管理页面
	@RequestMapping(value = "/merchantMenus", method = RequestMethod.GET)
	public String merchantMenus(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "511");
			if (result) {
				return "/xfglxt/merchantMenus";
			} else {
				model.put("noPage", "您没有进入商户菜品的权限！");
				return "/xfglxt/xfglmain";
			}
		} else {
			return "redirect:xfglmain.html";
		}
	}

	// 商户菜单管理页面
	@RequestMapping(value = "/merchantMenuType", method = RequestMethod.GET)
	public String merchantMenuType(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "512");
			if (result) {
				return "/xfglxt/merchantMenuType";
			} else {
				model.put("noPage", "您没有进入商户菜单的权限！");
				return "/xfglxt/xfglmain";
			}
		} else {
			return "redirect:xfglmain.html";
		}
	}

	// 商户菜单导入界面
	@RequestMapping(value = "/importMenus", method = RequestMethod.GET)
	public String importMenus(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "510");
			if (result) {
				return "/xfglxt/importMenus";
			} else {
				model.put("noPage", "您没有进入菜品导入的权限！");
				return "/xfglxt/xfglmain";
			}
		} else {
			return "redirect:xfglmain.html";
		}
	}

	/*// 餐次价格页面
	@RequestMapping(value = "/mealPrice", method = RequestMethod.GET)
	public String mealPrice(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "510");
			if (result) {
				return "/xfglxt/mealPrice";
			} else {
				model.put("noPage", "您没有进入餐次价格的权限！");
				return "/xfglxt/xfglmain";
			}
		} else {
			return "redirect:xfglmain.html";
		}
	}*/

	// 消费类型管理页面
	@RequestMapping(value = "/conType", method = RequestMethod.GET)
	public String conType(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "502");
			if (result) {
				return "/xfglxt/conType";
			} else {
				model.put("noPage", "您没有进入消费类型的权限！");
				return "/xfglxt/xfglmain";
			}
		} else {
			return "redirect:xfglmain.html";
		}
	}

	// 消费设备管理页面
	@RequestMapping(value = "/conTerminal", method = RequestMethod.GET)
	public String conTerminal(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "503");
			if (result) {
				return "/xfglxt/conTerminal";
			} else {
				model.put("noPage", "您没有进入消费设备的权限！");
				return "/xfglxt/xfglmain";
			}
		} else {
			return "redirect:xfglmain.html";
		}
	}

	// 消费记录查询页面
	@RequestMapping(value = "/conLog", method = RequestMethod.GET)
	public String conLog(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "504");
			if (result) {
				return "/xfglxt/conLog";
			} else {
				model.put("noPage", "您没有进入消费记录的权限！");
				return "/xfglxt/xfglmain";
			}
		} else {
			return "redirect:xfglmain.html";
		}
	}

	// 消费统计页面
	@RequestMapping(value = "/conStatistics", method = RequestMethod.GET)
	public String conStatistics(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "505");
			if (result) {
				return "/xfglxt/conStatistics";
			} else {
				model.put("noPage", "您没有进入消费统计的权限！");
				return "/xfglxt/xfglmain";
			}
		} else {
			return "redirect:xfglmain.html";
		}
	}

	/*// 部门消费统计页面
	@RequestMapping(value = "/conDepartment", method = RequestMethod.GET)
	public String conDepartment(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "507");
			if (result) {
				return "/xfglxt/conDepartment";
			} else {
				model.put("noPage", "您没有进入部门消费统计的权限！");
				return "/xfglxt/xfglmain";
			}
		} else {
			return "redirect:xfglmain.html";
		}
	}*/

	// 商户优惠页面
	@RequestMapping(value = "/merchantDiscount", method = RequestMethod.GET)
	public String merchantDiscount(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "508");
			if (result) {
				return "/xfglxt/merchantDiscount";
			} else {
				model.put("noPage", "您没有进入商户优惠的权限！");
				return "/xfglxt/xfglmain";
			}
		} else {
			return "redirect:xfglmain.html";
		}
	}

	// 积分优惠页面
	/*@RequestMapping(value = "/conDiscount", method = RequestMethod.GET)
	public String conDiscount(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "509");
			if (result) {
				return "/xfglxt/conDiscount";
			} else {
				model.put("noPage", "您没有进入积分优惠的权限！");
				return "/xfglxt/xfglmain";
			}
		} else {
			return "redirect:xfglmain.html";
		}
	}*/

	/*// 计时消费页面
	@RequestMapping(value = "/timeRule", method = RequestMethod.GET)
	public String timeRule(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "510");
			if (result) {
				return "/xfglxt/timeRule";
			} else {
				model.put("noPage", "您没有进入计时消费的权限！");
				return "/xfglxt/xfglmain";
			}
		} else {
			return "redirect:xfglmain.html";
		}
	}*/
	// 开票查询页面
	@RequestMapping(value = "/invoiceLog", method = RequestMethod.GET)
	public String invoiceLog(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "513");
			if (result) {
				return "/xfglxt/invoiceLog";
			} else {
				model.put("noPage", "您没有进入开票查询的权限！");
				return "/xfglxt/xfglmain";
			}
		} else {
			return "redirect:xfglmain.html";
		}
	}
	// 计时消费页面
	@RequestMapping(value = "/menuStatistics", method = RequestMethod.GET)
	public String menuStatistics(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "514");
			if (result) {
				return "/xfglxt/menuStatistics";
			} else {
				model.put("noPage", "您没有进入菜品统计的权限！");
				return "/xfglxt/xfglmain";
			}
		} else {
			return "redirect:xfglmain.html";
		}
	}
	// 临时卡消费统计界面
	@RequestMapping(value = "/lsConStatistics", method = RequestMethod.GET)
	public String lsConStatistics(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "515");
			if (result) {
				return "/xfglxt/lsConStatistics";
			} else {
				model.put("noPage", "您没有进入临时卡消费统计的权限！");
				return "/xfglxt/xfglmain";
			}
		} else {
			return "redirect:xfglmain.html";
		}
	}

	// 查询商户方法，在前台显示
	@RequestMapping(value = "/merchant/select", method = RequestMethod.POST)
	public void selectMerchant(HttpServletRequest request, HttpServletResponse response, DataGridModel dgm,
			ModelMap modelMap, Merchant merchant) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.selectMerchant(dgm, merchant);
		renderText(response, result);
	}

	// 查询商户方法，在下拉框显示
	@RequestMapping(value = "/merchantBox/select", method = RequestMethod.POST)
	public void selectMerchantBox(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.selectMerchantBox();
		renderText(response, result);
	}

	// 新增商户方法
	@RequestMapping(value = "/merchant/insert", method = RequestMethod.POST)
	public void insertMerchant(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			Merchant merchant) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.insertMerchant(merchant, request);
		renderText(response, result);
	}

	// 修改商户方法
	@RequestMapping(value = "/merchant/update", method = RequestMethod.POST)
	public void updateMerchant(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			Merchant merchant) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.updateMerchant(merchant, request);
		renderText(response, result);
	}

	// 查询消费类型方法，在前台显示
	@RequestMapping(value = "/conType/select", method = RequestMethod.POST)
	public void selectConType(HttpServletRequest request, HttpServletResponse response, DataGridModel dgm,
			ModelMap modelMap, ConType conType) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.selectConType(dgm, conType);
		renderText(response, result);
	}

	// 查询消费类型方法，在下拉框显示
	@RequestMapping(value = "/conTypeBox/select", method = RequestMethod.POST)
	public void selectConTypeBox(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.selectConTypeBox();
		renderText(response, result);
	}

	// 新增消费类型方法
	@RequestMapping(value = "/conType/insert", method = RequestMethod.POST)
	public void insertConType(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			ConType conType) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.insertConType(conType, request);
		renderText(response, result);
	}

	// 修改消费类型方法
	@RequestMapping(value = "/conType/update", method = RequestMethod.POST)
	public void updateConType(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			ConType conType) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.updateConType(conType, request);
		renderText(response, result);
	}

	// 查询消费设备方法，在前台显示
	@RequestMapping(value = "/conTerminal/select", method = RequestMethod.POST)
	public void selectConTerminal(HttpServletRequest request, HttpServletResponse response, DataGridModel dgm,
			ModelMap modelMap, ConTerminal conTerminal) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.selectConTerminal(dgm, conTerminal);
		renderText(response, result);
	}

	// 查询消费设备方法，在下拉框显示
	@RequestMapping(value = "/conTerminalBox/select", method = RequestMethod.POST)
	public void selectConTerminalBox(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.selectConTerminalBox();
		renderText(response, result);
	}

	// 新增消费设备方法
	@RequestMapping(value = "/conTerminal/insert", method = RequestMethod.POST)
	public void insertConTerminal(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			ConTerminal conTerminal) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.insertConTerminal(conTerminal, request);
		renderText(response, result);
	}

	// 修改消费设备方法
	@RequestMapping(value = "/conTerminal/update", method = RequestMethod.POST)
	public void updateConTerminal(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			ConTerminal conTerminal) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.updateConTerminal(conTerminal, request);
		renderText(response, result);
	}

	// 查询商户应用方法，在组合树显示
	@RequestMapping(value = "/merchantAppTree/select", method = RequestMethod.POST)
	public void selectMerchantAppTree(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.selectMerchantAppTree();
		renderText(response, result);
	}

	// 查询设备商户方法，在组合树显示
	@RequestMapping(value = "/conTerminalMerchantTree/select", method = RequestMethod.POST)
	public void selectConTerminalMerchantTree(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.selectConTerminalMerchantTree();
		renderText(response, result);
	}

	// 查询消费记录方法，在前台显示
	@RequestMapping(value = "/conLog/select", method = RequestMethod.POST)
	public void selectConLog(HttpServletRequest request, HttpServletResponse response, DataGridModel dgm,
			ModelMap modelMap) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.selectConLog(dgm, request);
		renderText(response, result);
	}

	// 导出消费记录方法
	@RequestMapping(value = "/conLogExcel/select", method = RequestMethod.POST)
	public void selectConLogExcel(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		String result = xiaofeiguanliService.selectConLogExcel(request);
		renderText(response, result);
	}

	// 新增消费记录方法
	@RequestMapping(value = "/conLog/insert", method = RequestMethod.POST)
	public void insertConLog(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.insertConLog(request);
		renderText(response, result);
	}

	// 消费统计方法，在前台显示
	@RequestMapping(value = "/conStatistics/select", method = RequestMethod.POST)
	public void selectConStatistics(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.selectConStatistics(request);
		renderText(response, result);
	}
	
	// 临时卡消费统计方法，在前台显示
	@RequestMapping(value = "/lsConStatistics/select", method = RequestMethod.POST)
	public void selectLSConStatistics(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.selectLSConStatistics(request);
		renderText(response, result);
	}

	// 导出消费统计方法
	@RequestMapping(value = "/conStatisticsExcel/select", method = RequestMethod.POST)
	public void selectConStatisticsExcel(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		String result = xiaofeiguanliService.selectConStatisticsExcel(request);
		renderText(response, result);
	}

	// 查询操作员权限方法，在前台显示
	@RequestMapping(value = "/conOperatorPer/select", method = RequestMethod.POST)
	public void selectConOperatorPer(HttpServletRequest request, HttpServletResponse response, DataGridModel dgm,
			ModelMap modelMap, Con_OperatorPer con_OperatorPer) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.selectConOperatorPer(dgm, con_OperatorPer);
		renderText(response, result);
	}

	// 修改操作员权限方法
	@RequestMapping(value = "/conOperatorPer/update", method = RequestMethod.POST)
	public void updateConOperatorPer(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			ConOperatorPer conOperatorPer) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.updateConOperatorPer(conOperatorPer, request);
		renderText(response, result);
	}

	// 查询部门消费方法
	// @RequestMapping(value = "/departmentCon/select", method =
	// RequestMethod.POST)
	// public void selectDepartment(HttpServletRequest request,
	// HttpServletResponse response, ModelMap model) throws IOException {
	// response.setCharacterEncoding("UTF-8");
	// String result = xiaofeiguanliService.selectDepartmentCon(request);
	// renderText(response, result);
	// }

	// 查询商户优惠方法，在前台显示
	@RequestMapping(value = "/MerchantDiscount/select", method = RequestMethod.POST)
	public void selectMerchantDiscount(HttpServletRequest request, HttpServletResponse response, DataGridModel dgm,
			ModelMap modelMap) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.selectMerchantDiscount(dgm, request);
		renderText(response, result);
	}

	// 新增商户优惠方法
	@RequestMapping(value = "/MerchantDiscount/insert", method = RequestMethod.POST)
	public void insertMerchantDiscount(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			MerchantDiscount MerchantDiscount) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.insertMerchantDiscount(MerchantDiscount, request);
		renderText(response, result);
	}

	// 删除商户优惠方法
	@RequestMapping(value = "/MerchantDiscount/delete", method = RequestMethod.POST)
	public void deleteMerchantDiscount(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			MerchantDiscount MerchantDiscount) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.deleteMerchantDiscount(MerchantDiscount, request);
		renderText(response, result);
	}

	// 修改商户优惠方法
	@RequestMapping(value = "/MerchantDiscount/update", method = RequestMethod.POST)
	public void updateMerchantDiscount(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			MerchantDiscount MerchantDiscount) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.updateMerchantDiscount(MerchantDiscount, request);
		renderText(response, result);
	}

	// 查询积分优惠方法，在前台显示
	@RequestMapping(value = "/ConDiscount/select", method = RequestMethod.POST)
	public void selectConDiscount(HttpServletRequest request, HttpServletResponse response, DataGridModel dgm,
			ModelMap modelMap) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.selectConDiscount(dgm, request);
		renderText(response, result);
	}

	// 新增积分优惠方法
	@RequestMapping(value = "/ConDiscount/insert", method = RequestMethod.POST)
	public void insertConDiscount(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			ConDiscount ConDiscount) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.insertConDiscount(ConDiscount, request);
		renderText(response, result);
	}

	// 删除积分优惠方法
	@RequestMapping(value = "/ConDiscount/delete", method = RequestMethod.POST)
	public void deleteConDiscount(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			ConDiscount ConDiscount) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.deleteConDiscount(ConDiscount, request);
		renderText(response, result);
	}

	// 查询计时消费方法，在前台显示
	@RequestMapping(value = "/TimeRule/select", method = RequestMethod.POST)
	public void selectTimeRule(HttpServletRequest request, HttpServletResponse response, DataGridModel dgm,
			ModelMap modelMap) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.selectTimeRule(dgm, request);
		renderText(response, result);
	}

	// 新增计时消费方法
	@RequestMapping(value = "/TimeRule/insert", method = RequestMethod.POST)
	public void insertTimeRule(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			TimeRule TimeRule) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.insertTimeRule(TimeRule, request);
		renderText(response, result);
	}

	// 修改计时消费方法
	@RequestMapping(value = "/TimeRule/update", method = RequestMethod.POST)
	public void updateTimeRule(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			TimeRule TimeRule) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.updateTimeRule(TimeRule, request);
		renderText(response, result);
	}

	// 查询当前消费设备的最大数
	@RequestMapping(value = "/ConDeviceNums/select", method = RequestMethod.POST)
	public void selectConDeviceNums(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			TimeRule TimeRule) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.selectConDeviceNums(TimeRule, request);
		renderText(response, result);
	}

	// 查询当前菜品类型
	@RequestMapping(value = "/MenuTypeBox/select", method = RequestMethod.POST)
	public void selectMenuTypeBox(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.selectMenuTypeBox(request);
		renderText(response, result);
	}
	

	// 查询计时消费方法，在前台显示
	@RequestMapping(value = "/MenuList/select", method = RequestMethod.POST)
	public void selectMenuList(HttpServletRequest request, HttpServletResponse response, DataGridModel dgm,
			ModelMap modelMap) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.selectMenuList(request, dgm);
		renderText(response, result);
	}

	// 查询计时消费方法，在前台显示
	@RequestMapping(value = "/menuList/insert", method = RequestMethod.POST)
	public void insertMenuList(HttpServletRequest request, HttpServletResponse response, DataGridModel dgm,
			ModelMap modelMap) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.insertMenuList(request, dgm);
		renderText(response, result);
	}

	// 查询计时消费方法，在前台显示
	@RequestMapping(value = "/menuList/update", method = RequestMethod.POST)
	public void updateMenuList(HttpServletRequest request, HttpServletResponse response, DataGridModel dgm,
			ModelMap modelMap) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.updateMenuList(request, dgm);
		renderText(response, result);
	}

	// 查询餐次价格方法，在前台显示
	@RequestMapping(value = "/MealPrice/select", method = RequestMethod.POST)
	public void selectMealPrice(HttpServletRequest request, HttpServletResponse response, DataGridModel dgm,
			ModelMap modelMap) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.selectMealPrice(dgm, request);
		renderText(response, result);
	}

	// 新增餐次价格方法
	@RequestMapping(value = "/MealPrice/insert", method = RequestMethod.POST)
	public void insertMealPrice(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			MealPrice MealPrice) throws IOException {
		System.out.println(1111);
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.insertMealPrice(MealPrice, request);
		renderText(response, result);
	}

	// 删除餐次价格方法
	@RequestMapping(value = "/MealPrice/delete", method = RequestMethod.POST)
	public void deleteMealPrice(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			MealPrice MealPrice) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.deleteMealPrice(MealPrice, request);
		renderText(response, result);
	}

	// 修改餐次价格方法
	@RequestMapping(value = "/MealPrice/update", method = RequestMethod.POST)
	public void updateMealPrice(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			MealPrice MealPrice) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.updateMealPrice(MealPrice, request);
		renderText(response, result);
	}
	
	// 查询菜品类型方法
	@RequestMapping(value = "/FMenuType/select", method = RequestMethod.POST)
	public void selectFMenuType(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,DataGridModel dgm,
			MealPrice MealPrice) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.selectFMenuType(dgm, request);
		renderText(response, result);
	}
	// 新增菜品类型方法
	@RequestMapping(value = "/menuType/insert", method = RequestMethod.POST)
	public void insertMenuType(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,DataGridModel dgm,
			MealPrice MealPrice) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.insertMenuType(dgm, request);
		renderText(response, result);
	}
	// 修改菜品类型方法
	@RequestMapping(value = "/menuType/update", method = RequestMethod.POST)
	public void updateMenuType(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,DataGridModel dgm,
			MealPrice MealPrice) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.updateMenuType(dgm, request);
		renderText(response, result);
	}
	
	// 修改菜品类型方法
	@RequestMapping(value = "/selectTerminalByContype", method = RequestMethod.POST)
	public void selectTerminalByContype(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,DataGridModel dgm,
			MealPrice MealPrice) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.selectTerminalByContype(dgm, request);
		renderText(response, result);
	}
	
	// 界面显示导入的菜品
	@RequestMapping(value = "/showMenusExcel", method = RequestMethod.POST)
	public void showMenusExcel(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = xiaofeiguanliService.showMenusExcel(request);
		renderText(response, result);
	}
	// 导入菜品
	@RequestMapping(value = "/importMenusExcel", method = RequestMethod.POST)
	public void importMenusExcel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		response.setCharacterEncoding("UTF-8");
		String result = xiaofeiguanliService.importMenusExcel(request);
		renderText(response, result);
	}
	// 查询开票信息
	@RequestMapping(value = "/invoiceLog/select", method = RequestMethod.POST)
	public void selectInvoiceLog(HttpServletRequest request, HttpServletResponse response, ModelMap model,DataGridModel dgm) throws Exception {
		response.setCharacterEncoding("UTF-8");
		String result = xiaofeiguanliService.selectInvoiceLog(request,dgm);
		renderText(response, result);
	}
	// 更新开票信息
	@RequestMapping(value = "/invoice/update", method = RequestMethod.POST)
	public void updateInvoiceLog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		String result = xiaofeiguanliService.updateInvoiceLog(request);
		renderText(response, result);
	}
	// 统计菜品消费
	@RequestMapping(value = "/conMenu/select", method = RequestMethod.POST)
	public void selectConMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		String result = xiaofeiguanliService.selectConMenu(request);
		renderText(response, result);
	}
}
