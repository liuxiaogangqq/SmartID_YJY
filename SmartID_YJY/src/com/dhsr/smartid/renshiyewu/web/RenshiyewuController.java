package com.dhsr.smartid.renshiyewu.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dhsr.smartid.base.web.BaseController;
import com.dhsr.smartid.peizhiguanli.dao.PeizhiguanliMapper;
import com.dhsr.smartid.peizhiguanli.domain.Operator;
import com.dhsr.smartid.peizhiguanli.domain.OperatorLog;
import com.dhsr.smartid.peizhiguanli.domain.SystemConfigure;
import com.dhsr.smartid.renshiyewu.domain.Company;
import com.dhsr.smartid.renshiyewu.domain.Department;
import com.dhsr.smartid.renshiyewu.domain.Mark;
import com.dhsr.smartid.renshiyewu.domain.User;
import com.dhsr.smartid.renshiyewu.service.RenshiyewuService;
import com.dhsr.smartid.util.DataGridModel;
import com.dhsr.smartid.util.HttpClintUtil;
import com.dhsr.smartid.util.PubFun;

import net.sf.json.JSONObject;

/**
 * 人事业务的Web层
 * 
 * @author qidong
 *
 */
@Controller
public class RenshiyewuController extends BaseController {

	@Resource
	private RenshiyewuService renshiyewuService;
	@Resource
	private PeizhiguanliMapper peizhiguanliMapper;

	/*private static String jumpip = "redirect:http://www.zgcicpark.com.cn/sso/?redirect=http://www.zgcicpark.com.cn/SmartID_ZGC/index.html";
	private static String tokenip = "http://www.zgcicpark.com.cn/sso/token";*/
	private static String jumpip = "redirect:http://150.138.117.98:8082/sso/?redirect=http://150.138.117.98:8999/SmartID_ZGC/index.html";
	private static String tokenip = "http://150.138.117.98:8082/sso/token";
	
	private Boolean checkJurisdiction(Operator operator, String pageRightId) {
		Boolean result = false;
		if(operator.getOperatorId().equals("admin")){
			return true;
		}else{
			String[] PageList = operator.getPageList().split(",");
			for (String Page : PageList) {
				if (Page.indexOf(pageRightId) == 0) {
					result = true;
					break;
				}
			}
			return result;
		}
	}

	// 登录页
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response) {
		/*String token = CookieUtils.getCookieValue(request, "IC_TOKEN");
		System.out.println(token);
		if(token == null){
			return jumpip;
		}else{
			Map<String,String> map = new HashMap<>();
			map.put("token", token);
	        String json = HttpClintUtil.doPost(tokenip,map);
	        JSONObject fromObject = JSONObject.fromObject(json);
        	Map<String,Object> result = (Map)fromObject;
			String status = result.get("status").toString();
			if(status.equals("200")){
				String string = result.get("data").toString();
				JSONObject fromObject1 = JSONObject.fromObject(string);
			    Map<String,Object> UserInfo = (Map)fromObject1;
			    String userId = UserInfo.get("userId").toString();
				Operator operator = renshiyewuService.selectOperatorByOperId(userId);
				if(operator == null){
					return jumpip;
				}else{
					request.getSession(true).setAttribute(Operator_SESSION, operator);
					//Session有效期
					SystemConfigure systemConfigure = peizhiguanliMapper.selectSystemConfigureById("SessionTimeout");
					request.getSession(true).setMaxInactiveInterval(Integer.valueOf(systemConfigure.getConfigureValue()) * 60);
					return "index";
				}
			}else if(status.equals("400")){
				
				return jumpip;
			}else{
				
				return jumpip;
			}
		}*/
		return "/login";
	}

	// 主页
		@RequestMapping(value = "/index", method = RequestMethod.GET)
		public String index(HttpServletRequest request, HttpServletResponse response) {
			/*System.out.println("进入");
			String token = CookieUtils.getCookieValue(request, "IC_TOKEN");
			System.out.println("进入"+token);
			if(token != null){
				Map<String,String> map = new HashMap<>();
				map.put("token", token);
				String json = HttpClintUtil.doPost(tokenip,map);
				JSONObject fromObject = JSONObject.fromObject(json);
				Map<String,Object> result = (Map)fromObject;
				String status = result.get("status").toString();
				if(status.equals("200")){
					String string = result.get("data").toString();
					JSONObject fromObject1 = JSONObject.fromObject(string);
					Map<String,Object> UserInfo = (Map)fromObject1;
					String userId = UserInfo.get("loginName").toString();
					System.out.println(userId+"//////////////////////////////////");
					Operator operator = renshiyewuService.selectOperatorByOperId(userId);
							
					if(operator == null){
						return "404";
					}else{
						request.getSession(true).setAttribute(Operator_SESSION, operator);
								//Session有效期
						SystemConfigure systemConfigure = peizhiguanliMapper.selectSystemConfigureById("SessionTimeout");
						request.getSession(true).setMaxInactiveInterval(Integer.valueOf(systemConfigure.getConfigureValue()) * 60);
						return "index";
					}
				}else if(status.equals("400")){
					return jumpip;
				}else{
					return jumpip;
				}
			}else{
				return jumpip;
			}*/
			return "index";
		}
		
	
	// 主页
	@RequestMapping(value = "/indexNo", method = RequestMethod.POST)
	public void indexNo(HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, String[]> parameterMap = request.getParameterMap();
		System.out.println(parameterMap);
		
		
		
	//	return "/index";
	}

	
	@RequestMapping(value = "/rsywmain", method = RequestMethod.GET)
	public String rsywmain(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "2");
			if (result) {
				return "/rsywzx/rsywmain";
			} else {
				model.put("noSystem", "您没有进入人事业务中心的权限");
				return "/index";
			}
		} else {
			model.put("backLogin", "login.html");
			return "/rsywzx/rsywmain";
		}
	}

	@RequestMapping(value = "/rsywright", method = RequestMethod.GET)
	public String rsywright() {
		return "/rsywzx/rsywright";
	}

	@RequestMapping(value = "/rsywsidebar", method = RequestMethod.GET)
	public String rsywsidebar() {
		return "/rsywzx/rsywsidebar";
	}

	@RequestMapping(value = "/rsywtop", method = RequestMethod.GET)
	public String rsywtop() {
		return "/rsywzx/rsywtop";
	}

	// 公司管理页面
	@RequestMapping(value = "/company", method = RequestMethod.GET)
	public String company(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "201");
			if (result) {
				return "/rsywzx/company";
			} else {
				model.put("noPage", "您没有进入公司管理的权限！");
				return "/rsywzx/rsywmain";
			}
		} else {
			return "redirect:rsywmain.html";
		}
	}

	// 部门管理页面
	@RequestMapping(value = "/department", method = RequestMethod.GET)
	public String department(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "202");
			if (result) {
				return "/rsywzx/department";
			} else {
				model.put("noPage", "您没有进入部门管理的权限！");
				return "/rsywzx/rsywmain";
			}
		} else {
			return "redirect:rsywmain.html";
		}
	}

	// 人员管理页面
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String user(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "203");
			if (result) {
				return "/rsywzx/user";
			} else {
				model.put("noPage", "您没有进入人员管理的权限！");
				return "/rsywzx/rsywmain";
			}
		} else {
			return "redirect:rsywmain.html";
		}

	}

	// 人员管理页面
	@RequestMapping(value = "/userExcel", method = RequestMethod.GET)
	public String userExcel(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "205");
			if (result) {
				return "/rsywzx/userExcel";
			} else {
				model.put("noPage", "您没有进入人员导入的权限！");
				return "/rsywzx/rsywmain";
			}
		} else {
			return "redirect:rsywmain.html";
		}

	}
	// 图片导入页面
	@RequestMapping(value = "/userImage", method = RequestMethod.GET)
	public String userImage(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "205");
			if (result) {
				return "/rsywzx/userImage";
			} else {
				model.put("noPage", "您没有进入图片导入的权限！");
				return "/rsywzx/rsywmain";
			}
		} else {
			return "redirect:rsywmain.html";
		}
		
	}

	// 标识管理页面
	@RequestMapping(value = "/mark", method = RequestMethod.GET)
	public String mark(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "204");
			if (result) {
				return "/rsywzx/mark";
			} else {
				model.put("noPage", "您没有进入标识管理的权限！");
				return "/rsywzx/rsywmain";
			}
		} else {
			return "redirect:rsywmain.html";
		}
	}
	
	// 标识管理页面
	@RequestMapping(value = "/markLog", method = RequestMethod.GET)
	public String markLog(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "204");
			if (result) {
				return "/rsywzx/markLog";
			} else {
				model.put("noPage", "您没有进入卡片操作查询的权限！");
				return "/rsywzx/rsywmain";
			}
		} else {
			return "redirect:rsywmain.html";
		}
	}
	
	
	// 操作员登录
	//@RequestMapping(value = "/login.html", method = RequestMethod.POST)
	/*public String loginprocess(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "OperatorId", required = true) String OperatorId,
			@RequestParam(value = "Password", required = true) String Password, ModelMap model)
			throws UnsupportedEncodingException {*/
	public String loginprocess(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		/*String token = CookieUtils.getCookieValue(request, "IC_TOKEN");
		System.out.println(token);
		if(token == null){
			return "redirect:http://114.242.157.8:8002/?redirect=http://172.16.0.4:8095/SmartID_ZGC/index.html";
		}else{
	        String json = HttpClintUtil.doGet("http://114.242.157.8:8002/token/"+token);
	        JSONObject fromObject = JSONObject.fromObject(json);
        	Map<String,Object> result = (Map)fromObject;
			String status = result.get("status").toString();
			if(status.equals("200")){
				String string = result.get("data").toString();
				JSONObject fromObject1 = JSONObject.fromObject(string);
			    Map<String,Object> UserInfo = (Map)fromObject1;
			    String userId = UserInfo.get("userId").toString();
				Operator operator = renshiyewuService.selectOperatorByOperId(userId);
				if(operator == null){
					return "redirect:http://114.242.157.8:8002/?redirect=http://172.16.0.4:8095/SmartID_ZGC/index.html";
				}else{
					request.getSession(true).setAttribute(Operator_SESSION, operator);
					//Session有效期
					SystemConfigure systemConfigure = peizhiguanliMapper.selectSystemConfigureById("SessionTimeout");
					request.getSession(true).setMaxInactiveInterval(Integer.valueOf(systemConfigure.getConfigureValue()) * 60);
					return "index";
				}
			}else if(status.equals("400")){
				
				return "redirect:http://114.242.157.8:8002/?redirect=http://172.16.0.4:8095/SmartID_ZGC/index.html";
			}else{
				
				return "redirect:http://114.242.157.8:8002/?redirect=http://172.16.0.4:8095/SmartID_ZGC/index.html";
			}
		}*/
		Operator operatorIn = new Operator();
		operatorIn.setOperatorId("admin");
		operatorIn.setPassword(DigestUtils.md5Hex("123456"));
		Operator operator = peizhiguanliMapper.selectOperatorByIdAndPwd(operatorIn);
		request.getSession(true).setAttribute(Operator_SESSION, operator);
		//Session有效期
		SystemConfigure systemConfigure = peizhiguanliMapper.selectSystemConfigureById("SessionTimeout");
		request.getSession(true).setMaxInactiveInterval(Integer.valueOf(systemConfigure.getConfigureValue()) * 60);
		return "index";
		/*response.setCharacterEncoding("utf-8");
		Operator operator = null;
		if (StringUtils.isEmpty(OperatorId) ) {
			model.put(ACTION_RESULT, "用户名不能为空");
			model.put(Operator_SESSION, operator);
			return "/login";
		} else if(StringUtils.isEmpty(Password)){
			model.put(ACTION_RESULT, "密码不能为空");
			model.put(Operator_SESSION, operator);
			return "/login";
		} else {
			Operator operatorIn = new Operator();
			operatorIn.setOperatorId(OperatorId);
			operatorIn.setPassword(DigestUtils.md5Hex(Password));
			operator = peizhiguanliMapper.selectOperatorByIdAndPwd(operatorIn);
			if (operator != null) {
				// 插入登录记录
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				OperatorLog operatorLog = new OperatorLog(null, operator.getOperatorInnerId(), operator.getOperatorId(),
						operator.getOperatorName(), operator.getPassword(), 0, sdf.format(new Date()),
						PubFun.getRemoteAddress(request), null);
				peizhiguanliMapper.insertOperatorLog(operatorLog);
				request.getSession(true).setAttribute(Operator_SESSION, operator);
				// Session有效期
				SystemConfigure systemConfigure = peizhiguanliMapper.selectSystemConfigureById("SessionTimeout");
				request.getSession(true)
						.setMaxInactiveInterval(Integer.valueOf(systemConfigure.getConfigureValue()) * 60);
				model.put(ACTION_RESULT, "成功");
				model.put(Operator_SESSION, operator);
				return "/index";
			} else {
				model.put(ACTION_RESULT, "用户名或密码错误,请重新输入");
				model.put(Operator_SESSION, operator);
				return "/login";
			}
		}*/
	}
	
	// 操作员登录
		@RequestMapping(value = "/login.html", method = RequestMethod.POST)
		public String loginprocess(HttpServletRequest request, HttpServletResponse response,
				@RequestParam(value = "OperatorId", required = true) String OperatorId,
				@RequestParam(value = "Password", required = true) String Password, ModelMap model)
				throws UnsupportedEncodingException {
			response.setCharacterEncoding("utf-8");
			Operator operator = null;
			if (StringUtils.isEmpty(OperatorId) ) {
				model.put(ACTION_RESULT, "用户名不能为空");
				model.put(Operator_SESSION, operator);
				return "/login";
			} else if(StringUtils.isEmpty(Password)){
				model.put(ACTION_RESULT, "密码不能为空");
				model.put(Operator_SESSION, operator);
				return "/login";
			} else {
				Operator operatorIn = new Operator();
				operatorIn.setOperatorId(OperatorId);
				operatorIn.setPassword(DigestUtils.md5Hex(Password));
				operator = peizhiguanliMapper.selectOperatorByIdAndPwd(operatorIn);
				if (operator != null) {
					// 插入登录记录
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					OperatorLog operatorLog = new OperatorLog(null, operator.getOperatorInnerId(), operator.getOperatorId(),
							operator.getOperatorName(), operator.getPassword(), 0, sdf.format(new Date()),
							PubFun.getRemoteAddress(request), null);
					peizhiguanliMapper.insertOperatorLog(operatorLog);
					request.getSession(true).setAttribute(Operator_SESSION, operator);
					// Session有效期
					SystemConfigure systemConfigure = peizhiguanliMapper.selectSystemConfigureById("SessionTimeout");
					request.getSession(true)
							.setMaxInactiveInterval(Integer.valueOf(systemConfigure.getConfigureValue()) * 60);
					model.put(ACTION_RESULT, "成功");
					model.put(Operator_SESSION, operator);
					return "/index";
				} else {
					model.put(ACTION_RESULT, "用户名或密码错误,请重新输入");
					model.put(Operator_SESSION, operator);
					return "/login";
				}
			}
		}

	// 操作员注销
	@RequestMapping(value = "/logout.html", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			OperatorLog operatorLog = new OperatorLog(null, operator.getOperatorInnerId(), operator.getOperatorId(),
					operator.getOperatorName(), operator.getPassword(), 1, sdf.format(new Date()),
					PubFun.getRemoteAddress(request), null);
			peizhiguanliMapper.insertOperatorLog(operatorLog);
		}
		request.getSession(true).setAttribute(Operator_SESSION, null);
		/*String token = CookieUtils.getCookieValue(request, "IC_TOKEN");
		Map<String,String> map = new HashMap<>();
		map.put("token", token);
		String json = HttpClintUtil.doPost("http://114.242.157.8:8002/logout",map);
		JSONObject fromObject = JSONObject.fromObject(json);
    	Map<String,Object> result = (Map)fromObject;
		String status = result.get("status").toString();
		if(status.equals("200")){
			return "redirect:login.html";
		}else if(status.equals("400")){
			return "redirect:404.html";
		}else{
			return "redirect:404.html";
		}*/
		return "login";
	}

	// 查询公司方法，在前台显示
	@RequestMapping(value = "/company/select", method = RequestMethod.POST)
	public void selectCompany(HttpServletRequest request, HttpServletResponse response, DataGridModel dgm,
			ModelMap modelMap, Company company) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = renshiyewuService.selectCompany(dgm, company);
		renderText(response, result);
	}

	// 查询公司方法，在下拉框显示
	@RequestMapping(value = "/companyBox/select", method = RequestMethod.POST)
	public void selectCompanyBox(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = renshiyewuService.selectCompanyBox();
		renderText(response, result);
	}
	
	// 新增公司方法
	@RequestMapping(value = "/company/insert", method = RequestMethod.POST)
	public void insertCompany(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			Company company) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = renshiyewuService.insertCompany(company, request);
		renderText(response, result);
	}

	// 修改公司方法
	@RequestMapping(value = "/company/update", method = RequestMethod.POST)
	public void updateCompany(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			Company company) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = renshiyewuService.updateCompany(company, request);
		renderText(response, result);
	}

	// 查询部门方法
	@RequestMapping(value = "/department/select", method = RequestMethod.POST)
	public void selectDepartment(HttpServletRequest request, HttpServletResponse response, Department department,
			ModelMap model) throws IOException {
		response.setCharacterEncoding("UTF-8");
		String result = renshiyewuService.selectDepartment(department,request);
		renderText(response, result);
	}

	// 查询公司部门方法
	@RequestMapping(value = "/companyDepartmentTree/select", method = RequestMethod.POST)
	public void companyDepartmentTree(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws IOException {
		response.setCharacterEncoding("UTF-8");
		String result = renshiyewuService.companyDepartmentTree(request);
		renderText(response, result);
	}

	// 新增部门方法
	@RequestMapping(value = "/department/insert", method = RequestMethod.POST)
	public void insertDepartment(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			Department department) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = renshiyewuService.insertDepartment(department, request);
		renderText(response, result);
	}

	// 修改部门方法
	@RequestMapping(value = "/department/update", method = RequestMethod.POST)
	public void updateDepartment(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			Department department) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = renshiyewuService.updateDepartment(department, request);
		renderText(response, result);
	}

	// 查询人员方法
	@RequestMapping(value = "/user/select", method = RequestMethod.POST)
	public void selectUser(HttpServletRequest request, HttpServletResponse response, DataGridModel dgm, ModelMap model)
			throws IOException {
		response.setCharacterEncoding("UTF-8");
		String result = renshiyewuService.selectUser(dgm, request);
		renderText(response, result);
	}

	// 人员导出Excel方法
	@RequestMapping(value = "/userExcel/select", method = RequestMethod.POST)
	public void selectUserExcel(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		String result = renshiyewuService.selectUserExcel(request);
		renderText(response, result);
	}

	// 查询人员部门方法
	@RequestMapping(value = "/userDepartmentTree/select", method = RequestMethod.POST)
	public void userDepartmentTree(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws IOException {
		response.setCharacterEncoding("UTF-8");
		String result = renshiyewuService.userDepartmentTree(request);
		renderText(response, result);
	}

	// 新增人员方法
	@RequestMapping(value = "/user/insert", method = RequestMethod.POST)
	public void insertUser(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, User user)
			throws IOException {
		response.setCharacterEncoding("UTF-8");
		String result = renshiyewuService.insertUser(user, request);
		renderText(response, result);
	}

	// 修改人员方法
	@RequestMapping(value = "/user/update", method = RequestMethod.POST)
	public void updateUser(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, User user)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = renshiyewuService.updateUser(user, request);
		renderText(response, result);
	}

	// 修改人员密码
	@RequestMapping(value = "/user/updatePWD", method = RequestMethod.POST)
	public void updatePWDUser(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = renshiyewuService.updatePWDUser(request);
		renderText(response, result);
	}

	// 查询标识方法，在前台显示
	@RequestMapping(value = "/mark/select", method = RequestMethod.POST)
	public void selectMark(HttpServletRequest request, HttpServletResponse response, DataGridModel dgm,
			ModelMap modelMap) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = renshiyewuService.selectMark(dgm, request);
		renderText(response, result);
	}

	// 新增标识方法
	@RequestMapping(value = "/mark/insert", method = RequestMethod.POST)
	public void insertMark(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Mark mark)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = renshiyewuService.insertMark(mark, request);
		renderText(response, result);
	}
	// 新增标识方法
	@RequestMapping(value = "/mark/buCard", method = RequestMethod.POST)
	public void insertBuMark(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Mark mark)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = renshiyewuService.insertBuMark(mark, request);
		renderText(response, result);
	}

	// 修改标识方法
	@RequestMapping(value = "/mark/update", method = RequestMethod.POST)
	public void updateMark(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Mark mark)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = renshiyewuService.updateMark(mark, request);
		renderText(response, result);
	}

	// 修改标识方法
	@RequestMapping(value = "/markState/update", method = RequestMethod.POST)
	public void updateMarkState(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Mark mark)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = renshiyewuService.updateMarkState(mark,request);
		renderText(response, result);
	}

	// 查询人员权限方法，在前台显示
	@RequestMapping(value = "/userPer/select", method = RequestMethod.POST)
	public void selectUserPer(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			Integer UserInnerId) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = renshiyewuService.selectUserPer(UserInnerId);
		renderText(response, result);
	}

	// 修改人员权限方法
	@RequestMapping(value = "/userPer/update", method = RequestMethod.POST)
	public void updateUserPer(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, String str)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = renshiyewuService.updateUserPer(str);
		renderText(response, result);
	}

	// 人员导入验证
	@RequestMapping(value = "/showUserExcel.html", method = RequestMethod.POST)
	public void showUserExcel(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws IOException {
		response.setCharacterEncoding("UTF-8");
		String result = renshiyewuService.showUserExcel(request);
		renderText(response, result);
	}

	// 人员导入
	@RequestMapping(value = "/importUserExcel.html", method = RequestMethod.POST)
	public void importUserExcel(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		String result = renshiyewuService.importUserExcel(request);
		renderText(response, result);
	}
	// 人员导入
	@RequestMapping(value = "user/imageImport.html", method = RequestMethod.POST)
	public void imageImport(HttpServletRequest request, HttpServletResponse response, ModelMap model,MultipartHttpServletRequest requestFile)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		String result = renshiyewuService.importUserImage(requestFile,request);
		renderText(response, result);
	}
	
	// 查询当前数据库中最大人员数量
	@RequestMapping(value = "/userMaxmum/select.html", method = RequestMethod.POST)
	public void selectuserMaxmum(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		String result = renshiyewuService.selectuserMaxmum(request);
		renderText(response, result);
	}
	
	// 查询当前数据库中最大人员数量
	@RequestMapping(value = "/markLog/select", method = RequestMethod.POST)
	public void selectMarkLog(HttpServletRequest request, HttpServletResponse response,DataGridModel dgm, ModelMap model)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		String result = renshiyewuService.selectMarkLog(request,dgm);
		renderText(response, result);
	}
	
	// 查询当前数据库中最大人员数量
	@RequestMapping(value = "/SelectQuanxianByOperInnerId", method = RequestMethod.POST)
	public void SelectQuanxianByOperInnerId(HttpServletRequest request, HttpServletResponse response,DataGridModel dgm, ModelMap model)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		String result = renshiyewuService.SelectQuanxianByOperInnerId(request);
		renderText(response, result);
	}
	
	// 查询当前数据库中最大人员数量
	@RequestMapping(value = "/userShouquan/update", method = RequestMethod.POST)
	public void updateuserShouquan(HttpServletRequest request, HttpServletResponse response,DataGridModel dgm, ModelMap model)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		String result = renshiyewuService.updateuserShouquan(request);
		renderText(response, result);
	}
	
	// 从门禁中同步人员信息
	@RequestMapping(value = "/tongburenyuanxinxi", method = RequestMethod.POST)
	public void tongburenyuanxinxi(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
        String json = HttpClintUtil.doPost("http://114.242.157.8:8070/PersonnelSynchronization/tongbu");
        System.out.println(json);
	    JSONObject fromObject = JSONObject.fromObject(json);
        Map<String,Object> result = (Map)fromObject;
		String status = result.get("success").toString();
		JSONObject jo = new JSONObject();
		if(status.equals("200")){
			jo.put("code", 200);
			jo.put("msg", "同步完成");
		}else{
			jo.put("code", 500);
			jo.put("msg", "同步失败");
		}
		renderText(response, jo.toString());
	}
	
	// 设置扣费部门从门禁中同步人员信息
	@RequestMapping(value = "/CompanyPower", method = RequestMethod.POST)
	public void CompanyPower(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		String result = renshiyewuService.CompanyPower(request);
		renderText(response, result);
	}
	
	// 设置扣费部门从门禁中同步人员信息
	@RequestMapping(value = "/depCon/update", method = RequestMethod.POST)
	public void updateDepCon(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		String result = renshiyewuService.updateDepCon(request);
		renderText(response, result);
	}

}
