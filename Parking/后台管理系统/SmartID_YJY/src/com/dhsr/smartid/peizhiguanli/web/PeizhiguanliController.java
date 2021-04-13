package com.dhsr.smartid.peizhiguanli.web;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dhsr.smartid.base.web.BaseController;
import com.dhsr.smartid.peizhiguanli.domain.App;
import com.dhsr.smartid.peizhiguanli.domain.Area;
import com.dhsr.smartid.peizhiguanli.domain.Configure;
import com.dhsr.smartid.peizhiguanli.domain.Operator;
import com.dhsr.smartid.peizhiguanli.domain.SysTerminal;
import com.dhsr.smartid.peizhiguanli.domain.SystemConfigure;
import com.dhsr.smartid.peizhiguanli.domain.UserType;
import com.dhsr.smartid.peizhiguanli.service.PeizhiguanliService;
import com.dhsr.smartid.util.DataGridModel;

/**
 * 配置管理的Web层
 * 
 * @author qidong
 *
 */
@Controller
public class PeizhiguanliController extends BaseController {

	@Resource
	private PeizhiguanliService peizhiguanliService;

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

	@RequestMapping(value = "/pzglmain", method = RequestMethod.GET)
	public String pzglmain(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "1");
			if (result) {
				return "/pzglzx/pzglmain";
			} else {
				model.put("noSystem", "您没有进入配置管理中心的权限");
				return "/index";
			}
		} else {
			model.put("backLogin", "login.html");
			return "/pzglzx/pzglmain";
		}
	}

	@RequestMapping(value = "/pzglright", method = RequestMethod.GET)
	public String pzglright() {
		return "/pzglzx/pzglright";
	}

	@RequestMapping(value = "/pzglsidebar", method = RequestMethod.GET)
	public String pzglsidebar() {
		return "/pzglzx/pzglsidebar";
	}

	@RequestMapping(value = "/pzgltop", method = RequestMethod.GET)
	public String pzgltop() {
		return "/pzglzx/pzgltop";
	}

	// 系统配置页面
	@RequestMapping(value = "/systemConfigure", method = RequestMethod.GET)
	public String systemConfigure(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "101");
			if (result) {
				return "/pzglzx/systemConfigure";
			} else {
				model.put("noPage", "您没有进入系统配置的权限！");
				return "/pzglzx/pzglmain";
			}
		} else {
			return "redirect:pzglmain.html";
		}
	}

	// 页面配置页面
	@RequestMapping(value = "/configure", method = RequestMethod.GET)
	public String configure(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "102");
			if (result) {
				return "/pzglzx/configure";
			} else {
				model.put("noPage", "您没有进入页面配置的权限！");
				return "/pzglzx/pzglmain";
			}
		} else {
			return "redirect:pzglmain.html";
		}
	}

	// 区域管理页面
	@RequestMapping(value = "/area", method = RequestMethod.GET)
	public String area(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "103");
			if (result) {
				return "/pzglzx/area";
			} else {
				model.put("noPage", "您没有进入区域管理的权限！");
				return "/pzglzx/pzglmain";
			}
		} else {
			return "redirect:pzglmain.html";
		}
	}

	// 应用管理页面
	@RequestMapping(value = "/app", method = RequestMethod.GET)
	public String app(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "104");
			if (result) {
				return "/pzglzx/app";
			} else {
				model.put("noPage", "您没有进入应用管理的权限！");
				return "/pzglzx/pzglmain";
			}
		} else {
			return "redirect:pzglmain.html";
		}
	}

	// 我的信息页面
	@RequestMapping(value = "/myInfo", method = RequestMethod.GET)
	public String myInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "108");
			if (result) {
				return "/pzglzx/myInfo";
			} else {
				model.put("noPage", "您没有进入我的信息的权限！");
				return "/pzglzx/pzglmain";
			}
		} else {
			return "redirect:pzglmain.html";
		}
	}

	// 操作员管理页面
	@RequestMapping(value = "/operator", method = RequestMethod.GET)
	public String operator(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "105");
			if (result) {
				return "/pzglzx/operator";
			} else {
				model.put("noPage", "您没有进入操作员管理的权限！");
				return "/pzglzx/pzglmain";
			}
		} else {
			return "redirect:pzglmain.html";
		}
	}

	// 人员类型管理页面
	@RequestMapping(value = "/userType", method = RequestMethod.GET)
	public String userType(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "106");
			if (result) {
				return "/pzglzx/userType";
			} else {
				model.put("noPage", "您没有进入人员类型管理的权限！");
				return "/pzglzx/pzglmain";
			}
		} else {
			return "redirect:pzglmain.html";
		}
	}

	// 设备管理页面
	@RequestMapping(value = "/sysTerminal", method = RequestMethod.GET)
	public String sysTerminal(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "107");
			if (result) {
				return "/pzglzx/sysTerminal";
			} else {
				model.put("noPage", "您没有进入设备管理的权限！");
				return "/pzglzx/pzglmain";
			}
		} else {
			return "redirect:pzglmain.html";
		}
	}

	// 查询页面配置方法，在前台显示
	@RequestMapping(value = "/configure/select", method = RequestMethod.POST)
	public void selectConfigure(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			String Page) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.selectConfigure(Page);
		renderText(response, result);
	}

	// 修改页面配置方法
	@RequestMapping(value = "/configure/update", method = RequestMethod.POST)
	public void updateConfigure(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			Configure configure) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.updateConfigure(configure);
		renderText(response, result);
	}

	// 查询系统配置方法，在前台显示
	@RequestMapping(value = "/systemConfigure/select", method = RequestMethod.POST)
	public void selectSystemConfigure(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.selectSystemConfigure();
		renderText(response, result);
	}

	// 根据编号查询系统配置
	@RequestMapping(value = "/systemConfigureById/select", method = RequestMethod.POST)
	public void selectSystemConfigureById(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			String ConfigureId) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.selectSystemConfigureById(ConfigureId);
		renderText(response, result);
	}

	// 修改系统配置方法
	@RequestMapping(value = "/systemConfigure/update", method = RequestMethod.POST)
	public void updateSystemConfigure(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			SystemConfigure systemConfigure) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.updateSystemConfigure(systemConfigure);
		renderText(response, result);
	}

	// 查询区域方法，在前台显示
	@RequestMapping(value = "/area/select", method = RequestMethod.POST)
	public void selectArea(HttpServletRequest request, HttpServletResponse response, DataGridModel dgm,
			ModelMap modelMap, Area area) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.selectArea(dgm, area);
		renderText(response, result);
	}

	// 查询区域方法，在下拉框显示
	@RequestMapping(value = "/areaBox/select", method = RequestMethod.POST)
	public void selectAreaBox(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.selectAreaBox();
		renderText(response, result);
	}

	// 新增区域方法
	@RequestMapping(value = "/area/insert", method = RequestMethod.POST)
	public void insertArea(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Area area)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.insertArea(area, request);
		renderText(response, result);
	}

	// 修改区域方法
	@RequestMapping(value = "/area/update", method = RequestMethod.POST)
	public void updateArea(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Area area)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.updateArea(area, request);
		renderText(response, result);
	}

	// 查询应用方法，在前台显示
	@RequestMapping(value = "/app/select", method = RequestMethod.POST)
	public void selectApp(HttpServletRequest request, HttpServletResponse response, DataGridModel dgm,
			ModelMap modelMap, App app) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.selectApp(dgm, app);
		renderText(response, result);
	}

	// 查询应用方法，在下拉框显示
	@RequestMapping(value = "/appBox/select", method = RequestMethod.POST)
	public void selectAppBox(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			Integer AppTypeInnerId) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.selectAppBox(AppTypeInnerId);
		renderText(response, result);
	}

	// 新增应用方法
	@RequestMapping(value = "/app/insert", method = RequestMethod.POST)
	public void insertApp(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, App app)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.insertApp(app, request);
		renderText(response, result);
	}

	// 修改应用方法
	@RequestMapping(value = "/app/update", method = RequestMethod.POST)
	public void updateApp(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, App app)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.updateApp(app, request);
		renderText(response, result);
	}

	// 查询应用类型方法，在下拉框显示
	@RequestMapping(value = "/appTypeBox/select", method = RequestMethod.POST)
	public void selectAppTypeBox(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.selectAppTypeBox();
		renderText(response, result);
	}

	// 查询标识类型方法，在下拉框显示
	@RequestMapping(value = "/markTypeBox/select", method = RequestMethod.POST)
	public void selectMarkTypeBox(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.selectMarkTypeBox();
		renderText(response, result);
	}

	// 查询终端类型，在下拉框显示
	@RequestMapping(value = "/terminalTypeBox/select", method = RequestMethod.POST)
	public void selectTerminalTypeBox(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.selectTerminalTypeBox();
		renderText(response, result);
	}

	// 查询操作员方法，在前台显示
	@RequestMapping(value = "/operator/select", method = RequestMethod.POST)
	public void selectOperator(HttpServletRequest request, HttpServletResponse response, DataGridModel dgm,
			ModelMap modelMap, Operator operator) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.selectOperator(dgm, operator, request);
		renderText(response, result);
	}

	// 查询当前操作员方法，在前台显示
	@RequestMapping(value = "/operator/selectNow", method = RequestMethod.POST)
	public void selectNowOperator(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.selectNowOperator(request);
		renderText(response, result);
	}

	// 查询操作员方法，在下拉框显示
	@RequestMapping(value = "/operatorBox/select", method = RequestMethod.POST)
	public void selectOperatorBox(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.selectOperatorBox();
		renderText(response, result);
	}

	// 新增操作员方法
	@RequestMapping(value = "/operator/insert", method = RequestMethod.POST)
	public void insertOperator(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			Operator operator) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.insertOperator(operator, request);
		renderText(response, result);
	}

	// 修改操作员密码方法
	@RequestMapping(value = "/operator/updatePWD", method = RequestMethod.POST)
	public void updatePWDOperator(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.updatePWDOperator(request);
		renderText(response, result);
	}

	// 修改操作员方法
	@RequestMapping(value = "/operator/update", method = RequestMethod.POST)
	public void updateOperator(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			Operator operator) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.updateOperator(operator, request);
		renderText(response, result);
	}

	// 查询区域应用方法，在组合树显示
	@RequestMapping(value = "/areaAppTree/select", method = RequestMethod.POST)
	public void selectAreaAppTree(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			Integer AppTypeInnerId) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.selectAreaAppTree(AppTypeInnerId);
		renderText(response, result);
	}

	// 查询人员类型方法，在前台显示
	@RequestMapping(value = "/userType/select", method = RequestMethod.POST)
	public void selectUserType(HttpServletRequest request, HttpServletResponse response, DataGridModel dgm,
			ModelMap modelMap, UserType userType) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.selectUserType(dgm, userType);
		renderText(response, result);
	}

	// 查询人员类型方法，在下拉框显示
	@RequestMapping(value = "/userTypeBox/select", method = RequestMethod.POST)
	public void selectUserTypeBox(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.selectUserTypeBox();
		renderText(response, result);
	}

	// 新增人员类型方法
	@RequestMapping(value = "/userType/insert", method = RequestMethod.POST)
	public void insertUserType(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			UserType userType) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.insertUserType(userType, request);
		renderText(response, result);
	}

	// 修改人员类型方法
	@RequestMapping(value = "/userType/update", method = RequestMethod.POST)
	public void updateUserType(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			UserType userType) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.updateUserType(userType, request);
		renderText(response, result);
	}

	// 查询页面权限树方法
	@RequestMapping(value = "/pageRightTree/select", method = RequestMethod.POST)
	public void pageRightTree(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws IOException {
		response.setCharacterEncoding("UTF-8");
		String result = peizhiguanliService.pageRightTree();
		renderText(response, result);
	}

	// 查询设备方法，在前台显示
	@RequestMapping(value = "/sysTerminal/select", method = RequestMethod.POST)
	public void selectSysTerminal(HttpServletRequest request, HttpServletResponse response, DataGridModel dgm,
			ModelMap modelMap, SysTerminal sysTerminal) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.selectSysTerminal(dgm, sysTerminal);
		renderText(response, result);
	}

	// 查询设备方法，在下拉框显示
	@RequestMapping(value = "/sysTerminalBox/select", method = RequestMethod.POST)
	public void selectSysTerminalBox(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.selectSysTerminalBox();
		renderText(response, result);
	}

	// 新增设备方法
	@RequestMapping(value = "/sysTerminal/insert", method = RequestMethod.POST)
	public void insertSysTerminal(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			SysTerminal sysTerminal) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.insertSysTerminal(sysTerminal, request);
		renderText(response, result);
	}

	// 修改设备方法
	@RequestMapping(value = "/sysTerminal/update", method = RequestMethod.POST)
	public void updateSysTerminal(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			SysTerminal sysTerminal) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = peizhiguanliService.updateSysTerminal(sysTerminal, request);
		renderText(response, result);
	}

}
